package com.yanbober.support_library_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yanbober.support_library_demo.adapter.MultiRecyclerViewAdapter;
import com.yanbober.support_library_demo.bean.Bungumi;
import com.yanbober.support_library_demo.persistence.Constant;
import com.yanbober.support_library_demo.persistence.URLConstant;
import com.yanbober.support_library_demo.utils.GsonUtil;
import com.yanbober.support_library_demo.utils.HttpUtil;
import com.yanbober.support_library_demo.utils.RandomUtil;
import com.yanbober.support_library_demo.utils.SharePreferenceUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 番剧
 */
public class BungumiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "BungumiFragment";
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout mSwipeContainer;
    private RecyclerView mRecyclerView;
    private List<Bungumi.ListEntity> list;
    /**
     * 可以在这里保存状态
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

//    private RecyclerViewAdapter mRecyclerViewAdapter;
    private MultiRecyclerViewAdapter mRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_details_fragment, container, false);
        ButterKnife.bind(this, view);
//        View view_header = inflater.inflate(R.layout.recyclerview_header, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");

        iniView();
        if (mRecyclerViewAdapter!=null) {
            mRecyclerView.setAdapter(mRecyclerViewAdapter);//
            return;
        }
        getViewFromLocal();
        if (list==null){
            mSwipeContainer.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeContainer.setRefreshing(true);
                }
            });
            getBungumi();
        }

    }
//intentService
    private void iniView() {
        mSwipeContainer.setOnRefreshListener(this);

        mRecyclerView.setHasFixedSize(false);
//        StaggeredGridLayoutManager mStaggeredGridLayoutManager = new FullyStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//表示两列，并且是竖直方向的瀑布流
        StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//表示两列，并且是竖直方向的瀑布流
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
//        mStaggeredGridLayoutManager.setSpanSizeLookup(new GridSpanSizeLookup(mStaggeredGridLayoutManager.getSpanCount()));
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);

    }


    /**
     * 解析json放入view里
     *  @param json String
     */
    private void jsonToRecyclerView(String json){
        if("".equals(json)) return;
        Bungumi bungumi = GsonUtil.parseJson(json, Bungumi.class);
        list = bungumi.getList();
        if (list!=null && mRecyclerViewAdapter==null){
//            mRecyclerViewAdapter = new RecyclerViewAdapter(getActivity(), list);
            mRecyclerViewAdapter = new MultiRecyclerViewAdapter(getActivity(), list);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
        }

        mRecyclerViewAdapter.notifyDataSetChanged();
        mSwipeContainer.setRefreshing(false);
    }

    /**
     * 获取节目列表。赋值给list
     */
    public void getBungumi() {
        HttpUtil.getInstance().send(HttpRequest.HttpMethod.GET, URLConstant.URL_BUNGUMI + "&r=" + RandomUtil.getRandom(999), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                jsonToRecyclerView(responseInfo.result);
                SharePreferenceUtil.setValue(getActivity(), Constant.SP_BILI_FANJU, responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.d(TAG, "onFailure" + s);
                Log.d(TAG, "SharePreferenceUtil" + SharePreferenceUtil.getString(getActivity(), Constant.SP_BILI_FANJU));
                mSwipeContainer.setRefreshing(false);
            }
        });
    }



    /**
     * 首次打开从本地拿历史数据
     */
    public void getViewFromLocal(){
        jsonToRecyclerView(SharePreferenceUtil.getString(getActivity(),Constant.SP_BILI_FANJU));
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh" );
        getBungumi();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
