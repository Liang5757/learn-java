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
                        <button class="btn btn-white" type="button" onclick="back()" style="margin-top: -8px;">返回</button>
                    </div>
                    <h5>章节完成情况</h5>
                </div>
                <div class="dataTables_wrapper form-inline">
                    <input type="radio" name="table_bar" value="table" checked/>表格
                    <input type="radio" name="table_bar" id="bar" value="bar"/>图形
                    <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                        <thead>
                        <tr>
                            <td>章</td>
                            <td>题目编号</td>
                            <td>题目简介</td>
                            <td>已完成人数</td>
                            <td>已开始做题人数</td>
                            <td>编译正确次数</td>
                            <td>运行正确次数</td>
                            <td>编译错误次数</td>
                            <td>运行错误次数</td>
                            <td>平均完成时长</td>
                        </tr>
                        </thead>
                        <tbody>
                        [#list tables as table]
                            <tr>
                                <td>${table.chapter}</td>
                                <td>${table.questionName}</td>
                                <td>${table.description}</td>
                                <td>${table.finish}</td>
                                <td>${table.beginWorkCount}</td>
                                <td>${table.cmpRightCount}</td>
                                <td>${table.runRightCount}</td>
                                <td>${table.cmpErrorCount}</td>
                                <td>${table.runErrorCount}</td>
                                <td>${table.averageTime}</td>
                            </tr>
                        [/#list]
                        </tbody>
                    </table>
[#--                    [@pagination pageNumber = page.pageNumber totalPages = page.totalPages]--]
[#--                        [#include "/admin/include/pagination.ftl"]--]
[#--                    [/@pagination]--]
                </div>
            </div>
        </div>

        <form id="chapterForm" action="${base}/admin/exercise_situation/chapter_bar.jhtml" method="post">
            <input type="hidden" id="schemeId" name="schemeId"/>
            <input type="hidden" id="chapterName" name="chapterName" />
            <input type="hidden" id="studentIds" name="studentIds" />
        </form>
        [#include "/admin/exercise_situation/loading.ftl"]
    </div>
</div>
<!-- BEGIN Script -->

[#include "/admin/include/script.ftl"]

<!-- END Script -->
<script>
    $("input[name=table_bar]").click(function(){
        var value = $(this).val();
        if (value === 'bar'){
            $('#loading').modal('show');
            $("#bar").attr("checked","true");
            document.getElementById("schemeId").setAttribute("value",${schemeId});
            document.getElementById("chapterName").setAttribute("value",'${chapterName}');
            document.getElementById("studentIds").setAttribute("value",JSON.stringify(${studentIds}));
            document.getElementById("chapterForm").submit();
        }
    });
    $(document).ready(function () {
        [@flash_message /]
    });
    function back() {
        document.location.href="${base}/admin/exercise_situation/list.jhtml"
    }

</script>
</body>

</html>