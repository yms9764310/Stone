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
        //初始化信息
        // initMenuInfo();
    }

    init();

    /**
     * 定义表格
     * */
    function defineTable() {
        tableIns = table.render({
            elem: '#demo'
            , height: 415
            , url: $tool.getContext() + 'ToDoList/viewWorkProgress.do' //数据接口
            , method: 'post'
            , page: true //开启分页
            , limit: 5
            , limits: [5, 6, 7, 8, 9, 10]
            , cols: [[ //表头
                {type: 'numbers', title: '', fixed: 'left'}
                , {field: 'id', title: '加工单ID', width: '10%', align: 'center'}
                , {field: 'bom_id', title: '物料ID', width: '10%', align: 'center'}
                , {field: 'process_user_id', title: '负责人', width: '15%', templet: '#upc', align: 'center'}
                , {field: 'begin_date', title: '开始时间', width: '15%', templet: '#upc', align: 'center'}
                , {field: 'end_date', title: '结束时间', width: '15%', templet: '#upc', align: 'center'}
                , {field: 'process_type', title: '加工作业类型', width: '10%', templet: '#upc', align: 'center'}
                , {fixed: 'right', title: '操作', width: 150, align: 'left', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
            ]]
        });

        //为toolbar添加事件响应
        table.on('tool(userFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var row = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            //区分事件
            if (layEvent === 'look') { //查看详情
                lookDetails(row.id);
            } else if (layEvent === 'editThreshold') {//设置阈值
                editThreshold(row.id);
            }
        });
    }

    defineTable();
    //查询
    form.on("submit(queryUser)", function (data) {
        var name = data.field.creator;
        //表格重新加载
        tableIns.reload({
            where: {
                name: name,
            }

        });
        return false;
    });

    //查看详情
    function editThreshold(id) {
        layer.confirm('确定审核吗？', function (confirmIndex) {
            layer.close(confirmIndex);//关闭confirm
            //向服务端发送删除指令
            var req = {
                id: id
            };
            $api.DeleteStudent(req, function (data) {
                layer.msg("删除成功", {time: 1000}, function () {
                    //obj.del(); //删除对应行（tr）的DOM结构
                    //重新加载表格
                    tableIns.reload();
                });
            });
        });
    }

    //查看加工单详情
    function lookDetails(id) {
        var index = layui.layer.open({
            title: "查看详情",
            type: 2,
            content: "lookWorkSpeed.html?id=" + id,
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
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
    }

});
