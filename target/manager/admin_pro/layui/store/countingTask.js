layui.config({
    base: $config.resUrl + 'admin_pro/layui/assets/lay/modules/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api: 'api',
    formSelects: 'formSelects-v4'
}).use(['form', 'layer', 'jquery','ajaxExtention','$tool','$api','formSelects'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery,
        $tool = layui.$tool,
        formSelects = layui.formSelects,
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

    //初始化下拉框
    $api.GetProductList(null,function (res) {
        var data = res.data;
        if(data.length > 0){
            var html = '<option value="">--请选择--</option>';
            for(var i=0;i<data.length;i++){
            // &nbsp;&nbsp;&nbsp;类型：'+data[i].modelType+'
                html += '<option value="'+data[i].id+'">'+data[i].name+'</option>>';
            }
            $('#product_id').append($(html));
            formSelects.render('select1');
        }
    });

    /**
     * 表单提交
     * */
    form.on("submit(addStudent)", function (data) {
        var check_user_id = data.field.check_user_id;
        var begin_date = data.field.begin_date;
        var end_date = data.field.end_date;
        var storeCheckTaskDetailList = layui.formSelects.value('select1','storeCheckTaskDetailList');
        // alert(JSON.stringify(storeCheckTaskDetailList));
        // var product_id = data.field.product_id;
        //请求
        var req = {
            check_user_id:check_user_id,
            begin_date:begin_date,
            end_date:end_date,
            storeCheckTaskDetailList:storeCheckTaskDetailList
        };
        $api.InsertCountingTask(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
            layer.msg("添加成功！",{time:1000},function () {
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            });
        });
        return false;
    })
});
