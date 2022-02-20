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
                <form id="listForm" action="${base}/admin/managerContent.jhtml">
                    <div class="ibox-content">
                        <div class="row">
                        	<div class="col-sm-3 m-b-xs">
                                <input type="text" placeholder="请输入管理员所属学校" name="filter_schoolName" value="${(params.schoolName)!''}" class="input-sm form-control" autocomplete="off">
                            </div>
                            <div class="col-sm-3 m-b-xs">
                              <input type="text" placeholder="请输入管理员用户名" name="filter_username" value="${(params.username)!''}" class="input-sm form-control">
                            </div>
                     		<div class="col-sm-3 m-b-xs"><input type="text" placeholder="请输入管理员姓名" name="filter_name" value="${(params.name)!''}" class="input-sm form-control">
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
                           <a class="btn btn-info btn-xs btn-outline" id="btn-reset-loippi-group">初始化密码</a>

                        </div>

                        <div class="dataTables_wrapper form-inline">
                            <table id="table" class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>

                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
                                    <td>序号</td>
                                    <td>用户名</td>
                                    <td>姓名</td>
									<td>学校</td>
									<td>邮箱</td>
									<td>更新者</td>
									<td>更新时间</td>
                                </tr>
                                </thead>
                                <tbody>
                                [#list page.content as schooladmin]
                                	
		                                <tr>
		                                    <td>
		                                        <input type="checkbox" class="i-checks" name="ids" value="${schooladmin.id}" id="${schooladmin.id}">
		                                    </td>
											<td>${schooladmin_index + 1}</td>
											<td><a href="edit.jhtml?id=${schooladmin.id}" style="color:#23c6c8">${schooladmin.username}</a></td>
											<td>${schooladmin.name}</td>
											<td>${schooladmin.school.schoolName}</td>
											<td>${schooladmin.email}</td>
											<td>${schooladmin.lastUpdatedBy}</td>
											<td>${schooladmin.lastUpdatedDate?string('yyyy-MM-dd HH:mm:SS')}</td>
				                         </tr>
				                     
		                           [/#list]
		                                </tbody>
			                            </table>
                                  [@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
                                  [#include "/admin/include/pagination.ftl"]
                                  [/@pagination]
                                  <input class="hidden" name="recordflag" value="0">

                </form>
                <div id="recordAdminId" class="hidden">
                  [#list recordAdminId as adminId]
                      <span>${adminId}</span>
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

        //重置密码
        $("#btn-reset-loippi-group").click(function(){
            // var $checkeds = jQuery($("input[name='ids']:checked"));
            // var ids = []
            // if($checkeds.length > 0){
            //     for(var i = 0; i<$checkeds.length;i++){
            //         ids.push($($checkeds[i]).val())
            //     }
            //     location.href = "resetPassword.jhtml?ids=" + ids;
            // }else{
            //     art.warn("请至少选择一条记录进行重置密码");
            // }
            if(getCookie("selectArr")){
                var resetIds = getCookie("selectArr").split(",")
                clearCookie("selectArr")
                location.href = "resetPassword.jhtml?ids=" + resetIds
            }else{
                art.warn("请至少选择一条记录进行重置密码")
            }
        });


        // // 复选框
        // $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});
        // // 选择所有，复选框改变
        // $("input[name='checkAll']").on('ifChanged', function () {
        //     $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
        //     if ($(this).is(":checked")) {
        //         $.ajax({
        //           type:"post",
        //           url:"recordAdmin.jhtml",
        //           data:{
        //               flag:2
        //           }
        //         })
        //     } else {
        //       $.ajax({
        //         type:"post",
        //         url:"recordAdmin.jhtml",
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
        //           url:"recordAdmin.jhtml",
        //           data:{
        //               adminID:$(this).val(),
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
        //         url:"recordAdmin.jhtml",
        //         data:{
        //             adminID:$(this).val(),
        //             flag:0
        //         },
        //         success:function (data) {
        //             console.log(data);
        //         }
        //       })
        //     }
        // });

        // //判断当前的复选框是否被选中，是则为选中状态
        // var sp = $("#recordAdminId").children()
        // for(var i = 0 ; i < $("input[name='ids']").length ; i++){
        //  for(var j = 0 ; j < sp.length;j++){
        //       if($("input[name='ids']")[i].value == sp[j].innerHTML){
        //           console.log($("#"+sp[j].innerHTML))
        //           $("#"+sp[j].innerHTML).iCheck('check')

        //       }
        //   }
        // }


          
      });
</script>
</body>

</html>
