package com.example.latte.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.latte.R;
import com.example.latte.util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by Tession on 2018/4/25.
 */

public class LatteLoader {

    /**
     * 缩放比例
     */
    private static final int LOADER_SIZE_SCALE = 8;
    /**
     * 偏移量
     */
    private static final int LOADER_OFF_SCALE = 10;

    /**
     * 同意管理dialog
     */
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    /**
     * 提供一个默认的样式
     */
    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotateIndicator.name();

    /**
     * @param context 这里为什么不用applicationContext呢，因为在webview，其他view上显示会报错
     * @param type
     */
    public static void showLoading(Context context, String type) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        dialog.setContentView(avLoadingIndicatorView);

        //控制宽高
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            //额外处理,根据屏幕上下会有一个偏移量
            lp.height = lp.height + deviceHeight / LOADER_OFF_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context, Enum<LoaderStyle> type) {
        showLoading(context, type.name());
    }

    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    /**
     * 停止loading
     */
    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    //cancel和dismiss的区别
                    //cancel会执行一些cancel的一些回调，以后可以自己加入一些回调
                    //dismiss只是单纯的消失
                    dialog.cancel();
                }
            }
        }
    }
}
