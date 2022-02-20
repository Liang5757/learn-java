
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
                <form id="listForm" action="list.jhtml" method="get">
                    <div class="ibox-content">
                    
                        <div class="row">
                            [#if systemUser.roleId == -1]
                        		<div class="col-sm-3 m-b-xs">
                                    <input type="text" placeholder="请输入学校名称" name="filter_schoolName" value="${(params.schoolName)!''}" class="input-sm form-control">
                                </div>
                            [/#if]
                            [#if systemUser.roleId == -1 || systemUser.roleId == 1]
                                <div class="col-sm-3 m-b-xs">
                                    <input type="text" placeholder="请输入学院名称" name="filter_collegeName" value="${(params.collegeName)!''}" class="input-sm form-control">
                                </div>
                            [/#if]
                                <div class="col-sm-3 m-b-xs">
                                    <input type="text" placeholder="请输入专业名称" name="filter_majorName" value="${(params.majorName)!''}" class="input-sm form-control">
                                </div>
                                <div class="col-sm-3 m-b-xs">
                                    <input type="text" placeholder="请输入班级名称" name="filter_cName" value="${(params.cName)!''}" class="input-sm form-control">
                                </div>
                                <div class="col-sm-3 m-b-xs">
                                    <input type="text" placeholder="请输入课程名称" name="filter_course_name" value="${(params.course_name)!''}" class="input-sm form-control">
                                </div>
                                <div class="col-sm-3 m-b-xs">
                                    <input type="text" placeholder="请输入教师名称" name="filter_tName" value="${(params.tName)!''}" class="input-sm form-control">
                                </div>
                                <div class="col-sm-1">
                                    <div class="input-group">
                                        <button type="submit" class="btn btn-sm btn-danger" onclick="changePageNum()">查询</button>
                                    </div>
                                </div>
                        </div>
                        <div>
                        	[#if systemUser.roleId == -1 || systemUser.roleId == 1]

                            <a class="btn btn-outline btn-success btn-xs" id="btn-add-loippi" href="add.jhtml"><i
                                    class="fa fa-plus"></i> 添加</a>

                            <a class="btn btn-outline btn-info btn-xs" id="btn-edit-loippi"><i class="fa fa-paste"></i> 修改</a>

                           	<a class="btn btn-outline btn-danger btn-xs btn-delete-loippi-group"><i
                                    class="fa fa-trash"></i> 删除</a>
                        	[/#if]
                        </div>

                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>
									[#if systemUser.roleId == -1 || systemUser.roleId == 1]
                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
                                    [/#if]
                                    <th>序号</th>
                                    <th>课程名称</th>
                                    <th>班级名称</th>
                                    <th>学校</th>
                                    <th>学院</th>
                                    <th>专业</th>
                                    <th>教师姓名</th>
                                    <th>作业表</th>
                                    <th>更新者</th>
                                    <th>更新时间</th>
                                </tr>
                                </thead>

                               <tbody>
                                [#list page.content as course_arrange]
                                <tr>
                                [#if systemUser.roleId == -1 || systemUser.roleId == 1]
                                    <td>
                                        <input type="checkbox" class="i-checks" name="ids" value="${course_arrange.id}" id="${course_arrange.id}">
                                    </td>
                                 [/#if]
                                    <td>${course_arrange_index + 1}</td>
                                    <td>[#if systemUser.roleId == 0]${course_arrange.course.courseName}[#else]<a href="edit.jhtml?id=${course_arrange.id}" style="color:#23c6c8">${course_arrange.course.courseName}</a>[/#if]</td>
                                    <td>
	                                    [#if systemUser.roleId != 0]
	                                    	${course_arrange.classSystem.className}
	                                    [#else]
	                                    	<a href="${base}/admin/student/list.jhtml?filter_cName=${course_arrange.classSystem.id}" style="color:#3390cb">
	                                     		${course_arrange.classSystem.className}[/#if]
                                    </td>
                                    <td>${course_arrange.classSystem.major.college.school.schoolName}</td>
                                    <td>${course_arrange.classSystem.major.college.collegeName}</td>
                                    <td>${course_arrange.classSystem.major.majorName}</td>
                                    <td>${course_arrange.teacher.name}</td>
                                    <td>
                                        [#list course_arrange.courseArrangeAndWorkingTable as cAndw]
                                            [#if (cAndw_index+1) < (course_arrange.courseArrangeAndWorkingTable?size)]
                                                <span>${cAndw.workingTable.tableName}、</span>
                                            [#else]
                                                <span>${cAndw.workingTable.tableName}</span>
                                            [/#if]
                                    	[/#list]
                                    </td>
                                    <td>${course_arrange.update_person}</td>
                                    <td>[#if course_arrange.update_date??]${course_arrange.update_date?string('yyyy-MM-dd HH:mm:SS')}[/#if]</td>
                                </tr>
                                [/#list]
                                </tbody>
                            </table>
                            [@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
                            [#include "/admin/include/pagination.ftl"]
                            [/@pagination]
                        </div>
                    </div>
                    <input class="hidden" name="recordflag" value="0">
                </form>
                <div id="recordCourseArrangeId" class="hidden">
                  [#list recordCourseArrangeId as courseArrangeId]
                      <span>${courseArrangeId}</span>
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

        // // 复选框
        // $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red'});
        // // 选择所有，复选框改变
        // $("input[name='checkAll']").on('ifChanged', function () {
        //     $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
        //     if ($(this).is(":checked")) {
        //         $.ajax({
        //           type:"post",
        //           url:"recordCourseArrange.jhtml",
        //           data:{
        //               flag:2
        //           }
        //         })
        //     } else {
        //       $.ajax({
        //         type:"post",
        //         url:"recordCourseArrange.jhtml",
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
        //           url:"recordCourseArrange.jhtml",
        //           data:{
        //               courseArrangeID:$(this).val(),
        //               flag:1
        //           }

        //         })
        //     } else {
        //       //取消选中
        //       $.ajax({
        //         type:"post",
        //         url:"recordCourseArrange.jhtml",
        //         data:{
        //             courseArrangeID:$(this).val(),
        //             flag:0
        //         }
        //       })
        //     }
        // });

        // //判断当前的复选框是否被选中，是则为选中状态
        // var sp = $("#recordCourseArrangeId").children()
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


