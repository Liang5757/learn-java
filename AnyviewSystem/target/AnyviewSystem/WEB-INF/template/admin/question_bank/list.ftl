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

    <link href="${base}/resources/js/download/facebox/facebox.css" media="screen" rel="stylesheet" type="text/css"/>
    <link href="${base}/resources/js/download/downloadr/downloadr.css" media="screen" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        #facebox .footer {
            margin: 10px;
        }
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <form id="listForm" action="list.jhtml" method="get">
                    <div class="ibox-content">
                        <div class="row">
                    		[#if systemUser.roleId=-1]
                    		<div class="col-sm-3 m-b-xs">
                                <input type="text" placeholder="请输入课程名称" name="filter_courseName"
                                       value="${(params.courseName)!''}" class="input-sm form-control">
                            </div>
                            [/#if]
                            <div class="col-sm-3 m-b-xs">
                                <input type="text" placeholder="请输入题库名称" name="filter_question_bank"
                                       value="${(params.question_bank)!''}" class="input-sm form-control">
                            </div>
                            <div class="col-sm-1">
                                <div class="input-group">
                                    <button type="submit" class="btn btn-sm btn-danger" onclick="changePageNum()"> 查询
                                    </button>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div>

                            <a class="btn btn-outline btn-success btn-xs" id="btn-add-loippi" href="add.jhtml"><i
                                    class="fa fa-plus"></i> 添加</a>

                            <a class="btn btn-outline btn-info btn-xs" id="btn-edit-loippi"><i class="fa fa-paste"></i>
                                修改</a>

                            <a class="btn btn-outline btn-danger btn-xs btn-delete-loippi-group"><i
                                    class="fa fa-trash"></i> 删除</a>


                            <div class="ibox-tools">
                                <a class="btn btn-outline btn-danger btn-xs" href="importQuestionBank.jhtml">批量导入</a>
                                <a class="btn btn-outline btn-info btn-xs" id="output">下载模板</a>
                                [#if systemUser.roleId==-1]
                                <a class="btn btn-outline btn-danger btn-xs" id="btn-questionbank-loippi">批量导出</a>
                                [/#if]
                            </div>

                        </div>

                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>

                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
                                    <th>序号</th>
                                    <th>题库</th>
                                    <th>课程</th>
                                    <th>题目</th>
                                    <th>公开级别</th>
                                    <th>更新者</th>
                                    <th>更新时间</th>
                                </tr>
                                </thead>

                                <tbody>
                                [#assign counts=0]
                                [#list page.content as questionbank]
                                <tr>
                                    <td>
                                        <input type="checkbox" class="i-checks" name="ids" value="${questionbank.id}"
                                               id="${questionbank.id}">
                                    </td>
                                    <td>${questionbank_index + 1}</td>
                                    <td><a href="edit.jhtml?id=${questionbank.id}"
                                           style="color:#23c6c8">${questionbank.question_bank}</a></td>
                                    <td>
                                        ${questionbank.course_name}
                                    </td>
                                    <td style="text-align:center"><a class="btn btn-outline btn-success"
                                                                     href="${base}/admin/question/list.jhtml?id=${questionbank.id}">管理</a>
                                    </td>
                                    <td>
                                    [#if questionbank.public_level==0]完全保密[/#if]
                                    [#if questionbank.public_level==1]公开给特定学校(
                                       <div style="display: inline-block;"
                                            title="[#list schoolList as schoolObj][#if counts > 0][#assign count = 0][#list 0..counts-1 as i][#assign count = count+numberList[i]?number][/#list][#if schoolObj_index+1 > count && schoolObj_index < (count+numberList[counts]?number)][#if numberList[counts]?number > 3]${schoolObj.schoolName}&#10;[/#if][/#if][#elseif counts == 0][#if schoolObj_index < numberList[counts]?number][#if numberList[counts]?number > 3]${schoolObj.schoolName}&#10;[/#if][/#if][/#if][/#list]">
                                       [#list schoolList as schoolObj]
                                         [#if counts > 0]
                                             [#assign count = 0]
                                             [#list 0..counts-1 as i]
                                                 [#assign count = count+numberList[i]?number]
                                             [/#list]
                                             [#if schoolObj_index+1 > count && schoolObj_index < (count+numberList[counts]?number)]
                                                 [#if numberList[counts]?number > 3]
                                                     [#if schoolObj_index+1 > count+3]
                                                        ...
                                                         [#break]
                                                     [#else]
                                                         ${schoolObj.schoolName}、
                                                     [/#if]
                                                 [#else]
                                                     [#if schoolObj_index+1 == count+numberList[counts]?number]
                                                         ${schoolObj.schoolName}
                                                     [#else]
                                                         ${schoolObj.schoolName}、
                                                     [/#if]
                                                 [/#if]
                                             [/#if]
                                         [#elseif counts == 0]
                                             [#if schoolObj_index < numberList[counts]?number]
                                                 [#if numberList[counts]?number > 3]
                                                     [#if schoolObj_index+1 > count+3]
                                                        ...
                                                         [#break]
                                                     [#else]
                                                         ${schoolObj.schoolName}、
                                                     [/#if]
                                                 [#else]
                                                     [#if schoolObj_index+1 == count+numberList[counts]?number]
                                                         ${schoolObj.schoolName}
                                                     [#else]
                                                         ${schoolObj.schoolName}、
                                                     [/#if]
                                                 [/#if]
                                             [/#if]
                                         [/#if]
                                       [/#list]
                                       </div>
                                        [#assign counts=counts+1]
                                       )
                                    [/#if]
                                    [#if questionbank.public_level==2]完全公开[/#if]
                                    [#if questionbank.public_level==3]本校公开[/#if]
                                    </td>
                                    <td>${questionbank.update_person}</td>
                                    <td>[#if questionbank.update_date??]${questionbank.update_date?string('yyyy-MM-dd HH:mm:SS')}[/#if]</td>
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
                <input class="hidden" id="num" value="${number}">
                [#--<div id="recordQuestionBankId" class="hidden">--]
                  [#--[#list recordQuestionBankId as QuestionBankId]--]
                      [#--<span>${QuestionBankId}</span>--]
                  [#--[/#list]--]
                [#--</div>--]
            </div>
        </div>
    </div>
</div>

<!-- BEGIN Script -->
[#include "/admin/include/script.ftl"]
<!-- END Script -->
<script src="${base}/resources/js/download/facebox/facebox.js"></script>
<script src="${base}/resources/js/download/downloadr/jqbrowser.js"></script>
<script src="${base}/resources/js/download/downloadr/downloadr.js"></script>
<script>
    $(document).ready(function () {
        [@flash_message /]
        $(".chosen-select").chosen({width: "100%"});

        //编辑用户
        $("#btn-edit-loippi").click(function () {
            var $checkeds = jQuery($("input[name='ids']:checked"));
            if ($checkeds.length > 1) {
                art.warn("每次只能选择一条记录进行修改");
                return;
            } else if ($checkeds.length == 0) {
                art.warn("请选择一条记录进行修改");
                return;
            }
            location.href = "edit.jhtml?id=" + $($checkeds[0]).val();
        });

        //导出题库
        $("#btn-questionbank-loippi").click(function () {
            // var $checkeds = jQuery($("input[name='ids']:checked"));
            // var checkArr = [];
            // if($checkeds.length == 0){
            //     art.warn("请选择一条记录进行导出");
            //     return;
            // }else{
            // 	for(var i = 0;i<$checkeds.length;i++){
            // 		checkArr.push($($checkeds[i]).val());
            //  	}
            // }
            if (getCookie("selectArr")) {
                //发送ajax请求获取url,下载题库压缩包
                $.ajax({
                    type: "get",
                    url: "exportQuestionBank.jhtml?ids=" + getCookie("selectArr"),
                    success: function (urlArr) {
                        $("body").append('<a style="display:none" rel="downloadr" id="fileLink"></a>')
                        $("#fileLink").attr("href", "${base}/upload/tempfile.zip")
                        $('a[rel*=downloadr]').downloadr();
                        document.getElementById("fileLink").click()
                        console.log(urlArr)
                        // console.log($(".downloadA"))
                        $(".downloadA").mousedown(function (e) {
                            if (e.which == 1 || e.which == 3) {
                                setTimeout(function () {
                                    $.ajax({
                                        type: "get",
                                        url: "deleteFile.jhtml",
                                        data: {
                                            filepath: Arr.join(",")
                                        },
                                        success: function (data) {
                                            console.log(data)
                                            location.href = "list.jhtml"
                                        }
                                    })
                                }, 10000)
                            }
                        })
                    }
                })
            } else {
                art.warn("请至少选择一条记录进行导出");
                return;
            }


            //location.href = "exportQuestionBank.jhtml?id=" + checkArr;
        });

        //批量导入
        if ($("#num").val() == "-1") {
            art.success("导入数据失败，导入数据条数为0条")
        } else if ($("#num").val() == "-2") {
            art.success("此题库已在系统中存在，请勿重复导入")
        } else if ($("#num").val() != "") {
            art.success("文件导入成功，导入数据数为" + $("#num").val() + "条")
        }

        // // 复选框
        // $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red'});
        // // 选择所有，复选框改变
        // $("input[name='checkAll']").on('ifChanged', function () {
        //     $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
        //     if ($(this).is(":checked")) {
        //         $.ajax({
        //           type:"post",
        //           url:"recordQuestionBank.jhtml",
        //           data:{
        //               flag:2
        //           }
        //         })
        //     } else {
        //       $.ajax({
        //         type:"post",
        //         url:"recordQuestionBank.jhtml",
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
        //           url:"recordQuestionBank.jhtml",
        //           data:{
        //               questionBankID:$(this).val(),
        //               flag:1
        //           }
        //         })
        //     } else {
        //       //取消选中
        //       $.ajax({
        //         type:"post",
        //         url:"recordQuestionBank.jhtml",
        //         data:{
        //             questionBankID:$(this).val(),
        //             flag:0
        //         }
        //       })
        //     }
        // });

        // //判断当前的复选框是否被选中，是则为选中状态
        // var sp = $("#recordQuestionBankId").children()
        // for(var i = 0 ; i < $("input[name='ids']").length ; i++){
        //  for(var j = 0 ; j < sp.length;j++){
        //       if($("input[name='ids']")[i].value == sp[j].innerHTML){
        //           $("#"+sp[j].innerHTML).iCheck('check')
        //       }

        //   }
        // }

    });

    //模板导出
    $("#output").click(function () {
        var aLink = document.getElementById('output')
        aLink.href = "${base}/resources/studentTemplet/课程名称_题库名称(模板).zip"
        // aLink.href="${base}/resources/studentTemplet/question_bank.zip"
        // aLink.download = "课程名称_题库名称(模板)"   //跨域则download属性无效
        // art.success("模板导出成功")
    })


</script>
</body>

</html>
