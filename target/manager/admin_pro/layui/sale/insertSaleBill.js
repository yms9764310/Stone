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
    /**
     * 页面初始化
     * */
    function init() {
        //初始化下拉框
        initParentMenu();
    }

    init();

    function initParentMenu() {
        $api.GetFirstClassMenus(null,function (res) {
            var data = res.data;
            if (data.length > 0) {
                var html = '<option value="">--请选择--</option>';
                for (var i = 0; i < data.length; i++) {
                    html += '<option value="' + data[i].id + '">' + data[i].name + '</option>>';
                }
                $('#parentMenu').append($(html));
                form.render();
            }
        });
    }
    /**
     * 定义表格
     * */

    /**
     * 加载角色列表
     * */
    function loadRoleList() {
        var url = $tool.getContext()+'SaleBill/listSaleBill.do';
        var req =  {
            page:1,
            limit:999
        };
    }
    /**
     * 表单提交
     * */
    form.on("submit(insertSaleBill)", function (data) {
        var address = data.field.address;
        var sale_money = data.field.sale_money;
        var deliver_date = data.field.deliver_date;
        var settle_type = data.field.settle_type;
        var customer_id = data.field.customer_id;
        //请求
        var req = {
            address:address,
            sale_money:sale_money,
            deliver_date:deliver_date,
            settle_type:settle_type,
            customer_id:customer_id,
        };
        $api.InsertSaleBill(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
            layer.msg("添加成功！",{time:1000},function () {
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            });
        });
        return false;
    })
});

