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
            elem: '#demoBill'
            , height: 415
            , url: $tool.getContext() + 'procurementBill/listProcurement.do' //数据接口
            , method: 'post'
            , page: true //开启分页
            , limit: 5
            , limits: [5, 6, 7, 8, 9, 10]
            , cols: [[ //表头
                {type: 'numbers', title: '序号', fixed: 'left'}
                , {field: 'creator', title: '创建人', width: '5%',align:'center'}
                , {field: 'createDate', title: '创建时间', width: '10%',align:'center'}
                , {field: 'modifier', title: '修改人', width: '5%', templet: '#upc',align:'center'}
                , {field: 'modifyDate', title: '修改时间', width: '10%', templet: '#upc',align:'center'}
                , {field: 'putInDate', title: '入库时间', width: '10%', templet: '#upc',align:'center'}
                , {field: 'emergent', title: '是否紧急', width: '10%', templet: '#upc',align:'center'}
                , {fixed: 'right', title: '操作', width: 217, align: 'left', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
            ]],
            done: function(res, curr, count){
                $api.LogOut(null,function (data) {
                    for(var i=0;i<data.data.length;i++){
                        if(data.data[i]=="admin"){
                            $('.d1').css("display","block");
                        }
                        else{
                            $('.d1').css("display","none");
                        }
                    }
                });
            }

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
            }else if (layEvent==='completed'){//待完成
                completed(row.id);
            }else if (layEvent==='editCompleted'){
                editCompleted(row.id);//待完成编辑
            }
        });
    }
    defineTable();

    //查询事项信息
    form.on("submit(queryUser)",function (data) {
        var creator=data.field.creator;
        //表格重新加载
        tableIns.reload({
            where: {
                creator: creator
            }
        });
        return false;
    });

    //创建采办事项
    $(".insert_btn").click(function () {
        var index = layui.layer.open({
            title: "创建采办事项信息",
            type: 2,
            content: "insertBill.html",
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function () {
            layui.layer.full(index);
        });
        layui.layer.full(index);
    });

    //编辑采办事项
    function editCheck(id) {
        var index = layui.layer.open({
            title: "编辑采办事项信息",
            type: 2,
            content: "editPurchaseBill.html?id=" +id,
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

    //删除
    function delCheck(id) {
        layer.confirm('确认删除吗？', function (confirmIndex) {
            layer.close(confirmIndex);//关闭confirm
            //向服务端发送删除指令
            var req = {
                id: id
            };
            $api.DeletePurchaseBill(req,function (data) {
                layer.msg("删除成功", {time: 1000,icon:6}, function () {
                    //obj.del(); //删除对应行（tr）的DOM结构
                    //重新加载表格
                    tableIns.reload();
                });
            });
        });
    }

    //审核
    function audit(id) {
        var index = layui.layer.open({
            title: "审核采办事项信息",
            type: 2,
            content: "auditPurchaseBill.html?id=" +id,
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

    //待完成,采购人员创建订单
    function completed(id) {
        var index = layui.layer.open({
            title: "审核采办事项信息",
            type: 2,
            content: "completedBill.html?id=" +id,
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

    //待完成编辑
    function editCompleted(id) {
        var index = layui.layer.open({
            title: "编辑采办事项信息",
            type: 2,
            content: "editCompletedBill.html?id=" +id,
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


});
