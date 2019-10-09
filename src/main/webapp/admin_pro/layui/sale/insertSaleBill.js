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
        //初始化下拉框
        initParentMenu();
    }

    init();


    /**
     * 定义表格
     * */
    function initParentMenu() {
        //初始化下拉框
        $api.GetSaleBillName(null,function (res) {
            var data = res.data;
            if(data.length > 0){
                var html = '';
                for(var i=0;i<data.length;i++){
                    html += '<option value="'+data[i].id+'">'+data[i].name+'</option>>';
                }
                $('#ProductName').append($(html));
                form.render();
            }
        });
        $api.GetCusChoose(null,function (res) {
            var data = res.data;
            if (data.length > 0) {
                var html = '<option value="">--请选择--</option>';
                for (var i = 0; i < data.length; i++) {
                    html += '<option value="' + data[i].id + '">' + data[i].name + '</option>>';
                }
                $('#parentMenu').append($(html));
                form.render();
            }
        });
    }

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
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var sale_bill_id = queryArgs['id'];
        var address = data.field.address;
        var sale_money = data.field.sale_money;
        var deliver_date = data.field.deliver_date;
        var settle_type = data.field.settle_type;
        var customer_id = data.field.customer_id;
        var product = $('.grandfather').children('div');
        var saleBillDetailList = [];
        for (var i = 0; i < product.length; i++) {
            //获取商品ID
            var productName = $($($(product[i]).children('div')[0]).children('.layui-input-block')[0]).children("select").find("option:selected").val();
            //获取商品数量
            var productNumber =  $($($($(product[i]).children('div')[1]).children('.layui-input-block')[0]).children('input')[0]).val();
            //获取商品单价
            var productPrice = $($($($(product[i]).children('div')[2]).children('.layui-input-block')[0]).children('input')[0]).val();
            //获取总价格
            var sum_money = $($($($(product[i]).children('div')[3]).children('.layui-input-block')[0]).children('input')[0]).val();
            //获取单位
            /*var unit=$($($($(product[i]).children('div')[4]).children('.layui-input-block')[0]).children('input')[0]).val();*/
            var saleBillDetail = {product_id:productName,price:productPrice,number:productNumber,sum_money:sum_money};
            saleBillDetailList.push(saleBillDetail);
        }
        //请求
        var req = {
            sale_bill_id:sale_bill_id,
            saleBillDetailList:saleBillDetailList,
            address:address,
            sale_money:sale_money,
            deliver_date:deliver_date,
            settle_type:settle_type,
            customer_id:customer_id,
        };
        alert(JSON.stringify(req));
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
function zAppend(a){
    var id =$($(a.prev()).children()).length+1;
    $(a.prev()).append(
        '<div class="father">\n' +
        '           <div class="layui-inline layui-form-item">\n' +
        '               <label class="layui-form-label">商品名称'+id+'</label>\n' +
        '               <div class="layui-input-block">\n' +
        '                   <select id="ProductName" name="name" type="" class="ProductName'+id+'">\n' +
        '                       <option value="">--请选择--</option>\n' +
        '                   </select>\n' +
        '               </div>\n' +
        '           </div>\n' +
        '           <div class="layui-inline layui-form-item">\n' +
        '               <label class="layui-form-label">商品数量</label>\n' +
        '               <div class="layui-input-block">\n' +
        '                   <input type="number" name="number" id="productnumber" class="layui-input" min="1" >\n' +
        '               </div>\n' +
        '           </div>\n' +
        '    <div class="layui-inline layui-form-item">\n' +
        '        <label class="layui-form-label">商品价格</label>\n' +
        '        <div class="layui-input-block">\n' +
        '            <input type="number" name="price" id="productprice" class="layui-input" min="1" >\n' +
        '        </div>\n' +
        '    </div>\n' +
        '\n' +
        '    <div class="layui-inline layui-form-item">\n' +
        '        <label class="layui-form-label">商品总价</label>\n' +
        '        <div class="layui-input-block">\n' +
        '            <input type="text" class="layui-input" lay-verify="required" autocomplete="off" name="sum_money">\n' +
        '        </div>\n' +
        '    </div>\n' +
        /*'     <div class="layui-form-item parent-menu">\n' +
        '        <label class="layui-form-label">单位</label>\n' +
        '        <div class="layui-input-block">\n' +
        '            <input type="text" class="layui-input" lay-verify="required" autocomplete="off" name="unit" placeholder="请输入单位">\n' +
        '        </div>\n' +
        '     </div>'+*/
        '</div>\n'
    );
    layui.form.render("select");
    initProductName();
}
function initProductName() {
    $api.GetSaleBillName(null,function (res) {
        var data = res.data;
        if(data.length > 0){
            var html = '';
            for(var i=0;i<data.length;i++){
                html += '<option value="'+data[i].id+'">'+data[i].name+'</option>>';
            }
            var id =$($('.grandfather').children()).length;
            $('.ProductName'+id).append($(html));
            layui.form.render();
        }
    });
}


