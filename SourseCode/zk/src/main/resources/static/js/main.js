var urlDizhi;
var ServerOut;
var loadIndex;

//检测服务器是否是连接状态
function ServerCheck(obj){

    loadIndex = layer.msg("加载中，请稍后...", {
        icon: 16,
        time:10000,
        shade: [0.1,"#fff"],
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


function getNavList() {
    var url=getUrl_manage().getNavList;
    ajaxSubmitByJson(url,null,callgetNavList);
}

function callgetNavList(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){

        if (isNotEmpty(data.data)) {
            var appCache = data.data;

            if (isNotEmpty(appCache.data.title)) {
                //页头
                var Top_Title = appCache.data.title;
                $("#topTitle").html(Top_Title);
                $("#spanTitle").html(Top_Title);
            }

            if (isNotEmpty(appCache.data.bottom)) {
                //页脚
                var bottom_html = "";
                var bottom_name = appCache.data.bottom.name;
                var bottom_declaration = appCache.data.bottom.declaration;
                var bottom_url = appCache.data.bottom.url;

                // if (isNotEmpty(appCache.data.bottom.image.src) && appCache.data.bottom.image.src != '/') {
                //     $(".layui-footer").css("height", "50px").css("line-height", "52px");
                //     bottom_html = " <a href=\"" + bottom_url + "\">" + "<img width='" + appCache.data.bottom.image.width + "' height='" + appCache.data.bottom.image.height + "' src='" + appCache.data.bottom.image.src + "'>" + "</a>";
                // } else {
                // }
                bottom_html = bottom_declaration + " <a href=\"" + bottom_url + "\">" + bottom_name + "</a>";
                $("#bottom_mian").html(bottom_html);
            }

            if (isNotEmpty(appCache.data.guidepageUrl)) {
                //设置引导页地址 guidepage
                $("#guidepage").attr("href", appCache.data.guidepageUrl);
            }

        }
        layui.use('element', function(){
            var element =  layui.element;
            element.render();
        });
    }else{
        layer.msg(data.message);
    }
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

                if (servername == "zk") {
                    continue;
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
                    //layer.close(loadIndex);//关闭load特效
                    return;
                }
            }

        }

        layer.msg(ServerOut + "服务器尚未连接或已断开，暂不能访问", {time: 5000, icon: 2});

    }else{
        layer.msg(data.message, {time: 5000, icon:2});
    }

}



