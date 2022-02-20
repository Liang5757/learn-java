
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
                        <!-- <button type="button" class="btn btn-white" onclick="window.history.back();"
                                style="margin-top: -8px;">返回
                        </button> -->
                        <a class="btn btn-white" href="${base}/admin/managerContent.jhtml" style="margin-top: -8px;">返回</a>
                    </div>
                    <h5>添加管理员</h5>
                </div>
                <div class="ibox-content">
                    <form id="saveAclForm" class="class="form-horizontal"" action="${base}/admin/saveManager.jhtml" method="post">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-6 vaild">
                                    <label class="col-sm-3 control-label">用户名<span class="required"> * </span>:</label>
                                    <div class="col-sm-9">
                                        <input  id="username" placeholder="请输入管理员用户名" name="username" class="form-control">
                                        <input type="hidden" name="id" id="userId" value="" >
                                    </div>
                                </div>
                              </div>
                              &nbsp;
                              <div class="row">
                                 <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">姓名<span class="required"> * </span>:</label>
                                    <div class="col-sm-9">
                                        <input name="name" placeholder="请输入管理员姓名" class="form-control">
                                        <input type="hidden" name="lastUpdatedBy" value="${BysystemUser.name}">
										                    <input type="hidden" name="createdBy" value="${BysystemUser.name}">
                                    </div>
                                </div>
                              </div>
                              &nbsp;
                              <div class="row">
                                 <div class="col-sm-6">
                                    <label class="col-sm-3 control-label">学校<span class="required"> * </span>:</label>

                                    <div class="col-sm-9">
                                      <!-- <input name="schoolName" id="schoolName_admin" placeholder="请输入学校名称" class="form-control">
                                      <input name="schoolId" id="schoolId" class="hidden"> -->
                               <!-- <select name="schoolId" id="schoolId" class="bs-select form-control">
                                            <option hassubinfo="true" value="">请选择学校</option>
	                                        [#list schoolList as school]
	                                            <option value="${school.id}">${school.schoolName!}</option>
	                                        [/#list]
	                                     </select> -->
                                       <select class="form-control" name="schoolId" id="schoolName_admin">
                                       </select>
                                    </div>
                                </div>
                              </div>
                              &nbsp;
                              <div class="row">
                                   		<div class="col-sm-6">
                                            <label class="col-sm-3 control-label">邮箱<span class="required"> * </span>:</label>
                                            <div class="col-sm-9">
                                                <input name="email" placeholder="请输入邮箱地址" class="form-control">
                                            </div>
                                        </div>
                            </div>

                        </div>
                        <!-- <div class="hr-line-dashed"></div> -->
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
            submitHandler:function (form) {   //不使用submit默认提交功能，先进行自定义验证，成功则提交表单
                $.ajax({
                  url: "${base}/admin/check_adminName.jhtml",
                  type: "GET",
                  data: {
                    id: $("#userId").val(),
                    schoolId: $("#schoolName_admin").val(),
                    username: $("#username").val()
                  },
                  success:function (data) {   //发送ajax请求给后端进行验证，返回结果为true/false  
                      if(data){
                        $(".vaild").removeClass("has-error")
                        $(".vaild").addClass("has-success")
                        $(".vaild span:last").text("")
                        form.submit()
                      }else{
                        console.log(data)
                        $(".vaild").removeClass("has-success")
                        $(".vaild").addClass("has-error")
                        $(".vaild span:last").text("已存在")
                      }
                  }
                })
            },
            onfocusout: function(element) { $(element).valid(); },
            onkeyup: function(element) { $(element).valid(); },
            rules: {
                name: {
                    required: true,

                },
                email: {
                    required: true,
                    email: true
                },

                schoolId: {
                    required: true
                },

                username: {
                    required: true,
                    // remote: {
                    //     url: "${base}/admin/check_adminName.jhtml",
                    //     cache: false,
                    //     type: "GET",
                    //     data: {
                    //    		 id: function () {
                    //             return $("#userId").val();
                    //         },
                    //          schoolId: function () {
                    //             return $("#schoolName_admin").val();
                    //         },
                            
                    //         username: function () {

                    //             return $("#username").val();
                    //         },

                    //     }
                    // }
                    // remote:function () {
                    //     if($("#schoolName_admin").val()!="" && $("#schoolName_admin").val()!=null){
                    //         return {
                    //             url: "${base}/admin/check_adminName.jhtml",
                    //             cache: false,
                    //             type: "GET",
                    //             data: {
                    //                 id: $("#userId").val(),
                    //                 schoolId: $("#schoolName_admin").val(),
                    //                 username: $("#username").val()
                    //             }
                    //         }
                    //     }
                    // }

                },
            },
            messages: {
                username: {
                    remote: "${message('admin.validate.exist')}"
                }
            }
        });

        //联想搜索
        // $("#schoolName_admin").autocomplete({
        //   source:function (requeset,response) {
        //       $.ajax({
        //         type:"GET",
        //         url:"AdminSchool.jhtml",
        //         data:{
        //           "term":$("#schoolName_admin").val()
        //         },
        //         success:function (data) {
        //           if(data.length >= 1){
        //               var schoolNames=[]
        //               data.forEach(function(schoolObj){
        //                  schoolNames.push({label:schoolObj.schoolName,value:schoolObj.schoolName,id:schoolObj.id})
        //               })
        //               response(schoolNames)
        //           } else {
        //             response(["学校不存在"])
        //           }
        //         }
        //       })
        //
        //   },
        //   select:function (event, ui) {
        //     $("#schoolName_admin").val(ui.item.label)
        //     $("#schoolId").val(ui.item.id)
        //
        //   }
        // })

        $("#schoolName_admin").select2({
          placeholder: '请选择学校',
          ajax: {
            type:"get",
            url: "AdminSchool.jhtml",
            data: function (params) {
              console.log(params.term);
              return {
                term: params.term,
              };
            },
            processResults: function (data) {
              console.log(data);
              var schools = []
              if(data.length>0){
                for (var i = 0; i < data.length; i++) {
                    schools.push({id:data[i].id,text:data[i].schoolName})
                }
                // data.forEach(schoolObj=>{
                //     schools.push({id:schoolObj.id,text:schoolObj.schoolName})
                // })
              }
              return {
                results: schools
              };
            },
            cache: true
          },
          escapeMarkup: function (markup) { return markup; }
          });
    });

</script>
</body>

</html>
