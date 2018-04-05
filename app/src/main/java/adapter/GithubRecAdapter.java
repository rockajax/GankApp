package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.fuckthingapp.R;

import java.util.List;

import mConfig.Config;
import Utils.HttpUtil;
import Utils.JsonUtil;
import Utils.TimeUtil;
import model.GithubBean;
import view.GifView;

import static android.content.ContentValues.TAG;

/**
 * Created by mac on 2018/4/3.
 */

public class GithubRecAdapter extends RecyclerView.Adapter{
    private List<GithubBean> beanList;
    private Context context;
    private int FOOTER = 1;
    private int NORMAL = 2;
    private RecyclerView recyclerView;
    private boolean isLoadMore = false;

    public GithubRecAdapter(List<GithubBean> beanList, Context context, RecyclerView recyclerView) {
        this.beanList = beanList;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==getItemCount()-1){
            return FOOTER;
        }else{
            return NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==NORMAL){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.android_item,parent,false);
            GithubRecAdapter.mViewHolder holder = new GithubRecAdapter.mViewHolder(view);
            return holder;
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_footer,parent,false);
            GithubRecAdapter.FooterHolder holder = new GithubRecAdapter.FooterHolder(view);
            return holder;
        }
    }

    public interface OnItemClickListener {
        void OnClickItem(View view, int position);
    }
    private GithubRecAdapter.OnItemClickListener MyonItemClickListener;
    public void onItemClickListner(GithubRecAdapter.OnItemClickListener onItemClickListener){
        MyonItemClickListener = onItemClickListener;
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,final int position) {
        if(holder instanceof GithubRecAdapter.mViewHolder){
            if(beanList.get(position).getImages()!=null){
                ((mViewHolder) holder).webView.setTag(beanList.get(position).getImages().get(0));
                ((mViewHolder) holder).loadGif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: "+beanList.get(position).getImages().get(0));
                        if(beanList.get(position).getImages().get(0).equals(((mViewHolder) holder).webView.getTag())){
                            WebSettings settings = ((mViewHolder) holder).webView.getSettings();
                            settings.setUseWideViewPort(true);
                            settings.setLoadWithOverviewMode(true);
                            ((mViewHolder) holder).webView.loadUrl(beanList.get(position).getImages().get(0));
                            Toast.makeText(context,"点开了记得关掉",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            ((mViewHolder) holder).closeGif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((mViewHolder) holder).webView.destroy();
                }
            });
            ((mViewHolder) holder).textView.setText(
                    beanList.get(position).getDesc());
            /**
             * 实现item点击绑定
             */
            View view = ((LinearLayout)holder.itemView).getChildAt(0);
            if(MyonItemClickListener != null){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = holder.getLayoutPosition();
                        MyonItemClickListener.OnClickItem(view,position);
                    }
                });
            }
        }
        if (getItemViewType(position) == FOOTER){
            loadmore();
        }

    }
    private static int i = 2;
    private void loadmore(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                isLoadMore = true;
                String url = Config.getURL("Android",20,i);
                HttpUtil.get(url, new HttpUtil.Callback() {
                    @Override
                    public void onResponse(String response) {
                        JsonUtil.AddData1(response,beanList);
                        notifyDataSetChanged();
                        isLoadMore = false;
                    }
                });
                i++;
            }
        }).start();
    }


    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+beanList.size());
        return beanList.size()+1;
    }

    class mViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        Button loadGif;
        Button closeGif;
        WebView webView;
            public mViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.android_time);
                loadGif = itemView.findViewById(R.id.show_gif);
                webView = itemView.findViewById(R.id.show_gif_webview);
                closeGif = itemView.findViewById(R.id.close_gif);
            }
    }

    class FooterHolder extends RecyclerView.ViewHolder{
        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

    public void refreshData(List<GithubBean> beanList1){
        beanList = beanList1;
    }
}
