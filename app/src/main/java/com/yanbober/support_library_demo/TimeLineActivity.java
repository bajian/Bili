package com.yanbober.support_library_demo;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.yanbober.support_library_demo.adapter.ExpandableAdapter;
import com.yanbober.support_library_demo.bean.TimeLineBean;
import com.yanbober.support_library_demo.persistence.URLConstant;
import com.yanbober.support_library_demo.utils.GsonUtil;
import com.yanbober.support_library_demo.utils.OkHttpClientManager;
import com.yanbober.support_library_demo.view.SystemBarTintManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 新番放送
 */
public class TimeLineActivity extends AppCompatActivity {


    @Bind(R.id.left_tv)
    TextView mLeftTv;
    @Bind(R.id.goBack)
    ImageView mGoBack;
    @Bind(R.id.menuButton)
    ImageView mMenuButton;
    @Bind(R.id.mainTitile)
    TextView mMainTitile;
    @Bind(R.id.right_btn)
    ImageView mRightBtn;
    @Bind(R.id.right_tv)
    TextView mRightTv;
    @Bind(R.id.windowTitle)
    RelativeLayout mWindowTitle;
    @Bind(R.id.expandableListView)
    ExpandableListView mExpandableListView;
    public TimeLineBean mTimeLineBean;
    private static String TAG="TimeLineActivity";
    private ExpandableAdapter mExpandableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);
        //初始化控件及布局
        initView();
        Log.d(TAG, "setData" );
        setData();
    }

    private void setData() {
        //TODO GET local strage
        OkHttpClientManager.getAsyn(URLConstant.URL_Time_Line, new OkHttpClientManager.ResultCallback<TimeLineBean>() {

            @Override
            public void onError(Request request, Exception e) {
                Log.d(TAG,"onError"+e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(TimeLineBean response) {
                mTimeLineBean = response;
                //TODO SET local strage
                Log.d(TAG,mTimeLineBean.toString());
                mExpandableAdapter=new ExpandableAdapter(TimeLineActivity.this,mTimeLineBean.getList());
                mExpandableListView.setAdapter(mExpandableAdapter);
                mExpandableAdapter.notifyDataSetChanged();
                int groupCount=mExpandableAdapter.getGroupCount();
                for (int i=0;i<groupCount;i++) {
                    mExpandableListView.expandGroup(i);
                }
            }
        });
    }

    private void jsonToListView(String json) {
        mTimeLineBean = GsonUtil.parseJson(json, TimeLineBean.class);

    }

    private void bindAdapter(){
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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

/*

    @OnClick({R.id.LL_drawer_home, R.id.iv_download_center, R.id.iv_search_center, R.id.ic_game_center})
    void click(View view) {
        switch (view.getId()) {
            case R.id.LL_drawer_home:

                break;
            case R.id.iv_download_center:
                break;
            case R.id.iv_search_center:
                break;
            case R.id.ic_game_center:
                break;
        }
    }
*/


}
