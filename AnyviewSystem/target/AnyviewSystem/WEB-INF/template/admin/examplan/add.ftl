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
                    </div>

                    <h5>添加考试编排</h5>
                </div>
                <div class="ibox-content">
                    <form id="saveExamPlanForm" class="form-horizontal" action="save.jhtml" method="post">
                        <div class="form-group" >
                            <div class="row">
                                <div class="col-sm-7">
                                    <label class="col-sm-2 control-label">班级名称<span class="required"> * </span>:</label>

                                    <div class="col-sm-8">
                                        <select name="cId" id="cId" onchange="selectCourse(this.value)"
                                                class="bs-select form-control">

                                        <option value="">请选择班级</option>
		                                [#list classList as class]
		                                       <option value="${class.id}">${class.className}</option>
		                                [/#list]

                                        </select>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-7">
                                    <label class="col-sm-2 control-label">课程名称<span class="required"> * </span>:</label>

                                    <div class="col-sm-8">
                                        <select name="courseId" id="courseId" onchange="selectWorkingTable(this.value)"
                                                class="bs-select form-control">

                                        <option value="">请选择所属课程</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-7">
                                    <label class="col-sm-2 control-label">考试名称<span
                                            class="required"> * </span>:</label>

                                    <div class="col-sm-8">
                                        <input type="text" name="epName" id="epName" class="form-control"
                                               maxlength="200"/>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-7">
                                    <label class="col-sm-2 control-label">作业表<span class="required"> * </span>:</label>

                                    <div class="col-sm-8">
                                        <select name="vId" id="vId"
                                                class="bs-select form-control">

                                        <option value="">请选择作业表</option>

                                        </select>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-7">
                                    <label class="col-sm-2 control-label">开始方式<span class="required"> * </span>:</label>

                                    <div class="col-sm-8">
                                        <select name="kind" id="kind"
                                                class="bs-select form-control">

                                        <option value="">请选择开始方式</option>
                                        <option value="0">手动</option>
                                        <option value="1">自动</option>

                                        </select>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
							<div class="row" style="display:none" id="showTime">
                            	<div class="col-sm-7">
                                	<label class="col-sm-2 control-label">开始时间<span class="required"> * </span>:</label>

                                	<div class="col-sm-8">
                                    	<input type="text" name="startTime" id="startTime" placeholder="请选择开始时间"
                                           class="form-control"  maxlength="200"/>
                                	</div>
                            	</div>
                        	</div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-7">
                                    <label class="col-sm-2 control-label">持续时间<span
                                            class="required"> * </span>:</label>

                                    <div class="col-sm-8">
                                        <input type="text" name="duration" id="duration" class="form-control"
                                               maxlength="200"/>
                                    </div>
                                    <div class="col-sm-2" style="margin-top:7px;font-size:14px;padding:0"><span>分钟</span></div>
                                </div>
                            </div>
                        </div>
                        <!--/row-->
                        <div class="form-group">
                            <div class="col-sm-12 col-sm-offset-2" style="margin-top:20px;">
                                <input type="button" class="btn btn-danger" id="isExist" value="确定">
                            </div>
                        </div>
                	</div>
                </form>
                <!-- 查看班级及课程及作业表是否都已存在 -->
               <!--  <input id="isClassExist" class="hidden">
                <input id="isCourseExist" class="hidden">
                <input id="isWorkTableExist" class="hidden"> -->
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

        //给validate添加验证方法。验证正整数
        jQuery.validator.addMethod("positiveinteger", function(value, element) {
          var aint=parseInt(value);  
          return aint>0&& (aint+"")==value;   
        }, "Please enter a valid number.");   

        $("#saveExamPlanForm").validate({
          rules: {
				    cId: {
                      required: true
                    },
                    courseId: {
                      required: true,
                    },
                    epName: {
                      required: true
                    },
                    vId: {
                      required: true
                    },
                    kind: {
                      required: true
                    },
                    startTime: {
                      required: function () {
                          if($("#kind").val() != "1"){
                            return false
                          }else {
                            return true
                          }
                      }
                    },
                    duration: {
                      required: true,
                      // digits:true   //只能输入0或者正整数
                      positiveinteger:true
                    }
                },
                messages: {
                  courseId: {
                    pattern: "${message("admin.validate.illegal")}",
                    remote: "${message("admin.validate.exist")}",
                  },
                  duration:{  positiveinteger:"请输入正整数"}  //验证错误的信息提示
                }
        });
    });
    //通过课程ID获取作业表-----------------------------------------------------------------------
    function selectWorkingTable(id){
	var workingTable=document.getElementById("vId");
	var options=workingTable.getElementsByTagName("option");
	for(var m=1;m<options.length;m++){
		var op=options[m];
		workingTable.removeChild(op);
		m--;
	}
		$.ajax({
   				type : "post", //  请求方式 POST或GET
		   		url:"${base}/admin/examplan/WorkingTableAjax.jhtml",
		   		data:{courseId:id},
		   		success :function(data){
	             var json = eval(data); //数组
	             $.each(json, function (index, item) {
	                 //循环获取数据
	        		var workingTableId = json[index].id;
	                var workingTableName = json[index].tableName;
	                var option1=document.createElement("option");
	         		var text1=document.createTextNode(workingTableName)
					option1.value=workingTableId;
	         		option1.appendChild(text1);
	         		workingTable.appendChild(option1);
	             });
		   },

		   // 请求出错时调用的函数
		   error:function(){
			   alert("数据发送失败");
		   }
		});

    //验证课程是否已存在
    // check_ExamName(id,1)
	}

	//通过班级Id获取课程------------------------------------------------------
	 function selectCourse(cId){
	 console.log(cId)
	var course=document.getElementById("courseId");
	var options=course.getElementsByTagName("option");
	for(var m=1;m<options.length;m++){
		var op=options[m];
		course.removeChild(op);
		m--;
	}
	$.ajax("${base}/admin/examplan/CourseAjax.jhtml",// 发送请求的URL字符串。
			{
   				type : "post", //  请求方式 POST或GET
		   		// 发送到服务器的数据。
		   		data:{classId:cId},
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
    //验证班级是否已存在
    // check_ExamName(cId,0)
	}

  //验证班级及课程及作业表是否都已存在，存在则无法添加
  // function check_ExamName(Id,flag) {
  //     console.log(Id);
  //   $.ajax({
  //     type:"get",
  //     url:"check_ExamName.jhtml?Id="+Id,
  //     success:function (data) {
  //       console.log(data);
  //       if(flag == 0){  //班级
  //         $("#isClassExist").val(data)
  //       }else if(flag == 1){  //课程
  //         $("#isCourseExist").val(data)
  //       }else if(flag == 2){  //作业表
  //         $("#isWorkTableExist").val(data)
  //       }
  //     }
  //   })
  // }
  $("#isExist").click(function () {
    let cId = $("#cId").val() || -1,
        courseId = $("#courseId").val() || -1,
        vId = $("#vId").val() || -1
  $.ajax({
        type:"get",
        url:"check_ExamName.jhtml?classId="+cId+"&courseId="+courseId+"&workingTableId="+vId,
        success:function (data) {
          console.log(data)
          if(data){
              art.warn("所在班级及该课程中已存在此作业表")
          }else{
              $("#saveExamPlanForm").submit()
          }
        }
      })
})

  //判断开始方式是否为手动，是则显示开始时间
  $("#kind").change(function () {
      if($("#kind").val() == "1"){
          $("#showTime").css("display","")
      }else{
          $("#showTime").css("display","none")
      }
  })
</script>
</body>
</html>
