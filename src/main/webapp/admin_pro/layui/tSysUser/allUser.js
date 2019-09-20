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
     * 定义表格
     * */
    function defineTable() {
        tableIns = table.render({
            elem: '#demo'
            , height: 415
            , url: $tool.getContext() + 'SysUser/listUser.do' //数据接口
            , method: 'post'
            , page: true //开启分页
            , limit: 5
            , limits: [5, 6, 7, 8, 9, 10]
            , cols: [[ //表头
                {type: 'numbers', title: '', fixed: 'left'}
                , {field: 'id', title: '员工ID', width: '6%', align: 'center'}
                , {field: 'name', title: '姓名', width: '10%', align: 'center'}
                , {field: 'sex', title: '性别', width: '8%', templet: '#upc', align: 'center'}
                , {field: 'age', title: '年龄', width: '8%', templet: '#upc', align: 'center'}
                , {field: 'phone', title: '电话', width: '12%', templet: '#upc', align: 'center'}
                , {field: 'depart_id', title: '部门', width: '10%', templet: '#upc', align: 'center'}
                , {field: 'job_id', title: '岗位', width: '10%', templet: '#upc', align: 'center'}
                , {field: 'depart_role_id', title: '部门角色', width: '10%', templet: '#upc', align: 'center'}
                , {field: 'max_threshold', title: '最大阈值', width: '10%', templet: '#upc', align: 'center'}
                , {fixed: 'right', title: '操作', width: 217, align: 'left', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
            ]]

        });

        //为toolbar添加事件响应
        table.on('tool(userFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var row = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            //区分事件
            if (layEvent === 'del') { //删除
                deleteRole(row.id);
            } else if (layEvent === 'edit') { //编辑
                editRole(row.id,row.department_id);
            }
        });
    }
    defineTable();

    //查询
    form.on("submit(queryUser)", function (data) {
        var name = data.field.name;
        var department = data.field.department;
        //表格重新加载
        tableIns.reload({
            where: {
                name: name,
                department:department
            }

        });
        return false;
    });

    //添加角色
    $(".add_btn").click(function () {
        var index = layui.layer.open({
            title: "添加部门",
            type: 2,
            content: "addUser.html",
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


//批量导入
    layui.use('upload', function () {
        var $ = layui.jquery
            , upload = layui.upload;
        var uploadInst = upload.render({

            elem: '#upfile'
            , url: '/Stone/demp/fileUpload.do'
            , auto: false
            , accept: 'file'
            //,multiple: true
            , choose: function (obj) {
                layer.confirm('确认导入吗？', function (confirmIndex) {
                    obj.preview(function (index, file, result) {
                        var formData = new FormData();
                        //# 给formData对象添加<input>标签,注意与input标签的ID一致
                        formData.append('upfile', file);
                        $.ajax({
                            url: '/Stone/demp/fileUpload.do',//这里写你的url
                            type: 'POST',
                            data: formData,
                            contentType: false,// 当有文件要上传时，此项是必须的，否则后台无法识别文件流的起始位置
                            processData: false,// 是否序列化data属性，默认true(注意：false时type必须是post)
                            //dataType: 'json',//这里是返回类型，一般是json,text等
                            //clearForm: true,//提交后是否清空表单数据
                            success: function (data) {
                                //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                                if(data.data=="false"){
                                layer.msg("该部门名称已经存在", {time: 1000}, function () {
                                    //重新加载表格
                                    location.reload()
                                });
                                }else{
                                    layer.msg("导入成功", {time: 1000}, function () {
                                        //重新加载表格
                                        location.reload()
                                    });
                                }
                            },
                            error: function (data, status, e) {  //提交失败自动执行的处理函数。
                                console.error(e);
                            }
                        });
                    });


                });
            }
            , done: function (res) {
                console.log(res)
            }
        });
    });


    //删除
    function deleteRole(id) {
        layer.confirm('确认删除吗？', function (confirmIndex) {
            layer.close(confirmIndex);//关闭confirm
            //向服务端发送删除指令
            var req = {
                id: id
            };
            $api.DeleteRole(req, function (data) {
                layer.msg("删除成功", {time: 1000}, function () {
                    //obj.del(); //删除对应行（tr）的DOM结构
                    //重新加载表格
                    tableIns.reload();
                });
            });
        });
    }

    //修改
    function editRole(id,department_id) {
        var index = layui.layer.open({
            title: "修改学生",
            type: 2,
            //传递多个参数用&
            content: "editRole.html?id="+ id+"&department_id="+department_id,
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
