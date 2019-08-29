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

    /*
       * 初始化菜单信息
       * */
    function init() {
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];
        //alert(JSON.stringify(admId));

        var req = {
            id: id
        };
        //findById
        $api.LookProduct(req, function (res) {
            //获取到指定条目信息
            var data=res.data;
            //alert(JSON.stringify(data));
            console.log(data);
            //alert(JSON.stringify(data.productName));
            $(data).each(function (index,item) {
                //alert(item.name);
                var sysProduct="<input type='text' class=\"layui-input-inline layui-input\" name='productName' value='"+item.productName+"' readonly>";
                $(".shop").append(sysProduct);
            });
            form.render();
        });
    }
    init();

});
