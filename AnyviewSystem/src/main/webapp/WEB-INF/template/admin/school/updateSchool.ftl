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
<script type="text/javascript">

	function select() {
		document.location.href="${base}/admin/school/editSchoolContent.jhtml"
	}
</script>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>修改学校</h5>
                    <div class="ibox-tools">
                         <!-- <button onclick="back()" class="btn btn-outline btn-success">返回</button> -->
                         <a class="btn btn-white" href="${base}/admin/school/managerSchoolContent.jhtml" style="margin-top: -8px;">返回</a>
          		    </div>
                </div>
                <div class="ibox-content">
             		    <label class="control-label">学校<span class="required"> * </span>:</label>
                    <button onclick="select()" class="btn btn-outline btn-success btn-sm">选择</button>
                    <form id="saveAclForm" class="form-horizontal" action="${base}/admin/school/sureEdit.jhtml" method="post" style="margin:10px 0;">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-6" >
                                    <div class="col-sm-9">
                                      		<input type="hidden" id="schoolId" name="id" value="${schoolId!}">
                                      		  <input type="hidden" name="schoolUpdater" value="${systemUser.name}">
											 	[#if school??]
													  <input type="hidden" name="schoolName" value="${school.schoolName}" id="schoolNames">${school.schoolName}

												 [/#if]

												 [#if systemSchool??]
													  <input type="hidden" name="schoolName" id="schoolNames" value="${systemSchool.schoolName}">${systemSchool.schoolName}

												 [/#if]
                                    </div>
                                </div>

                            </div>
                        </div>
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
        var $browserButton = $("#browserButton");
        $browserButton.browser({input: $("#avatar")});
        $(".chosen-select").chosen({width: "100%"});

        $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});

        $("#roleId").change(function () {
            var data = $(this).val();

            var data = {
                id: data
            };
            var index = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            //获取id
            $.get("${base}/admin/user/listRoleTemplate.jhtml", data, function (result, status) {
                var tableCon = $("#role_acls");
                tableCon.empty();
                tableCon.append(result);
                layer.close(index);
                $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});
            }, "html");

        });

        $("#saveAclForm").validate({
            rules: {
                schoolName: {
                    required: true,
                    remote: {
                        url: "${base}/admin/school/check_schoolName.jhtml",
                        cache: false,
                        type: "GET",
                        data: {
                            schoolName: function () {
                                return $("#schoolNames").val();
                            },
                            id: $("#schoolId").val()
                        }
                    }
                }
            },
            messages: {
                schoolName: {
                    remote: "${message("admin.validate.exist")}"
                },
            }
        });
    });
    // function back() {
    // 		document.location.href="${base}/admin/school/managerSchoolContent.jhtml"
    // }

</script>
</body>

</html>
