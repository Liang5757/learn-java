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
          <div class="ibox-title">
                        <h5>选择学校</h5>
                        <div class="ibox-tools">
                            <!-- <button type="button" class="btn btn-white" onclick="back()"
                                    style="margin-top: -8px;">返回
                            </button> -->
                            <a class="btn btn-white" href="${base}/admin/school/add.jhtml" style="margin-top: -8px;">返回</a>
                        </div>
            </div>
            <div class="ibox float-e-margins">
                <form id="listForm" action="${base}/admin/school/SchoolContent.jhtml">
                    <div class="ibox-content">
                        <div class="row">

                            <div class="col-sm-3 m-b-xs"><input type="text" id="filter_schoolName" placeholder="请输入学校名称" name="filter_schoolName" value="${(params.schoolName)!''}" class="form-control" style="height:37px;">
                            </div>
                        	 <div class="col-sm-3 m-b-xs"><select class="form-control" id="filter_schoolAddress" name="filter_schoolAddress" style="height:37px;">
			                          	[#if params.schoolAddress??]
			                          		 <option value="${(params.schoolAddress)!''}">${(params.schoolAddress)!''}</option>
								        [#else]
			                          		<option value="">全部省份</option>
			                          	[/#if]
			                           [#list schoolAddress as address]
								        	<option value="${address}">${address}</option>
								       	[/#list]
								       </select>
                            </div>
                            <div class="col-sm-1">
                                <div class="input-group">
                                    <button type="submit" class="btn btn-sm btn-danger" onclick="changePageNum()"> 查询</button> </span>

                                </div>
                            </div>
                        </div>
                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>

                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
	                                   <td>序号</td>
	                                   <td>学校</td>
	                                   <td>所在省份</td>
                                </tr>
                                </thead>
                                <tbody>
                                [#list page.content as school]
                                <tr>
                                    <td>
                               			  <input type="checkbox" class="i-checks" name="ids" value="${school.id}" id="${school.id}">
                                    </td>
										                <td>${school_index + 1}</td>
										                <td class="allSchoolName">${school.schoolName}</td>
										                <td>${school.schoolAddress}</td>
                                </tr>
                                [/#list]
                                </tbody>
                            </table>
                            <!-- 分页 使用了自定义指令  通过#macro设置 -->
                            <!-- pageNumber:当前页 -->
                            <!-- totalPages：总页数 -->
                            [@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
                            [#include "/admin/include/pagination.ftl"]
                            [/@pagination]

                        </div>
                        <div class="ibox-tools" style="height:50px;">
                        <input type="button" class="btn btn-danger" id="submitBtn"
                                style="margin-top: -20px;" value="确定">
                       </div>
                    </div>
                    <!-- <input type="text" name="selectAll" id="selectAll" class="hidden"> -->
                </form>
                <div id="recordSystemSchool"  class="hidden">
                  [#list recordSystemSchool as schoolId]
                      <span>${schoolId}</span>
                  [/#list]
                </div>
                <div id="systemSchoolNameList"  class="hidden">
                  [#list systemSchoolNameList as schoolName]
                      <span>${schoolName}</span>
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
	var submitBtn = document.getElementById('submitBtn')
	submitBtn.onclick = function () {


   //  var $checkeds = $("input[name='ids']:checked");
   //  var $inp = $("input[name='ids']");
   //  var $schoolId = $("#recordSystemSchool").children()
   //  var idArr = []
   //  var selectIds = ''
   //  //获取勾选的学院id与名称
   //  if($schoolId.length > 0 || $checkeds.length > 0){
   //    for (var i = 0; i < $schoolId.length; i++) {
   //        if (i != 0) {
   //            selectIds += ",";
   //        }
   //        selectIds += $schoolId[i].innerHTML;
   //    }

   //    if(selectIds!=""){
   //      idArr = selectIds.split(',')
   //    }

   //    //没有翻页时，手动加入id与名称
   //    for(var p = 0 ; p < $checkeds.length;p++){
   //      if(!$($checkeds[p]).prop("disabled")){
   //        //$.inArray : jquery判断数组中是否包含某个元素 没有则返回-1 有则返回元素下标值
   //        if($.inArray($($checkeds[p]).val(),idArr) == -1){
   //          idArr.push($($checkeds[p]).val())
   //        }
   //      }
   //    }
   //    //取消选择
   //    for(var q = 0 ; q < $inp.length;q++){
   //      //判断当前页面的复选框是否有存在于id数组中，且不为选中状态
   //      if($.inArray($($inp[q]).val(),idArr) != -1 && $($inp[q]).is(":checked") == false){
   //        idArr.splice($.inArray($($inp[q]).val(),idArr),1)
   //      }
   //    }

  	// }
   //    //var $schoolId = $("#recordSystemSchool").children()
   //  if(idArr.length>0){
   //    document.location.href="${base}/admin/school/sureSelectSchool.jhtml"
   //  }else{
   //    art.warn("请至少勾选一条数据");
   //    return false;
   //  }

   //将用户所勾选的id传给后端
   // if(getCookie("selectArr")){
   //    var ids = getCookie("selectArr")
   //    clearCookie("selectArr")  
   //    if(getCookie("selectAll")){   //查看是否为全选
   //      $("#selectAll").val(getCookie("selectAll"))
   //    }
   //    document.location.href="${base}/admin/school/sureSelectSchool.jhtml?schoolIds="+ids
   // }else{
   //    art.warn("请至少勾选一条数据");
   //    return false;
   // }
   //将用户所勾选的id传给服务器端
   console.log(getCookie("school_selectArr"))
   if(getCookie("school_selectArr")){
      var school_selectArr = getCookie("school_selectArr").split(",")
      var ids = []
      // clearCookie("school_selectArr") 
      for(var i = 0; i < school_selectArr.length; i++){
         if(!($("#"+school_selectArr[i]).attr("disabled"))){
            ids.push(school_selectArr[i])
         }
      }
      // console.log(ids)
      location.href="${base}/admin/school/sureSelectSchool.jhtml?schoolIds="+ids
   }else{
      art.warn("请至少勾选一条数据");
      return false;
   }
}
$(document).ready(function () {


  // // 选择所有，复选框改变
  // $("input[name='checkAll']").on('ifChanged', function () {
  //     $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
  //     if ($(this).is(":checked")) {
  //         $.ajax({
  //           type:"post",
  //           url:"recordSystemSchool.jhtml",
  //           data:{
  //               flag:2
  //           }
  //         })

  //     } else {
  //       $.ajax({
  //         type:"post",
  //         url:"recordSystemSchool.jhtml",
  //         data:{
  //             flag:3
  //         }
  //       })
  //     }
  // });

  
  // //选择单个,点击事件
  // $("input[name='ids']").on('ifChanged', function () {
  //     var arr = []
  //     //选中状态
  //     if ($(this).is(":checked")) {
  //         $.ajax({
  //           type:"post",
  //           url:"recordSystemSchool.jhtml",
  //           data:{
  //               schoolID:$(this).val(),
  //               flag:1
  //           }

  //         })

  //     } else {
  //       //取消选中
  //       $.ajax({
  //         type:"post",
  //         url:"recordSystemSchool.jhtml",
  //         data:{
  //             schoolID:$(this).val(),
  //             flag:0
  //         }
  //       })
      
  //     }
  // });
  // //判断当前的复选框是否被选中，是则为选中状态
  // var sp = $("#recordSystemSchool").children()
  // for(var i = 0 ; i < $("input[name='ids']").length ; i++){
  //  for(var j = 0 ; j < sp.length;j++){
  //       if($("input[name='ids']")[i].value == sp[j].innerHTML){
  //           $("#"+sp[j].innerHTML).iCheck('check')
  //       }
  //   }
  // }
  //已存在的学校 设置不可勾选 并且背景为灰色
  var schoolNames = $("#systemSchoolNameList").children()  //已存在的学校名称
  var allSchoolNames =  $(".allSchoolName")               //全部的学校
  for (var i = 0; i < schoolNames.length; i++) {
      for (var j = 0; j < allSchoolNames.length; j++) {
          if(allSchoolNames[j].innerHTML == schoolNames[i].innerHTML){
              var id = allSchoolNames[j].parentNode.children[0].children[0].children[0].id
              $("#"+id).iCheck('check')
              $("#"+id).iCheck('disable')
              allSchoolNames[j].parentNode.style.backgroundColor="#e7eaec"
          }
      }
  }
    //初始化复选框
    $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});
      // 选择所有，复选框改变
    $("input[name='checkAll']").on('ifClicked', function () {
      //判断用户是否勾选了全选选项，是则将当前页面的复选框全部选中，并将其ID存入Cookie中
      if ($(this).is(":checked")) {
         for (var i = 0; i < $("input[name='ids']").length; i++) {
           if($($("input[name='ids']")[i]).attr("disabled") != "disabled"){
              $($("input[name='ids']")[i]).iCheck('uncheck'); 
           }
         }
      } else {
        for (var j = 0; j < $("input[name='ids']").length; j++) {
           if($($("input[name='ids']")[j]).attr("disabled") != "disabled"){
              $($("input[name='ids']")[j]).iCheck('check'); //用户取消全选，则将Cookie中存放的SelectArr数组清除
           }
        }
      }
    });



  //选择单个,点击事件
  $("input[name='ids']").on('ifChanged', function () {
      var arr = []
      console.log(getCookie("school_selectArr"))
      //选中状态
      if ($(this).is(":checked") && $(this).attr("disabled") != "disabled") {
          //当Cookie中已经存有存放ID的数组，则在此基础上增加ID
          if(getCookie("school_selectArr")){
            arr = getCookie("school_selectArr").split(",")
          }
          if($.inArray($(this).val(),arr) == -1 && $(this).attr("disabled") != "disabled"){  //查看数组中是否不存在此选项ID并且不为禁止选择项，有则添加此选项到Cookie中

            arr.push($(this).val())
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
          if($.inArray($(this).val(),arr) != -1 && $(this).attr("disabled") != "disabled"){   //查看数组中是否有存在此选项ID并且不为禁止选择项，有则删除此选项
            arr.splice($.inArray($(this).val(),arr),1)  //删除元素
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
        if($("input[name='ids']")[i].value == sp[j]){
            $("#"+sp[j]).iCheck('check')
        }
      }
    }
    //获取cookie中的school_selectArr数组，遍历循环查看当前页面是否都被选中，是则将全选选项勾选中
    isSelectAll()
  }
})

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

//返回
// function back() {
//   // clearCookie("selectArr")
//   // clearCookie("selectAll")
//   document.location.href = "${base}/admin/school/add.jhtml"
// }
</script>
</body>

</html>
