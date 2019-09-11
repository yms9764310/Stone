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
        Insert_ys_Product:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'T_Produce/InsertProduct.do',req,config,successCallback,errorCallback);
        },
        Insert_ys_ProductBom:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'T_ProduceBom/InsertProductBom.do',req,config,successCallback,errorCallback);
        },
        Insert_ys_ProductTask:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'T_ProduceTask/saveProductTask.do',req,config,successCallback,errorCallback);
        },
        GetProductName:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'T_Produce/getProductName.do',req,config,successCallback,errorCallback);
        },
        GetProductMaterialName:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'T_Produce/getProductMaterialName.do',req,config,successCallback,errorCallback);
        },
        GetProductBomName:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'T_ProduceBom/getProductBomName.do',req,config,successCallback,errorCallback);
        },
        DeleteStudent:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'Student/delete.do',req,config,successCallback,errorCallback);
        },
        Delete_ys_Product:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'T_Produce/deleteProduct.do',req,config,successCallback,errorCallback);
        },
        Delete_ys_ProductTask:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'T_ProduceTask/deleteProductTask.do',req,config,successCallback,errorCallback);
        },
        Delete_ys_ProductBom:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'T_ProduceBom/deleteProductBom.do',req,config,successCallback,errorCallback);
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
        Update_ys_Product:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'T_Produce/UpdateProduct.do',req,config,successCallback,errorCallback);
        },
        Update_ys_ProductBom:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'T_ProduceBom/UpdateProductBom.do',req,config,successCallback,errorCallback);
        },
        Update_ys_ProductTask:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'T_ProduceTask/UpdateProductTask.do',req,config,successCallback,errorCallback);
        },
        Update_ys_ProductTaskAuditing:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'T_ProduceTask/UpdateProductTaskAuditing.do',req,config,successCallback,errorCallback);
        },
        Update_ys_ProductTaskReject:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'T_ProduceTask/UpdateProductTaskReject.do',req,config,successCallback,errorCallback);
        },
    };

    //输出扩展模块
    exports('$api', API);
});


