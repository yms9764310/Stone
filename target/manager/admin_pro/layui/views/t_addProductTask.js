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
        $tool = layui.$tool,F
        $api = layui.$api;
   laydate = layui.laydate;

    /**
     * 页面初始化
     * */
    //日期时间选择器
    laydate.render({
        elem: '#test5'
        ,type: 'datetime'
    });
    laydate.render({
        elem: '#test6'
        ,type: 'datetime'
    });
    function init() {
        // //初始化下拉框
        // $api.GetProductName(null,function (res) {
        //     var data = res.data;
        //     if(data.length > 0){
        //         var html = '';
        //         for(var i=0;i<data.length;i++){
        //             html += '<option value="'+data[i].id+'">'+data[i].name+'</option>>';
        //         }
        //         $('#ProductName').append($(html));
        //         form.render();
        //     }
        // });
        getList();
    }



    init();

    /**
     * 定义表格
     * */

    /**
     * 加载角色列表
     * */
    function loadRoleList() {
        // var url = $tool.getContext()+'Student/StudentList.do';
        // var req =  {
        //     page:1,
        //     limit:999
        // };
    }


    /**
     * 表单提交
     * */
    form.on("submit(addProductTask)", function (data) {
        var begin_date = data.field.begin_date;
        var end_date = data.field.end_date;
        var description = data.field.description;
        var zujian = $('.grandfather').children('div');
        var listTaskDetail = [];
        var DetailId = [];
        for(var i=0;i<zujian.length;i++){
                var DetailName = $($($(zujian[i]).children('div')[0]).children('.layui-input-block')[0]).children("select").find("option:selected").val();
                // alert(DetailName);
                var DetailNumber =  $($($($(zujian[i]).children('div')[1]).children('.layui-input-block')[0]).children('input')[0]).val();
                // alert(DetailNumber);
            DetailId.push(DetailName)
            var TaskDetail = {product_id:DetailName,number:DetailNumber};
            listTaskDetail.push(TaskDetail);
        }

        //请求
        var req = {
            begin_date:begin_date,
            end_date:end_date,
            description:description,
            listTaskDetail:listTaskDetail
        };
        for (var i=0;i<DetailId.length-1;i++){
            for(var j = 0 ; j<DetailId.length-i-1;j++){
                if (DetailId[j]==DetailId[j+1]){
                    layer.msg("有重复的，笨比！",{time:1000},function () {
                        // window.location.reload()//截止  不能往下运行  弹框提示数据不合法
                    });
                }else {

                }
            }
        }
        alert(JSON.stringify(req));
        $api.Insert_ys_ProductTask(JSON.stringify(req),{contentType:'application/json;charset=utf-8'},function (data) {
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
    $(a.prev()).append('<div class="father">' +
        ' <div class="layui-inline layui-form-item">\n' +
        '               <label class="layui-form-label">商品'+id+'</label>\n' +
        '               <div class="layui-input-block">\n' +
        '                   <select name="name" type="" class="ProductMaterialName'+id+'">\n' +
        '                       <option value="">--请选择--</option>\n' +
        '                   </select>\n' +
        '               </div>\n' +
        '           </div>\n' +
        '           <div class="layui-inline layui-form-item">\n' +
        '               <label class="layui-form-label">组件数量</label>\n' +
        '               <div class="layui-input-block">\n' +
        '                   <input type="number" name="number" id="number1" class="layui-input" min="1" >\n' +
        '               </div>\n' +
        '               </div>\n' +
        '           </div>');

    // "<div class=\"layui-inline layui-form-item\">\n" +
    // "               <label class=\"layui-form-label\">商品组件"+id+"</label>\n" +
    // "               <div class=\"layui-input-block\">\n" +
    // "                   <select  name=\"name\" type=\"\" class=\"ProductMaterialName\">\n" +
    // "                       <option value=\"\">--请选择--</option>\n" +
    // "                   </select>\n" +
    // "               </div>\n" +
    // "           </div>\n" +
    // "           <div class=\"layui-inline layui-form-item\">\n" +
    // "               <label class=\"layui-form-label\">商品组件个数</label>\n" +
    // "               <div class=\"layui-input-block\">\n" +
    // "                   <input type=\"number\" name=\"number\" id=\"number1\" class=\"layui-input\" min=\"1\" >\n" +
    // "               </div>\n" +
    // "           </div>"
    //     zbz += '<div class="zbz"></div>';
    // var zbz = '';
    // for(var i =0;i<item.length;i++){
    // }
    // var bz = '<div class="bz"><div class="zbzColl">'+zbz+'</div></div>';
    getList();
    // /layui.form.render();
}

function getList() {
    $api.GetProductBomName(null,function (res) {
        var data = res.data;
        if(data.length > 0){
            var html = '';
            for(var i=0;i<data.length;i++){
                html += '<option value="'+data[i].product_id+'">'+data[i].name+'</option>>';
            }
            var id =$($('.grandfather').children()).length;
            $('.ProductMaterialName'+id).append($(html));
            layui.form.render();
        }
    });
}
