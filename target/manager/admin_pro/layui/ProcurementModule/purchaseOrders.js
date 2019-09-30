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
                , {field: 'creator', title: '创建人', width: '10%',align:'center'}
                , {field: 'purchaseName', title: '采购人员', width: '10%',align:'center'}
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

        //为toolbar添加事件响应
        table.on('tool(billFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var row = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            //区分事件
            if (layEvent === 'delOrders') { //删除
                delOrders(row.id);
            } else if (layEvent === 'editOrders') { //编辑
                editOrders(row.id);
            }else if (layEvent==='auditOrders') {//审核
                auditOrders(row.id);
            }else if(layEvent==='close'){//是否关闭
                close(row.id);
            }else if (layEvent==='lookReason') {//查看关闭原因
                lookReason(row.id);
            }
        });
    }
defineTable();

    //删除
    function delOrders(id) {
        layer.confirm('确认删除吗？', function (confirmIndex) {
            layer.close(confirmIndex);//关闭confirm
            //向服务端发送删除指令
            var req = {
                id: id
            };
            $api.DeleteBillOrders(req,function (data) {
                layer.msg("删除成功", {time: 1000,icon:6}, function () {
                    //obj.del(); //删除对应行（tr）的DOM结构
                    //重新加载表格
                    tableIns.reload();
                });
            });
        });
    }

    //采购单的编辑
    function editOrders(id) {
        var index = layui.layer.open({
            title: "编辑采购单信息",
            type: 2,
            content: "editBillOrders.html?id=" +id,
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: [1,'#3595CC'],
                        time:4000
                    });
                }, 500)
            }
        });

        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function () {
            layui.layer.full(index);
        });
        layui.layer.full(index);
    }

    //采购单的审核
    function auditOrders(id) {
        var index = layui.layer.open({
            title: "审核订单",
            type: 2,
            content: "auditBillOrders.html?id=" +id,
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: [1,'#3595CC'],
                        time:4000
                    });
                }, 500)
            }
        });

        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function () {
            layui.layer.full(index);
        });
        layui.layer.full(index);
    }

    //是否关闭
    function close(id) {
        layer.prompt({title:"请填写备注和关闭理由!",formType:2},function (value,index) {
            var reason=value;
            if (reason==null||reason=="") {
                layer.msg("请输入关闭原因!",{time:1500,icon:5});
                return false;
            }
            var req={
                id:id,
                reason:reason
            };
            $api.UpdateCloseBill(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
                layer.msg("关闭成功!", {time: 1000,icon:6}, function () {
                    layer.close(index);
                    tableIns.reload();
                });
            });
        });
        return false;
    }
    //查看关闭原因
    function lookReason(id) {
        var req = {
            id: id
        };
        $api.LookReason(req, function (res) {
            var data = res.data;
            alert(JSON.stringify(data.reason));
            layer.prompt({title: "请填写备注和关闭理由!", formType: 2,value:data.reason}, function (value, index) {
                layer.close(index);
                tableIns.reload();
            });
        });
    }
});
