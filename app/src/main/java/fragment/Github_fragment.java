package fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mac.fuckthingapp.R;

import java.util.ArrayList;
import java.util.List;

import adapter.GithubRecAdapter;
import mConfig.Config;
import Loader.HttpUtil;
import Utils.JsonUtil;
import model.GithubBean;

/**
 * Created by mac on 2018/4/3.
 */

@SuppressLint("ValidFragment")
public class Github_fragment extends Fragment {
    private Activity activity;
    private Context context;
    private RecyclerView recyclerView;
    private List<GithubBean> beanList;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GithubRecAdapter adapter;
    public Github_fragment(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.android_fragment,container,false);
        initData();
        initRefresh();
        return view;
    }
    private void initData(){
        beanList = new ArrayList<>();
        String url = Config.getURL("Android",20,1);
        HttpUtil.get(url, new HttpUtil.Callback() {
            @Override
            public void onResponse(String response) {
                JsonUtil.AddData1(response,beanList);
                recyclerView = view.findViewById(R.id.android_recyclerview);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                adapter = new GithubRecAdapter(beanList,context,recyclerView);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(linearLayoutManager);
                adapter.onItemClickListner(new GithubRecAdapter.OnItemClickListener() {
                    @Override
                    public void OnClickItem(View view, int position) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri uri = Uri.parse(beanList.get(position).getUrl());
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
            }
        });

    }
    private void initRefresh(){
        swipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout1);
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
                adapter.notifyDataSetChanged();
                String url = Config.getURL("Android",20,1);
                HttpUtil.get(url, new HttpUtil.Callback() {
                    @Override
                    public void onResponse(String response) {
                        JsonUtil.AddData1(response,beanList);
                        adapter.refreshData(beanList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
