
function login_login(){

    var url=getUrl_manage().logining;

    var loginaccount =$('input[name="loginaccount"]').val();
    var password =$('input[name="password"]').val();
    var data={
        loginaccount:loginaccount,
        password:password
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

            if (isNotEmpty(appCache.data.bottom)) {
                if (!isNotEmpty(appCache.data.bottom) || !isNotEmpty(appCache.data.bottom.name) || !isNotEmpty(appCache.data.bottom.declaration) || !isNotEmpty(appCache.data.bottom.url)) {
                    return;
                }
                //页脚
                var bottom_name = appCache.data.bottom.name;
                var bottom_declaration = appCache.data.bottom.declaration;
                var bottom_url = appCache.data.bottom.url;
                var bottom_html = bottom_declaration + " <a href=\"" + bottom_url + "\">" + bottom_name + "</a>";
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


