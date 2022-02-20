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
    
<style>
    .table tbody tr td{
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
    }
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
	
    <div class="ibox-title">
    	<div class="ibox-tools">
         <!-- <a class="btn btn-white" href="${base}/admin/exercise/list.jhtml" style="margin-top: -8px;">返回</a>-->
         <button class="btn btn-white" type="button" onclick="window.history.back();" style="margin-top: -8px;">返回</button>
    	</div>
    	<h5>${name}题目列表</h5>
	</div>
	
	
    <div class="dataTables_wrapper form-inline">
                <form id="listForm" action="selectlist.jhtml" method="get">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable" style='table-layout:fixed;'>
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>章节</th>
                                    <th>习题名</th>
                                    <th style='width:38%;'>题目描述</th>
                                    <th>通过</th>
                                    <th>得分</th>
                                    <!-- <th>完成时间</th> -->
                                    <th>打开</th>
                                </tr>
                                </thead>

                                <tbody id="record">
                                  [#list maps as map]
                                    <tr>
                                  	<th>${map_index+1}</th>
                                    <td>${map.chapter}</td>
                                    <td>${map.questionName}</td>
                           	        <!--  <td>${map.descption}</td>  -->
                                    <td><a title="${map.description}">${map.description}</a></td>
                                    <td>
                                    	[#if map.pass=="是"]<font color="green">${map.pass}</font>[/#if]
                                    	[#if map.pass=="否" || map.pass=="未提交"]<font color="red">${map.pass}</font>[/#if]
                                    </td>
                                    <td>${map.score}</td>
                                    <!-- <td>${(map.firstPastTime?string("yyyy-MM-dd hh:mm:ss"))!'无数据'}</td> -->
									<td style="text-align:center"><a class="btn btn-white" href="${base}/admin/exercise/correctQuestionStudent.jhtml?cid=${cId}&sid=${sId}&index=0&flag=0&eindex=${map_index}" >打开</a></td>
									</tr>
                                  [/#list]
                                </tbody>
                            </table>


                    <input class="hidden" name="recordflag" value="0">
                </form>
                
                
                <div id="recordTeacherId" class="hidden">
                  [#list recordTeacherId as teacherId]
                      <span>${teacherId}</span>
                  [/#list]
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
                    location.href = "${base}/admin/exercise/correctQuestion.jhtml?id="+id+"&flag=0&index="+index+""
                }else{
                    art.warn("批改题目已被删除")
                }
            }
        })

    }

</script>
</body>

</html>
