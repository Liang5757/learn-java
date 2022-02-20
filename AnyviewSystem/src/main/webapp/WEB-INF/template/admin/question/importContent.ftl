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
                        <!-- <a class="btn btn-white" href="editContent.jhtml" style="margin-top: -8px;">返回</a> -->
                    </div>

                    <h5>内容导入</h5>
                </div>
                <div class="ibox-content">
                    <form id="saveContentForm" class="form-horizontal" action="saveimportContent.jhtml" onsubmit="checkContentFlag()" method="post" enctype="multipart/form-data">
                        <div class="form-group" >
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">导入文件<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <div id="file-pretty">
                                            <div class="form-group" style="padding-left: 15px;padding-right: 15px;">
                                                <input type="file" name="cfile" id="cfile" class="form-control" multiple >
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row" style="color: red;">
                                <div class="col-sm-8">
                                    <div class="col-sm-12">
                                        <h5>*注意</h5>
                                        <h6>1、文件类型须是文本且有后缀名，不能是.doc或.docx。</h6>
                                        <h6>2、文件名称，须由字母、数字、下划线组成，不能包含汉字或其他字符。</h6>
                                    </div>
                                </div>
                            </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-12 col-sm-offset-2">
                                <button type="submit" class="btn btn-danger">确定</button>
                            </div>
                        </div>
                	</div>
                  <input class="hidden" id="contentFlag" name="ContentFlag">
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

        $("#saveContentForm").validate({
          rules:{
              cfile:{
                required:true
              }
          }
        })
    });

    function checkContentFlag() {
        // console.log(storage);
        $("#contentFlag").val(storage['ContentFlag'])
        // console.log($("#contentFlag").val());
    }

</script>
</body>
</html>
