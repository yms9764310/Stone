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
            , url: $tool.getContext() + 'StoreManagement/StoreCheck.do' //数据接口
            , method: 'post'
            , page: true //开启分页
            , limit: 5
            , limits: [5, 6, 7, 8, 9, 10]
            , cols: [[ //表头
                {type: 'numbers', title: '', fixed: 'left'}
                , {field: 'creator', title: '创建人', width: '19%', align: 'center'}
                , {field: 'create_date', title: '创建时间', width: '20%', align: 'center',templet: '#upc'}
                , {field: 'check_user_id', title: '盘点人', width: '15%', align: 'center',templet: '#upc'}
                , {field: 'begin_date', title: '开始时间', width: '20%', align: 'center',templet: '#upc'}
                , {field: 'display_name', title: '状态', width: '14%', align: 'center',templet: '#upc'}
                , {fixed: 'right', title: '操作', width: 152, align: 'left', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
            ]]
        });
        //为toolbar添加事件响应
        table.on('tool(userFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var row = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            //区分事件
            if (layEvent === 'edit') { //编辑
                editCheck(row.id);
            }else  if (layEvent === 'delete') { //删除
                deleteCheck(row.id);
            }else  if (layEvent === 'counting') { //盘点
                viewCheck(row.id);
            }
        });
    }
    defineTable();
    //查询
    form.on("submit(queryUser)", function (data) {
        alert(JSON.stringify(data));
        var startTime = data.field.start_time;
        var endTime = data.field.end_time;
        //表格重新加载
        tableIns.reload({
            where: {
                startTime: startTime,
                endTime: endTime
            }

        });
        return false;
    });

    //创建盘点任务
    $(".add_btn").click(function () {
        var index = layui.layer.open({
            title: "创建盘点任务",
            type: 2,
            content: "countingTask.html",
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
    });

    //删除
    function deleteCheck(id) {
        layer.confirm('确认删除吗？', function (confirmIndex) {
            layer.close(confirmIndex);//关闭confirm
            //向服务端发送删除指令
            var req = {
                id: id
            };

            $api.DeleteCheckTask(req, function (data) {
                layer.msg("删除成功", {time: 1000}, function () {
                    //obj.del(); //删除对应行（tr）的DOM结构
                    //重新加载表格
                    tableIns.reload();
                });
            });
        });
    }

    //编辑
    function editCheck(id) {
        var index = layui.layer.open({
            title: "编辑盘点任务",
            type: 2,
            content: "editCountingTask.html?id="+id,
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

    //盘点
    function viewCheck(id) {
        var index = layui.layer.open({
            title: "确定盘点任务",
            type: 2,
            content: "viewCountingTask.html?id="+id,
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
