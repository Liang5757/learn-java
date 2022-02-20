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
                <button type="submit" class="btn btn-success" onclick="window.location.href='${base}/admin/download/download.jhtml'">下载客户端</button>
            </div>
        </div>
    </div>
</div>

<!-- BEGIN Script -->
[#include "/admin/include/script.ftl"]
<!-- END Script -->
<script>
   var storage = window.sessionStorage
   var inpIdArr = []
    $(document).ready(function () {
        [@flash_message /]
        $(".chosen-select").chosen({width: "100%"});

        //编辑用户
        $("#btn-edit-loippi").click(function(){
            var $checkeds = jQuery($("input[name='ids']:checked"));
            if($checkeds.length > 1){
                art.warn("每次只能选择一条记录进行修改");
                return;
            }else if($checkeds.length == 0){
                art.warn("请选择一条记录进行修改");
                return;
            }
            location.href = "edit.jhtml?id=" + $($checkeds[0]).val();
        });

        // 复选框
        $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red'});
        // 选择所有，复选框改变
        $("input[name='checkAll']").on('ifChanged', function () {
            $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
            if ($(this).is(":checked")) {
                $.ajax({
                  type:"post",
                  url:"recordQuestion.jhtml",
                  data:{
                      flag:2
                  }
                })
            } else {
              $.ajax({
                type:"post",
                url:"recordQuestion.jhtml",
                data:{
                    flag:3
                }
              })
            }
        });

</script>
</body>

</html>
