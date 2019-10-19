package com.example.gll.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * bitmap工具类：文字转成图片
 * Created by frank on 2018/1/24.
 */

public class BitmapUtil {

    private final static int TEXT_SIZE = 4;
    private final static int TEXT_COLOR = Color.WHITE;

    /**
     * 文本转成Bitmap
     * @param text 文本内容
     * @param context 上下文
     * @return 图片的bitmap
     */
    public static Bitmap textToBitmap(String text, Context context) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
//
        LinearLayout linearLayout = new LinearLayout(context);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        linearLayout.setLayoutParams(layoutParams);
//        linearLayout.setLayoutParams(layoutParams);

        tv.setLayoutParams(layoutParams);
        tv.setText(text);
        tv.setTextSize(scale * TEXT_SIZE);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setDrawingCacheEnabled(true);
        tv.setTextColor(TEXT_COLOR);
        tv.setBackgroundColor(Color.TRANSPARENT);
        tv.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
        tv.buildDrawingCache();
        Bitmap bitmap = tv.getDrawingCache();
        int rate = bitmap.getHeight() / 20;
//        return add2Bitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background),
//                Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false));
        return
                Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false);
    }

    /**
     * 横向拼接
     * <功能详细描述>
     * @param first
     * @param second
     * @return
     */
    private static Bitmap add2Bitmap(Bitmap first, Bitmap second) {
//        int width = first.getWidth() + second.getWidth()+10;
//        int height = Math.max(first.getHeight(), second.getHeight());


        int width = Math.max(first.getWidth() ,second.getWidth());
        int height = first.getHeight() + second.getHeight()+10;

        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, 0, first.getHeight()+10, null);
        return result;

    }

    public static Bitmap getAlplaBitmap(Bitmap sourceImg, int number) {

        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];
        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg.getWidth(), sourceImg.getHeight());

        number = number * 255 / 100;

        for (int i = 0; i < argb.length; i++) {

              argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);

        }

         sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg.getHeight(), Bitmap.Config.ARGB_8888);

        return sourceImg;
    }
    /**
     * 文字生成图片
     * @param filePath filePath
     * @param text text
     * @param context context
     * @return 生成图片是否成功
     */
    public static boolean textToPicture(String filePath, String text , Context context){
        Bitmap bitmap = textToBitmap(text , context);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                if(outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 删除源文件
     * @param filePath filePath
     * @return 删除是否成功
     */
    public static boolean deleteTextFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.delete();
    }

}
