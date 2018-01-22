package com.raokui.netapi.http;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

/**
 * Created by 饶魁 on 2018/1/22.
 */

public class HttpTask<T> implements Runnable {
    private IHttpService httpService;
    private IHttpListener httpListener;

    public <T> HttpTask(String url, T requestInfo, IHttpService httpService, IHttpListener httpListener) {
        this.httpService = httpService;
        this.httpListener = httpListener;
        httpService.setUrl(url);
        httpService.setHttpCallBack(httpListener);

        // 把请求信息的对象装换成Json格式
        String requestContent = JSON.toJSONString(requestInfo);
        if (requestInfo != null) {
            try {
                httpService.setRequestData(requestContent.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        httpService.execute();
    }
}
