package com.example.gll.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class CustomHorizontalScrollView extends HorizontalScrollView{
	private final static String TAG = CustomHorizontalScrollView.class.getSimpleName();

	OnScrolled listenerScrolled;

	private int lastScrollX;
	private int _quantity = 10;
	private Context _context;

	public CustomHorizontalScrollView(Context context) {
		super(context);
		this._context = context;
	}
	public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this._context = context;
	}
	
	public interface OnScrolled{
		public void onNumberPicked(int quantity);
	}

	public void setOnScrollListener(OnScrolled listener) {
		this.listenerScrolled = listener;
	}

	/** 
	 * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法
	 */  
	private Handler handler = new Handler() {  

		public void handleMessage(android.os.Message msg) {  
			int scrollY = CustomHorizontalScrollView.this.getScrollX();  

			//此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息  
			if(lastScrollX != scrollY){  
				lastScrollX = scrollY;  
				handler.sendMessageDelayed(handler.obtainMessage(), 5);    
			}else {
				int dpToPx50 = dp2px(_context, 50);
				if (lastScrollX < dpToPx50/2) {
					CustomHorizontalScrollView.this.smoothScrollTo(0, 0);
					_quantity = 0;
				}else {
					int yushu = lastScrollX%dpToPx50;
					_quantity = lastScrollX/dpToPx50;
					if (yushu != 0) {
						if (yushu > dpToPx50/2) {
							CustomHorizontalScrollView.this.smoothScrollTo((lastScrollX/dpToPx50 + 1)*dpToPx50, 0);
							_quantity = lastScrollX/dpToPx50 + 1;
						}else {
							CustomHorizontalScrollView.this.smoothScrollTo((lastScrollX/dpToPx50)*dpToPx50, 0);
							_quantity = lastScrollX/dpToPx50;
						}
					}
				}
				_quantity += 1;
				if (null != listenerScrolled) {
					listenerScrolled.onNumberPicked(_quantity);
				}
			}
		};  

	};

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		lastScrollX = this.getScrollY();
		switch(ev.getAction()){  
		case MotionEvent.ACTION_UP:  
			handler.sendMessageDelayed(handler.obtainMessage(), 5);    
			break;  
		}  
		return super.onTouchEvent(ev);
	}
	
	private int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
