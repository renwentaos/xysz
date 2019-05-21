package com.xws.xysz.enums;

/**
 * 所有类别的状态枚举
 * Created by yangg on 2018/11/21.
 */
public class StateEnum {

    /**
     * 基础
     */
    public enum Basic implements EnumInterface{
        正常(0),
        冻结(-1);
        // 定义私有变量
        private Integer code;

        // 构造函数，枚举类型只能为私有
        private Basic(Integer code) {
            this.code = code;
        }
        public Integer getCode() {
            return code;
        }
        public String getName(){
            return super.toString();
        }

    }


    /**
     * 个人信息相关
     */
    public enum Info implements EnumInterface {
        已驳回(-1),
        未提交(0),
        审核中(1),
        已完成(2);
        // 定义私有变量
        private Integer code;

        // 构造函数，枚举类型只能为私有
        private Info(Integer code) {
            this.code = code;
        }
        public Integer getCode() {
            return code;
        }
        public String getName(){
            return super.toString();
        }

    }




}
