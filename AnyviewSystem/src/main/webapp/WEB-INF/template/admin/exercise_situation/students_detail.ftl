<!DOCTYPE html>
<html
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
                        <button class="btn btn-white" type="button" onclick="back()" style="margin-top: -8px;">返回
                        </button>
                    </div>
                    <h5>做题情况</h5>
                </div>
                <div class="dataTables_wrapper form-inline">
                    <input type="radio" name="student_scheme" value="scheme"/>按作业表
                    <input type="radio" name="student_scheme" id="student" value="student" checked/>按学生
                    <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                        <thead>
                        <tr>
                            <td>班级</td>
                            <td>学号</td>
                            <td>姓名</td>
                            <td>性别</td>
                            <td>已完成题数</td>
                            <td>开始做题时间</td>
                            <td>首次通过时间</td>
                            <td>在线时长</td>
                            <td>上次登录时间</td>
                            <td>IP地址:端口</td>
                        </tr>
                        </thead>
                        <tbody>
                        
                        [#list tables as table]
                        	<!-- VID=${vId} -->
                            <tr>
                                <td>${table.className}</td>
                                <td>
                                	<a href="${base}/admin/exercise_situation/data_analysis.jhtml?CID=${table.classId}&SID=${table.studentId}&VID=${vid}">${table.userName}</a>
                                </td>
                                <td>${table.name}</td>
                                <td>${table.sex}</td>
                                <td>${table.doneCount}</td>
                                <td>${table.beginTime?string('yyyy-MM-dd HH:mm:SS')}</td>
                                <td>${table.firstPastTime?string('yyyy-MM-dd HH:mm:SS')}</td>
                                <td>${table.accumTime}</td>
                                <td>有问题</td>
                                <td>${table.ipAndPort}</td>
                            </tr>
                        [/#list]
                        </tbody>
                    </table>
[#--                    <ul class="pagination pull-right">--]
[#--                        <li>--]
[#--                            <a href="#">上一页</a>--]
[#--                        </li>--]
[#--                        <li>--]
[#--                            <a href="#">1</a>--]
[#--                        </li>--]
[#--                        <li>--]
[#--                            <a href="#">2</a>--]
[#--                        </li>--]
[#--                        <li>--]
[#--                            <a href="#">3</a>--]
[#--                        </li>--]
[#--                        <li>--]
[#--                            <a href="#">4</a>--]
[#--                        </li>--]
[#--                        <li>--]
[#--                            <a href="#">5</a>--]
[#--                        </li>--]
[#--                        <li>--]
[#--                            <a href="#">下一页</a>--]
[#--                        </li>--]

[#--                    </ul>--]
                </div>
                <form id="studentSituationForm" action="${base}/admin/exercise_situation/scheme_detail.jhtml"
                      method="post">
                    <input type="hidden" id="ccsIds" name="ccsIds"/>
                </form>
            </div>
            [#include "/admin/exercise_situation/loading.ftl"]
        </div>
    </div>
</div>
<!-- BEGIN Script -->

[#include "/admin/include/script.ftl"]

<!-- END Script -->
<script>

    $("input[name=student_scheme]").click(function () {
        var value = $(this).val();
        if (value === 'scheme') {
            $('#loading').modal('show');
            document.getElementById("ccsIds").setAttribute("value", JSON.stringify(${ccsIds}));
            document.getElementById("studentSituationForm").submit();
        }
    });

    $(document).ready(function () {
        [@flash_message /]
    });

    function back() {
        document.location.href = "${base}/admin/exercise_situation/list.jhtml"
    }
</script>
</body>

</html>