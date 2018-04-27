package com.example.latte.net.intercepors;

import android.support.annotation.RawRes;

import com.example.latte.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 *
 * @author Tession
 * @date 2018/4/27
 */

public class DebugInterceptor extends BaseInterceptor {

    private final String DEBUG_URL;//模拟的url
    private final int DEBUG_RAW_ID; //测试时用本地的资源

    public DebugInterceptor(String debugUrl, int rawId) {
        DEBUG_URL = debugUrl;
        DEBUG_RAW_ID = rawId;
    }


    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                //假设成功
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                //http协议用1.1，因为2.0还没有普及
                .protocol(Protocol.HTTP_1_1)
                .build();
    }
    /**
     * @param chain
     * @param rawId 充分应用android自带的注解，表示必须是Raw里的资源
     * @return
     */
    private Response debugResponse(Chain chain, @RawRes int rawId) {
        final String json = FileUtil.getRawFile(rawId);
        return getResponse(chain, json);
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        //得到我们拦截的url
        final String url = chain.request().url().toString();
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID);
        }
        return chain.proceed(chain.request());
    }
}
