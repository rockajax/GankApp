package fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mac.fuckthingapp.R;

import Loader.ImageLoader;
import mConfig.Config;

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


            /**
             *用了个老版的图片加载
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()==10){
                         ImageLoader.setContext(context)
                                    .load(Config.getStuPhotoUrl(s.toString()))
                                    .into(imageView)
                                    .setNullBitmap(true)
                                    .begin();
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
