layui.config({
    base: $config.resUrl + 'admin_pro/layui/assets/lay/modules/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api: 'api'
}).use(['form', 'layer', '$api', 'jquery', 'table', 'laypage', 'laytpl', 'ajaxExtention', '$tool', 'upload'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        upload = layui.upload,
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
                , {field: 'create_date', title: '创建时间', width: '20%', align: 'center', templet: '#upc'}
                , {field: 'check_user_id', title: '盘点人', width: '15%', align: 'center', templet: '#upc'}
                , {field: 'begin_date', title: '开始时间', width: '20%', align: 'center', templet: '#upc'}
                , {field: 'display_name', title: '状态', width: '14%', align: 'center', templet: '#upc'}
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
            } else if (layEvent === 'delete') { //删除
                deleteCheck(row.id);
            } else if (layEvent === 'counting') { //盘点
                viewCheck(row.id);
            } else if (layEvent === 'export') { //导出
                exportCheck(row.id);
            } else if (layEvent === 'review') { //审核
                reviewCheck(row.id);
            } else if (layEvent === 'look') { //查看
                lookCheck(row.id);
            }
        });
    }

    defineTable();
    //查询
    form.on("submit(queryUser)", function (data) {
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
    //重置
    form.on("submit(rest)",function (data) {
        document.getElementById("start_time").value="";
        document.getElementById("end_time").value="";
        location.reload();
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

    //审核
    function reviewCheck(id) {
        var index = layui.layer.open({
            title: "审核盘点结果",
            type: 2,
            content: "reviewCountingTask.html?id=" + id,
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

    //查看
    function lookCheck(id) {
        var index = layui.layer.open({
            title: "查看结果单",
            type: 2,
            content: "lookCountingTask.html?id=" + id,
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
            content: "editCountingTask.html?id=" + id,
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

    //导出
    function exportCheck(id) {
        var index = layui.layer.open({
            title: "导出盘点任务",
            type: 2,
            content: "exportCountingTask.html?id=" + id,
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
            content: "viewCountingTask.html?id=" + id,
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

    //导入结果单
    upload.render({
        elem: '#upfile'
        , url: '/Stone/StoreManagement/fileUpload.do'
        , auto: false
        , accept: 'file'
        //,multiple: true
        // , bindAction: '#test9'
        , choose: function (obj) {
            layer.confirm('确认导入吗？', function (confirmIndex) {
                obj.preview(function (index, file, result) {
                    var formData = new FormData();
                    //# 给formData对象添加<input>标签,注意与input标签的ID一致
                    formData.append('upfile', file);
                    $.ajax({
                        url: '/Stone/StoreManagement/fileUpload.do',//这里写你的url
                        type: 'POST',
                        data: formData,
                        contentType: false,// 当有文件要上传时，此项是必须的，否则后台无法识别文件流的起始位置
                        processData: false,// 是否序列化data属性，默认true(注意：false时type必须是post)
                        //dataType: 'json',//这里是返回类型，一般是json,text等
                        //clearForm: true,//提交后是否清空表单数据
                        success: function (data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                            layer.msg("导入成功", {time: 1000}, function () {
                                //重新加载表格
                                tableIns.reload();
                            });
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
    // layui.use('upload', function () {
    //     var $ = layui.jquery
    //         , upload = layui.upload;
    //     var uploadInst = upload.render({
    //         elem: '#upfile'
    //         , url: '/Stone/StoreManagement/fileUpload.do'
    //         , auto: false
    //         , accept: 'file'
    //         //,multiple: true
    //         , choose: function (obj) {
    //             layer.confirm('确认导入吗？', function (confirmIndex) {
    //                 obj.preview(function (index, file, result) {
    //                     var formData = new FormData();
    //                     //# 给formData对象添加<input>标签,注意与input标签的ID一致
    //                     formData.append('upfile', file);
    //                     $.ajax({
    //                         url: '/Stone/StoreManagement/fileUpload.do',//这里写你的url
    //                         type: 'POST',
    //                         data: formData,
    //                         contentType: false,// 当有文件要上传时，此项是必须的，否则后台无法识别文件流的起始位置
    //                         processData: false,// 是否序列化data属性，默认true(注意：false时type必须是post)
    //                         //dataType: 'json',//这里是返回类型，一般是json,text等
    //                         //clearForm: true,//提交后是否清空表单数据
    //                         success: function (data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
    //                             layer.msg("导入成功", {time: 1000}, function () {
    //                                 //重新加载表格
    //                                 tableIns.reload();
    //                             });
    //                         },
    //                         error: function (data, status, e) {  //提交失败自动执行的处理函数。
    //                             console.error(e);
    //                         }
    //                     });
    //              });
    //
    //
    //             });
    //         }
    //         , done: function (res) {
    //             console.log(res)
    //         }
    //     });
    // });

});
