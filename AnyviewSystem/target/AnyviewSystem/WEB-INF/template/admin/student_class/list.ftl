
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
                            [#if systemUser.roleId == -1]
                        	   <div class="col-sm-3 m-b-xs">
                                    <input type="text" placeholder="请输入班级所属学校" name="filter_schoolName" value="${(params.schoolName)!''}" class="input-sm form-control">
                                </div>
                            [/#if]
                            <div class="col-sm-3 m-b-xs">
                                <input type="text" placeholder="请输入班级所属学院" name="filter_collegeName" value="${(params.collegeName)!''}" class="input-sm form-control">
                            </div>
                            <div class="col-sm-3 m-b-xs">
                                <input type="text" placeholder="请输入班级所属专业" name="filter_majorName" value="${(params.majorName)!''}" class="input-sm form-control">
                            </div>
                            <div class="col-sm-3 m-b-xs">
                                <input type="text" placeholder="请输入班级名称" name="filter_className" value="${(params.className)!''}" class="input-sm form-control">
                            </div>
                            <div class="col-sm-1">
                                <div class="input-group">
                                    <button type="submit" class="btn btn-sm btn-danger" onclick="changePageNum()"> 查询</button>
                                </div>
                            </div>
                        </div>
                        <div>
                            <a class="btn btn-outline btn-success btn-xs" id="btn-add-loippi" href="add.jhtml"><i
                                    class="fa fa-plus"></i> 添加</a>

                            <a class="btn btn-outline btn-info btn-xs" id="btn-edit-loippi"><i class="fa fa-paste"></i> 修改</a>

                           	<a class="btn btn-outline btn-danger btn-xs btn-delete-loippi-group"><i
                                    class="fa fa-trash"></i> 删除</a>
                        </div>

                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
                                    <th>序号</th>
                                    <th><a href="list.jhtml?classNameflag=[#if classNameflag == 'asc' || classNameflag == null]desc[#elseif classNameflag == 'desc']asc[/#if]" style="color:#666">班级<em class="fs-up"><i class="[#if classNameflag == 'desc' || classNameflag == null]arrow-up[#elseif classNameflag == 'asc']selected-up[/#if]"></i><i class="[#if classNameflag == 'asc' || classNameflag == null]arrow-down[#elseif classNameflag == 'desc']selected-down[/#if]"></i></em></a></th>
                                    <th>学校</th>
                                    <th>学院</th>
                                    <th>专业</th>
                                    <th>年届</th>
                                    <th>更新者</th>
                                    <th>更新时间</th>
                                </tr>
                                </thead>

                               <tbody>
                                [#list page.content as student_class]
                                <tr>
                                    <td>
                                        <input type="checkbox" class="i-checks" name="ids" value="${student_class.id}" id="${student_class.id}">
                                    </td>
                                    <td>${student_class_index + 1}</td>
                                    <td><a href="edit.jhtml?id=${student_class.id}" style="color:#23c6c8">${student_class.className}</a></td>
                                    <td>${student_class.major.college.school.schoolName}</td>
                                    <td>${student_class.major.college.collegeName}</td>
                                    <td>${student_class.major.majorName}</td>
                                    <td>${student_class.year}</td>
                                    <td>${student_class.update_person}</td>
                                    <td>[#if student_class.update_date??]${student_class.update_date?string('yyyy-MM-dd HH:mm:SS')}[/#if]</td>
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
                <div id="recordClassId" class="hidden">
                  [#list recordClassId as classId]
                      <span>${classId}</span>
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

      /*  // 复选框
        $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red'});
        // 选择所有，复选框改变
        $("input[name='checkAll']").on('ifChanged', function () {
            $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
            if ($(this).is(":checked")) {
                $.ajax({
                  type:"post",
                  url:"recordClass.jhtml",
                  data:{
                      flag:2
                  }
                })
            } else {
              $.ajax({
                type:"post",
                url:"recordClass.jhtml",
                data:{
                    flag:3
                }
              })
            }
        });

        //选择单个,点击事件
        $("input[name='ids']").on('ifClicked', function () {
            //选中状态
            if ($(this).is(":checked") == false) {
                $.ajax({
                  type:"post",
                  url:"recordClass.jhtml",
                  data:{
                      classID:$(this).val(),
                      flag:1
                  },
                  success:function (data) {
                      console.log(data)
                  }

                })
            } else {
              //取消选中
              $.ajax({
                type:"post",
                url:"recordClass.jhtml",
                data:{
                    classID:$(this).val(),
                    flag:0
                },
                success:function (data) {
                    console.log(data);
                }
              })
            }
        });

        //判断当前的复选框是否被选中，是则为选中状态
        var sp = $("#recordClassId").children()
        for(var i = 0 ; i < $("input[name='ids']").length ; i++){
         for(var j = 0 ; j < sp.length;j++){
              if($("input[name='ids']")[i].value == sp[j].innerHTML){
                  // console.log($("#"+sp[j].innerHTML))
                  $("#"+sp[j].innerHTML).iCheck('check')

              }
          }
        }*/

        

    });
</script>
</body>

</html>
