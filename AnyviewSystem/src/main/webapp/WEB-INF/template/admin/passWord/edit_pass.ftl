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
                    <h5>修改密码</h5>
                    <div class="ibox-tools">
                        <button class="btn btn-white" type="button"onclick="back();" style="margin-top: -8px;">返回</button>
                    </div>
                </div>
                <div class="ibox-content">
                    <form id="inputForm" class="form-horizontal" action="updatePass.jhtml" method="post">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">新密码<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                        <input type="password" name="password" id="password" class="form-control" placeholder="请输入新密码">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">确认新密码<span class="required"> * </span>:</label>
                                    <div class="col-sm-9">
                                        <input type="password" name="confimPass" id="confimPass" class="form-control" placeholder="请再次输入新密码">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-12 col-sm-offset-2">
                                <button class="btn btn-danger" type="submit">确定</button>
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

        $("#browserButton").browser({input: $("#avatar")});
        $(".chosen-select").chosen({width: "100%"});

        jQuery("#inputForm").validate({
            rules: {
                password: {
                    required: true
                },
                confimPass: {
                    required: true,
                    equalTo: "#password"
                }
            },
            messages: {}
        });
    });
function back() {
    		document.location.href="${base}/admin/common/index.jhtml"
    	}
</script>
</body>

</html>
