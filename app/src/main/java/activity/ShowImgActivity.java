package activity;

import android.app.ActionBar;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mac.fuckthingapp.R;

import java.io.File;
import java.io.IOException;

import Utils.CacheUtil;
import Utils.FileUtil;
import Utils.MD5Util;
import Utils.ToastUtil;

import static android.content.ContentValues.TAG;

public class ShowImgActivity extends AppCompatActivity {
    private ImageView imageView;
    private String url;
    private Button save;
    private Button set;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_img);
        imageView = findViewById(R.id.show_image);
        save = findViewById(R.id.SaveImg_Btn);
        set = findViewById(R.id.setImg_Btn);
        getBundle();
        SaveImg();
        SetImg();
    }

    private void getBundle(){
        Bundle bundle = getIntent().getBundleExtra("bd");
        if(bundle!=null){
            url = bundle.getString("img");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SetImgView(url);
                }
            });
        }
    }

    private void SetImgView(final String url){
        if(url!=null){
            CacheUtil.getInstance(ShowImgActivity.this).setImageCacheNotCompress(url,imageView,true);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImgActivity.this.finish();
            }
        });
    }

    private void SaveImg(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = MD5Util.StringToMD5(url.substring(url.lastIndexOf(File.separator) + 1));
                byte[] data = FileUtil.getInstance(ShowImgActivity.this).readBytesFromStorage(filename);
                if(data!=null && data.length>0){
                    Log.d(TAG, "在showImgActivity中保存图片");
                    bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                    MediaStore.Images.Media.insertImage(ShowImgActivity.this.getContentResolver(),bitmap,filename,null);
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    File file = null;
                    if (filename != null) {
                        file = new File(ShowImgActivity.this.getFilesDir(),filename);
                    }
                    Uri uri = Uri.fromFile(file);
                    intent.setData(uri);
                    ShowImgActivity.this.sendBroadcast(intent);
                    ImageView imageView = new ImageView(getApplicationContext());
                    imageView.setBackgroundResource(R.mipmap.ic_launcher_round);

                    new ToastUtil(ShowImgActivity.this
                                    ,R.layout.toast_layout
                                    ,"保存成功")
                            .setColor(Color.WHITE,Color.BLACK)
                            .setGravity(Gravity.CENTER,0,0)
                            .show(3000);
                }
            }
        });
    }
    private void SetImg(){
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WallpaperManager manager = WallpaperManager.getInstance(ShowImgActivity.this);
                String filename = MD5Util.StringToMD5(url.substring(url.lastIndexOf(File.separator) + 1));
                byte[] data = FileUtil.getInstance(ShowImgActivity.this).readBytesFromStorage(filename);
                if(data!=null && data.length>0){
                    Log.d(TAG, "在showImgActivity中保存图片");
                    bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                    try {
                        manager.setBitmap(bitmap);
                        new ToastUtil(ShowImgActivity.this
                                ,R.layout.toast_layout
                                ,"设置成功")
                                .setColor(Color.WHITE,Color.BLACK)
                                .setGravity(Gravity.CENTER,0,0)
                                .show(3000);
                    } catch (IOException e) {
                        e.printStackTrace();
                        new ToastUtil(ShowImgActivity.this
                                ,R.layout.toast_layout
                                ,"设置失败")
                                .setColor(Color.WHITE,Color.BLACK)
//                                .setGravity(Gravity.CENTER,0,0)
                                .show(3000);
                    }

                }
            }
        });
    }
}
