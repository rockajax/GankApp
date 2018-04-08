package start;

import android.widget.ImageView;

import Cache.CacheWays.DefaultNetCache;
import Cache.mCache;
import NoUsedOldLoader.mLoader;
import config.RequestOptions;
import config.mConfig;

/**
 * Created by mac on 2018/4/8.
 * 自定义设置类
 */

public class RequestBuilder {
    private Object source;
    private mCache cache;
    private mConfig config;
    private mLoader loader;
    private ImageView imageView;

    /**
     * 默认配置
     * @param source 图片
     * @param loader
     * @return
     */
    public RequestBuilder init(Object source, mLoader loader) {
        this.source = source;
        this.loader = loader;
        this.cache = DefaultNetCache.getInstance();
        this.config = new RequestOptions();
        return this;
    }

    public RequestBuilder apply(mConfig config){
        this.config = config;
        return this;
    }

    public RequestBuilder cacheStrategy(mCache cache){
        this.cache = cache;
        return this;
    }

    public RequestBuilder into(ImageView imageView){
        this.imageView = imageView;
        return this;
    }

    public void display(){
        display(null);
    }

    public void display(Object o){
        loader.setCache(cache);
        loader.setConfig(config);
        loader.load(source,imageView,o);
    }
}
