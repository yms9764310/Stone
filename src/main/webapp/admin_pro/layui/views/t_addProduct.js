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
     * 加载角色列表
     * */
    function loadRoleList() {
        // var url = $tool.getContext()+'Student/StudentList.do';
        // var req =  {
        //     page:1,
        //     limit:999
        // };
    }
    /**
     * 表单提交
     * */
    form.on("submit(addProduct)", function (data) {
        var name = data.field.name;
        var kind = data.field.kind;
        var model_type = data.field.model_type;
        var standard = data.field.standard;
        var description = data.field.description;
        //请求
        var req = {
            creator:2,
            name:name,
            kind:kind,
            model_type:model_type,
            standard:standard,
            description:description
        };
        $api.Insert_ys_Product(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
            layer.msg("添加成功！",{time:1000},function () {
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            });
        });
        return false;
    })
});
