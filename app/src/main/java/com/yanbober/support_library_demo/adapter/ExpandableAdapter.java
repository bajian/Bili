package com.yanbober.support_library_demo.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.yanbober.support_library_demo.bean.TimeLineBean;
import com.yanbober.support_library_demo.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 可以参考 http://www.open-open.com/lib/view/open1406014566679.html
 * Created by hgx on 2015/10/19.
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {
    private static String TAG="ExpandableAdapter";
    private List<TimeLineBean.ListEntity> list,list_copy;
    private Context mContext;
    int day=Calendar.getInstance().get(Calendar.DAY_OF_WEEK);//今天是周几
    private int week_num[] = {0,1,2,3,4,5,6};
    private String week_word[] = {"日","一","二","三","四","五","六","其他"};


    private List<List<TimeLineBean.ListEntity>> dataList=new ArrayList();

    public ExpandableAdapter(Context c,List<TimeLineBean.ListEntity> list){
        this.mContext=c;
        this.list=this.list_copy=list;
        int firstCount = list_copy.size();
        List<TimeLineBean.ListEntity> otherList=new ArrayList();
        //全部按更新时间排序
        Collections.sort(list_copy,new SortByUpdateTime());

//        for (TimeLineBean.ListEntity entity:list_copy)
//            Log.d(TAG, entity.getTitle());


        long otherTime= TimeUtil.getMillis(-7)/1000;
        for(int i=0;i<7;i++)
        {
            int listSize=list_copy.size();
            int index = day - i-1;
            if (index<0) index+=7;
            List<TimeLineBean.ListEntity> weekList=new ArrayList();//存放一周每天的

            for (int j=0;j<listSize;j++){
                if (list_copy.get(j).getWeekday()==index && list_copy.get(j).getLastupdate()>otherTime){
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView tv = new TextView(mContext);
        int index = day - groupPosition-1;
        if (index<0) index+=7;
        tv.setText(week_word[index]);
        return tv;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView tv = new TextView(mContext);
        tv.setText(dataList.get(groupPosition).get(childPosition).getTitle());
//        getGenericView(dataList.get(groupPosition).get(childPosition).getTitle());
        return tv;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    // View stub to create Group/Children 's View
    public TextView getGenericView(String string)
    {
        //演示案例，实际案例要用viewholder
        // Layout parameters for the ExpandableListView
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 64);
        TextView text = new TextView(mContext);
        text.setLayoutParams(layoutParams);
        // Center the text vertically
        text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        // Set the text starting position
        text.setPadding(36, 0, 0, 0);
        text.setText(string);
        return text;
    }
    /**
     * 按更新时间排序
     */
    class SortByUpdateTime implements Comparator {

        @Override
        public int compare(Object lhs, Object rhs) {
            TimeLineBean.ListEntity l1 = (TimeLineBean.ListEntity) lhs;
            TimeLineBean.ListEntity l2 = (TimeLineBean.ListEntity) rhs;
            if (l1.getLastupdate()>l2.getLastupdate())
                return 1;
            return 0;
        }
    }
}
