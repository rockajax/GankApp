package fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mac.fuckthingapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Utils.CacheUtil;
import Utils.FileUtil;
import Utils.MD5Util;
import Utils.ToastUtil;
import activity.ShowImgActivity;
import adapter.FuliRecAdapter;
import manager.LinearLayoutManagerWrapper;
import mConfig.Config;
import Utils.HttpUtil;
import Utils.JsonUtil;
import model.FuliBean;

import static android.content.ContentValues.TAG;

/**
 * Created by mac on 2018/4/3.
 */

@SuppressLint("ValidFragment")
public class Stu_fragment extends Fragment {
    private Activity activity;
    private Context context;
    private Button save;
    private EditText editText;
    private ImageView imageView;
    private View view;
    private String url;
    CallBackStuValue callBackStuValue;
    public Stu_fragment(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackStuValue = (CallBackStuValue) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.stu_fragment,container,false);
        init();
        return view;
    }

    private void init(){
        editText = view.findViewById(R.id.stu_edit);
        imageView = view.findViewById(R.id.stu_img);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()==10){
                        CacheUtil.getInstance(context)
                                .setImageCacheNotCompress(Config.getStuPhotoUrl(s.toString()),imageView,true);
                            url = s.toString();
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackStuValue.SendValue(Config.getStuPhotoUrl(url));
            }
        });
    }
    public interface CallBackStuValue{
        void SendValue(String Value);
    }
}
