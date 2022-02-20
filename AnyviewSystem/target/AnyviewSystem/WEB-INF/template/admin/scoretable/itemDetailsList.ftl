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
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">

            <div class="ibox-title">
                <div class="ibox-tools">
                    <div class="ibox-tools">
                        <button class="btn btn-white" type="button" onclick="window.history.back();"
                                style="margin-top: -8px;">返回
                        </button>
                    </div>
                </div>
                <h5>题目完成情况</h5>

            </div>
            <div class="ibox float-e-margins">
                <form id="listForm" action="itemDetails.jhtml" method="get">
                    <input type="hidden" value="${filter_cID}" name="filter_cID">
                    <input type="hidden" value="${filter_vId}" name="filter_vId">
                    <input type="hidden" value="${filter_courseId}" name="filter_courseId">
                    <div class="ibox-content">
                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable" style='table-layout:fixed;'>
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>题目名称</th>
                                    <th>章节</th>
                                    <th style="width: 400px">题目描述</th>
                                    <th>难度</th>
                                    <th>完成情况</th>
                                    <th>通过率</th>
                                </tr>
                                </thead>

                                <tbody>
                                [#list page.content as exercise]
                                <tr>
                                    <td>${exercise_index + 1}</td>
                                    [#if exercise.question??]
                                        <td>${exercise.question.question_name}</td>
                                        <td>${exercise.question.chapter}</td>
                                        <td><a title="${questionContentList[exercise_index + ((page.pageable.pageNumber-1)*page.pageable.pageSize)].question_description}">${questionContentList[exercise_index + ((page.pageable.pageNumber-1)*page.pageable.pageSize)].question_description}</a></td>
                                        <td>${exercise.question.difficulty}</td>
                                    [#else ]
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    [/#if]
                                    <td style="text-align:center"><a class="btn btn-white"
                                                                     href="${base}/admin/ScoreTable/checkContent.jhtml?questionId=[#if exercise.question??]${exercise.question.id}[/#if]&filter_cID=${filter_cID}">查看</a>
                                    </td>
                                    <td>${exercise.passPercent*100}%</td>
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
