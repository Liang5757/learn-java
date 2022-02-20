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
                    <div class="ibox-tools">
                        <button class="btn btn-white" type="button"onclick="window.history.back();" style="margin-top: -8px;">返回</button>
                      <!--  <a class="btn btn-white" href="${base}/admin/teacher/list.jhtml" style="margin-top: -8px;">返回</a>-->
                    </div>

                    <h5>添加教师</h5>
                </div>
                <div class="ibox-content">
                    <form id="saveTeacherForm" class="form-horizontal" action="save.jhtml" method="post">
                        <div class="form-group" >
                        	<div class="row" >
                                <div class="col-sm-6" style="display:none">
                                    <label class="col-sm-3 control-label">教师ID<span class="required"> * </span>:</label>
                                    <div class="col-sm-9">
                                        <input type="text" name="id" id="teacherId" class="form-control" maxlength="200"
                                               readonly="readonly"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                          <div class="row">
                              <div class="col-sm-6">
                                  <label class="col-sm-3 control-label">学校<span class="required"> * </span>:</label>
                                  <div class="col-sm-9">
                                  	[#if systemUser.roleId=-1]
                                    <!-- <input name="schoolName" id="schoolName" placeholder="请输入学校名称" class="form-control">
                                    <input name="schoolId" id="schoolId" class="hidden"> -->
                                          <!-- <select name="schoolName" id="schoolId" onchange="add1(this.value)"
                                              class="bs-select form-control" >
	                                         <option hassubinfo="true" value="">请选择学校</option>
	                                         [#list schoolList as school]
	                                            <option value="${school.id}">${school.schoolName}</option>
	                                      	 [/#list]
	                                      </select> -->
                                        <select class="form-control" name="schoolId" id="schoolName" onchange="add1(this.value);">
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
                              <div class="col-sm-6">
                                  <label class="col-sm-3 control-label">学院<span class="required"> * </span>:</label>
                                  <div class="col-sm-9">
                                      <select name="collegeName" id="collegeId"
                                              class="bs-select form-control">
                                      <option value="">请选择学院</option>
                                       [#if systemUser.roleId=1]
	                                        [#list collegeList as college]
	                                           <option value="${college.id}">${college.collegeName}</option>
	                                        [/#list]

		                                 [/#if]
                                      </select>
                                  </div>
                              </div>
                          </div>
                      </div>
                      <div class="form-group">
                          <div class="row">
                              <div class="col-sm-6 vaild">
                                  <label class="col-sm-3 control-label">工号<span class="required"> * </span>:</label>
                                  <div class="col-sm-9">
                                      <input type="text" name="number" placeholder="请输入教师工号" id="number" class="form-control"
                                             maxlength="200"/>
                                  </div>
                              </div>
                          </div>
                     </div>
                     <div class="form-group">
                          <div class="row">
                              <div class="col-sm-6">
                                  <label class="col-sm-3 control-label">姓名<span class="required"> * </span>:</label>
                                  <div class="col-sm-9">
                                      <input type="text" name="name" placeholder="请输入教师姓名" id="name" class="form-control"
                                             maxlength="200"/>
                                  </div>
                              </div>
                          </div>
                    </div>
                    <div class="form-group">
                          <div class="row">
                              <div class="col-sm-6">
                                  <label class="col-sm-3 control-label">性别<span class="required"> * </span>:</label>
                                  <div class="col-sm-9">
                                      <select name="sex" id="sex"
                                              class="bs-select form-control">
                                      <option value="">请选择性别</option>
                                      <option value="男">男</option>
                                      <option value="女">女</option>
                                      </select>
                                  </div>
                              </div>
                          </div>
                   </div>
                   <div class="form-group">
                          <div class="row">
                              <div class="col-sm-6">
                                  <label class="col-sm-3 control-label">邮箱<span class="required"> * </span>:</label>
                                  <div class="col-sm-9">
                                      <input type="text" name="email" placeholder="请输入教师邮箱" id="email" class="form-control"
                                             maxlength="200"/>
                                  </div>
                              </div>
                          </div>
                   </div>
                        <!--/row-->

                        <!-- <div class="hr-line-dashed"></div> -->
                    <div class="form-group">
                        <div class="col-sm-12 col-sm-offset-2">
                                <button type="submit"  class="btn btn-danger">确定</button>
                        </div>
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
<script type="text/javascript">



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

        $("#saveTeacherForm").validate({
            submitHandler:function (form) {   //不使用submit默认提交功能，先进行自定义验证，成功则提交表单
                $.ajax({
                  url: "${base}/admin/teacher/check_username.jhtml",
                  type: "GET",
                  data: {
                    teacherId: function () {
                      return $("#teacherId").val();
                    },
                    username: function () {
                      return $("#number").val();
                    },
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
            rules: {
               	name: {
                    required: true
                },
                number: {
                    required: true,
                   //  remote: {
                   //      url: "${base}/admin/teacher/check_username.jhtml",
                   //      cache: false,
                   //      type: "GET",
                   //      data: {
                   //      	teacherId: function () {
                   //              return $("#teacherId").val();
                   //      	},
                   //          username: function () {
                   //              return $("#number").val();
                   //          },
                   //         schoolId: function () {
                   //              if($("#schoolName").val()){
                   //                return $("#schoolName").val();
                   //              }else if($("#schoolId").val()){
                   //                return $("#schoolId").val()
                   //              }
                   //      	}
                   //  	}
                  	// }
                    // remote: function () {
                    //     if($("#schoolName").val() || $("#schoolId").val()){
                    //       return {
                    //         url: "${base}/admin/teacher/check_username.jhtml",
                    //         cache: false,
                    //         type: "GET",
                    //         data: {
                    //           teacherId: $("#teacherId").val(),
                    //           username: $("#number").val(),
                    //           schoolId: function () {
                    //             if($("#schoolName").val()){
                    //               return $("#schoolName").val();
                    //             }else if($("#schoolId").val()){
                    //               return $("#schoolId").val()
                    //             }
                    //           }
                    //         }
                    //       }
                    //     }
                    // }
                },

                sex: {
                    required: true
                },
                collegeName: {
                    required: true
                },
                schoolName: {
                    required: true
                },
                email: {
                	required: true,
                    email: true
                }
            },
            messages: {
                number: {
                    pattern: "${message("admin.validate.illegal")}",
                    remote: "${message("admin.validate.exist")}"
                }
            }
        });



    });

   function add1(sId){
	var college=document.getElementById("collegeId");
	var options=college.getElementsByTagName("option");
	for(var m=1;m<options.length;m++){
		var op=options[m];
		college.removeChild(op);
		m--;
	}
	if($('#schoolName').val()!=""){
			$.ajax("${base}/admin/major/MajorAjax.jhtml",// 发送请求的URL字符串。
			{
				dataType : "json", // 预期服务器返回的数据类型。
   				type : "post", //  请求方式 POST或GET
		   		contentType:"application/json", //  发送信息至服务器时的内容编码类型
		   		// 发送到服务器的数据。
		   		data:JSON.stringify({id:sId}),
		   		async:  true , // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
		   		// 请求成功后的回调函数。
		   		success :function(data){
		       		$("#list").html('');
	             	var json = eval(data); //数组  //eval:运行字符串中的js代码
	             	$.each(json, function (index, item) {
		                 //循环获取数据
		        		var collegeId = json[index].id;
		                var collegeName = json[index].collegeName;
		                var option1=document.createElement("option");
		         		var text1=document.createTextNode(collegeName)
						option1.value=collegeId;
		         		option1.appendChild(text1);
		         		college.appendChild(option1);
		                // $("#list").html($("#list").html() + "<br>" + collegeId + " - " + collegeCollege +"<br/>");
	             	});

		   },

		   // 请求出错时调用的函数
		   error:function(){
			   alert("数据发送失败");
		   }


		});
	}
		}


</script>
</body>
</html>
