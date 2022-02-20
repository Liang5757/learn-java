<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script>
        if (window != top)
            top.location.reload();
    </script>
    <title>AnyviewSystem管理后台</title>
    <link href="${base}/resources/themes/classic/css/base.css" rel="stylesheet" type="text/css">
    <link href="${base}/resources/plugins/artdialog/skins/simple.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="${base}/resources/iconfont/iconfont.css">
    <script type="text/javascript" src="${base}/resources/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="${base}/resources/js/admin/jsbn.js"></script>
    <script type="text/javascript" src="${base}/resources/js/admin/prng4.js"></script>
    <script type="text/javascript" src="${base}/resources/js/admin/rng.js"></script>
    <script type="text/javascript" src="${base}/resources/js/admin/rsa.js"></script>
    <script type="text/javascript" src="${base}/resources/js/admin/base64.js"></script>
    <script type="text/javascript" src="${base}/resources/js/admin/common.js"></script>

    <script src="${base}/resources/plugins/artdialog/artDialog.min.js?skin=default" type="text/javascript"></script>
    <script src="${base}/resources/plugins/artdialog/artDialog.plugins.js" type="text/javascript"></script>

    <!-- i-checks -->
    <link rel="stylesheet" href="${base}/resources/css/plugins/iCheck/custom.css">
    <script src="${base}/resources/js/plugins/iCheck/icheck.min.js"></script>

    <!-- select2 -->
    <link href="${base}/resources/css/select2.min.css" rel="stylesheet" />
    <script src="${base}/resources/js/select2.min.js"></script>
    <!-- validate -->
    <script src="${base}/resources/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${base}/resources/js/plugins/validate/messages_zh.min.js"></script>
    <script>


        var storage=window.localStorage
        var sessionStorage = window.sessionStorage

        $().ready(function () {
        [@flash_message /]

            var $loginForm = $("#loginForm");
            var $enPassword = $("#enPassword");
            var $username = $("#username");
            var $password = $("#password");

            // 表单验证、记住用户名
            $loginForm.submit(function () {
                if ($username.val() == "") {
                    art.warn("${message("admin.login.usernameRequired")}");
                    return false;
                }
                if ($password.val() == "") {
                    art.warn("${message("admin.login.passwordRequired")}");
                    return false;
                }

                var rsaKey = new RSAKey();
                rsaKey.setPublic(b64tohex("${modulus}"), b64tohex("${exponent}"));
                var enPassword = hex2b64(rsaKey.encrypt($password.val()));
                $enPassword.val(enPassword);


            });

            // 初始化单选框
            $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});

            //监听单选框点击事件
            $("input[name='roleId']").on('ifClicked', function () {
                //清除用户已填写的信息
                $(".select2-selection__rendered").html('<span class="select2-selection__placeholder">请选择学校</span>')  //清除学校名称
                $(".schoolName").val("")  //清除已选择的学校名称
                $(".select2-selection__rendered").attr("title",""); //清除title

                $("#username").val("")  //清除用户名
                $("#password").val("")  //清除密码

                //用户选择超级管理员选项时则隐藏学校，否则显示学校
                if($(this).val() == "-1"){
                  $(".animate1").css("display","none")  //隐藏学校
                  //重新显示动画效果
                  if($(".anima").attr("class").indexOf("bounceIn") != -1){
                    $(".anima").removeClass("bounceIn")
                    $(".anima").addClass("restart")
                  }else if($(".anima").attr("class").indexOf("restart") != -1){
                    $(".anima").removeClass("restart")
                    $(".anima").addClass("bounceIn")
                  }                  
                }else{
                  $(".animate1").css("display","") //显示学校
                  //重新显示动画效果
                  if($(".anima").attr("class").indexOf("bounceIn") != -1){
                    $(".anima").removeClass("bounceIn")
                    $(".anima").addClass("restart")
                  }else if($(".anima").attr("class").indexOf("restart") != -1){
                    $(".anima").removeClass("restart")
                    $(".anima").addClass("bounceIn")
                  }
                }
                $("#schoolId-error").text("")
            })
        $(".schoolName").select2({
          placeholder: '请选择学校',
          ajax: {
            type:"get",
            url: "${base}/admin/AdminSchool.jhtml",
            data: function (params) {
              return {
                term: params.term,
              };
            },
            processResults: function (data) {
              var schools = []
              if(data.length>0){
                for (var i = 0; i < data.length; i++) {
                  schools.push({id:data[i].id,text:data[i].schoolName})
                }
              }
              return {
                results: schools
              };
            },
            cache: true
          },
          escapeMarkup: function (markup) { return markup; },
          allowClear  : true  //允许清除
            // minimumInputLength: 1,
            // templateResult: formatRepo,
            // templateSelection: formatRepoSelection
          });

         //监听select2清除选中项事件，删除title
          $(".schoolName").on("select2:unselect",function () {
              if($(".select2-selection__rendered").attr("title")){
                $(".select2-selection__rendered").attr("title","")
              }
          })

          //获取用户登录时所勾选的角色，并将其勾选中
          if(storage.getItem("User")){
            var $user = $("input:radio[name='roleId']")
            for(var i = 0 ; i < $user.length;i++){
              if(storage.getItem("User") == $($user[i]).val()){
                  $($user[i]).iCheck("check")
              }
            }
            //页面加载时，如果用户之前登录的为超级管理员，则将学校隐藏
            if(storage.getItem("User") == "-1"){
              $(".animate1").css("display","none")  //隐藏学校
            }
          }
        });

        
        //保存用户登录时所勾选的角色
        function saveUser() {
            storage.setItem("User",$("input:radio[name='roleId']:checked").val())
            return true
        }
        

    </script>
    <style>
    .select2-container--default .select2-selection--single .select2-selection__rendered {
        font-family: MicrosoftYaHei;
        font-size: 14px;
        line-height: 43px;
        letter-spacing: 0.3px;
        text-align: left;
        color: #1b9cff;
      }
      .select2-container--default .select2-selection--single {
        padding-left: 30px;
        background:#fafafa url(${base}/resources/themes/classic/images/home_school.png) no-repeat;
        background-position:5px 12px;
        background-size:18px;
        height: 43px;
        border: solid 1px #b8b8b8;
        border-radius: 4px;
        padding-left: 23px
      }
      .select2-container--default .select2-selection--single .select2-selection__arrow {top: 9px;}
      .select2-container--default .select2-selection--single .select2-selection__placeholder {color: #777;}
      #select2-schoolId-dj-results li{float: none;}

      #schoolId-error{
        color: red;
      }
    </style>
</head>

<body class="login-page"
      style="background: url('${base}/resources/themes/classic/images/login_bg1.jpg');">
<div class="login-content">
    <div class="login-panel">
        <div class="logo">
          <p class="login-panel-p">Anyview System</p>
        </div>
        <div style="margin:15px 40px;">
          <form class="m-t" id="loginForm" action="${base}/admin/login.jhtml" method="post" onsubmit="return saveUser()">
              <ol class="login-form">
                <li style="text-align:center">
                 <label class="user-radio"><input type="radio" name="roleId" value="-1" class="i-checks"> 超级管理员</label>
                 <label class="user-radio"><input type="radio" name="roleId" value="1" class="i-checks"> 管理员</label>
                 <label class="user-radio"><input type="radio" name="roleId" value="0" class="i-checks"> 教师</label>
                 <label class="user-radio"><input type="radio" name="roleId" value="3" class="i-checks"> 学生</label>
               </li>
               <li class="animate1 bounceIn anima">
                 <!-- <input class="schoolName" type="text" name="schoolName" id="schoolName" placeholder="请输入学校名称"> -->
                 <!-- <input name="schoolId" id="schoolId" style="display:none"> -->
                 <select class="schoolName" name="schoolId" style="width:343px;">
                    <option value="">请选择学校</option>
                 </select>
               </li>
                  <li class="animate2 bounceIn anima"><input class="acc user" type="text"
                                                       name="username" id="username" placeholder="请输入用户名"
                                                       autocomplete="off"></li>
                  <input type="hidden" id="enPassword" name="enPassword"/>
                  <li class="animate3 bounceIn anima">
                      <input class="pass" type="password" name="password"
                        id="password" placeholder="请输入密码" autocomplete="off">
                  </li>
                  <li style="text-align: right">
                      <a href="${base}/admin/common/forgetPassword.jhtml" >忘记密码?</a>
                  </li>
                  <li class="animate4 bounceIn anima">
                      <button name="submit" id="sub">登 录</button>
                      <!-- <input type="button" name="submit" id="sub" value="登 录"> -->
                  </li>
              </ol>
          </form>
        </div>
    </div>
</div>

<div class="footer-fixed" style="font:400 14px/1 微软雅黑;text-shadow: none;">
    <div class="footer" style="color:#6c6c6c;"><span>Copyright©2019 广东工业大学可视计算工作室. 版权所有 粤ICP备16008367号-1</span></div>
</div>

<script type="text/javascript">
    // $(document).ready(function () {
    //     $("#loginForm").validate({
    //         rules: {
    //          schoolName: {
    //                 required: function () {
    //                     var $roleId = $(".roleId")
    //                     var user = ""
    //                     for (var i = 0; i < $roleId.length; i++) {
    //                       if($($roleId[i]).is(":checked")){
    //                         user = $($roleId[i]).val()
    //                       }
    //                     }
    //                     if (user != "-1") {
    //                       return true
    //                     }else{
    //                       return false
    //                     }
    //                 }

    //             }
    //         }
    //     });
    // })
    $("#loginForm").validate({
            rules: {
             schoolId: {
                    required: function () {
                        var $roleId = $(".roleId")
                        var user = ""
                        for (var i = 0; i < $roleId.length; i++) {
                          if($($roleId[i]).is(":checked")){
                            user = $($roleId[i]).val()
                          }
                        }
                        if (user != "-1") {
                          return true
                        }else{
                          return false
                        }
                    }

                }
            }
        });
</script>
</body>
</html>
