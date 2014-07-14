/*
 * DensityUtil.java
 * classes : com.cloud.app.utils.DensityUtil
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年6月25日   上午11:31:24
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.utils;

import android.content.Context;
/**
 * cn.icnt.dinners.utils.DensityUtil
 * @author Andrew Lee <br/>
 * create at 2014年7月10日 下午2:11:04
 */
public class DensityUtil {
	private static final String TAG = "DensityUtil";

	/**
	 * dp转换成px
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px转换成dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
