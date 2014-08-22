package cn.icnt.dinners.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import cn.icnt.dinners.dinner.R;

public class SharePopupWindow extends PopupWindow {
    private LinearLayout share_sina, share_qq, share_wechat;  
    private View mMenuView;  
   
    public SharePopupWindow(Activity context,OnClickListener itemsOnClick) {  
        super(context);  
        LayoutInflater inflater = (LayoutInflater) context  
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        mMenuView = inflater.inflate(R.layout.share_pop, null);  
        share_sina = (LinearLayout) mMenuView.findViewById(R.id.share_sina);  
        share_qq = (LinearLayout) mMenuView.findViewById(R.id.share_qq);  
        share_wechat = (LinearLayout) mMenuView.findViewById(R.id.share_wechat);  
        //设置按钮监听  
        share_sina.setOnClickListener(itemsOnClick);  
        share_qq.setOnClickListener(itemsOnClick);  
        share_wechat.setOnClickListener(itemsOnClick);  
        //设置SelectPicPopupWindow的View  
        this.setContentView(mMenuView);  
        //设置SelectPicPopupWindow弹出窗体的宽  
        this.setWidth(LayoutParams.FILL_PARENT);  
        //设置SelectPicPopupWindow弹出窗体的高  
        this.setHeight(LayoutParams.WRAP_CONTENT);  
        //设置SelectPicPopupWindow弹出窗体可点击  
        this.setFocusable(true);  
        //设置SelectPicPopupWindow弹出窗体动画效果  
        this.setAnimationStyle(R.style.PopupAnimation);  
        //实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(0xb0000000);  
        //设置SelectPicPopupWindow弹出窗体的背景  
        this.setBackgroundDrawable(dw);  
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框  
        mMenuView.setOnTouchListener(new OnTouchListener() {  
            public boolean onTouch(View v, MotionEvent event) {  
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();  
                int y=(int) event.getY();  
                if(event.getAction()==MotionEvent.ACTION_UP){  
                    if(y<height){  
                        dismiss();  
                    }  
                }                 
                return true;  
            }  
        });  
   
    }  
   
}
