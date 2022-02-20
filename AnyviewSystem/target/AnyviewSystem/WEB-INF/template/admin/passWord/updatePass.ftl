<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>${setting.siteName} - 主页</title>
    <meta name="keywords" content="${setting.siteName}">
    <meta name="description" content="${setting.siteName}">

    <!-- BEGIN HEADER -->
    [#include "/admin/include/style.ftl"]
    <!-- END HEADER -->
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>重置密码</h5>
                    <div class="ibox-tools">
                        <button class="btn btn-white" type="button"onclick="back();" style="margin-top: -8px;">返回</button>
                    </div>
                </div>
                <div class="ibox-content">
                    <form id="inputForm" class="form-horizontal" action="doforgetPass.jhtml" method="post">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2">角色名<span class="required"> * </span>:</label>
                                    <div class="col-sm-10">
                                        <label class="user-radio" style="padding-left:1px;"><input type="radio" name="roleId" value="1" class="i-checks"> 管理员　</label>
                                        <label class="user-radio"><input type="radio" name="roleId" value="0" class="i-checks"> 教师　</label>
                                        <label class="user-radio"><input type="radio" name="roleId" value="3" class="i-checks"> 学生　</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2">学校名<span class="required"> * </span>:</label>
                                    <div class="col-sm-10">
                                        <select class="schoolName" name="schoolId" style="width:100%"></select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2 control-label">用户名<span class="required"> * </span>:</label>

                                    <div class="col-sm-10">
                                        <input type="text" name="username" id="username" value="${(systemUserNew.username)!''}" class="form-control" placeholder="请输入用户名">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2 control-label">邮箱<span class="required"> * </span>:</label>

                                    <div class="col-sm-10">
                                        <input type="text" name="email" value="${(systemUserNew.email)!''}" class="form-control" placeholder="请输入邮箱">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-12 col-sm-offset-2">
                                <button class="btn btn-danger" type="submit">确定</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- BEGIN Script -->
[#include "/admin/include/script.ftl"]
<!-- END Script -->
<script>

    $(document).ready(function () {
        [@flash_message /]

        $("#browserButton").browser({input: $("#avatar")});
        $(".chosen-select").chosen({width: "100%"});

        jQuery("#inputForm").validate({
            rules: {
                username: {
                    required: true
                },
               email: {
                    required: true,
                    email: true
                },
            },
            messages: {}
        });


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
                // data.forEach(schoolObj=>{
                //     schools.push({id:schoolObj.id,text:schoolObj.schoolName})
                // })
              }
              return {
                results: schools
              };
            },
            cache: true
          },
          escapeMarkup: function (markup) { return markup; },
            // minimumInputLength: 1,
            // templateResult: formatRepo,
            // templateSelection: formatRepoSelection
          });
    });
function back() {
    		document.location.href="${base}/admin/login.jhtml"
    	}
</script>
</body>

</html>
