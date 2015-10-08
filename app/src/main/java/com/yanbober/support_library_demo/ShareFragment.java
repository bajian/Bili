package com.yanbober.support_library_demo;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.bajian.viewflow.CircleFlowIndicator;
import com.example.bajian.viewflow.ViewFlow;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yanbober.support_library_demo.adapter.ViewFlowAdapter;
import com.yanbober.support_library_demo.bean.TuijianViewPagerBean;
import com.yanbober.support_library_demo.persistence.Constant;
import com.yanbober.support_library_demo.persistence.URLConstant;
import com.yanbober.support_library_demo.utils.GsonUtil;
import com.yanbober.support_library_demo.utils.HttpUtil;
import com.yanbober.support_library_demo.utils.SharePreferenceUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 推荐
 */
public class ShareFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "ShareFragment";
    /*    @InjectView(R.id.abv)
        AutoBannerViewPager mAbv;*/
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout mSwipeContainer;
    @Bind(R.id.viewflow)
    ViewFlow mViewflow;
    @Bind(R.id.viewflowindic)
    CircleFlowIndicator mViewflowindic;
    @Bind(R.id.framelayout)
    FrameLayout mFramelayout;
    @Bind(R.id.animationIV)
    ImageView mAnimationIV;

    private View mParentView;
    private List<View> views;
    private TuijianViewPagerBean mTuijianViewPagerBean;
    private List<TuijianViewPagerBean.ListEntity> tuijianList;
    private ViewFlowAdapter mViewFlowAdapter;
    private AnimationDrawable animationDrawable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.share_fragment, container, false);
        ButterKnife.bind(this, mParentView);
        return mParentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iniView();
        getViewFromLocal();
        if (tuijianList == null)
            getViewPagerData();

        startAnime();
    }

    private void iniView() {
        mSwipeContainer.setOnRefreshListener(this);
    }

/*    private void initViewPager() {
        views=new ArrayList<>();
        for (int i=0;i<tuijianList.size();i++){
            Log.d(TAG, "getImageurl" +tuijianList.get(i).getImageurl());
            ImageView view = new ImageView(getActivity());
            ImageLoader.getInstance().displayImage(tuijianList.get(i).getImageurl(),view);
            views.add(view);
        }
        mViewPagerAdapter=new ViewPagerAdapter(views);
        mAbv.setAdapter(mViewPagerAdapter);
    }*/


    private void getViewPagerData() {
        HttpUtil.getInstance().send(HttpRequest.HttpMethod.GET, URLConstant.URL_TUIJIAN_VIEWPAGER, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.d(TAG, "onSuccess" + responseInfo.result);
                jsonToViewFolw(responseInfo.result);
                SharePreferenceUtil.setValue(getActivity(), Constant.SP_BILI_VIEWFLOW, responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.d(TAG, "onFailure" + s);
                mSwipeContainer.setRefreshing(false);

            }
        });
    }

    private void startAnime(){
        mAnimationIV.setImageResource(R.drawable.frame_anim_loading);
        animationDrawable = (AnimationDrawable) mAnimationIV.getDrawable();
        if(!animationDrawable.isRunning()){
            animationDrawable.start();
        }
    }

    private void stopAnime(){
        if (animationDrawable!=null)
        if(animationDrawable.isRunning())
            animationDrawable.stop();
    }

    /**
     * 首次打开从本地拿历史数据
     */
    public void getViewFromLocal() {
        jsonToViewFolw(SharePreferenceUtil.getString(getActivity(), Constant.SP_BILI_VIEWFLOW));
    }

    private void jsonToViewFolw(String json) {
        if ("".equals(json)) return;
        mTuijianViewPagerBean = GsonUtil.parseJson(json, TuijianViewPagerBean.class);
        tuijianList = mTuijianViewPagerBean.getList();
        mViewFlowAdapter = new ViewFlowAdapter(getActivity(), tuijianList);
        mViewflow.setAdapter(mViewFlowAdapter);
        mViewflow.setmSideBuffer(tuijianList.size()); // 实际图片张数
        mViewflow.setFlowIndicator(mViewflowindic);
        mViewflow.setTimeSpan(4500);
        mViewflow.setSelection(tuijianList.size() * 1000);    //设置初始位置
        mViewflow.startAutoFlowTimer();  //启动自动播放
        mSwipeContainer.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh");
        getViewPagerData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
