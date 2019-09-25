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

    }

    init();

    /**
     * 定义表格
     * */

    /**
     * 表单提交
     * */
    form.on("submit(addPay)", function (data) {
        var sum_money = data.field.sum_money;
        var pay_date = data.field.pay_date;
        var account_no = data.field.account_no;
        var source_type = data.field.source_type;

        //请求
        var req = {
            sum_money:sum_money,
            pay_date:pay_date,
            account_no:account_no,
            source_type:source_type

        };
        alert(JSON.stringify(req))
        $api.AddPay(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
            console.log(data.data)
            if(data.data==1){
                layer.msg("添加成功！",{time:1000},function () {
                    layer.closeAll("iframe");//疯狂模式，关闭所有层
                    //刷新父页面
                    parent.location.reload();
                });
            }
        });
        return false;
})
});
