package com.example.latte.app;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Tession on 2018/4/21.
 * 对外的工具类，所以都是一些静态方法
 */

public final class Latte {

    public static Configurator init(Context context) {
        getConfigurations().put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<Object, Object> getConfigurations() {
        return Configurator.getInstance().getLatteConfigs();
    }

    public static <T> T getConfiguration(Enum<ConfigKeys> key){
        return Configurator.getInstance().getConfiguration(key);
    }

    public static Context getApplicationContext(){
        return (Context) getConfigurations().get(ConfigKeys.APPLICATION_CONTEXT);
    }
}
