package activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mac.fuckthingapp.R;

import java.util.ArrayList;

import Utils.ToastUtil;
import adapter.FuliFragmentPagerAdapter;
import fragment.Github_fragment;
import fragment.Fuli_fragment;
import fragment.Stu_fragment;

public class MainActivity extends AppCompatActivity
        implements Fuli_fragment.CallBackValue, Stu_fragment.CallBackStuValue{
    private ActionBarDrawerToggle toggle;//DrawerLaytou与toolbar的联动开关
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private android.support.v7.widget.Toolbar toolbar;

    private TabLayout mTablayout;
    private ViewPager mViewpager;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private ArrayList<Fragment> fragmentlist;
    private ArrayList<String> titlelist;

    private Stu_fragment fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDrawerLayout();
        initFragment();
    }



    private void initFragment(){
        mTablayout = findViewById(R.id.tab_title);
        mViewpager = findViewById(R.id.viewpager);

        fragmentlist = new ArrayList<>();
        titlelist = new ArrayList<>();

        Fuli_fragment fragement = new Fuli_fragment(MainActivity.this,this);
        fragmentlist.add(fragement);
        titlelist.add("妹子福利");

        Github_fragment fragment1 = new Github_fragment(MainActivity.this,this);
        fragmentlist.add(fragment1);
        titlelist.add("Andoird");

        fragment2 = new Stu_fragment(MainActivity.this,this);
        fragmentlist.add(fragment2);
        titlelist.add("学生照片查询");

        fragmentPagerAdapter = new FuliFragmentPagerAdapter(getSupportFragmentManager(),fragmentlist,titlelist);
        mViewpager.setAdapter(fragmentPagerAdapter);
        mTablayout.setupWithViewPager(mViewpager);
    }


    private void initDrawerLayout(){
        init();
        hideScrollBar();
        setActionBar();
        setDrawerToggle();
        setListener();
    }
    private void init() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
    }
    private void hideScrollBar() {
        //去除navigation中的滑动条
        navigationView.getChildAt(0).setVerticalScrollBarEnabled(false);
    }

    private void setActionBar(){
        setSupportActionBar(toolbar);//设置actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示home图标
    }
    //设置DrawerLayout开关 并且和Home图标联动
    private void setDrawerToggle(){
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,0,0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();//同步drawerlayout状态
    }

    private void setListener(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_1:
                        Toast.makeText(MainActivity.this,"还没做呢",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_2:
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri uri = Uri.parse("http://gank.io/");
                        intent.setData(uri);
                        startActivity(intent);
                        break;
                    case R.id.item_3:
                        Toast.makeText(MainActivity.this,"点击图片里面的设置按钮噢！",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_4:
                        Toast.makeText(MainActivity.this,"安全起见。。只能用学号查！",Toast.LENGTH_SHORT).show();
//                        FragmentManager fmanger = getSupportFragmentManager();
//                        FragmentTransaction transaction = fmanger.beginTransaction();
//                        transaction.replace(R.id.viewpager, fragment2);
//                        transaction.commit();
//                        mViewpager.setCurrentItem(2);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void SendValue(String Value) {
        Log.d("fxy", "SendValue: " + Value);
        Intent intent = new Intent();
        intent.setClass(this, ShowImgActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("img", Value);
        intent.putExtra("bd", bundle);
        startActivity(intent);
    }
}
