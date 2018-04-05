package mConfig;

/**
 * Created by mac on 2018/4/2.
 */

public class Config {
    //数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
    //请求个数： 数字，大于0
    //第几页：数字，大于0
    public static String getURL(String type,int getItemNum,int page){
        return "http://gank.io/api/data/"+type+"/"+getItemNum+"/"+page;
    }

    public static String getRandomURL(String type,int getItemNum){
        return "http://gank.io/api/random/data/"+type+"/"+getItemNum;
    }

    public static String getStuPhotoUrl(String StuId){
        return "http://zzzia.net/ykt/"+StuId+".jpg";
    }
}
