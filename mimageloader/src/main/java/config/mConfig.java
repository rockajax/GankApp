package config;

import java.util.concurrent.Executor;

/**
 * Created by mac on 2018/4/8.
 */

public interface mConfig {
    Executor getThreadPoolExecutor();
    long getDiskCacheSize();
    boolean hasPreloadPic();
    int getWidth();
    int getHeight();
    boolean isAutoSizeByView();
    boolean isAutoSizeByHeight();
    boolean isAutoSizeByWidth();
    int getErrorPic();
    int getPreloadPic();
}
