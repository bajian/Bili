package com.yanbober.support_library_demo.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;


/**
 * 重写ScrollView拦截向上滑动事件，解决ViewFlow滑动冲突
 * 在系统调用底层onTouchEvent之前会先调用父View的onInterceptTouchEvent方法判断，
 * 父层View是不是要截获本次touch事件之后的action。如果onInterceptTouchEvent返回了true，
 * 那么本次touch事件之后的所有action都不会再向深层的View传递，统统都会传给负层View的onTouchEvent
 * Created by hgx on 2015/7/22.
 * 08-02已通过VIEWFLOW设置getParent().requestDisallowInterceptTouchEvent(true);//阻止父层的View截获touch事件
 *  但本例子还是很有意义的
 */
public class MyNestedScrollView extends NestedScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop;

    public MyNestedScrollView(Context context) {
        super(context);
    }
    public MyNestedScrollView(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    public MyNestedScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }
}
