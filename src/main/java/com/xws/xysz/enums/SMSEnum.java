package com.xws.xysz.enums;

/**
 * 所有类别的等级枚举
 * Created by yangg on 2018/11/21.
 */
public class SMSEnum {

    /**
     * 短信类型
     */
    public enum SMS{
        注册("1001"),
        找回密码("1002"),
        额度批复("1003"),
        放款("1004"),
        还款("1005"),
        拒批额度("1006"),
        推新("1007");
        // 定义私有变量
        private String code;

        // 构造函数，枚举类型只能为私有
        private SMS(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public String getName(){
            return super.toString();
        }

    }
}
