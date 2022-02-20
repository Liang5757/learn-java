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
                        <!--  <a class="btn btn-white" href="${base}/admin/question_bank/list.jhtml" style="margin-top: -8px;">返回</a>-->
                    </div>

                    <h5>添加题库</h5>
                </div>
                <div class="ibox-content">
                    <form id="saveQuestionbankForm" class="form-horizontal" action="save.jhtml" method="post">
                        <div class="form-group" >
                        	<div class="row" >
                                <div class="col-sm-6" style="display:none">
                                    <label class="col-sm-3 control-label">题库ID<span
                                            class="required"> * </span></label>

                                    <div class="col-sm-9">
                                        <input type="text" name="questionBankId" id="questionBankId" class="form-control" maxlength="200"
                                               readonly="readonly"/>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">课程<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <select name="course_name" id="course_name"
                                              class="bs-select form-control" >
	                                         <option hassubinfo="true" value="">请选择课程</option>
	                                         [#list courseList as course]
	                                            <option value="${course.courseName}">${course.courseName}</option>
	                                      	 [/#list]
	                                      </select>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6 vaild">
                                    <label class="col-sm-3 control-label">题库<span
                                            class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <input type="text" name="question_bank" placeholder="请输入题库名称" id="question_bank" class="form-control"
                                               maxlength="200"/>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">公开级别<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <select name="public_level" id="public_level"
                                                class="bs-select form-control">
                                        <option value="">请选择公开级别</option>
                                        <option value="0">完全保密</option>
										[#if systemUser.roleId=-1]
                                        <option value="1">公开给特定学校</option>
                                        [/#if]
                                        [#if systemUser.roleId=1 || systemUser.roleId=0]
                                        <option value="3">本校公开</option>
                                        [/#if]
                                        <option value="2">完全公开</option>
                                        
                                        </select>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row" id="specific" style="display:none;">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">特定学校<span
                                            class="required"> * </span>:</label>
                                    <div class="col-sm-9" id="selectSchoolName">
                                        <button type="button" class="btn btn-sm btn-primary" name="bt-select-school" id="bt-select-school"> 选择
                                        </button>
                                        <!-- 添加学校名称及ID处 -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--/row-->

                        <!-- <div class="hr-line-dashed"></div> -->
                        <div class="form-group">
                            <div class="col-sm-12 col-sm-offset-2">
                                <button type="submit"  class="btn btn-danger">确定</button>
                            </div>
                        </div>
                </form>
            </div>
        </div>
    </div>
</div>
</div>
<!-- BEGIN SchoolDialog -->
<div class="modal inmodal" id="selectSchool" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">选择学校</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <div class="row">
                        <div class="dataTables_wrapper form-inline dialog-table">

                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-danger ok-set-school">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- END SchoolDialog -->


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

        	//-----------------------------选择特定学校---------------------------------
            $("#bt-select-school").on("click",function () {
            var data = {};
            var index = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            //获取id
            $.get("${base}/admin/question_bank/listDialogSchool.jhtml", data, function (result, status) {
                      var tableCon = $(".dialog-table");
                      tableCon.empty();
                      tableCon.append(result);
                      layer.close(index);
                      $('#selectSchool').modal('show');

                      // // 复选框
                      // $('.dialog-table .i-checks').iCheck({
                      //     checkboxClass: 'icheckbox_square-red',
                      //     radioClass: 'iradio_square-red',
                      // });


                      // // 选择所有，复选框改变
                      // $(".dialog-table input[name='checkAll']").on('ifChanged', function () {
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
                      // $(".dialog-table input[name='ids']").on('ifClicked', function () {
                      //     //选中状态
                      //     if ($(this).is(":checked") == false) {
                      //         $.ajax({
                      //           type:"post",
                      //           url:"recordSchool.jhtml",
                      //           data:{
                      //               schoolID:$(this).val(),
                      //               flag:1
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
                      //         }
                      //       })
                      //     }
                      // });

                      // //判断当前的复选框是否被选中，是则为选中状态
                      // var sp = $("#recordSchoolId").children()
                      // for(var i = 0 ; i < $(".dialog-table input[name='ids']").length ; i++){
                      //  for(var j = 0 ; j < sp.length;j++){
                      //       if($(".dialog-table input[name='ids']")[i].value == sp[j].innerHTML){
                      //           //console.log($("#"+sp[j].innerHTML))
                      //           $("#"+sp[j].innerHTML).iCheck('check')

                      //       }
                      //   }
                      // }
                // 初始化复选框
                $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});

                // 选择所有，复选框改变
                $("input[name='checkAll']").on('ifClicked', function () {
                  //判断用户是否勾选了全选选项，是则将当前页面的复选框全部选中，并将其ID存入Cookie中
                  if ($(this).is(":checked")) {
                     $("input[name='ids']").iCheck('uncheck'); 
                  } else {
                    $("input[name='ids']").iCheck('check');   //用户取消全选，则将Cookie中存放的SelectArr数组清除
                  }
                });

  
                //选择单个,点击事件
                $("input[name='ids']").on('ifChanged', function () {
                      var arr = []
                      //选中状态
                    if ($(this).is(":checked")) {
                          //当Cookie中已经存有存放ID的数组，则在此基础上增加ID
                          if(getCookie("school_selectArr")){
                            arr = getCookie("school_selectArr").split(",")
                          }

                          var str = $(this).val()+":"+$(this).attr("data-name")  //将用户所选择的ID及学校名称存到cookie中

                          if($.inArray(str,arr) == -1){  //去重
                            // console.log($(this).attr("data-name"))
                            arr.push(str)
                            setCookie("school_selectArr",arr.join(","))
                          }
                          //用户勾选复选框时,判断当前页面所有复选框是否都已勾选中，是则将全选设为勾选状态
                          isSelectAll()
                    } else {  //反选状态
                        //查看Cookie中是否已存有用户选中的ID
                        if(getCookie("school_selectArr")){
                          //取消勾选时，查看全选选项是否被选中，是则将其取消掉
                          if($("input[name='checkAll']").is(":checked")){
                            $("input[name='checkAll']").iCheck("uncheck")
                          }
                          //删除cookie中存在的此选项ID
                          arr = getCookie("school_selectArr").split(",")
                          var str = $(this).val()+":"+$(this).attr("data-name")  //将用户所选择的ID及学校名称从cookie中移除

                          if($.inArray(str,arr) != -1){   //查看数组中是否有存在此选项ID，有则删除此选项
                            arr.splice($.inArray(str,arr),1)  //删除元素
                          }
                          setCookie("school_selectArr",arr.join(","))   //删除之后再重新设置Cookie
                        }   
                    }
                });

                //判断当前的复选框是否被选中，是则为选中状态
                if(getCookie("school_selectArr")){
                    var sp = getCookie("school_selectArr").split(",")
                    for(var i = 0 ; i < $("input[name='ids']").length ; i++){
                      for(var j = 0 ; j < sp.length;j++){
                        var id = sp[j].split(":")[0]
                        if($("input[name='ids']")[i].value == id){
                            $("#"+id).iCheck('check')
                        }
                      }
                    }
                    //获取cookie中的selectArr数组，遍历循环查看当前页面是否都被选中，是则将全选选项勾选中
                    isSelectAll()
                }
            }, "html");
        });

        $(".ok-set-school").click(function () {
          // var $checkeds = $(".dialog-table input[name='ids']:checked");
          // var $inp = $(".dialog-table input[name='ids']");
          // var $SchoolId = $("#recordSchoolId").children()
          // var $SchoolName = $("#recordSchoolName").children()
          // var idArr = [],nameArr = []
          // var selectIds = '', selectNames = '';
          // //获取勾选的学院id与名称
          // if($SchoolId.length > 0 || $checkeds.length > 0){
          //   for (var i = 0; i < $SchoolId.length; i++) {
          //       if (i != 0) {
          //           selectIds += ",";
          //       }
          //       selectIds += $SchoolId[i].innerHTML;
          //   }
          //   for (var j = 0; j < $SchoolName.length; j++) {
          //       if (j != 0) {
          //           selectNames += "、";
          //       }
          //       selectNames += $SchoolName[j].innerHTML
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
          //   //将所选择的名字显示到界面上
          //   if(nameArr.length<=3){
          //     $("#main_school").text(nameArr.join("、"));
          //   }else{
          //     var name = nameArr.slice(0,3)
          //     $("#main_school").text(name.join('、')+"、...");
          //   }

          //   $("#specific_school").val(idArr.join(","));
          //   $('#selectSchool').modal('hide');
          // }else{
          //   art.alert("请选择一个学校")
          // }
            if(getCookie("school_selectArr")){
                    var school_selectArr = getCookie("school_selectArr").split(","),
                        idArr = []
                        nameArr = []
                    //获取用户所勾选的ID及学院名称
                    for (var d = 0; d < school_selectArr.length; d++) {
                        idArr.push(school_selectArr[d].split(":")[0])
                        nameArr.push(school_selectArr[d].split(":")[1])
                    }
                    if(nameArr.length<=3){
                        $("#main_school").text(nameArr.join("、"));
                    }else{
                        var name = nameArr.slice(0,3)
                        $("#main_school").text(name.join('、')+"、...");
                    }
                    $("#specific_school").val(idArr.join(","));
                    $('#selectSchool').modal('hide');
            }else{
                alert("请选择一个学校")
            }
        });
        //------------------------------------------------------------------------

        $("#saveQuestionbankForm").validate({
            submitHandler:function (form) {   //不使用submit默认提交功能，先进行自定义验证，成功则提交表单
                $.ajax({
                  url: "${base}/admin/question_bank/checkBank.jhtml",
                  type: "GET",
                  data: {
                    id: $("#questionBankId").val(),
                    bankName: $("#question_bank").val(),
                    courseName: $("#course_name").val()
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
            onfocusout: function(element) { $(element).valid(); },
            onkeyup: function(element) { $(element).valid(); },
            rules: {
                question_bank: {
                    required: true,
                   //  remote: {
                   //      url: "${base}/admin/question_bank/checkBank.jhtml",
                   //      cache: false,
                   //      type: "GET",
                   //      data: {
                   //      	id: function () {
                   //              return $("#questionBankId").val();
                   //      	},
                   //      	bankName: function () {
                   //              return $("#question_bank").val();
                   //      	},
                   //        courseName: function () {
                   //              return $("#course_name").val();
                   //        }
                   //  	  }
                  	// }
                    // remote: function () {
                    //   if($("#course_name").val()){
                    //     return {
                    //       url: "${base}/admin/question_bank/checkBank.jhtml",
                    //       cache: false,
                    //       type: "GET",
                    //       data: {
                    //         id: $("#questionBankId").val(),
                    //         bankName: $("#question_bank").val(),
                    //         courseName: $("#course_name").val()
                    //       }
                    //     }
                    //   }
                    // }
                },
                course_name: {
                    required: true
                },
                public_level: {
                    required: true
                },
                specific_school: {
                    required: true
                }
            },
            messages: {
                question_bank: {
                    pattern: "${message("admin.validate.illegal")}",
                    remote: "${message("admin.validate.exist")}"
                }
            }
        });
    });


  //显示或者隐藏特定学校选择按钮
  $("#public_level").change(function(){
  	if($("#public_level").val() == "1"){
  		$("#specific").css("display","")
      $("#selectSchoolName").append('<div style="display:inline-block" id="schools"><input type="hidden" name="specific_school" id="specific_school"/><span id="main_school"></span></div>')
  	}else{
		  $("#specific").css("display","none")
      $("#selectSchoolName").children("#schools").remove()
  	 }
  })


</script>
</body>
</html>
