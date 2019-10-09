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


    //查询

    var imgUrl = [];



        $tool = layui.$tool,
        layui.use(['upload', '$tool'], function () {
            var $ = layui.jquery
                , upload = layui.upload
                , $tool = layui.$tool;

            //凭证上传
            var uploadInst = upload.render({
                elem: '#preview_img'
                , url: $tool.getContext()+'PayBill/upload.do'
                ,accept:'file'
                , auto: false //不自动上传
                , bindAction: '#upload_img' //上传绑定到隐藏按钮
                , choose: function (obj) {
                    //预读本地文件
                    obj.preview(function (index, file, result) {
                        $('#fileName').val(file.name);  //展示文件名
                    })
                }
                , done: function (res) {
                    $('#credential_hide').val(res.msg); //隐藏输入框赋值
                    $('#submitForm').click(); //上传成功后单击隐藏的提交按钮
                }
                , error: function (index, upload) {
                    layer.msg('上传失败！' + index, {icon: 5});
                }
            });

            //确定按钮点击事件
            $('#fake').click(function () {
                $(this).attr({'disabled': 'disabled'});
                $('#upload_img').click();//单击隐藏的上传按钮
            });

            /*监听提交*/
            form.on('submit(add_recharge_submit)', function (data) {
                addRecharge(data.field);
                return false;
            });

            });






    function edit(id) {
        var index = layui.layer.open({
            title: "审核",
            type: 2,
            content: "Pay.html?id=" + id,
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
