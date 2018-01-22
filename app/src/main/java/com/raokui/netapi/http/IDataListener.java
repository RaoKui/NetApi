package com.raokui.netapi.http;

/**
 * Created by 饶魁 on 2018/1/22.
 */

public interface IDataListener<M> {

    void onSuccess(M m);

    void onFailure();

}
