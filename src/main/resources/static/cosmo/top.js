$(document).ready(function () {
    $("a[name='logout']").click(function (event) {
        event.preventDefault();
        var href = $(this).attr("href")
        layer.confirm('确认登出账号？', {
            btn: ['确认', '取消'] //按钮
        }, function () {
            window.location.href = href;
        });
    })
})
var loaderData;

function timeLoader(loadder, showElement, stepTime, endTime, focusElement, nowTime) {
    if (!nowTime || nowTime == 0) {
        nowTime = 0;
        loadder.show();
        loaderData = null;
        showElement.hide();
    }
    if (nowTime >= endTime && loaderData) {
        loadder.hide();
        showElement.fadeIn();
        if (focusElement) {
            focusElement.focus();
        }
    } else {
        nowTime = nowTime + stepTime;
        setTimeout(function () {
            timeLoader(loadder, showElement, stepTime, endTime, focusElement, nowTime);
        }, stepTime);
    }
}
