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
            elem: '#demo'
            , height: 415
            , url: $tool.getContext() + 'Customer/loadByCusId.do' //数据接口
            , method: 'post'
            , page: true //开启分页
            , limit: 5
            , limits: [5, 6, 7, 8, 9, 10]
            , cols: [[ //表头
                {type: 'numbers', title: '序号', fixed: 'left'}
                , {field: 'creator', title: '创建人', width: '40%'}
                , {field: 'create_date', title: '创建时间', width:'70%'}
                , {field: 'modifier', title: '修改人', width: '40%'}
                , {field: 'modify_date', title: '修改时间', width: '70%'}
                , {field: 'bill_no', title: '订单编号', width: '40%'}
                , {field: 'sale_id', title: '负责人', width: '40%'}
                , {field: 'address', title: '收货地址', width: '40%'}
                , {field: 'sale_money', title: '订单总金额', width: '40%'}
                , {field: 'deliver_date', title: '交付时间', width: '40%'}
                , {field: 'settle_type', title: '结算方式', width: '40%'}
                , {field: 'customer_id', title: '客户', width: '40%'}
            ]]
        });
        //为toolbar添加事件响应
        table.on('tool(userFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var row = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
        });
    }
    defineTable();

});
