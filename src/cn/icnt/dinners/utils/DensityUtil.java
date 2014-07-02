/*
 * DensityUtil.java
 * classes : com.cloud.app.utils.DensityUtil
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014��6��25�� ����11:31:24
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.utils;

import android.content.Context;

/**
 * com.cloud.app.utils.DensityUtil
 * 
 * @author Andrew Lee <br/>
 *         create at 2014��6��25�� ����11:31:24
 */
public class DensityUtil {
	private static final String TAG = "DensityUtil";

	/**
	 * �����ֻ��ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
