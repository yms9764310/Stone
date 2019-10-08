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
        initMenuInfo();
    }
    init();

    function initMenuInfo() {
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];
        var url = $tool.getContext() + 'SysLoginUser/find.do';
        var req = {
            id: id
        };
        $api.GetSysUsers(req, function (res) {
            var data = res.data;
            $("[name='id']").val(data.id);
        });
    }


    /**
     * 确定修改
     * */
    form.on("submit(sureUpdate)", function (data) {
        var id = data.field.id;
        var oldPwd = data.field.psd1;
        var newPwd = data.field.psd3;
        //请求
        var url = $tool.getContext()+'SysLoginUser/changePassword.do';
        var req = {
            id: id,
            oldPwd: oldPwd,
            psd: newPwd
        };
        alert(JSON.stringify(req));
        $api.UpdatePassword(JSON.stringify(req), {contentType: "application/json;charset=UTF-8"}, function (data) {
            //top.layer.close(index);(关闭遮罩已经放在了ajaxExtention里面了)
            if(data.data==300){
                layer.msg("修改成功！", {time: 1000}, function () {
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                });
            }else if(data.data==200){
                layer.msg("原始密码错误！", {time: 1000}, function () {

                });
            }

        });
        return false;
    })

});
