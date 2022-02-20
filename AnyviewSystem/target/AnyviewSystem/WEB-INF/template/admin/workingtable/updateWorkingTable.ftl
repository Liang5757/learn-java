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
                      <button class="btn btn-white" type="button" onclick="window.history.back();" style="margin-top: -8px;">返回</button>
                          <!--  <a class="btn btn-white" href="${base}/admin/workingtable/WorkingContent.jhtml" style="margin-top: -8px;">返回</a>-->
                  </div>
                    <h5>修改课程</h5>
                </div>
                <div class="ibox-content">
                    <form id="saveTableForm" class="form-horizontal" action="${base}/admin/workingtable/editSave.jhtml"
                          method="post">
                        <input type="hidden" name="tableUpdater" value="${systemUser.name}">

                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-7">
                                    <label class="col-sm-2 control-label">学校<span class="required"> * </span>:</label>

                                    <div class="col-sm-8">
                                        [#if systemUser.roleId=-1]
                                        <!-- <select name="schoolName" id="schoolId" onchange="selectCollege(this.value)"
                                                class="bs-select form-control">

                                            <option hassubinfo="true" value="${workingTable.course.college.school.id}">
                                                ${workingTable.course.college.school.schoolName}
                                            </option>
                                            [#list schoolList as school]
                                            <option value="${school.id}">${school.schoolName}</option>
                                            [/#list]

                                        </select> -->
                                        <select class="form-control" name="schoolId" id="schoolName" value="${workingTable.course.college.school.id}" onchange="selectCollege(this.value);">
                                           <option hassubinfo="true" value="${workingTable.course.college.school.id}">${workingTable.course.college.school.schoolName}</option>
                                        </select>
                                        [/#if]

                                        [#if systemUser.roleId=1]
                                        <input type="hidden" name="schoolName" id="schoolId"
                                               value="${systemUser.school.id}">${systemUser.school.schoolName}
                                        [/#if]
                                         [#if systemUser.roleId=0]
                                        <input type="hidden" name="schoolName" id="schoolId"
                                               value="${tteacher.college.school.id}">${tteacher.college.school.schoolName}
                                        [/#if]
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-7">
                                    <label class="col-sm-2 control-label">学院<span class="required"> * </span>:</label>

                                    <div class="col-sm-8">
                                    [#if systemUser.roleId=-1||systemUser.roleId=1]
                                        <select  name="collegeName" id="collegeId" onchange="selectMajor(this.value)"
                                                class="bs-select form-control">

                                            <option id="collegeOption" value="${workingTable.course.college.id}">
                                                ${workingTable.course.college.collegeName}
                                            </option>
                                            [#list colleges as college]
                                            <option value="${college.id}">${college.collegeName}</option>
                                            [/#list]

                                        </select>
                                        [/#if]
                                        [#if systemUser.roleId=0]
                                       		 <input type="hidden" name="collegeName" id="collegeId"
                                               value="${workingTable.course.college.id}">${workingTable.course.college.collegeName}
                                       	[/#if]
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-7">
                                    <label class="col-sm-2 control-label">公开级别<span class="required"> * </span>:</label>

                                    <div class="col-sm-8">
                                        <select name="tableLevel" class="bs-select form-control">
                                            <option hassubinfo="true" value="${workingTable.tableLevel}">
                                            	[#if workingTable.tableLevel==0]
												完全保密
												[/#if]

												[#if workingTable.tableLevel==1]
												本校公开
												[/#if]
												[#if workingTable.tableLevel==2]
												完全公开
												[/#if]
                                            </option>
                                            [#list levelList as level]
                                             <option
                                             	[#if level=="完全保密"]
                                             	value="0"
												[/#if]
												[#if level=="本校公开"]
												value="1"
												[/#if]
												[#if level=="完全公开"]
												value="2"
												[/#if]
                                             >${level}</option>
                                            [/#list]
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-7">
                                    <label class="col-sm-2 control-label">课程<span class="required"> * </span>:</label>

                                    <div class="col-sm-8">
                                        <select name="courseId" id="courseId"
                                                class="bs-select form-control">

                                            <option value="${workingTable.course.id}">${workingTable.course.courseName} </option>
											                      [#list courses as course]
                                              <option value="${course.id}">${course.courseName}</option>
                                            [/#list]
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>

                       
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-7 vaild">
                                    <label class="col-sm-2 control-label">作业表<span class="required"> * </span>:</label>

                                    <div class="col-sm-8">
                                        <input type="hidden" id="tableId" name="id" value="${workingTable.id}">
                                        <input name="tableName" id="tableName" value="${workingTable.tableName}"
                                               class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-7">
                                    <label class="col-sm-2 control-label">类型<span class="required"> * </span>:</label>

                                    <div class="col-sm-8">
                                        <select name="tableType" class="bs-select form-control" id="tableType">
                                            <option hassubinfo="true" value="${workingTable.tableType}">
                                                [#if workingTable.tableType==0]
												    作业题
												[#elseif workingTable.tableType==1]
												    考试题
												[/#if]
                                            </option>
                                            [#list typeList as type]
                                                <option value="[#if type == '作业题']0[#elseif type == '考试题']1[/#if]">${type}</option>
                                            [/#list]
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                       
                       <div class="form-group" style="display: [#if workingTable.tableType!=1]none[/#if]" id="totalT">
                            <div class="row">
                                <div class="col-sm-7">
                                    <label class="col-sm-2 control-label">时长<span class="required"> * </span>:</label>
                                    <div class="col-sm-8">
                                        <input name="totalTime" id="totalTime" value="${workingTable.totalTime}"
                                               class="form-control" type="number">
                                    </div>
                                    <div class="col-sm-2" style="margin-top:7px;padding:0;font-size: 14px;"><span>分钟</span></div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-7">
                                    <label class="col-sm-2 control-label">状态<span class="required"> * </span>:</label>

                                    <div class="col-sm-8">
                                        <select name="tableStatus" class="bs-select form-control">
                                            <option hassubinfo="true" value="${workingTable.tableStatus}">
                                                [#if workingTable.tableStatus==0]
												停用
												[/#if]
												[#if workingTable.tableStatus==1]
												启用
												[/#if]
											                                            </option>
                                            [#list statusList as status]
                                            <option
                                           	 	[#if status=="停用"]
													 value="0"
												[/#if]
												[#if status=="启用"]
													value="1"
												[/#if]
                                           >${status}</option>
                                            [/#list]
                                        </select>
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

        $("#saveTableForm").validate({
            submitHandler:function (form) {   //不使用submit默认提交功能，先进行自定义验证，成功则提交表单
                $.ajax({
                  url: "${base}/admin/workingtable/check_tableName.jhtml",
                  type: "GET",
                  data: {
                    tableName: $("#tableName").val(),
                    courseId: $("#courseId").val(),
                    id: $("#tableId").val()
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
                tableName: {
                    required: true,
                    // remote: {
                    //     url: "${base}/admin/workingtable/check_tableName.jhtml",
                    //     cache: false,
                    //     type: "GET",
                    //     data: {

                    //         tableName: function () {
                    //             return $("#tableName").val();
                    //         },
                    //         courseId: function () {
                    //             return $("#courseId").val();
                    //         },
                           
                    //         id: function () {
                    //             return $("#tableId").val();
                    //         }
                    //     }
                    // }
                    // remote: function () {
                    //     if ($("#courseId").val()) {
                    //         return {
                    //             url: "${base}/admin/workingtable/check_tableName.jhtml",
                    //             cache: false,
                    //             type: "GET",
                    //             data: {
                    //                 tableName: $("#tableName").val(),
                    //                 courseId: $("#courseId").val(),
                    //                 id: $("#tableId").val()
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
                
                courseId: {
                    required: true
                },

                tableType: {
                    required: true
                },
                 totalTime: {
                    required: function () {
                        if($("#tableType").val() == "1"){
                            return true
                        }else{
                            return false
                        }
                    },
                    digits:true
                },
               
                tableStatus: {
                    required: true
                },
                tableLevel: {
                    required: true
                }
            },
            messages: {
                tableName: {
                    pattern: "${message("admin.validate.illegal")}",
                remote: "${message("admin.validate.exist")}"
        }
    }
  })
});
      //通过schoolId获取college的信息---------------------------------------------------------
    function selectCollege(sId){
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



	 function selectMajor(cId){

	var college=document.getElementById("collegeId");

	if(!college.value){
		art.warn("请选择学院")

	}else{


	// var teacher=document.getElementById("teacherId");
	// var options=teacher.getElementsByTagName("option");

	// for(var m=0;m<options.length;m++){
	// 	var op=options[m];
	// 	teacher.removeChild(op);
	// 	m--;
	// }

	// var teacherOption=document.createElement("option");
	// teacherOption.value="";
	// teacherOption.innerHTML = "请选择老师";
	// teacher.appendChild(teacherOption);


	// //通过collegeId获取teacher的信息-------------------------------------------------------------

	// $.ajax("${base}/admin/course_arrange/TeacherAjax.jhtml",// 发送请求的URL字符串。
	// 		{
 //   				type : "post", //  请求方式 POST或GET
	// 	   		// 发送到服务器的数据。
	// 	   		data:{
	// 	   			collegeId:cId
	// 	   		},
	// 	   		// 请求成功后的回调函数。
	// 	   		success :function(data){
	//              var json = eval(data); //数组
	//              $.each(json, function (index, item) {
	//                  //循环获取数据
	//         		var teacherId = json[index].id;
	//                 var teacherName = json[index].user.name;
	//                 var option1=document.createElement("option");
	//          		var text1=document.createTextNode(teacherName)
	// 				option1.value=teacherId;
	//          		option1.appendChild(text1);
	//          		teacher.appendChild(option1);
	//              });
	// 	   },

	// 	   // 请求出错时调用的函数
	// 	   error:function(){
	// 		   alert("数据发送失败");
	// 	   }
	// });

	 //通过collegeId获取course的信息-------------------------------------------------------------



	 var course=document.getElementById("courseId");
	 var options=course.getElementsByTagName("option");
	 for(var m=0;m<options.length;m++){
		var op=options[m];
		course.removeChild(op);
		m--;
	 }

	var courseOption=document.createElement("option");
	courseOption.value="";
	courseOption.innerHTML = "请选择课程";
	course.appendChild(courseOption);

	$.ajax("${base}/admin/course_arrange/CourseAjax.jhtml",// 发送请求的URL字符串。
			{
   				type : "post", //  请求方式 POST或GET
		   		// 发送到服务器的数据
		   		data:{
		   		     collegeId:$("#collegeId").val(),
		   		},
		   		// 请求成功后的回调函数。
		   		success :function(data){
	             var json = eval(data); //数组
	             $.each(json, function (index, item) {
	                 //循环获取数据
	        		var courseId = json[index].id;
	                var courseName = json[index].courseName;
	                var option1=document.createElement("option");
	         		var text1=document.createTextNode(courseName)
					option1.value=courseId;
	         		option1.appendChild(text1);
	         		course.appendChild(option1);
	             });
		   },

		   // 请求出错时调用的函数
		   error:function(){
			   alert("数据发送失败");
		   }
	});

   }

}

//选择考试题时，显示考试时长
$("#tableType").change(function () {
    if($("#tableType").val() == "1"){
        $("#totalTime").attr("min","1")
        $("#totalT").css("display","")
        $("#totalTime").val("")
    }else{
        $("#totalTime").removeAttr("min")
        $("#totalTime").val(0)
        $("#totalT").css("display","none")
    }
})

</script>
</body>

</html>
