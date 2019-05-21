var memberId,applyId;
var isInitMap = false;
var isRunMap = false;
var travelData, myIcon, map;
var travelIndex = 0;
$(function () {
    memberId = $("#memberId").val();
    applyId = $("#applyId").val();
    loadLoanPre();
    loadContacts();
    loadYYS();
    loadDS();

    var nowStepIndex = 0;
    var isSwitchBody = false;
    var stepLength = $("#tabStep li").length;
    $("button[name='btnNextStep']").click(function () {
        switchTabs(nowStepIndex + 1);
    })

    $("button[name='btnPrevStep']").click(function () {
        switchTabs(nowStepIndex - 1);
    })

    $("li[name='tabStep']").click(function () {
        if ($(this).data("switch") != true) {
            return;
        }
        var index = $("#tabStep li").index(this);
        switchTabs(index);
    })

    $("button[name='btnQuickReject']").click(function () {
        layer.prompt({title: '请输入拒批的原因', formType: 2}, function (text, index) {
            formSubmit("reject", text);
        });
    })

    $("button[name='btnTop']").click(function () {
        $("html,body").animate({scrollTop:0},500);
    })

    function switchTabs(targetIndex) {
        if (nowStepIndex == targetIndex) {
            return;
        } else if (isSwitchBody) {
            layer.msg("请不要点击太快");
            return;
        } else if (targetIndex < 0) {
            layer.msg("已经是第1步了");
            return;
        } else if (targetIndex > stepLength - 1) {
            layer.msg("已经是最终步骤了");
            return;
        }
        isSwitchBody = true;
        var tabSteps = $("li[name='tabStep']");
        var nowStepTab = tabSteps.eq(nowStepIndex);
        if (targetIndex > nowStepIndex) {
            nowStepTab.removeClass("text-primary border-primary").addClass("text-success border-success");
            for (var i = nowStepIndex + 1; i < targetIndex; i++) {
                tabSteps.eq(i).addClass("text-success border-success");
            }
        } else {
            nowStepTab.removeClass("text-primary border-primary");
            for (var i = nowStepIndex + 1; i >= targetIndex; i--) {
                tabSteps.eq(i).removeClass("text-success border-success");
            }
        }
        nowStepTab = tabSteps.eq(targetIndex);
        nowStepTab.addClass("text-primary border-primary");
        nowStepTab[0].scrollIntoView(false);

        var nowStepBody = $("div[name='bodyStep']").eq(nowStepIndex)
        nowStepBody.fadeOut(100, function () {
            if (nowStepBody.attr("id") == "travelBody") {
                endMap();
            }
            nowStepBody = $("div[name='bodyStep']").eq(targetIndex);
            nowStepBody.fadeIn(200, function () {
                isSwitchBody = false;
                if (nowStepBody.attr("id") == "travelBody") {
                    if (isInitMap == false) {
                        loadBDMap();
                    }
                    beginMap();
                }
                if (nowStepTab.data("switch") != true) {
                    nowStepTab.data("switch", true);
                    nowStepTab.css("cursor", "pointer");
                }
                nowStepIndex = targetIndex;

            });
        });
    }

    var headerHeight = $("header").innerHeight();
    var stepParent = $("#tabStep").parent();
    stepParent.css("position", "sticky");
    stepParent.css("top", headerHeight);
    stepParent.css("z-index", "998");
    stepParent.css("background", "#fff");

    var relationId;

    $(document).on("click","a[name='btnContactRemark']",function () {
        relationId = $(this).data("id");
        $("#contactRemarkModal input[type='radio']").prop('checked',false);
        $("#contactRemarkModal").modal("show");
    })


    $("#btnSaveContactsRemark").click(function () {
        var th = $("input[name='radioTh']:checked").val();
        var gx = $("input[name='radioRelation']:checked").val();
        var relation = null;
        if (typeof(th) != "undefined"){
            relation = th;
        }else{
            layer.msg('请确认是否打通电话？');
            return false;
        }
        if(typeof(gx) != "undefined"){
            relation+="-"+gx;
        }else{
            layer.msg('请确认关系');
            return false;
        }
        if(relation != null){
            $.ajax({
                type:'post',
                url:'/merchant/apply/contacts/mark',
                data:{
                    relationId:relationId,
                    memberId:memberId,
                    isCall:th,
                    relation:gx
                },
                dataType:'text',
                success:function (data) {
                    if(data == null || data.length == 0){
                       layer.msg("成功标记");
                    }else{
                        layer.msg(data);
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    layer.alert("出错了，请联系管理员");
                    console.info(jqXHR,textStatus,errorThrown);
                }
            })
            $("#contactRemarkModal").modal("hide");
            $("#relations"+relationId).text(relation);
            $("#relations"+relationId).removeAttr("style");


        }
    })

})


function loadLoanPre() {
    var shixinCourts, zhixingCourts, jieanCourts;
    $.post("/merchant/bd/before", {
        "memberId": memberId
    }, function (data) {
        if(data == ''){
            layer.msg("此客户已不属于该商户，无法查看第三方风控数据");
            return;
        }
        var tmpl = $.templates("#loanPreTmpl");
        var html = tmpl.render(data);
        $("#loanPreBody").html(html);
        shixinCourts = data["shiXinCourts"];
        zhixingCourts = data["zhiXingCourts"];
        jieanCourts = data["jieAnCourts"];
        $("a[name='btnCourt']").on("click", function () {
            var type = $(this).data("type");
            var index = $(this).data("index");
            var court;
            if (type == "shixin") {
                court = shixinCourts[index];
            } else if (type == "zhixing") {
                court = zhixingCourts[index];
            } else if (type == "jiean") {
                court = jieanCourts[index];
            } else {
                return;
            }
            var htm = '<div style="padding:30px; line-height: 30px; background-color: #393D49; color: #fff;">';
            htm += "<b style='color:#ccc'>案号：</b>" + court["caseCode"];
            htm += "<br><b style='color:#ccc'>执行法院：</b>" + court["executeCourt"];
            htm += "<br><b style='color:#ccc'>立案时间：</b>" + court["caseDate"];
            htm += "<br><b style='color:#ccc'>被执行人：</b>" + court["executedName"]
            if (court["gender"] != null && court["gender"].length > 0) {
                htm += "-" + court["gender"];
            }
            if (court["age"] != null && court["age"].length > 0) {
                htm += "-" + court["age"] + "岁";
            }
            if (court["executeSubject"] != null && court["executeSubject"].length > 0) {
                htm += "<br><b style='color:#ccc'>执行标的：</b>" + court["executeSubject"];
            }
            if (court["executeStatus"] != null && court["executeStatus"].length > 0) {
                htm += "<br><b style='color:#ccc'>执行状态：</b>" + court["executeStatus"];
            }
            if (court["termDuty"] != null && court["termDuty"].length > 0) {
                htm += "<br><b style='color:#ccc'>生效法律文书确定的义务：</b>" + court["termDuty"];
            }
            if (court["executeCode"] != null && court["executeCode"].length > 0) {
                htm += "<br><b style='color:#ccc'>执行依据文号：</b>" + court["executeCode"];
            }
            if (court["evidenceCourt"] != null && court["evidenceCourt"].length > 0) {
                htm += "<br><b style='color:#ccc'>做出依据执行法院：</b>" + court["evidenceCourt"];
            }
            if (court["carryOut"] != null && court["carryOut"].length > 0) {
                htm += "<br><b style='color:#ccc'>被执行人履行情况：</b>" + court["carryOut"];
            }
            if (court["specificCircumstances"] != null && court["specificCircumstances"].length > 0) {
                htm += "<br><b style='color:#ccc'>被执行人行为具体情形：</b>" + court["specificCircumstances"];
            }

            htm += "</div>";

            layer.open({
                type: 1
                , title: false //不显示标题栏
                , closeBtn: false
                , area: '450px;'
                , shade: 0.8
                , id: 'LAY_layuipro' //设定一个id，防止重复弹出
                , resize: false
                , btn: ['关闭']
                , btnAlign: 'c'
                , moveType: 1 //拖拽模式，0或者1
                , shadeClose: true
                , content: htm

            });

        })
    });


}

function loadContacts() {
    $.post("/merchant/bd/contacts", {
        "memberId": memberId
    }, function (data) {
        //通讯录
        if(data == ''){
            layer.msg("此客户已不属于该商户，无法查看通讯录数据");
            return;
        }
        var tmpl = $.templates("#contactTmpl");
        var da = new Object();
        da["contacts"] = data;
        var html = tmpl.render(da);
        $("#contactBody").html(html);
        $('[data-toggle="tooltip"]').tooltip();

    });
}

function loadYYS() {
    $.post("/merchant/bd/yys", {
        "memberId": memberId
    }, function (data) {
        //运营商
        if(data == ''){
            layer.msg("此客户已不属于该商户，无法查看运营商数据");
            return;
        }
        var jj1 = $("#jj1").data("mobile");
        var jj2 = $("#jj2").data("mobile");
        data["jj"] = new Array();
        for(var i = 0;i<data.mhYysContactList.length;i++){
            var item = data.mhYysContactList[i];
            if(item.contactMobile == jj1 || item.contactMobile == jj2){
                data["jj"].push(item);
                if(data["jj"].length == 2){
                    break;
                }
            }
        }
        var tmpl = $.templates("#yysTmpl");
        var html = tmpl.render(data);
        $("#yysBody").html(html);
        yysJSLoad(data);

        //出行记录
        var travelTmpl = $.templates("#travelTmpl");
        var travelHtml = travelTmpl.render(data);
        $("#travelBody").html(travelHtml);
        travelData = data["mhYysTravelList"];
    });
}

function loadBDMap() {
    map = new BMap.Map("mapDiv");
    map.centerAndZoom(new BMap.Point(116.331398, 39.897445), 7);
    myIcon = new BMap.Icon("/img/personal.png", new BMap.Size(32, 32), {    //小车图片
        // offset: new BMap.Size(0, -5),    //相当于CSS精灵
        imageOffset: new BMap.Size(0, 0)    //图片的偏移量。为了是图片底部中心对准坐标点。
    });
}

function beginMap() {
    if (travelData != null && travelData.length > 0) {
        travelIndex = 0;
        isRunMap = true;
        BDMapPath();
    }
}

function endMap() {
    isRunMap = false;
}

function BDMapPath() {
    var myGeo = new BMap.Geocoder();
    myGeo.getPoint(travelData[travelIndex]["leaveCity"], function (point) {
        if (point) {
            var start = point;
            myGeo.getPoint(travelData[travelIndex]["arriveCity"], function (point) {
                if (point) {
                    var end = point;
                    var drivingShow = new BMap.DrivingRoute(map, {renderOptions: {map: map, autoViewport: true}});
                    drivingShow.search(start, end);
                    window.run = function () {
                        var driving = new BMap.DrivingRoute(map);    //驾车实例
                        driving.search(start, end);
                        driving.setSearchCompleteCallback(function () {
                            var pts = driving.getResults().getPlan(0).getRoute(0).getPath();    //通过驾车实例，获得一系列点的数组
                            var paths = pts.length;    //获得有几个点
                            var carMk = new BMap.Marker(pts[0], {icon: myIcon});
                            map.addOverlay(carMk);
                            i = 0;
                            var position = 5;

                            function resetMkPoint(i) {
                                if (isRunMap == false) {
                                    map.removeOverlay(carMk)
                                    drivingShow.clearResults();
                                    return;
                                }
                                carMk.setPosition(pts[i]);
                                if (i < paths) {
                                    setTimeout(function () {
                                        i = i + position;
                                        resetMkPoint(i);
                                    }, 1);
                                } else {
                                    if (travelIndex + 1 < travelData.length) {
                                        map.removeOverlay(carMk)
                                        drivingShow.clearResults();
                                        travelIndex++;
                                        BDMapPath();
                                    }
                                }
                            }


                            setTimeout(function () {
                                resetMkPoint(position);
                            }, 1)

                        });
                    }

                    setTimeout(function () {
                        run();
                    }, 300);
                }
            }, travelData[travelIndex]["leaveCity"])
        }
    }, travelData[travelIndex]["leaveCity"])


}

function loadDS() {
    var dsBody = $("#dsBody");
    if (dsBody.length == 0) {
        return;
    }
    $.post("/merchant/bd/ds", {
        "memberId": memberId,
        "jd": dsBody.data("jd") == 1 ? true : false,
        "tb": dsBody.data("tb") == 1 ? true : false
    }, function (data) {
        if(data == ''){
            layer.msg("此客户已不属于该商户，无法查看电商数据");
            return;
        }

        var tmpl = $.templates("#dsTmpl");
        var html = tmpl.render(data);
        dsBody.html(html);
    });
}

var allContactList;

function yysJSLoad(data) {
    // totalScoreChart(data.totalScore);
    // scoreChart(data);
    contactBlackListChart(data);
    monthCallCountStatsChart(data.mhYysMonthStatsList);
    monthCallTimeStatsChart(data.mhYysMonthStatsList);
    monthMoneyStatsChart(data.mhYysMonthStatsList);
    allContactList = data.mhYysContactList;
    Pagination.init($("#htPage"), loadYYSContactData);
    loadYYSContactData(0);
}

var color = {
    info: "#79B2EB",
    warning: "#FBB357",
    danger: "#EA585A",
    success: "#91D66B",
    primary: "#4B89FC",
    light: "#f8f9fa",
    white: "#ffffff"
}

function getColorByScore(score) {
    if (score >= 60) {
        return color.success;
    } else if (score >= 40 && score < 60) {
        return color.info;
    } else if (score >= 20 && score < 40) {
        return color.warning;
    } else {
        return color.danger;
    }
}

var clrHex = new Array('ff', '9d', '8c', 'ce', '7f', 'ab');

function getRandomColors(len) {
    var clrs = new Array();
    for (var i = 0; i < len; i++) {
        var color = "#";
        for (var j = 0; j < 3; j++) {
            color += clrHex[Math.floor(Math.random() * 6)]
        }
        clrs[i] = color;
    }
    return clrs;
}

function totalScoreChart(totalScore) {
    var ctx = document.getElementById("totalScoreChart").getContext('2d');
    var totalScoreColor = getColorByScore(totalScore);
    var totalScoreChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            datasets: [{
                data: [totalScore, 100 - totalScore],
                backgroundColor: [
                    totalScoreColor,
                    color.white,
                ]
            }]
        },
        options: {
            tooltips: {
                enabled: false
            },
            cutoutPercentage: 70,
            plugins: {
                doughnutlabel: {
                    labels: [
                        {
                            text: totalScore,
                            font: {
                                size: '40'
                            },
                            color: totalScoreColor
                        }
                    ]
                }
            }
        }
    });
}


function scoreChart(data) {
    var ctx = document.getElementById("scoreChart").getContext('2d');
    var scoreChart = new Chart(ctx, {
        type: 'polarArea',
        data: {
            datasets: [{
                data: [data.multapplyScore, data.callInfoScore, data.baseInfoScore, data.riskContactInfoScore, data.billInfoScore],
                backgroundColor: getRandomColors(5)
            }],
            labels: [
                '历史借贷行为',
                '通话行为',
                '基本信息',
                '联系人信息',
                '消费行为'
            ]
        },
        options: {
            scale: {
                display: false
            },
            legend: {
                position: "bottom",
                labels: {
                    usePointStyle: true
                }
            }
        }
    });
}

function contactBlackListChart(data) {
    var ctx = document.getElementById("contactBlackListChart").getContext('2d');
    var contactBlackListChart = new Chart(ctx, {
        type: 'bar',
        data: {
            datasets: [{
                data: [
                    data.blackTop10ContactCreditcrackCountRatio,
                    data.blackTop10ContactDiscreditrepayCountRatio,
                    data.blackTop10ContactStudentloansOverdueCountRatio,
                    data.blackTop10ContactCarloanBlacklistCountRatio,
                    data.blackTop10ContactPaymentfraudCountRatio,
                    data.blackTop10ContactScalpingCountRatio,
                    data.blackTop10ContactFakemobileCountRatio
                ],
                backgroundColor: getRandomColors(7)
            }],
            labels: [
                '信贷逾期',
                '逾期后还款',
                '助学贷款逾期',
                '车贷黑名单',
                '游戏消费欠费',
                '刷单',
                '虚假号码'
            ]
        },
        options: {
            title: {
                display: true,
                text: "黑名单人数占比(%)"
            },
            legend: {
                display: false
            }
        }
    });
}

function monthCallCountStatsChart(monthStatsList) {
    var ctx = document.getElementById("monthCallCountStatsChart").getContext('2d');
    var data = [
        {
            label: "主叫通话次数",
            data: [],
            backgroundColor: color.success
        },
        {
            label: "被叫通话次数",
            data: [],
            backgroundColor: color.info
        }
    ];
    var length = monthStatsList.length;
    var labels = new Array(length);
    for (var i = 0; i < length; i++) {
        var monthStats = monthStatsList[i];
        labels[i] = monthStats.month;
        data[0].data[i] = monthStats.callCountActive;
        data[1].data[i] = monthStats.callCountPassive;
    }
    var monthCallCountStatsChart = new Chart(ctx, {
        type: 'bar',
        data: {
            datasets: data,
            labels: labels
        },
        options: {
            title: {
                display: true,
                text: "每个月通话次数(次)"
            }
        }
    });
}

function monthCallTimeStatsChart(monthStatsList) {
    var ctx = document.getElementById("monthCallTimeStatsChart").getContext('2d');
    var data = [
        {
            label: "主叫通话时长",
            data: [],
            backgroundColor: color.success
        },
        {
            label: "被叫通话时长",
            data: [],
            backgroundColor: color.info
        }
    ]
    var length = monthStatsList.length;
    var labels = new Array(length);
    for (var i = 0; i < length; i++) {
        var monthStats = monthStatsList[i];
        labels[i] = monthStats.month;
        data[0].data[i] = monthStats.callTimeActive;
        data[1].data[i] = monthStats.callTimePassive;
    }
    var monthCallTimeStatsChart = new Chart(ctx, {
        type: 'bar',
        data: {
            datasets: data,
            labels: labels
        },
        options: {
            title: {
                display: true,
                text: "每个月通话时长(秒)"
            }
        }
    });
}

function monthMoneyStatsChart(monthStatsList) {
    var ctx = document.getElementById("monthMoneyStatsChart").getContext('2d');
    var data = [
        {
            label: "充值金额",
            data: [],
            backgroundColor: color.success,
            borderColor: color.success,
            fill: false
        },
        {
            label: "消费金额",
            data: [],
            backgroundColor: color.info,
            borderColor: color.info,
            fill: false
        }
    ]

    var length = monthStatsList.length;
    var labels = new Array(length);
    for (var i = 0; i < length; i++) {
        var monthStats = monthStatsList[i];
        labels[i] = monthStats.month;
        data[0].data[i] = monthStats.rechargeAmount;
        data[1].data[i] = monthStats.consumeAmount;
    }

    var monthMoneyStatsChart = new Chart(ctx, {
        type: 'line',
        data: {
            datasets: data,
            labels: labels
        },
        options: {
            title: {
                display: true,
                text: "每月消费和充值"
            },
            tooltips: {
                mode: 'index',
                intersect: false,
            },
            hover: {
                mode: 'nearest',
                intersect: true
            },
        }
    });
}

function loadYYSContactData(page) {
    var length = allContactList.length;
    var size = 20;
    var startIndex = page * size;
    Pagination.Page($("#htPage"), page, length, size);
    if (length <= startIndex) {
        return;
    }
    var endIndex = startIndex + size;
    if (endIndex > length) {
        endIndex = length;
    }
    var tmpl = $.templates("#yysContactTmpl"); // Get compiled template
    var html = tmpl.render(allContactList.slice(startIndex, endIndex));      // Render template using data - as HTML string
    $("#yysContactBody").html(html);
}

function formSubmit(type, opinion) {
    var typeTxt,amount,isSuccess;
    if (type == "reject") {
        if (!opinion) {
            opinion = $("#opinion").val();
        }
        if (opinion == null || opinion.length == 0) {
            layer.msg("拒批时，审批意见不能为空");
            return;
        }
        typeTxt = "拒批";
        isSuccess = false;
    } else {
        amount = $("#amount").val();
        if (amount == null || amount.length == 0) {
            layer.msg("通过审批时，额度不能为空");
            return;
        }
        $("#relAmount").val(amount)
        typeTxt = "通过";
        isSuccess = true;
    }


    layer.confirm('确认' + typeTxt + '此额度申请？', {
        btn: ['确认', '取消'] //按钮
    }, function () {
        $("#relOpinion").val(opinion);
        $("#type").val(type);
        var index = layer.load(1,{shade:0.4});
        $.ajax({
            type: 'post',
            url: '/merchant/apply/audit',
            data: $("#form").serialize(),
            success: function(data) {
                layer.close(index);
                if(data == null || data.length == 0){
                    layer.alert("审批成功",function(){
                        parent.auditAfter(isSuccess,applyId,opinion,amount);
                    })
                }else{
                    layer.alert(data);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                layer.alert("出错了，请联系管理员");
                console.info(jqXHR,textStatus,errorThrown);
            }
        });
    });


}