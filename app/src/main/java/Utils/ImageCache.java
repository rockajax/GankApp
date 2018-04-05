package Utils;

/**
 * Created by mac on 2018/4/2.
 */
import android.graphics.Bitmap;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.Map;

/**
 * LruCache的子类ImageCache
 */

public class ImageCache extends LruCache<String,Bitmap> {


    private Map<String,SoftReference<Bitmap>> CacheMap;//定义一个强引用 用于查询bitmap

    public ImageCache(Map<String, SoftReference<Bitmap>> cacheMap) {
        super((int) (Runtime.getRuntime().maxMemory()/8));//这里最大内存设为运行时内存的1/8
        this.CacheMap = cacheMap;
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

    /**
     * 取出强引用map
     * @return
     */
    public Map<String, SoftReference<Bitmap>> getCacheMap() {
        return CacheMap;
    }
}
