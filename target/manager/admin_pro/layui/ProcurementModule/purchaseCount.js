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

    var show = '<option value="">请选择要统计的商品</option>'; //全局变量
    $.ajax({
        url:$tool.getContext() + 'procurementBill/listPurchaseBillAudited.do',//数据接口
        data:{},
        dataType:"json",
        type:'post',
        async:false,
        contentType : "application/json",
        success:function (result) {
            console.log(result.data);
            for (var x in result.data){
                //alert(JSON.stringify(result.data[x].name));
                show+='<option value="'+result.data[x].productId+'">'+result.data[x].productName+'</option>';
            }
            $("#userName").html(show);
        }
    });
    layui.form.render('select');//需要渲染一下



    $.ajax({
        url:$tool.getContext() + 'procurementBill/countPurchase.do'//数据接口
        ,data:{}
        ,dataType:"json"
        ,type:'post'
        ,async:true
        ,contentType : "application/json"
        ,success:function (count) {
            console.log(count.data);
            //alert(JSON.stringify(count.data));
            var createDates=[];//类别数组,实际是存放x轴坐标值
            var moneys=[];//金额数组,实际是存放y轴坐标值
            var kinds=[];//种类数组，实际是存放y轴坐标值
            var nums=[];//数量数组，实际是存放y轴坐标值
            $(count.data).each(function (index,item) {
                createDates.push(item.createDate);
                moneys.push(item.money);
                kinds.push(item.kind);
                nums.push(item.numberTotal);
            });
            myChart.hideLoading();    //隐藏加载动画
            myChart.setOption({     //加载数据图表
                xAxis:{
                    data:createDates
                },
                series:[{
                    name:'金额',
                    data:moneys
                },
                    {
                        name:'种类',
                        data:kinds
                    },
                    {
                        name:'数量',
                        data:nums
                    }]
            });
        }
    });

    form.on('select(renyuan)',function (data1) {
        var productId=data1.value;
        $.ajax({
            url:$tool.getContext() + 'procurementBill/countPurchase.do'//数据接口
            ,data:{}
            ,dataType:"json"
            ,type:'post'
            ,async:true
            ,contentType : "application/json"
            ,success:function (count) {
                console.log(count.data);
                alert(JSON.stringify(count.data));
                var createDates=[];//类别数组,实际是存放x轴坐标值
                var moneys=[];//金额数组,实际是存放y轴坐标值
                var kinds=[];//种类数组，实际是存放y轴坐标值
                var nums=[];//数量数组，实际是存放y轴坐标值
                $(count.data).each(function (index,item) {
                    createDates.push(item.createDate);
                    moneys.push(item.money);
                    kinds.push(item.kind);
                    nums.push(item.numberTotal);
                });
                myChart.hideLoading();    //隐藏加载动画
                myChart.setOption({     //加载数据图表
                    xAxis:{
                        data:createDates
                    },
                    series:[{
                        name:'金额',
                        data:moneys
                    },
                        {
                            name:'种类',
                            data:kinds
                        },
                        {
                            name:'数量',
                            data:nums
                        }]
                });
            }
        });
    });
});
