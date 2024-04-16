package com.leyunone.cloudcloud.exception;

import com.leyunone.cloudcloud.bean.DataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class RestExceptionAdvice {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ServiceException.class)
    public DataResponse<?> serviceException(ServiceException e, HttpServletResponse response){
        return DataResponse.buildFailure(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public DataResponse<?> handleOtherException(Exception e){
        e.printStackTrace();
        return DataResponse.buildFailure(e.getMessage());
    }

}
