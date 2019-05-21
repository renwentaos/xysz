function audit(applyId,memberId){
    var index = layer.open({
        type: 2,
        title:"额度审核",
        skin: 'layui-layer-lan',
        area: ['95%', '95%'],
        content: '/merchant/apply/audit?id='+applyId+'&memberId='+memberId
    });
}