package com.example.gll.myapplication;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gll.myapplication.listener.OnDrawViewListener;
import com.example.gll.myapplication.view.CircleView;
import com.example.gll.myapplication.view.ColorBar;
import com.example.gll.myapplication.view.CustomHorizontalScrollView;

import java.util.Random;

public class MainActivity extends  AppCompatActivity implements View.OnClickListener

{
    private TextView reset,back,select_color,withdrawing,thick_thin,drawAnimal,save;
    private CircleView mCirCleView;
    private ColorBar colorBar;
    private CustomHorizontalScrollView customHorizontalScrollView;
    private ImageView iv_gif,imageView;
    private ViewGroup group;
    private boolean isOne=true;
    private boolean isDrawAnimal=false;
    private RelativeLayout.LayoutParams params;
    private int[] res={R.drawable.iv_gif_1,
            R.drawable.iv_gif_2,
            R.drawable.iv_gif_3,
            R.drawable.iv_gif_4,
            R.drawable.iv_gif_5,
            R.drawable.iv_gif_6,
            R.drawable.iv_gif_7,
            R.drawable.iv_gif_8,
            R.drawable.iv_gif_9,
            R.drawable.iv_gif_10,
            R.drawable.iv_gif_11,
            R.drawable.iv_gif_12,
            R.drawable.iv_gif_13,
            R.drawable.iv_gif_14,
            R.drawable.iv_gif_15,
            R.drawable.iv_gif_16,
            R.drawable.iv_gif_17,
            R.drawable.iv_gif_18,
            R.drawable.iv_gif_19,
            R.drawable.iv_gif_20,
            R.drawable.iv_gif_21,
            R.drawable.iv_gif_22,
            R.drawable.iv_gif_23,
            R.drawable.iv_gif_24,
            R.drawable.iv_gif_25,
            R.drawable.iv_gif_26,
            R.drawable.iv_gif_27,
            R.drawable.iv_gif_28,
            R.drawable.iv_gif_29,
            R.drawable.iv_gif_30,
            R.drawable.iv_gif_31,
            R.drawable.iv_gif_32,
            R.drawable.iv_gif_33,
            R.drawable.iv_gif_34,
            R.drawable.iv_gif_35,
            R.drawable.iv_gif_36};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
    }

    private void initUI() {
        group = findViewById(R.id.rl_relativeLayout); //获取原来的布局容器



        group.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        customHorizontalScrollView=findViewById(R.id.customHorizontalScrollView);

        colorBar=findViewById(R.id.colorBar);
        reset=findViewById(R.id.reset);
        reset.setOnClickListener(this);
        back=findViewById(R.id.back);
        back.setOnClickListener(this);
        thick_thin=findViewById(R.id.thick_thin);
        thick_thin.setOnClickListener(this);
        save=findViewById(R.id.save);
        save.setOnClickListener(this);

        drawAnimal=findViewById(R.id.drawAnimal);
        drawAnimal.setOnClickListener(this);
        select_color=findViewById(R.id.select_color);
        select_color.setOnClickListener(this);
        withdrawing=findViewById(R.id.withdrawing);
        withdrawing.setOnClickListener(this);
        mCirCleView=findViewById(R.id.mCirCleView);
        mCirCleView.setPaintColor(Color.BLACK);
        mCirCleView.setPaintStrokeMiter(15);
        colorBar.setOnColorChangerListener(new ColorBar.ColorChangeListener() {
            @Override
            public void colorChange(int color) {
                mCirCleView.setPaintColor(color);
            }
        });
        customHorizontalScrollView.setOnScrollListener(new CustomHorizontalScrollView.OnScrolled() {
            @Override
            public void onNumberPicked(int quantity) {
                mCirCleView.setPaintStrokeMiter(quantity);
            }
        });

        mCirCleView.setOnDrawViewListener(new OnDrawViewListener() {
            @Override
            public void getXY(int left, int top, int right, int bottom) {
//                if (isOne) {
                    imageView=new ImageView(MainActivity.this);
//                    imageView.setPadding(left, top, right, bottom);
//                    imageView.setPadding(20,20,50,50);
                    int max=35;
                    int min=0;
                    Random random = new Random();
                    int s = random.nextInt(max)%(max-min+1) + min;
                    setGIFImage(res[s], imageView);
                    params=new RelativeLayout.LayoutParams(
                        100, 100);
                    params.setMargins(left, top, right, bottom);
                    imageView.setLayoutParams(params);
                    group.addView(imageView);
                    isOne=false;
//                }
            }
        });
//        imageView.setPadding(left,top,right,bottom);
//        imageView.setPadding(20,20,50,50);
//        setGIFImage(R.drawable.iv_gif,imageView);
//        group.addView(imageView);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //清空画布
            case R.id.reset:
                mCirCleView.pathReset();
                if (isDrawAnimal){
                    group.removeAllViews();
                }
                break;
            //隐藏颜色选择器
            case R.id.back:
                hideColorBar();
                break;
            //显示颜色选择器
            case R.id.select_color:
                mCirCleView.pathReset();
                showColorBar();
                showToase("你选择了画图");
                mCirCleView.setDrawAnimal(false);
                if (isDrawAnimal){
                    group.removeAllViews();
                }
                isDrawAnimal=false;

                break;
            //撤回
            case R.id.withdrawing:
                mCirCleView.withDrawing();
                if (isDrawAnimal&&group.getChildCount()>0){
                    group.removeViewAt(group.getChildCount()-1);
                }
                break;
            //选择粗细
            case R.id.thick_thin:
                mCirCleView.pathReset();
                showCustomHorizontalScrollView();
                mCirCleView.setDrawAnimal(false);
                if (isDrawAnimal){
                    group.removeAllViews();
                }
                isDrawAnimal=false;
                showToase("你选择了画图");
                break;
            //画动物
            case R.id.drawAnimal:
                mCirCleView.pathReset();
                mCirCleView.setDrawAnimal(true);
                isDrawAnimal=true;
                showToase("你选择了画动物");
                break;
                //保存
            case R.id.save:
//                mCirCleView.saveToSDCard();
//                Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
//                intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
//                sendBroadcast(intent);
//                String[] paths = new String[]{Environment.getExternalStorageDirectory().toString()};
//                MediaScannerConnection.scanFile(this, paths, null, null);
                break;
        }
    }
    private void showColorBar(){
        thick_thin.setVisibility(View.GONE);
        colorBar.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        reset.setVisibility(View.GONE);
        select_color.setVisibility(View.GONE);
        withdrawing.setVisibility(View.GONE);
        drawAnimal.setVisibility(View.GONE);

    }
    private void showCustomHorizontalScrollView(){
        thick_thin.setVisibility(View.GONE);
        customHorizontalScrollView.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        reset.setVisibility(View.GONE);
        select_color.setVisibility(View.GONE);
        withdrawing.setVisibility(View.GONE);
        drawAnimal.setVisibility(View.GONE);
    }
    private void hideColorBar(){
        thick_thin.setVisibility(View.VISIBLE);
        customHorizontalScrollView.setVisibility(View.GONE);
        colorBar.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        reset.setVisibility(View.VISIBLE);
        select_color.setVisibility(View.VISIBLE);
        withdrawing.setVisibility(View.VISIBLE);
        drawAnimal.setVisibility(View.VISIBLE);
    }

    private void setGIFImage(int res, ImageView imageView){
        Glide.with(MainActivity.this).load(res)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }
    private void showToase(String message){
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
    }

}
