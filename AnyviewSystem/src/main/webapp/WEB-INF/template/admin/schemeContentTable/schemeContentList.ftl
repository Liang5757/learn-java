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
              <div class="ibox-tools">
                  <button class="btn btn-white" type="button" onclick="back()" style="margin-top: -8px;">返回</button>
              </div>
              <h5>题目管理</h5>
           </div>
                <form id="listForm" action="schemeContent.jhtml" method="get">
                    <div class="ibox-content">

                    <div class="row">
                            <div class="col-sm-3 m-b-xs">
                                <input type="text" placeholder="请输入题目名称" name="filter_VPName" value="${(params.VPName)!''}"  class="input-sm form-control">

                          		 <input type="hidden" placeholder="请输入题目名称" name="filter_VID"  value="${VID}"/>
                            </div>
                     		<div class="col-sm-1">
                                <div class="input-group">
                                    <button type="submit" class="btn btn-sm btn-danger" onclick="changePageNum()">查询</button> </span>
                                </div>
                            </div>
                    </div>
                        <div>

                            <a class="btn btn-outline btn-success btn-xs" id="btn-add-loippi" href="add.jhtml"><i
                                    class="fa fa-plus"></i>添加</a>
                           	<a class="btn btn-outline btn-danger btn-xs" id="deleteRecord"><i
                                    class="fa fa-trash"></i>删除</a>
                            <a class="btn btn-outline btn-info btn-xs" id="setting_time">设置开始/结束时间</a>

                        </div>

                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
                                    <th>序号</th>
                                    <th>题目名称</th>
                                    <th><a href="schemeContent.jhtml?VChapName_flag=[#if VChapName_flag == 'asc' || VChapName_flag == null]desc[#elseif VChapName_flag == 'desc']asc[/#if]" style="color:#666">章节
                                      <em class="fs-up"><i class="[#if VChapName_flag == 'desc' || VChapName_flag == null]arrow-up[#elseif VChapName_flag == 'asc']selected-up[/#if]"></i><i class="[#if VChapName_flag == 'asc' || VChapName_flag == null]arrow-down[#elseif VChapName_flag == 'desc']selected-down[/#if]"></i></em></a></th>
                                    <th>题目描述</th>
                                    <th><a href="schemeContent.jhtml?difficulty_flag=[#if difficulty_flag == 'asc' || difficulty_flag == null]desc[#elseif difficulty_flag == 'desc']asc[/#if]" style="color:#666">难度<em class="fs-up"><i class="[#if difficulty_flag == 'desc' || difficulty_flag == null]arrow-up[#elseif difficulty_flag == 'asc']selected-up[/#if]"></i><i class="[#if difficulty_flag == 'asc' || difficulty_flag == null]arrow-down[#elseif difficulty_flag == 'desc']selected-down[/#if]"></i></em></a></th>
                                    <th>开始时间</th>
                                    <th>结束时间</th>
                                </tr>
                                </thead>

                               <tbody>
                                [#list page as schemeContent]
                                <tr>
                                    <td>
                                        <input type="checkbox" class="i-checks" name="ids" id="${schemeContent.ID}" value="${schemeContent.ID}">
                                    </td>
                                    <td>${schemeContent_index + 1}</td>
                                    <td>${schemeContent.VPName}</td>
                                    <td>${schemeContent.question.chapter}</td>
                                    <td>[#if questionContentList[schemeContent_index].question_description!= "" && questionContentList[schemeContent_index].question_description?length gt 29]<div style="display: inline-block;width: 200px;" title="${questionContentList[schemeContent_index].question_description?html}">${questionContentList[schemeContent_index].question_description?substring(0,30)}...</div>[#else]${questionContentList[schemeContent_index].question_description}[/#if]</td>
                                    <td>${schemeContent.difficulty}</td>
                                    <td>${(schemeContent.startTime?string('yyyy-MM-dd HH:mm:SS'))!}</td>
                                    <td>${(schemeContent.finishTime?string('yyyy-MM-dd HH:mm:SS'))!}</td>
                                </tr>
                                [/#list]
                                </tbody>
                            </table>
                           
                        </div>
                    </div>
                </form>
                <div id="recordschemeContent" class="hidden">
                  [#list recordschemeContent as schemeContent]
                      <span>${schemeContent}</span>
                  [/#list]
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal inmodal" id="settingDialog" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h1 class="modal-title">设置开始/结束时间</h1>
                
			<form action="${base}/admin/schemeContentTable/settingTime.jhtml" onsubmit="addIds()">
   				开始时间：<input  name="startTime" id="tableStartTime"/> <br>
   				结束时间：<input  name="stopTime" id="tableStopTime"/>
            <div class="modal-footer">
             	<button type="submit" class="btn btn-danger ok-set-college" >确定</button>
                <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
               
            </div>
            <input name="idsList" id="idsList" class="hidden">
            </form>
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
        
       
          
      
    $("#setting_time").click(function(){
         var $checkeds = jQuery($("input[name='ids']:checked"));
        	if($checkeds.length == 0){
                art.warn("至少选择一条记录进行设置");
                return;
            }
            var data = {};
            var index = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            layer.close(index);
             $('#settingDialog').modal('show');
            
        });

        // 初始化复选框
        $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red'});
       	// 选择所有
     	  $("input[name='checkAll']").on('ifClicked', function () {
         $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'uncheck' : 'check');
     	  });
        //单击,判断是否全选
        $("input[name='ids']").on('ifChanged', function () {
            //判断当前页面的复选框是否都被选中，选中则勾选全选按钮，否则取消勾选全选
            if($(this).is(":checked")){
              isSelectAll()
            }else{
              if($("input[name='checkAll']").is(":checked")){
                $("input[name='checkAll']").iCheck("uncheck")
              }
            }
        });

         // 删除多条记录
    $("#deleteRecord").click(function () {
        var $checkeds = jQuery($("input[name='ids']:checked"));
        if ($checkeds.length <= 0) {
            art.warn('请选择删除记录！');
            return;
        }
        bootbox.confirm(message("admin.dialog.deleteConfirm"), function (result) {
            if (result) {
                $.ajax({
                    url: "delete.jhtml",
                    type: "POST",
                    data: $checkeds.serialize(),
                    dataType: "json",
                    cache: false,
                    success: function (message) {
                        art.message(message);
                        if (message.type == "success") {
                            window.setTimeout(function () {
                                if (typeof listTemplate == 'undefined') {
                                    $("#listForm").submit();
                                } else {
                                    loadTemplate();
                                }
                            }, 1000);
                        }
                    }
                });
            }
        });
        // if(getCookie("selectArr")){
        //   if (getCookie("selectArr").split(",").length <= 0) {
        //     art.warn('请选择删除记录！');
        //     return;
        //   }
        //   //当用户选择删除记录，弹出对话框，确认是否要删除
        //   bootbox.confirm(message("admin.dialog.deleteConfirm"), function (result) {
        //     //当用户点击确认删除记录时，发送用户已勾选的记录ID给服务器端
        //     if (result) {
        //         $.ajax({
        //             url: "delete.jhtml",
        //             type: "POST",
        //             data: {ids:getCookie("selectArr")},
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
        //   });
        // }
    });
       /**
        // 选择所有，复选框改变
        $("input[name='checkAll']").on('ifChanged', function () {
            $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
            if ($(this).is(":checked")) {
                $.ajax({
                  type:"post",
                  url:"recordschemeContent.jhtml",
                  data:{
                      flag:2
                  }
                })
            } else {
              $.ajax({
                type:"post",
                url:"recordschemeContent.jhtml",
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
                  url:"recordschemeContent.jhtml",
                  data:{
                      itemId:$(this).val(),
                      flag:1
                  }

                })
            } else {
              //取消选中
              $.ajax({
                type:"post",
                url:"recordschemeContent.jhtml",
                data:{
                    itemId:$(this).val(),
                    flag:0
                }
              })
            }
        });

        //判断当前的复选框是否被选中，是则为选中状态
        var sp = $("#recordschemeContent").children()
        for(var i = 0 ; i < $("input[name='ids']").length ; i++){
         for(var j = 0 ; j < sp.length;j++){
              if($("input[name='ids']")[i].value == sp[j].innerHTML){
                  $("#"+sp[j].innerHTML).iCheck('check')

              }
          }
        }*/

    });
   function back() {
    		document.location.href="${base}/admin/workingtable/WorkingContent.jhtml"
    }
    
    //
    function addIds(){
    	var $checkeds = $("input[name='ids']:checked")
    	var checkedArr = []
    	if($checkeds.length > 0){
    	  for(var i=0;i<$checkeds.length;i++){
    		checkedArr.push($checkeds[i].id)
    	  }
    	}
    	$("#idsList").val(checkedArr)
    }
</script>
</body>

</html>
