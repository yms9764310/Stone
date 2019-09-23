layui.config({
    base: $config.resUrl + 'admin_pro/layui/assets/lay/modules/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api: 'api',
    formSelects: 'formSelects-v4'
}).use(['form', 'layer', 'jquery','ajaxExtention','$tool','$api','laydate','formSelects'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery,
        $tool = layui.$tool,
        laydate=layui.laydate,
        formSelects = layui.formSelects,
        $api = layui.$api;

    laydate.render({
        elem:'#ruKu',
        type:'datetime',
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
        $api.LoadBill(req, function (res) {
            //获取到指定条目信息
            var data=res.data;
            //alert(JSON.stringify(data));
            $("input[name='id']").val(data.id);
            $("input[name='creator']").val(data.creator);
            $("input[name='putInDate']").val(data.putInDate);
            $("input[name='emergent'][value='3']").attr("checked",data.emergent=='3' ? true:false);
            $("input[name='emergent'][value='4']").attr("checked",data.emergent=='4' ? true:false);
            $(data.purchaseBillDetailList).each(function (index,item) {
                //alert(item.sysPurchaseProduct.name);
                layui.formSelects.value('select1',[item.sysPurchaseProduct.id],true);
            });
            console.log(data);
            form.render();
        });
    }
    init();

    var productSupplier;
    var htmls = '<option value="">请选择商品</option>'; //全局变量
    $.ajax({
        url:$tool.getContext() + 'procurementBill/listSysPurchaseProduct.do',//数据接口
        data:{},
        dataType:"json",
        type:'post',
        async:false,
        contentType : "application/json",
        success:function (result) {
            productSupplier=result.data;
            console.log(result.data);
            for (var x in productSupplier){
                htmls+='<option value="'+productSupplier[x].id+'">'+productSupplier[x].name+'</option>'
            }
            $("#productValue").html(htmls);
        }
    });
    formSelects.render('select');//需要渲染一下

    //追加采办商品
    // $("#parentDiv").click(function () {
    //     var show='<div class="layui-form-item parent-menu" ><label class="layui-form-label">采办商品:</label>' +
    //         '<div class="layui-input-inline"><select name="productId" id="productValue" lay-verify="required" required>';
    //     var htmls = '<option value="" selected="selected">请选择商品</option>'
    //     for (var x in productSupplier) {
    //         htmls += '<option value="' + productSupplier[x].id + '">' + productSupplier[x].name + '</option>';
    //     }
    //     show+=htmls;
    //     show+='</select></div>';
    //     show+='</div>';
    //     $("#insert").append(show);
    //     layui.form.render("select");
    // });


    form.on("submit(updateBill)",function (data) {
        var id=$("input[name='id']").val();
        var creator=$("input[name='creator']").val();
        var putInDate=$("input[name='putInDate']").val();
        var emergent=$("input[name='emergent']:checked").val();
        if (emergent==null||emergent==''){
            layer.msg("是否紧急未选择!",{time:1000,icon:5});
            return false;
        }
        var purchaseBillDetailList=formSelects.value('select1');
        //获取select父类长度
        // var productId=$('.insert').children('.parent-menu');
        //alert(productId.length);
        //遍历获取select选中的值
        // var purchaseBillDetailList=[];
        // for (var i=0;i<productId.length;i++){
        //     var productName=$($(productId[i]).children('.layui-input-inline')[0]).children('select').find("option:selected").val();
        //     //alert(productName);
        //     purchaseBillDetailList.push({productId:productName});
        // }
        //alert(JSON.stringify(purchaseBillDetailList));
        var req={
            id:id,
            creator:creator,
            putInDate:putInDate,
            emergent:emergent,
            purchaseBillDetailList:purchaseBillDetailList
        };
        console.log(JSON.stringify(req));
        layer.confirm("确定修改吗?",function (confirmIndex) {
            layer.close(confirmIndex);//关闭confirm
            $api.UpdatePurchaseBill(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function () {
                layer.msg("编辑成功！",{time:1000,icon:6},function () {
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                });
            });
        });
        return false;
    });
});
