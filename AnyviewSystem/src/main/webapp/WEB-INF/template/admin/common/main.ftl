[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>${setting.siteName} - 主页</title>
    <meta name="keywords" content="${setting.siteName}">
    <meta name="description" content="${setting.siteName}">
    <link href="${base}/resources/themes/classic/css/skin_0.css" rel="stylesheet" type="text/css" id="cssfile">
    <link rel="stylesheet" href="${base}/resources/iconfont/iconfont.css">
    <script type="text/javascript" src="${base}/resources/js/jquery-2.1.1.min.js"></script>
    <script src="${base}/resources/js/admin/common.js" type="text/javascript"></script>
    <script src="${base}/resources/themes/classic/js/main.js" type="text/javascript"></script>
</head>
<body style="margin: 0px;" scroll="no">
<div id="pagemask" style="display: none;"></div>
<table style="width: 100%;" id="frametable" height="100%" width="100%"
       cellpadding="0" cellspacing="0">
    <tbody>
    <tr style="background: rgb(255, 255, 255);">
        <td colspan="2" class="mainhd">
            <div class="layout-header">
                <!-- Title/Logo - can use text instead of image -->
                <div id="title">
                    <p class="title-p">Anyview System</p>
                </div>
                <!-- Top navigation -->
                <div id="topnav" class="top-nav">
                    <ul>
                        <li class="adminid topnav-user" title="${principal.name}">
                            <strong>${principal.name}</strong></li>
                        <li class="topnav-password">
                          <a
                                href="${base}/admin/common/edit_pass.jhtml"
                                target="workspace"><span>修改密码</span></a></li>
                        <li class="topnav-logout">
                          <a  href="javascript:;" title="退出" id="signOut"><span>退出</span></a></li>
                    </ul>
                </div>
            </div>
        </td>
    </tr>
    <tr style="background: rgb(251, 251, 251);">
        <td class="menutd" valign="top" width="161">
            <div id="mainMenu" class="main-menu">
            		<ol id="sort_0" style="display: block;">
                    [#list acls as acl]
                		[@shiro.hasPermission name = acl.permission]
                      [#if acl.id != 11 && acl.id != 17]
                        <li>
                     	    <a href="javascript:void(0);" url="${base}${acl.url}" name="${acl.id}" id="${acl.id}"
            					         onclick="openItem(&#39;${acl.id}&#39;,&#39;${acl.id}&#39;);" class="${acl.id}">${acl.aclName}</a>
                        </li>
                      [/#if]

                    [/@shiro.hasPermission]
                    [/#list]
                 </ol>
            </div>
        </td>
        <td valign="top" width="100%">
            <iframe src="${base}/admin/common/index.jhtml"
                    id="workspace" name="workspace"
                    style="overflow: visible; height: 889px; width: 1740px;"
                    frameborder="0" width="100%" height="100%" scrolling="yes"
                    onload="window.parent"></iframe>
        </td>
    </tr>
    </tbody>
</table>

<script type="text/javascript">

  //页面刷新时，查看是否存在selectArr  有则删除
  if (getCookie("selectArr")) {
    clearCookie("selectArr")
  }
  //取得cookie
  function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';'); //把cookie分割成组
    for(var i = 0;i < ca.length;i++) {
      var c = ca[i]; //取得字符串
      while (c.charAt(0)==' ') { //判断一下字符串有没有前导空格
        c = c.substring(1,c.length); //有的话，从第二位开始取
      }
      if (c.indexOf(nameEQ) == 0) { //如果含有我们要的name
      return unescape(c.substring(nameEQ.length,c.length)); //解码并截取我们要值
      }
    }
    return false;
  }
  //清除cookie
  function clearCookie(name) {
    setCookie(name, "", -1);
  }
  //设置cookie
  function setCookie(name, value, seconds) {
    seconds = seconds || 0; //seconds有值就直接赋值，没有为0。
    var expires = "";
    if (seconds != 0 ) { //设置cookie生存时间
      var date = new Date();
      date.setTime(date.getTime()+(seconds*1000));
      expires = "; expires="+date.toGMTString();
    }
    document.cookie = name+"="+escape(value)+expires+"; path=/"; //转码并赋值
  }

  //退出登录
  $("#signOut").click(function(){
      $.ajax({
        type:"get",
        url:"${base}/admin/common/logout.jhtml",
        success:function(data){
            //返回“0”则退出成功，重定向到登录页
            if(data == "0"){
                sessionStorage.setItem("isLogin",1)  //设置isLogin为0 表示未登录
                location.href="${base}/admin/login.jhtml"
            }
        }
      })
    })
</script>
</body>
</html>
