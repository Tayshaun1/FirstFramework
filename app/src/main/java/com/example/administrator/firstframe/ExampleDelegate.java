package com.example.administrator.firstframe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.latte.delegates.LattDelegate;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.IError;
import com.example.latte.net.callback.IFailure;
import com.example.latte.net.callback.ISuccess;

/**
 * Created by Tession on 2018/4/24.
 */

public class ExampleDelegate extends LattDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rooView) {
        testRestClient();
    }

    private void testRestClient() {
        RestClient.build()
                .url("http://news.baidu.com/")
                .loader(getContext())
//                .params("", "")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
//                        Toast.makeText(Latte.getApplicatoinContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Log.e(getClass().getSimpleName(), "onFailure");
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.e(getClass().getSimpleName(), "onError:" + msg);
                    }
                })
                .build()
                .get();
    }

    private void testRestClient2() {
        RestClient.build()
                .url("http://news.baidu.com/")
                .loader(getContext())
//                .params("", "")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
//                        Toast.makeText(Latte.getApplicatoinContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Log.e(getClass().getSimpleName(), "onFailure");
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.e(getClass().getSimpleName(), "onError:" + msg);
                    }
                })
                .dir("")
                .extension("")
                .name("")
                .build()
                .download();
    }

    private void testRestClient3() {
        RestClient.build()
                .url("http://news.baidu.com/")
                .loader(getContext())
//                .params("", "")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
//                        Toast.makeText(Latte.getApplicatoinContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Log.e(getClass().getSimpleName(), "onFailure");
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.e(getClass().getSimpleName(), "onError:" + msg);
                    }
                })
                .dir("")
                .extension("")
                .name("")
                .build()
                .download();
    }
}
