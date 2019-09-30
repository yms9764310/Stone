layui.config({
    base: $config.resUrl + 'admin_pro/layui/assets/lay/modules/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api: 'api'
}).use(['form', 'layer', 'jquery', 'ajaxExtention', '$tool', '$api'], function () {
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
        var url = $tool.getContext() + 'StaffSetting/get.do';
        var req = {
            id: id
        };
        $api.GetWorkSpeed(req, function (res) {
            var data = res.data;
            var finish = '';
            for (var i = 0; i < res.data.length; i++) {
                var end_date = '';
                var process_user_id = '';
                var process_type = '';
                var state = '';
                end_date += '<h3 class="layui-timeline-title">截止时间：' + res.data[i].end_date + '</h3>'
                process_user_id += ' <p>负责人：' + res.data[i].process_user_id + ' </p>'
                process_type += ' <p>工作类型：' + res.data[i].process_type + '</p>'
                state += ' <p>完成状态：' + res.data[i].state + '</p>'
                if(res.data[i].state=="已完成"){
                    finish += '<li class="layui-timeline-item">\n' +
                        '                <i class="layui-icon layui-timeline-axis">&#x1005;</i>\n' +
                        '                <div class="layui-timeline-content layui-text">\n' +
                        end_date +
                        process_user_id +
                        process_type +
                        state+
                        '</div>\n' +
                        '</li>\n'
                }else {
                    finish += '<li class="layui-timeline-item">\n' +
                        '                <i class="layui-icon layui-timeline-axis">&#x1007;</i>\n' +
                        '                <div class="layui-timeline-content layui-text">\n' +
                        end_date +
                        process_user_id +
                        process_type +
                        state+
                        '</div>\n' +
                        '</li>\n'
                }
            }
            $('.layui-timeline').append(finish);
            form.render();//重新绘制表单，让修改生效
        });
    }

});
