package com.example.gll.myapplication.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.gll.myapplication.R;
import com.example.gll.myapplication.listener.OnDrawViewListener;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gll on 2017/11/20.
 */

public class CircleView extends View {
    /**
     * 默认画笔的颜色
     */
    private int color=Color.BLUE;
    /**
     * 默认画笔的粗细
     */
    private int paintStrokeMiter=15;
    private Resources mResources;
    private Rect mSrcRect, mDestRect;
    private int r=0;
    public float currentx=40;
    public float currenty=50;
    public float prex;
    public float prey;
    /**
     * 定义路径
     */
    private Path mpath= new Path();
    private Paint mPaint,m,mBitmapPaint;
    private List<Path> mList=new ArrayList<>();
    private List<Paint> mListPaint=new ArrayList<>();
    private boolean withDrawing=false;
    private boolean isDrawAnimal=false;

    private Bitmap mBitmap;
    private Canvas mCanvas;


    private OnDrawViewListener onDrawViewListener;
    public void setOnDrawViewListener(OnDrawViewListener onDrawViewListener){
        this.onDrawViewListener=onDrawViewListener;
    }
    //设置画笔的颜色
    public void setPaintColor(int color){
        this.color=color;
        init();
    }
    //设置画笔的宽度
    public void setPaintStrokeMiter(int paintStrokeMiter){
        this.paintStrokeMiter=paintStrokeMiter;
        init();
    }
    //设置画小动物
    public void setDrawAnimal(boolean isDrawAnimal){
        this.isDrawAnimal=isDrawAnimal;
    }



    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    private void init(){
        mSrcRect = new Rect(0, 0, 200, 200);
        mDestRect = new Rect(0, 0, 200, 200);
        mResources = getResources();
        mPaint=new Paint();
        //设置画笔颜色
        mPaint.setColor(color);
        //设置画笔为填充
        mPaint.setStyle(Paint.Style.STROKE);
        //设置画笔宽度
//        mPaint.setStrokeMiter(500f);
        mPaint.setStrokeWidth(paintStrokeMiter);
        mPaint.setAntiAlias(true);
        m=new Paint();
        m.setColor(color);
        m.setStrokeMiter(paintStrokeMiter);

        mBitmapPaint=new Paint();
//        initCanvas();


    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //判断如果不是画动物的状态就画路径,如果是画动物的状态就不画路径
        if (!isDrawAnimal) {
            if (mList.size() > 0) {
                for (int i = 0; i < mList.size(); i++) {
                    canvas.drawPath(mList.get(i), mListPaint.get(i));
                }
            }
            //判断如果不是正在画路径就画上圆点.
            if (!withDrawing) {
                //画路径
                canvas.drawPath(mpath, mPaint);
                canvas.drawCircle(currentx, currenty, r, m);
            }
        }


    }

    //画动物记录上一个动物的位置
    private float initx,inity;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentx=event.getX();
        currenty=event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                withDrawing=false;
                mpath= new Path();
                r=paintStrokeMiter;
                mpath.moveTo(currentx,currenty);
                prex = currentx;
                prey = currenty;
                initx=currentx;
                inity=currenty;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDrawAnimal) {
                    if ( initx - event.getX() > 100 || event.getX() - initx > 100
                            || event.getY() - inity > 100 || inity - event.getY() > 100) {
                        int left = (int) currentx;
                        int top = (int) currenty;
                        int right = 1080 / 2 - (int) currentx;
                        int bottom = 1920 / 2 - (int) currenty;
                        Log.i("LLL", left + "--" + top + "--" + right + "--" + bottom);
                        onDrawViewListener.getXY(left, top, right, bottom);
                        initx = event.getX();
                        inity = event.getY();
                    }
                }
                mpath.quadTo(prex,prey,currentx,currenty);
                prex = currentx;
                prey = currenty;
                break;
            case MotionEvent.ACTION_UP:
                mList.add(mpath);
                mListPaint.add(mPaint);
                r=0;
                break;
        }



        invalidate();
        return true;
    }
    public void pathReset(){
        if (mList.size()>0) {
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).reset();

            }
        }
        mList=new ArrayList<>();
        mListPaint=new ArrayList<>();
        withDrawing=true;
        invalidate();
    }
    public void withDrawing(){
        withDrawing=true;
        if (mList.size()>0) {
                mList.remove(mList.size()-1);
                mListPaint.remove(mListPaint.size()-1);
        }
        invalidate();
    }
    //保存到sd卡
    public void saveToSDCard() {
        //获得系统当前时间，并以该时间作为文件名
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate) + "paint.png";
        File file = new File("sdcard/" + str);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

        Log.e("TAG", "图片已保存");
    }
    public void initCanvas() {
        //画布大小
        mBitmap = Bitmap.createBitmap(1080/2, 1920/2, Bitmap.Config.ARGB_8888);
        mBitmap.eraseColor(Color.argb(0, 0, 0, 0));
        mCanvas = new Canvas(mBitmap);  //所有mCanvas画的东西都被保存在了mBitmap中
        mCanvas.drawColor(Color.TRANSPARENT);
    }

}
