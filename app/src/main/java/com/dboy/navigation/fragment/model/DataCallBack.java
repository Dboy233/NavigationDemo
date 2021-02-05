package com.dboy.navigation.fragment.model;

/**
 * @author DBoy
 * @date 2021/2/5
 * Class 描述 : 模拟数据回调
 */
public interface DataCallBack<T> {

    void success(T data);

    void failure(String message);

}
