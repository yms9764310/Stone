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


    init();

    function initMenuInfo() {
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];
        var req = {
            id: id
        };

        $api.GetCheckTask(req, function (res) {
            var data = res.data;
            console.log(data);
             alert(JSON.stringify(data));
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
                    '<input type="text" class="layui-input" lay-verify="required|manNum" name="check_number"placeholder="请输入盘点数量">' +
                    '</div>' +
                    '</div>' +
                    '</div>'
            }
            $('.Product').append(Product);
            form.render();//重新绘制表单，让修改生效
        });
    }


    /**
     * 表单提交
     * */
    form.on("submit(editCountingTask)", function (data) {
        var queryArgs = $tool.getQueryParam();
        var id = queryArgs['id'];
        var check_user_id = data.field.check_user_id;
        var check_id = data.field.check_id;
        var begin_date = data.field.begin_date;
        var end_date = data.field.end_date;
        var productNumber = $(".Product").children(".parent-menu")
        var storeCheckTaskDetailList  = [];
        for (var i = 0; i < productNumber.length; i++) {
            var countingNumber = $($($($(productNumber[i]).children(".layui-inline")[0]).children(".layui-input-inline")[0]).children(".layui-input")[0]).val();
            var productId = $($(productNumber[i]).children(".product_id")[0]).attr("value");
            var storeCheckTaskDetail={product_id:productId,check_number:countingNumber,check_id:id};
            storeCheckTaskDetailList.push(storeCheckTaskDetail);
        }



        // alert(JSON.stringify(storeCheckTaskDetailList));
        // var product_id = data.field.product_id;
        //请求
        var req = {
            id: id,
            check_user_id: check_user_id,
            begin_date: begin_date,
            check_id: check_id,
            end_date: end_date,
            storeCheckTaskDetailList: storeCheckTaskDetailList
        };
        alert(JSON.stringify(req));
        $api.SureCountingTask(JSON.stringify(req), {contentType: 'application/json;charset=utf-8'}, function (data) {
            if (data.data == "success") {
                layer.msg("确定成功！", {time: 1000}, function () {
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                });
            } else if (data.data == "error") {
                layer.msg("修改失败，您不是主管，没有权限！", {time: 1000}, function () {

                });
            }
        });
        return false;
    })
});
