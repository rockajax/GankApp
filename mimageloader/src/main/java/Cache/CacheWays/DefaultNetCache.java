package Cache.CacheWays;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;

import Cache.mCache;
import start.ImageLoader;
/**
 * Created by mac on 2018/4/8.
 */

public class DefaultNetCache implements mCache {

    private MemoryCache memoryCache;//LruCache
    private DiskCache diskCache;//磁盘缓存
    private NetCache netCache;//网络下载
    private volatile static  DefaultNetCache instance;

    private DefaultNetCache(){
        File CacheDir = ImageLoader.getInstance().getContext().getExternalCacheDir();
        if(CacheDir==null){
            Log.d("defaultNetCache", "DefaultNetCache: ");
            return;
        }
        String filepath = CacheDir.getPath();
        memoryCache = new MemoryCache();
        diskCache = new DiskCache(filepath);
        netCache = new NetCache();
    }

    public static DefaultNetCache getInstance(){
        if(instance==null){
            synchronized (DefaultNetCache.class){
                if(instance == null){
                    instance = new DefaultNetCache();
                }
            }
        }
        return instance;
    }

    @Override
    public void putImage(String url, Bitmap bitmap) {

    }

    @Override
    public Bitmap getImage(String url) {
        Bitmap bitmap = memoryCache.get(url);

        if (bitmap != null) {
            Log.e("defaultNetCache", "find in memory");
            memoryCache.put(url, bitmap);//在LruCache中增加一次计数
            return bitmap;
        }
        bitmap = diskCache.getImage(url);

        if (bitmap != null) {//在disk中找到图片 在LruCache中增加一次计数
            memoryCache.put(url, bitmap);
            Log.e("defaultNetCache", "find in disk");
            return bitmap;
        }

        bitmap = netCache.getImage(url);
        if (bitmap != null) {//从网络中加载
            Log.e("defaultNetCache", "download from netWork");
            memoryCache.put(url, bitmap);
            diskCache.putImage(url, bitmap);
            return bitmap;
        }
        Log.e("strategy", "NetWork is wrong " + url);
        return null;
    }
}
