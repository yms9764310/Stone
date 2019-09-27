layui.config({
    base: $config.resUrl + 'admin_pro/layui/assets/lay/modules/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api: 'api'
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
            elem: '#demo'
            , height: 415
            , url: $tool.getContext() + 'PayBill/listPayBill.do' //数据接口
            , method: 'post'
            , page: true //开启分页
            , limit: 5
            , limits: [5, 6, 7, 8, 9, 10]
            , cols: [[ //表头
                {type: 'numbers', title: '', fixed: 'left'}
                , {field: 'creator_name', title: '创建人', width: '8%'}
                , {field: 'create_date', title: '创建时间', width: '8%'}
                , {field: 'modify_name', title: '修改人', width: '8%', templet: '#upc'}
                , {field: 'modify_date', title: '修改时间', width: '8%', templet: '#upc'}
                , {field: 'state', title: '状态', width: '8%', templet: '#upc'}
                , {field: 'commit_user_name', title: '提交人姓名', width: '8%', templet: '#upc'}
                , {field: 'effect_user_name', title: '审核人姓名', width: '8%', templet: '#upc'}
                , {field: 'source_type', title: '来源但类型', width: '8%', templet: '#upc'}
                , {field: 'pay_date', title: '应付日期', width: '8%', templet: '#upc'}
                , {field: 'account_no', title: '账号', width: '8%', templet: '#upc'}
                , {field: 'payment_voucher_path', title: '凭证', width: '8%', templet: '#upc'}
                , {fixed: 'right', title: '操作', width: 217, align: 'left', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
            ]], done: function(res, curr, count){

            }

        });

        //为toolbar添加事件响应
        table.on('tool(userFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var row = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            //区分事件
            if (layEvent === 'shang') {
                //删除
            } else if (layEvent === 'edit') { //编辑
                edit(row.id);
            }
        });
    }
    defineTable();
    //查询
    form.on("submit(queryUser)", function (data) {
        var sname = data.field.sname;
        //表格重新加载
        tableIns.reload({
            where: {
                sname: sname
            }

        });
        return false;
    });


    function edit(id) {
        var index = layui.layer.open({
            title: "审核",
            type: 2,
            content: "Pay.html?id=" + id,
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });

        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function () {
            layui.layer.full(index);
        });
        layui.layer.full(index);
    }



});
