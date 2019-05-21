//闭包限定命名空间
(function ($) {
    $.fn.extend({
        "myPages": function (options) {
            var opts = $.extend({}, defaluts, options);
            this.each(function () {
                var url = opts.url;
                var pageNo = opts.pageNo
                var pageSize = opts.pageSize
                var firstPage = opts.firstPage
                var lastPage = opts.lastPage
                var preText = opts.preText
                var nextText = opts.nextText
                var firstText = opts.firstText
                var lastText = opts.lastText
                var showPageButton = opts.showPageButton
                url = url.replaceAll("{pageSize}", pageSize);
                var prePage = pageNo == firstPage ? pageNo : pageNo - 1;
                var nextPage = pageNo == lastPage ? pageNo : pageNo + 1;


                var str=location.href; //取得整个地址栏
                var num = str.indexOf("?");
                if(num > -1){
                    var params = str.substring(num);
                    url = url + params;
                }

                var isMobile = false;
                var width = $(this).parent().width();//(window.innerWidth > 0) ? window.innerWidth : screen.width;

                if (width < 576) {
                    isMobile = true;
                }


                //生成HTML
                var html = "<nav><ul class='myPages'>";
                if (!isMobile) {
                    html += "<li><a href='" + url.replaceAll("{pageNo}", firstPage) + "'> " + firstText + " </a></li>";
                }
                if (pageNo == firstPage) {
                    //html += "<li  class='disabled'><a class='noClickPage' href='javascript:void(0)'>上一页</a></li>";
                } else {
                    html += "<li><a href='" + url.replaceAll("{pageNo}", prePage) + "'>" + preText + "</a></li>";
                }
                if (!isMobile) {
                    //计算循环
                    var temp = showPageButton / 2;
                    var begin = firstPage;
                    var end = pageNo + temp;
                    if (end > lastPage) {
                        end = lastPage;
                    }
                    if (pageNo - temp > firstPage) {
                        begin = pageNo - temp;
                    }
                    if (end - showPageButton > firstPage && end - begin < showPageButton) {
                        begin = end - showPageButton;
                    }

                    if (end - begin < showPageButton && begin + showPageButton < lastPage) {
                        end = begin + showPageButton;
                    }

                    var counts = 0;
                    for (var i = begin; i <= end; i++) {
                        counts++;
                        if (i == pageNo) {
                            html += "<li  class='active'><a class='noClickPage' href='javascript:void(0)'>" + pageNo + "</a></li>";
                        } else {

                            html += "<li><a href='" + url.replaceAll("{pageNo}", i) + "'>" + i + "</a></li>";
                        }
                    }
                }
                if (pageNo == lastPage) {
                    //html += "<li class='disabled'><a class='noClickPage' href='javascript:void(0)'>下一页</a></li>";
                } else {
                    html += "<li><a href='" + url.replaceAll("{pageNo}", nextPage) + "'>" + nextText + "</a></li>";
                }
                if (!isMobile) {
                    html += "<li><a href='" + url.replaceAll("{pageNo}", lastPage) + "'> " + lastText + " </a></li>";
                    html +="<li><input id='txtQuick' type='text' value='"+pageNo+"'><a id='btnQuick' href='javascript:void(0)'>跳转</a></li>";
                }
                html += "</ul></nav>";

                $(this).append(html);
                $(this).addClass("myPages");

                $("#txtQuick").focus(function () {
                    $(this).select();
                }).keyup(function (event) {
                    if(event.keyCode == 13 || event.keyCode == 108){
                        $("#btnQuick").click();
                    }else{
                        var val = $(this).val();
                        if(val.length == 1) {
                            val = val.replace(/[^1-9]/g, '')
                        } else {
                            val = val.replace(/\D/g, '')
                        }
                        if(val.length == 0){
                            val = 1;
                        }else if(val < 1){
                            val = 1;
                        }else if(val > lastPage){
                            val = lastPage;
                        }
                        $(this).val(val);
                    }
                })

                $("#btnQuick").click(function(){
                    window.location.href = url.replaceAll("{pageNo}",$("#txtQuick").val());
                })
            });

        }
    });
    //默认参数
    var defaluts = {
        pageNo: 1,
        pageSize: 10,
        firstPage: 1,
        lastPage: 1,
        preText: "&lt;",
        nextText: "&gt;",
        firstText: "«",
        lastText: "»",
        url: "/{pageNo}-{pageSize}",
        showPageButton: 6
    };
})(window.jQuery);
String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}