package NoUsedOldLoader;

import android.util.Log;
import android.view.View;
import config.mConfig;
import Cache.mCache;

/**
 * Created by mac on 2018/4/8.
 */

public abstract class mLoader {
    private static final String TAG = mLoader.class.getSimpleName();
    private mCache cache;
    private mConfig config;


    protected abstract boolean checkSource(Object o);
    public abstract void load(Object o, View imageView, Object tag);

    public void load(Object o,View imageview){
        load(o,imageview,null);
    }

    mCache getCache(){
        return cache;
    }

    public void setCache(mCache cache) {
        this.cache = cache;
    }

    mConfig getConfig() {
        return config;
    }

    public void setConfig(mConfig config) {
        this.config = config;
    }

    /**
     * 确认必要的配置
     * @return
     */
    boolean checkEngine() {
        if (cache == null) {
            Log.e("loader", "cache is null");
            return false;
        }
        if (config == null) {
            Log.e("loader", "config is null");
            return false;
        }
        return true;
    }
}
