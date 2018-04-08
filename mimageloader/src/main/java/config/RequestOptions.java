package config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by mac on 2018/4/8.
 */

public class RequestOptions implements mConfig {

    private int preloadPic;//占位图，优先加载
    private int errorPic;//错误图片
    private static final int NO_PIC = -1;
    private long diskCacheSize;//磁盘缓存的最大容量
    private Executor threadPoolExecutor;//线程池
    private boolean isAutoSizeByView;//自动宽高
    private boolean isAutoSizeByHeight;
    private boolean isAutoSizeByWidth;
    private int width, height;//宽高

    public RequestOptions(){
        preloadPic = NO_PIC;
        errorPic = NO_PIC;
        diskCacheSize = 1024 * 1024 * 300;
        threadPoolExecutor = Executors.newCachedThreadPool();
    }

    /**
     * 自定义宽高
     * @param imgWidth
     * @param imgHeight
     * @return
     */
    public RequestOptions reSize(int imgWidth,int imgHeight){
        this.width = imgWidth;
        this.height = imgHeight;
        isAutoSizeByView = false;
        isAutoSizeByHeight = false;
        isAutoSizeByWidth = false;
        return this;
    }

    @Override
    public Executor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }
    public RequestOptions setTreadPoolExecutor(Executor treadPoolExecutor) {
        this.threadPoolExecutor = treadPoolExecutor;
        return this;
    }


    @Override
    public long getDiskCacheSize() {
        return diskCacheSize;
    }
    public RequestOptions setDiskCacheSize(long diskCacheSize) {
        this.diskCacheSize = diskCacheSize;
        return this;
    }

    @Override
    public boolean hasPreloadPic() {
        return preloadPic != NO_PIC;
    }
    public RequestOptions setPreloadPic(int resource) {
        this.preloadPic = resource;
        return this;
    }


    @Override
    public int getWidth() {
        return width;
    }
    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isAutoSizeByView() {
        return isAutoSizeByView;
    }
    public RequestOptions setAutoSizeByView(boolean autoSize) {
        isAutoSizeByView = autoSize;
        return this;
    }



    @Override
    public boolean isAutoSizeByHeight() {
        return isAutoSizeByHeight;
    }
    public RequestOptions setAutoSizeByHeight(int height) {
        isAutoSizeByView = false;
        isAutoSizeByHeight = true;
        this.height = height;
        return this;
    }



    @Override
    public boolean isAutoSizeByWidth() {
        return isAutoSizeByWidth;
    }
    public RequestOptions setAutoSizeByWidth(int width) {
        isAutoSizeByView = false;
        isAutoSizeByWidth = true;
        this.width = width;
        return this;
    }



    @Override
    public int getErrorPic() {
        return errorPic;
    }
    public RequestOptions setErrorPic(int resource) {
        this.errorPic = resource;
        return this;
    }


    @Override
    public int getPreloadPic() {
        return preloadPic;
    }
}
