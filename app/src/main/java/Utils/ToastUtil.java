package Utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.fuckthingapp.R;

import activity.ShowImgActivity;

/**
 * Created by mac on 2018/4/5.
 */

public class ToastUtil {
    private Toast mToast;
    private TextView textView;
    private TimeCount timeCount;
    private String message;
    private Handler handler = new Handler();
    private boolean canceled = true;
    private View view;
    private WindowManager.LayoutParams  mParams;
    private WindowManager windowManager;


    public ToastUtil(Context context,int layoutId,String msg){
        this.message = msg;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            view = inflater.inflate(layoutId,null);
        }
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        view.setBackgroundResource(R.drawable.toast_round);
        textView = view.findViewById(R.id.toast_tv);
        textView.setText(msg);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToast.cancel();
            }
        });

        if(mToast==null){
            mToast = new Toast(context);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(view);
    }
    public ToastUtil setColor(int BackgroudColor,int TextColor){
        textView.setTextColor(TextColor);
//        view.setBackgroundColor(BackgroudColor);
        return this;
    }
    public ToastUtil setGravity(int gravity,int offsetX,int offsetY){
        mToast.setGravity(gravity,offsetX,offsetY);
        return this;
    }


    public ToastUtil show(){
        windowManager.addView(view,mParams);
        mToast.show();
        return this;
    }


    /**
     * 自定义时长
     * @param duration
     */
    public ToastUtil show(int duration){
        timeCount = new TimeCount(duration,1000);
        if(canceled){
            timeCount.start();
            canceled = false;
            showUntilCancel();
        }
        return this;
    }


    private void hide(){
        if(mToast!=null){
            mToast.cancel();
        }
        canceled = true;
    }
    private void showUntilCancel(){
        if(canceled){
            return;
        }
        mToast.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showUntilCancel();
            }
        },Toast.LENGTH_SHORT);
    }

    public Toast getmToast() {
        return mToast;
    }

    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval); //millisInFuture总计时长，countDownInterval时间间隔(一般为1000ms)
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textView.setText(message + ": " + millisUntilFinished / 1000 + "s后消失");
        }

        @Override
        public void onFinish() {
            hide();
        }
    }
}
