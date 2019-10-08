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
    // function initMenuInfo() {
    //     var queryArgs = $tool.getQueryParam();//获取查询参数
    //     var id = queryArgs['id'];
    //
    //     var url = $tool.getContext() + 'StaffSetting/find.do';
    //     var req = {
    //         id: id
    //     };
    //     $api.GetDepartUsers(req, function (res) {
    //         var data = res.data;
    //         $("[name='name']").val(data.name);
    //         $("input:radio[value='" + data.sex + "']").attr('checked', 'true');
    //         $("[name='age']").val(data.age);
    //         $("[name='phone']").val(data.phone);
    //         $("[name='job_id']").val(data.job_id);
    //         $("[name='depart_id']").val(data.depart_id);
    //         $("[name='depart_role_id']").val(data.depart_role_id);
    //         $("[name='max_threshold']").val(data.max_threshold);
    //         form.render();//重新绘制表单，让修改生效
    //     });
    // }
    /**
     * 定义表格
     * */
    function defineTable() {
        tableIns = table.render({
            elem: '#demo'
            , height: 415
            , url: $tool.getContext() + 'Message/findToDoList.do' //数据接口
            , method: 'post'
            , page: true //开启分页
            , limit: 5
            , limits: [5, 6, 7, 8, 9, 10]
            , cols: [[ //表头
                {type: 'numbers', title: '', fixed: 'left'}
                , {field: 'from_user_name', title: '发送人', width: '12%', align: 'center'}
                , {field: 'message_content', title: '通知内容', width: '20%', templet: '#upc', align: 'center'}
                , {field: 'message_type', title: '消息类型', width: '12%', templet: '#upc', align: 'center'}
                , {field: 'state', title: '消息状态', width: '20%', templet: '#upc', align: 'center'}
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
        var message_type = data.field.message_type;
        //表格重新加载
        tableIns.reload({
            where: {
                name: name,
                message_type: message_type,
            }

        });
        return false;
    });
    //重置
    form.on("submit(rest)",function (data) {
        document.getElementById("message_type").value="";
        document.getElementById("name").value="";
        location.reload();
    });

    //编辑
    function reviewUser(id) {
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

    //设置阈值
    function editThreshold(id) {
        var index = layui.layer.open({
            title: "设置阈值",
            type: 2,
            content: "editThreshold.html?id=" + id,
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
