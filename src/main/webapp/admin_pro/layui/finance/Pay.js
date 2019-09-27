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


    function uploadRender(target) {
        $tool = layui.$tool,
        layui.use(['upload', '$tool'], function () {
            var $ = layui.jquery
                , upload = layui.upload
                , $tool = layui.$tool;

            //凭证上传
            var uploadInst = upload.render({
                elem: target
                , url: $tool.getContext()+'PayBill/upload.do'
                , multiple: true
                , auto: true
                ,accept:'images'
                // ,data:{type:'UploadImg'}//上传业务类型，后台会根据这个值将文件放入相应文件夹下
                , choose: function (obj) {
                    //预读本地文件示例，不支持ie8
                    obj.preview(function (index, file, result) {
                    });
                }
                ,before : function () {
                    console.log("123");
                    $('#' + $(target).attr('data-id')).empty();
                }
                , done: function (res) {
                    //上传完毕
                    imgUrl.push(res.data);

                }
                ,allDone: function(obj){ //当文件全部被提交后，才触发
                    console.log(imgUrl);
                    for (var a=0; a<imgUrl.length;a++){
                        $('#' + $(target).attr('data-id')).append('<img width="200px" height="200px" src="'+ imgUrl[a] + '" class="layui-upload-img">')
                    }
                    imgUrl = [];
                }


                // , success: function (data) {
                //     console.log(data)
                // }
            });


        });
    }
    uploadRender('#test');

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
