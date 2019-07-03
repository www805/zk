var urlDizhi;
var ServerOut;

//检测服务器是否是连接状态
function ServerCheck(obj){

    urlDizhi = obj.title;
    ServerOut = $(obj).attr("value");

    var url=getUrl_manage().ZKcontrolList;

    ajaxSubmit(url,null,callServerCheck);

}

//主动去请求如果断就断
function getControl(){

    var url=getUrl_manage().ZKcontrolList;

    ajaxSubmit(url,null,callControl);

}

function callControl(data){

    if(null!=data&&data.actioncode=='SUCCESS'){

        if(isNotEmpty(data.data)){
            var list = data.data;
            var ControlInfoParamVO = null;

            //连接成功的时候删除
            $("#ecMsg").addClass("layui-bg-gray");
            $("#mcMsg").addClass("layui-bg-gray");
            $("#trmMsg").addClass("layui-bg-gray");
            $("#trmService").addClass("layui-bg-gray");

            for (var i = 0; i < list.length; i++) {
                ControlInfoParamVO = list[i];
                //连接失败的时候新增
                if (ControlInfoParamVO.servername == "ec" && ControlInfoParamVO.status == 1) {
                    $("#ecMsg").removeClass("layui-bg-gray");
                }else if(ControlInfoParamVO.servername == "mc" && ControlInfoParamVO.status == 1){
                    $("#mcMsg").removeClass("layui-bg-gray");
                }else if(ControlInfoParamVO.servername == "trm" && ControlInfoParamVO.status == 1){
                    $("#trmMsg").removeClass("layui-bg-gray");
                    $("#trmService").removeClass("layui-bg-gray");
                }
            }

        }
    }else{
        layer.msg(data.message,{icon: 2});
    }

}
function callServerCheck(data){

    if(null!=data&&data.actioncode=='SUCCESS'){
        //alert(data.message);

        if (null != data.data) {
            var list = data.data;
            var ControlInfoParamVO = "";

            for (var i = 0; i < list.length; i++) {
                ControlInfoParamVO = list[i];
                if(ServerOut == ControlInfoParamVO.servername.toUpperCase() && ControlInfoParamVO.status == 1){
                    document.getElementById('optionZK').src = urlDizhi;
                    return;
                }
            }
        }

        layer.msg(ServerOut + "服务器尚未连接或已断开，暂不能访问", {time: 5000, icon: 2});

    }else{
        layer.msg(data.message, {time: 5000, icon:2});
    }

}


