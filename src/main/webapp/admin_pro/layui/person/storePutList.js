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
            , url: $tool.getContext() + 'StorePutIn/StorePut.do' //数据接口
            , method: 'post'
            , page: true //开启分页
            , limit: 5
            , limits: [5, 6, 7, 8, 9, 10]
            , cols: [[ //表头
                {type: 'numbers', title: '', fixed: 'left'}
                , {field: 'user_id', title: '申请人', width: '13%', align: 'center'}
                , {field: 'source_type', title: '来源表', width: '13%', align: 'center'}
                , {field: 'product_name', title: '商品名称', width: '13%', align: 'center'}
                , {field: 'date', title: '出/入库时间', width: '20%', templet: '#upc', align: 'center'}
                , {field: 'typename', title: '类型名字', width: '17%', templet: '#upc', align: 'center'}
                , {fixed: 'right', title: '操作', width: 105, align: 'left', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
            ]]
        });

        //为toolbar添加事件响应
        table.on('tool(userFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var row = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            //区分事件
            if (layEvent === 'reviewIn') { //入库审核
                ReviewIn(row.id);
            }else  if (layEvent === 'reviewOut') { //出库审核
                ReviewOut(row.id);
            }
        });
    }
    defineTable();
    //查询
    form.on("submit(queryUser)", function (data) {
        var name = data.field.name;
        var startTime = data.field.start_time;
        var endTime = data.field.end_time;
        var typename = data.field.typename;
        //表格重新加载
        tableIns.reload({
            where: {
                name: name,
                startTime: startTime,
                endTime: endTime,
                typename:typename
            }

        });
        return false;
    });

    //重置
    form.on("submit(rest)",function (data) {
        document.getElementById("name").value="";
        document.getElementById("start_time").value="";
        document.getElementById("end_time").value="";
        document.getElementById("typename").value="";
        location.reload();
    });

    //入库审核
    function ReviewIn(id) {
        var index = layui.layer.open({
            title: "入库审核",
            type: 2,
            content: "reviewIn.html?id=" + id,
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

    //出库审核
    function ReviewOut(id) {
        var index = layui.layer.open({
            title: "出库审核",
            type: 2,
            content: "reviewOut.html?id=" + id,
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
