package com.leyunone.cloudcloud.exception;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/20 15:40
 */
public class ServiceException extends RuntimeException{
    
    private ResultCode code;
    
    public ServiceException(ResultCode code){
        this.code = code;
    }
}
