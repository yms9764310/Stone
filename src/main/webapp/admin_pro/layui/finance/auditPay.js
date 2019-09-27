layui.config({
    base: $config.resUrl + 'admin_pro/layui/assets/lay/modules/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api: 'api'
}).use(['form', 'layer', 'jquery','ajaxExtention','$tool','$api'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery,
        $tool = layui.$tool,
        $api = layui.$api;

    var queryArgs = $tool.getQueryParam();//获取查询参数
    var id = queryArgs['id'];
    var req = {
        id: id
    };
    $(".examine").click(function () {
        layer.confirm('确认通过审核吗', function (confirmIndex) {
            layer.close(confirmIndex);//关闭confirm

            $api.HandleBillPass(req, function (data) {
                if (data.data == 0) {
                    layer.msg("审核通过！", {time: 1000}, function () {
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    });
                }
            });
        });
        return false;
    });
    var data;

    function initMenuInfo() {
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];
            var req = {
                id:id
        };

        $api.loadPay(req,function (res) {
            data = res.data;
            $("[name='sum_money']").val(data.sum_money);
            $("[name='pay_date']").val(data.pay_date);
            $("[name='account_no']").val(data.account_no);
            $("[name='source_type']").val(data.source_type);
            form.render();//重新绘制表单，让修改生效
        });
    }

    initMenuInfo();
    /**
     * 定义表格
     * */



    /**
     * 表单提交
     * */
    $(".look").click(function () {
        var index = layui.layer.open({
            title: "参看",
            type: 2,
            content: "../ProcurementModule/editCompletedBill.html?id="+data.source_id,
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

});
