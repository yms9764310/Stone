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
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];
        var req = {
            id:id
        };
        $api.ListDempById(req,function (res) {
            var data = res.data;
            if (data.length > 0) {
                var html = '<option value="">--请选择--</option>';
                //selected规定选项（在首次显示在列表中时）表现为选中状态。
                for (var i = 0; i < data.length; i++) {
                    if(data[i].id == queryArgs['department_id']){
                        html += '<option value="' + data[i].id +'"' + 'selected=true' + '>' + data[i].name + '</option>';
                    }else{
                        html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                    }
                }
                $('#id').append($(html));
                form.render();
            }
        });
    }



    init()
    function initMenuInfo() {
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];
            var req = {
                id:id
        };
        $api.LoadRoleId(req,function (res) {
            var data = res.data;

            $("[name='name']").val(data.name);
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
    form.on("submit(editDempt)", function (data) {
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];
        var name = data.field.name;
        var department_id = data.field.id;
        var description= data.field.description;
        //请求

        var req = {
            id:id,
            name:name,
            department_id:department_id,
            description:description
        };

        $api.UpdateRole(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
            layer.msg("修改成功！",{time:1000},function () {
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            });

        });
        return false;
    })
});
