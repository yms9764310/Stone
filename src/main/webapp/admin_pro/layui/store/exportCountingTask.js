layui.config({
    base: $config.resUrl + 'admin_pro/layui/assets/lay/modules/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api: 'api',
    formSelects: 'formSelects-v4'
}).use(['form', 'layer', 'jquery', 'ajaxExtention', '$tool', '$api', 'formSelects'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery,
        $tool = layui.$tool,
        formSelects = layui.formSelects,
        $api = layui.$api;


    /**
     * 页面初始化
     * */
    function init() {
        //初始化菜单信息
        initMenuInfo();
    }




    function initMenuInfo() {
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];
        var req = {
            id: id
        };

        $api.GetCheckTask(req, function (res) {
            var data = res.data;
            console.log(data);
            // $("[name='id']").val(data.id);
            $("[name='check_user_id']").val(data.check_user_id);
            $("[name='check_user_name']").val(data.check_user_name);
            $("[name='begin_date']").val(data.begin_date);
            $("[name='end_date']").val(data.end_date);
            var Product = '';
            for (var i = 0; i < res.data.storeCheckTaskDetailList.length; i++) {
                Product += '<div class="layui-form-item parent-menu">\n' +
                    '<input class="product_id" type="text" value="' + res.data.storeCheckTaskDetailList[i].product_id + '"hidden="hidden" >\n'+
                    '     <div style="overflow: hidden"> <label class="layui-form-label product_name">商品名称：' + res.data.storeCheckTaskDetailList[i].name + '</label>\n' +
                    '<div class="count">该商品库存数：'+res.data.storeCheckTaskDetailList[i].number+'</div>\n' +
                    '   </div>' +
                    '<div class="layui-inline">\n' +
                    '        <label class="layui-form-label">盘点数量</label>\n' +
                    '        <div class="layui-input-inline">' +
                    '<input type="text" class="layui-input" name="check_number" readonly="readonly" placeholder="请输入盘点数量">' +
                    '</div>' +
                    '</div>' +
                    '</div>'
            }
            $('.Product').append(Product);
            form.render();//重新绘制表单，让修改生效
        });
    }

    init();
    //导出盘点任务
    $(".export").click(function () {
        var queryArgs = $tool.getQueryParam();
        var id = queryArgs['id'];
        var url =  $tool.getContext()+"StoreManagement/exportExcel.do";
        var req = {
            check_id: id,
           };
        $api.ExportCountingTask(req, {contentType: 'application/json;charset=utf-8'}, function (data) {
            if (data.data == xssfWorkbook) {
                layer.msg("导出成功！", {time: 1000}, function () {
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                });
            }
        });
    });

});
