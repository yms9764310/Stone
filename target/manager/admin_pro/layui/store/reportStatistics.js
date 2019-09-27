layui.config({
    base: $config.resUrl + 'admin_pro/layui/assets/lay/modules/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api: 'api',
    echarts:'echarts.min' //echarts 核心包

}).use(['form', 'layer', '$api', 'jquery', 'table', 'laypage', 'laytpl', 'ajaxExtention', '$tool','echarts'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        laypage = layui.laypage,
        laytpl = layui.laytpl,
        $tool = layui.$tool,
        table = layui.table,
        $api = layui.$api;
    layer = layui.layer;
    echarts = layui.echarts;
    layuiTable = layui.table;
    var tableIns;//表格实例

    //查询
    form.on("submit(queryUser)", function (data) {
        var startTime = data.field.start_time;
        var endTime = data.field.end_time;
        if(startTime!=""&&endTime!=""){
            var Charts = echarts.init(document.getElementById('main'));
            // 显示标题，图例和空的坐标轴
            var ption={
                title: {
                    text: '石材库存损耗'
                },
                tooltip: {},
                legend: {
                    data:['损耗']
                },
                xAxis: {
                    data: []
                },
                yAxis: {},
                series: [{
                    name: '损耗',
                    type: 'bar',
                    data: []
                }]
            };
            Charts.setOption(ption,true);
            Charts.showLoading();    //数据加载完之前先显示一段简单的loading动画


      $.ajax({
                type : "post",
                async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url : "/Stone/StoreManagement/countStoreLoss.do",    //请求发送到TestServlet处
                data : {"startTime":startTime,"endTime":endTime},
                dataType : "json",        //返回数据形式为json
                success : function(result) {
                    //请求成功时执行该函数内容，result即为服务器返回的json对象
                    var names=[];
                    var nums=[];
                    if (result.data) {
                        for(var i=0;i<result.data.length;i++){
                            names.push(result.data[i].product_name);    //挨个取出类别并填入类别数组
                        }
                        for(var i=0;i<result.data.length;i++){
                            nums.push(result.data[i].loss_number);    //挨个取出销量并填入损耗数组
                        }
                        Charts.hideLoading();             //隐藏加载动画
                        Charts.setOption({        //加载数据图表
                            xAxis: {
                                data: names
                            },
                            series: [{
                                // 根据名字对应到相应的系列
                                name: '损耗',
                                data: nums
                            }]
                        });

                    }

                },
                error : function(errorMsg) {
                    //请求失败时执行该函数
                    alert("图表请求数据失败!");
                    myChart.hideLoading();
                }
            })
        }else{
            window.location.reload();//刷新父页面
        }
    });

});
layui.define('echarts.min',function(exports){exports('echarts.min',echarts)});
