package com.cbs.cbs.model;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Response", description = "Endpoint response")
public class Response<T, K> {
    private T data;
    private K error;
    public  Response(T data, K error){
        this.data = data;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public K getError() {
        return error;
    }
}
