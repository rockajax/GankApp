package start;

import android.content.Context;

/**
 * Created by mac on 2018/4/8.
 * 图片加载发起者
 */

public class ImageLoader {

    public static final String TAG = ImageLoader.class.getSimpleName();
    private volatile static ImageLoader instance;
    private RequestManager requestManager;
    private Context context;

    public static ImageLoader getInstance(){
        if(instance == null){
            synchronized (ImageLoader.class){
                if(instance == null){
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }
    private ImageLoader(){
        requestManager = new RequestManager();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static RequestManager with(Context context){
        getInstance().setContext(context);
        return getInstance().requestManager;
    }

    public static void clearCache(){
        getInstance().context = null;
        getInstance().requestManager = null;
    }
}
