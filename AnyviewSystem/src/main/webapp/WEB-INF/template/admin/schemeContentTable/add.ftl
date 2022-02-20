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
                       <div class="ibox-tools">
                         <button onclick="back()" class="btn btn-white" style="margin-top: -8px;">返回</button>
          		         </div>
                    </div>

                    <h5>添加题目</h5>
                </div>

                <div class="ibox-content">
                
                
                    <form id="saveSchemeContentTable" class="form-horizontal" action="add.jhtml" method="post">
  			<div class="ibox-content">
                    <div class="row">
                    		
                            <div class="col-sm-3 m-b-xs">
                            	   <select name="questionBankIds" id="multipleItem"  value="${(params.question_bank)!''}" class="bs-select form-control">
	                                	<!-- [#if checkquestionBank??]

			                          		<option value="${(params.question_bank_id)!''}">${checkquestionBank.question_bank}</option>
								                    [/#if]
			                          		<option value="">全部</option>

		                              [#list ttquestionBankList as itemQuest]
		                                   <option value="${itemQuest.id}">${itemQuest.question_bank}</option>
		                              [/#list] -->
                                  <!-- 显示查询之后已选择的选项 -->
                                  [#if checkquestionBank]
                                    [#list checkquestionBank as questionBank]
                                      <option value="${questionBank.id}" selected>${questionBank}</option>
                                    [/#list]
                                  [/#if]
		                           </select>
		                            
                            </div>
                             <div class="col-sm-3 m-b-xs">
                                <!-- <div>${tt_filter_VID}</div> -->
                            	   <select name="workingIds" id="multipleTable" class="bs-select form-control" multiple="multiple">

		                          	<!-- [#if tt_filter_VID??]
		                          	 <option  value="${params.VID}">${tt_filter_VID.tableName}</option>
		                          	[/#if]
		                          	 <option hassubinfo="true" value="">全部</option>
		                           	  [#list workingTableList as table]
		                                   <option value="${table.id}">${table.tableName}</option>
		                              [/#list] -->
                                  <!-- 显示查询之后已选择的选项 -->
                                  [#if tt_filter_VID]
                                    [#list tt_filter_VID as workingTable]
                                      <option value="${workingTable.id}" selected>${workingTable.tableName}</option>
                                    [/#list]
                                  [/#if]
		                           </select>
                            </div>
                     		<div class="col-sm-1">
                                <div class="input-group">
                                    <button type="submit" class="btn btn-sm btn-danger">查询</button> </span>
                                </div>
                            </div>
                    </div>


                        <div class="dataTables_wrapper form-inline">
						      <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>

                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
                                    <th>序号</th>
                                    <th>题目名称</th>
                                    <th><a href="add.jhtml?VChapName_flag=[#if VChapName_flag == 'asc' || VChapName_flag == null]desc[#elseif VChapName_flag == 'desc']asc[/#if]" style="color:#666">章节<em class="fs-up"><i class="[#if VChapName_flag == 'desc' || VChapName_flag == null]arrow-up[#elseif VChapName_flag == 'asc']selected-up[/#if]"></i><i class="[#if VChapName_flag == 'asc' || VChapName_flag == null]arrow-down[#elseif VChapName_flag == 'desc']selected-down[/#if]"></i></em></a></th>
                                    <th>题目描述</th>
                                    <th><a href="add.jhtml?difficulty_flag=[#if difficulty_flag == 'asc' || difficulty_flag == null]desc[#elseif difficulty_flag == 'desc']asc[/#if]" style="color:#666">难度<em class="fs-up"><i class="[#if difficulty_flag == 'desc' || difficulty_flag == null]arrow-up[#elseif difficulty_flag == 'asc']selected-up[/#if]"></i><i class="[#if difficulty_flag == 'asc' || difficulty_flag == null]arrow-down[#elseif difficulty_flag == 'desc']selected-down[/#if]"></i></em></a></th>
                                    <th>题库</th>
                                    <th>所在作业表</th>
                                </tr>
                                </thead>

                               <tbody>
                               	[#if noSchemeContentTable==0 || noquestionList==0]
                               	[#else]
                                [#list questionsList as question]
                                <tr>
                                    <td>
                                        <input type="checkbox" class="i-checks" name="ids" value="${question.id}" id="${question.id}">

                                    </td>  

                                    <td>${question_index + 1}</td>
                                    <td>${question.question_name}</td>
                                    <td>${question.chapter}</td>
                                    <td>[#if questionContentList[question_index].question_description!= "" && questionContentList[question_index].question_description?length gt 29]<div style="display: inline-block;width: 200px;" title="${questionContentList[question_index].question_description?html}">${questionContentList[question_index].question_description?substring(0,30)}...</div>[#else]${questionContentList[question_index].question_description}[/#if]</td>
                                    <td>${question.difficulty}</td>
                                    <td>${question.questionBank.question_bank}[#if question.questionBank.enabled==0](题库已被删除)[/#if]</td>
                                    [#if question.workingTableName??]
                                    <td>${question.workingTableName}</td>
                                    [#else]
                                     <td>/</td>
                                    [/#if]
                                </tr>
                                [/#list]
                                [/#if]
                                </tbody>
                            </table>
                      </div>
                        <div class="form-group">
                            <div class="col-sm-12 col-sm-offset-2">
                                <input type="button" onclick="addSave()" class="btn btn-danger" value="确定">
                            </div>
                        </div>
                    </form>
                    <div id="questionsIdList" class="hidden">
                      [#list questionsIdList as questionId]
                      <span>${questionId}</span>
                      [/#list]
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>


<!-- BEGIN Script -->
[#include "/admin/include/script.ftl"]
<!-- END Script -->
<script>

"use strict";

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
        
       $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});
     // 选择所有
     $("input[name='checkAll']").on('ifChanged', function () {
         $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
     });
        
  /**
           // 复选框
  $('.i-checks').iCheck({
      checkboxClass: 'icheckbox_square-red',
      radioClass: 'iradio_square-red',
  });
 
   // 选择所有，复选框改变
  // $("input[name='checkAll']").on('ifChanged', function () {
  //     $("input[name='ids1']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
  //     if ($(this).is(":checked")) {
  //         $.ajax({
  //           type:"post",
  //           url:"recordsTable.jhtml",
  //           data:{
  //               flag:2
  //           }
  //         })
  //     } else {
  //       $.ajax({
  //         type:"post",
  //         url:"recordsTable.jhtml",
  //         data:{
  //             flag:3
  //         }
  //       })
  //     }
  //     $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
  //     if ($(this).is(":checked")) {
  //         $.ajax({
  //           type:"post",
  //           url:"recordsItem.jhtml",
  //           data:{
  //               flag:2
  //           }
  //         })
  //     } else {
  //       $.ajax({
  //         type:"post",
  //         url:"recordsItem.jhtml",
  //         data:{
  //             flag:3
  //         }
  //       })
  //     }
  //      });
  //        //选择单个,点击事件
  // $("input[name='ids']").on('ifClicked', function () {
  //     //选中状态
  //     if ($(this).is(":checked") == false) {
  //         $.ajax({
  //           type:"post",
  //           url:"recordsItem.jhtml",
  //           data:{
  //               itemId:$(this).val(),
  //               flag:1
  //           }

  //         })
  //     } else {
  //       //取消选中
  //       $.ajax({
  //         type:"post",
  //         url:"recordsItem.jhtml",
  //         data:{
  //             itemId:$(this).val(),
  //             flag:0
  //         }
  //       })
  //     }
  // });
  
  // //选择单个,点击事件
  // $("input[name='ids1']").on('ifClicked', function () {
  //     //选中状态
  //     if ($(this).is(":checked") == false) {
  //         $.ajax({
  //           type:"post",
  //           url:"recordsTable.jhtml",
  //           data:{
  //               itemId:$(this).val(),
  //               flag:1
  //           }

  //         })
  //     } else {
  //       //取消选中
  //       $.ajax({
  //         type:"post",
  //         url:"recordsTable.jhtml",
  //         data:{
  //             itemId:$(this).val(),
  //             flag:0
  //         }
  //       })
  //     }
  // });
  //     //判断当前的复选框是否被选中，是则为选中状态
  // var sp = $("#recordsItem").children()
  // for(var i = 0 ; i < $("input[name='ids']").length ; i++){
  //  for(var j = 0 ; j < sp.length;j++){
  //       if($("input[name='ids']")[i].value == sp[j].innerHTML){
  //         console.log($("#"+sp[j].innerHTML));
  //           $("#"+sp[j].innerHTML).iCheck('check')
  //       }
  //   }
  // }
  
  //  //判断当前的复选框是否被选中，是则为选中状态
  // var sp = $("#recordsTable").children()
  // for(var i = 0 ; i < $("input[name='ids1']").length ; i++){
  //  for(var j = 0 ; j < sp.length;j++){
  //       if($("input[name='ids1']")[i].value == sp[j].innerHTML){
  //           $("#"+sp[j].innerHTML).iCheck('check')
  //       }
  //   }
  // }
 
 */
  //判断当前的复选框是否被选中，是则为选中状态
  var sp = $("#questionsIdList").children()
  for(var i = 0 ; i < $("input[name='ids']").length ; i++){
   for(var j = 0 ; j < sp.length;j++){
        if($("input[name='ids']")[i].value == sp[j].innerHTML){
            $("#"+sp[j].innerHTML).iCheck('check')
            // var $Tr = $("#"+sp[j].innerHTML).parent().parent().parent()
            // $($Tr).css('display','none')
        }
    }
  }


    //联想输入题库，可多选
      $("#multipleItem").select2({
        placeholder: '请选择题库',
        multiple: true,  //可多选
        ajax: {
          type:"get",
          url: "itemNames.jhtml",
          data: function (params) {
            console.log(params.term)
            return {
              term: params.term,
            };
          },
          processResults: function (data) {
            var itemIds = []
            console.log(data)
            if(data.length>0){
              data.forEach(itemObj=>{
                  itemIds.push({id:itemObj.questionBankId,text:itemObj.questionBankName})
              })
            }
            return {
              results: itemIds
            };
          },
          cache: true
        },
        escapeMarkup: function (markup) { return markup; }
      });

    //联想输入作业表，可多选
    var test = $("#multipleTable").select2({
        placeholder: '请选择参考作业表',
        multiple: true,  //可多选
        ajax: {
          type:"get",
          url: "tableNames.jhtml",
          data: function (params) {
            return {
              term: params.term,
            };
          },
          processResults: function (data) {
            var tableIds = []
            if(data.length>0){
              data.forEach(tableObj=>{
                  tableIds.push({id:tableObj.tableId,text:tableObj.tableName})
              })
            }
            return {
              results: tableIds
            };
          },
          cache: true
        },
        escapeMarkup: function (markup) { return markup; }
      });
});
function addSave() {

  var $checkeds = $("input[name='ids']:checked")
  if($checkeds.length == 0){
  	art.warn("请至少选择一条记录")
  	return ;
  }else{
    var arr = []
    for(var i = 0;i < $checkeds.length; i++){
      arr.push($($checkeds[i]).val() + ':' + $($($($checkeds[i]).parent().parent().parent()).children("td:last-child")).text()) 
    }
    // console.log(arr.join(","))
    document.location.href="${base}/admin/schemeContentTable/saveAdd.jhtml?ids="+arr.join(",")
  }

}

 function back() {
    		document.location.href="${base}/admin/schemeContentTable/back.jhtml"
    }
    
</script>
</body>
</html>