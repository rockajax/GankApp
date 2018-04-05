package fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mac.fuckthingapp.R;

import java.util.ArrayList;
import java.util.List;

import adapter.FuliRecAdapter;
import mConfig.Config;
import Utils.HttpUtil;
import Utils.JsonUtil;
import model.FuliBean;

/**
 * Created by mac on 2018/4/2.
 */

@SuppressLint("ValidFragment")
public class Fuli_fragment extends android.support.v4.app.Fragment {
    private Activity activity;
    private Context context;
    private RecyclerView recyclerView;
    private List<FuliBean> beanList;
    private View view;
    static boolean isLoadMore =false;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FuliRecAdapter adapter;
    CallBackValue callBackValue;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackValue = (CallBackValue) getActivity();
    }

    public Fuli_fragment(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fuli_fragment,container,false);
        initData();
        initRefresh();
        return view;
    }
    private void initData(){
        beanList = new ArrayList<>();
        String url = Config.getURL("福利",20,1);
        HttpUtil.get(url, new HttpUtil.Callback() {
            @Override
            public void onResponse(String response) {
                JsonUtil.AddData(response,beanList);
                recyclerView = view.findViewById(R.id.fuli_recyclerview);
                adapter = new FuliRecAdapter(beanList,context,recyclerView);
                adapter.onItemClickListner(new FuliRecAdapter.OnItemClickListener() {
                    @Override
                    public void OnClickItem(View view, int position) {
                        String url = beanList.get(position).getUrl();
                        callBackValue.SendValue(url);
                    }
                });
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(context,2));
            }
        });
    }
    private void initRefresh(){
        swipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                beanList.clear();
                if(recyclerView.getChildAt(0)!=null){
                    recyclerView.removeAllViews();
                }
                String url = Config.getURL("福利",20,2);
                HttpUtil.get(url, new HttpUtil.Callback() {
                    @Override
                    public void onResponse(String response) {
                        JsonUtil.AddData(response,beanList);
                        adapter.refreshData(beanList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    public interface CallBackValue{
        void SendValue(String Value);
    }

}
