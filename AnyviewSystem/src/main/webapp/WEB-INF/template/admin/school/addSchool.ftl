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
                    <h5>添加学校</h5>

                   <div class="ibox-tools">
                         <button onclick="back()" class="btn btn-outline btn-success">返回</button>
                       <!--   <a class="btn btn-white" href="${base}/admin/school/managerSchoolContent.jhtml" style="margin-top: -8px;">返回</a>-->
          		    </div>
                </div>
                <div class="ibox-content">
                  <label class="control-label">学校<span class="required"> * </span>:</label>
             		    <button onclick="select()" class="btn btn-outline btn-success">选择</button>
                    <form id="saveAclForm" class="form-horizontal" action="${base}/admin/school/sureInsert.jhtml" method="post">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-6" >
                                    <div class="col-sm-9">
                                      <input type="hidden" id="schoolId" name="id" value="0">
                                   			[#if schoolL??]
												  <input type="hidden" name="schoolUpdater" value="${systemUser.name}">
												  <input type="hidden" name="schoolCreater" value="${systemUser.name}">
												  <input type="hidden" name="isDelete" value="1">
												  [#list schoolL as school]
													  <input type="hidden" name="schoolName" id="schoolNames" value="${school.schoolName}">
                                                    [#if schoolL?size < 4]
                                                        [#if (school_index+1 < schoolL?size)]
                                                            <span>${school.schoolName},</span>
                                                        [#else]
                                                            <span>${school.schoolName}</span>
                                                        [/#if]
                                                    [#else]
                                                        [#if school_index < 3]
                                                            <span>${school.schoolName},</span>
                                                        [#else]
                                                            <span>...</span>
                                                            [#break]
                                                        [/#if]
                                                    [/#if]
												  [/#list]
											   [/#if]
                                    </div>
                                </div>
                            </div>
                        </div>
                      
                        <div class="form-group" style="height:50px;">
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

        $("#saveAclForm").validate({
            rules: {
                schoolName: {
                    required: true,
                    remote: {
                        url: "${base}/admin/school/check_schoolName.jhtml",
                        cache: false,
                        type: "GET",
                        data: {
                        	id: $("#schoolId").val(),
                            schoolName: function () {
                                return $("#schoolNames").val();
                            }
                        }
                    }
                },
            },
            messages: {
                schoolName: {
                    remote: "${message("admin.validate.exist")}"
                },
            }
        });
    });
    	function select() {
    		document.location.href="${base}/admin/school/SchoolContent.jhtml"
    	}
    // 	   function back() {
    // 		document.location.href="${base}/admin/school/managerSchoolContent.jhtml"
    // }
    
</script>
</body>
</html>
