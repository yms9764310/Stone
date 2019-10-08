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
        var url = $tool.getContext()+'StorePutIn/getPutIn.do';
        var req = {
            id:id
        };
        $api.GetPutIn(req,function (res) {
            var data = res.data;
            $("[name='product_id']").val(data.product_id);
            $("[name='put_in_user_id']").val(data.put_in_user_id);
            $("[name='product_name']").val(data.product_name);
            $("[name='put_in_number']").val(data.put_in_number);
            $("[name='standard']").val(data.standard);
            $("[name='kind']").val(data.kind);
            $("[name='model_type']").val(data.model_type);
            $("[name='description']").val(data.description);
            $("[name='source_id']").val(data.source_id);
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
        var put_in_user_id = data.field.put_in_user_id;
        var put_in_number = data.field.put_in_number;
        var standard = data.field.standard;
        var kind = data.field.kind;
        var model_type = data.field.model_type;
        var description = data.field.description;
        var source_id = $("input[name='source_id']").val();

        var req = {
            id:queryArgs['id'],
            product_id:product_id,
            source_id:source_id,
            put_in_user_id:put_in_user_id,
            put_in_number: put_in_number,
            standard: standard,
            kind: kind,
            model_type: model_type,
            description: description
        };
        $api.updatePutInSuccess(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
            if(data.data=="success"){
                layer.msg("审核通过！", {time: 1000}, function () {
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                });
            }else if(data.data=="error"){
                layer.msg("修改失败，您不是主管，没有权限！", {time: 1000}, function () {

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
        var put_in_user_id = data.field.put_in_user_id;
        var put_in_number = data.field.put_in_number;
        var standard = data.field.standard;
        var kind = data.field.kind;
        var model_type = data.field.model_type;
        var description = data.field.description;
        //请求
        var url = $tool.getContext()+'StorePutIn/updatePutIn.do';
        var req = {
            id:queryArgs['id'],
            product_id:product_id,
            put_in_user_id:put_in_user_id,
            put_in_number: put_in_number,
            standard: standard,
            kind: kind,
            model_type: model_type,
            description: description
        };
        $api.updatePutInReject(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
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
