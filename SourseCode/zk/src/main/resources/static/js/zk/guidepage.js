var tem_status = 0;
/**
 * 获取服务器状态
 */
function getServerStatus() {
    var url=getUrl_manage().getServerStatus;
    ajaxSubmitByJson(url,null,callgetServerStatus);
}

function callgetServerStatus(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        // console.log(data.data);

        $("#trm").html("连接中").removeClass("success").addClass("error");
        $("#ec").html("连接中").removeClass("success").addClass("error");
        $("#mc").html("连接中").removeClass("success").addClass("error");
        $("#trm_upload").show();
        $("#ec_upload").show();
        $("#mc_upload").show();
        tem_status = 0;

        var serverStatus = data.data;
        if (isNotEmpty(serverStatus) && serverStatus.length > 0) {

            var cishu = 0;
            for (var i = 0; i < serverStatus.length; i++) {

                var server = serverStatus[i];
                if ("trm" == server.servername && server.status == 1) {
                    $("#goto_client").attr("value", server.url);
                    $("#trm").html("已启动").removeClass("error").addClass("success");
                    $("#trm_upload").hide();
                    tem_status = 1;
                    cishu++;
                } else if ("ec" == server.servername && server.status == 1) {
                    $("#ec").html("已启动").removeClass("error").addClass("success");
                    $("#ec_upload").hide();
                    cishu++;
                } else if ("mc" == server.servername && server.status == 1) {
                    $("#mc").html("已启动").removeClass("error").addClass("success");
                    $("#mc_upload").hide();
                    cishu++;
                }
            }
            if (cishu >= 3) {
                $("#serverStatus").css("width", "170px").css("padding-right","10px");
            }else {
                $("#serverStatus").css("width", "260px").css("padding-right","0px");
            }
        }

    }else{
        layer.msg(data.message, {time: 5000, icon:6});
    }
}


function getNavList() {
    var url=getUrl_manage().getNavList;
    ajaxSubmitByJson(url,null,callgetNavList);
}

function callgetNavList(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){

        if (isNotEmpty(data.data.data)) {
            var appCache = data.data;

            if(isNotEmpty(appCache.data.guidepage.img.src)){
                $(".openbox2").css({"background-image": "url(" + appCache.data.guidepage.img.src + ")"});
                if(isNotEmpty(appCache.data.guidepage.img.width)){
                    $(".openbox2").css("width", appCache.data.guidepage.img.width);
                }
                if(isNotEmpty(appCache.data.guidepage.img.height)){
                    $(".openbox2").css("height", appCache.data.guidepage.img.height);
                }
                if(isNotEmpty(appCache.data.guidepage.img.span)){
                    $(".openbox2").css("margin-bottom", appCache.data.guidepage.img.span);
                }
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
