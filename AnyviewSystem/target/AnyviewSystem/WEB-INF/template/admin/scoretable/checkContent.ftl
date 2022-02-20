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
                <form id="listForm" action="checkContent.jhtml" method="get">
                    <input type="hidden" name="filter_cID" value="${filter_cID}">
                    <div class="ibox-content">
                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>学号</th>
                                    <th>姓名</th>
                                    <th>是否通过</th>
                                </tr>
                                </thead>

                               <tbody>
                                [#list page.content as exercise]
                                <tr>
                                    <td>${exercise_index + 1}</td>
                                    <td>${exercise.student.username}</td>
                                    <td>${exercise.student.name}</td>
                                    <td>
                                    	[#if exercise.runResult==1]
                                    	是
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
