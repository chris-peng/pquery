package cn.com.chrisp.android.pquery;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class UnitUtil {
	
	private static float density = -1;
	
	public static void init(Context context){
		density = context.getResources().getDisplayMetrics().density;
	}
	
	private static void checkInit(){
		if(density < 0){
			throw new RuntimeException("未初始化" + UnitUtil.class.getName());
		}
	};
	
	/**
	 * android长度单位转换，dip->px
	 * @author chris
	 * @param dipValue
	 * @return
	 * @since 2012-8-15 上午11:42:55
	 */
	public static int dip2px(float dipValue){
		checkInit();
		return (int)(dipValue * density + 0.5f);
	}
	
	/**
	 * android长度单位转换，px->dip
	 * @author chris
	 * @param pxValue
	 * @return
	 * @since 2012-8-15 上午11:43:27
	 */
	public static int px2dip(float pxValue){
		checkInit();
		return (int)(pxValue / density + 0.5f);
	}
	
	/** 获取屏幕的宽 */
	public final static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
}
