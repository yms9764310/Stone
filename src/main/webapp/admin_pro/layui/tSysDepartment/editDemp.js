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
                    if(data[i].id == queryArgs['parent_id']){
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
        var url = $tool.getContext()+'demp/loaddemp.do';
            var req = {
                id:id
        };
        $api.LoadDempId(req,function (res) {
            var data = res.data;

            $("[name='name']").val(data.name);
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
        var  did= queryArgs['id'];
        var create_date1 = queryArgs['newstr'];
        var create_date=create_date1.replace("_"," ");
        var name = data.field.name;
        var id = data.field.id;
        //请求

        var req = {
            create_date:create_date,
            did:did,
            name:name,
            id:id
        };

        $api.UpdateDemp(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
            if(data.data==0){
                layer.msg("已经有该部门名称！", {time: 1000}, function () {
                    // layer.closeAll("iframe");
                    //刷新父页面
                    location.reload();
                });
            }
            else{
                layer.msg("修改成功！",{time:1000},function () {
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                });
            }
        });
        return false;
    })
});
