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
    <style type="text/css">
        .col-sm-3{
            color:#a5a5a5;
        }
        .opColor{
            color: #333
        }

    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
	<div class="ibox-title">
    	<div class="ibox-tools">
    		<button class="btn btn-white" type="button"onclick="window.location='${base}/admin/exercise/correct.jhtml?workingTableId=${vid}'" 
    			style="margin-top: -8px;">返回</button>
         	<!--<button class="btn btn-white" type="button"onclick="window.history.back();" style="margin-top: -8px;">返回</button>-->
         	<!--<td style="text-align:center"><a class="btn btn-white" href="${base}/admin/exercise/correct.jhtml?workingTableId=${vid}" >返回</a></td>-->
    	</div>
    	<h5>该题目学生列表</h5>
	</div>
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <form id="listForm" action="selectlist.jhtml" method="get">
                    <div class="ibox-content">
                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>姓名</th>
                                    <th>学号</th>
                                    <th>通过</th>
                                    <th>得分</th>
                                    <th>完成时间</th>
                                    <th>打开</th>
                                </tr>
                                </thead>

                                <tbody id="record">
                                  [#list exerciseList as exercise]

                                  <tr>
                                      <th>${exercise_index+1}</th>
                                       [#if exercise.student != null]
                                        <td>${exercise.student.name}</td>
                                        <td>${exercise.student.username}</td>
                                       [#else]
                                        <td></td>
                                        <td></td>
                                       [/#if]
                                      <td>[#if exercise.runResult == 0]<font color="red">否</font>
                                      	[#elseif exercise.runResult == 1]<font color="green">是</font>[#else]/[/#if]</td>
                                      <td>${exercise.score}</td>
                                        [#if exercise.accumTime!= null]
                                        <td>${exercise.accumTime}</td>
                                        [#else]
                                        <td></td>
                                        [/#if]
                                       
                                       <td style="text-align:center"><a class="btn btn-white" href="${base}/admin/exercise/correctQuestionByProblem.jhtml?sindex=${exercise_index}&flag=1" >打开</a></td>
                                      <!--<td style="text-align:center"><input type="button" class="btn btn-white" value="打开" id="exercise" onclick="checkexercise(${exercise.question.id},${exercise_index})"></td>-->
                                  </tr>

                                  [/#list]
                                  [#list studentList as student]
                                  <tr>
                                      <th>${student_index+1+exerciseList?size}</th>
                                        <td>${student.name}</td>
                                        <td>${student.username}</td>
                                      <td><font color="red">否</font></td>
                                      <td>0</td>
                                      <td>0</td>  
                                      <td>尚未提交</td>   
                                  </tr>

                                  [/#list]                               
                                </tbody>
                            </table>

                        </div>
                    </div>
                    <input class="hidden" name="recordflag" value="0">
                </form>
                <div id="recordTeacherId" class="hidden">
                  [#list recordTeacherId as teacherId]
                      <span>${teacherId}</span>
                  [/#list]
                </div>
            </div>
        </div>
    </div>
</div>

<!-- BEGIN Script -->
[#include "/admin/include/script.ftl"]
<!-- END Script -->
<script>

    function checkexercise(id,index){
        $.ajax({
            url:"${base}/admin/exercise/judgeQuestion.jhtml?id="+id+"&flag=0",
            success:function(data){
                if(data != "0"){
                    location.href = "${base}/admin/exercise/correctQuestion.jhtml?id="+id+"&flag=1&index="+index
                }else{
                    art.warn("批改题目已被删除")
                }
            }
        })

    }

</script>
</body>

</html>
