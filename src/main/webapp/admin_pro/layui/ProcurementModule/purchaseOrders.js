layui.config({
    base: $config.resUrl + 'admin_pro/layui/assets/lay/modules/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api: 'api',
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
     * 定义表格
     * */
    function defineTable() {
        tableIns = table.render({
            elem: '#demoBillOrders'
            , height: 415
            , url: $tool.getContext() + 'procurementBill/purchaseBillOrders.do' //数据接口
            , method: 'post'
            , page: true //开启分页
            , limit: 5
            , limits: [5, 6, 7, 8, 9, 10]
            , cols: [[ //表头
                {type: 'numbers', title: '序号', fixed: 'left'}
                , {field: 'creator', title: '创建人', width: '5%',align:'center'}
                , {field: 'purchaseName', title: '采购人员', width: '15%',align:'center'}
                , {field: 'createDate', title: '创建时间', width: '10%',align:'center'}
                , {field: 'modifier', title: '修改人', width: '5%', templet: '#upc',align:'center'}
                , {field: 'modifyDate', title: '修改时间', width: '10%', templet: '#upc',align:'center'}
                , {field: 'putInDate', title: '入库时间', width: '10%', templet: '#upc',align:'center'}
                , {field: 'emergent', title: '是否紧急', width: '10%', templet: '#upc',align:'center'}
                , {field: 'sumMoney', title: '总金额', width: '10%', templet: '#upc',align:'center'}
                , {field: 'expectDate', title: '预期时间', width: '10%', templet: '#upc',align:'center'}
                , {fixed: 'right', title: '操作', width: 217, align: 'left', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
            ]]

        });

        //为toolbar添加事件响应
        table.on('tool(billFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var row = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            //区分事件
            if (layEvent === 'delCheck') { //删除
                delCheck(row.id);
            } else if (layEvent === 'editCheck') { //编辑
                editCheck(row.id);
            }else if (layEvent==='audit') {//审核
                audit(row.id);
            }
        });
    }
    defineTable();


});
