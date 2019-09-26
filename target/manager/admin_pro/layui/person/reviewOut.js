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
        var url = $tool.getContext()+'StorePutIn/getCheckOut.do';
        var req = {
            id:id
        };
        $api.GetCheckOut(req,function (res) {
            var data = res.data;
            $("[name='product_id']").val(data.product_id);
            $("[name='product_name']").val(data.product_name);
            $("[name='number']").val(data.number);
            form.render();//重新绘制表单，让修改生效
        });
    }

    /**
     * 审核通过
     * */
    form.on("submit(review)", function (data) {
        // console.log(data)
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var product_id = data.field.product_id;
        var number = data.field.number;
        //请求
        var url = $tool.getContext()+'StorePutIn/updateCheckOut.do';
        var req = {
            id:queryArgs['id'],
            product_id:product_id,
            number: number
        };
        alert(JSON.stringify(req));
        $api.updateCheckOutSuccess(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
            if(data.data=="success"){
                layer.msg("审核通过！", {time: 1000}, function () {
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                });
            }else if(data.data=="error"){
                layer.msg("审核失败，您不是主管，没有权限！", {time: 1000}, function () {

                });
            }else if(data.data=="break"){
                layer.msg("审核失败，库存不足！", {time: 1000}, function () {

                });
            }else if(data.data=="Acquisition"){
                layer.confirm('是否创建采办事项', {icon: 3, title:'提示'}, function(index){
                    layer.closeAll("iframe");
                    //调用另外一个页面的方法


                    layer.close(index);
                });
            }
        });
        return false;
    })

    /**
     * 驳回
     * */
    form.on("submit(reject)", function (data) {
        // console.log(data)
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var product_id = data.field.product_id;
        var number = data.field.number;
        //请求
        var url = $tool.getContext()+'StorePutIn/updateCheckOut.do';
        var req = {
            id:queryArgs['id'],
            product_id:product_id,
            number: number
        };
        $api.updateCheckOutReject(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
            if(data.data=="success"){
                layer.msg("成功驳回！", {time: 1000}, function () {
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                });
            }else{
                layer.msg("驳回失败，您不是主管，没有权限！", {time: 1000}, function () {

                });
            }
        });
        return false;
    })
});
