package com.well.swipecomm.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by mingwei on 3/19/16.
 *
 *
 * 微博：     明伟小学生(http://weibo.com/u/2382477985)
 * Github:   https://github.com/gumingwei
 * CSDN:     http://blog.csdn.net/u013045971
 * QQ&WX：   721881283
 *
 * zhaopenglin
 * 这个类就是一个辅助类，是将view从WindowManager添加或删除的逻辑独立出来的一个类
 */
public class SwipeWindowManager {

    private static final String TAG = "SwipeWindowManager";

    private WindowManager mWindowManager;

    private WindowManager.LayoutParams mParams;

    public SwipeWindowManager(int x, int y, Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        mParams = new WindowManager.LayoutParams();
        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;//这里如果不换成TYPE_PHONE的话那个滑动窗口滑动出来会慢慢消失
//        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mParams.format = PixelFormat.RGBA_8888;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_FULLSCREEN;
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.x = x;
        mParams.y = y;
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
    }

    private boolean isHasWindowManager() {
        return mWindowManager != null;
    }

    public void show(View view) {
        Log.i(TAG,"show");
        if (isHasWindowManager()) {
            if (view.getParent() == null) { //这个判断是保证该view只会被添加一次
                Log.i(TAG,"show view.getParent() != null and add the view");
                mWindowManager.addView(view, mParams);
            }
        }
    }

    public boolean hasView(View view) {
        if (isHasWindowManager()) {
            return view.getParent() != null;
        }
        return false;
    }

    public void dismiss(View view) {
        if (isHasWindowManager()) {
            if (view.getParent() != null) {
                mWindowManager.removeView(view);
            }
        }
    }

}
