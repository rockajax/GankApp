package Utils; /**
 * Created by mac on 2018/4/2.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件操作工具类
 */

public class FileUtil {
    /**
     * bitmap写入磁盘
     */
    public static void writeBitmapToDisk(String filename, String path, Bitmap bitmap){
        File bitmapDir = new File(path);
        if(!bitmapDir.exists()){
            bitmapDir.mkdir();
        }
        FileOutputStream fos = null;
        try {
            File file = new File(path,filename);
            if(file.exists())
                return;
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从内存中读取磁盘的bitmap
     */
    public static Bitmap getBitmapFromDisk(String filename,String path){
        Bitmap bitmap = null;
        File file = new File(path);//文件夹

        File[] files = file.listFiles();
        for (File file1: files) {
            if(file1.getName().equals(filename)){
                bitmap = BitmapFactory.decodeFile(file1.getPath());
                break;
            }
        }
        return bitmap;
    }

    public static void cleanDiskCache(String path,long diskCacheSize){
        File file = new File(path);
        File[] files = file.listFiles();
        long sum = 0;
        for (File file1: files) {
            sum+=file1.length();
        }
        if(sum > diskCacheSize){
            for (File f1: files) {
                f1.delete();
            }
        }
    }
}

