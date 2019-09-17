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




    form.on("submit(login)", function (data) {
        console.log(data.field);

        var account_name = $("[name='account_name']").val();
        var psd = $("[name='psd']").val();
        var req = {
            account_name:account_name,
            psd:psd
        };

        $api.Login(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {


            console.log(data.data)
            if(data.data==false){
                layer.msg("用户名或者密码错误！", {time: 1000}, function () {
                    // layer.closeAll("iframe");//疯狂模式，关闭所有层
                    //刷新父页面
                    location.reload();
                });
            }
            else{
                layer.msg("登录成功！",{time:1000},function () {
                //保存用户信息到session中
                    window.location.href = $tool.getResUrl()+"admin_pro/layui/index.html";
                });
            }

        });
        return false;
    });
});
