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

                    <h5>修改考试编排</h5>
                </div>
                <div class="ibox-content">
                    <form id="saveExamPlanForm" class="form-horizontal" action="editSave.jhtml" method="post">
                        <div class="form-group" >
                        	<div class="row" >
                                <div class="col-sm-7" style="display:none">
                                    <label class="col-sm-2 control-label">考试编排ID<span
                                            class="required"> * </span></label>

                                    <div class="col-sm-8">
                                        <input type="text" name="epId" id="epId" class="form-control" maxlength="200"
                                               value="${examPlan.epId}" readonly="readonly"/>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-7">
                                    <label class="col-sm-2 control-label">班级名称<span class="required"> * </span>:</label>

                                    <div class="col-sm-8">
                                        <select name="cId" id="cId" onchange="selectCourse(this.value)"
                                                class="bs-select form-control">

		                                [#list classList as class]
		                                       <option value="${class.id}" [#if examPlan.classSystem.className==class.className]selected[/#if]>${class.className}</option>
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

                                        [#list courseList as course]
	                                            <option value="${course.id}" [#if examPlan.course.id==course.id]selected[/#if]>${course.courseName}</option>
	                                    [/#list]
	                                    
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
                                               maxlength="200" value="${examPlan.epName}"/>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-7">
                                    <label class="col-sm-2 control-label">作业表<span class="required"> * </span>:</label>

                                    <div class="col-sm-8">
                                        <select name="vId" id="vId"
                                                class="bs-select form-control" value="${examPlan.vId}" onchange="check_Change()">

                                        [#list workingTableList as workingTable]
	                                            <option value="${workingTable.id}" [#if examPlan.workingTable.id==workingTable.id]selected[/#if]>${workingTable.tableName}</option>
	                                    [/#list]
	                                    
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

                                        <option value="" [#if examPlan.kind==""]selected[/#if]>请选择开始方式</option>
                                        <option value="0" [#if examPlan.kind==0]selected[/#if]>手动</option>
                                        <option value="1" [#if examPlan.kind==1]selected[/#if]>自动</option>

                                        </select>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
							<div class="row"  style="display:[#if examPlan.kind==0]none[/#if]" id="showTime">
                            	<div class="col-sm-7">
                                	<label class="col-sm-2 control-label">开始时间<span class="required"> * </span>:</label>

                                	<div class="col-sm-8">
                                    	<input type="text" name="startTime" id="startTime" placeholder="请选择开始时间"
                                           class="form-control"  maxlength="200" value="[#if examPlan.kind==1]${examPlan.startTime?string("yyyy-MM-dd hh:mm:ss")}[/#if]"/>
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
                                               maxlength="200" value="${examPlan.duration}"/>
                                    </div>
                                    <div class="col-sm-2" style="margin-top:7px;font-size:14px;padding:0"><span>分钟</span></div>
                                </div>
                            </div>
                        </div>
                        <!--/row-->
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-12 col-sm-offset-2">
                                <input type="button" class="btn btn-danger" id="isExist" value="确定">
                            </div>
                        </div>
                	</div>
                </form>
                <input class="hidden" id="checkChange">
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
                    required: true
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
                    // digits:true  //只能输入0或者正整数
                    positiveinteger: true
                }
            },
            messages: {
                courseId: {
                    pattern: "${message("admin.validate.illegal")}",
                    remote: "${message("admin.validate.exist")}"
                },
                duration:{  positiveinteger:"请输入正整数"}  //验证错误的信息提示
            }
        });
    });
    //通过课程ID获取作业表-----------------------------------------------------------------------
    function selectWorkingTable(id){
	var workingTable=document.getElementById("vId");
	var options=workingTable.getElementsByTagName("option");
	for(var m=0;m<options.length;m++){
		var op=options[m];
		workingTable.removeChild(op);
		m--;
	}
	
	var workingTableOption=document.createElement("option");
	workingTableOption.value="";
	workingTableOption.innerHTML = "请选择作业表";
	workingTable.appendChild(workingTableOption);
	
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
        check_Change()
	}

	//通过班级Id获取课程------------------------------------------------------
	 function selectCourse(cId){
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
	
	var workingTable=document.getElementById("vId");
	var options=workingTable.getElementsByTagName("option");
	for(var m=0;m<options.length;m++){
		var op=options[m];
		workingTable.removeChild(op);
		m--;
	}
	
	var workingTableOption=document.createElement("option");
	workingTableOption.value="";
	workingTableOption.innerHTML = "请选择作业表";
	workingTable.appendChild(workingTableOption);
	
	$.ajax("${base}/admin/examplan/CourseAjax.jhtml",// 发送请求的URL字符串。
			{
				dataType : "json", // 预期服务器返回的数据类型。
   				type : "post", //  请求方式 POST或GET
		   		contentType:"application/json", //  发送信息至服务器时的内容编码类型
		   		// 发送到服务器的数据。
		   		data:JSON.stringify({classId:cId}),
		   		async:  true , // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
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
        check_Change()
	}

    $("#isExist").click(function () {
        if($("#checkChange").val()){
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
        }else{
            $("#saveExamPlanForm").submit()
        }
        
    })	
	  //判断开始方式是否为手动，是则显示开始时间
  $("#kind").change(function () {
      if($("#kind").val() == "1"){
          $("#showTime").css("display","")
      }else{
          $("#showTime").css("display","none")
      }
  })

  //查看班级或者课程或者作业表是否有更改
  function check_Change() {
      $("#checkChange").val(1)
  }

</script>
</body>
</html>
