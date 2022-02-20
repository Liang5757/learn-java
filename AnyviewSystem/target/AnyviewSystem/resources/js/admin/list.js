var storage = window.sessionStorage  //定义全局变量session
$(document).ready(function () {
    var layerFrame;
    bootbox.setDefaults({locale: framework.locale});   //识别  按钮转换为中文


    // 初始化复选框
     $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});
     // // 选择所有
     // $("input[name='checkAll']").on('ifChanged', function () {
     //     $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
     // });

      // 选择所有，复选框改变
    $("input[name='checkAll']").on('ifClicked', function () {
      //判断当前复选框是否被选中 选中则取消 反之亦然
      // $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');  
      // if ($(this).is(":checked")) {
      //     setCookie("selectAll",1)  //设置cookie标志
      // } else {
      //   if(getCookie("selectAll")){  //反选则删除cookie
      //     clearCookie("selectAll")
      //     clearCookie("selectArr")
      //     clearCookie("removeselectArr")
      //   }
      // }
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
          if(getCookie("selectArr")){
            arr = getCookie("selectArr").split(",")
          }
          if($.inArray($(this).val(),arr) == -1){  //去重
            arr.push($(this).val())
            setCookie("selectArr",arr.join(","))
          }
          //用户勾选复选框时,判断当前页面所有复选框是否都已勾选中，是则将全选设为勾选状态
          isSelectAll()
      } else {  //反选状态
        // if(getCookie("selectAll")){  //当用户点击了全选
        //   if(getCookie("removeselectArr")){   //查看用户是否有取消掉某个选项
        //     arr = getCookie("removeselectArr").split(",")
        //   }
        //   if($.inArray($(this).val(),arr) == -1){  //去重
        //     arr.push($(this).val())
        //     setCookie("removeselectArr",arr.join(","))   //将用户取消的选项发送给服务器端
        //   }
        // }else{
        //     if(getCookie("selectArr")){
        //         arr = getCookie("selectArr").split(",")
        //         if($.inArray($(this).val(),arr) != -1){   //查看数组中是否有存在此选项ID，有则删除此选项
        //             arr.splice($.inArray($(this).val(),arr),1)  //删除元素
        //         }
        //         setCookie("selectArr",arr.join(","))   //删除之后再重新设置Cookie
        //     }   
        // }
        //查看Cookie中是否已存有用户选中的ID
        if(getCookie("selectArr")){
          //取消勾选时，查看全选选项是否被选中，是则将其取消掉
          if($("input[name='checkAll']").is(":checked")){
            $("input[name='checkAll']").iCheck("uncheck")
          }
          //删除cookie中存在的此选项ID
          arr = getCookie("selectArr").split(",")
          if($.inArray($(this).val(),arr) != -1){   //查看数组中是否有存在此选项ID，有则删除此选项
            arr.splice($.inArray($(this).val(),arr),1)  //删除元素
          }
          setCookie("selectArr",arr.join(","))   //删除之后再重新设置Cookie
        }   
      }
  });

  // //判断是否为全选 是则所有选择框为选中状态
  // if(getCookie("selectAll")){
  //   $("input[name='checkAll']").iCheck("check")
  // }

  //判断是否为分页 不为分页则删除cookie  
  if (getCookie("selectArr") && location.href.indexOf("pageNumber") == -1) {
        // clearCookie("selectArr")
        // clearCookie("selectAll")
        // clearCookie("removeselectArr")  
        clearCookie("selectArr")
  }

    //判断当前的复选框是否被选中，是则为选中状态
  if(getCookie("selectArr")){
    var sp = getCookie("selectArr").split(",")
    for(var i = 0 ; i < $("input[name='ids']").length ; i++){
      for(var j = 0 ; j < sp.length;j++){
        if($("input[name='ids']")[i].value == sp[j]){
            $("#"+sp[j]).iCheck('check')
        }
      }
    }
    //获取cookie中的selectArr数组，遍历循环查看当前页面是否都被选中，是则将全选选项勾选中
    isSelectAll()

  }
  //判断是否为分页或者是在确定选择学校页面 否则则删除cookie (添加学校页面)
  if (getCookie("school_selectArr") && location.href.indexOf("admin/school/SchoolContent.jhtml") == -1 
    && location.href.indexOf("admin/school/sureSelectSchool.jhtm") == -1) {
      clearCookie("school_selectArr")
  }
  //判断是否为分页或者是在确定选择学校页面 否则则删除cookie  (修改学校页面)
  if (getCookie("edit_SchoolName") && location.href.indexOf("admin/school/editSchoolContent.jhtml") == -1 
    && location.href.indexOf("admin/school/sureEditSchool.jhtml") == -1) {
      clearCookie("edit_SchoolName")
  }
  //当不在学生管理的添加/修改页面时，删除cookie
  //学院cookie
  if (getCookie("college_selectArr") && location.href.indexOf("pageNumber") == -1) {
        clearCookie("college_selectArr")
        // clearCookie("college_selectAll")
  }
  //专业cookie
  if (getCookie("major_selectArr") && location.href.indexOf("pageNumber") == -1) {
        clearCookie("major_selectArr")
        // clearCookie("major_selectAll")
  }
  //班级cookie
  if (getCookie("class_selectArr") && location.href.indexOf("pageNumber") == -1) {
        clearCookie("class_selectArr")
        // clearCookie("class_selectAll")
  }

  //当不在课程编排的添加/修改页面时，删除cookie
  //课程编排班级cookie
  if (getCookie("course_class_selectArr") && location.href.indexOf("pageNumber") == -1) {
        clearCookie("course_class_selectArr")
        // clearCookie("course_class_selectAll")
  }
  //作业表cookie
  if (getCookie("workingtable_selectArr") && location.href.indexOf("pageNumber") == -1) {
        clearCookie("workingtable_selectArr")
        // clearCookie("workingtable_selectAll")
  }




    //跳转
    var $listForm = $("#listForm");

    // 页码跳转
    $.pageSkip = function (pageNumber) {
        $("#pageNumber").val(pageNumber);
        if (typeof listTemplate == 'undefined') {
            $listForm.submit();
        } else {
            loadTemplate();
        }
        //return false;
    }


    // 删除多条记录
    $(".btn-delete-loippi-group").click(function () {
        // var $checkeds = jQuery($("input[name='ids']:checked"));

        // if ($checkeds.length <= 0) {
        //     art.warn('请选择删除记录！');
        //     return;
        // }

        // bootbox.confirm(message("admin.dialog.deleteConfirm"), function (result) {
        //     if (result) {
        //         $.ajax({
        //             url: "delete.jhtml",
        //             type: "POST",
        //             data: $checkeds.serialize(),
        //             dataType: "json",
        //             cache: false,
        //             success: function (message) {
        //                 art.message(message);
        //                 if (message.type == "success") {
        //                     window.setTimeout(function () {
        //                         if (typeof listTemplate == 'undefined') {
        //                             $listForm.submit();
        //                         } else {
        //                             loadTemplate();
        //                         }
        //                     }, 1000);
        //                 }
        //             }
        //         });
        //     }
        // });
        if(getCookie("selectArr")){
          if (getCookie("selectArr").split(",").length <= 0) {
            art.warn('请选择删除记录！');
            return;
          }
          //当用户选择删除记录，弹出对话框，确认是否要删除
          bootbox.confirm(message("admin.dialog.deleteConfirm"), function (result) {
            //当用户点击确认删除记录时，发送用户已勾选的记录ID给服务器端
            if (result) {
                $.ajax({
                    url: "delete.jhtml",
                    type: "POST",
                    data: {ids:getCookie("selectArr")},
                    dataType: "json",
                    cache: false,
                    success: function (message) {
                        art.message(message);
                        if (message.type == "success") {
                            window.setTimeout(function () {
                                if (typeof listTemplate == 'undefined') {
                                    $listForm.submit();
                                } else {
                                    loadTemplate();
                                }
                            }, 1000);
                        }
                    }
                });
            }
          });
        }else{
          art.warn('请选择删除记录！');
          return;
        }
    });

    //联想搜索
    // $("#schoolName").autocomplete({
    //   source:function (requeset,response) {
    //       $.ajax({
    //         type:"GET",
    //         url:"../AdminSchool.jhtml",
    //         data:{
    //           "term":$("#schoolName").val()
    //         },
    //         success:function (data) {
    //           if(data.length >= 1){
    //               var schoolNames=[]
    //               data.forEach(function(schoolObj){
    //                  schoolNames.push({label:schoolObj.schoolName,value:schoolObj.schoolName,id:schoolObj.id})
    //               })
    //               response(schoolNames)
    //           }else{
    //             response(["学校不存在"])
    //           }
    //         }
    //       })
    //
    //   },
    //   select:function (event, ui) {
    //     $("#schoolName").val(ui.item.label)
    //     $("#schoolId").val(ui.item.id)
    //
    //     //验证是否有add1函数，有则调用函数 触发联动
    //     try{
    //       if(add1 && typeof(add1) == "function"){
    //           add1($("#schoolId").val())
    //       }
    //     }catch(e){}
    //
    //   }
    // })

    //联想输入学校
    $("#schoolName").select2({
      placeholder: '请选择学校',
      ajax: {
        type:"get",
        url: "../AdminSchool.jhtml",
        data: function (params) {
          return {
            term: params.term,
          };
        },
        processResults: function (data) {
          var schools = []
          if(data.length>0){
            data.forEach(schoolObj=>{
                schools.push({id:schoolObj.id,text:schoolObj.schoolName})
            })
          }
          return {
            results: schools
          };
        },
        cache: true
      },
      escapeMarkup: function (markup) { return markup; }
      });

    //选择作业表开始时间
    $('#tableStartTime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose : true,
        language:"zh-CN"
    }).on('changeDate',function(ev){   //开始时间变化时，设置结束时间不超过开始时间
        var tableStartTime=$("#tableStartTime").val();
        $("#tableStopTime").datetimepicker('setStartDate',tableStartTime);
        $("#tableStartTime").datetimepicker('hide');
    });
    //选择作业表结束时间
    $('#tableStopTime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose : true,
        language:"zh-CN"

    }).on('changeDate',function(ev){   //结束时间变化时，设置开始时间不可超过结束时间
        var tableStartTime=$("#tableStartTime").val();
        var tableStopTime=$("#tableStopTime").val();
        $("#tableStartTime").datetimepicker('setEndDate',tableStopTime);
        $("#tableStopTime").datetimepicker('hide');
    })

    //选择考试编排开始时间
    $('#startTime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose : true,
        language:"zh-CN",
        startDate:new Date()
    });

    //当用户不在添加题目中，存在着checkContentFlag 则将其删除
    // if(window.location.href.indexOf("admin/question/add.jhtml") == -1 && storage.getItem("checkContentFlag") != null){
    //     storage.removeItem("checkContentFlag")
    // }
    if (window.location.href.indexOf("admin/question/editContent.jhtml") == -1 &&
              window.location.href.indexOf("admin/question/importContent.jhtml") == -1) {
        storage.removeItem("question_description")
        storage.removeItem("standard_answer")
        storage.removeItem("student_answer")
        storage.removeItem("headerfile_name")
        storage.removeItem("headerfile_content")
        storage.removeItem("headerFileArr")
        storage.removeItem("headerFileContentArr")
        storage.removeItem("ContentFlag")

    }
    if (window.location.href.indexOf("admin/question/checkContent.jhtml") == -1 &&
            window.location.href.indexOf("admin/question/editContent.jhtml") == -1 &&
              window.location.href.indexOf("admin/question/importContent.jhtml") == -1) {
        storage.removeItem("check_question_description")
        storage.removeItem("check_standard_answer")
        storage.removeItem("check_student_answer")
        storage.removeItem("check_headerfile_name")
        storage.removeItem("check_headerfile_content")
        storage.removeItem("check_headerFileArr")
        storage.removeItem("check_headerFileContentArr")
        storage.removeItem("ContentFlag")
        // storage.removeItem("isEdit")

    }
    // if (window.location.href.indexOf("admin/school/managerSchoolContent.jhtml") == -1) {
    //     storage.removeItem("tt_flag")
    // }

    // console.log(window.location.href);


})

//初始化复选框
// function initCheck(){
//
//     // 复选框
//     // $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-green', radioClass: 'iradio_square-green',});
//     $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});
//     // 选择所有
//     $("input[name='checkAll']").on('ifChanged', function () {
//         $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
//     });
//
// }

//loading图标
function loadTemplate() {
    var index = layer.load(1, {
        shade: [0.1, '#fff'] //0.1透明度的白色背景
    });
    var data = $("#listForm").serialize();   //表单序列化
    $.get(listTemplate, data, function (result, status) {   //发送get请求 重新渲染
        $("#listForm .dataTables_wrapper").empty();
        $("#listForm .dataTables_wrapper").html(result);
        layer.close(index);   //关闭loading图标
        initCheck();    //初始化复选框
    }, "html");
}

function showRight(){
    var rightPanel = $("#right-panel");
    var morePanel = $("#more-panel");
    if(rightPanel.is(":hidden")){
        rightPanel.show();
        morePanel.empty();
    }
}

function showMore(htmlObj){
    var rightPanel = $("#right-panel");
    var morePanel = $("#more-panel");
    if(!rightPanel.is(":hidden")){
        rightPanel.hide();
    }
    morePanel.empty();
    morePanel.append(htmlObj);
}


//取得cookie
  function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';'); //把cookie分割成组
    for(var i = 0;i < ca.length;i++) {
      var c = ca[i]; //取得字符串
      while (c.charAt(0)==' ') { //判断一下字符串有没有前导空格
        c = c.substring(1,c.length); //有的话，从第二位开始取
      }
      if (c.indexOf(nameEQ) == 0) { //如果含有我们要的name
      return unescape(c.substring(nameEQ.length,c.length)); //解码并截取我们要值
      }
    }
    return false;
  }
//清除cookie
  function clearCookie(name) {
    setCookie(name, "", -1);
  }
//设置cookie
  function setCookie(name, value, seconds) {
    seconds = seconds || 0; //seconds有值就直接赋值，没有为0。
    var expires = "";
    if (seconds != 0 ) { //设置cookie生存时间
      var date = new Date();
      date.setTime(date.getTime()+(seconds*1000));
      expires = "; expires="+date.toGMTString();
    }
    document.cookie = name+"="+escape(value)+expires+"; path=/"; //转码并赋值
  }

  //判断是否为全选状态
  function isSelectAll() {
    for(var a = 0 ; a < $("input[name='ids']").length ; a++){
      //有一个选项没被选中则跳出循环，全选不为勾选中的状态
      if($($("input[name='ids']")[a]).is(":checked") == false){
        break      
      }
      //循环结束，当前页面全部复选框都被勾选中，则全选为勾选状态
      if(a >= $("input[name='ids']").length-1){
        $("input[name='checkAll']").iCheck("check")
      }
    }
  }

  //点击查询按钮时，将pageNumber设置为1
  function changePageNum() {
    // console.log($("#pageNumber").val())
    $("#pageNumber").val("1")
  }