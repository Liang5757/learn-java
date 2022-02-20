<div class="animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <form id="commonForm" action="${base}/admin/question_bank/listDialogSchool.jhtml"  method="get">
                    <div class="ibox-content">
                        <div class="dataTables_wrapper form-inline dialog-table">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable dialog-table">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
                                    <th>序号</th>
                                    <th>学校</th>
                                </tr>
                                </thead>
                                <tbody>
                                [#list page.content as school]
                                <tr>
                                    <td>
                                        <input type="checkbox" class="i-checks" name="ids" id="${school.id}" value="${school.id}" data-name="${school.schoolName}">
                                    </td>
                                    <td>${school_index + 1}</td>
                                    <td>${school.schoolName}</td>
                                </tr>
                                [/#list]
                                </tbody>
                            </table>
                        	[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
                            [#include "/admin/include/paginationdialog.ftl"]
                        	[/@pagination]
                        </div>
                    </div>
                </form>
                <div id="recordSchoolId" class="hidden">
                  [#list recordSchoolId as SchoolId]
                      <span>${SchoolId}</span>
                  [/#list]
                </div>
                <div id="recordSchoolName" class="hidden">
                  [#list recordSchoolName as SchoolName]
                      <span>${SchoolName}</span>
                  [/#list]
                </div>
            </div>
        </div>
    </div>
</div>

<script>

    var listTemplate = "${base}/admin/question_bank/listDialogSchool";

    // 页码跳转
    $.pageSkipDialog = function (pageNumber) {
        $("#commonForm #pageNumber").val(pageNumber);
        reload();
    };

    $(document).on("click",".btn-search-fds",function(){
        $("#commonForm #pageNumber").val(1);
        reload();
    });

    function reload() {
        var index = layer.load(1, {
            shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        var data = $("#commonForm").serialize();
        $.get(listTemplate, data, function (result, status) {
            layer.close(index);
            var tableCon = $(".dialog-table");
            tableCon.empty();
            tableCon.append(result);

           /* // 复选框
            $('.dialog-table .i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});
            // 选择所有，复选框改变
            $(".dialog-table input[name='checkAll']").on('ifChanged', function () {
                $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
                if ($(this).is(":checked")) {
                    $.ajax({
                      type:"post",
                      url:"recordSchool.jhtml",
                      data:{
                          flag:2
                      }
                    })
                } else {
                  $.ajax({
                    type:"post",
                    url:"recordSchool.jhtml",
                    data:{
                        flag:3
                    }
                  })
                }
            });

            //选择单个,点击事件
            $(".dialog-table input[name='ids']").on('ifClicked', function () {
                //选中状态
                if ($(this).is(":checked") == false) {
                    $.ajax({
                      type:"post",
                      url:"recordSchool.jhtml",
                      data:{
                          schoolID:$(this).val(),
                          flag:1
                      }

                    })
                } else {
                  //取消选中
                  $.ajax({
                    type:"post",
                    url:"recordSchool.jhtml",
                    data:{
                        schoolID:$(this).val(),
                        flag:0
                    }
                  })
                }
            });

            //判断当前的复选框是否被选中，是则为选中状态
            var sp = $("#recordSchoolId").children()
            var $recordSchoolName = $("#recordSchoolName").children()
            for(var i = 0 ; i < $(".dialog-table input[name='ids']").length ; i++){
             for(var j = 0 ; j < sp.length;j++){
                  if($(".dialog-table input[name='ids']")[i].value == sp[j].innerHTML){
                      //console.log($("#"+sp[j].innerHTML))
                      $("#"+sp[j].innerHTML).iCheck('check')

                  }
              }
              //获取原先的学院名称，使其为勾选状态
            for(var a = 0 ; a < $recordSchoolName.length; a++){
              //getAttribute : 获取元素的属性值
              if($("input[name='ids']")[i].getAttribute("data-name") == $recordSchoolName[a].innerHTML){
                  $("#"+$("input[name='ids']")[i].getAttribute("id")).iCheck('check')
              }
             }
            }  */

            // 初始化复选框
                $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});

                // 选择所有，复选框改变
                $("input[name='checkAll']").on('ifClicked', function () {
                  //判断用户是否勾选了全选选项，是则将当前页面的复选框全部选中，并将其ID存入Cookie中
                  if ($(this).is(":checked")) {
                     $("input[name='ids']").iCheck('uncheck'); 
                  } else {
                    $("input[name='ids']").iCheck('check');   //用户取消全选，则将Cookie中存放的SelectArr数组清除
                  }
                });

  
                //选择单个,点击事件
                $("input[name='ids']").on('ifChanged', function () {
                      var arr = []
                      //选中状态
                    if ($(this).is(":checked")) {
                          //当Cookie中已经存有存放ID的数组，则在此基础上增加ID
                          if(getCookie("school_selectArr")){
                            arr = getCookie("school_selectArr").split(",")
                          }

                          var str = $(this).val()+":"+$(this).attr("data-name")  //将用户所选择的ID及学校名称存到cookie中

                          if($.inArray(str,arr) == -1){  //去重
                            // console.log($(this).attr("data-name"))
                            arr.push(str)
                            setCookie("school_selectArr",arr.join(","))
                          }
                          //用户勾选复选框时,判断当前页面所有复选框是否都已勾选中，是则将全选设为勾选状态
                          isSelectAll()
                    } else {  //反选状态
                        //查看Cookie中是否已存有用户选中的ID
                        if(getCookie("school_selectArr")){
                          //取消勾选时，查看全选选项是否被选中，是则将其取消掉
                          if($("input[name='checkAll']").is(":checked")){
                            $("input[name='checkAll']").iCheck("uncheck")
                          }
                          //删除cookie中存在的此选项ID
                          arr = getCookie("school_selectArr").split(",")
                          var str = $(this).val()+":"+$(this).attr("data-name")  //将用户所选择的ID及学校名称从cookie中移除

                          if($.inArray(str,arr) != -1){   //查看数组中是否有存在此选项ID，有则删除此选项
                            arr.splice($.inArray(str,arr),1)  //删除元素
                          }
                          setCookie("school_selectArr",arr.join(","))   //删除之后再重新设置Cookie
                        }   
                    }
                });

                //判断当前的复选框是否被选中，是则为选中状态
                if(getCookie("school_selectArr")){
                    var sp = getCookie("school_selectArr").split(",")
                    for(var i = 0 ; i < $("input[name='ids']").length ; i++){
                      for(var j = 0 ; j < sp.length;j++){
                        var id = sp[j].split(":")[0]
                        if($("input[name='ids']")[i].value == id){
                            $("#"+id).iCheck('check')
                        }
                      }
                    }
                    //获取cookie中的selectArr数组，遍历循环查看当前页面是否都被选中，是则将全选选项勾选中
                    isSelectAll()
                }

                //判断当前的复选框是否被选中，是则为选中状态
                var $main_SchoolName = $("#main_school").text().split("、")
                for(var i = 0 ; i < $(".dialog-table input[name='ids']").length ; i++){
                  //获取原先的名称，使其为勾选状态
                  if($("#main_school").children().length > 0){
                    $main_SchoolName = $("#main_school").children()
                    for(var a = 0 ; a < $main_SchoolName.length; a++){
                      var school_name = ""
                      //getAttribute : 获取元素的属性值
                      if($main_SchoolName[a].innerHTML.indexOf("、") != -1){
                        school_name = $main_SchoolName[a].innerHTML.slice(0,$main_SchoolName[a].innerHTML.indexOf("、"))
                      }else{
                        school_name = $main_SchoolName[a].innerHTML
                      }
                      if($("input[name='ids']")[i].getAttribute("data-name") == school_name){
                          $("#"+$("input[name='ids']")[i].getAttribute("id")).iCheck('check')
                      }
                  }
                }else{
                  for(var a = 0 ; a < $main_SchoolName.length; a++){
                    //getAttribute : 获取元素的属性值
                    if($("input[name='ids']")[i].getAttribute("data-name") == $main_SchoolName[a]){
                        $("#"+$("input[name='ids']")[i].getAttribute("id")).iCheck('check')
                    }
                   }
                }
              }
        }, "html");

    }

</script>
