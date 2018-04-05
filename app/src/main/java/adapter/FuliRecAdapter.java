package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mac.fuckthingapp.R;

import java.util.List;

import Utils.CacheUtil;
import mConfig.Config;
import Utils.HttpUtil;
import Utils.JsonUtil;
import Utils.TimeUtil;
import model.FuliBean;

/**
 * Created by mac on 2018/4/3.
 */

public class FuliRecAdapter extends RecyclerView.Adapter{
    private List<FuliBean> beanList;
    private Context context;
    private int FOOTER = 1;
    private int NORMAL = 2;
    private RecyclerView recyclerView;
    private boolean isLoadMore = false;

    public FuliRecAdapter(List<FuliBean> beanList, Context context,RecyclerView recyclerView) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fuli_item,parent,false);
            mViewHolder holder = new mViewHolder(view);
            return holder;
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_footer,parent,false);
            FooterHolder holder = new FooterHolder(view);
            return holder;
        }
    }

    public interface OnItemClickListener {
        void OnClickItem(View view, int position);
    }
    private OnItemClickListener MyonItemClickListener;
    public void onItemClickListner(OnItemClickListener onItemClickListener){
        MyonItemClickListener = onItemClickListener;
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,final int position) {
        if(holder instanceof mViewHolder){
            CacheUtil.getInstance(context).setImageCacheNotCompress(beanList.get(position).getUrl(),((mViewHolder) holder).imageView,true);
            ((mViewHolder) holder).textView.setText(TimeUtil.FormatTime(beanList.get(position).getPublishedAt())
                    + " 作者:"+beanList.get(position).getWho());
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
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isLoadMore) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
    private static int i = 2;
    private void loadmore(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                isLoadMore = true;
                String url = Config.getURL("福利",20,i);
                i++;
                HttpUtil.get(url, new HttpUtil.Callback() {
                    @Override
                    public void onResponse(String response) {
                        JsonUtil.AddData(response,beanList);
                        notifyDataSetChanged();
                        isLoadMore = false;
                    }
                });
            }
        }).start();
    }


    @Override
    public int getItemCount() {
        return beanList.size()+1;
    }

    class mViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public mViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.fuli_time);
            imageView = itemView.findViewById(R.id.fuli_img);
        }
    }
    class FooterHolder extends RecyclerView.ViewHolder{
        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

    public void refreshData(List<FuliBean> beanList1){
        beanList = beanList1;
    }
}
