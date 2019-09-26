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
            , height: 315
            , url: $tool.getContext() + 'purchaseSupplier/listSupplier.do' //数据接口
            , method: 'post'
            , page: true //开启分页
            , limit:10
            , limits:[10,20,30,40,50,60,70]
            , cols: [[ //表头
                {type: 'numbers', title: '序号', fixed: 'left'}
                , {field: 'creator', title: '创建人', width: '5%',align:'center'}
                , {field: 'name', title: '供应商', width: '10%',align:'center'}
                , {field: 'createDate', title: '创建时间', width: '13%',align:'center'}
                , {field: 'modifier', title: '修改人', width: '5%', templet: '#upc',align:'center'}
                , {field: 'modifyDate', title: '修改时间', width: '13%', templet: '#upc',align:'center'}
                , {field: 'companyName', title: '公司名', width: '10%', templet: '#upc',align:'center'}
                , {field: 'contactName', title: '联系人', width: '6%', templet: '#upc',align:'center'}
                , {field: 'contactPhone', title: '联系电话', width: '10%', templet: '#upc',align:'center'}
                , {field: 'address', title: '公司地址', width: '10%', templet: '#upc',align:'center'}
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
                delSupplier(row.id);
            } else if(layEvent==='NoEdit'){
                NoEditSupplier(row.id);
            }else if (layEvent === 'edit') { //编辑
                editSupplier(row.id);
            }else if (layEvent==='look') {
                lookProduct(row.id)
            }
        });
    }
    defineTable();
    //查询
    form.on("submit(findLike)", function (data) {
        //查询的校验
        var searchSysProductName=document.getElementById("SysProductName").value;
        var searchName=document.getElementById("name").value;
        if ((searchSysProductName==null||searchSysProductName=="")&&(searchName==null||searchName=="")){
            layer.msg("你未输入要查询的条件!",{time:1500,icon:8});
            return false;
        }else{
            var SysProductName = data.field.SysProductName;
            var name = data.field.name;
            //表格重新加载
            tableIns.reload({
                where: {
                    SysProductName: SysProductName,
                    name:name
                }
            });
        }
        return false;
    });

    //不可修改
    function NoEditSupplier(){
        layer.msg("已生效,不可修改！！！",{time:1000,icon:2},function () {
            //重载表格
            tableIns.reload();
        });
    };


    //添加
    $(".insert_btn").click(function () {
        var index = layui.layer.open({
            title: "添加供应商信息",
            type: 2,
            content: "insertSupplier.html",
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


    //删除
    function delSupplier(id) {
        layer.confirm('确认删除吗？', function (confirmIndex) {
            layer.close(confirmIndex);//关闭confirm
            //向服务端发送删除指令
            var req = {
                id: id
            };
            $api.DeletePurchase(req,function (data) {
                layer.msg("删除成功", {time: 1000,icon:6}, function () {
                    //obj.del(); //删除对应行（tr）的DOM结构
                    //重新加载表格
                    tableIns.reload();
                });
            });
        });
    }

    //修改
    function editSupplier(id) {
        var index = layui.layer.open({
            title: "修改供应商信息",
            type: 2,
            content: "editSupplier.html?id=" +id,
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




// Excel导入
    layui.use('upload', function () {
        var $ = layui.jquery
            , upload = layui.upload;
        var uploadInst = upload.render({

            elem: '#upfile'
            , url: '/Stone/purchaseSupplier/fileUpload.do'
            , auto: false
            //判断是否是Excel文件
            , accept: 'file'
            //,multiple: true
            , choose: function (obj) {
                //选择要导入Excel表格
                layer.confirm('确认导入吗？',{icon:3}, function (confirmIndex) {
                    obj.preview(function (index, file, result) {
                        var formData = new FormData();
                        //# 给formData对象添加<input>标签,注意与input标签的ID一致
                        formData.append('upfile', file);
                        $.ajax({
                            url: '/Stone/purchaseSupplier/fileUpload.do',//这里写你的url
                            type: 'POST',
                            data: formData,
                            contentType: false,// 当有文件要上传时，此项是必须的，否则后台无法识别文件流的起始位置
                            processData: false,// 是否序列化data属性，默认true(注意：false时type必须是post)
                            //dataType: 'json',//这里是返回类型，一般是json,text等
                            //clearForm: true,//提交后是否清空表单数据
                            success: function (data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                                layer.msg("导入成功", {time: 1000,icon:6}, function () {
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
    });

    //查看商品
    function lookProduct(id) {
        var index = layui.layer.open({
            title: "商品信息",
            type: 2,
            area:['40%','50%'],
            fixed: false, //不固定
            maxmin: true,
            btn:['确定','取消'],
            content: "lookProduct.html?id="+id,
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: [1,'#3595CC'],
                        time:4000
                    });
                }, 500)
            }
        });
    }

});
