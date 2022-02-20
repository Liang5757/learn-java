<div class="animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <form id="commonForm" action="${base}/admin/course_arrange/listDialogClass.jhtml"  method="get">
                    <div class="ibox-content">

                        <div class="dataTables_wrapper form-inline dialog-table">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable dialog-table">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
                                    <td>序号</td>
                                    <td>专业</td>
                                </tr>
                                </thead>
                                <tbody>
                                [#list page.content as major]
                                <tr>
                                    <td>
                                        <input type="checkbox" class="i-checks" name="ids" value="${major.id}" id="${major.id}" data-name="${major.majorName}">
                                    </td>
                                    	<td>${major_index + 1}</td>
										<td>${major.majorName}</td>
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
                <div id="recordMajorId" class="hidden">
                  [#list recordMajorId as majorId]
                      <span>${majorId}</span>
                  [/#list]
                </div>
                <div id="recordMajorName" class="hidden">
                  [#list recordMajorName as majorName]
                      <span>${majorName}</span>
                  [/#list]
                </div>
            </div>
        </div>
    </div>
</div>

<script>

    var listTemplate = "${base}/admin/student/listDialogMajor.jhtml";

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
            var tableCon = $("#selectProfessionList");
            tableCon.empty();
            tableCon.append(result);

          /*  // 复选框
            $('#selectProfessionList .i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});
             // 选择所有，复选框改变
            $("#selectProfessionList input[name='checkAll']").on('ifChanged', function () {
                $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
                if ($(this).is(":checked")) {
                    $.ajax({
                      type:"post",
                      url:"recordMajor.jhtml",
                      data:{
                          flag:2
                      }
                    })
                } else {
                  $.ajax({
                    type:"post",
                    url:"recordMajor.jhtml",
                    data:{
                        flag:3
                    }
                  })
                }
            });

            //选择单个,点击事件
            $("#selectProfessionList input[name='ids']").on('ifClicked', function () {
                //选中状态
                if ($(this).is(":checked") == false) {
                    $.ajax({
                      type:"post",
                      url:"recordMajor.jhtml",
                      data:{
                          majorID:$(this).val(),
                          flag:1
                      }

                    })
                } else {
                  //取消选中
                  $.ajax({
                    type:"post",
                    url:"recordMajor.jhtml",
                    data:{
                        majorID:$(this).val(),
                        flag:0
                    }
                  })
                }
            });


            var sp = $("#recordMajorId").children()
            var $main_profession = $("#main_profession").text().split('、')
            for(var i = 0 ; i < $("#selectProfessionList input[name='ids']").length ; i++){
            //判断当前的复选框是否被选中，是则为选中状态
             for(var j = 0 ; j < sp.length;j++){
                  if($("#selectProfessionList input[name='ids']")[i].value == sp[j].innerHTML){
                      $("#"+sp[j].innerHTML).iCheck('check')
                  }
              }
              //获取原先的名称，使其为勾选状态
              for(var a = 0 ; a < $main_profession.length; a++){
                //getAttribute : 获取元素的属性值
                if($("#selectProfessionList input[name='ids']")[i].getAttribute("data-name") == $main_profession[a]){
                    $("#"+$("#selectProfessionList input[name='ids']")[i].getAttribute("id")).iCheck('check')
                  }
              }
            } */
            /*

            $("input[name='checkAll']").on('ifChanged', function () {
                    $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
                    if ($(this).is(":checked")) {
                        setCookie("major_selectAll",1)  //设置cookie标志
                    } else {
                        if(getCookie("major_selectAll")){  //反选则删除cookie
                            clearCookie("major_selectAll")
                            clearCookie("major_selectArr")
                        }
                    }
                });
                //选择单个,点击事件
                $("input[name='ids']").on('ifChanged', function () {
                    var arr = []
                    //选中状态
                    if ($(this).is(":checked")) {
                        if(getCookie("major_selectArr")){
                            arr = getCookie("major_selectArr").split(",")
                        }
                        if($.inArray($(this).val(),arr) == -1){  //去重
                            arr.push($(this).val())
                            console.log(arr)
                            setCookie("major_selectArr",arr.join(","))
                        }
                        
                    } else {  //反选状态
                        if(getCookie("major_selectArr")){
                            arr = getCookie("major_selectArr").split(",")
                            if($.inArray($(this).val(),arr) != -1){
                                arr.splice($.inArray($(this).val(),arr),1)  //删除元素
                            }
                            setCookie("major_selectArr",arr.join(","))

                            clearCookie("major_selectAll")   //删除全选
                        }
                    }
                });
                //判断是否为全选 是则所有选择框为选中状态
                if(getCookie("major_selectAll")){
                    $("input[name='checkAll']").iCheck("check")
                }
                //判断当前的复选框是否被选中，是则为选中状态
                if(getCookie("major_selectArr")){
                    var sp = getCookie("major_selectArr").split(",")
                    for(var i = 0 ; i < $("input[name='ids']").length ; i++){
                        for(var j = 0 ; j < sp.length;j++){
                            if($("input[name='ids']")[i].value == sp[j]){
                                $("#"+sp[j]).iCheck('check')
                            }
                        }
                    }
                } 

                var $main_profession = $("#main_profession").text().split('、')
                for(var i = 0 ; i < $("#selectProfessionList input[name='ids']").length ; i++){
                    //获取原先的名称，使其为勾选状态
                    for(var a = 0 ; a < $main_profession.length; a++){
                        //getAttribute : 获取元素的属性值
                        if($("#selectProfessionList input[name='ids']")[i].getAttribute("data-name") == $main_profession[a]){
                            $("#"+$("#selectProfessionList input[name='ids']")[i].getAttribute("id")).iCheck('check')
                        }
                    }
                }*/
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
                $("#selectProfessionList input[name='ids']").on('ifChanged', function () {
                      console.log(getCookie("major_selectArr"))
                      var arr = []
                      //选中状态
                    if ($(this).is(":checked")) {
                          //当Cookie中已经存有存放ID的数组，则在此基础上增加ID
                          if(getCookie("major_selectArr")){
                            arr = getCookie("major_selectArr").split(",")
                          }
                          var str = $(this).val()+":"+$(this).attr("data-name")
                          if($.inArray(str,arr) == -1){  //去重
                            arr.push(str)
                            setCookie("major_selectArr",arr.join(","))
                          }
                          //用户勾选复选框时,判断当前页面所有复选框是否都已勾选中，是则将全选设为勾选状态
                          isSelectAll()
                    } else {  //反选状态
                        //查看Cookie中是否已存有用户选中的ID
                        if(getCookie("major_selectArr")){
                          //取消勾选时，查看全选选项是否被选中，是则将其取消掉
                          if($("#selectProfessionList input[name='checkAll']").is(":checked")){
                            $("#selectProfessionList input[name='checkAll']").iCheck("uncheck")
                          }
                          //删除cookie中存在的此选项ID
                          arr = getCookie("major_selectArr").split(",")
                          var str = $(this).val()+":"+$(this).attr("data-name")
                          if($.inArray(str,arr) != -1){   //查看数组中是否有存在此选项ID，有则删除此选项
                            arr.splice($.inArray(str,arr),1)  //删除元素
                          }
                          setCookie("major_selectArr",arr.join(","))   //删除之后再重新设置Cookie
                        }   
                    }
                });

                //判断当前的复选框是否被选中，是则为选中状态
                if(getCookie("major_selectArr")){
                    var sp = getCookie("major_selectArr").split(",")
                    for(var i = 0 ; i < $("#selectProfessionList input[name='ids']").length ; i++){
                      for(var j = 0 ; j < sp.length;j++){
                        var id = sp[j].split(":")[0]
                        if($("#selectProfessionList input[name='ids']")[i].value == id){
                            $("#selectProfessionList #"+id).iCheck('check')
                        }
                      }
                    }
                    //获取cookie中的selectArr数组，遍历循环查看当前页面是否都被选中，是则将全选选项勾选中
                    isSelectAll()
                }

            // var sp = $("#recordMajorId").children()
            var $main_profession = $("#main_profession").text().split('、')
            for(var i = 0 ; i < $("#selectProfessionList input[name='ids']").length ; i++){
              //获取原先的专业名称，使其为勾选状态
                if($("#main_profession").children().length > 0){
                  $main_profession = $("#main_profession").children()
                  //获取专业名称，将有此专业名称的一行勾选中
                 for(var a = 0 ; a < $main_profession.length; a++){
                   var main_profession_name = ""
                   if($main_profession[a].innerHTML.indexOf("、") != -1){
                     main_profession_name = $main_profession[a].innerHTML.slice(0,$main_profession[a].innerHTML.indexOf("、"))
                   }else{
                     main_profession_name = $main_profession[a].innerHTML
                   }
                   //getAttribute : 获取元素的属性值
                   if($("#selectProfessionList input[name='ids']")[i].getAttribute("data-name") == main_profession_name){
                       $("#"+$("#selectProfessionList input[name='ids']")[i].getAttribute("id")).iCheck('check')
                   }
                 }
                }else {
                    for(var a = 0 ; a < $main_profession.length; a++){
                      //getAttribute : 获取元素的属性值
                      if($("#selectProfessionList input[name='ids']")[i].getAttribute("data-name") == $main_profession[a]){
                          $("#"+$("#selectProfessionList input[name='ids']")[i].getAttribute("id")).iCheck('check')
                        }
                    }
                }
            }
        }, "html");

    }

</script>
