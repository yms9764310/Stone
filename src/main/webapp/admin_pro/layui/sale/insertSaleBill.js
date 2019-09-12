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

    $("#parentIframe").click(function () {
        layer.open({
            type:2,
            title:'添加商品',
            id:'link',
            skin:'layui-layer-rim',
            area:['75%','80%'],
            fixed: false, //不固定
            maxmin: true,
            btn:['确定','取消'],
            content:'listCustomer.html',
            yes:function (index,layero) {
                var body=layer.getChildFrame('body',index);
                var searchInput=body.find("#searchInput").val();
                //alert(searchInput[0]);
                var inputName=searchInput.split(",");
                var searchInputId=body.find("#searchInputId").val();
                var inputId=searchInputId.split(",");
                if (searchInput==null||searchInput==""){
                    layer.msg("请选择客户",{time:1000,icon:5});
                    return false;
                }
                // document.getElementById("pname").value=searchInput;
                // document.getElementById("pid").value=searchInputId;
                var g=0;
                $(".shop").empty();
                var show="";
                $(inputName).each(function (index,item) {
                    show+="<input type='text' class=\"layui-input-inline layui-input\" name='name' value='"+item+"' readonly>"
                });
                $(inputId).each(function (index,item) {
                    show+="<input type='text' name='id' value='"+item+"' hidden>"
                });
                $(".shop").append(show);
                layer.close(index);
            },
            cancel:function () {
            }
        });
    });
    /**
     * 页面初始化
     * */
    function init() {
    }

    init();

    /**
     * 定义表格
     * */

    /**
     * 加载角色列表
     * */
    function loadRoleList() {
        var url = $tool.getContext()+'SaleBill/listSaleBill.do';
        var req =  {
            page:1,
            limit:999
        };
    }
    /**
     * 表单提交
     * */
    form.on("submit(insertSaleBill)", function (data) {
        var address = data.field.address;
        var sale_money = data.field.sale_money;
        var deliver_date = data.field.deliver_date;
        var settle_type = data.field.settle_type;
        var customer_id = data.field.id;
        //请求
        var req = {
            address:address,
            sale_money:sale_money,
            deliver_date:deliver_date,
            settle_type:settle_type,
            customer_id:customer_id,
        };
        $api.InsertSaleBill(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
            layer.msg("添加成功！",{time:1000},function () {
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            });
        });
        return false;
    })
});

