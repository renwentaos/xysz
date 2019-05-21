(function (factory) {
    if (typeof define === "function" && define.amd) {
        define(["jquery", "../jquery.validate"], factory);
    } else if (typeof module === "object" && module.exports) {
        module.exports = factory(require("jquery"));
    } else {
        factory(jQuery);
    }
}(function ($) {

    /*
     * Translated default messages for the jQuery validation plugin.
     * Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
     */
    $.extend($.validator.messages, {
        required: "${name}不能为空",
        remote: "请修正此字段",
        email: "${name}的邮件格式不正确",
        url: "请输入有效的网址",
        date: "请输入有效的日期",
        dateISO: "请输入有效的日期 (YYYY-MM-DD)",
        number: "请输入有效的数字",
        digits: "${name}必须是整数",
        creditcard: "请输入有效的信用卡号码",
        equalTo: "两次输入的密码不一致",
        extension: "请输入有效的后缀",
        maxlength: $.validator.format("${name}的长度最多是 {0} 个字符"),
        minlength: $.validator.format("${name}的长度最少是 {0} 个字符"),
        rangelength: $.validator.format("${name}的长度必须是 {0} 到 {1} 个字符"),
        range: $.validator.format("请输入范围在 {0} 到 {1} 之间的数值"),
        max: $.validator.format("${name}的值不能高于 {0} "),
        min: $.validator.format("${name}的值不能低于 {0} ")
    });

    $.validator.setDefaults({
        showErrors: function (errorMap, errorList) {
            if (errorList.length > 0) {
                var error = errorList[0];
                $(error.element).focus();
                var msg = error.message.replace("${name}", $(error.element).data("fieldname"))
                layer.msg(msg);
                return false;
            } else {
                return true;
            }
        },
        onfocusout: false,
        onkeyup: false,
        onclick: false
    });
    return $;
}));


//大于
jQuery.validator.addMethod("gt", function (value, element, param) {
    var target = $(param[0]);
    console.info(target)
    console.info(Number(target.val()))
    return Number(value) > Number(target.val());

}, $.validator.format("${name}必须大于{1}"));
//小于
jQuery.validator.addMethod("lt", function (value, element, param) {
    console.info(value)
    console.info(element)
    console.info(param)
    var target = $(param[0]);
    var max = Number(target.val());
    if (max == 0)
        return true;
    return Number(value) < Number(target.val());
}, $.validator.format("${name}必须小于{1}"));