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
                    </div>

                    <h5>题目内容导入</h5>
                </div>
                <div class="ibox-content">
                    <form id="saveQuestionBankForm" class="form-horizontal" action="saveimportQuestionContent.jhtml" method="post"  enctype="multipart/form-data" onsubmit="saveCheckContentFlag()">
                        <div class="form-group" >
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">导入压缩包<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <div id="file-pretty">
                                            <div class="form-group" style="padding-left: 15px;padding-right: 15px;">
                                                <input type="file" name="qcfile" id="qcfile" class="form-control">
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
                                        <h6>2、每次只能导入一个题目的内容，如果需要一次导入多个题目的内容时，请在题库管理里面，采用题库批量导入的方式，或者在题目管理里面，采用题目批量导入的方式。</h6>
                                        <h5>提示：</h5>
                                        <h6>a、题目名称里面，须是题目内容，比如，题目描述、标准答案、学生答案。</h6>
                                        <h6>b、题目名称、题目内容文件名称，须由字母、数字、下划线组成，不能包含汉字或其他字符。</h6>
                                        <h6>c、题目内容文件中的学生答案文件名应为章节名_题目名，例：该题库的章节名为CP01，题目名为PE01，则学生答案文件名应为CP01_PE01</h6>
                                        <h6>d、题目内容文件顺序为：学生答案,题目描述,主文件内容,头文件内容,标准答案</h6>
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
        $("#saveQuestionBankForm").validate({
          rules:{
            qcfile:{
              required:true
            }
          }
        })
    });

    function saveCheckContentFlag() {
      //设置临时存储数据在session中
      if(!window.sessionStorage){
          alert("浏览器不支持localstorage"); 
          return false;
      }else{
          var storage=window.sessionStorage;
          //设置flag
          storage.setItem("checkContentFlag","1");
      }

    }

</script>
</body>
</html>
