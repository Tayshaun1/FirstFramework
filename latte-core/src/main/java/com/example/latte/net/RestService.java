package com.example.latte.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Tession on 2018/4/24.
 * 通用的封装
 */

public interface RestService {

    /**
     * 通用get请求
     *
     * @param url
     * @param params value用Object，就可以传入任何东西了
     * @return
     */
    @GET
    Call<String> get(@Url String url, @QueryMap Map<String, Object> params);


    /**
     * 通用post请求
     *
     * @param url
     * @param params value用Object，就可以传入任何东西了
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @FieldMap Map<String, Object> params);

    @DELETE
    Call<String> delete(@Url String url, @QueryMap Map<String, Object> params);

    /**
     * 默认一次下载到你的内存里，然后当下载完毕之后再统一写到你的文件里，这里会有一种问题：
     * 当文件过大，内存呼溢出，所以这里加上Streaming注解，边下载边写入，这里当然也要异步处理
     *
     * @param url
     * @param params
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);


    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);

}
