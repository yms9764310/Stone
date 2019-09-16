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
        //初始化菜单信息
        initMenuInfo();
    }
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

    init();

    function initMenuInfo() {
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];
        var req = {
            id:id
        };

        $api.GetCheckTask(req,function (res) {
            var data = res.data;
            // $("[name='id']").val(data.id);
            $("[name='check_user_id']").val(data.check_user_id);
            $("[name='begin_date']").val(data.begin_date);
            $("[name='end_date']").val(data.end_date);

            $(data.storeCheckTaskDetailList).each(function (index,item) {
                //alert(item.sysPurchaseProduct.name);
                layui.formSelects.value('select1',[item.product_id],true);
            });
            menu_roleIds = data.roleIdList;//保存菜单所属角色id列表，初始化选中时用
            //加载角色列表
            loadRoleList();
            form.render();//重新绘制表单，让修改生效
        });
    }
    /**
     * 加载角色列表
     * */
    function loadRoleList() {
        var url = $tool.getContext() + 'StoreManagement/findUsersName.do';
        var req = {
            page: 1,
            limit: 999
        };

    }

    /**
     * 表单提交
     * */
    form.on("submit(editCountingTask)", function (data) {
        var queryArgs = $tool.getQueryParam();
        var id = queryArgs['id'];
        var check_user_id = data.field.check_user_id;
        var begin_date = data.field.begin_date;
        var end_date = data.field.end_date;
        var storeCheckTaskDetailList = layui.formSelects.value('select1','storeCheckTaskDetailList');
        // alert(JSON.stringify(storeCheckTaskDetailList));
        // var product_id = data.field.product_id;
        //请求
        var req = {
            id:id,
            check_user_id:check_user_id,
            begin_date:begin_date,
            end_date:end_date,
            storeCheckTaskDetailList:storeCheckTaskDetailList
        };
        $api.UpdateCountingTask(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
            if(data.data=="success") {
                layer.msg("修改成功！", {time: 1000}, function () {
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                });
            }else if(data.data=="error"){
                layer.msg("修改失败，您不是主管，没有权限！", {time: 1000}, function () {

                });
            }
        });
        return false;
    })
});
