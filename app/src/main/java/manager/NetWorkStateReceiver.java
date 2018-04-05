//package manager;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.net.ConnectivityManager;
//import android.net.Network;
//import android.net.NetworkInfo;
//import android.os.Build;
//import android.widget.Toast;
//
//import com.example.mac.fuckthingapp.R;
//
//import Utils.ToastUtil;
//import activity.ShowImgActivity;
//
///**
// * Created by mac on 2018/4/5.
// */
//
//public class NetWorkStateReceiver extends BroadcastReceiver {
//    private Context context ;
//    private ToastUtil toastUtil = new ToastUtil(context, R.layout.toast_layout,"123",1,1);
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
//        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
//
//            //获得ConnectivityManager对象
//            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//            //获取ConnectivityManager对象对应的NetworkInfo对象
//            //获取WIFI连接的信息
//            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//            //获取移动数据连接的信息
//            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
////            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
////                toastUtil
////                        .setTextAndDuration(context,"WIFI已连接,移动数据已连接",Toast.LENGTH_SHORT)
////                        .setToastColor(Color.WHITE,Color.parseColor("#FF8535"))
////                        .show();
////            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
////                toastUtil
////                        .setTextAndDuration(context,"WIFI已连接,移动数据已断开",Toast.LENGTH_SHORT)
////                        .setToastColor(Color.WHITE,Color.parseColor("#FF8535"))
////                        .show();
////            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
////                toastUtil
////                        .setTextAndDuration(context,"WIFI已断开,移动数据已连接",Toast.LENGTH_SHORT)
////                        .setToastColor(Color.WHITE,Color.parseColor("#FF8535"))
////                        .show();
////            } else {
////                toastUtil
////                        .setTextAndDuration(context,"WIFI已断开,移动数据已断开",Toast.LENGTH_SHORT)
////                        .setToastColor(Color.WHITE,Color.parseColor("#FF8535"))
////                        .show();
////            }
//        //API大于23时使用下面的方式进行网络监听
//        }else {
//            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            Network[] networks = connMgr.getAllNetworks();
//            StringBuilder sb = new StringBuilder();
//            for (int i=0; i < networks.length; i++){
//                //获取ConnectivityManager对象对应的NetworkInfo对象
//                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
//                sb.append(networkInfo.getTypeName())
//                  .append(" connect is ")
//                  .append(networkInfo.isConnected());
//            }
////            toastUtil
////                    .setTextAndDuration(context,sb.toString(),2000)
////                    .setToastColor(Color.WHITE,Color.parseColor("#FF8535"))
////                    .show();
//        }
//    }
//}
