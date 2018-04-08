package Cache.bitmap;

import android.graphics.Bitmap;

import Cache.mCache;

/**
 * Created by mac on 2018/4/8.
 */

public class DefaultBitmapCache implements mCache {
    @Override
    public void putImage(String url, Bitmap bitmap) {

    }

    @Override
    public Bitmap getImage(String url) {
        return null;
    }
}
