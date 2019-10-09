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
            , url: $tool.getContext() + 'StoreManagement/findCheckOut.do' //数据接口
            , method: 'post'
            , page: true //开启分页
            , limit: 5
            , limits: [5, 6, 7, 8, 9, 10]
            , cols: [[ //表头
                {type: 'numbers', title: '', fixed: 'left'}
                , {field: 'product_name', title: '商品名称', width: '12%', align: 'center'}
                , {field: 'number', title: '出库数量', width: '12%', templet: '#upc', align: 'center'}
                , {field: 'checkout_date', title: '出库时间', width: '12%', templet: '#upc', align: 'center'}
                , {field: 'source_kind', title: '出库类型', width: '12%', templet: '#upc', align: 'center'}
                , {field: 'checkout_user_name', title: '负责人名字', width: '20%', templet: '#upc', align: 'center'}
                , {field: 'state', title: '状态', width: '15%', templet: '#upc', align: 'center'}
                , {fixed: 'right', title: '操作', width: 150, align: 'left', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
            ]]
        });

        //为toolbar添加事件响应
        table.on('tool(userFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var row = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            //区分事件
            if (layEvent === 'review') { //编辑
                reviewUser(row.id);
            } else if (layEvent === 'editThreshold') {//设置阈值
                editThreshold(row.id);
            }
        });
    }

    defineTable();
    //查询
    form.on("submit(queryUser)", function (data) {
        var name = data.field.name;
        var startTime = data.field.start_time;
        var endTime = data.field.end_time;
        var source_kind = data.field.source_kind;

        //表格重新加载
        tableIns.reload({
            where: {
                name: name,
                startTime: startTime,
                endTime: endTime,
                source_kind: source_kind
            }

        });
        return false;
    });
    //重置
    form.on("submit(rest)",function (data) {
        document.getElementById("name").value="";
        document.getElementById("start_time").value="";
        document.getElementById("end_time").value="";
        document.getElementById("source_kind").value="";
        location.reload();
    });

    //删除
    function reviewUser(id) {
        layer.confirm('确定删除吗？', function (confirmIndex) {
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
});
