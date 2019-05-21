//弹出层
function showDiv() {
    $(".background-div").fadeIn(function () {
        $(this).height($(document).height());
    })
}

function hideDiv() {
    $(".background-div").fadeOut(function () {
    })
}


//屏蔽页面错误

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


//字符串转日期格式，strDate要转为日期格式的字符串
function getDate(strDate) {
    var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
        function (a) {
            return parseInt(a, 10) - 1;
        }).match(/\d+/g) + ')');
    return date;
}


/**
 * 判断某一年是否是闰年
 * @param year 可以是一个date类型，也可以是一个int类型的年份，不传默认当前时间
 */
function isLeapYear(year) {
    if (year === undefined) year = new Date();
    if (year instanceof Date) year = year.getFullYear();
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
}

/**
 * 获取某一年某一月的总天数，没有任何参数时获取当前月份的
 * 方式一：$.getMonthDays();
 * 方式二：$.getMonthDays(new Date());
 * 方式三：$.getMonthDays(2013, 12);
 */
function getMonthDays(date, month) {
    var y, m;
    if (date == undefined) date = new Date();
    if (date instanceof Date) {
        y = date.getFullYear();
        m = date.getMonth();
    } else if (typeof date == 'number') {
        y = date;
        m = month - 1;
    }
    var days = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]; // 非闰年的一年中每个月份的天数
    //如果是闰年并且是2月
    if (m == 1 && isLeapYear(y)) return days[m] + 1;
    return days[m];
}


/**
 * 获得传进来日期月份1号是周几
 */
function getMonthFirstWeek(date) {
    var nd = new Date(date.Format("MM/dd/yyyy"));
    nd.setDate(1);
    return nd.getDay();
}


//字符串转换为 金额显示模式
function strFormatMoney(s, isInt) {
    s = s + "";
    if (/[^0-9\.]/.test(s)) return "invalid value";
    s = s.replace(/^(\d*)$/, "$1.");
    s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
    s = s.replace(".", ",");
    var re = /(\d)(\d{3},)/;
    while (re.test(s)) {
        s = s.replace(re, "$1,$2");
    }
    s = s.replace(/,(\d\d)$/, ".$1");
    s = s.replace(/^\./, "0.");
    if (isInt != null && isInt != "undefined" && isInt == true) {
        //alert()
        s = s.substring(0, s.indexOf("."));
    }
    return s;
};

//trim
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.ltrim = function () {
    return this.replace(/(^\s*)/g, "");
}
String.prototype.rtrim = function () {
    return this.replace(/(\s*$)/g, "");
}

//replaceALL
String.prototype.replaceAll = function (f, e) {//吧f替换成e
    var reg = new RegExp(f, "g"); //创建正则RegExp对象
    return this.replace(reg, e);
}


//获得URL参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}


//========Cookie操作相关========
//设置Cookie,天为单位
//使用方法：setCookie('user', 'simon', 11)
function setCookie(name, value, iDay) {
    var oDate = new Date();
    oDate.setDate(oDate.getDate() + iDay);
    document.cookie = name + '=' + value + ';expires=' + oDate;
};

//获取cookie
//使用方法：getCookie('user')
function getCookie(name) {
    var arr = document.cookie.split('; '); //多个cookie值是以; 分隔的，用split把cookie分割开并赋值给数组
    for (var i = 0; i < arr[i].length; i++) //历遍数组
    {
        var arr2 = arr[i].split('='); //原来割好的数组是：user=simon，再用split('=')分割成：user simon 这样可以通过arr2[0] arr2[1]来分别获取user和simon
        if (arr2[0] == name) //如果数组的属性名等于传进来的name
        {
            return arr2[1]; //就返回属性名对应的值
        }
        return ''; //没找到就返回空
    }
};

// 删除cookie
//使用方法：removeCookie('user')
function removeCookie(name) {
    setCookie(name, 1, -1); //-1就是告诉系统已经过期，系统就会立刻去删除cookie
};


//图片压缩,targetWidth:想要的压缩后宽度   targetSize:想要的压缩后图片大小，单位kb
function dealImage(base64, targetWidth, targetSize, callback) {
    var newImage = new Image();
    var quality = 1;    //压缩系数0-1之间
    newImage.src = base64;
    newImage.setAttribute("crossOrigin", 'Anonymous');	//url为外域时需要
    var imgWidth, imgHeight;
    newImage.onload = function () {
        imgWidth = this.width;
        imgHeight = this.height;
        var canvas = document.createElement("canvas");
        var ctx = canvas.getContext("2d");
        if (Math.max(imgWidth, imgHeight) > targetWidth) {
            if (imgWidth > imgHeight) {
                canvas.width = targetWidth;
                canvas.height = targetWidth * imgHeight / imgWidth;
            } else {
                canvas.height = targetWidth;
                canvas.width = targetWidth * imgWidth / imgHeight;
            }
        } else {
            canvas.width = imgWidth;
            canvas.height = imgHeight;
        }
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.drawImage(this, 0, 0, canvas.width, canvas.height);
        var base64 = canvas.toDataURL("image/jpeg", quality); //压缩语句
        // 如想确保图片压缩到自己想要的尺寸,如要求在50-150kb之间，请加以下语句，quality初始值根据情况自定
        while (base64.length / 1024 > targetSize) {
            quality -= 0.01;
            base64 = canvas.toDataURL("image/jpeg", quality);
        }
        callback(base64);//必须通过回调函数返回，否则无法及时拿到该值
    }
}

function textNum(obj){
    obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
    obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
}

//处理url，将query和当前url地址组合处理
function URlHandle(query){
    var url = window.location.host;
    url = url+query;
    url = url.replaceAll("//","/");
    url = document.location.protocol+"//"+url;
    return url;
}


//点击查看大图-layer
$(function () {
    var dataImg = $("[data-img]");
    dataImg.attr("title","点击查看原图");
    dataImg.css("cursor","pointer");

    dataImg.click(function () {
        var url = $(this).data("img");
        layer.photos({
            photos: {
                "title": "查看图片", //相册标题
                "id": 1, //相册id
                "start": 0, //初始显示的图片序号，默认0
                "data": [   //相册包含的图片，数组格式
                    {
                        "alt": "图片",
                        "pid": 1, //图片id
                        "src": url, //原图地址
                        "thumb": url //缩略图地址
                    }
                ]
            }
            ,anim: 5
        });
    })
})

