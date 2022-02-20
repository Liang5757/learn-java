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
<style>
.loader { position: relative; width: 60px; height: 60px; border-radius: 50%; display: inline-block; top: -100px;left: 45%; }    
.loader-1 .loader-outter { position: absolute; border: 4px solid #f50057; border-left-color: transparent; border-bottom: 0; width: 100%; height: 100%; border-radius: 50%; -webkit-animation: loader-1-outter 1s cubic-bezier(.42, .61, .58, .41) infinite; animation: loader-1-outter 1s cubic-bezier(.42, .61, .58, .41) infinite; }
.loader-1 .loader-inner { position: absolute; border: 4px solid #f50057; border-radius: 50%; width: 40px; height: 40px; left: calc(50% - 20px); top: calc(50% - 20px); border-right: 0; border-top-color: transparent; -webkit-animation: loader-1-inner 1s cubic-bezier(.42, .61, .58, .41) infinite; animation: loader-1-inner 1s cubic-bezier(.42, .61, .58, .41) infinite; }

    /* ----------------     KEYFRAMES    ----------------- */
    
@-webkit-keyframes loader-1-outter {
 0% {
 -webkit-transform: rotate(0deg);
 transform: rotate(0deg);
}
 100% {
 -webkit-transform: rotate(360deg);
 transform: rotate(360deg);
}
}
 @keyframes loader-1-outter {
 0% {
 -webkit-transform: rotate(0deg);
 transform: rotate(0deg);
}
 100% {
 -webkit-transform: rotate(360deg);
 transform: rotate(360deg);
}
}
 @-webkit-keyframes loader-1-inner {
 0% {
 -webkit-transform: rotate(0deg);
 transform: rotate(0deg);
}
 100% {
 -webkit-transform: rotate(-360deg);
 transform: rotate(-360deg);
}
}
 @keyframes loader-1-inner {
 0% {
 -webkit-transform: rotate(0deg);
 transform: rotate(0deg);
}
 100% {
 -webkit-transform: rotate(-360deg);
 transform: rotate(-360deg);
}
}
 
</style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">

                <div class="ibox-title">
                    <div class="ibox-tools">
                        <!-- <button class="btn btn-white" type="button" onclick="window.history.back();" style="margin-top: -8px;">返回</button> -->
                        <a class="btn btn-white" href="${base}/admin/student/list.jhtml" style="margin-top: -8px;">返回</a>
                    </div>

                    <h5>学生批量导入</h5>
                </div>
                <div class="ibox-content">
                    <form id="saveStudentForm" class="form-horizontal" action="saveStudent.jhtml" method="post"  enctype="multipart/form-data">
                        <div class="form-group" >
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">导入excel文件<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <div id="file-pretty">
                                            <div class="form-group" style="padding-left: 15px;padding-right: 15px;">
                                                <input type="file" name="excelFile" id="excelFile" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row" style="color: red">
                                <div class="col-sm-6">
                                    <div class="col-sm-3"></div>
                                    <div class="col-sm-9">
                                        <h6>*注意：导入学生的学校，学院，专业，班级不能为空并且存在于该系统</h6>
                                    </div>
                                </div>
                            </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-12 col-sm-offset-2">
                                <button type="submit"  class="btn btn-danger">确定</button>
                            </div>
                        </div>
                	</div>
                </form>
                <div class="loader loader-1 hidden">
                    <div class="loader-outter"></div>
                    <div class="loader-inner"></div>
                </div>
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
        $(".chosen-select").chosen({width: "100%"});
        $(".input-daterange").datepicker({keyboardNavigation: false, forceParse: false, autoclose: true});
        $(".browserButton").browser({
            callback: function (url) {
                $("#" + $(this).attr('for')).val(url);
            }
        });
        $(".input-group.date").datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });

        $("#saveStudentForm").validate({
          rules:{
              excelFile:{
                required:true
              }
          },
          success:function () {   //验证成功并提交表单时 显示加载动画
            $(".loader").removeClass("hidden")
          }
        })

    });
</script>
</body>
</html>
