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

    }

    init();

    /**
     * 确定修改
     * */
    form.on("submit(sureUpdate)", function (data) {
        var id = 1;
        var oldPwd = data.field.psd1;
        var newPwd = data.field.psd3;
        //请求
        var url = $tool.getContext()+'SysLoginUser/changePassword.do';
        var req = {id: id,
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


    //添加学生
    $(".add_btn").click(function () {
        var index = layui.layer.open({
            title: "添加学生",
            type: 2,
            content: "addStudent.html",
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function () {
            layui.layer.full(index);
        });
        layui.layer.full(index);
    });

});
