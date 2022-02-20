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
                        <!-- <button class="btn btn-white" type="button"onclick="window.history.back();" style="margin-top: -8px;">返回</button> -->
                        <a class="btn btn-white" href="${base}/admin/question_bank/list.jhtml" style="margin-top: -8px;">返回</a>
                    </div>

                    <h5>题库批量导入</h5>
                </div>
                <div class="ibox-content">
                    <form id="saveQuestionBankForm" class="form-horizontal" action="saveimportQuestionBank.jhtml" method="post"  enctype="multipart/form-data">
                        <div class="form-group" >
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">导入压缩包<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <div id="file-pretty">
                                            <div class="form-group" style="padding-left: 15px;padding-right: 15px;">
                                                <input type="file" name="qbfile" id="qbfile" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row" style="color: red;">
                                <div class="col-sm-8">
                                    <div class="col-sm-12">
                                        <h5>*注意</h5>
                                        <h6>1、压缩包须是zip格式压缩文件。由于部分rar软件本身的一些问题，rar格式压缩文件导入时可能会不成功。</h6>
                                        <h6>2、压缩包的名字须是“课程名称_题库名称.zip”，比如“数据结构_ 数据结构题库.zip”，添加后，课程名称和题库名称被分别放在题库管理里面的课程字段、题库字段里面。</h6>
                                        <h5>提示：</h5>
                                        <h6>a、课程名称须是系统中课程管理里面已存在的课程，否则导入不成功。</h6>
                                        <h6>b、课程名称_题库名称的下一级目录，须是章节名称。章节名称的下一级目录，须是题目名称。题目名称的下一级，就是题目内容，比如，题目描述、标准答案、学生答案。</h6>
                                        <h6>c、章节名称里面的数字，须是1~20之间的整数，否则导入不成功。</h6>
                                        <h6>d、章节名称、题目名称、题目内容文件名称，须由字母、数字、下划线组成，不能包含汉字或其他字符。</h6>
                                        <h6>e、题目内容文件中的学生答案文件名应为章节名_题目名，例：该题库的章节名为CP01，题目名为PE01，则学生答案文件名应为CP01_PE01</h6>
                                        <h6>f、题目内容文件顺序为：学生答案,题目描述,主文件内容,头文件内容,标准答案</h6>
                                    </div>
                                </div>
                            </div>

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

        //检验必填值是否有填写
        $("#saveQuestionBankForm").validate({
          rules:{
            qbfile:{
              required:true
            }
          }
        })
    });
</script>
</body>
</html>
