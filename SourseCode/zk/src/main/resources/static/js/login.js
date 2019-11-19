
function login_login(){

    var url=getUrl_manage().logining;

    var loginaccount =$('input[name="loginaccount"]').val();
    var password =$('input[name="password"]').val();
    var rememberpassword = $("#rememberpassword").is(":checked");//获取是否选中
    var data={
        loginaccount:loginaccount,
        password:password,
        rememberpassword:rememberpassword,
    };
    ajaxSubmit(url,data,callbackgetAdminInfoPage);

}

function callbackgetAdminInfoPage(data){

    if(null!=data&&data.actioncode=='SUCCESS'){
        var url=getUrl_manage().main;
        window.location.href=url;
    }else{
        layer.msg(data.message, {icon: 2});
    }
    layui.use('form', function(){
        var form = layui.form;
        form.render();
    });
}

function login_logout(){

    var url=getUrl_manage().logout;

    ajaxSubmit(url,null,callLogout);
}

function getClientUrl(clientUrl){

    var url=getUrl_manage().getClientUrl;

    var data={
        clientUrl:clientUrl,
    };
    ajaxSubmit(url,data,callgetClientUrl);

}

function getNavList() {
    var url=getUrl_manage().getNavList;
    ajaxSubmitByJson(url,null,callgetNavList);
}

function callgetNavList(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){

        if (isNotEmpty(data.data.data)) {
            var appCache = data.data;

            if (isNotEmpty(appCache.data.title)) {
                //页头
                var Top_Title = appCache.data.title;
                $("#topTitle").html(Top_Title);
                $("#spanTitle").html(Top_Title);
            }

            // if (isNotEmpty(appCache.data.guidepageUrl)) {
            //     //设置引导页地址 guidepage
            //     $("#guidepage").attr("href", appCache.data.guidepageUrl);
            // }

            if (isNotEmpty(appCache.data.bottom)) {
                if (!isNotEmpty(appCache.data.bottom) || !isNotEmpty(appCache.data.bottom.name) || !isNotEmpty(appCache.data.bottom.declaration)) {
                    return;
                }
                //页脚
                var bottom_html = "";
                var bottom_name = appCache.data.bottom.name;
                var bottom_declaration = appCache.data.bottom.declaration;
                var bottom_url = appCache.data.bottom.url;
                if(!isNotEmpty(bottom_url)){
                    bottom_url = "#";
                }

                if (isNotEmpty(appCache.data.bottom.image.src) && appCache.data.bottom.image.src != '/') {
                    $(".systemlogo").css({"background-image":"url(" + appCache.data.bottom.image.src + ")"});
                    if(isNotEmpty(appCache.data.bottom.image.width) && isNotEmpty(appCache.data.bottom.image.height)){
                        $(".systemlogo").css("background-size", appCache.data.bottom.image.width + "px " + appCache.data.bottom.image.height + 'px');
                    }
                }else{
                    $(".systemlogo").css({"background-image":"url(/uimaker/images/loginlogo-5aa2cf210dbf067bd57c42a470703719.png)"});
                }
                bottom_html = bottom_declaration + " <a href=\"" + bottom_url + "\">" + bottom_name + "</a>";
                $("#bottom_mian").html(bottom_html);
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

function callLogout(data){

    if(null!=data&&data.actioncode=='SUCCESS'){
        //alert(data.message);
        var url=getUrl_manage().gotologin;
        window.location.href="/";
    }else{
        // alert(data.message);
        layer.msg(data.message, {time: 5000, icon:6});
    }

}



function callgetClientUrl(data){

    if(null!=data&&data.actioncode=='SUCCESS'){
        console.log("提交客户端地址成功");
    }else{
        layer.msg(data.message, {icon: 2});
    }
    layui.use('form', function(){
        var form = layui.form;
        form.render();
    });
}


function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);//search,查询？后面的参数，并匹配正则
    if(r!=null)return  unescape(r[2]); return null;
}

//记住密码----------------------------------------start
function getrememberpassword(){
    var url=getUrl_manage().getLoginCookie;
    var data={};
    ajaxSubmit(url,data,function (d) {
        if(null!=d&&d.actioncode=='SUCCESS')
            var data=d.data;
        if (isNotEmpty(data)){
            var loginaccount=data.loginaccount==null?"":data.loginaccount;
            var password=data.password==null?"":data.password;
            $('.loginuser').val(loginaccount);
            $('.loginpwd').val(password);
            if (isNotEmpty(loginaccount)&&isNotEmpty(password)){
                $("#rememberpassword").attr("checked","true");
            }
        }else{
            layer.msg(d.message,{icon: 5});
        }
        layui.use(['form'], function(){
            var form=layui.form;
            form.render()
        });
    });
}
//记住密码----------------------------------------end