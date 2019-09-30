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


    //根据商品名称，商品类型查询出供应商商品列表
    form.on("submit(findProduct)",function (data) {
        var purchaseName=data.field.purchaseName;
        var putInDate=data.field.putInDate;
        var expectDate=data.field.expectDate;
        var req={
            purchaseName:purchaseName,
            putInDate:putInDate,
            expectDate:expectDate
        }
        if ((purchaseName==null||purchaseName=="")&&(putInDate==null||putInDate=="")&&(expectDate==null||expectDate=="")){
            layer.msg("请输入要查询的内容!",{time:1500,icon:5});
            return false;
        }
        $api.BillOrdersLike(req,function (data) {
            var tableIns;//表格实例


            /**
             * 定义表格
             * */
            function defineTable() {
                tableIns = table.render({
                    elem: '#demoBill'
                    , height: 415
                    , url: $tool.getContext() + 'procurementBill/listBillOrdersLike.do?purchaseName='+purchaseName+'putInDate='+putInDate+'expectDate='+expectDate //数据接口
                    , method: 'post'
                    , page: true //开启分页
                    , limit: 5
                    , limits: [5, 6, 7, 8, 9, 10]
                    , cols: [[ //表头
                        {type: 'numbers', title: '序号', fixed: 'left'}
                        , {field: 'creator', title: '创建人', width: '10%',align:'center'}
                        , {field: 'purchaseName', title: '采购人员', width: '15%',align:'center'}
                        , {field: 'createDate', title: '创建时间', width: '10%',align:'center'}
                        , {field: 'modifier', title: '修改人', width: '10%', templet: '#upc',align:'center'}
                        , {field: 'modifyDate', title: '修改时间', width: '10%', templet: '#upc',align:'center'}
                        , {field: 'putInDate', title: '入库时间', width: '10%', templet: '#upc',align:'center'}
                        , {field: 'emergent', title: '是否紧急', width: '10%', templet: '#upc',align:'center'}
                        , {field: 'sumMoney', title: '总金额', width: '10%', templet: '#upc',align:'center'}
                        , {field: 'expectDate', title: '预期时间', width: '10%', templet: '#upc',align:'center'}
                        , {fixed: 'right', title: '操作', width: 217, align: 'left', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
                    ]]

                });
            }
            defineTable();
        });
        return false;
    });



    form.on("submit(rest)",function (data) {
        document.getElementById("billId").value="";
        document.getElementById("purchaseName").value="";
        document.getElementById("start_time").value="";
        document.getElementById("end_time").value="";
        location.reload();
    })
});
