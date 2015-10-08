package com.yanbober.support_library_demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;


/**
 * 重写ScrollView拦截向上滑动事件，解决ViewFlow滑动冲突
 * 在系统调用底层onTouchEvent之前会先调用父View的onInterceptTouchEvent方法判断，
 * 父层View是不是要截获本次touch事件之后的action。如果onInterceptTouchEvent返回了true，
 * 那么本次touch事件之后的所有action都不会再向深层的View传递，统统都会传给负层View的onTouchEvent
 * Created by hgx on 2015/7/22.
 * 08-02已通过VIEWFLOW设置getParent().requestDisallowInterceptTouchEvent(true);//阻止父层的View截获touch事件
 *  但本例子还是很有意义的
 */
public class MyScrollView extends ScrollView {
    private float xDistance, yDistance, xLast, yLast;
    public MyScrollView(Context context) {
        super(context);
    }
    public MyScrollView(Context context,AttributeSet attrs){
        super(context, attrs);
    }
    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if (xDistance > yDistance) {
                    return false;//不截获本次事件
                }
//                return false;//不截获本次事件
        }
        return super.onInterceptTouchEvent(ev);
    }
}
