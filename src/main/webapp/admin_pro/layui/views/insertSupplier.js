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
                if (searchInput==null||searchInput==""){
                    layer.msg("请选择商品",{time:1000,icon:5});
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

    /**
     * 表单提交
     *
     *  */
    //添加
    form.on("submit(insertSupplier)", function (data) {
        var creator = $("input[name='creator']").val();
        var arrName=[];
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
            supplierProductList.push({"productId":txtId.eq(i).val(),"maxNumber":txtMax.eq(i).val(),"price":txtPrice.eq(i).val()});
        }
        console.info(supplierProductList);
        var companyName = $("input[name='company_name']").val();
        var contactName = $("input[name='contact_name']").val();
        var contactPhone = $("input[name='contact_phone']").val();
        var address = $("input[name='address']").val();
        //请求
        var req = {
            creator:creator,
            supplierProductList:supplierProductList,
            state:1,
            companyName:companyName,
            contactName:contactName,
            contactPhone:contactPhone,
            address:address
        };
        // alert(JSON.stringify(req));
        console.log(JSON.stringify(req));
        $api.InsertSupplier(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function () {
            layer.msg("添加成功！",{time:1000,icon:6},function () {
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            });
        });
        return false;
    });

});
