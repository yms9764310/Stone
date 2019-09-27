layui.config({
    base: $config.resUrl + 'admin_pro/layui/assets/lay/modules/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api: 'api',
}).use(['form', 'layer', '$api', 'jquery', 'table', 'laypage', 'laytpl', 'ajaxExtention', '$tool','laydate'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        laypage = layui.laypage,
        laytpl = layui.laytpl,
        $tool = layui.$tool,
        table = layui.table,
        $api = layui.$api,
    laydate=layui.laydate,
    layer = layui.layer,
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



laydate.render({
    elem:'#year',
    type:'year',
    format:'yyyy',//指定时间格式
    value:new Date(),
    done:function (value,date,endDate) {
        var toDate=value;
        var productId=$("#year").parent().prev().prev().children().eq(0).val();
        if (productId==null||productId==""){
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            // 指定图表的配置项和数据
            var option = {
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    data:['金额', '种类', '数量']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',
                        axisTick : {show: false},
                        data : []
                    }

                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'金额',
                        type:'bar',
                        label: {
                            normal: {
                                show: true,
                            }
                        },
                        data:[]
                    },
                    {
                        name:'种类',
                        type:'bar',
                        label: {
                            normal: {
                                show: true
                            }
                        },
                        data:[]
                    },
                    {
                        name:'数量',
                        type:'bar',
                        label: {
                            normal: {
                                show: true,
                            }
                        },
                        data:[]
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option,true);
            myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
            var yearDate=$("#year").val();
            $.ajax({
                url:$tool.getContext() + 'procurementBill/countPurchase.do'//数据接口
                ,data:JSON.stringify({yearDate:toDate})
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
        } else{
            // 基于准备好的dom，初始化echarts实例
            var charts = echarts.init(document.getElementById('main'));
            // 指定图表的配置项和数据
            var optionProduct = {
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    data:['金额','种类','数量']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',
                        axisTick : {show: false},
                        data : []
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'金额',
                        type:'bar',
                        label: {
                            normal: {
                                show: true,
                            }
                        },
                        data:[]
                    },
                    {
                        name:'数量',
                        type:'bar',
                        label: {
                            normal: {
                                show: true,
                            }
                        },
                        data:[]
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            charts.setOption(optionProduct,true);
            charts.showLoading();    //数据加载完之前先显示一段简单的loading动画

            var year = $("#year").val();
            //alert(JSON.stringify({productId:productId}))
            $.ajax({
                url: $tool.getContext() + 'procurementBill/countPurchaseProduct.do'//数据接口
                , data:JSON.stringify({productId:productId,yearDate:toDate})
                , dataType: "json"
                , type: 'post'
                , async: true
                , contentType: "application/json"
                , success: function (count) {
                    console.log(count.data);
                    //alert(JSON.stringify(count.data));
                    var createDates = [];//类别数组,实际是存放x轴坐标值
                    var moneys = [];//金额数组,实际是存放y轴坐标值
                    var nums = [];//数量数组，实际是存放y轴坐标值
                    $(count.data).each(function (index, item) {
                        createDates.push(item.createDate);
                        moneys.push(item.money);
                        nums.push(item.numberTotal);
                    });
                    charts.hideLoading();    //隐藏加载动画
                    charts.setOption({     //加载数据图表

                        xAxis: {
                            data: createDates
                        },
                        series: [{
                            name: '金额',
                            data: moneys
                        },
                            {
                                name: '数量',
                                data: nums
                            }]
                    });
                }
            });
        }
    }
});


    var yearDate=$("#year").val();
    $.ajax({
        url:$tool.getContext() + 'procurementBill/countPurchase.do'//数据接口
        ,data:JSON.stringify({yearDate:yearDate})
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
        if (productId==null||productId==""){
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            // 指定图表的配置项和数据
            var option = {
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    data:['金额', '种类', '数量']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',
                        axisTick : {show: false},
                        data : []
                    }

                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'金额',
                        type:'bar',
                        label: {
                            normal: {
                                show: true,
                            }
                        },
                        data:[]
                    },
                    {
                        name:'种类',
                        type:'bar',
                        label: {
                            normal: {
                                show: true
                            }
                        },
                        data:[]
                    },
                    {
                        name:'数量',
                        type:'bar',
                        label: {
                            normal: {
                                show: true,
                            }
                        },
                        data:[]
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option,true);
            myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
            var yearDate=$("#year").val();
            $.ajax({
                url:$tool.getContext() + 'procurementBill/countPurchase.do'//数据接口
                ,data:JSON.stringify({yearDate:yearDate})
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
        } else {
            // 基于准备好的dom，初始化echarts实例
            var charts = echarts.init(document.getElementById('main'));
            // 指定图表的配置项和数据
            var optionProduct = {
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    data:['金额','种类','数量']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',
                        axisTick : {show: false},
                        data : []
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'金额',
                        type:'bar',
                        label: {
                            normal: {
                                show: true,
                            }
                        },
                        data:[]
                    },
                    {
                        name:'数量',
                        type:'bar',
                        label: {
                            normal: {
                                show: true,
                            }
                        },
                        data:[]
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            charts.setOption(optionProduct,true);
            charts.showLoading();    //数据加载完之前先显示一段简单的loading动画

            var year = $("#year").val();
            //alert(JSON.stringify({productId:productId}))
            $.ajax({
                url: $tool.getContext() + 'procurementBill/countPurchaseProduct.do'//数据接口
                , data:JSON.stringify({productId:productId,yearDate:year})
                , dataType: "json"
                , type: 'post'
                , async: true
                , contentType: "application/json"
                , success: function (count) {
                    console.log(count.data);
                    //alert(JSON.stringify(count.data));
                    var createDates = [];//类别数组,实际是存放x轴坐标值
                    var moneys = [];//金额数组,实际是存放y轴坐标值
                    var nums = [];//数量数组，实际是存放y轴坐标值
                    $(count.data).each(function (index, item) {
                        createDates.push(item.createDate);
                        moneys.push(item.money);
                        nums.push(item.numberTotal);
                    });
                    charts.hideLoading();    //隐藏加载动画
                    charts.setOption({     //加载数据图表

                        xAxis: {
                            data: createDates
                        },
                        series: [{
                            name: '金额',
                            data: moneys
                        },
                            {
                                name: '数量',
                                data: nums
                            }]
                    });
                }
            });
        }
    });

});
