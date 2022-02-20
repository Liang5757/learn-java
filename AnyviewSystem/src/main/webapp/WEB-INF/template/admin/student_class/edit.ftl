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
                       <!-- <a class="btn btn-white" href="${base}/admin/student_class/list.jhtml" style="margin-top: -8px;">返回</a>-->
                    </div>

                    <h5>修改班级</h5>
                </div>
                <div class="ibox-content">
                    <form id="saveClassForm" class="form-horizontal" action="editSave.jhtml" method="post">
                        <div class="form-group" >
                        	<div class="row" >
                                <div class="col-sm-6" style="display:none">
                                    <label class="col-sm-3 control-label">班级ID<span
                                            class="required"> * </span></label>

                                    <div class="col-sm-9">
                                        <input type="text" name="id" id="classId" class="form-control" maxlength="200"
                                               value="${student_class.id}" readonly="readonly"/>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">学校<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                    	[#if systemUser.roleId=-1]
                                      <!-- <input name="schoolName" id="schoolName" placeholder="请输入学校名称" value="${student_class.major.college.school.schoolName}" class="form-control">
                                      <input name="schoolId" id="schoolId" class="hidden" value="${student_class.major.college.school.id}"> -->
	                                        <!-- <select name="schoolName" id="schoolId" onchange="selectCollege(this.value)"
	                                                class="bs-select form-control">

	                                        	<option hassubinfo="true" value="${student_class.major.college.school.id}">${student_class.major.college.school.schoolName}</option>
		                                        	[#list schoolList as school]
			                                            <option value="${school.id}">${school.schoolName}</option>
			                                        [/#list]

	                                        </select> -->
                                          <select class="form-control" name="schoolId" id="schoolName" value="${student_class.major.college.school.id}" onchange="add1(this.value);">
                                             <option hassubinfo="true" value="${student_class.major.college.school.id}">${student_class.major.college.school.schoolName}</option>
                                          </select>
	                                     [/#if]
	                                     [#if systemUser.roleId=1]
		                                     <input type="hidden" name="schoolName" id="schoolId" value="${systemUser.school.id}">${systemUser.school.schoolName}
		                                 [/#if]
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">学院<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <select name="collegeName" id="collegeId"  onchange="selectMajor(this.value)"
                                                class="bs-select form-control">

                                        [#list collegeList as college]
	                                            <option value="${college.id}" [#if student_class.major.college.id==college.id]selected[/#if]>${college.collegeName}</option>
	                                    [/#list]

                                        </select>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">专业<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <select name="majorName" id="majorId"
                                                class="bs-select form-control">

                                         [#list majorList as major]
	                                            <option value="${major.id}" [#if student_class.major.majorName==major.majorName]selected[/#if]>${major.majorName}</option>
	                                     [/#list]

                                        </select>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">年届<span
                                            class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <input type="text" name="year" placeholder="请输入班级年届" id="year" class="form-control"
                                               maxlength="200" value="${student_class.year}"/>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6 vaild">
                                    <label class="col-sm-3 control-label">班级<span
                                            class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <input type="text" name="className" placeholder="请输入班级名称" id="className" class="form-control"
                                               maxlength="200" value="${student_class.className}"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--/row-->
                        <div class="hr-line-dashed"></div>
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

        $("#saveClassForm").validate({
            submitHandler:function (form) {   //不使用submit默认提交功能，先进行自定义验证，成功则提交表单
                $.ajax({
                  url: "${base}/admin/student_class/check_classname.jhtml",
                  type: "GET",
                  data: {
                    classId: $("#classId").val(),
                    className: $("#className").val(),
                    majorId: $("#majorId").val(),
                    year: $("#year").val()
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
				className: {
                    required: true,
                    // remote: {
                    //     url: "${base}/admin/student_class/check_classname.jhtml",
                    //     cache: false,
                    //     type: "GET",
                    //     data: {
                    //     	classId: function () {
                    //             return $("#classId").val();
                    //         },
                    //         className: function () {
                    //             return $("#className").val();
                    //         },
                    //        	majorId: function () {
                    //             return $("#majorId").val();
                    //     	}
                    // 	}
                    // }
                    // remote: function () {
                    //     if($("#classId").val()!="" && $("#className").val()!="" && $("#majorId").val()!=""){
                    //         return {
                    //             url: "${base}/admin/student_class/check_classname.jhtml",
                    //             cache: false,
                    //             type: "GET",
                    //             data: {
                    //                 classId: $("#classId").val(),
                    //                 className: $("#className").val(),
                    //                 majorId: $("#majorId").val()
                    //             }
                    //         }
                    //     }
                    // }
                },
                schoolName: {
                    required: true
                },
                collegeName: {
                    required: true
                },
                majorName: {
                    required: true
                },
                year: {
                    required: true,
                    digits:true
                    
                }
            },
            messages: {
                className: {
                    pattern: "${message("admin.validate.illegal")}",
                    remote: "${message("admin.validate.exist")}"
                },majorName: {
                    pattern: "${message("admin.validate.illegal")}",
                    remote: "${message("admin.validate.exist")}"
                },year: {
                    pattern: "${message("admin.validate.illegal")}",
                    remote: "${message("admin.validate.exist")}"
                }
            }
        });
    });

    //通过学校id获取相应的学院----------------------------------------------------
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

	var major=document.getElementById("majorId");
	var options=major.getElementsByTagName("option");
	for(var m=0;m<options.length;m++){
		var op=options[m];
		major.removeChild(op);
		m--;
	}

	var majorOption=document.createElement("option");
	majorOption.value="";
	majorOption.innerHTML = "请选择专业";
	major.appendChild(majorOption);

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
	             });
		   },

		   // 请求出错时调用的函数
		   error:function(){
			   alert("数据发送失败");
		   }
	});


	}
	//通过学院id获取相应的专业--------------------------------------------------------------
	 function selectMajor(cId){
	var major=document.getElementById("majorId");
	var options=major.getElementsByTagName("option");
	for(var m=0;m<options.length;m++){
		var op=options[m];
		major.removeChild(op);
		m--;
	}

	var majorOption=document.createElement("option");
	majorOption.value="";
	majorOption.innerHTML = "请选择专业";
	major.appendChild(majorOption);

	$.ajax("${base}/admin/student_class/MajorAjax.jhtml",// 发送请求的URL字符串。
			{
				dataType : "json", // 预期服务器返回的数据类型。
   				type : "post", //  请求方式 POST或GET
		   		contentType:"application/json", //  发送信息至服务器时的内容编码类型
		   		// 发送到服务器的数据。
		   		data:JSON.stringify({id:cId}),
		   		async:  true , // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
		   		// 请求成功后的回调函数。
		   		success :function(data){
	             var json = eval(data); //数组
	             $.each(json, function (index, item) {
	                 //循环获取数据
	        		var majorId = json[index].id;
	                var majorName = json[index].majorName;
	                var option1=document.createElement("option");
	         		var text1=document.createTextNode(majorName)
					option1.value=majorId;
	         		option1.appendChild(text1);
	         		major.appendChild(option1);
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
