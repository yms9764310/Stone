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
    }

    init();

    /**
     * 定义表格
     * */
    function defineTable() {
        tableIns = table.render({
            elem: '#demo'
            , height: 415
            , url: $tool.getContext() + 'Customer/get.do' //数据接口
            , method: 'post'
            , page: true //开启分页
            , limit: 5
            , limits: [5, 6, 7, 8, 9, 10]
            , cols: [[ //表头
                {type: 'numbers', title: '序号', fixed: 'left'}
                , {field: 'id', title: 'ID', width: '5%'}
                , {field: 'creator', title: '创建人', width: '7%'}
                , {field: 'create_date', title: '创建时间', width:'10%'}
                , {field: 'modifier', title: '修改人', width: '7%'}
                , {field: 'modify_date', title: '修改时间', width: '10%'}
                , {field: 'name', title: '客户名字', width: '10%'}
                , {field: 'address', title: '客户地址', width: '10%'}
                , {field: 'phone', title: '联系方式', width: '10%'}
                , {field: 'company', title: '所属机构', width: '10%'}
                , {fixed: 'right', title: '操作', width: '20%', align: 'left', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
            ]]
        });
        //为toolbar添加事件响应
        table.on('tool(userFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var row = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            //区分事件
            if (layEvent === 'del') { //删除
                deleteCustomer(row.id);
            } else if (layEvent === 'edit') { //编辑
                editCustomer(row.id);
            } else if (layEvent === 'look') { //查看交易记录
                checkCustomer(row.id);
            }
        });
    }
    defineTable();
    //查询
    form.on("submit(queryUser)", function (data) {
        var name = data.field.name;
        var address = data.field.address;
        //表格重新加载
        tableIns.reload({
            where: {
                name: name,
                address:address
            }
        });
        return false;
    });
    //添加学生
    $(".add_btn").click(function () {
        var index = layui.layer.open({
            title: "添加客户信息",
            type: 2,
            content: "insertCustomer.html",
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
    function deleteCustomer(id) {
        layer.confirm('确认删除吗？', function (confirmIndex) {
            layer.close(confirmIndex);//关闭confirm
            //向服务端发送删除指令
            var req = {
                id: id
            };
            $api.DeleteCustomer(req, function (data) {
                layer.msg("删除成功", {time: 1000}, function () {
                    //obj.del(); //删除对应行（tr）的DOM结构
                    //重新加载表格
                    tableIns.reload();
                });
            });
        });
    }

    //修改
    function editCustomer(id) {
        var index = layui.layer.open({
            title: "修改学生",
            type: 2,
            content: "updateCustomer.html?id=" + id,
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


    //批量导入
    layui.use('upload', function () {
        var $ = layui.jquery
            , upload = layui.upload;
        var uploadInst = upload.render({

            elem: '#upfile'
            , url: '/Stone/Customer/fileUpload.do'
            , auto: false
            , accept: 'file'
            //,multiple: true
            , choose: function (obj) {
                layer.confirm('确认导入吗？', function (confirmIndex) {
                    layer.closeAll('dialog');
                    obj.preview(function (index, file, result) {
                        var formData = new FormData();
                        //# 给formData对象添加<input>标签,注意与input标签的ID一致
                        formData.append('upfile', file);
                        $.ajax({
                            url: '/Stone/Customer/fileUpload.do',//这里写你的url
                            type: 'POST',
                            data: formData,
                            contentType: false,// 当有文件要上传时，此项是必须的，否则后台无法识别文件流的起始位置
                            processData: false,// 是否序列化data属性，默认true(注意：false时type必须是post)
                            //dataType: 'json',//这里是返回类型，一般是json,text等
                            //clearForm: true,//提交后是否清空表单数据
                            success: function (data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                                layer.msg("导入成功", {time: 1000}, function () {
                                    layer.closeAll("iframe");
                                    //刷新父页面
                                    location.reload();
                                });
                            },
                            error: function (data, status, e) {  //提交失败自动执行的处理函数。
                                console.error(e);
                            }
                        });
                    });
                return false;
                });
            }
            , done: function (res) {
                console.log(res)
            }
        });
    });
    //查看交易记录
    function checkCustomer(id) {
        var index = layui.layer.open({
            title: "该客户交易记录",
            type: 2,
            content: "checkCustomer.html?id=" + id,
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
