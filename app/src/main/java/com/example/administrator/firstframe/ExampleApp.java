package com.example.administrator.firstframe;

import android.app.Application;

import com.example.latte.app.Latte;
import com.example.latte.ec.icon.FontEcModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by Tession on 2018/4/21.
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                //使用使用自定义的图片
                .withIcon(new FontEcModule())
//                .withInterceptor()
                .withApiHost("http://127.0.0.1/")
                //.with....
                .configure();
    }
}
