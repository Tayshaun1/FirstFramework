package com.example.latte.ui;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * Created by Tession on 2018/4/25.
 * 官方的方式是通过反射，取load的名字来加载出这个load，我们每次请求都去反射一次，那么性能还是堪忧的，我们自己改进下,用WeakHashMap缓存，之后就不用每次都反射了
 */

public class LoaderCreator {
    private static final WeakHashMap<String, Indicator> LOADINGMAP = new WeakHashMap<>();

    static AVLoadingIndicatorView create(String type, Context context) {
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if (LOADINGMAP.get(type) == null) {
            final Indicator indicator = getIndicator(type);
            LOADINGMAP.put(type, indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADINGMAP.get(type));
        return avLoadingIndicatorView;
    }

    private static Indicator getIndicator(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        //这里用StringBuilder(线程不安全的)，是因为会有大量的字符串拼接
        final StringBuilder drawableClassName = new StringBuilder();
        //如果包含“.”就表示传入的是一个类名
        if(!name.contains(".")){
            //使用反射里的一些方法
            final String defaultpackageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawableClassName
                    .append(defaultpackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(name);
        try {
            final Class<?> drawableClass=Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
