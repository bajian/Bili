package com.yanbober.support_library_demo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.johnpersano.supertoasts.SuperToast;
import com.yanbober.support_library_demo.adapter.FragmentAdapter;
import com.yanbober.support_library_demo.utils.ToastUtil;
import com.yanbober.support_library_demo.view.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.iv_user_header)
    ImageView mIvUserHeader;
    @Bind(R.id.tv_userName)
    TextView mTvUserName;
    @Bind(R.id.LL_drawer_home)
    LinearLayout mLLDrawerHome;
    @Bind(R.id.ic_game_center)
    ImageView mIcGameCenter;
    @Bind(R.id.iv_download_center)
    ImageView mIvDownloadCenter;
    @Bind(R.id.iv_search_center)
    ImageView mIvSearchCenter;
    @Bind(R.id.LL_drawer_right)
    LinearLayout mLLDrawerRight;
    //将ToolBar与TabLayout结合放入AppBarLayout
    private Toolbar mToolbar;
    //DrawerLayout中的左侧菜单控件
    private NavigationView mNavigationView;
    //DrawerLayout控件
    private DrawerLayout mDrawerLayout;
    //Tab菜单，主界面上面的tab切换菜单
    private TabLayout mTabLayout;
    //v4中的ViewPager控件
    private ViewPager mViewPager;
    private SuperToast toast;
    private CircleImageView mIvDrawerheader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //初始化控件及布局
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    private void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
        }
        //MainActivity的布局文件中的主要控件初始化
        mToolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) this.findViewById(R.id.navigation_view);
        mTabLayout = (TabLayout) this.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) this.findViewById(R.id.view_pager);
        //初始化ToolBar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

//        actionBar.setDisplayHomeAsUpEnabled(true)    // 给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP
//        actionBar.setDisplayShowHomeEnabled(true)   //使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题，否则，显示应用程序图标，对应id为android.R.id.home，对应ActionBar.DISPLAY_SHOW_HOME
//        actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        //对NavigationView添加item的监听事件
        mNavigationView.setNavigationItemSelectedListener(naviListener);
        View headerView = getLayoutInflater().inflate(R.layout.drawer_header, null);
        mIvDrawerheader= (CircleImageView) headerView.findViewById(R.id.iv_drawerheader);
        mIvDrawerheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LogInActivity.class));
            }
        });
        mNavigationView.addHeaderView(headerView);

        //开启应用默认打开DrawerLayout
//        mDrawerLayout.openDrawer(mNavigationView);

        //初始化TabLayout的title数据集
        String[] titles =getResources().getStringArray(R.array.main_fragment_name);
        //初始化TabLayout的title
        for (int i = 0; i < titles.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles[i]));
        }
        //初始化ViewPager的数据集
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new BungumiFragment());
        fragments.add(new RecommendFragment());
        fragments.add(new AgendaFragment());
        fragments.add(new AgendaFragment());
        fragments.add(new AgendaFragment());
        //创建ViewPager的adapter
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        //千万别忘了，关联TabLayout与ViewPager
        //同时也要覆写PagerAdapter的getPageTitle方法，否则Tab没有title
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);//给Tabs设置适配器

        toast = ToastUtil.getSuperToast(this, getString(R.string.one_more_exit));
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        quitToast();
    }

    private void quitToast() {
        if (null == toast.getView().getParent()) {
            toast.show();
        } else {
            System.exit(0);
        }
    }

    @OnClick({R.id.LL_drawer_home,R.id.iv_download_center,R.id.iv_search_center,R.id.ic_game_center})
    void click(View view) {
        switch (view.getId()) {
            case R.id.LL_drawer_home:
                if (mDrawerLayout.isDrawerOpen(mNavigationView)){
                    mDrawerLayout.closeDrawer(mDrawerLayout);
                }else{
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.iv_download_center:
                break;
            case R.id.iv_search_center:
                break;
            case R.id.ic_game_center:
                break;
        }
    }


    private NavigationView.OnNavigationItemSelectedListener naviListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            //点击NavigationView中定义的menu item时触发反应
            switch (menuItem.getItemId()) {
                case R.id.menu_info_details:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.menu_share:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.menu_agenda:
                    mViewPager.setCurrentItem(2);
                    break;
            }
            menuItem.setChecked(true);
            //关闭DrawerLayout回到主界面选中的tab的fragment页
            mDrawerLayout.closeDrawer(mNavigationView);
            return false;
        }
    };

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //主界面右上角的menu菜单
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_info_details:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.menu_share:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.menu_agenda:
                mViewPager.setCurrentItem(2);
                break;
            case android.R.id.home:
                //主界面左上角的icon点击反应
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
