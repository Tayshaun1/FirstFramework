package com.example.latte.app;

/**
 * Created by Tession on 2018/4/21.
 * 枚举本身就是单例，并且只能被初始化一次，在多线程中可以使用它来实现惰性单例
 */

public enum ConfigKeys {
    /**
     * 网络存储域名的
     */
    API_HOST,
    /**
     * 全局的上下文
     */
    APPLICATION_CONTEXT,
    /**
     * 初始化配置是否完成
     */
    CONFIG_READY,
    /**
     *
     */
    ICON,
    /**
     * 拦截器
     */
    INTERCEPTOR
}
