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
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">

                <div class="ibox-title">
                    <div class="ibox-tools">
                       <button class="btn btn-white" type="button" onclick="window.history.back();" style="margin-top: -8px;">返回</button>
                        <!--  <a class="btn btn-white" href="${base}/admin/student/list.jhtml"
                           style="margin-top: -8px;">返回</a>-->
                    </div>

                    <h5>修改学生</h5>
                </div>
                <div class="ibox-content">
                    <form id="saveStudentForm" class="form-horizontal" action="editSave.jhtml" method="post">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-6" style="display:none">
                                    <label class="col-sm-3 control-label">学生ID<span
                                            class="required"> * </span></label>

                                    <div class="col-sm-9">
                                        <input type="text" name="id" id="studentId" class="form-control" maxlength="200"
                                               value="${student.id}" readonly="readonly"/>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">学校<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                    	[#if systemUser.roleId=-1]
                                          <select class="form-control" name="schoolId" id="schoolName"
                                                  value="${student.classList[0].classSystem.major.college.school.id}"
                                                  onchange="add1(this.value);">
                                              <option hassubinfo="true"
                                                      value="${student.classList[0].classSystem.major.college.school.id}">${student.classList[0].classSystem.major.college.school.schoolName}</option>
                                          </select>
                                        [/#if]
	                                    [#if systemUser.roleId=1||systemUser.roleId=0]
		                                     <input type="hidden" name="schoolId" id="schoolId"
                                                    value="${systemUser.school.id}">${systemUser.school.schoolName}
                                        [/#if]
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">学院<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                   		[#if systemUser.roleId=0]
                                    		<input type="hidden" name="college" id="college"
                                                   value="${teacherInit.college.id}">${teacherInit.college.collegeName}</input>
                                        [/#if]
                                    	[#if systemUser.roleId=-1||systemUser.roleId=1]
                                        <button type="button" class="btn btn-sm btn-outline btn-success"
                                                name="bt-select-college" id="bt-select-college"> 选择
                                        </button>
                                        <input type="hidden" name="college" id="college" value="college"/>
                                        <span id="main_college" class="collegename">
                                        [#list student.classList as class]
                                    	   [#if class_index+1 < student.classList?size]
                                    	       <span>${class.classSystem.major.college.collegeName}、</span>
                                           [#else]
                                    	       <span>${class.classSystem.major.college.collegeName}</span>
                                           [/#if]
                                        [/#list]
                                        </span>
                                        [/#if]
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">专业<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                    	[#if systemUser.roleId=0]
                                    		<input type="hidden" name="major" id="major"
                                                   value="${ttclassSystem.major.id}">${ttclassSystem.major.majorName}</input>
                                        [/#if]
                                    	[#if systemUser.roleId=-1||systemUser.roleId=1]
                                        <button type="button" class="btn btn-sm btn-outline btn-success"
                                                name="bt-select-major" id="bt-select-major"> 选择
                                        </button>
                                        <input type="hidden" name="major" id="major" value="major"/>
                                        <span id="main_profession">
                                        [#list student.classList as class]
                                    	[#if (class_index+1) < (student.classList?size)]
                                    	<span>${class.classSystem.major.majorName}、</span>
                                        [#else]
                                    	<span>${class.classSystem.major.majorName}</span>
                                        [/#if]
                                        [/#list]
                                        </span>
                                        [/#if]
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">班级<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                     	[#if systemUser.roleId=0]
                                    		<input type="hidden" name="className" id="className"
                                                   value="${ttclassSystem.id}">${ttclassSystem.className}</input>
                                        [/#if]
                                    	[#if systemUser.roleId=-1||systemUser.roleId=1]
                                        <button type="button" class="btn btn-sm btn-outline btn-success"
                                                name="bt-select-class" id="bt-select-class"> 选择
                                        </button>
                                        <input type="hidden" name="className" id="className" value="className"/>
                                        <span id="main_class">
                                        [#list student.classList as class]
                                            [#if (class_index+1) < (student.classList?size)]
                                            <span>${class.classSystem.className}、</span>
                                            [#else]
                                            <span>${class.classSystem.className}</span>
                                            [/#if]
                                        [/#list]
                                        </span>
                                        [#list student.classList as class]
                                            <span class="main_year" style="display: none">${class.classSystem.year}</span>
                                        [/#list]
                                        [/#if]


                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">姓名<span
                                            class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <input type="text" name="name" id="name" class="form-control"
                                               maxlength="200" value="${student.name}"/>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6 vaild">
                                    <label class="col-sm-3 control-label">学号<span
                                            class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <input type="text" name="number" id="number" class="form-control"
                                               maxlength="200" value="${student.username}"/>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">性别<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <select name="sex" id="sex"
                                                class="bs-select form-control">

                                            <option value="" [#if student.sex=="" ]selected[/#if]>请选择性别</option>
                                            <option value="男" [#if student.sex=="男" ]selected[/#if]>男</option>
                                            <option value="女" [#if student.sex=="女" ]selected[/#if]>女</option>

                                        </select>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">状态<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <select name="state" id="state"
                                                class="bs-select form-control">
                                            <option value="" [#if student.state=="" ]selected[/#if]>请选择状态</option>
                                            <option value="正常" [#if student.state=="正常" ]selected[/#if]>正常</option>
                                            <option value="休学" [#if student.state=="休学" ]selected[/#if]>休学</option>

                                        </select>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">邮箱<span
                                            class="required"></span>:</label>

                                    <div class="col-sm-9">
                                        <input type="text" name="email" id="email" class="form-control"
                                               maxlength="200" value="${student.email}"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--/row-->

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-12 col-sm-offset-2">
                                <button type="submit" class="btn btn-danger">确定</button>
                            </div>
                        </div>
                </div>
                </form>
            </div>
        </div>
    </div>
</div>
</div>

<!-- BEGIN CollegeDialog -->
<div class="modal inmodal" id="selectCollege" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width: 1000px;">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">选择学院</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <div class="row">
                        <div class="dataTables_wrapper form-inline dialog-table" id="selectCollegeList">

                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-danger ok-set-college">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- END CollegeDialog -->

<!-- BEGIN ProfessionDialog -->
<div class="modal inmodal" id="selectProfession" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">选择专业</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <div class="row">
                        <div class="dataTables_wrapper form-inline dialog-table" id="selectProfessionList">

                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-danger ok-set-major">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- END ProfessionDialog -->

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
        //重新选择学校 清空学院、专业、班级的cookie
        $("#schoolName").change(function () {
            clearCookie("college_selectArr")
            clearCookie("major_selectArr")
            clearCookie("class_selectArr")
        })
        //选择学院操作对话框--------------------------------------------------------------
        $("#bt-select-college").on("click", function () {
            if ($("#schoolName").val() || $("#schoolId").val()) {
                var data = {};
                var index = layer.load(1, {
                    shade: [0.1, '#fff'] //0.1透明度的白色背景
                });
                //获取id
                $.get("${base}/admin/student/listDialogCollege.jhtml", data, function (result, status) {
                    var tableCon = $("#selectCollegeList");
                    tableCon.empty();
                    tableCon.append(result);
                    layer.close(index);
                    $('#selectCollege').modal('show');
                    // // 复选框
                    // $('#selectCollegeList .i-checks').iCheck({
                    //     checkboxClass: 'icheckbox_square-red',
                    //     radioClass: 'iradio_square-red',
                    // });


                    // // 选择所有，复选框改变
                    // $("#selectCollegeList input[name='checkAll']").on('ifChanged', function () {
                    //     $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
                    //     if ($(this).is(":checked")) {
                    //         $.ajax({
                    //           type:"post",
                    //           url:"recordCollege.jhtml",
                    //           data:{
                    //               flag:2
                    //           }
                    //         })
                    //     } else {
                    //       $.ajax({
                    //         type:"post",
                    //         url:"recordCollege.jhtml",
                    //         data:{
                    //             flag:3
                    //         }
                    //       })
                    //     }
                    // });

                    // //选择单个,点击事件
                    // $("#selectCollegeList input[name='ids']").on('ifClicked', function () {
                    //     //选中状态
                    //     if ($(this).is(":checked") == false) {
                    //         $.ajax({
                    //           type:"post",
                    //           url:"recordCollege.jhtml",
                    //           data:{
                    //               collogeID:$(this).val(),
                    //               flag:1
                    //           }

                    //         })
                    //     } else {
                    //       //取消选中
                    //       $.ajax({
                    //         type:"post",
                    //         url:"recordCollege.jhtml",
                    //         data:{
                    //             collogeID:$(this).val(),
                    //             flag:0
                    //         }
                    //       })
                    //     }
                    // });
                    // $("input[name='checkAll']").on('ifChanged', function () {
                    //         $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
                    //         if ($(this).is(":checked")) {
                    //             setCookie("college_selectAll",1)  //设置cookie标志
                    //         } else {
                    //             if(getCookie("college_selectAll")){  //反选则删除cookie
                    //                 clearCookie("college_selectAll")
                    //                 clearCookie("college_selectArr")
                    //             }
                    //         }
                    //     });
                    //     //选择单个,点击事件
                    //     $("input[name='ids']").on('ifChanged', function () {
                    //         var arr = []
                    //         //选中状态
                    //         if ($(this).is(":checked")) {
                    //             if(getCookie("college_selectArr")){
                    //                 arr = getCookie("college_selectArr").split(",")
                    //             }
                    //             if($.inArray($(this).val(),arr) == -1){  //去重
                    //                 arr.push($(this).val())
                    //                 setCookie("college_selectArr",arr.join(","))
                    //             }

                    //         } else {  //反选状态
                    //             if(getCookie("college_selectArr")){
                    //                 arr = getCookie("college_selectArr").split(",")
                    //                 if($.inArray($(this).val(),arr) != -1){
                    //                     arr.splice($.inArray($(this).val(),arr),1)  //删除元素
                    //                 }
                    //                 setCookie("college_selectArr",arr.join(","))

                    //                 clearCookie("college_selectAll")   //删除全选
                    //             }
                    //         }
                    //     });
                    //     //判断是否为全选 是则所有选择框为选中状态
                    //     if(getCookie("college_selectAll")){
                    //         $("input[name='checkAll']").iCheck("check")
                    //     }
                    //     //判断当前的复选框是否被选中，是则为选中状态
                    //     if(getCookie("college_selectArr")){
                    //         var sp = getCookie("college_selectArr").split(",")
                    //         for(var i = 0 ; i < $("input[name='ids']").length ; i++){
                    //             for(var j = 0 ; j < sp.length;j++){
                    //                 if($("input[name='ids']")[i].value == sp[j]){
                    //                     $("#"+sp[j]).iCheck('check')
                    //                 }
                    //             }
                    //         }
                    //     }
                    // 初始化复选框
                    $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});

                    // 选择所有，复选框改变
                    $("#selectCollegeList input[name='checkAll']").on('ifClicked', function () {
                        //判断用户是否勾选了全选选项，是则将当前页面的复选框全部选中，并将其ID存入Cookie中
                        if ($(this).is(":checked")) {
                            $("#selectCollegeList input[name='ids']").iCheck('uncheck');
                        } else {
                            $("#selectCollegeList input[name='ids']").iCheck('check');   //用户取消全选，则将Cookie中存放的SelectArr数组清除
                        }
                    });


                    //选择单个,点击事件
                    $("#selectCollegeList input[name='ids']").on('ifChanged', function () {
                        console.log(getCookie("college_selectArr"))
                        var arr = []
                        //选中状态
                        if ($(this).is(":checked")) {
                            //当Cookie中已经存有存放ID的数组，则在此基础上增加ID
                            if (getCookie("college_selectArr")) {
                                arr = getCookie("college_selectArr").split(",")
                            }
                            var str = $(this).val() + ":" + $(this).attr("data-name")
                            if ($.inArray(str, arr) == -1) {  //去重
                                arr.push(str)
                                setCookie("college_selectArr", arr.join(","))
                            }
                            //用户勾选复选框时,判断当前页面所有复选框是否都已勾选中，是则将全选设为勾选状态
                            isSelectAll()
                        } else {  //反选状态
                            //查看Cookie中是否已存有用户选中的ID
                            if (getCookie("college_selectArr")) {
                                //取消勾选时，查看全选选项是否被选中，是则将其取消掉
                                if ($("#selectCollegeList input[name='checkAll']").is(":checked")) {
                                    $("#selectCollegeList input[name='checkAll']").iCheck("uncheck")
                                }
                                //删除cookie中存在的此选项ID
                                arr = getCookie("college_selectArr").split(",")
                                var str = $(this).val() + ":" + $(this).attr("data-name")
                                if ($.inArray(str, arr) != -1) {   //查看数组中是否有存在此选项ID，有则删除此选项
                                    arr.splice($.inArray(str, arr), 1)  //删除元素
                                }
                                setCookie("college_selectArr", arr.join(","))   //删除之后再重新设置Cookie
                            }
                        }
                    });

                    //判断当前的复选框是否被选中，是则为选中状态
                    if (getCookie("college_selectArr")) {
                        var sp = getCookie("college_selectArr").split(",")
                        for (var i = 0; i < $("#selectCollegeList input[name='ids']").length; i++) {
                            for (var j = 0; j < sp.length; j++) {
                                var id = sp[j].split(":")[0]
                                if ($("#selectCollegeList input[name='ids']")[i].value == id) {
                                    $("#selectCollegeList #" + id).iCheck('check')
                                }
                            }
                        }
                        //获取cookie中的selectArr数组，遍历循环查看当前页面是否都被选中，是则将全选选项勾选中
                        isSelectAll()
                    }
                    var $main_college = $("#main_college").text().split('、')  //只有一个的时候??
                    // console.log($main_college)
                    // console.log($("#main_college").children())
                    // var sp = $("#recordCollegeId").children()
                    for (var i = 0; i < $("#selectCollegeList input[name='ids']").length; i++) {
                        //判断当前的复选框是否被选中，是则为选中状态
                        // for(var j = 0 ; j < sp.length;j++){
                        //      if($("#selectCollegeList input[name='ids']")[i].value == sp[j].innerHTML){
                        //          $("#"+sp[j].innerHTML).iCheck('check')
                        //      }
                        //  }
                        //获取原先的学院名称，使其为勾选状态
                        if ($("#main_college").children().length > 0) {
                            $main_college = $("#main_college").children()
                            for (var a = 0; a < $main_college.length; a++) {
                                var main_college_name = ""
                                //getAttribute : 获取元素的属性值
                                if ($main_college[a].innerHTML.indexOf("、") != -1) {
                                    main_college_name = $main_college[a].innerHTML.slice(0, $main_college[a].innerHTML.indexOf("、"))
                                } else {
                                    main_college_name = $main_college[a].innerHTML
                                }
                                if ($("#selectCollegeList input[name='ids']")[i].getAttribute("data-name") == main_college_name) {
                                    $("#" + $("#selectCollegeList input[name='ids']")[i].getAttribute("id")).iCheck('check')
                                }
                            }
                        } else {
                            for (var a = 0; a < $main_college.length; a++) {
                                //getAttribute : 获取元素的属性值
                                if ($("#selectCollegeList input[name='ids']")[i].getAttribute("data-name") == $main_college[a]) {
                                    $("#" + $("#selectCollegeList input[name='ids']")[i].getAttribute("id")).iCheck('check')
                                }
                            }
                        }
                    }
                }, "html");
            } else {
                alert("请选择学校")
            }
        });

        //确定选择学院
        $(".ok-set-college").click(function () {
            // var $checkeds = $("#selectCollegeList input[name='ids']:checked");
            // var $inp = $("#selectCollegeList input[name='ids']");
            // var $collegeId = $("#recordCollegeId").children()
            // var $collegeName = $("#recordCollegeName").children()
            // var idArr = [],nameArr = []
            // var selectIds = '', selectNames = '';
            // //获取勾选的学院id与名称
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

            //   //将学院id与名称字符串转换为数组,方便遍历
            //   if(selectIds!="" || selectNames!=""){
            //     idArr = selectIds.split(',')
            //     nameArr = selectNames.split('、')
            //   }

            //   //没有翻页时，手动加入学院id与名称
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
            //   //当用户有选择学院时，发送ajax请求
            //   if(idArr.length>0){
            //     //发送请求的URL字符串。
            //     $.ajax("${base}/admin/student/MajorAjax.jhtml",
            //             {
            //                 type: "post",
            //                 // 发送到服务器的数据。
            //                 data: {
            //                     selectIds: idArr.join(",")
            //                 },
            //                 success:function(){
            //                   $("#selectCollegeList").empty()
            //                 }
            //             });
            //   }
            //   //将所选择的名字显示到界面上
            //   if(nameArr.length<=3){
            //     $("#main_college").text(nameArr.join("、"));
            //   }else{
            //     var name = nameArr.slice(0,3)
            //     $("#main_college").text(name.join('、')+"、...");
            //   }

            //   $("#college").val(idArr.join(","));
            //   // $("#main_college").text(selectNames);
            //   // $("#college").val(selectIds);
            //   $('#selectCollege').modal('hide');
            //   $("#main_profession").text("");
            //   $("#major").val("");
            //   $("#main_class").text("");
            //   $("#className").val("");
            // }else{
            //   alert("请选择一个学院")
            // }

            if (getCookie("college_selectArr")) {
                var college_selectArr = getCookie("college_selectArr").split(","),
                        idArr = [],
                        nameArr = []
                for (var d = 0; d < college_selectArr.length; d++) {
                    idArr.push(college_selectArr[d].split(":")[0])
                    nameArr.push(college_selectArr[d].split(":")[1])
                }
                //发送请求的URL字符串。
                $.ajax("${base}/admin/student/MajorAjax.jhtml",
                        {
                            type: "post",
                            // 发送到服务器的数据。
                            data: {
                                selectIds: idArr.join(",")
                            },
                            success: function () {
                                $("#selectCollegeList").empty()
                            }
                        });
                // //将所选择的名字显示到界面上
                // for(var i = 0;i < idArr.length;i++){
                //     nameArr.push($("#selectCollegeList #"+idArr[i]).attr("data-name"))
                // }
                if (nameArr.length <= 3) {
                    $("#main_college").text(nameArr.join("、"));
                } else {
                    var name = nameArr.slice(0, 3)
                    $("#main_college").text(name.join('、') + "、...");
                }
                $("#college").val(idArr.join(","));

                //重新选择学院时，清空已选择的专业及班级
                $("#main_profession").text("");
                $("#major").val("");
                $("#main_class").text("");
                $("#className").val("");
                //清除专业及班级的cookie
                clearCookie("major_selectArr")
                // clearCookie("major_selectAll")
                clearCookie("class_selectArr")
                // clearCookie("class_selectAll")

                $('#selectCollege').modal('hide');
            } else {
                alert("请选择一个学院")
            }
        });

        //选择专业操作对话框-------------------------------------------------------------------
        $("#bt-select-major").on("click", function () {
            if ($("#college").val()) {
                var data = {};
                var index = layer.load(1, {
                    shade: [0.1, '#fff'] //0.1透明度的白色背景
                });
                //获取id
                $.get("${base}/admin/student/listDialogMajor.jhtml", data, function (result, status) {
                    var tableCon = $("#selectProfessionList");
                    tableCon.empty();
                    tableCon.append(result);
                    layer.close(index);
                    $('#selectProfession').modal('show');

                    // // 复选框
                    // $('#selectProfessionList .i-checks').iCheck({
                    //     checkboxClass: 'icheckbox_square-red',
                    //     radioClass: 'iradio_square-red',
                    // });
                    // // 选择所有，复选框改变
                    // $("#selectProfessionList input[name='checkAll']").on('ifChanged', function () {
                    //     $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
                    //     if ($(this).is(":checked")) {
                    //         $.ajax({
                    //           type:"post",
                    //           url:"recordMajor.jhtml",
                    //           data:{
                    //               flag:2
                    //           }
                    //         })
                    //     } else {
                    //       $.ajax({
                    //         type:"post",
                    //         url:"recordMajor.jhtml",
                    //         data:{
                    //             flag:3
                    //         }
                    //       })
                    //     }
                    // });

                    // //选择单个,点击事件
                    // $("#selectProfessionList input[name='ids']").on('ifClicked', function () {
                    //     //选中状态
                    //     if ($(this).is(":checked") == false) {
                    //         $.ajax({
                    //           type:"post",
                    //           url:"recordMajor.jhtml",
                    //           data:{
                    //               majorID:$(this).val(),
                    //               flag:1
                    //           }

                    //         })
                    //     } else {
                    //       //取消选中
                    //       $.ajax({
                    //         type:"post",
                    //         url:"recordMajor.jhtml",
                    //         data:{
                    //             majorID:$(this).val(),
                    //             flag:0
                    //         }
                    //       })
                    //     }
                    // });
                    // $("input[name='checkAll']").on('ifChanged', function () {
                    //         $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
                    //         if ($(this).is(":checked")) {
                    //             setCookie("major_selectAll",1)  //设置cookie标志
                    //         } else {
                    //             if(getCookie("major_selectAll")){  //反选则删除cookie
                    //                 clearCookie("major_selectAll")
                    //                 clearCookie("major_selectArr")
                    //             }
                    //         }
                    //     });
                    //     //选择单个,点击事件
                    //     $("input[name='ids']").on('ifChanged', function () {
                    //         var arr = []
                    //         //选中状态
                    //         if ($(this).is(":checked")) {
                    //             if(getCookie("major_selectArr")){
                    //                 arr = getCookie("major_selectArr").split(",")
                    //             }
                    //             if($.inArray($(this).val(),arr) == -1){  //去重
                    //                 arr.push($(this).val())
                    //                 console.log(arr)
                    //                 setCookie("major_selectArr",arr.join(","))
                    //             }

                    //         } else {  //反选状态
                    //             if(getCookie("major_selectArr")){
                    //                 arr = getCookie("major_selectArr").split(",")
                    //                 if($.inArray($(this).val(),arr) != -1){
                    //                     arr.splice($.inArray($(this).val(),arr),1)  //删除元素
                    //                 }
                    //                 setCookie("major_selectArr",arr.join(","))

                    //                 clearCookie("major_selectAll")   //删除全选
                    //             }
                    //         }
                    //     });
                    //     //判断是否为全选 是则所有选择框为选中状态
                    //     if(getCookie("major_selectAll")){
                    //         $("input[name='checkAll']").iCheck("check")
                    //     }
                    //     //判断当前的复选框是否被选中，是则为选中状态
                    //     if(getCookie("major_selectArr")){
                    //         var sp = getCookie("major_selectArr").split(",")
                    //         for(var i = 0 ; i < $("input[name='ids']").length ; i++){
                    //             for(var j = 0 ; j < sp.length;j++){
                    //                 if($("input[name='ids']")[i].value == sp[j]){
                    //                     $("#"+sp[j]).iCheck('check')
                    //                 }
                    //             }
                    //         }
                    //     }

                    // 初始化复选框
                    $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});

                    // 选择所有，复选框改变
                    $("#selectProfessionList input[name='checkAll']").on('ifClicked', function () {
                        //判断用户是否勾选了全选选项，是则将当前页面的复选框全部选中，并将其ID存入Cookie中
                        if ($(this).is(":checked")) {
                            $("#selectProfessionList input[name='ids']").iCheck('uncheck');
                        } else {
                            $("#selectProfessionList input[name='ids']").iCheck('check');   //用户取消全选，则将Cookie中存放的SelectArr数组清除
                        }
                    });


                    //选择单个,点击事件
                    $("#selectProfessionList input[name='ids']").on('ifChanged', function () {
                        console.log(getCookie("major_selectArr"))
                        var arr = []
                        //选中状态
                        if ($(this).is(":checked")) {
                            //当Cookie中已经存有存放ID的数组，则在此基础上增加ID
                            if (getCookie("major_selectArr")) {
                                arr = getCookie("major_selectArr").split(",")
                            }
                            var str = $(this).val() + ":" + $(this).attr("data-name")
                            if ($.inArray(str, arr) == -1) {  //去重
                                arr.push(str)
                                setCookie("major_selectArr", arr.join(","))
                            }
                            //用户勾选复选框时,判断当前页面所有复选框是否都已勾选中，是则将全选设为勾选状态
                            isSelectAll()
                        } else {  //反选状态
                            //查看Cookie中是否已存有用户选中的ID
                            if (getCookie("major_selectArr")) {
                                //取消勾选时，查看全选选项是否被选中，是则将其取消掉
                                if ($("#selectProfessionList input[name='checkAll']").is(":checked")) {
                                    $("#selectProfessionList input[name='checkAll']").iCheck("uncheck")
                                }
                                //删除cookie中存在的此选项ID
                                arr = getCookie("major_selectArr").split(",")
                                var str = $(this).val() + ":" + $(this).attr("data-name")
                                if ($.inArray(str, arr) != -1) {   //查看数组中是否有存在此选项ID，有则删除此选项
                                    arr.splice($.inArray(str, arr), 1)  //删除元素
                                }
                                setCookie("major_selectArr", arr.join(","))   //删除之后再重新设置Cookie
                            }
                        }
                    });

                    //判断当前的复选框是否被选中，是则为选中状态
                    if (getCookie("major_selectArr")) {
                        var sp = getCookie("major_selectArr").split(",")
                        for (var i = 0; i < $("#selectProfessionList input[name='ids']").length; i++) {
                            for (var j = 0; j < sp.length; j++) {
                                var id = sp[j].split(":")[0]
                                if ($("#selectProfessionList input[name='ids']")[i].value == id) {
                                    $("#selectProfessionList #" + id).iCheck('check')
                                }
                            }
                        }
                        //获取cookie中的selectArr数组，遍历循环查看当前页面是否都被选中，是则将全选选项勾选中
                        isSelectAll()
                    }

                    // var sp = $("#recordMajorId").children()
                    var $main_profession = $("#main_profession").text().split('、')
                    for (var i = 0; i < $("#selectProfessionList input[name='ids']").length; i++) {
                        //判断当前的复选框是否被选中，是则为选中状态
                        // for(var j = 0 ; j < sp.length;j++){
                        //      if($("#selectProfessionList input[name='ids']")[i].value == sp[j].innerHTML){
                        //          $("#"+sp[j].innerHTML).iCheck('check')
                        //      }
                        //  }

                        //获取原先的专业名称，使其为勾选状态
                        if ($("#main_profession").children().length > 0) {
                            $main_profession = $("#main_profession").children()
                            //获取专业名称，将有此专业名称的一行勾选中
                            for (var a = 0; a < $main_profession.length; a++) {
                                var main_profession_name = ""
                                if ($main_profession[a].innerHTML.indexOf("、") != -1) {
                                    main_profession_name = $main_profession[a].innerHTML.slice(0, $main_profession[a].innerHTML.indexOf("、"))
                                } else {
                                    main_profession_name = $main_profession[a].innerHTML
                                }
                                //getAttribute : 获取元素的属性值
                                if ($("#selectProfessionList input[name='ids']")[i].getAttribute("data-name") == main_profession_name) {
                                    $("#" + $("#selectProfessionList input[name='ids']")[i].getAttribute("id")).iCheck('check')
                                }
                            }
                        } else {
                            for (var a = 0; a < $main_profession.length; a++) {
                                //getAttribute : 获取元素的属性值
                                if ($("#selectProfessionList input[name='ids']")[i].getAttribute("data-name") == $main_profession[a]) {
                                    $("#" + $("#selectProfessionList input[name='ids']")[i].getAttribute("id")).iCheck('check')
                                }
                            }
                        }
                    }
                }, "html");
            } else {
                alert("请选择学院");
            }
        });

        //确定选择专业
        $(".ok-set-major").click(function () {

            // var $checkeds = $("#selectProfessionList input[name='ids']:checked");
            // var $inp = $("#selectProfessionList input[name='ids']");
            // var $collegeId = $("#recordMajorId").children()
            // var $collegeName = $("#recordMajorName").children()
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
            //   //当用户有选择专业时，发送ajax请求
            //   if(idArr.length>0){
            //     //发送请求的URL字符串。
            //     $.ajax("${base}/admin/student/ClassAjax.jhtml",
            //             {
            //                 type: "post",
            //                 // 发送到服务器的数据。
            //                 data: {
            //                     selectIds: idArr.join(",")
            //                 },
            //                 success:function(){
            //                     $("#selectProfessionList").empty()
            //                 }
            //             });
            //   }
            //   //将所选择的名字显示到界面上
            //   if(nameArr.length<=3){
            //     $("#main_profession").text(nameArr.join("、"));
            //   }else{
            //     var name = nameArr.slice(0,3)
            //     $("#main_profession").text(name.join('、')+"、...");
            //   }
            //   $("#major").val(idArr.join(","));
            //   $('#selectProfession').modal('hide');
            //   $("#main_class").text("");
            //   $("#className").val("");
            // }else{
            //   alert("请选择一个专业")
            // }

            if (getCookie("major_selectArr")) {
                var major_selectArr = getCookie("major_selectArr").split(","),
                        idArr = [],
                        nameArr = []
                for (var d = 0; d < major_selectArr.length; d++) {
                    idArr.push(major_selectArr[d].split(":")[0])
                    nameArr.push(major_selectArr[d].split(":")[1])
                }
                //发送请求的URL字符串。
                $.ajax("${base}/admin/student/ClassAjax.jhtml",
                        {
                            type: "post",
                            // 发送到服务器的数据。
                            data: {
                                selectIds: idArr.join(",")
                            },
                            success: function () {
                                $("#selectProfessionList").empty()
                            }
                        });
                // //将所选择的名字显示到界面上
                // for(var i = 0;i < idArr.length;i++){
                //     nameArr.push($("#selectProfessionList #"+idArr[i]).attr("data-name"))
                // }
                if (nameArr.length <= 3) {
                    $("#main_profession").text(nameArr.join("、"));
                } else {
                    var name = nameArr.slice(0, 3)
                    $("#main_profession").text(name.join('、') + "、...");
                }
                $("#major").val(idArr.join(","));

                //重新选择专业时，将已选择的班级清空
                $("#main_class").text("");
                $("#className").val("");
                //清除班级cookie
                clearCookie("class_selectArr")
                // clearCookie("class_selectAll")

                $('#selectProfession').modal('hide');
            } else {
                alert("请选择一个专业")
            }
        });

        //选择班级操作对话框-------------------------------------------------------
        $("#bt-select-class").on("click", function () {
            if ($("#major").val()) {
                var data = {};
                var index = layer.load(1, {
                    shade: [0.1, '#fff'] //0.1透明度的白色背景
                });
                //获取id
                $.get("${base}/admin/student/listDialogClass.jhtml", data, function (result, status) {
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

                    // $("input[name='checkAll']").on('ifChanged', function () {
                    //         $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
                    //         if ($(this).is(":checked")) {
                    //             setCookie("class_selectAll",1)  //设置cookie标志
                    //         } else {
                    //             if(getCookie("class_selectAll")){  //反选则删除cookie
                    //                 clearCookie("class_selectAll")
                    //                 clearCookie("class_selectArr")
                    //             }
                    //         }
                    //     });
                    //     //选择单个,点击事件
                    //     $("input[name='ids']").on('ifChanged', function () {
                    //         var arr = []
                    //         //选中状态
                    //         if ($(this).is(":checked")) {
                    //             if(getCookie("class_selectArr")){
                    //                 arr = getCookie("class_selectArr").split(",")
                    //             }
                    //             if($.inArray($(this).val(),arr) == -1){  //去重
                    //                 arr.push($(this).val())
                    //                 console.log(arr)
                    //                 setCookie("class_selectArr",arr.join(","))
                    //             }

                    //         } else {  //反选状态
                    //             if(getCookie("class_selectArr")){
                    //                 arr = getCookie("class_selectArr").split(",")
                    //                 if($.inArray($(this).val(),arr) != -1){
                    //                     arr.splice($.inArray($(this).val(),arr),1)  //删除元素
                    //                 }
                    //                 setCookie("class_selectArr",arr.join(","))

                    //                 clearCookie("class_selectAll")   //删除全选
                    //             }
                    //         }
                    //     });
                    //     //判断是否为全选 是则所有选择框为选中状态
                    //     if(getCookie("class_selectAll")){
                    //         $("input[name='checkAll']").iCheck("check")
                    //     }
                    //     //判断当前的复选框是否被选中，是则为选中状态
                    //     if(getCookie("class_selectArr")){
                    //         var sp = getCookie("class_selectArr").split(",")
                    //         for(var i = 0 ; i < $("input[name='ids']").length ; i++){
                    //             for(var j = 0 ; j < sp.length;j++){
                    //                 if($("input[name='ids']")[i].value == sp[j]){
                    //                     $("#"+sp[j]).iCheck('check')
                    //                 }
                    //             }
                    //         }
                    //     }
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
                            if (getCookie("class_selectArr")) {
                                arr = getCookie("class_selectArr").split(",")
                            }
                            var str = $(this).val() + ":" + $(this).attr("data-name")
                            if ($.inArray(str, arr) == -1) {  //去重
                                arr.push(str)
                                setCookie("class_selectArr", arr.join(","))
                            }
                            //用户勾选复选框时,判断当前页面所有复选框是否都已勾选中，是则将全选设为勾选状态
                            isSelectAll()
                        } else {  //反选状态
                            //查看Cookie中是否已存有用户选中的ID
                            if (getCookie("class_selectArr")) {
                                //取消勾选时，查看全选选项是否被选中，是则将其取消掉
                                if ($("#selectClassList input[name='checkAll']").is(":checked")) {
                                    $("#selectClassList input[name='checkAll']").iCheck("uncheck")
                                }
                                //删除cookie中存在的此选项ID
                                arr = getCookie("class_selectArr").split(",")
                                var str = $(this).val() + ":" + $(this).attr("data-name")
                                if ($.inArray(str, arr) != -1) {   //查看数组中是否有存在此选项ID，有则删除此选项
                                    arr.splice($.inArray(str, arr), 1)  //删除元素
                                }
                                setCookie("class_selectArr", arr.join(","))   //删除之后再重新设置Cookie
                            }
                        }
                    });

                    //判断当前的复选框是否被选中，是则为选中状态
                    if (getCookie("class_selectArr")) {
                        var sp = getCookie("class_selectArr").split(",")
                        for (var i = 0; i < $("#selectClassList input[name='ids']").length; i++) {
                            for (var j = 0; j < sp.length; j++) {
                                var id = sp[j].split(":")[0]
                                if ($("#selectClassList input[name='ids']")[i].value == id) {
                                    $("#selectClassList #" + id).iCheck('check')
                                }
                            }
                        }
                        //获取cookie中的selectArr数组，遍历循环查看当前页面是否都被选中，是则将全选选项勾选中
                        isSelectAll()
                    }
                    // var sp = $("#recordClassId").children()
                    var $main_class = $("#main_class").text().split("、")
                    for (var i = 0; i < $("#selectClassList input[name='ids']").length; i++) {
                        //判断当前的复选框是否被选中，是则为选中状态
                        // for(var j = 0 ; j < sp.length;j++){
                        //      if($("#selectClassList input[name='ids']")[i].value == sp[j].innerHTML){
                        //          $("#"+sp[j].innerHTML).iCheck('check')
                        //      }
                        //  }

                        //获取原先的班级名称，使其为勾选状态
                        if ($("#main_class").children().length > 0) {
                            $main_class = $("#main_class").children()
                            for (var a = 0; a < $main_class.length; a++) {
                                var main_class_name = ""
                                if ($main_class[a].innerHTML.indexOf("、") != -1) {
                                    main_class_name = $main_class[a].innerHTML.slice(0, $main_class[a].innerHTML.indexOf("、"))
                                } else {
                                    main_class_name = $main_class[a].innerHTML
                                }
                                //getAttribute : 获取元素的属性值
                                if ($("#selectClassList input[name='ids']")[i].getAttribute("data-name") == main_class_name &&
                                        $("#selectClassList .class_year")[i].innerHTML == $(".main_year")[a].innerHTML) {
                                    $("#" + $("#selectClassList input[name='ids']")[i].getAttribute("id")).iCheck('check')
                                }
                            }
                        } else {
                            for (var a = 0; a < $main_class.length; a++) {
                                //getAttribute : 获取元素的属性值
                                if ($("#selectClassList input[name='ids']")[i].getAttribute("data-name") == $main_class[a] &&
                                        $("#selectClassList .class_year")[i].innerHTML == $(".main_year")[a].innerHTML) {
                                    $("#" + $("#selectClassList input[name='ids']")[i].getAttribute("id")).iCheck('check')
                                }
                            }
                        }
                    }
                }, "html");
            } else {
                alert("请选择专业")
            }
        });

        //确定选择班级
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
            //   $("#className").val(idArr.join(","));
            //   $('#selectClass').modal('hide');
            // }else{
            //   alert("请选择一个班级")
            // }

            if (getCookie("class_selectArr")) {
                var class_selectArr = getCookie("class_selectArr").split(","),
                        idArr = [],
                        nameArr = []
                for (var d = 0; d < class_selectArr.length; d++) {
                    idArr.push(class_selectArr[d].split(":")[0])
                    nameArr.push(class_selectArr[d].split(":")[1])
                }
                // //将所选择的名字显示到界面上
                // for(var i = 0;i < idArr.length;i++){
                //     nameArr.push($("#selectClassList #"+idArr[i]).attr("data-name"))
                // }
                if (nameArr.length <= 3) {
                    $("#main_class").text(nameArr.join("、"));
                } else {
                    var name = nameArr.slice(0, 3)
                    $("#main_class").text(name.join('、') + "、...");
                }
                $("#className").val(idArr.join(","));
                $('#selectClass').modal('hide');
            } else {
                alert("请选择一个班级")
            }
        });

        // ----------------------------------------------------------------------------
        $("#saveStudentForm").validate({
            submitHandler: function (form) {   //不使用submit默认提交功能，先进行自定义验证，成功则提交表单
                $.ajax({
                    url: "${base}/admin/student/check_username.jhtml",
                    type: "GET",
                    data: {
                        studentId: $("#studentId").val(),
                        username: $("#number").val(),
                        schoolId: function () {
                            if ($("#schoolName").val()) {
                                return $("#schoolName").val();
                            } else if ($("#schoolId").val()) {
                                return $("#schoolId").val()
                            }
                        }
                    },
                    success: function (data) {   //发送ajax请求给后端进行验证，返回结果为true/false
                        if (data) {
                            $(".vaild").removeClass("has-error")
                            $(".vaild").addClass("has-success")
                            $(".vaild span:last").text("")
                            form.submit()
                        } else {
                            $(".vaild").removeClass("has-success")
                            $(".vaild").addClass("has-error")
                            $(".vaild span:last").text("已存在")
                        }
                    }
                })
            },
            onfocusout: function (element) {
                $(element).valid();
            },
            onkeyup: function (element) {
                $(element).valid();
            },
            rules: {
                name: {
                    required: true
                },
                number: {
                    required: true,
                    // remote: {
                    //     url: "${base}/admin/student/check_username.jhtml",
                    //     cache: false,
                    //     type: "GET",
                    //     data: {
                    //     	studentId: function () {
                    //             return $("#studentId").val();
                    //     	},
                    //         username: function () {
                    //             return $("#number").val();
                    //         },
                    //        schoolId: function () {
                    //             if($("#schoolName").val()){
                    //                 return $("#schoolName").val();
                    //             }else if($("#schoolId").val()){
                    //                 return $("#schoolId").val()
                    //             }

                    //     	}
                    // 	}
                    // }
                    // remote: function () {
                    //     if ($("#schoolName").val() || $("#schoolId").val()) {
                    //         return {
                    //             url: "${base}/admin/student/check_username.jhtml",
                    //             cache: false,
                    //             type: "GET",
                    //             data: {
                    //                 studentId: $("#studentId").val(),
                    //                 username: $("#number").val(),
                    //                 schoolId: function () {
                    //                     if($("#schoolName").val()){
                    //                         return $("#schoolName").val();
                    //                     }else if($("#schoolId").val()){
                    //                         return $("#schoolId").val()
                    //                     }

                    //                 }
                    //             }
                    //         }
                    //     }
                    // }
                },
                sex: {
                    required: true
                },
                schoolName: {
                    required: true
                },
                college: {
                    required: true
                },
                major: {
                    required: true
                },
                className: {
                    required: true
                },
                state: {
                    required: true
                },
                email: {
                    email: true
                }
            },
            messages: {
                number: {
                    pattern: "${message("admin.validate.illegal")}",
                    remote: "${message("admin.validate.exist")}"
                }
            }
        });

        //var number = document.getElementById('number')
        //number.value = number.value.replace(/[^0-9]+/g, '')

        //去掉重复的学院名称
        var collegeNameArr = []
        for (var i = 0; i < $(".collegename").length; i++) {
            var sp = $(".collegename")[i].children
            if (sp.length > 1) {
                for (var a = 0; a < sp.length; a++) {
                    var str = sp[a].innerHTML
                    if (str.charAt(str.length - 1) == '、') {
                        str = str.split('、')[0]
                    }
                    if (collegeNameArr.indexOf(str) == -1 && str != "") {
                        collegeNameArr.push(str)
                    }
                }
                $(".collegename")[i].innerHTML = collegeNameArr.join("、")
            }
        }

    });

    //-----------------------------------------------------------------------
    function add1(schoolId) {

        $("#main_college").text("");
        $("#college").val("");
        $("#main_profession").text("");
        $("#major").val("");
        $("#main_class").text("");
        $("#className").val("");

        $.ajax("${base}/admin/student/CollegeAjax.jhtml",// 发送请求的URL字符串。
                {
                    type: "post", //  请求方式 POST或GET
                    // 发送到服务器的数据。
                    data: {
                        schoolId: schoolId
                    },
                    // 请求成功后的回调函数。
                    success: function (data) {


                    },

                    // 请求出错时调用的函数
                    error: function () {
                        alert("数据发送失败");
                    }
                });

    }
</script>
</body>
</html>
