layui.config({
    base: $config.resUrl + 'admin_pro/layui/assets/lay/modules/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api: 'api',
}).use(['form', 'layer', '$api', 'jquery', 'table', 'laypage', 'laytpl', 'ajaxExtention', '$tool'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        laypage = layui.laypage,
        laytpl = layui.laytpl,
        $tool = layui.$tool,
        table = layui.table,
        $api = layui.$api;
    layer = layui.layer;
    layuiTable = layui.table;


    form.on("submit(rest)",function (data) {
        document.getElementById("productName").value="";
        document.getElementById("kind").value="";
        location.reload();
    });
    //根据商品名称，商品类型查询出供应商商品列表
    form.on("submit(findProduct)",function (data) {
        var productName=data.field.productName;
        var kind=data.field.kind;
        var req={
            productName:productName,
            kind:kind
        }
        if ((productName==null||productName=="")&&(kind==null||kind=="")){
            layer.msg("请输入要查询的内容!",{time:1500,icon:5});
            return false;
        }
        $api.SupplierProductLike(req,function (data) {
            var tableIns;//表格实例
            /**
             * 页面初始化
             * */
            function init() {
            }
            init();

            /**
             * 定义表格
             * */
            function defineTable() {
                tableIns = table.render({
                    elem: '#demoBill'
                    , height: 315
                    ,url:$tool.getContext() + 'procurementBill/supplierProduct.do?productName='+productName+'&kind='+kind
                    , method: 'post'
                    , page: false //开启分页
                    , cols: [[ //表头
                        {type: 'numbers', title: '序号', fixed: 'left'}
                        , {field: 'supplierName', title: '供应商', width: '10%', align: 'center'}
                        , {field: 'productName', title: '商品名称', width: '13%', align: 'center'}
                        , {field: 'kind', title: '商品类型', width: '13%', align: 'center'}
                        , {field: 'maxNumber', title: '最大数量', width: '10%', templet: '#upc', align: 'center'}
                        , {field: 'price', title: '单价', width: '13%', templet: '#upc', align: 'center'}
                    ]]

                });
            }
            defineTable();
        });
        return false;
    });



});
