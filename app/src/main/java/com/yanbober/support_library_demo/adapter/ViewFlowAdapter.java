package com.yanbober.support_library_demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanbober.support_library_demo.R;
import com.yanbober.support_library_demo.bean.TuijianViewPagerBean;

import java.util.List;

/**
 * Created by hgx on 2015/7/21.
 */
public class ViewFlowAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<TuijianViewPagerBean.ListEntity> mTuijianList;

    public ViewFlowAdapter(Context context,List<TuijianViewPagerBean.ListEntity> tuijianList) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTuijianList=tuijianList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;   //返回很大的值使得getView中的position不断增大来实现循环
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.image_item, null);
        }
        ImageView mImageView = (ImageView) convertView.findViewById(R.id.imgView);
        ImageLoader.getInstance().displayImage(mTuijianList.get(position%mTuijianList.size())
                .getImageurl(),mImageView);
//        ((ImageView) convertView.findViewById(R.id.imgView)).setImageResource(ids[position%ids.length]);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*                Intent intent = new Intent(mContext,DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("image_id", ids[position%ids.length]);
                intent.putExtras(bundle);
                mContext.startActivity(intent);*/
            }
        });
        return convertView;
    }

}
