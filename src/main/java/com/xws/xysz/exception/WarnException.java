package com.xws.xysz.exception;

/**
 * 公用异常，如果抛出此异常必须捕获
 * 此异常只用来做业务流程控制作为用户提醒使用
 * Created by 杨光 on 2015/10/27.
 */
public class WarnException extends Exception {

    public WarnException(String msg){
        super(msg);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
