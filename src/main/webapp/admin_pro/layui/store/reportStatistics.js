layui.config({
    base: $config.resUrl + 'admin_pro/layui/assets/lay/modules/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api: 'api',
    echarts:'echarts.min' //echarts 核心包

}).use(['form', 'layer', '$api', 'jquery', 'table', 'laypage', 'laytpl', 'ajaxExtention', '$tool','echarts'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        laypage = layui.laypage,
        laytpl = layui.laytpl,
        $tool = layui.$tool,
        table = layui.table,
        $api = layui.$api;
    layer = layui.layer;
    echarts = layui.echarts;
    layuiTable = layui.table;
    var tableIns;//表格实例

    //查询
    form.on("submit(queryUser)", function (data) {
        var startTime = data.field.start_time;
        var endTime = data.field.end_time;
        //表格重新加载
        echarts.reload({
            where: {
                startTime: startTime,
                endTime: endTime,
            }

        });
        return false;
    });

});
layui.define('echarts.min',function(exports){exports('echarts.min',echarts)});
