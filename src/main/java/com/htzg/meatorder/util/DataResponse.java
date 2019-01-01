package com.htzg.meatorder.util;

/**
 * Created by jby on 2018/12/30.
 */
public class DataResponse<T> {

    public static Integer SUCCESS = 0;
    public static Integer FAILURE = 1;
    public static Integer PARTSUCCESS = 2;

    private Integer code;

    private String message;

    private T data;

    public static <T> DataResponse<T> success(String message){
        return new DataResponse<>(SUCCESS, message, null);
    }

    public static <T> DataResponse<T> success(T data){
        return new DataResponse<>(SUCCESS, "", data);
    }

    public static <T> DataResponse<T> success(String message, T data){
        return new DataResponse<>(SUCCESS, message, data);
    }

    public static <T> DataResponse<T> failure(String message){
        return new DataResponse<>(FAILURE, message, null);
    }

    public static <T> DataResponse<T> failure(T data){
        return new DataResponse<>(FAILURE, "", data);
    }

    public static <T> DataResponse<T> failure(String message, T data){
        return new DataResponse<>(FAILURE, message, data);
    }

    public DataResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
