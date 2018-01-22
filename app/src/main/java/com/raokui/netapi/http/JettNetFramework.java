package com.raokui.netapi.http;

/**
 * Created by 饶魁 on 2018/1/22.
 */

public class JettNetFramework {

    public static <T, M> void sendJsonRequest(String url, T requestInfo, Class<M> response, IDataListener<M> dataListener) {
        IHttpService httpService = new JsonHttpService();
        IHttpListener httpListener = new JsonHttpListener<>(response, dataListener);
        HttpTask<T> httpTask = new HttpTask<T>(url, requestInfo, httpService, httpListener);
        ThreadPoolManager.getInstance().execute(httpTask);
    }
}
