package com.raokui.netapi.http;

/**
 * 处理请求
 * Created by 饶魁 on 2018/1/22.
 */

public interface IHttpService {

    void setUrl(String url);

    void setRequestData(byte[] requestData);

    void execute();

    // 设置两个接口关系
    void setHttpCallBack(IHttpListener httpListener);

}
