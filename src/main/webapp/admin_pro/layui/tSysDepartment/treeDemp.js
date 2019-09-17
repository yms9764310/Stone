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

    //查询


    function initOrgTree() {
        //获取所有组织机构树
        $api.GetallOrg(null,function (res) {
            orgNodes = res.data;//保存一份
            renderTree('#org-tree', res.data);
        });
    }
    initOrgTree()
    /**
     * 绘制树
     * @param id dom id
     * @param nodes 树节点数据
     * */
    layui.use('tree')
    var tree=layui.tree;
    function renderTree(id, nodes) {
        //绘制前先清空
        $(id).empty();


        //绘制
        layui.tree({
            elem: id
            , nodes: nodes
            , click: function (node) {//显示组织机构数据
                console.log(node); //node即为当前点击的节点数据
            }
        });
    }


});
