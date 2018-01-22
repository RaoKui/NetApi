package com.raokui.netapi.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by 饶魁 on 2018/1/22.
 */

public class JsonHttpListener<M> implements IHttpListener {

    private Class<M> responseClass;
    private IDataListener<M> dataListener;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public JsonHttpListener(Class<M> responseClass, IDataListener<M> dataListener) {
        this.responseClass = responseClass;
        this.dataListener = dataListener;

    }

    @Override
    public void onFailure() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                dataListener.onFailure();
            }
        });
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        String content = getContent(inputStream);

        final M response = JSON.parseObject(content, responseClass);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                dataListener.onSuccess(response);
            }
        });
    }

    private String getContent(InputStream inputStream) {
        String content = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                System.out.println("Error=" + e.toString());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println("Error=" + e.toString());
                }

            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }
}
