package Cache.CacheWays;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import Cache.mCache;
import Utils.MD5Util;

/**
 * Created by mac on 2018/4/8.
 */

public class MemoryCache extends LruCache<String,Bitmap> implements mCache {

    //软引用的一个好处是当系统空间紧张的时候，软引用可以随时销毁，因此软引用是不会影响系统运行的，
    private Map<String,SoftReference<Bitmap>> CacheMap = new HashMap<>();//定义一个软引用 用于查询bitmap

    private LruCache<String,Bitmap> lruCache;

    public MemoryCache() {
        super((int) (Runtime.getRuntime().maxMemory()/8));
        lruCache = new LruCache<>((int) (Runtime.getRuntime().maxMemory()) / 8);
    }

    @Override
    public void putImage(String url, Bitmap bitmap) {
        lruCache.put(MD5Util.StringToMD5(url),bitmap);
    }

    @Override
    public Bitmap getImage(String url) {
        String filename = MD5Util.StringToMD5(url);
        Bitmap bitmap = null;
        bitmap = lruCache.get(filename);
        if (bitmap == null) {
            SoftReference<Bitmap> softReference = CacheMap.get(filename);
            if (softReference != null) {
                bitmap = softReference.get();
                if (bitmap != null) {
                    lruCache.put(filename, bitmap);
                }//弱引用找到了取出来 并且存到强引用里去
            }
        }
        return bitmap;
    }

    /**
     * 获取图片大小
     */
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    /**
     *
     * @param key      新加入的key
     * @param oldValue 被移除的value
     * @param newValue 新加入的value
     * 如果你cache的某个值需要明确释放，重写entryRemoved()
     * 当有图片从LruCache中移除时，将其放进软引用集合中
     */
    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        if(oldValue != null){
            SoftReference<Bitmap> softReference = new SoftReference<>(oldValue);
            if(key!=null){
                CacheMap.put(key,softReference);
            }
        }
    }

}
