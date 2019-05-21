package com.xws.xysz.enums;

/**
 * Created by 杨光 on 2018/5/14.
 */
public enum APICode {
    成功(0),
    系统错误(-1),//error级别，应该抛出errorException，让代码停止
    认证过期(-2),
    API格式参数错误(1),
    业务参数错误(2),//请求的参数不符合要求，抛出warnException提示用户信息
    无权限(3);

    // 定义私有变量
    private int code;

    // 构造函数，枚举类型只能为私有
    private APICode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getName(){
        return super.toString();
    }


}