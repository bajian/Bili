package com.yanbober.support_library_demo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bajian.viewflow.CircleFlowIndicator;
import com.example.bajian.viewflow.ViewFlow;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanbober.support_library_demo.R;
import com.yanbober.support_library_demo.SubActivity;
import com.yanbober.support_library_demo.bean.Bungumi;
import com.yanbober.support_library_demo.utils.MeasureUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author       : hgx
 * Date         : 2015-07-13
 * Time         : 09:47
 * Description  :
 */
public class MultiRecyclerViewAdapter extends BaseMultipleItemAdapter {

    private Context mContext;
    private List<Bungumi.ListEntity> mList;
    private int width;

    public MultiRecyclerViewAdapter(Context mContext, List<Bungumi.ListEntity> list) {
        super(mContext);
        this.mContext = mContext;
        this.mList = list;
        width = MeasureUtil.getScreenSize((Activity) mContext)[0] / 2 - (MeasureUtil.dp2px(10, mContext));
        setBottomCount(0);
        setHeaderCount(1);
    }


    /**
     * 在这里设置所有item的数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ((HeaderViewHolder) holder).mLlHeader.setLayoutParams(params);

            //设置占满全格
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setFullSpan(true);
            holder.itemView.setLayoutParams(layoutParams);
        } else if (holder instanceof ViewHolder) {
            onMyBindViewHolder((ViewHolder)holder, position-mHeaderCount-mBottomCount);
        }
    }

    public void onMyBindViewHolder(ViewHolder holder, int position) {
        Log.d("position", "" + position);
//        holder.mTextView.setBackgroundColor(mContext.getResources().getColor(colors[position%(colors.length)]));
        if ("weblink".equals(mList.get(position).getType())) {
            holder.tv_state.setVisibility(View.GONE);
        }
//        holder.iv_pic.setImageResource(R.mipmap.ic_launcher);
        //此处代码获取屏幕的宽高，实现不同屏幕的适配

            holder.tv_state.setText(mList.get(position).getTitle());
            holder.tv_question.setText(mList.get(position).getTitle());
            holder.cv.setVisibility(View.VISIBLE);
            //根据不同屏幕宽度计算图片大小
            int height = width * mList.get(position).getHeight() / mList.get(position).getWidth();//高通过宽等比例缩放
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    width, height);
            holder.iv_pic.setLayoutParams(params);
            ImageLoader.getInstance().displayImage(mList.get(position).getImageurl(), holder.iv_pic);

        holder.tv_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, SubActivity.class));
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderView(ViewGroup parent) {
        return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.recyclerview_multiheader, parent, false));
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBottomView(ViewGroup parent) {
        return null;
    }

    @Override
    public int getContentItemCount() {
        return mList.size();
    }

    /**
     * 瀑布流的viewholder，在这里设置item的监听事件
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_state;
        public ImageView iv_pic;
        public TextView tv_question;
        public CardView cv;

        public ViewHolder(View view) {
            super(view);
            tv_state = (TextView) view.findViewById(R.id.tv_state);
            tv_question = (TextView) view.findViewById(R.id.tv_question);
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            cv = (CardView) view.findViewById(R.id.cv);
        }
    }



    public static class HeaderViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.viewflow)
        ViewFlow mViewflow;
        @Bind(R.id.viewflowindic)
        CircleFlowIndicator mViewflowindic;
        @Bind(R.id.framelayout)
        FrameLayout mFramelayout;
        @Bind(R.id.iv_pic)
        ImageView mIvPic;
        @Bind(R.id.tv_question)
        TextView mTvQuestion;
        @Bind(R.id.cv)
        CardView mCv;
        @Bind(R.id.iv_pic_right)
        ImageView mIvPicRight;
        @Bind(R.id.tv_question_right)
        TextView mTvQuestionRight;
        @Bind(R.id.cv_right)
        CardView mCvRight;
        @Bind(R.id.iv_pic2)
        ImageView mIvPic2;
        @Bind(R.id.tv_question2)
        TextView mTvQuestion2;
        @Bind(R.id.cv2)
        CardView mCv2;
        @Bind(R.id.iv_pic_right2)
        ImageView mIvPicRight2;
        @Bind(R.id.tv_question_right2)
        TextView mTvQuestionRight2;
        @Bind(R.id.cv_right2)
        CardView mCvRight2;
        @Bind(R.id.ll_header)
        LinearLayout mLlHeader;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
