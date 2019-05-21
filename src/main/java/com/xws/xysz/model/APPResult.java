package com.xws.xysz.model;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by 杨光 on 2018/4/26.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class APPResult<T> {
    private static final Gson gson = new Gson();

    private int responseCode = 0;//返回码,默认成功
    private String errorMsg;//错误时错误信息描述
    private String token;//新的token
    private T result;//返回对象


    public APPResult(int responseCode, String errorMsg){
        this.responseCode = responseCode;
        this.errorMsg = errorMsg;
    }

    /**
     * 将当前实体转为Json
     * @return
     */
    public String toJson(){
        return gson.toJson(this);
    }


    /**
     * 从json转为APIResult
     * @param json json字符串
     * @param resultClazz APIResult实体中result属性想要转换的实体类型
     */
    public static APPResult fromJson(String json, Class resultClazz){
        return gson.fromJson(json, type(APPResult.class,resultClazz));
    }

    private static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}