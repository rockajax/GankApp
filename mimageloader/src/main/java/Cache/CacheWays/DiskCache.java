package Cache.CacheWays;

import android.graphics.Bitmap;

import Cache.mCache;
import Utils.FileUtil;
import Utils.MD5Util;

/**
 * Created by mac on 2018/4/8.
 */

public class DiskCache implements mCache {
    private String filePath;

    public DiskCache(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void putImage(String url, Bitmap bitmap) {
        FileUtil.writeBitmapToDisk(MD5Util.StringToMD5(url),filePath,bitmap);
    }

    @Override
    public Bitmap getImage(String url) {
        return FileUtil.getBitmapFromDisk(MD5Util.StringToMD5(url),filePath);
    }
}
