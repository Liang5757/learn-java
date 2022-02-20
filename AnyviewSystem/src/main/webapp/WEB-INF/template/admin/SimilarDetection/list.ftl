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
        .table .fs-up{
            display: inline-block;
            width: 14px;
            vertical-align: middle;
        }
        .table .arrow-up,.table .arrow-down,.table .selected-up,.table .selected-down{
            width: 14px;
            height: 8px;
            float: left;
        }
        .table .arrow-up{
            background: url('${base}/resources/themes/classic/images/arrow_up.png');
        }
        .table .arrow-down{
            background: url('${base}/resources/themes/classic/images/arrow_down.png');
        }
        .table .selected-up{
            background: url('${base}/resources/themes/classic/images/selected_up.png');
        }
        .table .selected-down{
            background: url('${base}/resources/themes/classic/images/selected_down.png');
        }
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <form id="listForm" action="list.jhtml" method="get">
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-3 m-b-xs">
                                <select name="filter_cId" id="cId" value="${(params.cId)!''}"
                                        class="bs-select form-control changeColor">
                                    <option value="" [#if classId==""]selected[/#if]>请选择班级</option>
                                  [#list classList as class]
                                         <option value="${class.id}" [#if classId == class.id]selected[/#if] class="opColor">${class.className}</option>
                                  [/#list]

                                </select>
                            </div>
                            <div class="col-sm-3 m-b-xs">
                                <select name="filter_courseId" id="courseId" value="${(params.courseId)!''}"
                                        class="bs-select form-control changeColor">
                                    <option value="" [#if courseId==""]selected[/#if]>请选择课程</option>
                                      [#list courseList as course]
                                         <option value="${course.id}" [#if courseId == course.id]selected[/#if] class="opColor">${course.courseName}</option>
                                      [/#list]
                                </select>
                            </div>
                            <div class="col-sm-3 m-b-xs">
                                <select name="filter_vId" id="vId" value="${(params.vId)!''}"
                                        class="bs-select form-control changeColor">
                                    <option value="" [#if workingTableId==""]selected[/#if]>请选择作业表</option>
                                      [#list workingTableList as workingTable]
                                         <option value="${workingTable.id}"  [#if workingTableId == workingTable.id]selected[/#if] class="opColor">${workingTable.tableName}</option>
                                      [/#list]
                                </select>
                            </div>
                            <div class="col-sm-1">
                                <div class="input-group">
                                    <button type="submit" class="btn btn-sm btn-danger" onclick="changePageNum()">查询</button> </span>
                                </div>
                            </div>
                        </div>
					
                        <div>
                            <a class="btn btn-outline btn-info btn-xs" id="btn-download">下载答案</a>
                        </div>
                    
                        <div>
                            <a class="btn btn-outline btn-info btn-xs" id="btn-detection" href="onlineTesting.jhtml">在线检测</a>
                        </div>

                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>
                                    <!--  <th><input type="checkbox" class="i-checks" name="checkAll"></th>  -->
                                    <th>序号</th>
                                    <th>章节</th>
                                    <th>开始时间</th>
                                    <th>结束时间</th>
                                </tr>
                                </thead>

                                <tbody>
                                [#list page.content as question]
                                <tr>
                                	<!--
                                    	<td>
                                        	<input type="checkbox" class="i-checks" name="ids" value="${question.VChapName}" id="${question.VChapName}">
                                    	</td>
                                    -->
                                    <td>${question_index + 1}</td>
                                    <td>${question.VChapName}</td>
                                    <td>${(question.startTime?string('yyyy-MM-dd HH:mm:SS'))!}</td>
                                    <td>${(question.finishTime?string('yyyy-MM-dd HH:mm:SS'))!}</td>

                                </tr>
                                [/#list]
                                </tbody>
                            </table>

                        </div>
                    </div>
                </form>
                <div id="recordschemeContent" class="hidden">
                  [#list recordschemeContent as schemeContent]
                      <span>${schemeContent}</span>
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
    $(document).ready(function () {
        [@flash_message /]
        $(".chosen-select").chosen({width: "100%"});

        $("#btn-download").click(function(){
            var $checkeds = jQuery($("input[name='ids']:checked"));
            if($('#cId').val()== ""){
                art.warn("请先筛选出一个班级");
                return;}
            else if ($('#courseId').val()== ""){
                art.warn("请先筛选出一个课程");
                return;
            }
            else if ($('#vId').val()== ""){
                art.warn("请先筛选出一个作业表");
                return;
            }

            else{
                art.warn("下载中，请稍等");
                location.href = "download.jhtml?classId="+$('#cId').val();
            }

        });


        // 初始化复选框
        $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red'});
        // 选择所有
        $("input[name='checkAll']").on('ifClicked', function () {
            $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'uncheck' : 'check');
        });
        //单击,判断是否全选
        $("input[name='ids']").on('ifChanged', function () {
            //判断当前页面的复选框是否都被选中，选中则勾选全选按钮，否则取消勾选全选
            if($(this).is(":checked")){
                isSelectAll()
            }else{
                if($("input[name='checkAll']").is(":checked")){
                    $("input[name='checkAll']").iCheck("uncheck")
                }
            }
        });

    });

</script>
</body>

</html>
