package Loader;

/**
 * Created by mac on 2018/4/2.
 */

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件操作工具类
 */

public class FileUtil {
    private static FileUtil instance;
    private Context context;//传入一个该activity的context

    public FileUtil(Context context) {
        this.context = context;
    }

    public static FileUtil getInstance(Context context){
        if(instance==null){
            synchronized (FileUtil.class){
                instance = new FileUtil(context);
            }
        }
        return instance;
    }

    /**
     * 将文件用字节写入内存
     */
    public void writeFileToStorage(String filename,byte[] b){
        FileOutputStream fos = null;
        try {
            File file = new File(context.getFilesDir(),filename);
            fos = new FileOutputStream(file);
            fos.write(b,0,b.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
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
     * 从内存中读取文件的字节
     */
    public byte[] readBytesFromStorage(String filename){
        byte[] b = null;
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = context.openFileInput(filename);
            baos = new ByteArrayOutputStream();
            byte[] temp = new byte[1024];
            int len = 0;
            while((len = fis.read(temp))!=-1){
                baos.write(temp,0,len);
            }
            b = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return b;
    }
}

