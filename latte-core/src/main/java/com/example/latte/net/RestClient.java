package com.example.latte.net;

import android.content.Context;

import com.example.latte.net.callback.IError;
import com.example.latte.net.callback.IFailure;
import com.example.latte.net.callback.IRequest;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.net.callback.RequestCallbacks;
import com.example.latte.ui.LatteLoader;
import com.example.latte.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author Tession
 * @date 2018/4/24
 * 具体请求的实现类
 * 框架要灵活的话使用什么样的设计模式会比较好：
 * 比如这个是要传入参数，并且没有顺序要求，传入什么用什么，那么用建造者模式就很好
 * 建造者模式又分为，标准模式和android简化的建造者模式alertDialog就是用着这种模式
 * <p>
 * 参数在第一次build时一次申明后就不允许更改，这里的变量用final
 * <p>
 * final的变量一开始没有赋值，那么就必须在构造方法中赋值
 */

public class RestClient {

    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final IFailure IFAILURE;
    private final IError IERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    /**
     * 因为dialog需要Context
     */
    private final Context CONTEXT;

    public RestClient(String url, Map<String, Object> params, IRequest request, ISuccess success, IFailure failure, IError error, RequestBody
            body, LoaderStyle loaderStyle, File file, Context context) {
        this.URL = url;
        PARAMS.putAll(params);
        this.IREQUEST = request;
        this.ISUCCESS = success;
        this.IFAILURE = failure;
        this.IERROR = error;
        this.BODY = body;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = file;
        this.CONTEXT = context;
    }

    /**
     * 创建构造者
     *
     * @return
     */
    public static RestClientBuild build() {
        return new RestClientBuild();
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;

        if (IREQUEST != null) {
            IREQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
//                call = service.delete(URL, PARAMS);
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName(),requestBody);
                call = service.upload(URL, body);
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(getResponseCallback());
        }
    }

    private Callback<String> getResponseCallback() {
        return new RequestCallbacks(IREQUEST, ISUCCESS, IFAILURE, IERROR, LOADER_STYLE);
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            //使用POST_RAW时，参数一定要是空的
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            //使用POST_RAW时，参数一定要是空的
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

}
