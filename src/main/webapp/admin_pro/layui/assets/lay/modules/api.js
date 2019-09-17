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
        DeleteStudent:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'Student/delete.do',req,config,successCallback,errorCallback);
        },
        ReviewSuccess:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'ToDoList/delete.do',req,config,successCallback,errorCallback);
        },
        GetStudent:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'Student/find.do',req,config,successCallback,errorCallback);
        },
        GetSysUsers:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'SysUser/find.do',req,config,successCallback,errorCallback);
        },
        GetDepartUsers:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'StaffSetting/get.do',req,config,successCallback,errorCallback);
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
        //获取客户ID
        GetCustomer:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'Customer/loadById.do',req,config,successCallback,errorCallback);
        },
        //编辑客户信息
        UpdateCustomer:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'Customer/updateCustomer.do',req,config,successCallback,errorCallback);
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
        //获取订单ID
        GetBill:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'SaleBill/loadById.do',req,config,successCallback,errorCallback);
        },
        GetFirstClassMenus:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'Customer/chooseCus.do',req,successCallback,errorCallback);
        },
        //创建新的订单
        InsertSaleBill:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'SaleBill/insertSaleBill.do',req,config,successCallback,errorCallback);
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
    };

    //输出扩展模块
    exports('$api', API);
});


