package com.linxz.doexcel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @auther ChillyLin
 * @date 2020/6/17
 * <p>
 * 自定义错误，用于404
 */

@ResponseStatus(HttpStatus.NOT_FOUND)  //将此类归类为资源找不到
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
