package Utils;

/**
 * Created by mac on 2018/4/2.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.example.mac.fuckthingapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * 缓存工具类
 */
public class CacheUtil {
    private static CacheUtil instance;

    private Context context;
    private ImageCache imageCache;
    private boolean isSetNullBitmap;

    public CacheUtil(Context context) {
        this.context = context;
        Map<String,SoftReference<Bitmap>> cacheMap = new HashMap<>();
        this.imageCache = new ImageCache(cacheMap);
    }

    public static CacheUtil getInstance(Context context){
        if(instance==null){
            synchronized (CacheUtil.class){
                instance = new CacheUtil(context);
            }
        }
        return instance;
    }

    /**
     * 将图片存入缓存中
     */
    private void putBitmapIntoCache(String filename,byte[] data){
        FileUtil.getInstance(context).writeFileToStorage(filename,data);//这里写进内存
        Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);

        if(filename!=null&&bitmap!=null){
            //同时也将图片放进LruCache的引用集合里面
            imageCache.put(filename,bitmap);
        }
    }

    /**
     * 从缓存中取出图片
     */
    private Bitmap getBitmapFromCache(String filename){
        Bitmap bitmap = null;
        bitmap = imageCache.get(filename);//在引用中找不到的话 去弱引用里面找
        if(bitmap==null){
            Log.d(TAG, "getBitmapFromCache: 开始在弱引用找");
            Map<String,SoftReference<Bitmap>> cacheMap = imageCache.getCacheMap();
            SoftReference<Bitmap> softReference = cacheMap.get(filename);
            if(softReference!=null){
                Log.d(TAG, "getBitmapFromCache: 弱引用找到了");
                bitmap = softReference.get();
                if(bitmap!=null&&bitmap!=null){
                    imageCache.put(filename,bitmap);
                }//弱引用找到了取出来 并且存到强引用里去
            }else{
                //如果不在弱引用中 就在内存去找
                Log.d(TAG, "getBitmapFromCache: 开始在内存里找");
                byte[] data = FileUtil.getInstance(context).readBytesFromStorage(filename);
                if(data!=null && data.length>0){
                    Log.d(TAG, "getBitmapFromCache: 内存中找到了");
                    bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                    if(filename!=null&&bitmap!=null){
                        imageCache.put(filename,bitmap);
                    }//找到了就放进引用集合里面
                }
            }
        }
        return bitmap;
    }

    /**
     * 使用三级缓存设置ImageView的图片
     */

    public void setImageCache(final String path, final ImageView view,boolean isSetNullBitmap) {
        Log.d(TAG, "setImageCache: 开始寻找图片");
        //将文件名转化为MD5 便于保存
        final String filename = MD5Util.StringToMD5(path.substring(path.lastIndexOf(File.separator) + 1));
        Bitmap bitmap = null;
        if(filename!=null){
             bitmap= getBitmapFromCache(filename);
        }
        if(bitmap!=null){
            Log.d(TAG, "setImageCache: 当前存在");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            view.setImageBitmap(bitmap);
        }else{
            Log.d(TAG, "setImageCache: 只有下载了");
            //如果在强引用 弱引用 内存中都找不到的话 就在网络上下载
            if(!path.equals("")){//地址不为空才下载
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        //先设置占位图再下载
                        view.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
                    }
                });
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        byte[] b = HttpUtil.getInstance().getByteArrayFromWeb(path);
                        if(b!=null&&b.length>0){
                            putBitmapIntoCache(filename,b);//下载了就存入内存和强引用中 相当于双缓存
                            final Bitmap bitmap1 = BitmapFactory.decodeByteArray(b,0,b.length);
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                                    bitmap1.compress(Bitmap.CompressFormat.JPEG, 50, out);
                                    view.setImageBitmap(bitmap1);
                                }
                            });
                        }
                    }
                }).start();
            }else{
                if(isSetNullBitmap){//如果没地址 可以选择是否设置占位图
                    view.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher));
                }
                if(!isSetNullBitmap)
                    Log.d(TAG, "setImageCache: 不设置占位图哈");
            }
        }
    }
    public void setImageCacheNotCompress(final String path, final ImageView view,boolean isSetNullBitmap) {
        Log.d(TAG, "setImageCache: 开始寻找图片");
        //将文件名转化为MD5 便于保存
        final String filename = MD5Util.StringToMD5(path.substring(path.lastIndexOf(File.separator) + 1));
        Bitmap bitmap = null;
        if(filename!=null){
            bitmap= getBitmapFromCache(filename);
        }
        if(bitmap!=null){
            Log.d(TAG, "setImageCache: 当前存在");
            view.setImageBitmap(bitmap);
        }else{
            Log.d(TAG, "setImageCache: 只有下载了");
            //如果在强引用 弱引用 内存中都找不到的话 就在网络上下载
            if(!path.equals("")){//地址不为空才下载
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        //先设置占位图再下载
                        view.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
                    }
                });
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        byte[] b = HttpUtil.getInstance().getByteArrayFromWeb(path);
                        if(b!=null&&b.length>0){
                            putBitmapIntoCache(filename,b);//下载了就存入内存和强引用中 相当于双缓存
                            final Bitmap bitmap1 = BitmapFactory.decodeByteArray(b,0,b.length);
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    view.setImageBitmap(bitmap1);
                                }
                            });
                        }
                    }
                }).start();
            }else{
                if(isSetNullBitmap){//如果没地址 可以选择是否设置占位图
                    view.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher));
                }
                if(!isSetNullBitmap)
                    Log.d(TAG, "setImageCache: 不设置占位图哈");
            }
        }
    }
}

