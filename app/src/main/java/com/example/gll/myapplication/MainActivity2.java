package com.example.gll.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.gll.myapplication.pageturn.factory.PicturesPageFactory;
import com.example.gll.myapplication.pageturn.view.CoverPageView;
import com.example.gll.myapplication.view.SlideBoardView;


/**
 * sample
 * 
 * @author Liangzheng
 */
public class MainActivity2 extends Activity {
//	SlideBoardView slideBoardView1 = null;
//	View flag;
	CoverPageView coverPageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		coverPageView = findViewById(R.id.coverPageView);
		PicturesPageFactory picturesPageFactory = new PicturesPageFactory(this,new int[]{R.drawable.ic_launcher_background,R.drawable.ic_launcher_background});
		coverPageView.setPageFactory(picturesPageFactory);



//		flag = findViewById(R.id.flag);
//		slideBoardView1 = (SlideBoardView) findViewById(R.id.slideBoardView1);
//
//		slideBoardView1.setOnCollapseBoardListener(new SlideBoardView.OnCollapseBoardListener() {
//
//			@Override
//			public void onCollapse(boolean isExpanded) {
//				if (isExpanded) {
//					flag.setBackgroundColor(Color.parseColor("#FFBB33"));
//				} else {
//					flag.setBackgroundColor(Color.parseColor("#FF8800"));
//				}
//			}
//		});

	}
}
