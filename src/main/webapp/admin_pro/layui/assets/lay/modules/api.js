/**
 * api接口列表
 */
layui.define(['$tool','jquery'], function (exports) {

    var $tool = layui.$tool,
        $ = layui.jquery;// 拿到模块变量

    /**
     * 封装一个post
     * */
    function doPost(url,req,successCallback,errorCallback) {
        $.ajax({
            url:url,
            data:req,
            method:"post",
            success:function (data) {
                successCallback(data);
            },
            error:function (error) {
                errorCallback(error);
            }
        });
    }

    /**
     * 封装一个get
     * */
    function doGet(url,req,successCallback,errorCallback) {
        $.ajax({
            url:url,
            data:req,
            method:"get",
            success:function (data) {
                successCallback(data);
            },
            error:function (error) {
                errorCallback(error);
            }
        });
    }

    /**
     * 封装一个支持更多参数的post
     * */
    function doComplexPost(url,req,config,successCallback,errorCallback) {
        var defaultConfig = {
            url:url,
            data:req,
            method:"post",
            success:function (data) {
                successCallback(data);
            },
            error:function (error) {
                errorCallback(error);
            }
        };
        var ajaxConfig = $.extend({},config,defaultConfig); // 合并参数

        $.ajax(ajaxConfig);
    }

    // API列表,工程庞大臃肿后可以将API拆分到单独的模块中
    var API = {
        AddStudent:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'Student/add.do',req,config,successCallback,errorCallback);
        },
        InsertCountingTask:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'StoreManagement/insertCountingTask.do',req,config,successCallback,errorCallback);
        },
        UpdateCountingTask:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'StoreManagement/updateCountingTask.do',req,config,successCallback,errorCallback);
        },
        SureCountingTask:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'StoreManagement/sureCountingTask.do',req,config,successCallback,errorCallback);
        },
        ReviewCountingTask:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'StoreManagement/reviewCountingTask.do',req,config,successCallback,errorCallback);
        },
        ExportCountingTask:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext()+'StoreManagement/exportExcel.do',req,config,successCallback,errorCallback);
        },
        //对账单导出
        ExportAccountStatement:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext()+'PayBill/excel.do',req,config,successCallback,errorCallback);
        },
        DeleteStudent:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'Student/delete.do',req,config,successCallback,errorCallback);
        },
        DeleteHistory:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'PersonLogHistory/delete.do',req,config,successCallback,errorCallback);
        },
        DeleteDemp:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'demp/deletedemp.do',req,config,successCallback,errorCallback);
        },
        DeleteProu:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'prou/deleteprou.do',req,config,successCallback,errorCallback);
        },
        ListDempByName:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'demp/listdempname.do',req,config,successCallback,errorCallback);
        },
        ListDempById:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'demp/listdempbyid.do',req,config,successCallback,errorCallback);
        },
        LoadDempId:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'demp/loaddemp.do',req,successCallback,errorCallback);
        },
        LoadProuId:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'prou/loadprou.do',req,successCallback,errorCallback);
        },
        UpdateDemp:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'demp/updatedemp.do',req,config,successCallback,errorCallback);
        },
        UpdateProu:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'prou/updateprou.do',req,config,successCallback,errorCallback);
        },
        Getrouce:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'prou/listresou.do',req,config,successCallback,errorCallback);
        },

        AddDemp:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'demp/savedemp.do',req,config,successCallback,errorCallback);
        },
        GetallOrg:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'demp/All.do',req,successCallback,errorCallback);
        },
        AddProu:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'prou/saveprou.do',req,config,successCallback,errorCallback);
        },

        //根据id查询审核内容
        loadPay:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'HandleBill/loadHandleBill.do',req,successCallback,errorCallback);
        },
        //根据id查询应收单审核内容
        loadReceive:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'ReceiveHandleBill/loadReceiveHandleBill.do',req,successCallback,errorCallback);
        },
        //付款凭证
        loadPayBill:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'PayBill/loadPayBill.do',req,successCallback,errorCallback);
        },
        //下载文件
        Down:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'PayBill/download.do',req,successCallback,errorCallback);
        },

        //创建应付单
        AddPay:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'HandleBill/saveHandleBill.do',req,config,successCallback,errorCallback);
        },
        //自己创建应收单
        AddRec:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'ReceiveHandleBill/saveReceiveHandleBill.do',req,config,successCallback,errorCallback);
        },
        //修改
        UpdatePay:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'PayBill/updatePayBill.do',req,config,successCallback,errorCallback);
        },
        //应付审核通过update
        HandleBillPass:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'HandleBill/updateHandleBill.do',req,successCallback,errorCallback);
        },
        //应收审核通过update
        ReceiveBillPass:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'ReceiveHandleBill/updateReceiveHandleBill.do',req,successCallback,errorCallback);
        },
        AddRole:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'role/saverole.do',req,config,successCallback,errorCallback);
        },
        AddRUser:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'SysUser/saveuser.do',req,config,successCallback,errorCallback);
        },
        DeleteRole:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'role/deleterole.do',req,config,successCallback,errorCallback);
        },
        ReviewSuccess:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'ToDoList/delete.do',req,config,successCallback,errorCallback);
        },
        GetStudent:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'Student/find.do',req,config,successCallback,errorCallback);
        },
        load_ys_Product:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'T_Produce/loadProduct.do',req,config,successCallback,errorCallback);
        },
        load_ys_ProductBom:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'T_ProduceBom/loadProductBom.do',req,config,successCallback,errorCallback);
        },
        load_ys_ProductTask:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'T_ProduceTask/loadProductTask.do',req,config,successCallback,errorCallback);
        },
        DeleteCheckTask:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'StoreManagement/deleteCheckTask.do',req,config,successCallback,errorCallback);
        },
        GetSysUsers:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'SysUser/find.do',req,config,successCallback,errorCallback);
        },
        GetUsersName:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'StoreManagement/findUsersName.do',req,config,successCallback,errorCallback);
        },
        GetProductList:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'purchaseSupplier/listPurchaseProduct.do',req,config,successCallback,errorCallback);
        },
        GetCheckTask:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'StoreManagement/getCheckTask.do',req,config,successCallback,errorCallback);
        },
        GetDepartUsers:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'StaffSetting/get.do',req,config,successCallback,errorCallback);
        },
        GetPutIn:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'StorePutIn/getPutIn.do',req,config,successCallback,errorCallback);
        },
        GetCheckOut:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'StorePutIn/getCheckOut.do',req,config,successCallback,errorCallback);
        },
        GetCheckOutWarn:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'StoreManagement/getCheckOut.do',req,config,successCallback,errorCallback);
        },
        GetDetails:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'ToDoList/get.do',req,config,successCallback,errorCallback);
        },
        GetWorkFlow:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'ToDoList/getWorkFlow.do',req,config,successCallback,errorCallback);
        },
        GetWorkSpeed:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'ToDoList/getWorkSpeed.do',req,config,successCallback,errorCallback);
        },
        UpdateStudent:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'Student/update.do',req,config,successCallback,errorCallback);
        },
        //添加客户信息
        InsertCustomer:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'Customer/insert.do',req,config,successCallback,errorCallback);
        },
        //删除客户信息
        DeleteCustomer:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'Customer/delete.do',req,config,successCallback,errorCallback);
        },
        GetCustomer:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'Customer/loadById.do',req,config,successCallback,errorCallback);
        },
        UpdateCustomer:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'Customer/updateCustomer.do',req,config,successCallback,errorCallback);
        },
        //添加供应商信息
        InsertSupplier:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'purchaseSupplier/insertSupplier.do',req,config,successCallback,errorCallback);
        },
        //删除供应商信息以及相关数据
        DeletePurchase:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'purchaseSupplier/deleteSupplier.do',req,successCallback,errorCallback);
        },
        //根据id获取指定供应商信息
        GetSupplier:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'purchaseSupplier/loadSupplier.do',req,successCallback,errorCallback);
        },
        //修改供应商信息
        editSupplier:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'purchaseSupplier/updateSupplier.do',req,config,successCallback,errorCallback);
        },
        //查看商品信息
        LookProduct:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'purchaseSupplier/loadProduct.do',req,successCallback,errorCallback);
        },
        //添加采办事项
        InsertPurchaseBill:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'procurementBill/insertPurchaseBill.do',req,config,successCallback,errorCallback);
        },
        //根据id获取采办事项信息
        LoadBill:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'procurementBill/loadPurchaseBill.do',req,successCallback,errorCallback);
        },
        //删除采办事项
        DeletePurchaseBill:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'procurementBill/deletePurchaseBill.do',req,successCallback,errorCallback);
        },
        //编辑采办事项
        UpdatePurchaseBill:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'procurementBill/updatePurchaseBill.do',req,config,successCallback,errorCallback);
        },
        //
        PurchaseSupplierOrders:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'procurementBill/purchaseSupplierLike.do',req,successCallback,errorCallback);
        },
        //审核
        UpdatePurchaseBillAudit:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'procurementBill/purchaseBillAudit.do',req,config,successCallback,errorCallback);
        },

        SupplierProductLike:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'procurementBill/supplierProduct.do',req,successCallback,errorCallback);
        },
        //待完成，根据id获取
        LoadCompletedBill:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'procurementBill/loadCompletedBill.do',req,successCallback,errorCallback);
        },
        //待完成,创建订单
        UpdateBillComplete:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'procurementBill/updateCompleted.do',req,config,successCallback,errorCallback);
        },
        //待完成,编辑操作
        EditCompletedBill:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'procurementBill/editCompleted.do',req,config,successCallback,errorCallback);
        },
        //删除采购订单
        DeleteBillOrders:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'procurementBill/deletePurchaseBill.do',req,successCallback,errorCallback);
        },
        //采购单，根据id获取信息
        LoadBillOrders:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'procurementBill/loadBillOrders.do',req,successCallback,errorCallback);
        },
        //采购单,编辑操作
        UpdateBillOrders:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'procurementBill/updateBillOrders.do',req,config,successCallback,errorCallback);
        },
        //采购单的审核,根据id获取
        LoadPurchaseBillOrders:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'procurementBill/loadPurchaseBillOrders.do',req,successCallback,errorCallback);
        },
        //采购单审核通过
        AuditBillOrders:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'procurementBill/updatePurchaseBillOrders.do',req,config,successCallback,errorCallback);
        },
        UpdatePassword:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'SysLoginUser/changePassword.do',req,config,successCallback,errorCallback);
        },
        UpdateMessgae:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'SysUser/changeMessage.do',req,config,successCallback,errorCallback);
        },
        UpdateStaffSetting:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'StaffSetting/changeMessage.do',req,config,successCallback,errorCallback);
        },
        UpdateThreshold:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'StaffSetting/changeThreshold.do',req,config,successCallback,errorCallback);
        },
        Login:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'login.do',req,config,successCallback,errorCallback);
        },
        LogOut:function(req,successCallback,errorCallback){ // 登出
            doPost($tool.getContext() + 'logout.do',req,successCallback,errorCallback);
        },
        updatePutInSuccess:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'StorePutIn/updatePutInSuccess.do',req,config,successCallback,errorCallback);
        },
        updateCheckOutSuccess:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'StorePutIn/updateCheckOutSuccess.do',req,config,successCallback,errorCallback);
        },
        updateCheckOutWarn:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'StoreManagement/updateCheckOutWarn.do',req,config,successCallback,errorCallback);
        },
        updatePutInReject:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'StorePutIn/updatePutInReject.do',req,config,successCallback,errorCallback);
        },
        updateCheckOutReject:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'StorePutIn/updateCheckOutReject.do',req,config,successCallback,errorCallback);
        },
        updateCheckState:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'StoreManagement/updateCheckState.do',req,config,successCallback,errorCallback);
        },
        //选择客户添加
        GetCusChoose:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'Customer/chooseCus.do',req,config,successCallback,errorCallback);
        },
        //选择商品
        GetSaleBillName:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'SaleBill/listProduct.do',req,config,successCallback,errorCallback);
        },
        //编辑订单
        UpdateSaleBill:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'SaleBill/updateSaleBill.do',req,config,successCallback,errorCallback);
        },
        //审核订单
        Pass:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'SaleBill/success.do',req,successCallback,errorCallback);
        },
        Defeat:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'SaleBill/defeat.do',req,successCallback,errorCallback);
        },
        //审核预览订单
        GetBill:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'SaleBill/loadById.do',req,config,successCallback,errorCallback);
        },
        //审核预览订单
        LookSaleBill:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'SaleBill/loadById2.do',req,config,successCallback,errorCallback);
        },
        //创建新的订单
        InsertSaleBill:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'SaleBill/insertSaleBill.do',req,config,successCallback,errorCallback);
        },

        //获取客户赋值下拉框
        GetFirstClassMenus:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'Customer/chooseCus.do',req,successCallback,errorCallback);
        },
    };

    //输出扩展模块
    exports('$api', API);
});


