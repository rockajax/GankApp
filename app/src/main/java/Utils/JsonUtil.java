package Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import model.FuliBean;
import model.GithubBean;

/**
 * Created by mac on 2018/4/3.
 */

public class JsonUtil {
    public static String JsonToString(String response, String json){
        try {
            if(response!=null){
                JSONObject jsonObject = new JSONObject(response);
                String targetInfo = jsonObject.getString(json);
                return targetInfo;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void AddData(String response,List<FuliBean> beanList){
        try {
            String results = JsonUtil.JsonToString(response,"results");
            JSONArray jsonArray = new JSONArray(results);
            for(int i = 0 ; i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FuliBean fuliBean =
                        new FuliBean.Builder()
                                .url(jsonObject.getString("url"))
                                .who(jsonObject.getString("who"))
                                .publishedAt(jsonObject.getString("publishedAt"))
                                .desc(jsonObject.getString("desc"))
                                .build();
                beanList.add(fuliBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void AddData1(String response,List<GithubBean> beanList){
        try {
            if(response!=null&&beanList!=null){
                String results = JsonUtil.JsonToString(response,"results");
                JSONArray jsonArray = new JSONArray(results);
                List<String> imgList = new ArrayList<>();
                for(int i = 0 ; i < jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String images = jsonObject.getString("images");
                    JSONArray jsonArray1 = new JSONArray(images);
                    for(int j = 0 ;j<jsonArray1.length();j++){
                        imgList.add(jsonArray1.getString(j));
                    }
                    GithubBean githubBean =
                            new GithubBean.Builder()
                                    .url(jsonObject.getString("url"))
                                    .who(jsonObject.getString("who"))
                                    .desc(jsonObject.getString("desc"))
                                    .images(imgList)
                                    .publishedAt(jsonObject.getString("publishedAt"))
                                    .build();
                    beanList.add(githubBean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
