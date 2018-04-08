package Utils; /**
 * Created by mac on 2018/4/2.
 */

import android.accounts.NetworkErrorException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class HttpUtil {
    private static HttpUtil instance;
    private HttpUtil(){}

    /**
     * 单例模式 避免重复定义httpUtil
     * @return
     */
    public static HttpUtil getInstance(){
        if(instance==null){
            synchronized (HttpUtil.class){
                instance = new HttpUtil();
            }
        }
        return instance;
    }

    public Bitmap getBitmapFromWeb(String path){
        if(Looper.myLooper() == Looper.getMainLooper()){
            throw new RuntimeException("不能在主线程进行网络操作");
        }

        InputStream is = null;
        Bitmap bitmap = null;
        HttpURLConnection connection=null;
        try {
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            if(bitmap!=null){
                return bitmap;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
                if(connection!=null){
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}

