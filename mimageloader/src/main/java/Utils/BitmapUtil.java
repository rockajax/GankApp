package Utils;

import android.graphics.Bitmap;

/**
 * Created by mac on 2018/4/8.
 */

public class BitmapUtil {

    public static Bitmap getEmptyBitmap(){
        return Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_4444);
    }
    //通过指定宽高设置bitmap
    public static Bitmap resize(Bitmap bitmap,int width,int height){
        return Bitmap.createScaledBitmap(bitmap,width,height,true);
    }
    //通过宽度自动设置bitmap
    public static Bitmap autoResizeByWidth(Bitmap bitmap,int width){
        float w = bitmap.getWidth();
        float h = bitmap.getHeight();
        int height = (int) (h / w * width);
        return resize(bitmap,width,height);
    }
    //通过高度自动设置bitmap
    public static Bitmap autoResizeByHeight(Bitmap bitmap,int height){
        float w = bitmap.getWidth();
        float h = bitmap.getHeight();
        int width = (int) (w / h * height);
        return resize(bitmap,width,height);
    }

}
