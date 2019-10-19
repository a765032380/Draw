package com.example.gll.myapplication.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import static android.view.GestureDetector.*;

public class MyRelativeLayout extends RelativeLayout {
    public MyRelativeLayout(Context context) {
        super(context);
        init();
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    GestureDetector gestureDetector;
    ObjectAnimator objectAnimator;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("LLLLLLL","ev.getRawX()="+ev.getRawX());
        objectAnimator = ObjectAnimator.ofFloat(this,"translationX",ev.getRawX(),ev.getRawX(),0f);
        objectAnimator.setDuration(0);
        objectAnimator.start();
//        boolean onTouchEvent = gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean onTouchEvent = gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    private void init(){
         gestureDetector = new GestureDetector(getContext(), new OnGestureListener(){
            @Override
            public boolean onDown(MotionEvent e) {
                // 按下
                return false;
            }
            @Override
            public void onShowPress(MotionEvent e) {
                // TODO Auto-generated method stub
            }
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // 点击
                return false;
            }
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.d("LLLLLLL","aaaaaaa");
                // 滑动
                return false;
            }
            @Override
            public void onLongPress(MotionEvent e) {
                // 长按
            }
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2,float velocityX, float velocityY) {
                Log.d("LLLLLLL","aaaaaaa");
                if(e1.getRawX()-e2.getRawX()>200){
                    Log.d("LLLLLLL","bbbbbbbb");
                    objectAnimator = ObjectAnimator.ofFloat(this,"translationX",e1.getRawX(),e2.getRawX(),0f);
                    objectAnimator.setDuration(0);
                    objectAnimator.start();
                    //向左滑（与滑动动画配合效果较好）
                }else if(e2.getRawX()-e1.getRawX()>200){
                    //向右滑
                }
                if(Math.abs(e1.getRawY()-e2.getRawY())>200){
                    Log.d("LLLLLLL","不能斜着滑动");
                    System.out.println("不能斜着滑动");
                    return true;//true表示我们消费了这个触摸事件
                }
                if(Math.abs(velocityX)<150 ||Math.abs(velocityY)<100){
                    Log.d("LLLLLLL","滑动的太慢了，请滑快点");
                    System.out.println("滑动的太慢了，请滑快点");
                    return true;
                }
                return true;
            }
        });
    }

}
