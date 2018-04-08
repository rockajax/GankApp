package Cache;

import android.graphics.Bitmap;

/**
 * Created by mac on 2018/4/8.
 */

public interface mCache {
    void putImage(String url,Bitmap bitmap);
    Bitmap getImage(String url);
}
