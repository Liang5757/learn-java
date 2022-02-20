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
                       <button class="btn btn-white" type="button"onclick="window.history.back();" style="margin-top: -8px;">返回</button>
                            <!--  <a class="btn btn-white" href="${base}/admin/course_arrange/list.jhtml" style="margin-top: -8px;">返回</a> -->
                    </div>

                    <h5>添加课程编排</h5>
                </div>
                <div class="ibox-content">
                    <form id="saveCourseArrangeForm" class="form-horizontal" action="save.jhtml" method="post">
                        <div class="form-group" >
                        	<div class="row" >
                                <div class="col-sm-6" style="display:none">
                                    <label class="col-sm-3 control-label">课程编排ID<span
                                            class="required"> * </span></label>

                                    <div class="col-sm-9">
                                        <input type="text" name="id" id="courseArrangeId" class="form-control" maxlength="200"
                                               readonly="readonly"/>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">学校<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                     [#if systemUser.roleId=-1]
                                      <!-- <input name="schoolName" id="schoolName" placeholder="请输入学校名称" class="form-control">
                                      <input name="schoolId" id="schoolId" class="hidden"> -->
                                        <!-- <select name="schoolName" id="schoolId" onchange="selectCollege(this.value)"
                                                class="bs-select form-control">

                                        	<option hassubinfo="true" value="">请选择学校</option>
                                        	[#list schoolList as school]
	                                            <option value="${school.id}">${school.schoolName}</option>
	                                        [/#list]

                                        </select> -->
                                        <select class="form-control" name="schoolId" id="schoolName" onchange="add1(this.value);">
                                        </select>
                                         [/#if]
	                                     [#if systemUser.roleId=1]
		                                     <input type="hidden" name="schoolName" id="schoolId" value="${systemUser.school.id}">${systemUser.school.schoolName}
		                                 [/#if]
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">学院<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <select name="collegeName" id="collegeId"  onchange="selectMajor(this.value)"
                                                class="bs-select form-control">

                                         <option value="">请选择学院</option>
											[#if systemUser.roleId=1]
		                                        [#list collegeList as college]
		                                           <option value="${college.id}">${college.collegeName}</option>
		                                        [/#list]

			                                 [/#if]
                                        </select>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">专业<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <select name="majorName" id="majorId" onchange="selectGrade(this.value)"
                                                class="bs-select form-control">

                                         <option value="">请选择专业</option>

                                        </select>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">班级<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <button type="button" class="btn btn-sm btn-outline btn-success" name="bt-select-class" id="bt-select-class"> 选择
                                        </button>
                                        <input type="hidden" name="class_name" id="class_name"/>
                                        <span id="main_class"></span>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">课程<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <select name="courseName" id="courseId"
                                                class="bs-select form-control">

                                        <option value="">请选择课程</option>

                                        </select>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6 vaild">
                                    <label class="col-sm-3 control-label">教师<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <select name="teacherName" id="teacherId"
                                                class="bs-select form-control">

                                        <option value="">请选择教师</option>

                                        </select>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">作业表<span class="required"></span>:</label>

                                    <div class="col-sm-9">
                                        <button type="button" class="btn btn-sm btn-outline btn-success" name="bt-select-workingtable" id="bt-select-workingtable"> 选择
                                        </button>
                                        <input type="hidden" name="working_table" id="working_table"/>
                                        <span id="main_workingtable"></span>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <!--/row-->

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-12 col-sm-offset-2">
                                <button type="submit"  class="btn btn-danger">确定</button>
                            </div>
                        </div>
                	</div>
                </form>
            </div>
        </div>
    </div>
</div>
</div>
<!-- BEGIN WorkingTableDialog -->
<div class="modal inmodal" id="selectWorkingTable" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">选择作业表</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <div class="row">
                        <div class="dataTables_wrapper form-inline dialog-table" id="selectWorkingTableList">

                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-danger ok-set-workingtable">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- END WorkingTableDialog -->

<!-- BEGIN ClassDialog -->
<div class="modal inmodal" id="selectClass" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">选择班级</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <div class="row">
                        <div class="dataTables_wrapper form-inline dialog-table" id="selectClassList">

                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-danger ok-set-class">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- END ClassDialog -->

<!-- BEGIN Script -->
[#include "/admin/include/script.ftl"]
<!-- END Script -->
<script>

    $(document).ready(function () {
        [@flash_message /]
        $(".chosen-select").chosen({width: "100%"});
        $(".input-daterange").datepicker({keyboardNavigation: false, forceParse: false, autoclose: true});
        $(".browserButton").browser({
            callback: function (url) {
                $("#" + $(this).attr('for')).val(url);
            }
        });
        $(".input-group.date").datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
            //重新选择课程 则清空已选择的作业表数据
            $("#courseId").change(function () {
                //清空已选择的作业表ID跟名字
                $("#working_table").val("")
                $("#main_workingtable").text("")

                //清除cookie
                clearCookie("workingtable_selectArr")
            })

            //专业改变时，清空班级及其cookie
            $("#majorId").change(function () {
               clearCookie("course_class_selectArr")
               $("#class_name").val("")
               $("#main_class").text("")
            })

        	//选择作业表对话框操作--------------------------------------------------------------
            $("#bt-select-workingtable").on("click",function () {
            if($("#courseId").val()){
            var data = {};
            var index = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            //获取id
            $.get("${base}/admin/course_arrange/listDialogWorkingTable.jhtml?courseId="+$("#courseId").val(), data, function (result, status) {
                var tableCon = $("#selectWorkingTableList");
                tableCon.empty();
                tableCon.append(result);
                layer.close(index);
                $('#selectWorkingTable').modal('show');

                // // 复选框
                // $('.dialog-table .i-checks').iCheck({
                //     checkboxClass: 'icheckbox_square-red',
                //     radioClass: 'iradio_square-red',
                // });
                // // 选择所有，复选框改变
                // $("#selectWorkingTableList input[name='checkAll']").on('ifChanged', function () {
                //     $("#selectWorkingTableList input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
                //     if ($(this).is(":checked")) {
                //         $.ajax({
                //           type:"post",
                //           url:"recordWorkingTable.jhtml",
                //           data:{
                //               flag:2
                //           }
                //         })
                //     } else {
                //       $.ajax({
                //         type:"post",
                //         url:"recordWorkingTable.jhtml",
                //         data:{
                //             flag:3
                //         }
                //       })
                //     }
                // });

                // //选择单个,点击事件
                // $("#selectWorkingTableList input[name='ids']").on('ifClicked', function () {
                //     //选中状态
                //     if ($(this).is(":checked") == false) {
                //         $.ajax({
                //           type:"post",
                //           url:"recordWorkingTable.jhtml",
                //           data:{
                //               wrokingTableID:$(this).val(),
                //               flag:1
                //           }

                //         })
                //     } else {
                //       //取消选中
                //       $.ajax({
                //         type:"post",
                //         url:"recordWorkingTable.jhtml",
                //         data:{
                //             wrokingTableID:$(this).val(),
                //             flag:0
                //         }
                //       })
                //     }
                // });

                // //判断当前的复选框是否被选中，是则为选中状态
                // var sp = $("#recordWorkingTableId").children()
                // for(var i = 0 ; i < $("#selectWorkingTableList input[name='ids']").length ; i++){
                //  for(var j = 0 ; j < sp.length;j++){
                //       if($("#selectWorkingTableList input[name='ids']")[i].value == sp[j].innerHTML){
                //           $("#"+sp[j].innerHTML).iCheck('check')
                //       }
                //   }
                // }
                //  $("input[name='checkAll']").on('ifChanged', function () {
                //     $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
                //     if ($(this).is(":checked")) {
                //         setCookie("workingtable_selectAll",1)  //设置cookie标志
                //     } else {
                //         if(getCookie("workingtable_selectAll")){  //反选则删除cookie
                //             clearCookie("workingtable_selectAll")
                //             clearCookie("workingtable_selectArr")
                //         }
                //     }
                // });
                // //选择单个,点击事件
                // $("input[name='ids']").on('ifChanged', function () {
                //     var arr = []
                //     //选中状态
                //     if ($(this).is(":checked")) {
                //         if(getCookie("workingtable_selectArr")){
                //             arr = getCookie("workingtable_selectArr").split(",")
                //         }
                //         if($.inArray($(this).val(),arr) == -1){  //去重
                //             arr.push($(this).val())
                //             console.log(arr)
                //             setCookie("workingtable_selectArr",arr.join(","))
                //         }
                        
                //     } else {  //反选状态
                //         if(getCookie("workingtable_selectArr")){
                //             arr = getCookie("workingtable_selectArr").split(",")
                //             if($.inArray($(this).val(),arr) != -1){
                //                 arr.splice($.inArray($(this).val(),arr),1)  //删除元素
                //             }
                //             setCookie("workingtable_selectArr",arr.join(","))

                //             clearCookie("workingtable_selectAll")   //删除全选
                //         }
                //     }
                // });
                // //判断是否为全选 是则所有选择框为选中状态
                // if(getCookie("workingtable_selectAll")){
                //     $("input[name='checkAll']").iCheck("check")
                // }
                // //判断当前的复选框是否被选中，是则为选中状态
                // if(getCookie("workingtable_selectArr")){
                //     var sp = getCookie("workingtable_selectArr").split(",")
                //     for(var i = 0 ; i < $("input[name='ids']").length ; i++){
                //         for(var j = 0 ; j < sp.length;j++){
                //             if($("input[name='ids']")[i].value == sp[j]){
                //                 $("#"+sp[j]).iCheck('check')
                //             }
                //         }
                //     }
                // }

                 // 初始化复选框
                $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});

                // 选择所有，复选框改变
                $("#selectWorkingTableList input[name='checkAll']").on('ifClicked', function () {
                  //判断用户是否勾选了全选选项，是则将当前页面的复选框全部选中，并将其ID存入Cookie中
                  if ($(this).is(":checked")) {
                     $("#selectWorkingTableList input[name='ids']").iCheck('uncheck'); 
                  } else {
                    $("#selectWorkingTableList input[name='ids']").iCheck('check');   //用户取消全选，则将Cookie中存放的SelectArr数组清除
                  }
                });

  
                //选择单个,点击事件
                $("#selectWorkingTableList input[name='ids']").on('ifChanged', function () {
                      var arr = []
                      //选中状态
                    if ($(this).is(":checked")) {
                          //当Cookie中已经存有存放ID的数组，则在此基础上增加ID
                          if(getCookie("workingtable_selectArr")){
                            arr = getCookie("workingtable_selectArr").split(",")
                          }
                          var str = $(this).val()+":"+$(this).attr("data-name")
                          if($.inArray(str,arr) == -1){  //去重
                            arr.push(str)
                            setCookie("workingtable_selectArr",arr.join(","))
                          }
                          //用户勾选复选框时,判断当前页面所有复选框是否都已勾选中，是则将全选设为勾选状态
                          isSelectAll()
                    } else {  //反选状态
                        //查看Cookie中是否已存有用户选中的ID
                        if(getCookie("workingtable_selectArr")){
                          //取消勾选时，查看全选选项是否被选中，是则将其取消掉
                          if($("#selectWorkingTableList input[name='checkAll']").is(":checked")){
                            $("#selectWorkingTableList input[name='checkAll']").iCheck("uncheck")
                          }
                          //删除cookie中存在的此选项ID
                          arr = getCookie("workingtable_selectArr").split(",")
                          var str = $(this).val()+":"+$(this).attr("data-name")
                          if($.inArray(str,arr) != -1){   //查看数组中是否有存在此选项ID，有则删除此选项
                            arr.splice($.inArray(str,arr),1)  //删除元素
                          }
                          setCookie("workingtable_selectArr",arr.join(","))   //删除之后再重新设置Cookie
                        }   
                    }
                });

                //判断当前的复选框是否被选中，是则为选中状态
                if(getCookie("workingtable_selectArr")){
                    var sp = getCookie("workingtable_selectArr").split(",")
                    for(var i = 0 ; i < $("#selectWorkingTableList input[name='ids']").length ; i++){
                      for(var j = 0 ; j < sp.length;j++){
                        var id = sp[j].split(":")[0]
                        if($("#selectWorkingTableList input[name='ids']")[i].value == id){
                            $("#selectWorkingTableList #"+id).iCheck('check')
                        }
                      }
                    }
                    //获取cookie中的selectArr数组，遍历循环查看当前页面是否都被选中，是则将全选选项勾选中
                    isSelectAll()
                }
                }, "html");
            }else{
            	alert("请选择课程")
            }
        });

        $(".ok-set-workingtable").click(function () {
          // var $checkeds = $("#selectWorkingTableList input[name='ids']:checked");
          // var $inp = $("#selectWorkingTableList input[name='ids']");
          // var $workingTableId = $("#recordWorkingTableId").children()
          // var $workingTableName = $("#recordWorkingTableName").children()
          // var idArr = [],nameArr = []
          // var selectIds = '', selectNames = '';
          // //获取勾选的id与名称
          // if($workingTableId.length > 0 || $checkeds.length > 0){
          //   for (var i = 0; i < $workingTableId.length; i++) {
          //       if (i != 0) {
          //           selectIds += ",";
          //       }
          //       selectIds += $workingTableId[i].innerHTML;
          //   }
          //   for (var j = 0; j < $workingTableName.length; j++) {
          //       if (j != 0) {
          //           selectNames += "、";
          //       }
          //       selectNames += $workingTableName[j].innerHTML
          //   }

          //   //将id与名称字符串转换为数组,方便遍历
          //   if(selectIds!="" || selectNames!=""){
          //     idArr = selectIds.split(',')
          //     nameArr = selectNames.split('、')
          //   }

          //   //没有翻页时，手动加入id与名称
          //   for(var p = 0 ; p < $checkeds.length;p++){
          //     //$.inArray : jquery判断数组中是否包含某个元素 没有则返回-1 有则返回元素下标值
          //     if($.inArray($($checkeds[p]).val(),idArr) == -1){
          //       idArr.push($($checkeds[p]).val())
          //       nameArr.push($($checkeds[p]).attr("data-name"))
          //     }
          //   }
          //   // console.log(nameArr);
          //   //取消选择
          //   for(var q = 0 ; q < $inp.length;q++){
          //     //判断当前页面的复选框是否有存在于id数组中，且不为选中状态
          //     if($.inArray($($inp[q]).val(),idArr) != -1 && $($inp[q]).is(":checked") == false){
          //       idArr.splice($.inArray($($inp[q]).val(),idArr),1)
          //       nameArr.splice($.inArray($($inp[q]).attr("data-name"),nameArr),1)
          //     }
          //   }
          //   //将所选择的名字显示到界面上
          //   if(nameArr.length<=3){
          //     $("#main_workingtable").text(nameArr.join("、"));
          //   }else{
          //     var name = nameArr.slice(0,3)
          //     $("#main_workingtable").text(name.join('、')+"、...");
          //   }

          //   $("#working_table").val(idArr.join(","));
          //   $('#selectWorkingTable').modal('hide');
          // }else{
          //   alert("请选择一个作业表")
          // }
          if(getCookie("workingtable_selectArr")){
                var workingtable_selectArr = getCookie("workingtable_selectArr").split(","),
                    idArr = [],
                    nameArr = []
                for (var d = 0; d < workingtable_selectArr.length; d++) {
                    idArr.push(workingtable_selectArr[d].split(":")[0])
                    nameArr.push(workingtable_selectArr[d].split(":")[1])
                }
                // //将所选择的名字显示到界面上
                // for(var i = 0;i < idArr.length;i++){
                //     nameArr.push($("#selectWorkingTableList #"+idArr[i]).attr("data-name"))
                // }
                if(nameArr.length<=3){
                    $("#main_workingtable").text(nameArr.join("、"));
                }else{
                    var name = nameArr.slice(0,3)
                    $("#main_workingtable").text(name.join('、')+"、...");
                }
                $("#working_table").val(idArr.join(","));
                $('#selectWorkingTable').modal('hide');
            }else{
                alert("请选择一个作业表")
            }
        });

		//选择班级对话框操作--------------------------------------------------------------------
        $("#bt-select-class").on("click",function () {
        if($("#majorId").val()){
            var data = {};
            var index = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            //获取id
            $.get("${base}/admin/course_arrange/listDialogClass.jhtml", data, function (result, status) {
                var tableCon = $("#selectClassList");
                tableCon.empty();
                tableCon.append(result);
                layer.close(index);
                $('#selectClass').modal('show');

                // // 复选框
                // $('#selectClassList .i-checks').iCheck({
                //     checkboxClass: 'icheckbox_square-red',
                //     radioClass: 'iradio_square-red',
                // });

                // // 选择所有，复选框改变
                // $("#selectClassList input[name='checkAll']").on('ifChanged', function () {
                //     $("#selectClassList input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
                //     if ($(this).is(":checked")) {
                //         $.ajax({
                //           type:"post",
                //           url:"recordClass.jhtml",
                //           data:{
                //               flag:2
                //           }
                //         })
                //     } else {
                //       $.ajax({
                //         type:"post",
                //         url:"recordClass.jhtml",
                //         data:{
                //             flag:3
                //         }
                //       })
                //     }
                // });

                // //选择单个,点击事件
                // $("#selectClassList input[name='ids']").on('ifClicked', function () {
                //     //选中状态
                //     if ($(this).is(":checked") == false) {
                //         $.ajax({
                //           type:"post",
                //           url:"recordClass.jhtml",
                //           data:{
                //               classID:$(this).val(),
                //               flag:1
                //           }

                //         })
                //     } else {
                //       //取消选中
                //       $.ajax({
                //         type:"post",
                //         url:"recordClass.jhtml",
                //         data:{
                //             classID:$(this).val(),
                //             flag:0
                //         }
                //       })
                //     }
                // });

                // //判断当前的复选框是否被选中，是则为选中状态
                // var sp = $("#recordClassId").children()
                // for(var i = 0 ; i < $("#selectClassList input[name='ids']").length ; i++){
                //  for(var j = 0 ; j < sp.length;j++){
                //       if($("#selectClassList input[name='ids']")[i].value == sp[j].innerHTML){
                //           $("#"+sp[j].innerHTML).iCheck('check')
                //       }
                //   }
                // }

                // $("input[name='checkAll']").on('ifChanged', function () {
                //     $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
                //     if ($(this).is(":checked")) {
                //         setCookie("course_class_selectAll",1)  //设置cookie标志
                //     } else {
                //         if(getCookie("course_class_selectAll")){  //反选则删除cookie
                //             clearCookie("course_class_selectAll")
                //             clearCookie("course_class_selectArr")
                //         }
                //     }
                // });
                // //选择单个,点击事件
                // $("input[name='ids']").on('ifChanged', function () {
                //     var arr = []
                //     //选中状态
                //     if ($(this).is(":checked")) {
                //         if(getCookie("course_class_selectArr")){
                //             arr = getCookie("course_class_selectArr").split(",")
                //         }
                //         if($.inArray($(this).val(),arr) == -1){  //去重
                //             arr.push($(this).val())
                //             console.log(arr)
                //             setCookie("course_class_selectArr",arr.join(","))
                //         }
                        
                //     } else {  //反选状态
                //         if(getCookie("course_class_selectArr")){
                //             arr = getCookie("course_class_selectArr").split(",")
                //             if($.inArray($(this).val(),arr) != -1){
                //                 arr.splice($.inArray($(this).val(),arr),1)  //删除元素
                //             }
                //             setCookie("course_class_selectArr",arr.join(","))

                //             clearCookie("course_class_selectAll")   //删除全选
                //         }
                //     }
                // });
                // //判断是否为全选 是则所有选择框为选中状态
                // if(getCookie("course_class_selectAll")){
                //     $("input[name='checkAll']").iCheck("check")
                // }
                // //判断当前的复选框是否被选中，是则为选中状态
                // if(getCookie("course_class_selectArr")){
                //     var sp = getCookie("course_class_selectArr").split(",")
                //     for(var i = 0 ; i < $("input[name='ids']").length ; i++){
                //         for(var j = 0 ; j < sp.length;j++){
                //             if($("input[name='ids']")[i].value == sp[j]){
                //                 $("#"+sp[j]).iCheck('check')
                //             }
                //         }
                //     }
                // }

                 // 初始化复选框
                $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});

                // 选择所有，复选框改变
                $("#selectClassList input[name='checkAll']").on('ifClicked', function () {
                  //判断用户是否勾选了全选选项，是则将当前页面的复选框全部选中，并将其ID存入Cookie中
                  if ($(this).is(":checked")) {
                     $("#selectClassList input[name='ids']").iCheck('uncheck'); 
                  } else {
                    $("#selectClassList input[name='ids']").iCheck('check');   //用户取消全选，则将Cookie中存放的SelectArr数组清除
                  }
                });

  
                //选择单个,点击事件
                $("#selectClassList input[name='ids']").on('ifChanged', function () {
                      var arr = []
                      //选中状态
                    if ($(this).is(":checked")) {
                          //当Cookie中已经存有存放ID的数组，则在此基础上增加ID
                          if(getCookie("course_class_selectArr")){
                            arr = getCookie("course_class_selectArr").split(",")
                          }
                          var str = $(this).val()+":"+$(this).attr("data-name")
                          if($.inArray(str,arr) == -1){  //去重
                            arr.push(str)
                            setCookie("course_class_selectArr",arr.join(","))
                          }
                          //用户勾选复选框时,判断当前页面所有复选框是否都已勾选中，是则将全选设为勾选状态
                          isSelectAll()
                    } else {  //反选状态
                        //查看Cookie中是否已存有用户选中的ID
                        if(getCookie("course_class_selectArr")){
                          //取消勾选时，查看全选选项是否被选中，是则将其取消掉
                          if($("#selectClassList input[name='checkAll']").is(":checked")){
                            $("#selectClassList input[name='checkAll']").iCheck("uncheck")
                          }
                          //删除cookie中存在的此选项ID
                          arr = getCookie("course_class_selectArr").split(",")
                          var str = $(this).val()+":"+$(this).attr("data-name")
                          if($.inArray(str,arr) != -1){   //查看数组中是否有存在此选项ID，有则删除此选项
                            arr.splice($.inArray(str,arr),1)  //删除元素
                          }
                          setCookie("course_class_selectArr",arr.join(","))   //删除之后再重新设置Cookie
                        }   
                    }
                });

                //判断当前的复选框是否被选中，是则为选中状态
                if(getCookie("course_class_selectArr")){
                    var sp = getCookie("course_class_selectArr").split(",")
                    for(var i = 0 ; i < $("#selectClassList input[name='ids']").length ; i++){
                      for(var j = 0 ; j < sp.length;j++){
                        var id = sp[j].split(":")[0]
                        if($("#selectClassList input[name='ids']")[i].value == id){
                            $("#selectClassList #"+id).iCheck('check')
                        }
                      }
                    }
                    //获取cookie中的selectArr数组，遍历循环查看当前页面是否都被选中，是则将全选选项勾选中
                    isSelectAll()
                }
            }, "html");
            }else{
            	alert("请选择专业")
            }
        });

        $(".ok-set-class").click(function () {
            // var $checkeds = $("#selectClassList input[name='ids']:checked");
            // var $inp = $("#selectClassList input[name='ids']");
            // var $collegeId = $("#recordClassId").children()
            // var $collegeName = $("#recordClassName").children()
            // var idArr = [],nameArr = []
            // var selectIds = '', selectNames = '';
            // //获取勾选的id与名称
            // if($collegeId.length > 0 || $checkeds.length > 0){
            //   for (var i = 0; i < $collegeId.length; i++) {
            //       if (i != 0) {
            //           selectIds += ",";
            //       }
            //       selectIds += $collegeId[i].innerHTML;
            //   }
            //   for (var j = 0; j < $collegeName.length; j++) {
            //       if (j != 0) {
            //           selectNames += "、";
            //       }
            //       selectNames += $collegeName[j].innerHTML
            //   }

            //   //将id与名称字符串转换为数组,方便遍历
            //   if(selectIds!="" || selectNames!=""){
            //     idArr = selectIds.split(',')
            //     nameArr = selectNames.split('、')
            //   }

            //   //没有翻页时，手动加入id与名称
            //   for(var p = 0 ; p < $checkeds.length;p++){
            //     //$.inArray : jquery判断数组中是否包含某个元素 没有则返回-1 有则返回元素下标值
            //     if($.inArray($($checkeds[p]).val(),idArr) == -1){
            //       idArr.push($($checkeds[p]).val())
            //       nameArr.push($($checkeds[p]).attr("data-name"))
            //     }
            //   }
            //   //取消选择
            //   for(var q = 0 ; q < $inp.length;q++){
            //     //判断当前页面的复选框是否有存在于id数组中，且不为选中状态
            //     if($.inArray($($inp[q]).val(),idArr) != -1 && $($inp[q]).is(":checked") == false){
            //       idArr.splice($.inArray($($inp[q]).val(),idArr),1)
            //       nameArr.splice($.inArray($($inp[q]).attr("data-name"),nameArr),1)
            //     }
            //   }
            //   //将所选择的名字显示到界面上
            //   if(nameArr.length<=3){
            //     $("#main_class").text(nameArr.join("、"));
            //   }else{
            //     var name = nameArr.slice(0,3)
            //     $("#main_class").text(name.join('、')+"、...");
            //   }

            //   $("#class_name").val(idArr.join(","));
            //   $('#selectClass').modal('hide');
            // }else{
            //   alert("请选择一个班级")
            // }
            if(getCookie("course_class_selectArr")){
                var course_class_selectArr = getCookie("course_class_selectArr").split(","),
                    idArr = [],
                    nameArr = []
                for (var d = 0; d < course_class_selectArr.length; d++) {
                    idArr.push(course_class_selectArr[d].split(":")[0])
                    nameArr.push(course_class_selectArr[d].split(":")[1])
                }
                // //将所选择的名字显示到界面上
                // for(var i = 0;i < idArr.length;i++){
                //     nameArr.push($("#selectClassList #"+idArr[i]).attr("data-name"))
                // }
                if(nameArr.length<=3){
                    $("#main_class").text(nameArr.join("、"));
                }else{
                    var name = nameArr.slice(0,3)
                    $("#main_class").text(name.join('、')+"、...");
                }
                $("#class_name").val(idArr.join(","));
                $('#selectClass').modal('hide');
            }else{
                alert("请选择一个班级")
            }
        });


        $("#saveCourseArrangeForm").validate({
            // onfocusout: function(element) { $(element).valid(); },
            // onkeyup: function(element) { $(element).valid(); },
            submitHandler:function (form) {   //不使用submit默认提交功能，先进行自定义验证，成功则提交表单
                $.ajax({
                  url: "${base}/admin/course_arrange/checkAll.jhtml",
                  type: "GET",
                  data: {
                    id: $("#courseArrangeId").val(),
                    class_id: $("#class_name").val(),
                    course_id: $("#courseId").val(),
                    teacher_id: $("#teacherId").val()
                  },
                  success:function (data) {   //发送ajax请求给后端进行验证，返回结果为true/false  
                      if(data){
                        $(".vaild").removeClass("has-error")
                        $(".vaild").addClass("has-success")
                        $(".vaild span:last").text("")
                        form.submit()
                      }else{
                        console.log(data)
                        $(".vaild").removeClass("has-success")
                        $(".vaild").addClass("has-error")
                        $(".vaild span:last").text("已存在")
                      }
                  }
                })
            },
            rules: {
                courseName: {
                    required: true
                },
                class_name: {
                    required: true
                },
                schoolName: {
                    required: true
                },
                collegeName: {
                    required: true
                },
                majorName: {
                    required: true
                },
                teacherName: {

					required: true,
                    // remote: {
                    //     url: "${base}/admin/course_arrange/checkAll.jhtml",
                    //     cache: false,
                    //     type: "GET",
                    //     data: {
                    //     	id: function () {
                    //             return $("#courseArrangeId").val();
                    //     	},
                    //         class_id: function () {
                    //             return $("#class_name").val();
                    //         },
                    //        	course_id: function () {
                    //             return $("#courseId").val();
                    //     	},
                    //        	teacher_id: function () {
                    //             return $("#teacherId").val();
                    //     	}
                    // 	}
                    // }
                    // remote: function (argument) {
                    //     if(){
                    //         return {
                    //             url: "${base}/admin/course_arrange/checkAll.jhtml",
                    //             cache: false,
                    //             type: "GET",
                    //             data: {
                    //                 id: $("#courseArrangeId").val(),
                    //                 class_id: $("#class_name").val(),
                    //                 course_id: $("#courseId").val(),
                    //                 teacher_id: $("#teacherId").val()
                    //             }
                    //         }
                    //     }
                    // }
                }
            },
            messages: {
                teacherName: {
                    pattern: "${message("admin.validate.illegal")}",
                    remote: "${message("admin.validate.exist")}"
                }

            }
        });
    });

    //通过schoolId获取college的信息--------------------------------------------------
    function add1(sId){
	var college=document.getElementById("collegeId");
	var options=college.getElementsByTagName("option");
	for(var m=1;m<options.length;m++){
		var op=options[m];
		college.removeChild(op);
		m--;
	}
	var major=document.getElementById("majorId");
	var options=major.getElementsByTagName("option");
	for(var m=1;m<options.length;m++){
		var op=options[m];
		major.removeChild(op);
		m--;
	}

	$("#main_class").text("");
    $("#class_name").val("");

	if($('#schoolName').val()!=""){
		$.ajax("${base}/admin/major/MajorAjax.jhtml",// 发送请求的URL字符串。
			{
				dataType : "json", // 预期服务器返回的数据类型。
   				type : "post", //  请求方式 POST或GET
		   		contentType:"application/json", //  发送信息至服务器时的内容编码类型
		   		// 发送到服务器的数据。
		   		data:JSON.stringify({id:sId}),
		   		async:  true , // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
		   		// 请求成功后的回调函数。
		   		success :function(data){
	             var json = eval(data); //数组
	             $.each(json, function (index, item) {
	                 //循环获取数据
	        		var collegeId = json[index].id;
	                var collegeName = json[index].collegeName;
	                var option1=document.createElement("option");
	         		var text1=document.createTextNode(collegeName)
					option1.value=collegeId;
	         		option1.appendChild(text1);
	         		college.appendChild(option1);
	             });
		   },

		   // 请求出错时调用的函数
		   error:function(){
			   alert("数据发送失败");
		   }
	});
	}


	}
	//通过collegeId获取major的信息-------------------------------------------------------
	 function selectMajor(cId){
	var major=document.getElementById("majorId");
	var options=major.getElementsByTagName("option");
	for(var m=1;m<options.length;m++){
		var op=options[m];
		major.removeChild(op);
		m--;
	}

	$("#main_class").text("");
    $("#class_name").val("");

	if($('#collegeId').prop('selectedIndex') != 0){
	$.ajax("${base}/admin/student_class/MajorAjax.jhtml",// 发送请求的URL字符串。
			{
				dataType : "json", // 预期服务器返回的数据类型。
   				type : "post", //  请求方式 POST或GET
		   		contentType:"application/json", //  发送信息至服务器时的内容编码类型
		   		// 发送到服务器的数据。
		   		data:JSON.stringify({id:cId}),
		   		async:  true , // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
		   		// 请求成功后的回调函数。
		   		success :function(data){
	             var json = eval(data); //数组
	             $.each(json, function (index, item) {
	                 //循环获取数据
	        		var majorId = json[index].id;
	                var majorName = json[index].majorName;
	                var option1=document.createElement("option");
	         		var text1=document.createTextNode(majorName)
					option1.value=majorId;
	         		option1.appendChild(text1);
	         		major.appendChild(option1);
	             });
		   },

		   // 请求出错时调用的函数
		   error:function(){
			   alert("数据发送失败");
		   }
	});

	//通过collegeId获取teacher的信息-------------------------------------------------------------
	var teacher=document.getElementById("teacherId");
	var options=teacher.getElementsByTagName("option");
	for(var m=1;m<options.length;m++){
		var op=options[m];
		teacher.removeChild(op);
		m--;
	}

	$.ajax("${base}/admin/course_arrange/TeacherAjax.jhtml",// 发送请求的URL字符串。
			{
   				type : "post", //  请求方式 POST或GET
		   		// 发送到服务器的数据。
		   		data:{
		   			collegeId:cId
		   		},
		   		// 请求成功后的回调函数。
		   		success :function(data){
	             var json = eval(data); //数组
	             $.each(json, function (index, item) {
	                 //循环获取数据
	        		var teacherId = json[index].id;
	                var teacherName = json[index].name;
	                var option1=document.createElement("option");
	         		var text1=document.createTextNode(teacherName)
					option1.value=teacherId;
	         		option1.appendChild(text1);
	         		teacher.appendChild(option1);
	             });
		   },

		   // 请求出错时调用的函数
		   error:function(){
			   alert("数据发送失败");
		   }
	});

	//通过collegeId获取course的信息-------------------------------------------------------------
	 var course=document.getElementById("courseId");
	 var options=course.getElementsByTagName("option");
	 for(var m=1;m<options.length;m++){
		var op=options[m];
		course.removeChild(op);
		m--;
	 }

	$.ajax("${base}/admin/course_arrange/CourseAjax.jhtml",// 发送请求的URL字符串。
			{
   				type : "post", //  请求方式 POST或GET
		   		// 发送到服务器的数据
		   		data:{
		   		     collegeId:$("#collegeId").val()
		   		},
		   		// 请求成功后的回调函数。
		   		success :function(data){
	             var json = eval(data); //数组
	             $.each(json, function (index, item) {
	                 //循环获取数据
	        		var courseId = json[index].id;
	                var courseName = json[index].courseName;
	                var option1=document.createElement("option");
	         		var text1=document.createTextNode(courseName)
					option1.value=courseId;
	         		option1.appendChild(text1);
	         		course.appendChild(option1);
	             });
		   },

		   // 请求出错时调用的函数
		   error:function(){
			   alert("数据发送失败");
		   }
	});

	}

	}
	//通过majorId获取class的信息-----------------------------------------------
	 function selectGrade(mId){

	 $("#main_class").text("");
     $("#class_name").val("");

	if($('#majorId').prop('selectedIndex') != 0){
	$.ajax("${base}/admin/course_arrange/ClassAjax.jhtml",// 发送请求的URL字符串。
			{
				dataType : "json", // 预期服务器返回的数据类型。
   				type : "post", //  请求方式 POST或GET
		   		contentType:"application/json", //  发送信息至服务器时的内容编码类型
		   		// 发送到服务器的数据。
		   		data:JSON.stringify({id:mId}),
		   		async:  true , // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
		   		// 请求成功后的回调函数。
		   		success :function(data){


		   },

		   // 请求出错时调用的函数
		   error:function(){
			   alert("数据发送失败");
		   }
	});
	}

	}


</script>
</body>
</html>
