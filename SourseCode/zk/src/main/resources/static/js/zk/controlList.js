



function getControlPageByParam_init(currPage,pageSize){

    var url=getUrl_manage().controlList;
    // var name =$("#name").val();
    var data={
        token:"",
        param:{
            currPage:currPage,
            pageSize:pageSize
        }
    };

    ajaxSubmitByJson(url,data,callbackgetControlPage);

}

function getControlPage(name,currPage,pageSize){


    var url=getUrl_manage().controlList;
    var data={
        token:"",
        param: {
            name: name,
            currPage: currPage,
            pageSize: pageSize
        }
    };
    ajaxSubmitByJson(url,data,callbackgetControlPage);
}

//清空已断开的缓存
function clearControlInfo(){

    var url=getUrl_manage().clearControlInfo;
    ajaxSubmit(url,null,callClearControlInfo);
}

function callbackgetControlPage(data){

    if(null!=data&&data.actioncode=='SUCCESS'){
        pageshow(data);

        if(isNotEmpty(data.data)){

            var listcountsize = data.data.pageparam.recordCount;
            if (listcountsize == 0) {
                $("#wushuju").show();
                $("#pagelistview").hide();
            } else {
                $("#wushuju").hide();
                $("#pagelistview").show();
            }

        }
    }else{
        layer.msg(data.message,{icon: 2});
    }

}

function callClearControlInfo(data){

    if(null!=data&&data.actioncode=='SUCCESS'){
        layer.msg("清空成功",{icon: 1});
        getControlPageByParam();
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

function getControlPageByParam(){

    var len=arguments.length;
    if(len==0){
        var currPage=1;
        var pageSize=10;//测试
        getControlPageByParam_init(currPage,pageSize);
    }else if (len==2){
        getControlPage('',arguments[0],arguments[1]);
    }else if(len>2){
        getControlPage(arguments[0],arguments[1],arguments[2]);
    }

}


function showpagetohtml(){

    if(isNotEmpty(pageparam)){
        var pageSize=pageparam.pageSize;
        var pageCount=pageparam.pageCount;
        var currPage=pageparam.currPage;
        var name=pageparam.name;
        var arrparam=new Array();
        arrparam[0]=name;
        showpage("paging",arrparam,'getControlPageByParam',currPage,pageCount,pageSize);
    }


}