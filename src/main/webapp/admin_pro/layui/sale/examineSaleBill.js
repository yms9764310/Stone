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

        var url = $tool.getContext()+'Customer/get.do';
        var req = {
            id:id
        };
        $api.GetBill(req,null,function (res) {
            var data = res.data;
            $("[name='name']").attr("value",data.name);
            $("[name='name']").attr("title",data.product_id);
            $("[name='number']").val(data.number);
            $("[name='price']").val(data.price);
            $("[name='sum_money']").val(data.sum_money);
            $("[name='creator']").val(data.creator);
            $("[name='create_date']").val(data.create_date);
            $("[name='modifier']").val(data.modifier);
            $("[name='modify_date']").val(data.modify_date);
            $("[name='bill_no']").val(data.bill_no);
            $("[name='sale_id']").val(data.sale_id);
            $("[name='address']").val(data.address);//地址
            $("[name='sale_money']").val(data.sale_money);
            $("[name='deliver_date']").val(data.deliver_date);//时间
            $("[name='settle_type']").val(data.settle_type);//时间
            $("[name='customer_id']").val(data.customer_id);//时间

            var list = data.saleBillDetailList;

            var detail='';
            for (var i = 0 ;i<list.length;i++) {
                detail +='<div class="father">\n' +
                    '           <div class="layui-inline layui-form-item">\n' +
                    '               <label class="layui-form-label">商品'+(i+1)+'</label>\n' +
                    '               <div class="layui-input-block">\n' +
                    '                   <input id="ProductName" name="name" style="border: none;" type="" readonly="readonly" value="'+list[i].name+'" title="'+list[i].product_id+'" class="ProductName'+(i+1)+'">\n' +
                    '               </div>\n' +
                    '           </div>\n' +
                    '           <div class="layui-inline layui-form-item">\n' +
                    '               <label class="layui-form-label">商品数量</label>\n' +
                    '               <div class="layui-input-block">\n' +
                    '                   <input type="number" name="number" id="productnumber" readonly="readonly" class="layui-input" min="1" value="'+list[i].number+'">\n' +
                    '               </div>\n' +
                    '           </div>\n' +
                    '    <div class="layui-inline layui-form-item">\n' +
                    '        <label class="layui-form-label">商品价格</label>\n' +
                    '        <div class="layui-input-block">\n' +
                    '            <input type="number" name="price" id="productprice" readonly="readonly" class="layui-input" min="1" value="'+list[i].price+'">\n' +
                    '        </div>\n' +
                    '    </div>\n' +
                    '\n' +
                    '    <div class="layui-inline layui-form-item">\n' +
                    '        <label class="layui-form-label">商品总价</label>\n' +
                    '        <div class="layui-input-block">\n' +
                    '            <input type="text" class="layui-input" lay-verify="required" readonly="readonly" autocomplete="off" name="sum_money" value="'+list[i].sum_money+'">\n' +
                    '        </div>\n' +
                    '    </div>\n' +
                    '</div>\n'
            }
            //加载角色列表
            //loadRoleList();
            $('.grandfather').append($(detail));
            form.render();//重新绘制表单，让修改生效
        });

        $(".examine").click(function () {
            layer.confirm('确认通过审核吗', function (confirmIndex) {
                layer.close(confirmIndex);//关闭confirm
                //向服务端发送删除指令
                var req = {
                    id:id
                };
                $api.Pass(req,function (data) {
                    layer.msg("审核通过！",{time:1000},function(){
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    });
                });
            });
        })
        $(".defeat").click(function () {
            layer.confirm('确认驳回请求吗？', function (confirmIndex) {
                layer.close(confirmIndex);//关闭confirm
                //向服务端发送删除指令
                var req = {
                    id:id
                };
                $api.Defeat(req,function (data) {
                    layer.msg("已驳回！",{time:1000},function(){
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    });
                });
            });
        })
    }
    /**
     * 加载角色列表
     * */
    function loadRoleList() {
        var url = $tool.getContext()+'SaleBill/get.do';
        var req =  {
            page:1,
            limit:999
        };
    }
});
