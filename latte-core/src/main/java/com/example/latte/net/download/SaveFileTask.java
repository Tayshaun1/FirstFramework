package com.example.latte.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.latte.app.Latte;
import com.example.latte.net.callback.IRequest;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by Tession on 2018/4/26.
 */

public class SaveFileTask extends AsyncTask<Object, Void, File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    /**
     * 其他回调就没写了
     *
     * @param request
     * @param success
     */
    public SaveFileTask(IRequest request, ISuccess success) {
        REQUEST = request;
        SUCCESS = success;
    }

    @Override
    protected File doInBackground(Object... parms) {
        String downloadDir = (String) parms[0];
        String extension = (String) parms[1];
        //请求体
        final ResponseBody body = (ResponseBody) parms[2];
        final String name = (String) parms[3];
        final InputStream is = body.byteStream();
        if (TextUtils.isEmpty(downloadDir)) {
            downloadDir = "down_loads";
        }
        if (TextUtils.isEmpty(extension)) {
            extension = "";
        }
        if (name == null) {
            return FileUtil.writeToDisk(is, downloadDir, extension.toLowerCase(), extension);
        } else {
            //有一个完整的名字的话，我们直接创建（这里的name就必须是一个完整的文件名了）
            return FileUtil.writeToDisk(is, downloadDir, name);
        }
    }

    /**
     * 完成操作会到主线程的一些操作
     *
     * @param file
     */
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        //特殊处理，比如下载的是类似apk直接安装
        autoInstallApk(file);
    }

    /**
     * 自动安装
     *
     * @param file
     */
    private void autoInstallApk(File file) {
        //获取文件的后缀名字
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            //新启一个栈
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(install);
        }
    }
}
