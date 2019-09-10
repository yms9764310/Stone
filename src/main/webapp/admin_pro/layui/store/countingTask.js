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

    //初始化下拉框
    $api.GetUsersName(null,function (res) {
        var data = res.data;
        if(data.length > 0){
            var html = '<option value="">--请选择--</option>';
            for(var i=0;i<data.length;i++){
                html += '<option value="'+data[i].id+'">'+data[i].name+'</option>>';
            }
            $('#check_user_id').append($(html));
            form.render();
        }
    });

    /**
     * 定义表格
     * */

    /**
     * 加载角色列表
     * */
    function loadRoleList() {
        var url = $tool.getContext()+'Student/StudentList.do';
        var req =  {
            page:1,
            limit:999
        };
    }
    /**
     * 表单提交
     * */
    form.on("submit(addStudent)", function (data) {
        var sname = data.field.sname;
        var sex = data.field.sex;
        var clazz = data.field.clazz;
        var password = data.field.password;
        //请求
        var req = {
            sname:sname,
            sex:sex,
            clazz:clazz,
            password:password
        };
        $api.AddStudent(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
            layer.msg("添加成功！",{time:1000},function () {
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            });
        });
        return false;
    })
});
