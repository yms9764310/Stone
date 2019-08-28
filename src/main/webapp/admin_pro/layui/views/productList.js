layui.config({
    base: $config.resUrl + 'admin_pro/layui/assets/lay/modules/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api: 'api'
}).use(['form', 'layer', '$api', 'jquery', 'table', 'laypage', 'laytpl', 'ajaxExtention', '$tool'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        laypage = layui.laypage,
        laytpl = layui.laytpl,
        $tool = layui.$tool,
        table = layui.table,
        $api = layui.$api;
    layer = layui.layer;
    layuiTable = layui.table;
    var tableIns;//表格实例

    /**
     * 页面初始化
     * */
    function init() {
    }

    init();

    /**
     * 定义表格
     * */
    function defineTable() {
        tableIns = table.render({
            elem: '#productDemo'
            , height: 450
            , url: $tool.getContext() + 'purchaseSupplier/listPurchaseProduct.do' //数据接口
            , method: 'post'
            , page: false //开启分页
            , cols: [[ //表头
                {type:'checkbox'},
                {field: 'id', title: 'ID', width: 300},
                {field: 'name', title: '商品名称', width: 300}

            ]]

        });

    }
    defineTable();
    var index=parent.layer.getFrameIndex(window.name);
    $("#transmit").click(function(){
        var checkStatus=table.checkStatus('productDemo');//获取复选框的行值
        data=checkStatus.data;
        //layer.alert(JSON.stringify(checkStatus));//测试取没取到复选框选中的行的值
        //checkStatus.data 这个是一个集合
        //如果有多个，是需要遍历
        //checkStatus.data[0].uname  获取第一个
        var arr=[];
        data.forEach(function (item) {
            arr.push(item.name)
        });
        // var product=$("#searchInput").val(arr);
        //将值赋给文本框
        console.log(arr);
        var pname=JSON.stringify(arr);
        window.sessionStorage.setItem("pname",pname);
        parent.layer.close(index);
        parent.location.reload();
    });
    $("#abolish").click(function () {
        parent.layer.close(index);
    });


    // $("#certain").click(function () {
    //    var product=$('#productName',window.parent.document);
    //     product.attr("value",$("#searchInput").val());
    //
    // });

});
