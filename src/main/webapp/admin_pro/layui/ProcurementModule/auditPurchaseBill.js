layui.config({
    base: $config.resUrl + 'admin_pro/layui/assets/lay/modules/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api: 'api'
}).use(['form', 'layer', 'jquery','ajaxExtention','$tool','$api','laydate'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery,
        $tool = layui.$tool,
        laydate=layui.laydate,
        $api = layui.$api;

    laydate.render({
        elem:'#ruKu',
        type:'datetime',
        trigger: 'click'
    });
    laydate.render({
        elem:'#expect',
        type:'datetime',
        trigger: 'click'
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
            console.log(data);
            $("input[name='id']").val(data.id);
            $("input[name='creator']").val(data.creator);
            $("input[name='putInDate']").val(data.putInDate);
            $("input[name='emergent'][value='13']").attr("checked",data.emergent=='13' ? true:false);
            $("input[name='emergent'][value='14']").attr("checked",data.emergent=='14' ? true:false);
            var productDiv='';
            $(data.purchaseBillDetailList).each(function (index,item) {
                //alert(JSON.stringify(item.sysPurchaseProduct.id));
                productDiv+='<div class="layui-form-item parent-menu"><label class="layui-form-label">采办商品:</label><div class="layui-input-inline">'
                +'<input type="text" class="layui-input" value="'+ item.sysPurchaseProduct.name +'" lay-verify="required" name="name" placeholder="" readonly>'
                +'<input type="text" hidden value="'+ item.sysPurchaseProduct.id +'" lay-verify="required" name="productId" placeholder="" readonly>'
                +'</div>';
                productDiv+='<label class="layui-form-label la">厂家:</label>\n' +
                    '               <div class="layui-input-inline supplier">\n' +
                    '                   <select name="supplier_id" id="supplierName" lay-filter="fangxiang" class="supplierName" lay-verify="required" required >\n';
                productDiv+= '<option value="">请选择厂家</option>';
                $(item.sysPurchaseProduct.supplierProductList).each(function (index,item) {
                    productDiv+='<option value="'+item.id+'">'+item.name+'</option>';
                });
                productDiv+='</select></div>';
                productDiv+='<label class="layui-form-label la">单价:</label>\n' +
                    '               <div class="layui-input-inline">\n' +
                    '                   <input type="text" name="price" class="layui-input price"  placeholder="0" readonly lay-verify="required" >\n' +
                    '               </div>';
                productDiv+='<label class="layui-form-label la">数量:</label>' +
                    '               <div class="layui-input-inline">' +
                    '                  <input  type="text" class="layui-input number" name="number" placeholder="0" lay-verify="required" >' +
                    '               </div>';
                productDiv+='<label class="layui-form-label la">金额:</label><div class="layui-input-inline">' +
                    '<input type="text" class="layui-input" placeholder="0" name="sum_money" lay-verify="required" readonly></div>';
                productDiv+='</div>';
            });
            $(".product").append(productDiv);
                layui.form.render('select');//需要渲染一下
            form.render();
            $(".number").blur(function () {
                //alert(JSON.stringify($(this).parent().prev().prev().children(".price").val()));
                //alert(JSON.stringify($(this).val()));
                //单价
                var txtPrice=$(this).parent().prev().prev().children(".price").val();
                var reg = new RegExp(/^\d*[\.]?\d*$/);
                if (!reg.test(txtPrice)){
                    layer.msg("单价非法!",{time:1200,icon:5});
                    return false;
                }
                //数量
                var txtAmount=$(this).val();
                var rege = new RegExp(/^\d*$/);
                if (!rege.test(txtAmount)){
                    layer.msg("数量非法!",{time:1200,icon:5});
                    return false;
                }
                //金额
                $(this).parent().next().next().children(".layui-input").val(txtPrice*txtAmount);
                var len=$(".product").children(".parent-menu");
                var sum=0;
                for (var j=0;j<len.length;j++){
                    var money=$(len[j]).children().eq(9).children().eq(0).val();
                    sum+=parseInt(money);
                }
                $("#txtTotal").val(sum);
            });
        });
    }
    init();



    var show = '<option value="">请分配采购人员</option>'; //全局变量
    $.ajax({
        url:$tool.getContext() + 'procurementBill/listSysUsersName.do',//数据接口
        data:{},
        dataType:"json",
        type:'post',
        async:false,
        contentType : "application/json",
        success:function (result) {
            console.log(result.data);
            for (var x in result.data){
                //alert(JSON.stringify(result.data[x].name));
                show+='<option value="'+result.data[x].id+'">'+result.data[x].name+'</option>'
            }
            $("#userName").html(show);
        }
    });
    layui.form.render('select');//需要渲染一下

    form.on("select(fangxiang)",function (data1) {
        var purchaseSupplierId=data1.value;
        //alert(JSON.stringify(purchaseSupplierId))
        var productId=$(data1.elem).parent().prev().prev().children().eq(1).val();
        if (purchaseSupplierId==null||purchaseSupplierId=="") {
            $(data1.elem).parent().next().next().children().eq(0).val(0);
            return false;
        }
        $.ajax({
            type:'post',
            url:$tool.getContext()+'procurementBill/supplierProductPrice.do?purchaseSupplierId='+purchaseSupplierId+'&productId='+productId,
            data:{},
            success:function (resultPrice) {
                //alert(JSON.stringify(resultPrice.data));
                $(resultPrice.data).each(function (index,item) {
                    $(data1.elem).parent().next().next().children().eq(0).val(item.price);
                });
                var txtPrice=$(data1.elem).parent().next().next().children().eq(0).val();
                var txtAmount=$(data1.elem).parent().next().next().next().next().children().eq(0).val();
                $(data1.elem).parent().next().next().next().next().next().next().children().eq(0).val(txtPrice*txtAmount);
                var len=$(".product").children(".parent-menu");
                var sum=0;
                for (var j=0;j<len.length;j++){
                    var money=$(len[j]).children().eq(9).children().eq(0).val();
                    sum+=parseInt(money);
                }
                $("#txtTotal").val(sum);
            }
        });
    });


    form.on("submit(updateBill)",function (data) {
        var id=$("input[name='id']").val();
        var creator=$("input[name='creator']").val();
        var putInDate=$("input[name='putInDate']").val();
        var emergent=$("input[name='emergent']:checked").val();
        if (emergent==null||emergent==''){
            layer.msg("是否紧急未选择!",{time:1000,icon:5});
            return false;
        }
        //alert($(".product").children(".parent-menu").length)
        var len=$(".product").children(".parent-menu");
        var purchaseBillDetailList=new Array();
        for (var i=0;i<len.length;i++){
            var productId=$(len[i]).children().eq(1).children().eq(1).val();
            var supplierId=$(len[i]).children().eq(3).children().eq(0).val();
            var price=$(len[i]).children().eq(5).children().eq(0).val();
            var number=$(len[i]).children().eq(7).children().eq(0).val();
            var sumMoney=$(len[i]).children().eq(9).children().eq(0).val();
            purchaseBillDetailList.push({"productId":productId,"supplierId":supplierId,"price":price,"number":number,"sumMoney":sumMoney});
        }
        if (number==0||number==""){
            layer.msg("数量未填!",{time:1300,icon:5});
            return false;
        }
        var purchaseId=$(".layui-form").children().eq(6).children().eq(1).children().eq(0).val();
        var expectDate=$("input[name='expect_date']").val();
        var sum=$("#txtTotal").val();
        var req={
            id:id,
            creator:creator,
            putInDate:putInDate,
            emergent:emergent,
            purchaseId:purchaseId,
            expectDate:expectDate,
            sumMoney:sum,
            purchaseBillDetailList:purchaseBillDetailList
        };
        console.log(req);
        //alert(JSON.stringify(req))
        layer.confirm("审核确定通过吗?",function (confirmIndex) {
            layer.close(confirmIndex);//关闭confirm
            $api.UpdatePurchaseBillAudit(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function () {
                layer.msg("审核成功！",{time:1000,icon:6},function () {
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                });
            });
        });
        return false;
    });


});

