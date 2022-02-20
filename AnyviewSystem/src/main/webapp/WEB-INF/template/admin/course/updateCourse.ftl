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
                    <h5>修改课程</h5>

                    <div class="ibox-tools">
                       <button type="button" class="btn btn-white" onclick="window.history.back();"
                                style="margin-top: -8px;">返回
                        </button>
                            <!--  <a class="btn btn-white" href="${base}/admin/course/CourseContent.jhtml" style="margin-top: -8px;">返回</a>-->
                    </div>
                </div>
                <div class="ibox-content">
                    <form id="saveCourseForm" class="form-horizontal" action="editSave.jhtml" method="post">
                        <input type="hidden" class="form-control" id="courseId" name="id" value="${course.id}">
                        <input type="hidden" name="courseUpdater" value="${systemUser.name}">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-6" >
                                    <label class="col-sm-3 control-label">学校<span class="required"> * </span>:</label>
                                    <div class="col-sm-9">
                                    	[#if systemUser.roleId=-1]
                                      <!-- <input name="schoolName" id="schoolName" placeholder="请输入学校名称" class="form-control" value="${course.college.school.schoolName}">
                                      <input name="schoolId" id="schoolId" class="hidden" value="${course.college.school.id}"> -->
                                            <!-- <select id="schoolId" onchange="add1(this.value);" class="bs-select form-control">
	                                            <option hassubinfo="true" value="${course.college.school.id}">${course.college.school.schoolName}</option>
		                                        [#list schoolLists as school]
		                                            <option value="${school.id}">${school.schoolName}</option>
		                                        [/#list]
	                                    	</select> -->
                                        <select class="form-control" name="schoolId" id="schoolName" value="${course.college.school.id}" onchange="add1(this.value);">
                                           <option hassubinfo="true" value="${course.college.school.id}">${course.college.school.schoolName}</option>
                                        </select>
	                                    [/#if]
	                                    [#if systemUser.roleId=1]
		                            		<input type="hidden" id="schoolId" value="${systemUser.school.id}">${systemUser.school.schoolName}
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
                      <select name="collegeId" id="collegeId" class="bs-select form-control">

                    <option value="${course.college.id}">${course.college.collegeName}</option>
                        	[#list colleges as college]
                               <option value="${college.id}">${college.collegeName}</option>
                            [/#list]

                    </select>
                               </div>
                            </div>
                          </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
	                            <div class="col-sm-6 vaild">
	                                    <label class="col-sm-3 control-label">课程名称<span class="required"> * </span>:</label>
	                                    <div class="col-sm-9">
	                                        <input type="text" name="courseName" id="courseName" class="form-control"
	                                               value="${course.courseName}">
	                                    </div>
	                             </div>
                            </div>
                        </div>
                        <!-- <div class="hr-line-dashed"></div> -->
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
        $("#saveCourseForm").validate({
            submitHandler:function (form) {   //不使用submit默认提交功能，先进行自定义验证，成功则提交表单
                $.ajax({
                  url: "${base}/admin/course/check_courseName.jhtml",
                  type: "GET",
                  data: {
                    id: $("#courseId").val(),
                    courseName: $("#courseName").val(),
                    collegeId: $("#collegeId").val()
                  },
                  success:function (data) {   //发送ajax请求给后端进行验证，返回结果为true/false  
                      if(data){
                        $(".vaild").removeClass("has-error")
                        $(".vaild").addClass("has-success")
                        $(".vaild span:last").text("")
                        form.submit()
                      }else{
                        console.log(data)
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

            	 schoolName: {
                    required: true
                },
                collegeId: {
                    required: true
                },
                courseName: {
                    required: true,
                    // remote: {
                    //     url: "${base}/admin/course/check_courseName.jhtml",
                    //     cache: false,
                    //     type: "GET",
                    //     data: {
                    //     	id: function () {
                    //             return $("#courseId").val();
                    //         },

                    //         courseName: function () {
                    //             return $("#courseName").val();
                    //         },
                    //        collegeId: function () {
                    //             return $("#collegeId").val();
                    //         }
                    //     }
                    // }
                    // remote: function () {
                    //   if ($("#collegeId").val()!="") {
                    //     return {
                    //       url: "${base}/admin/course/check_courseName.jhtml",
                    //       cache: false,
                    //       type: "GET",
                    //       data: {
                    //         id: $("#courseId").val(),
                    //         courseName: $("#courseName").val(),
                    //         collegeId: $("#collegeId").val()
                    //       }
                    //     }
                    //   }
                    // }
                }
            },
            messages: {
                courseName: {
                    remote: "${message("admin.validate.exist")}"
                },
            }
        });
    });

    function add1(sId){
    	var college=document.getElementById("collegeId");
    	var options=college.getElementsByTagName("option");
    	for(var m=0;m<options.length;m++){
    		var op=options[m];
    		college.removeChild(op);
    		m--;

    	}
    	var collegeOption=document.createElement("option");
		collegeOption.value="";
		collegeOption.innerHTML = "请选择学院";
		college.appendChild(collegeOption);
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
    	             var json = eval(data); //数组
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
</script>
</body>

</html>
