package Cache.CacheWays;

import android.graphics.Bitmap;

import Cache.mCache;
import Utils.HttpUtil;

/**
 * Created by mac on 2018/4/8.
 */

public class NetCache implements mCache {
    @Override
    public void putImage(String url, Bitmap bitmap) {

    }

    @Override
    public Bitmap getImage(String url) {
        return HttpUtil.getInstance().getBitmapFromWeb(url);
    }
}
