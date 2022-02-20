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
                    <!--
						<button class="btn btn-white" type="button" style="margin-top: -8px;" 
							onclick="window.location='${base}/admin/exercise/export.jhtml?pid=${pid}&sid=${sid}&cid=${cid}'">导出此题答案</button>
					-->
						<button class="btn btn-white" type="button" onclick="window.location='${base}/admin/exercise/stulist.jhtml?id=${pid}';" 
							style="margin-top: -8px;">返回</button>
                    </div>

                    <h5>按题目批改</h5>

                </div>
                <div class="ibox-content">
                    <form id="saveCorrectForm" class="form-horizontal" action="correctSave.jhtml" method="post">
                      [#list exerciseList as exercise]
                          [#if exercise_index > 0]
                        <div class="form-group showWorkTable" style="display:none">
                          [#else]
                        <div class="form-group showWorkTable">
                          [/#if]
                       		<div class="row">
                                <div class="col-sm-6" style="display:none">
                                    <label class="col-sm-3 control-label">标志位<span
                                            class="required"> * </span></label>

                                    <div class="col-sm-9">
                                        <input type="text" name="flag" id="eId" class="flag" maxlength="200"
                                               value="${flag}" readonly="readonly"/>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                        	<div class="row">
                                <div class="col-sm-6" style="display:none">
                                    <label class="col-sm-3 control-label">批改ID<span
                                            class="required"> * </span></label>

                                    <div class="col-sm-9">
                                        <input type="text" name="eId${exercise_index}" id="eId" class="form-control"
                                               maxlength="200"
                                               value="${exercise.EId}" readonly="readonly"/>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2 control-label">序号:</label>

                                    <div class="col-sm-9">
                                        <label class="col-sm-6 control-label">${sindex+1}</label>                            
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                          
                            <div class="row">
                            <!-- if-->
                                <div class="col-sm-6">
                                    <label class="col-sm-2 control-label">学号:</label>

                                    <div class="col-sm-9">
                                        <label class="col-sm-6 control-label">${exercise.student.username}</label>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                           <div class="row">
                               <div class="col-sm-6">
                                   <label class="col-sm-2 control-label">姓名:</label>

                                   <div class="col-sm-9">
                                       <label class="col-sm-6 control-label">${exercise.student.name}</label>
                                   </div>
                               </div>
                           </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2 control-label">题目描述:</label>

                                    <div class="col-sm-9">
                                        <textarea id="description" class="col-sm-9" rows="${(questionContentList[exercise_index].question_description?length / 18)?int}" 
                                       		style="border:none; resize:none; width:600px; padding:0; margin:0" readonly>${questionContentList[exercise_index].question_description}</textarea>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2 control-label">标准答案:</label>

                                    <div class="col-sm-9">
                                        <textarea id="standard" class="col-sm-9" rows="${(questionContentList[exercise_index].standard_answer?length / 17)?int}" 
                                        style="border: none;resize:none;width:600px;" readonly>${questionContentList[exercise_index].standard_answer}</textarea>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2 control-label">学生答案:</label>
                                    <div class="col-sm-9">
                                        <textarea id="answer" class="col-sm-9" rows="${(studentAnswerList[exercise_index]?length / 17)?int}" 
                                        	style="border: none;resize:none;width:600px;" readonly>${studentAnswerList[exercise_index]}</textarea>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2 control-label">通过<span class="required"></span>:</label>
                                    <div class="col-sm-4" style="margin-top:7px">
                                        <p id=pass>[#if exercise.runResult == 0]否[#elseif exercise.runResult == 1]是[#else]/[/#if]</p>
                                        <input class="hidden" name="kind${exercise_index}" id="kind" value="[#if exercise.runResult == 1]1[#else]2[/#if]">
                                    </div>
                                </div>
                            </div>
                          &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2 control-label">评语<span class="required"></span>:</label>

                                    <div class="col-sm-9 form-inline">
                                        <textarea rows="6" cols="60" placeholder="请填写评语" id="eComment"
                                                  name="eComment${sindex}"
                                        >${exercise.EComment!""}</textarea>
                                    </div>                                  
                                </div>
                            </div>
                        </div>
                      [/#list]
                        <!--/row-->

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-2">
                                <input class="btn btn-outline btn-success" type="button" id="prev" value="上一个">
                            </div>
                            <div class="col-sm-2">
                                <input type="button" class="btn btn-danger" id="save1" value="确定"></input>                          
                            </div>
                            <div class="col-sm-2">
                                <input class="btn btn-outline btn-success" type="button" id="next" value="下一个">
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

    var $workTable = $(".showWorkTable")  //获取所有的作业列表

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

        
        $($workTable[0]).css("display", "none");
        $($workTable[${index}]).css("display", "block");

    });


</script>
</body>
<script>

	
	$("#next").click(function (){
		if(${sindex}==${nsindex}){
			art.warn("后面没有了哦");
			return;
		}
		
		window.location.href="${base}/admin/exercise/correctQuestionByProblem.jhtml?sindex=${nsindex}&flag=1";
	})
	
	
	$("#prev").click(function (){
		if(${sindex}==${psindex}){
			art.warn("前面没有了哦");
			return;
		}
		
		window.location.href="${base}/admin/exercise/correctQuestionByProblem.jhtml?sindex=${psindex}&flag=1";
	})
	
  
	$("#save1").click(function () {		
		$.ajax({
			type:"POST",
     		url:"save1.jhtml",
     		data:{sindex:${sindex}, comment:$("#eComment").val()},
     		success:function(result){
     			art.warn("评价成功");
     			window.scrollTo(0,0);
     		}
     		
     	})	
     })
   
</script>
</html>
