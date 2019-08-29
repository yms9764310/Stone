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




    function initMenuInfo() {
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];
        var url = $tool.getContext()+'prou/loadprou.do';
            var req = {
                id:id
        };
        $api.LoadProuId(req,function (res) {
            var data = res.data;
            $("[name='name']").val(data.name);
            $("[name='kind']").val(data.kind);
            $("[name='model_type']").val(data.model_type);
            $("[name='standard']").val(data.standard);
            $("[name='description']").val(data.description);
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
    form.on("submit(editProu)", function (data) {

        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];
        var create_date1 = queryArgs['newstr'];
        //替换
        var create_date=create_date1.replace("_"," ");
        var name = data.field.name;
        var kind = data.field.kind;
        var model_type = data.field.model_type;
        var standard = data.field.standard;
        var description = data.field.description;
        //请求
        var req = {
            id:id,
            name:name,
            kind:kind,
            model_type:model_type,
            standard:standard,
            description:description,
            create_date:create_date
        };

        $api.UpdateProu(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
                layer.msg("修改成功！",{time:1000},function () {
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                });

        });
        return false;
    })
});
