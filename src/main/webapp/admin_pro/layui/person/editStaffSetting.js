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
        var url = $tool.getContext()+'StaffSetting/get.do';
        var req = {
            id:id
        };
        $api.GetDepartUsers(req,function (res) {
            var data = res.data;
            $("[name='id']").val(data.id);
            $("[name='name']").val(data.name);
            $("input:radio[value='" + data.sex + "']").attr('checked', 'true');
            $("[name='age']").val(data.age);
            $("[name='phone']").val(data.phone);
            $("[name='job_id']").val(data.job_id);
            $("[name='depart_id']").val(data.depart_id);
            $("[name='depart_role_id']").val(data.depart_role_id);
            form.render();//重新绘制表单，让修改生效
        });
    }

    /**
     * 表单提交
     * */
    form.on("submit(editStudent)", function (data) {
        // console.log(data)
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = data.field.id;
        var name = data.field.name;
        var sex = data.field.sex1;
        var age = data.field.age;
        var phone = data.field.phone;
        var job_id = data.field.job_id;
        var depart_id = data.field.depart_id;
        var depart_role_id = data.field.depart_role_id;
        //请求
        var url = $tool.getContext()+'StaffSetting/update.do';
        var req = {
            id:queryArgs['id'],
            name: name,
            sex: sex,
            age: age,
            phone: phone,
            job_id: job_id,
            depart_id: depart_id,
            depart_role_id: depart_role_id
        };
        alert(JSON.stringify(req));
        $api.UpdateStaffSetting(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
            if(data.data==300){
                layer.msg("修改成功！", {time: 1000}, function () {
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                });
            }else if(data.data==400){
                layer.msg("修改失败，您不是主管，没有权限！", {time: 1000}, function () {

                });
            }
        });
        return false;
    })
});
