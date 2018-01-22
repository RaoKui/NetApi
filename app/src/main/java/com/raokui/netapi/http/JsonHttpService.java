package com.raokui.netapi.http;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 饶魁 on 2018/1/22.
 */

public class JsonHttpService implements IHttpService {

    private String url;
    private byte[] requestData;
    private IHttpListener httpListener;

    private HttpURLConnection urlConnection = null;

    @Override
    public void execute() {
        // 进行网络的真实操作
        httpUrlConnPost();

    }

    private void httpUrlConnPost() {
        URL url = null;
        try {
            url = new URL(this.url);
            urlConnection = (HttpURLConnection) url.openConnection();// 打开http连接
            urlConnection.setConnectTimeout(6000);
            urlConnection.setUseCaches(false);
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.setReadTimeout(3000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            urlConnection.connect();
            // 字节流发送数据
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            if (requestData != null) {
                bos.write(requestData);
            }
            bos.flush();
            outputStream.close();
            bos.close();

            // 字节流写入数据
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                httpListener.onSuccess(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
            httpListener.onFailure();
        } finally {
            urlConnection.disconnect();
        }
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
    }

    @Override
    public void setHttpCallBack(IHttpListener httpListener) {
        this.httpListener = httpListener;
    }
}
