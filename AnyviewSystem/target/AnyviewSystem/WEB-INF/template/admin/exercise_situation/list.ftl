<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>${setting.siteName} - 做题情况</title>
    <meta name="keywords" content="${setting.siteName}">
    <meta name="description" content="${setting.siteName}">
    <!-- BEGIN HEADER -->
[#include "/admin/include/style.ftl"]
    <!-- END HEADER -->
    <style type="text/css">
	.circle {
		margin: 1em;	
	}

	.circles-decimals {
		font-size: .4em;
	}
	.contenta{
		display: inline-block;
		margin: 1em;
		border-radius:30px;
		height: 200px;
		width: 200px;
		background: url('${base}/resources/img/rb.jpg');
		//background: #86CCE6;
		cursor:pointer;
	}
	.containera{
		display: inline-block;
	}
	.innerp{
		text-align: center;
		font-size: 20px;
	}

    </style>
</head>

<body class="gray-bg">
	[#include "/admin/exercise_situation/loading.ftl"]
	[#list courseNameList as courseName] 
		</br>
		<div>
			<p style="font-size:30px; margin: 35px; display: inline;">${courseName}</p>
			<input type="button" value="合班统计"  class="btn btn-sm btn-danger" onclick="jump('${courseName}')"/>
			
		</div>

		</br>
		
		<form id="${courseName}jump" action="${base}/admin/exercise_situation/scheme_detail.jhtml" method="post">
			[#list courseArrangeList as courseArrange]			
				[#if courseArrange.course.courseName == courseName]	
					<div class="containera">
						
						<div class="contenta" onclick="jumpOne(${map[courseName+courseArrange.classSystem.className+"id"]});">
							<div class="circle" id=${courseName+courseArrange.classSystem.className}></div>
						</div>
						
						<p class="innerp">${courseArrange.classSystem.className}</p>
						
						<p class="innerp"><input type="checkbox" name="ccsIds" class="${courseName}checkbox" style="zoom:180%;" 
							value=${map[courseName+courseArrange.classSystem.className+"id"]}/></p>

					</div>
				[/#if]			
			[/#list]
			
		</form>
	[/#list]
	</br></br>
<p style="font-size:20px;">历史班级情况查询</p>
<div>
	<form id="historyJump" action="${base}/admin/exercise_situation/scheme_detail.jhtml" method="post">
		<p style="display: inline; font-weight:bold; font-size:15px;">课程:</p>
		<select id="historyCourse" style="display: inline; margin-right:50px">
			<option value="-1" selected>请选择课程</option>
		</select>
		
		<p style="display: inline; font-weight:bold; font-size:15px;">班级:</p>
		<select id="historyClass" style="display: inline;" name="ccsIds">
			<option value="-1" selected>请选择班级</option>
		</select>
		
		<input type="button" id="historyFind" class="btn btn-sm btn-danger" style="display: inline; margin-left:80px;" value="查询"></input>
	</form>
</div>
</br></br>

<form id="hiddenJump" action="${base}/admin/exercise_situation/scheme_detail.jhtml" method="post" style="display: none;">
	<select id="hiddenSelect" style="display: none;" name="ccsIds">
	</select>
</form>

<!-- BEGIN Script -->
[#include "/admin/include/script.ftl"]
<script type="text/javascript">
var colors = [
		['#FFFFFF', '#09F797'], ['#FFFFFF', '#09F797'], ['#FFFFFF', '#09F797'], ['#FFFFFF', '#09F797'], ['#FFFFFF', '#09F797']
	]

[#list courseNameList as courseName]
	var colorIndex = 0;
	var cn = "${courseName}";
	if(cn=="数据结构"){
		colorIndex = 1;
	}
	else if(cn=="c语言"){
		colorIndex = 2;
	}
	else if(cn=="离散数学"){
		colorIndex = 3;
	}
	
	[#list courseArrangeList as courseArrange]
		Circles.create({
			id:         "${courseName+courseArrange.classSystem.className}",
			value:		"${map[courseName+courseArrange.classSystem.className+"percent"]}",
			radius:     85,
			width:      15,
			colors:     colors[0],
			text:		"${map[courseName+courseArrange.classSystem.className+"percent"]}"+"%"
		});
	[/#list]
[/#list]

//合班统计按钮
function jump(courseName){
	//判空
	var checkBoxes = document.getElementsByName("ccsIds");
	for(var i=0;i<checkBoxes.length;i++){
		if(checkBoxes[i].checked && checkBoxes[i].className==courseName+"checkbox"){//判断不为空，提交表单
			$('#loading').modal('show');
			var formId = courseName+"jump";
			document.getElementById(formId).submit();
			return;
		}
	}
	art.warn("请至少选一个班级");	
}	

//设置历史课程中的内容
$(document).ready(function(){
	[#list courseNameList as courseName]
		var courseName = "${courseName}";
		$("#historyCourse").append("<option value='"+courseName+"'>"+courseName+"</option>");
	[/#list]
})

//监听历史课程的课程选单
$("#historyCourse").change(function(){
	$("#historyClass").empty();
	[#list historyList as courseArrange]
		if($("#historyCourse").val()=="${courseArrange.course.courseName}"){
			var className = "${courseArrange.classSystem.className}";
			//var arrangeId = ${courseArrange.id};
			var arrangeId = "${map[courseArrange.course.courseName+courseArrange.classSystem.className+"id"]}";
			$("#historyClass").append("<option value='"+arrangeId+"'>"+className+"</option>");
		}
	[/#list]
});

//查询按钮判空
$("#historyFind").click(function(){
	if($("#historyClass").val()==null){
		art.warn("该课程没有历史班级");
		return;	
	}
	else if($("#historyClass").val()=="-1"){
		art.warn("请选择班级");
		return;			
	}
	$('#loading').modal('show');
	$("#historyJump").submit();
})

//通过隐藏的form提交单个div信息
function jumpOne(id){
	$('#loading').modal('show');
	$("#hiddenSelect").empty();
	$("#hiddenSelect").append("<option value='"+id+"'selected>"+id+"</option>");
	$("#hiddenJump").submit();
}

</script>
<!-- END Script -->

</body>	
</html>
