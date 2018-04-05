package Utils;

/**
 * Created by mac on 2018/4/3.
 */

public class TimeUtil {
    public static String FormatTime(String time){
        int n = time.indexOf("T");
        String Target = time.substring(0,n)+"\n";
        return Target;
    }
}
