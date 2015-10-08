package com.yanbober.support_library_demo.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.example.bajian.viewflow.ViewFlow;

/**
 * 重写ViewPager拦截父控件ViewPager滑动事件，解决ViewFlow滑动冲突
 * Created by hgx on 2015/7/22.
 */
public class MyViewPager extends ViewPager {
//    private boolean enabled=false;

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        return v != this && v instanceof ViewFlow || super.canScroll(v, checkV, dx, x, y);
    }
/*外围容器想独自处理触屏事件，那么就应该在相应的onInterceptTouchEvent函数中返回true,表示要截获触屏事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(ev);
        }

        return false;
    }*/

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
