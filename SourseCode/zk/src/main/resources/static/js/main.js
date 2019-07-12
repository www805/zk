var urlDizhi;
var ServerOut;
var loadIndex;

//检测服务器是否是连接状态
function ServerCheck(obj){

    loadIndex = layer.load(1, {
        shade: [0.1,'#fff'] //0.1透明度的白色背景
        ,title:"加载中，请稍后..."
    });

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
            var xitonglistHTML = "";

            for (var i = 0; i < list.length; i++) {
                ControlInfoParamVO = list[i];

                var servername = ControlInfoParamVO.servername;
                var servertitle = ControlInfoParamVO.servertitle;
                var servertitletwo = ControlInfoParamVO.servertitletwo;
                var loginaccount = ControlInfoParamVO.loginusername;
                var adminpassword = ControlInfoParamVO.loginpassword;
                var url = ControlInfoParamVO.url + "?loginaccount=" + loginaccount + "&loginpassword=" + adminpassword;
                var urltwo = ControlInfoParamVO.urltwo + "?loginaccount=" + loginaccount + "&loginpassword=" + adminpassword;
                var status = ControlInfoParamVO.status;
                var spanState = "<span class=\"layui-badge layui-bg-gray\"";

                //连接失败的时候新增
                //连接成功的时候删除
                if (status == 1) {
                    spanState = "<span style='background-color:#5FB878;' class=\"layui-badge \"";
                }

                //配置到列表里面
                xitonglistHTML += "<dd><a title=\"" + url + "\" style='cursor:pointer;' value=\"" + servername.toUpperCase() + "\" onclick=\"ServerCheck(this);\">" + servertitle + spanState + " id=\"" + servername + "Msg\">" + servername.toUpperCase() + "</span></a></dd>";

                if(isNotEmpty(servertitletwo) && isNotEmpty(urltwo)){
                    xitonglistHTML += "<dd><a title=\"" + urltwo + "\" style='cursor:pointer;' value=\"" + servername.toUpperCase() + "\" onclick=\"ServerCheck(this);\">" + servertitletwo + spanState + " id=\"" + servername + "Msg\">" + servername.toUpperCase() + "</span></a></dd>";
                }
            }

            $("#xitonglist").html(xitonglistHTML);
        }else{
            var xitonglistHTML = "<dd><a href='javascript:;'>暂无服务器</a> </dd>";
            $("#xitonglist").html(xitonglistHTML);
        }
    }else{
        console.log(data.message);
        // layer.msg(data.message,{icon: 2});
    }

}
function callServerCheck(data){

    layer.close(loadIndex);//关闭load特效

    if(null!=data&&data.actioncode=='SUCCESS'){
        //alert(data.message);

        if(isNotEmpty(data.data)){
            var list = data.data;
            var ControlInfoParamVO = null;

            var loginaccount = "";
            var adminpassword = "";

            for (var i = 0; i < list.length; i++) {
                ControlInfoParamVO = list[i];
                var servername = ControlInfoParamVO.servername.toUpperCase();
                var status = ControlInfoParamVO.status;

                if(ServerOut == servername && status == 1){
                    loginaccount = ControlInfoParamVO.loginusername;
                    adminpassword = ControlInfoParamVO.loginpassword;
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


