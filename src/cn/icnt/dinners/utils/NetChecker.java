package cn.icnt.dinners.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

/**
 * 用以检测手机的网络连接,并对apn进行设置
 * 
 * @author think
 * 
 */
public class NetChecker {
    private static Uri PREFERRED_APN_URI = Uri
            .parse("content://telephony/carriers/preferapn");

    public static boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo net = connectivityManager.getActiveNetworkInfo();
        if (net != null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查当前的网络
     * 
     * @param context
     * @return
     */
    public static boolean checkNet(Context context) {
        // 获取到wifi和mobile连接方式
        boolean wifiConnection = isWIFIConnection(context);
        boolean mobileConnection = isMobileConnection(context);

        if (wifiConnection == false && mobileConnection == false) {
            // 如果都不能连接
            // 提示用户设置当前网络——跳转到设置界面
            return false;
        }

//        if (mobileConnection) {
//            // mobile:apn ip和端口
//            // 有部分手机：10.0.0.172 010.000.000.172 80
//            // ContentProvier——ContentReserver
//            setApn(context);
//        }
        return true;
    }

    /**
     * 读取apn设置信息获取到ip 端口
     */
    private static void setApn(Context context) {
        ContentResolver resolver = context.getContentResolver();
        Cursor query = resolver
                .query(PREFERRED_APN_URI, null, null, null, null);
        if (query != null && query.moveToNext()) {
           // Constants.PROXY_IP = query.getString(query.getColumnIndex("proxy"));
          //  Constants.PROXY_PORT = query.getInt(query.getColumnIndex("port"));
        }
    }

    /**
     * 判断wifi是否连接
     * 
     * @param context
     * @return
     */
    public static boolean isWIFIConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null)
            return networkInfo.isConnected();
        return false;
    }

    /**
     * 判断apn
     * 
     * @param context
     * @return
     */
    public static boolean isMobileConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null)
            return networkInfo.isConnected();
        return false;

    }

}
