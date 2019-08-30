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
            content:'listProduct.html',
            yes:function (index,layero) {
                var body=layer.getChildFrame('body',index);
                var searchInput=body.find("#searchInput").val();
                //alert(searchInput[0]);
                var inputName=searchInput.split(",");
                //产品id
                var searchInputId=body.find("#searchInputId").val();
                var inputId=searchInputId.split(",");
                //最大数量
                var searchInputMax=body.find("#searchInputMax").val();
                var inputMax=searchInputMax.split(",");
                //价格
                var searchInputPrice=body.find("#searchInputPrice").val();
                var inputPrice=searchInputPrice.split(",");
                //alert(JSON.stringify(inputPrice))
                if (searchInput==null||searchInput==""){
                    layer.msg("请选择商品",{time:1000,icon:5});
                    return false;
                }
                if (searchInputMax==null||searchInputMax==""){
                    layer.msg("请填写数量",{time:2000,icon:5});
                    return false;
                }
                if (searchInputPrice==null||searchInputPrice==""){
                    layer.msg("请填写价格",{time:2000,icon:5});
                    return false;
                }
                var g=0;
                $(".shop").empty();
                var show="";
                $(inputName).each(function (index,item) {
                    show+="<input type='text' class=\"layui-input-inline layui-input\" name='name' value='"+item+"' readonly>"
                });
                $(inputId).each(function (index,item) {
                    show+="<input type='text' name='productId' value='"+item+"' hidden>"
                });
                $(inputMax).each(function (index,item) {
                    show+="<input type='text' name='maxNumber' value='"+item+"' hidden>"
                });
                $(inputPrice).each(function (index,item) {

                    show+="<input type='text' name='price' value='"+item+"' hidden>"
                });
                $(".shop").append(show);
                layer.close(index);
            },
            cancel:function () {
            }
        });
    });

    /*
       * 初始化菜单信息
       * */
    //根据id获取指定条目信息
    function init() {
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];
        //alert(JSON.stringify(admId));

        var req = {
            id: id
        };
        //findById
        $api.GetSupplier(req, function (res) {
            //获取到指定条目信息
            var data=res.data;
            console.log(data);
            //alert(JSON.stringify(data.sysPurchaseProductList));
            //alert(JSON.stringify(data));
            $(data).each(function (index,item) {
                $("input[name='id']").val(item.id);
                $("input[name='creator']").val(item.creator);
                $("input[name='company_name']").val(item.companyName);
                $("input[name='contact_name']").val(item.contactName);
                $("input[name='contact_phone']").val(item.contactPhone);
                $("input[name='address']").val(item.address);
                $(item.supplierProductList).each(function (index,list) {
                    //alert(list.productName);
                    var sys="<input type='text' class=\"layui-input-inline layui-input\" name='name' value='"+list.productName+"' readonly>";
                    $(".shop").append(sys);
                });
                $(item.supplierProductList).each(function (index,item) {
                    //alert(item.name);
                    var sysId="<input type='text' hidden='hidden' name='productId' value='"+item.productId+"'>";
                    $(".shop").append(sysId);
                });
                $(item.supplierProductList).each(function (index,item) {
                    //alert(item.name);
                    var sysId="<input type='text' hidden='hidden' name='maxNumber' value='"+item.maxNumber+"'>";
                    $(".shop").append(sysId);
                });
                $(item.supplierProductList).each(function (index,item) {
                    //lert(item.price);
                    var sysId="<input type='text' hidden='hidden' name='price' value='"+item.price+"'>";
                    $(".shop").append(sysId);
                });
            });
            // $("input[name='name']").val(data.name);
            form.render();
        });
    }
    init();

//修改提交,获取修改后的数据
    form.on("submit(updateDemo)",function (data) {

        var id=$("input[name='id']").val();
        var arrName=new Array();
        var txt=$(".shop").find($("input[name='name']"));//获取所有的文本框
        for (var i=0;i<txt.length;i++){
            arrName.push(txt.eq(i).val())//将文本框的值添加到数组中
        }
        console.info(arrName);
        var supplierProductList=new Array();
        var txtId=$(".shop").find($("input[name='productId']"));//获取所有的文本框
        var txtMax=$(".shop").find($("input[name='maxNumber']"));//获取所有的文本框
        var txtPrice=$(".shop").find($("input[name='price']"));//获取所有的文本框
        for (var i=0;i<txtId.length;i++){
            supplierProductList.push({"productId":txtId.eq(i).val(),"maxNumber":txtMax.eq(i).val(),"price":txtPrice.eq(i).val()})//将文本框的值添加到数组中
        }
        console.info(supplierProductList);
        var creator=$("input[name='creator']").val();
        var companyName=$("input[name='company_name']").val();
        var contactName=$("input[name='contact_name']").val();
        var contactPhone=$("input[name='contact_phone']").val();
        var address=$("input[name='address']").val();



        //提交的信息是否正确
        var req={
            id:id,
            arrName:arrName,
            supplierProductList:supplierProductList,
            creator:creator,
            companyName:companyName,
            contactName:contactName,
            contactPhone:contactPhone,
            address:address
        }
        //alert(JSON.stringify(req))
        $api.editSupplier(JSON.stringify(req), {contentType: 'application/json;charset=utf-8'}, function () {
            layer.confirm("确定修改信息吗?",function (confirmIndex) {
                layer.close(confirmIndex);//关闭confirm
                layer.msg("修改成功！", {time: 1000,icon:1}, function () {
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                });
            });
            //top.layer.close(index);(关闭遮罩已经放在了ajaxExtention里面了)
            //正确:修改成功
        });
        return false;
    });

});
