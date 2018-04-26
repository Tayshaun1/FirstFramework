package com.example.latte.net;

import android.content.Context;

import com.example.latte.net.callback.IError;
import com.example.latte.net.callback.IFailure;
import com.example.latte.net.callback.IRequest;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Tession on 2018/4/24.
 * 无非就是传值的操作
 * 建造者模式有一个缺点，写起来很烦
 */

public class RestClientBuild {

    private String mUrl;
    /**
     * private Map<String, Object> mParams;
     * 每次都构建不好，所以移到RestCreator中去，直接在这里获取就好了
     */
    private static final Map<String, Object> PARAMS = RestCreator.getParams();
    private IRequest mIRequest;
    private ISuccess mISuccess;
    private IFailure mIFailure;
    private IError mIError;
    private RequestBody mBody;
    private LoaderStyle mLoaderStyle;
    private File mFile;
    private String downloadDir;
    private String extension;
    private String name;
    private Context mContext;

    /**
     * 不允许外部的类直接new，只允许同包的RestClient来new
     */
    public RestClientBuild() {

    }

    public final RestClientBuild url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuild params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuild params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuild file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuild file(String file) {
        this.mFile = new File(file);
        return this;
    }

    public final RestClientBuild dir(String dir) {
        this.downloadDir = dir;
        return this;
    }

    public final RestClientBuild extension(String extension) {
        this.extension = extension;
        return this;
    }

    public final RestClientBuild name(String name) {
        this.name = name;
        return this;
    }

    /**
     * @param raw 原始数据
     * @return
     */
    public final RestClientBuild raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuild onRequest(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    public final RestClientBuild success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuild failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuild error(IError iError) {
        this.mIError = iError;
        return this;
    }

    public final RestClientBuild loader(LoaderStyle style, Context context) {
        this.mLoaderStyle = style;
        this.mContext = context;
        return this;
    }

    public final RestClientBuild loader(Context context) {
        this.mLoaderStyle = LoaderStyle.BallClipRotateIndicator;
        this.mContext = context;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, mIRequest, mISuccess, mIFailure, mIError, mBody, mLoaderStyle, mFile, downloadDir, extension, name, mContext);
    }

}
