package com.example.latte.net.download;

import android.os.AsyncTask;

import com.example.latte.net.RestCreator;
import com.example.latte.net.callback.IError;
import com.example.latte.net.callback.IFailure;
import com.example.latte.net.callback.IRequest;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.util.file.FileUtil;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Tession on 2018/4/26.
 */

public class DownloadHandler {

    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final IFailure IFAILURE;
    private final IError IERROR;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;

    public DownloadHandler(String url, IRequest irequest, ISuccess isuccess, IFailure ifailure, IError ierror, String download_dir, String
            extension, String name) {
        URL = url;
        IREQUEST = irequest;
        ISUCCESS = isuccess;
        IFAILURE = ifailure;
        IERROR = ierror;
        DOWNLOAD_DIR = download_dir;
        EXTENSION = extension;
        NAME = name;
    }

    /**
     * 处理download
     */
    public final void handleDownload() {
        if (IREQUEST != null) {
            //开始下载
            IREQUEST.onRequestStart();
        }
        //因为download加了@Streaming，表示边下载边写入，我们可以写一个工具来辅助
        RestCreator.getRestService().download(URL, PARAMS).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    final ResponseBody responseBody = response.body();
                    final SaveFileTask task = new SaveFileTask(IREQUEST, ISUCCESS);
                    //这里以线程池的形式执行，也可以用队列的形式执行
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, responseBody, NAME);

                    //这里一定要注意判断，否则文件下载不全
                    if (task.isCancelled()) {
                        if (IREQUEST != null) {
                            IREQUEST.onRequestEnd();
                        }
                    }
                } else {
                    if (IERROR != null) {
                        IERROR.onError(response.code(), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (IFAILURE != null) {
                    IFAILURE.onFailure();
                }
            }
        });
    }
}
