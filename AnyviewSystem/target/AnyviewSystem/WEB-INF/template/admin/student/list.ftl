
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
                <div class="ibox-title">
                    [#if systemUser.roleId=0]
                     <div class="ibox-tools">
                        <!-- <button class="btn btn-white" type="button" onclick="back();"
                                style="margin-top: -8px;">返回
                        </button> -->
                        <a class="btn btn-white" href="${base}/admin/course_arrange/list.jhtml" style="margin-top: -8px;">返回</a>
                     </div>
                     [/#if]

                    <h5>学生管理</h5>
                </div>
                <form id="listForm" action="list.jhtml" method="get">
                    <div class="ibox-content">
                   
                    [#if systemUser.roleId == -1 || systemUser.roleId == 1]
                        
                            <div class="row">
                                [#if systemUser.roleId == -1]
                                    <div class="col-sm-3 m-b-xs">
                                        <input type="text" placeholder="请输入学生学校" name="filter_schoolName" value="${(params.schoolName)!''}" class="input-sm form-control">
                                    </div>
                                [/#if]
                                <div class="col-sm-3 m-b-xs">
                                    <input type="text" placeholder="请输入学生学院" name="filter_collegeName" value="${(params.collegeName)!''}" class="input-sm form-control">
                                </div>
                                <div class="col-sm-3 m-b-xs">
                                    <input type="text" placeholder="请输入学生专业" name="filter_majorName" value="${(params.majorName)!''}" class="input-sm form-control">
                                </div>
                                <div class="col-sm-3 m-b-xs">
                                    <input type="text" placeholder="请输入学生班级" name="filter_cName" value="${(params.cName)!''}" class="input-sm form-control">
                                </div>
                                <div class="col-sm-3 m-b-xs">
                                    <input type="text" placeholder="请输入学生学号" name="filter_username" value="${(params.username)!''}"
                                         class="input-sm form-control">
                                </div>
                                <div class="col-sm-3 m-b-xs">
                                    <input type="text" placeholder="请输入学生姓名" name="filter_name" value="${(params.name)!''}" class="input-sm form-control">
                                </div>
                                <div class="col-sm-1">
                                        <div class="input-group">
                                            <button type="submit" class="btn btn-sm btn-danger" onclick="changePageNum()"> 查询</button>
                                        </div>
                                </div>
                            </div>
                    <!-- <div class="row">
                      <div class="col-sm-1">
                              <div class="input-group">
                                  <button type="submit" class="btn btn-sm btn-danger"> 查询</button> </span>
                              </div>
                          </div>
                    </div> -->
                    [/#if]
                    [#if systemUser.roleId=0]
               
                        <div class="row">
                    		<div class="col-sm-3 m-b-xs">
                                <input type="text" placeholder="请输入学生学号" name="filter_user_name" value="${(params.username)!''}"
                                       class="input-sm form-control">
                            </div>
                            <div class="col-sm-3 m-b-xs">
                                <input type="text" placeholder="请输入学生姓名" name="filter_name" value="${(params.name)!''}" class="input-sm form-control">
                            </div>
						<div class="col-sm-1">
                                <div class="input-group">
                                    <button type="submit" class="btn btn-sm btn-danger" onclick="changePageNum()"> 查询</button>
                                </div>
                            </div>
                       </div>
                    [/#if]
                        <div>

                            <a class="btn btn-outline btn-success btn-xs" id="btn-add-loippi" href="add.jhtml"><i
                                    class="fa fa-plus"></i> 添加</a>

                            <a class="btn btn-outline btn-info btn-xs" id="btn-edit-loippi"><i class="fa fa-paste"></i> 修改</a>

                           	<a class="btn btn-outline btn-danger btn-xs btn-delete-loippi-group"><i
                                    class="fa fa-trash"></i> 删除</a>

                            <a class="btn btn-outline btn-success btn-xs" id="btn-initialize-loippi">初始化密码</a>

                            <div class="ibox-tools">
                            	<a class="btn btn-outline btn-info btn-xs" id="output">下载模板</a>

                                <a class="btn btn-outline btn-danger btn-xs" href="importStudent.jhtml"> 批量导入</a>
                    		</div>

                        </div>

                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
                                    <th>序号</th>
                                    <th><a href="list.jhtml?usernameflag=[#if usernameflag == 'asc' || usernameflag == null]desc[#elseif usernameflag == 'desc']asc[/#if]" style="color:#666">学号<em class="fs-up"><i class="[#if usernameflag == 'desc' || usernameflag == null]arrow-up[#elseif usernameflag == 'asc']selected-up[/#if]"></i><i class="[#if usernameflag == 'asc' || usernameflag == null]arrow-down[#elseif usernameflag == 'desc']selected-down[/#if]"></i></em></a></th>
                                    <th>姓名</th>
                                    <th>性别</th>
                                    <th>学校</th>
                                    <th>学院</th>
                                    <th>专业</th>
                                    <th>班级</th>
                                    <th>状态</th>
                                    <th>邮箱</th>
                                    [#if systemUser.roleId=0]
                                    <th>登录时间</th>
                                    [/#if]
                                    [#if systemUser.roleId=0]
                                    <th>登陆时IP地址:端口</th>
                                    [/#if]
                                    
                                    <!-- <th>更新者</th> -->
                                    
                                    <th>是否在线</th>
                                </tr>
                                </thead>

                               <tbody>
                                [#list page.content as student]
                                <tr>
                                    <td>
                                        <input type="checkbox" class="i-checks" name="ids" value="${student.id}" id="${student.id}">
                                    </td>
                                    <td>${student_index + 1}</td>
                                    <td><a href="edit.jhtml?id=${student.id}" style="color:#23c6c8">${student.username}</a></td>
                                    <td>${student.name}</td>
                                    <td>${student.sex}</td>
                                    <td>${student.classList[0].classSystem.major.college.school.schoolName}</td>
                                    <td class="collegename">
                                    [#list student.classList as class]
                                        [#if (class_index+1) < (student.classList?size)]
                                          <span>${class.classSystem.major.college.collegeName},</span>
                                        [#else]
                                          <span>${class.classSystem.major.college.collegeName}</span>
                                        [/#if]
                                    [/#list]
                                    </td>
                                    <td class="majorName">
                                    [#list student.classList as class]
                                    [#if (class_index+1) < (student.classList?size)]
                                    <span>${class.classSystem.major.majorName},</span>
                                    [#else]
                                    <span>${class.classSystem.major.majorName}</span>
                                    [/#if]
                                    [/#list]
                                    </td>
                                    <td>
                                    [#list student.classList as class]
                                    [#if (class_index+1) < (student.classList?size)]
                                    <span>${class.classSystem.className},</span>
                                    [#else]
                                    <span>${class.classSystem.className}</span>
                                    [/#if]
                                    [/#list]
                                    </td>
                                    <td>${student.state}</td>
                                    <td>${student.email}</td>
                                    <!--
                                    [#if student.roleId==3]
                                    <td>${student.saccumTime}</td>
                                    [#else ]
                                        <td></td>
                                    [/#if]
                                    -->
                                    <td>
                                    	[#if student.logTime??]
	                                    	${student.logTime?string('yyyy-MM-dd HH:mm:SS')}
	                                    [#else]
	                                    	无数据
	                                    [/#if]
                                    </td>
                                    [#if student.loginStatus==1]
                                            <td>${student.logIP}:${student.logPort}</td>
                                    [#else]
                                            <td>尚未登陆</td>
                                    [/#if]
                                    <!--
                                    <td>${student.lastUpdatedBy}</td>
                                    -->
                                    
                                    <!-- <td>[#if student.lastUpdatedDate??]${student.lastUpdatedDate?string('yyyy-MM-dd HH:mm:SS')}[/#if]</td> -->
                                    
                                    <td>
	                                    [#if student.loginStatus?? && student.loginStatus==1]
	                                    	在线
	                                    [#else]
	                                    	不在线
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
                    <input class="hidden" name="recordflag" value="0">
                </form>
                <input id="num" class="hidden" value="${number!""}">
                <input id="failNumber" class="hidden" value="${failNumber!""}">
                <div id="recordStudentId" class="hidden">
                  [#list recordStudentId as studentId]
                      <span>${studentId}</span>
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
            // if(getCookie("selectArr")){
            //     //获取Cookie中用户已选中的选项数组，发往服务器端进行编辑
            //     var resetIds = getCookie("selectArr").split(",")
            //     if(resetIds.length == 1){
            //         clearCookie("selectArr")
            //         location.href = "edit.jhtml?id=" + resetIds
            //     }else{
            //         art.warn("每次只能选择一条记录进行修改");
            //     }
            // }else{ 
            //     art.warn("请至少选择一条记录进行重置密码")
            // }
        });


        //初始化密码
        $("#btn-initialize-loippi").click(function(){

            if(getCookie("selectArr")){

                var resetIds = getCookie("selectArr").split(",")
                clearCookie("selectArr")
                // console.log(resetIds)
                location.href = "initialize.jhtml?ids=" + resetIds
            }else{
                art.warn("请至少选择一条记录进行重置密码")
            }
        });

        //去掉重复的学院名称
        var collegeNameArr = []
        for(var i= 0;i<$(".collegename").length;i++){
          var sp  = $(".collegename")[i].children
          if(sp.length > 1){
            for(var a = 0;a < sp.length;a++){
                var str = sp[a].innerHTML
                if(str.charAt(str.length-1) == ','){
                  str = str.split(',')[0]
                }
                if (collegeNameArr.indexOf(str) == -1 && str!="") {
                    collegeNameArr.push(str)
                }
            }
            $(".collegename")[i].innerHTML = collegeNameArr.join(",")
          }
        }
        //去掉重复的专业
        // var majorArr = []
        // for(var i= 0;i<$(".majorName").length;i++){
        //   var sp  = $(".majorName")[i].children
        //   if(sp.length > 1){
        //     for(var a = 0;a < sp.length;a++){
        //         var str = sp[a].innerHTML
        //         if(str.charAt(str.length-1) == ','){
        //           str = str.split(',')[0]
        //         }
        //         if (majorArr.indexOf(str) == -1 && str!="") {
        //             majorArr.push(str)
        //         }
        //     }
        //     $(".majorName")[i].innerHTML = majorArr.join(",")
        //   }
        // }
        //获取批量导入的记录数
        if($("#num").val() != "" && $("#num").val() != "-1"){
            art.success("成功导入"+$("#num").val()+"条数据!")
        }else if($("#num").val()=="-1"){
           art.success("成功导入0条数据!")
        }

        //导入失败时提示
        // if ($("#failNumber").val() != "-1" || $("#failNumber").val() != "") {
        //     console.log("failNumber----->")
        //     console.log($("#failNumber").val())
        //     art.warn("导入文件成功"+$("#num").val()+"条，失败"+$("#failNumber").val()+"条(班级为空或不存在)")
        // }else if($("#failNumber").val() == "-1"){
        //     console.log(33333333333333333333333)
        //     console.log($("#failNumber").val())
        //     art.warn("导入文件失败，导入数据"+$("#num").val()+"条")
        // }

        // // 复选框
        // $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red'});
        // // 选择所有，复选框改变
        // $("input[name='checkAll']").on('ifChanged', function () {
        //     $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
        //     if ($(this).is(":checked")) {
        //         $.ajax({
        //           type:"post",
        //           url:"recordStudent.jhtml",
        //           data:{
        //               flag:2
        //           }
        //         })
        //     } else {
        //       $.ajax({
        //         type:"post",
        //         url:"recordStudent.jhtml",
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
        //           url:"recordStudent.jhtml",
        //           data:{
        //               studentID:$(this).val(),
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
        //         url:"recordStudent.jhtml",
        //         data:{
        //             studentID:$(this).val(),
        //             flag:0
        //         },
        //         success:function (data) {
        //             console.log(data);
        //         }
        //       })
        //     }
        // });

        // //判断当前的复选框是否被选中，是则为选中状态
        // var sp = $("#recordStudentId").children()
        // for(var i = 0 ; i < $("input[name='ids']").length ; i++){
        //  for(var j = 0 ; j < sp.length;j++){
        //       if($("input[name='ids']")[i].value == sp[j].innerHTML){
        //           $("#"+sp[j].innerHTML).iCheck('check')

        //       }
        //   }
        // }


        

    });

    //模板导出
    $("#output").click(function(){
      var aLink = document.getElementById('output')
      aLink.href="${base}/resources/studentTemplet/templet.xls"
      aLink.download = "模板"
      // art.success("模板导出成功")
    })
    function back() {
 		 document.location.href = "${base}/admin/course_arrange/list.jhtml"
 	}
</script>
</body>

</html>
