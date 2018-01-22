package com.raokui.netapi.http;

import java.io.InputStream;

/**
 * 处理响应结果
 * Created by 饶魁 on 2018/1/22.
 */

public interface IHttpListener {

    void onSuccess(InputStream inputStream);

    void onFailure();

}
