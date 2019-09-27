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
            , url: $tool.getContext() + 'PersonLogHistory/find.do' //数据接口
            , method: 'post'
            , page: true //开启分页
            , limit: 5
            , limits: [5, 6, 7, 8, 9, 10]
            , cols: [[ //表头
                // {type:'checkbox', fixed: 'left'}
                {type: 'numbers', title: '', fixed: 'left'}
                , {field: 'user_name', title: '操作人名字', width: '25%', align: 'center'}
                , {field: 'modify_date', title: '操作时间', width: '30%', align: 'center'}
                , {field: 'description', title: '操作类型', width: '30%', align: 'center'}
                , {fixed: 'right', title: '操作', width: 217, align: 'left', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
            ]]
        });

        //为toolbar添加事件响应
        table.on('tool(userFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var row = obj.data; //获得当前行数据

            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            //区分事件
            if (layEvent === 'delete') { //删除
                deleteHistory(row.id);
            }
        });

        // table.on('toolbar(test)', function(obj){
        //     var checkStatus = table.checkStatus(obj.config.id);
        //     switch(obj.event){
        //         case 'getCheckData':
        //             var data = checkStatus.data;
        //             layer.alert(JSON.stringify(data));
        //             break;
        //         case 'getCheckLength':
        //             var data = checkStatus.data;
        //             layer.msg('选中了：'+ data.length + ' 个');
        //             break;
        //         case 'isAll':
        //             layer.msg(checkStatus.isAll ? '全选': '未全选')
        //             break;
        //     };
        // });
    }


    loadData();
    function loadData(){

        defineTable();

    }

    //查询
    form.on("submit(queryUser)", function (data) {
        var name = data.field.name;
        var startTime = data.field.start_time;
        var endTime = data.field.end_time;
        var description = data.field.description;
        //表格重新加载
        tableIns.reload({
            where: {
                name: name,
                startTime: startTime,
                endTime: endTime,
                description:description
            }

        });
        return false;
    });

    //删除
    function deleteHistory(id) {
        alert(id);
        layer.confirm('确定删除吗？', function (confirmIndex) {
            layer.close(confirmIndex);//关闭confirm
            //向服务端发送删除指令
            var req = {
                id: id
            };
            $api.DeleteHistory(req, function (data) {
                layer.msg("删除成功", {time: 1000}, function () {
                    //obj.del(); //删除对应行（tr）的DOM结构
                    //重新加载表格
                    tableIns.reload();
                });
            });
        });
    }



});
