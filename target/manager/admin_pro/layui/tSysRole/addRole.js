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
        $api.ListDempByName(null,function (res) {
            var data = res.data;
            if (data.length > 0) {
                var html = '<option value="">--请选择--</option>';
                for (var i = 0; i < data.length; i++) {
                    html += '<option value="' + data[i].id + '">' + data[i].name + '</option>>';
                }
                $('#id').append($(html));
                form.render();
            }
        });
    }

    init();

    /**
     * 定义表格
     * */

    /**
     * 表单提交
     * */
    form.on("submit(addProu)", function (data) {
        var id = data.field.id;
        var name = data.field.name;
        var description = data.field.description;
        //请求
        var req = {
            id:id,
            name:name,
            description:description
        };

        $api.AddRole(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
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
