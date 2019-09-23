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
    form.on("submit(addProu)", function (data) {
        var name = data.field.name;
        var kind = data.field.kind;
        var model_type = data.field.model_type;
        var standard = data.field.standard;
        var description = data.field.description;
        //请求
        var req = {
            name:name,
            kind:kind,
            model_type:model_type,
            standard:standard,
            description:description
        };

        $api.AddProu(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
            console.log(data.data)
               if(data.data=1){
                layer.msg("添加成功！",{time:1000},function () {
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                });
               }
        });
        return false;
})
});
