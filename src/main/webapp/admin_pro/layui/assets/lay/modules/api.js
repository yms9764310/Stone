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
        GetUsersName:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'StoreManagement/findUsersName.do',req,config,successCallback,errorCallback);
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
    };

    //输出扩展模块
    exports('$api', API);
});


