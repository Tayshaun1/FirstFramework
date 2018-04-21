package com.example.latte.app;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Tession on 2018/4/21.
 * 对外的工具类，所以都是一些静态方法
 */

public final class Latte {

    public static Configurator init(Context context) {
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    }

    private static HashMap<String, Object> getConfigurations() {
        return Configurator.getInstance().getLatteConfigs();
    }

}
