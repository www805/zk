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
        console.log(data.data);

        $("#trm").html("连接中").removeClass("success").addClass("error");
        $("#ec").html("连接中").removeClass("success").addClass("error");
        $("#mc").html("连接中").removeClass("success").addClass("error");
        $("#trm_upload").show();
        $("#ec_upload").show();
        $("#mc_upload").show();
        tem_status = 0;

        var serverStatus = data.data;
        if (isNotEmpty(serverStatus) && serverStatus.length > 0) {

            for (var i = 0; i < serverStatus.length; i++) {

                var server = serverStatus[i];
                if ("trm" == server.servername && server.status == 1) {
                    $("#trm").html("已启动").removeClass("error").addClass("success");
                    $("#trm_upload").hide();
                    tem_status = 1;
                } else if ("ec" == server.servername && server.status == 1) {
                    $("#ec").html("已启动").removeClass("error").addClass("success");
                    $("#ec_upload").hide();
                } else if ("mc" == server.servername && server.status == 1) {
                    $("#mc").html("已启动").removeClass("error").addClass("success");
                    $("#mc_upload").hide();
                }
            }
        }

    }else{
        layer.msg(data.message, {time: 5000, icon:6});
    }
}



