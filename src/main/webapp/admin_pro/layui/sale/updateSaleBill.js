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
        //初始化菜单信息
        initMenuInfo();
    }

    init();

    /**
     * 定义表格
     * */

    function initMenuInfo() {
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];

        var url = $tool.getContext()+'Customer/get.do';
        var req = {
            id:id
        };
        $api.GetBill(req,null,function (res) {
            var data = res.data;
            $("[name='id']").val(data.id);
            $("[name='address']").val(data.address);//地址
            $("[name='deliver_date']").val(data.deliver_date);//时间
            //加载角色列表
            loadRoleList();
            form.render();//重新绘制表单，让修改生效
        });
    }
    /**
     * 加载角色列表
     * */
    function loadRoleList() {
        var url = $tool.getContext()+'SaleBill/get.do';
        var req =  {
            page:1,
            limit:999
        };
    }
    /**
     * 表单提交
     * */
    form.on("submit(editCustomer)", function (data) {
        // console.log(data)
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];
        var address = data.field.address;
        var deliver_date = data.field.deliver_date;
        //请求
        var url = $tool.getContext()+'SaleBill/updateSaleBill.do';
        var req = {
            id:id,
            address:address,
            deliver_date:deliver_date,
        };
        $api.UpdateSaleBill(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
            layer.msg("修改成功！",{time:1000},function () {
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            });
        });
        return false;
    })
});
