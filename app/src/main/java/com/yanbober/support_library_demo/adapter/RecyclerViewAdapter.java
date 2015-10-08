package com.yanbober.support_library_demo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanbober.support_library_demo.R;
import com.yanbober.support_library_demo.SubActivity;
import com.yanbober.support_library_demo.bean.Bungumi;
import com.yanbober.support_library_demo.utils.MeasureUtil;

import java.util.List;

/**
 * Author       : hgx
 * Date         : 2015-07-13
 * Time         : 09:47
 * Description  :
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Bungumi.ListEntity> mList;
    private int width ;
    public RecyclerViewAdapter(Context mContext,List<Bungumi.ListEntity> list) {
        this.mContext = mContext;
        this.mList=list;
        width = MeasureUtil.getScreenSize((Activity) mContext)[0] / 2 -(MeasureUtil.dp2px(10,mContext));
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        Log.d("position", "" + position);
//        holder.mTextView.setBackgroundColor(mContext.getResources().getColor(colors[position%(colors.length)]));
        if("weblink".equals(mList.get(position).getType())) {
            holder.tv_state.setVisibility(View.GONE);
        }
//        holder.iv_pic.setImageResource(R.mipmap.ic_launcher);
        //此处代码获取屏幕的宽高，实现不同屏幕的适配
        if (position==0){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    width, LinearLayout.LayoutParams.WRAP_CONTENT);
            holder.cv_left.setLayoutParams(params);
            holder.cv_left.setVisibility(View.VISIBLE);
            holder.iv_button_left.setImageResource(R.mipmap.button_left_22);
            holder.iv_button_right.setImageResource(R.mipmap.button_text_bangumi_index);
            holder.iv_button_left.setBackgroundColor(Color.parseColor("#59D6E1"));
            holder.iv_button_right.setBackgroundColor(Color.parseColor("#59D6E1"));
            holder.cv.setVisibility(View.GONE);
        }else if(position==1){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    width, LinearLayout.LayoutParams.WRAP_CONTENT);
            holder.cv_left.setLayoutParams(params);
            holder.cv_left.setVisibility(View.VISIBLE);
            holder.iv_button_left.setImageResource(R.mipmap.button_right_33);
            holder.iv_button_right.setImageResource(R.mipmap.button_text_bangumi_timeline);
            holder.iv_button_left.setBackgroundColor(Color.parseColor("#FFA08B"));
            holder.iv_button_right.setBackgroundColor(Color.parseColor("#FFA08B"));
            holder.cv.setVisibility(View.GONE);
        }else{//position减去偏移量
            holder.tv_state.setText(mList.get(position - 2).getTitle());
            holder.tv_question.setText(mList.get(position-2).getTitle());
            holder.cv_left.setVisibility(View.GONE);
            holder.cv.setVisibility(View.VISIBLE);
            //根据不同屏幕宽度计算图片大小
            int height = width * mList.get(position-2).getHeight() /  mList.get(position-2).getWidth();//高通过宽等比例缩放
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    width, height);
            holder.iv_pic.setLayoutParams(params);
            ImageLoader.getInstance().displayImage(mList.get(position-2).getImageurl(),holder.iv_pic);
        }
        holder.tv_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, SubActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_state;
        public ImageView iv_pic;
        public TextView tv_question;
        public CardView cv_left;
        public CardView cv;
        public ImageView iv_button_left;
        public ImageView iv_button_right;

        public ViewHolder(View view) {
            super(view);
            tv_state = (TextView) view.findViewById(R.id.tv_state);
            tv_question = (TextView) view.findViewById(R.id.tv_question);
            iv_pic=(ImageView) view.findViewById(R.id.iv_pic);
//            cv_left=(CardView) view.findViewById(R.id.cv_left);
            cv=(CardView) view.findViewById(R.id.cv);/*
            iv_button_left=(ImageView) view.findViewById(R.id.iv_button_left);
            iv_button_right=(ImageView) view.findViewById(R.id.iv_button_right);*/
        }
    }
}
