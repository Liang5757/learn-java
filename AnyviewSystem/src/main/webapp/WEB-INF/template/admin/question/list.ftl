
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
                        <a class="btn btn-white" href="${base}/admin/question_bank/list.jhtml" style="margin-top: -8px;">返回</a>
                    </div>

                    <h5>题目管理</h5>
                </div>
                <form id="listForm" action="list.jhtml" method="get">
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-3 m-b-xs">
                                <input type="text" placeholder="请输入题目名称" name="filter_question_name" class="input-sm form-control">
                            </div>
                     		<div class="col-sm-1">
                                <div class="input-group">
                                    <button type="submit" class="btn btn-sm btn-danger" onclick="changePageNum()">查询</button>
                                </div>
                            </div>
                        <!-- <div class="ibox-tools">
                            <button type="button" class="btn btn-white" onclick="window.history.back()" style="margin-top: -8px;">返回</button>
                        </div> -->
                        </div>
                        <div>

                            <a class="btn btn-outline btn-success btn-xs" id="btn-add-loippi" href="add.jhtml"><i
                                    class="fa fa-plus"></i>添加</a>

                            <a class="btn btn-outline btn-info btn-xs" id="btn-edit-loippi"><i class="fa fa-paste"></i>修改</a>

                           	<a class="btn btn-outline btn-danger btn-xs btn-delete-loippi-group"><i
                                    class="fa fa-trash"></i>删除</a>


                            <div class="ibox-tools">
                                <a class="btn btn-outline btn-danger btn-xs" href="importQuestion.jhtml">批量导入</a>
                                <a class="btn btn-outline btn-info btn-xs" id="output">下载模板</a>
                    		</div>

                        </div>

                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>

                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
                                    <th>序号</th>
                                    <th>题目名称</th>
                                    <th>章节</th>
                                    <th>题目描述</th>
                                    <th>题目内容</th>
                                    <th>公开级别</th>
                                    <th>难度</th>
                                    <th>状态</th>
                                    <th>备注</th>
                                    <th>更新者</th>
                                    <th>更新时间</th>
                                </tr>
                                </thead>

                               <tbody>
                                [#assign count=0]
                                [#list page.content as question]
                                <tr>
                                    <td>
                                        <input type="checkbox" class="i-checks" id="${question.id}" name="ids" value="${question.id}">
                                    </td>
                                    <td>${question_index + 1}</td>
                                    <td><a href="edit.jhtml?id=${question.id}" style="color:#23c6c8">${question.question_name}</a></td>
                                    <td style="width:55px">${question.chapter}</td>
                                    <td>[#if questionContentList[question_index].question_description!= "" && questionContentList[question_index].question_description?length gt 29]<div style="display: inline-block;width: 200px;" title="${questionContentList[question_index].question_description?html}">${questionContentList[question_index].question_description?substring(0,30)}...</div>[#else]${questionContentList[question_index].question_description}[/#if]</td>
                                    <td style="text-align:center"><a class="btn btn-outline btn-success" href="${base}/admin/question/checkContent.jhtml?id=${question.id}" >编辑</a></td>
                                    <td>
                                    [#if question.public_level==0]完全保密[/#if]
                                    [#if question.public_level==1]公开给特定学校(
                                       <div style="display: inline-block;" title="[#list schoolList as schoolObj][#if count > 0][#if numberList[count] > 3][#if schoolObj_index+1 > numberList[count-1]?number && schoolObj_index < (numberList[count-1]+numberList[count])?number]${schoolObj.schoolName}&#10;[/#if][/#if][#else][#if numberList[count] > 3][#if schoolObj_index < (numberList[count])?number]${schoolObj.schoolName}&#10;[/#if][/#if][/#if][/#list]">
                                       [#list schoolList as schoolObj]
                                         [#if count > 0]
                                            [#if numberList[count] > 3]
                                                [#if schoolObj_index+1 > numberList[count-1]?number && schoolObj_index < (numberList[count-1]+3)?number]
                                                    ${schoolObj.schoolName}
                                                [#elseif schoolObj_index+1 == schoolList?size]
                                                    ...
                                                [/#if]
                                            [#else]
                                                [#if schoolObj_index+1 > numberList[count-1]?number && schoolObj_index < (numberList[count-1]+numberList[count])?number-1]
                                                    ${schoolObj.schoolName}
                                                [#elseif schoolObj_index+1 == (numberList[count-1]+numberList[count])?number]
                                                    ${schoolObj.schoolName}
                                                [/#if]
                                            [/#if]
                                         [#else]
                                            [#if numberList[count] > 3]
                                                [#if schoolObj_index < 3]
                                                    ${schoolObj.schoolName}
                                                [#elseif schoolObj_index+1 == schoolList?size]
                                                    ...
                                                [/#if]
                                            [#else]
                                                [#if schoolObj_index < (numberList[count])?number -1]
                                                    ${schoolObj.schoolName}
                                                [#elseif schoolObj_index+1 == (numberList[count])?number]
                                                    ${schoolObj.schoolName}
                                                [/#if]
                                            [/#if]
                                         [/#if]
                                       [/#list]
                                       </div>
                                       [#assign count=count+1]
                                       )
                                    [/#if]
                                    [#if question.public_level==2]完全公开[/#if]
                                    [#if question.public_level==3]本校公开[/#if]
									</td>
                                    <td>${question.difficulty}</td>
                                    [#if (question.state == "启用")]
                                      <td style="color:green">
                                    [#else]
                                      <td style="color:red">
                                    [/#if]
                                    ${question.state}
                                    </td>
                                    <td>${question.remark}</td>
                                    <td>${question.update_person}</td>
                                    <td>${question.update_date?string('yyyy-MM-dd HH:mm:SS')}</td>
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
                <input class="hidden" id="num" value="${importQuestionNumber}">
                <div id="recordQuestionId" class="hidden">
                  [#list recordQuestionId as QuestionId]
                      <span>${QuestionId}</span>
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
   var storage = window.sessionStorage
   var inpIdArr = []
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

        // // 复选框
        // $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red'});
        // // 选择所有，复选框改变
        // $("input[name='checkAll']").on('ifChanged', function () {
        //     $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
        //     if ($(this).is(":checked")) {
        //         $.ajax({
        //           type:"post",
        //           url:"recordQuestion.jhtml",
        //           data:{
        //               flag:2
        //           }
        //         })
        //     } else {
        //       $.ajax({
        //         type:"post",
        //         url:"recordQuestion.jhtml",
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
        //           url:"recordQuestion.jhtml",
        //           data:{
        //               questionID:$(this).val(),
        //               flag:1
        //           }
        //         })
        //     } else {
        //       //取消选中
        //       $.ajax({
        //         type:"post",
        //         url:"recordQuestion.jhtml",
        //         data:{
        //             questionID:$(this).val(),
        //             flag:0
        //         }
        //       })
        //     }
        // });



        // //判断当前的复选框是否被选中，是则为选中状态
        // var sp = $("#recordQuestionId").children()
        // for(var i = 0 ; i < $("input[name='ids']").length ; i++){
        //  for(var j = 0 ; j < sp.length;j++){
        //       if($("input[name='ids']")[i].value == sp[j].innerHTML){
        //           $("#"+sp[j].innerHTML).iCheck('check')
        //       }

        //   }
        // }

        //批量导入
        if($("#num").val()!="" && $("#num").val()!="-1"){
            art.success("文件导入成功，导入数据数为"+$("#num").val()+"条")
        }else if($("#num").val()=="-1"){
          art.success("文件导入失败")
        }
    });

    //模板导出
    $("#output").click(function(){
      var aLink = document.getElementById('output')
      aLink.href="${base}/resources/studentTemplet/章节名称(模板).zip"
      // aLink.href="${base}/resources/studentTemplet/章节名称(模板).zip"
      // aLink.download = "题目模板"
    })
</script>
</body>

</html>
