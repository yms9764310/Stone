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
        $api.GetBill(req,function (res) {
            var data = res.data;
            $("[name='id']").val(data.id);
            $("[name='creator']").val(data.creator);
            $("[name='create_date']").val(data.create_date);
            $("[name='modifier']").val(data.modifier);
            $("[name='modify_date']").val(data.modify_date);
            $("[name='bill_no']").val(data.bill_no);
            $("[name='sale_id']").val(data.sale_id);
            $("[name='address']").val(data.address);//地址
            $("[name='sale_money']").val(data.sale_money);
            $("[name='deliver_date']").val(data.deliver_date);//时间
            $("[name='settle_type']").val(data.settle_type);//时间
            $("[name='customer_id']").val(data.customer_id);//时间
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
});
