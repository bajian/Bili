package com.yanbober.support_library_demo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanbober.support_library_demo.R;
import com.yanbober.support_library_demo.bean.TimeLineBean;
import com.yanbober.support_library_demo.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 可以参考 http://www.open-open.com/lib/view/open1406014566679.html
 * Created by hgx on 2015/10/19.
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {
    private static String TAG = "ExpandableAdapter";
    private final LayoutInflater Inflate;
    private List<TimeLineBean.ListEntity> list, list_copy;
    private Context mContext;
    int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);//今天是周几
    private int week_num[] = {7, 1, 2, 3, 4, 5, 6};
    private String week_word[] = {"日", "一", "二", "三", "四", "五", "六", "其他"};


    private List<List<TimeLineBean.ListEntity>> dataList = new ArrayList();

    public ExpandableAdapter(Context c, List<TimeLineBean.ListEntity> list) {
        this.mContext = c;
        this.list = this.list_copy = list;
        Inflate = LayoutInflater.from(mContext);
        int firstCount = list_copy.size();
        List<TimeLineBean.ListEntity> otherList = new ArrayList();
        //全部按更新时间排序
        Collections.sort(list_copy, new SortByUpdateTime());
//        for (TimeLineBean.ListEntity entity:list_copy)
//            Log.d(TAG, entity.getTitle());

        long otherTime = TimeUtil.getMillis(-7) / 1000;
        for (int i = 0; i < 7; i++) {
            int listSize = list_copy.size();
            int index = day - i - 1;
            if (index < 0) index += 7;
            List<TimeLineBean.ListEntity> weekList = new ArrayList();//存放一周每天的

            for (int j = 0; j < listSize; j++) {
                if (list_copy.get(j).getWeekday() == index &&
                        list_copy.get(j).getLastupdate() > otherTime) {
                    //周index更新：
                    weekList.add(list_copy.get(j));
                    list_copy.remove(j);
                    listSize--;
                    j--;
                }
            }
            dataList.add(weekList);
        }
        dataList.add(list_copy);//最后剩下的归到其他


//        for (int i=0;i<dataList.size();i++)
//            Log.d(TAG, "dataList.get(0).size();" +dataList.get(i).size());


    }

    @Override
    public int getGroupCount() {
        return dataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return week_word[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataList.get(groupPosition).get(childPosition).getTitle();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        int index = day - groupPosition - 1;
        if (index < 0) index += 7;

        View view = convertView;
        ViewHolderGroup holder;
        int resId = mContext.getResources().getColor(R.color.commom_style_gray);
        if (groupPosition == 0)
            resId = mContext.getResources().getColor(R.color.commom_style_primary);

        if (view == null) {
            view = Inflate.inflate(R.layout.expandable_list_item_group, null, false);
            holder = new ViewHolderGroup(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolderGroup) view.getTag();
        }
        //着色
        //basicDrawable.mutate()解决染色错位
        Drawable basicDrawable = ContextCompat.getDrawable(mContext, R.mipmap.timeline_background);
        basicDrawable = DrawableCompat.wrap(basicDrawable);
        DrawableCompat.setTint(basicDrawable.mutate(), resId);
        holder.mIvCal.setImageDrawable(basicDrawable);

        holder.mVLine.setBackgroundColor(resId);
        holder.mTvDay.setTextColor(resId);
        holder.mTvWeek.setTextColor(resId);
        holder.mTvTime.setTextColor(resId);

        holder.mTvTime.setText(week_num[index] + "");
        holder.mTvDay.setText("周" + week_word[index]);
        holder.mTvWeek.setText("今天");
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        TextView tv = new TextView(mContext);
//        tv.setText(dataList.get(groupPosition).get(childPosition).getTitle());
        View view = convertView;
        ViewHolderChild holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.expandable_list_item_child, null, false);
            holder= new ViewHolderChild(view);
            view.setTag(holder);
        } else {
            holder= (ViewHolderChild) view.getTag();
        }

        int resId = mContext.getResources().getColor(R.color.commom_style_gray);
        if (dataList.get(groupPosition).get(childPosition).getNewX())
            resId = mContext.getResources().getColor(R.color.commom_style_primary);

        holder.mTvTitle.setText(dataList.get(groupPosition).get(childPosition).getTitle());
        holder.mTvTime.setText("第"+dataList.get(groupPosition).get(childPosition).getBgmcount()+"话");
        holder.mTvTitle.setTextColor(resId);
        holder.mTvTime.setTextColor(resId);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    /**
     * 按更新时间排序
     */
    class SortByUpdateTime implements Comparator {

        @Override
        public int compare(Object lhs, Object rhs) {
            TimeLineBean.ListEntity l1 = (TimeLineBean.ListEntity) lhs;
            TimeLineBean.ListEntity l2 = (TimeLineBean.ListEntity) rhs;
            if (l1.getLastupdate() > l2.getLastupdate())
                return 1;
            return 0;
        }
    }


    /**
     * ViewHolder Group
     */
    static class ViewHolderGroup {
        @Bind(R.id.iv_cal)
        ImageView mIvCal;
        @Bind(R.id.tv_time)
        TextView mTvTime;
        @Bind(R.id.fl_calenda)
        FrameLayout mFlCalenda;
        @Bind(R.id.tv_day)
        TextView mTvDay;
        @Bind(R.id.tv_week)
        TextView mTvWeek;
        @Bind(R.id.v_line)
        View mVLine;

        ViewHolderGroup(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'expandable_list_item_child.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolderChild {
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.tv_time)
        TextView mTvTime;

        ViewHolderChild(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
