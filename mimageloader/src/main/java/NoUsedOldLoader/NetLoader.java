package NoUsedOldLoader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import Utils.BitmapUtil;
import Utils.FileUtil;
import start.ImageLoader;

/**
 * Created by mac on 2018/4/8.
 */

public class NetLoader extends mLoader {
    @Override
    protected boolean checkSource(Object o) {
        if(!(o instanceof String)){
            Log.e("netLoader", "图片源不是String");
            return false;
        }
        return true;
    }

    @Override
    public void load(Object o, final View imageView, final Object tag) {
        if(!checkSource(o)||!checkEngine()) return;
        final String url = (String) o;//load start
        if(getConfig().hasPreloadPic()){
            ((ImageView) imageView).setImageResource(getConfig().getPreloadPic());
        }else{
            ((ImageView) imageView).setImageBitmap(BitmapUtil.getEmptyBitmap());
        }
        //从三级缓存加载图片
        getConfig().getThreadPoolExecutor().execute(new Runnable() {
            @Override
            public void run() {
                FileUtil.cleanDiskCache(ImageLoader.getInstance().getContext()
                                .getExternalCacheDir().getPath()
                        , getConfig().getDiskCacheSize());

                Bitmap bitmap = getCache().getImage(url);
                if(bitmap == null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((ImageView) imageView).setImageResource(getConfig().getErrorPic());
                        }
                    });
                    return;
                }
                if(imageView.getTag()==tag){
                    final Bitmap fixedBitmap = resize(bitmap,imageView);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((ImageView) imageView).setImageBitmap(fixedBitmap);
                        }
                    });
                }
            }
        });
    }

    private Bitmap resize(Bitmap bitmap, View imageView) {
        if (getConfig().getHeight() == 0 && getConfig().getWidth() == 0)
            return bitmap;
        if (getConfig().isAutoSizeByView()) {
            return BitmapUtil.autoResizeByWidth(bitmap, imageView.getWidth());
        } else if (getConfig().isAutoSizeByWidth()) {
            return BitmapUtil.autoResizeByWidth(bitmap, getConfig().getWidth());
        } else if (getConfig().isAutoSizeByHeight()) {
            return BitmapUtil.autoResizeByHeight(bitmap, getConfig().getHeight());
        } else {
            return BitmapUtil.resize(bitmap, getConfig().getWidth(), getConfig().getHeight());
        }
    }

    private void runOnUiThread(Runnable runnable){
        ((Activity)ImageLoader.getInstance().getContext()).runOnUiThread(runnable);
    }
}
