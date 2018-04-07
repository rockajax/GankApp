package Loader;

/**
 * Created by mac on 2018/4/2.
 */

import android.accounts.NetworkErrorException;
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

    public byte[] getByteArrayFromWeb(String path){
        byte[] b = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.connect();
            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                baos = new ByteArrayOutputStream();
                is = connection.getInputStream();
                byte[] temp = new byte[1024];
                int length = 0;
                while((length = is.read(temp))!=-1){
                    baos.write(temp,0,length);
                }
            }
            b = baos != null ? baos.toByteArray() : new byte[0];
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return b;
    }
    public interface  Callback{
        void onResponse(String response);
    }
    public static void post(final String url, final String content, final Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = HttpUtil.post(url,content);
                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResponse(response);
                    }
                });
            }
        }).start();
    }
    public static String post(String url,String content){

        BufferedReader reader = null;
        HttpURLConnection conn = null;
        try{
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            //post不用拼接
            String data = content;
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(data);
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            if(responseCode==200) {
                InputStream inputStream = conn.getInputStream();
                String line;
                StringBuilder response = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                while((line=reader.readLine())!=null){
                    response.append(line);
                }
                return response.toString();
            }else{
                throw new NetworkErrorException("网络连接错误"+responseCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }finally {
            if(reader!=null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    public static void get(final String url, final Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = HttpUtil.get(url);
                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResponse(response);
                    }
                });
            }
        }).start();
    }

    public static String get(String url) {
        HttpURLConnection conn = null;
        try {
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                while((len=is.read(buffer))!=-1){
                    os.write(buffer,0,len);
                }
                is.close();
                String response = os.toString();
                os.close();
                return response;
            } else {
                Log.d(TAG, "response status is "+responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

}

