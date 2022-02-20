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
                    <h5>添加学院</h5>
                    <div class="ibox-tools">
                        <button type="button" class="btn btn-white" onclick="window.history.back();"
                                style="margin-top: -8px;">返回
                        </button>
                            <!--   <a class="btn btn-white" href="${base}/admin/college/CollegeContent.jhtml" style="margin-top: -8px;">返回</a> -->
                    </div>
                </div>
                <div class="ibox-content">
                    <form id="saveCollegeForm" class="form-horizontal" action="${base}/admin/college/saveCollege.jhtml" method="post">
                       <input type="hidden" name="collegeUpdater" value="${systemUser.name}">
                       <input type="hidden" name="collegeCreater" value="${systemUser.name}">
                        <div class="form-group">
                            <div class="row">
                               <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">学校<span class="required"> * </span>:</label>
                                    <div class="col-sm-9">
                                   		 [#if systemUser.roleId=-1]
                                       <!-- <input name="schoolName" id="schoolName" placeholder="请输入学校名称" class="form-control">
                                       <input name="schoolId" id="schoolId" class="hidden"> -->
	                                        <!-- <select name="schoolId" id="schoolId" data-placeholder="请选择学校"
	                                                class="bs-select form-control">
	                                            <option hassubinfo="true" value="">请选择学校</option>
		                                        [#list schoolList as school]
		                                            <option value="${school.id}">${school.schoolName}</option>
		                                        [/#list]
		                                     </select> -->
                                         <select class="form-control" name="schoolId" id="schoolName">
                                         </select>
		                                 [/#if]

		                                 [#if systemUser.roleId=1]
		                                     <input type="hidden" name="schoolId" id="schoolId" value="${systemUser.school.id}">${systemUser.school.schoolName}
		                                 [/#if]
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                          <div class="row">
                            <div class="col-sm-6 vaild">
                                  <label class="col-sm-3 control-label">学院<span class="required"> * </span>:</label>


                                  <div class="col-sm-9">
                                      <input name="collegeName" id="collegeName" placeholder="请输入学院名称" class="form-control">
									                    <input type="hidden" id="collegeId" name="id" value="">
                                  </div>
                              </div>
                          </div>
                      </div>
               		    <div class="form-group" style="height:50px;">
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
        $("#saveCollegeForm").validate({
            submitHandler:function (form) {   //不使用submit默认提交功能，先进行自定义验证，成功则提交表单
                $.ajax({
                  url: "${base}/admin/college/check_collegeName.jhtml",
                  type: "GET",
                  data: {
                      id: $("#collegeId").val(),
                      collegeName: $("#collegeName").val(),
                      schoolId: function () {
                          if($("#schoolName").val()){
                              return $("#schoolName").val();
                          }else if($("#schoolId").val()){
                              return $("#schoolId").val()
                          }
                      }
                  },
                  success:function (data) {   //发送ajax请求给后端进行验证，返回结果为true/false  
                      if(data){
                        $(".vaild").removeClass("has-error")
                        $(".vaild").addClass("has-success")
                        $(".vaild span:last").text("")
                        form.submit()
                      }else{
                        $(".vaild").removeClass("has-success")
                        $(".vaild").addClass("has-error")
                        $(".vaild span:last").text("已存在")
                      }
                  }
                })
            },
            onfocusout: function(element) { $(element).valid(); },
            onkeyup: function(element) { $(element).valid(); },
            onsubmit:true,
            rules: {
                schoolId: {
                    required: true
                },
                collegeName: {
                    required: true,
                    // remote: {
                    //     url: "${base}/admin/college/check_collegeName.jhtml",
                    //     cache: false,
                    //     type: "GET",
                    //     data: {
                    //    		 id: function () {
                    //             return $("#collegeId").val();
                    //         },
                    //         collegeName: function () {
                    //             return $("#collegeName").val();
                    //         },
                    //        schoolId: function () {
                    //             if($("#schoolName").val()){
                    //               return $("#schoolName").val();
                    //             }else if($("#schoolId").val()){
                    //               return $("#schoolId").val()
                    //             }
                    //         }
                    //     }
                    // }
                    // remote: function () {
                    //   if($("#schoolName").val() || $("#schoolId").val()){
                    //     return {
                    //       url: "${base}/admin/college/check_collegeName.jhtml",
                    //       cache: false,
                    //       type: "GET",
                    //       data: {
                    //         id: $("#collegeId").val(),
                    //         collegeName: $("#collegeName").val(),
                    //         schoolId: function () {
                    //             if($("#schoolName").val()){
                    //               return $("#schoolName").val();
                    //             }else if($("#schoolId").val()){
                    //               return $("#schoolId").val()
                    //             }
                    //         }
                    //       }
                    //     }
                    //   }
                    // }
                }
            },
            messages: {
                collegeName: {
                    remote: "${message("admin.validate.exist")}"
                },
            }
        });
    });

</script>
</body>

</html>
