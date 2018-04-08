package start;

import android.graphics.Bitmap;
import android.net.Uri;

import NoUsedOldLoader.BitmapLoader;
import NoUsedOldLoader.NetLoader;

/**
 * Created by mac on 2018/4/8.
 * 处理请求的类
 */

public class RequestManager {

    //请求处理者
    private RequestBuilder requestBuilder;

    public RequestManager() {
        requestBuilder = new RequestBuilder();
    }

    public RequestBuilder load(String url){
        return requestBuilder.init(url,new NetLoader());
    }

    public RequestBuilder load(Uri uri){
        return requestBuilder.init(uri,new NetLoader());
    }

    public RequestBuilder load(Bitmap bitmap){
        return requestBuilder.init(bitmap,new BitmapLoader());
    }

    protected void clearCache(){

    }
}
