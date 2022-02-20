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

    <style>
    .table tbody tr td{
        overflow: hidden;
        text-overflow:ellipsis;
        white-space: nowrap;
    }
        .no{
            color: red;
            font-weight: bolder;
        }
    </style>

</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">
        
          <div class="ibox-title">
          			<div class="ibox-tools">
                        <div class="ibox-tools">
                        <button class="btn btn-white" type="button"onclick="window.history.back();" style="margin-top: -8px;">返回</button>
          		         </div>
                    </div>
                    <h5>题目完成情况</h5>
            </div>
            <div class="ibox float-e-margins">
                <form id="listForm" action="checkOneContent.jhtml" method="get">
                    <div class="ibox-content">
                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable" style='table-layout:fixed;'>
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>题目名称</th>
                                    <th>章节</th>
                                    <th style="width: 300px">题目描述</th>
                                    <th>难度</th>
                                    <th>做题时间</th>
                                    <th>卷面分</th>
                                    <th>态度分</th>
                                    <th>综合分</th>
                                    <th>是否通过</th>
                                </tr>
                                </thead>

                               <tbody>
                                [#list page.content as exercise]
                                <tr>
                                    <td>${exercise_index + 1}</td>
                                    <td>${exercise.question.question_name}</td>
                                    <td>${exercise.question.chapter}</td>
                                    <td><a title="${questionContentList[exercise_index + ((page.pageable.pageNumber-1)*page.pageable.pageSize)].question_description}">${questionContentList[exercise_index + ((page.pageable.pageNumber-1)*page.pageable.pageSize)].question_description}</a></td>
                                    <td>${exercise.question.difficulty}</td>
                                    <td>${exercise.accumTime}</td>
                                    <td>${exercise.pageScore}</td>
                                    <td>${exercise.attitudeScore}</td>
                                    <td>${exercise.score}</td>
                                    <td>
                                    	[#if exercise.runResult==1]
                                    	<div class="yes">是</div>
                                    	[#else]
                                    	<div class="no">否</div>
                                    	[/#if]
                                    </td>
                                </tr>
                                [/#list]
                                </tbody>
                            </table>
                            [@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
                            [#include "/admin/include/pagination.ftl"]
                            [/@pagination]
                        </div>
                         
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>

<!-- BEGIN Script -->
[#include "/admin/include/script.ftl"]
<!-- END Script -->

</body>

</html>
