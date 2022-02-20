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

      .caret-up{
        display: inline-block;
        width: 0;
        height: 0;
        margin-left: 2px;
        vertical-align: middle;
        border-bottom: 4px dashed;
        border-right: 4px solid transparent;
        border-left: 4px solid transparent;
      }

    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <form id="listForm" action="${base}/admin/school/managerSchoolContent.jhtml">
                    <div class="ibox-content">
                        <div class="row">
                        <div class="col-sm-3 m-b-xs"><input type="text" placeholder="请输入学校名称" name="filter_schoolName" value="${(params.schoolName)!''}" class="input-sm form-control">

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
                                   <td><a href="managerSchoolContent.jhtml?filter_schoolNameSort=[#if schoolNameSort == 'asc' || schoolNameSort == null]desc[#elseif schoolNameSort == 'desc']asc[/#if]&filter_schoolName=${(params.schoolName)!''}" style="color:#666">学校<span id="sortSchool" class="[#if schoolNameSort == 'desc']caret[#elseif schoolNameSort == 'asc' || schoolNameSort == null]caret-up[/#if]" style="margin-left:5px;"></span></a></td>
                                
                                   <td>更新者</td>
                                   <td>更新时间</td>
                                </tr>
                                </thead>
                                <tbody>
                                [#list page.content as managerSchool]
                                <tr>
                                    <td>
                                        <input type="checkbox" class="i-checks" name="ids" value="${managerSchool.id}" id="${managerSchool.id}">
                                    </td>
										<td>${managerSchool_index + 1}</td>
										<td><a href="edit.jhtml?id=${managerSchool.id}" style="color:#23c6c8">${managerSchool.schoolName}</a></td>
										<td>${managerSchool.schoolUpdater}</td>
										<td>${managerSchool.schoolUpdateTime?string('yyyy-MM-dd HH:mm:SS')}</td>

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
                <div id="recordSchoolId" class="hidden">
                  [#list recordSchoolId as schoolId]
                      <span>${schoolId}</span>
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
        // $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});
        // // 选择所有，复选框改变
        // $("input[name='checkAll']").on('ifChanged', function () {
        //     $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
        //     if ($(this).is(":checked")) {
        //         $.ajax({
        //           type:"post",
        //           url:"recordSchool.jhtml",
        //           data:{
        //               flag:2
        //           }
        //         })
        //     } else {
        //       $.ajax({
        //         type:"post",
        //         url:"recordSchool.jhtml",
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
        //           url:"recordSchool.jhtml",
        //           data:{
        //               schoolID:$(this).val(),
        //               flag:1
        //           },
        //           success:function (data) {
        //               console.log(data)
        //           }

        //         })
        //     } else {
        //       //取消选中
        //       $.ajax({
        //         type:"post",
        //         url:"recordSchool.jhtml",
        //         data:{
        //             schoolID:$(this).val(),
        //             flag:0
        //         },
        //         success:function (data) {
        //             console.log(data);
        //         }
        //       })
        //     }
        // });

        // //判断当前的复选框是否被选中，是则为选中状态
        // var sp = $("#recordSchoolId").children()
        // for(var i = 0 ; i < $("input[name='ids']").length ; i++){
        //  for(var j = 0 ; j < sp.length;j++){
        //       if($("input[name='ids']")[i].value == sp[j].innerHTML){
        //           // console.log($("#"+sp[j].innerHTML))
        //           $("#"+sp[j].innerHTML).iCheck('check')

        //       }
        //   }
        // }

        // //页面加载时 判断学校是正序或者倒序
        // if(storage['tt_flag'] == "0" || storage['tt_flag'] == undefined){
        //   $("#sortSchool").removeClass('caret-up')
        //   $("#sortSchool").addClass('caret')
        // }else if (storage['tt_flag'] == "1") {
        //   $("#sortSchool").removeClass('caret')
        //   $("#sortSchool").addClass('caret-up')
        // }

    });

    //点击学校名称进行排序
    // function sortSchoolName() {
    //     if(storage['tt_flag'] == "0" || storage['tt_flag'] == undefined){
    //         window.location.href="${base}/admin/school/managerSchoolContent.jhtml?schoolNameSort=tt_flag"
    //     }else if(storage['tt_flag'] == "1"){
    //         window.location.href="${base}/admin/school/managerSchoolContent.jhtml?schoolNameSort=tt_flag"
    //     }
    // }
</script>
</body>

</html>
