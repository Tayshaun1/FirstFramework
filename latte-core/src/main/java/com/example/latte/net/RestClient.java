package com.example.latte.net;

import com.example.latte.net.callback.IError;
import com.example.latte.net.callback.IFailure;
import com.example.latte.net.callback.IRequest;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.net.callback.RequestCallbacks;

import java.util.Map;
import java.util.WeakHashMap;

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

    public RestClient(String url, Map<String, Object> params, IRequest request, ISuccess success, IFailure failure, IError error, RequestBody
            body) {
        this.URL = url;
        PARAMS.putAll(params);
        this.IREQUEST = request;
        this.ISUCCESS = success;
        this.IFAILURE = failure;
        this.IERROR = error;
        this.BODY = body;
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

        switch (method) {
            case GET:
                service.get(URL, PARAMS);
                break;
            case POST:
                service.post(URL, PARAMS);
                break;
            case PUT:
                service.put(URL, PARAMS);
                break;
            case DELETE:
                service.delete(URL, PARAMS);
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(getResponseCallback());
        }
    }

    private Callback<String> getResponseCallback() {
        return new RequestCallbacks(IREQUEST, ISUCCESS, IFAILURE, IERROR);
    }

    public final void get(){
        request(HttpMethod.GET);
    }
}
