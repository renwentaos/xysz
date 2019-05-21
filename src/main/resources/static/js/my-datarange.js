function myRange(dateSelector,startValSelector,endValSelector)
{//定义locale汉化插件
    var drLocale = {
        "format": 'YYYY-MM-DD',
        "separator": " - ",
        "applyLabel": "确定",
        "cancelLabel": "清除",
        "fromLabel": "起始时间",
        "toLabel": "结束时间'",
        "customRangeLabel": "自定义",
        "weekLabel": "W",
        "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
        "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        "firstDay": 1
    };

    var drRange = {
        '今天': [moment(), moment()],
        '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
        '最近 7 天': [moment().subtract(6, 'days'), moment()],
        '最近 30 天': [moment().subtract(29, 'days'), moment()],
        '本月': [moment().startOf('month'), moment().endOf('month')],
        '上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
    };

    $(dateSelector).daterangepicker({
        'locale': drLocale,
        ranges: drRange,
        alwaysShowCalendars: true,
        autoUpdateInput: false,
        opens: "left"
    });
    $(dateSelector).on('apply.daterangepicker', function (ev, picker) {
        var start = picker.startDate.format("YYYY/MM/DD");
        var end = picker.endDate.format("YYYY/MM/DD")
        $(this).val(start + ' - ' + end);
        $(startValSelector).val(start);
        $(endValSelector).val(end);
    });

    $(dateSelector).on('cancel.daterangepicker', function (ev, picker) {
        $(this).val('');
        $(startValSelector).val('');
        $(endValSelector).val('');
    });

}