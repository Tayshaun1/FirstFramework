package com.example.latte.net.callback;

/**
 * Created by Tession on 2018/4/24.
 */

public interface IRequest {

    /**
     * 请求前的回调
     */
    void onRequestStart();

    /**
     * 请求后的回调
     */
    void onRequestEnd();
}
