package com.well.swipe.activitys;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.*;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.well.swipe.R;
import com.well.swipe.service.SwipeService;
import com.well.swipe.tools.SwipeSetting;
import com.well.swipecomm.utils.SettingHelper;

/**
 * Created by mingwei on 4/4/16.
 *
 *
 * 微博：     明伟小学生(http://weibo.com/u/2382477985)
 * Github:   https://github.com/gumingwei
 * CSDN:     http://blog.csdn.net/u013045971
 * QQ&WX：   721881283
 *
 *
 */
public class SplashActivity extends Activity {

    private final int REQUEST_ALERT_WINDOW = 1;
    private final static String TAG ="SplashActivity";
    RequestAlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_splash);
    }

    /**
     * 可出现在其他应用上的应用的界面的启动
     */
    private void startPermission() {
        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void finishSplash() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                startSwipeSetting();
            } else {
                mAlertDialog = new RequestAlertDialog(this);
                mAlertDialog.setTitle(getString(R.string.request_windows_title)).
                        setContentDes(getString(R.string.request_windows_content)).
                        setPositiveTitle(getString(R.string.request_windows_pos)).
                        setNegativeTitle(getString(R.string.request_windows_nev)).
                        onPositive(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mAlertDialog.dismiss();
                                startPermission();
                            }
                        })
                        .onNegative(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mAlertDialog.dismiss();
                                finishSplash();
                            }
                        })
                        .setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0)
                                {
                                    mAlertDialog.dismiss();
                                    finishSplash();
                                }
                                return false;
                            }
                        }).show();
            }
        }
    }

    private void startSwipeSetting() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), SwipeSettingActivity.class));
                if (SettingHelper.getInstance(getBaseContext()).getBoolean(SwipeSetting.SWIPE_TOGGLE, true)) {
                    startService(new Intent(getBaseContext(), SwipeService.class));
                }
                finish();
            }
        }, 1000);//延时1秒是为了防止闪屏
    }

}
