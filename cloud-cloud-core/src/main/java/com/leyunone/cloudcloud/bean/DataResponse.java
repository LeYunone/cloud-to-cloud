package com.leyunone.cloudcloud.bean;

import com.leyunone.cloudcloud.exception.ResultCode;
import lombok.*;

import java.io.Serializable;

/**
 * @author LeYunone
 * @create 2021-08-10 15:05
 *   一般响应结果集
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class DataResponse<T> implements Serializable {

    private T result;

    private boolean success;

    private String message;

    private Integer code;

    public DataResponse() {
    }

    public static <T> DataResponse<T> of() {
        DataResponse<T> response = new DataResponse<>();
        response.setSuccess(true);
        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMessage(ResultCode.SUCCESS.getDesc());
        return response;
    }

    public static <T> DataResponse<T> of(T data) {
        DataResponse<T> response = new DataResponse<>();
        response.setSuccess(true);
        response.setResult(data);
        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMessage(ResultCode.SUCCESS.getDesc());
        return response;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static <T> DataResponse<T> of(boolean status, ResultCode responseCode, T data) {
        DataResponse<T> response = new DataResponse<>();
        response.setSuccess(status);
        response.setResult(data);
        response.setCode(responseCode.getCode());
        response.setMessage(responseCode.getDesc());
        return response;
    }

    public static DataResponse buildFailure(ResultCode responseCode) {
        return of(false, responseCode, (Object)null);
    }

    public static DataResponse buildFailure(String message) {
        DataResponse response = of(false, ResultCode.ERROR, (Object)null);
        response.setMessage(message);
        return response;
    }

    public static DataResponse buildFailure(){
        DataResponse response = new DataResponse();
        response.setSuccess(false);
        response.setCode(ResultCode.ERROR.getCode());
        response.setMessage(ResultCode.ERROR.getDesc());
        return response;
    }

    public static DataResponse buildSuccess() {
        DataResponse response = new DataResponse();
        response.setSuccess(true);
        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMessage(ResultCode.SUCCESS.getDesc());
        return response;
    }


}
