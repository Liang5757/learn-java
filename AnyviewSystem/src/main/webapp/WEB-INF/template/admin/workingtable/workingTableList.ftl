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
                <form id="listForm" action="${base}/admin/workingtable/WorkingContent.jhtml">
                    <div class="ibox-content">
                        <div class="row">

							[#if systemUser.roleId=-1||systemUser.roleId=0]
                            <div class="col-sm-3 m-b-xs">
                              <input type="text" placeholder="请输入作业表学校" name="filter_schoolName" value="${(params.schoolName)!''}" class="input-sm form-control">
                            </div>
                            [/#if]
                            <div class="col-sm-3 m-b-xs">
                              <input type="text"  placeholder="请输入作业表学院" name="filter_collegeName" value="${(params.collegeName)!''}" class="input-sm form-control">
                            </div>
                            <div class="col-sm-3 m-b-xs">
                              <input type="text" placeholder="请输入作业表课程" name="filter_courseName" value="${(params.courseName)!''}" class="input-sm form-control">
                            </div>
                            <div class="col-sm-3 m-b-xs">
                              <input type="text" placeholder="请输入作业表名称" name="filter_tableName" value="${(params.tableName)!''}" class="input-sm form-control">
                            </div>                     

                            <div class="col-sm-1">
                                <div class="input-group">
                                    <button type="submit" class="btn btn-sm btn-danger" onclick="changePageNum()"> 查询</button> </span>
                                </div>
                            </div>
                        </div>

                        <div>
                            <a class="btn btn-outline btn-success btn-xs" id="btn-add-loippi" href="add.jhtml"><i
                                    class="fa fa-plus"></i> 添加</a>

                            <a class="btn btn-info btn-xs btn-outline" id="btn-edit-loippi"><i class="fa fa-paste"></i> 修改</a>

                            <a class="btn btn-outline btn-danger btn-xs btn-delete-loippi-group"><i
                                    class="fa fa-trash"></i> 删除</a>
                        </div>
                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>

                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
                                    <td>序号</td>
                                    <td>作业表</td>
                                    <td>课程</td>
                                    <td>题目</td>
                                    <td>类型</td>
                                    <td>时长</td>
                                    <td>学院</td>
                                    <td>学校</td>
                                    <td>公开级别</td>
                                    <td>状态</td>
                                    <td><a href="WorkingContent.jhtml?updater_flag=[#if updater_flag == 'asc' || updater_flag == null]desc[#elseif updater_flag == 'desc']asc[/#if]" style="color:#666">更新者<span class="[#if updater_flag == 'asc' || updater_flag == null]caret[#elseif updater_flag == 'desc']caret-up[/#if]" style="margin-left:5px;"></span></a></td>
                                    <td>更新时间</td>

                                </tr>
                                </thead>
                                <tbody>
                                [#list page.content as table]
	                                	<tr>
		                                    <td>
		                                        <input type="checkbox" class="i-checks" name="ids" value="${table.id}" id="${table.id}" >
		                                    </td>
											<td>${table_index + 1}</td>
											<td><a href="edit.jhtml?id=${table.id}" style="color:#23c6c8">${table.tableName}</a></td>
											<td>${table.course.courseName}</td>
											<td style="text-align:center"><a class="btn btn-outline btn-success" href="${base}/admin/schemeContentTable/schemeContent.jhtml?filter_vid=${table.id}">管理</a></td>
											<td>
												[#if table.tableType==0]
												作业题
												[/#if]
												[#if table.tableType==1]
												考试题
												[/#if]
											</td>
											[#if table.totalTime!=0]
											<td>${table.totalTime}</td>
											[#else]
											<td>/</td>
											[/#if]
											<td>${table.course.college.collegeName}</td>
											<td>${table.course.college.school.schoolName}</td>
											<td>
												[#if table.tableLevel==0]
												完全保密
												[/#if]
												[#if table.tableLevel==1]
												本校公开
												[/#if]
												[#if table.tableLevel==2]
												完全公开
												[/#if]

											</td>
											
											<td>
												[#if table.tableStatus==0]
												停用
												[/#if]
												[#if table.tableStatus==1]
												启用
												[/#if]
												
											</td>
											<td>${table.tableUpdater}</td>
											<td>${table.tableUpdateTime?string('yyyy-MM-dd HH:mm:SS')}</td>
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
                <div id="recordWorkId" class="hidden">
                  [#list recordWorkId as WorkId]
                      <span>${WorkId}</span>
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

        //编辑用户
        $("#btn-edit-loippi").click(function(){
            var $checkeds = jQuery($("input[name='ids']:checked"));
            if($checkeds.length > 1){
                art.warn("每次只能选择一条记录进行修改");
                return;
            }else if($checkeds.length == 0){
                art.warn("请选择一条记录进行修改");
                return;
            }
            location.href = "edit.jhtml?id=" + $($checkeds[0]).val();
        });
        //     // 复选框
        // $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});
        // // 选择所有，复选框改变
        // $("input[name='checkAll']").on('ifChanged', function () {
        //     $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
        //     if ($(this).is(":checked")) {
        //         $.ajax({
        //           type:"post",
        //           url:"recordWork.jhtml",
        //           data:{
        //               flag:2
        //           }
        //         })
        //     } else {
        //       $.ajax({
        //         type:"post",
        //         url:"recordWork.jhtml",
        //         data:{
        //             flag:3
        //         }
        //       })
        //     }
        // });

        // //选择单个,点击事件
        // $("input[name='ids']").on('ifClicked', function () {
        //     //选中状态
        //     if ($(this).is(":checked") == false) {
        //         $.ajax({
        //           type:"post",
        //           url:"recordWork.jhtml",
        //           data:{
        //               workId:$(this).val(),
        //               flag:1
        //           }

        //         })
        //     } else {
        //       //取消选中
        //       $.ajax({
        //         type:"post",
        //         url:"recordWork.jhtml",
        //         data:{
        //             workId:$(this).val(),
        //             flag:0
        //         }
        //       })
        //     }
        // });

        // //判断当前的复选框是否被选中，是则为选中状态
        // var sp = $("#recordAdminId").children()
        // for(var i = 0 ; i < $("input[name='ids']").length ; i++){
        //  for(var j = 0 ; j < sp.length;j++){
        //       if($("input[name='ids']")[i].value == sp[j].innerHTML){
        //           $("#"+sp[j].innerHTML).iCheck('check')

        //       }
        //   }
        // }

      });

</script>
</body>

</html>
