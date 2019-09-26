layui.config({
    base: $config.resUrl + 'admin_pro/layui/assets/lay/modules/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api: 'api'
}).use(['form', 'layer', '$api', 'jquery', 'table', 'laypage', 'laytpl', 'ajaxExtention', '$tool'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        laypage = layui.laypage,
        laytpl = layui.laytpl,
        $tool = layui.$tool,
        table = layui.table,
        $api = layui.$api;
    layer = layui.layer;
    layuiTable = layui.table;
    var tableIns;//表格实例

    /**
     * 页面初始化
     * */
    function init() {
        //初始化菜单信息
        initMenuInfo();
    }

    init();

    function initMenuInfo() {
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];

        var url = $tool.getContext() + 'SysUser/find.do';
        var req = {
            id: id
        };
        $api.GetSysUsers(req, function (res) {
            var data = res.data;
            $("[name='name']").val(data.name);
            $("input:radio[value='" + data.sex + "']").attr('checked', 'true');
            $("[name='age']").val(data.age);
            $("[name='phone']").val(data.phone);
            $("[name='job_id']").val(data.job_id);
            $("[name='depart_id']").val(data.depart_id);
            $("[name='depart_role_id']").val(data.depart_role_id);
            $("[name='max_threshold']").val(data.max_threshold);
            form.render();//重新绘制表单，让修改生效
        });
    }

    /**
     * 确定修改
     * */
    form.on("submit(sureUpdate)", function (data) {
        var id = 1;
        var name = data.field.name;
        var sex = data.field.sex1;
        var age = data.field.age;
        var phone = data.field.phone;
        var job_id = data.field.job_id;
        var depart_id = data.field.depart_id;
        var depart_role_id = data.field.depart_role_id;
        var max_threshold = data.field.max_threshold;
        //请求
        var url = $tool.getContext() + 'SysLoginUser/changePassword.do';
        var req = {
            id: id,
            name: name,
            sex: sex,
            age: age,
            phone: phone,
            job_id: job_id,
            depart_id: depart_id,
            depart_role_id: depart_role_id,
            max_threshold: max_threshold
        };
        alert(JSON.stringify(req));
        $api.UpdateMessgae(JSON.stringify(req), {contentType: "application/json;charset=UTF-8"}, function (data) {
            //top.layer.close(index);(关闭遮罩已经放在了ajaxExtention里面了)
            layer.msg("修改成功！",{time:1000},function () {
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            });

        });
        return false;
    })

});
