<div class="animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <form id="commonForm" action="${base}/admin/course_arrange/listDialogCollege.jhtml"  method="get">
                    <div class="ibox-content">

                        <div class="dataTables_wrapper form-inline dialog-table">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable dialog-table">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
                                    <td>序号</td>
                                    <td>学院</td>
                                </tr>
                                </thead>
                                <tbody>
                                [#list page.content as college]
                                <tr>
                                    <td>
                                        <input type="checkbox" class="i-checks" name="ids" value="${college.id}" id="${college.id}" data-name="${college.collegeName}">
                                    </td>
                                    	<td>${college_index + 1}</td>
										<td>${college.collegeName}</td>
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
                <div id="recordCollegeId" class="hidden">
                  [#list recordCollegeId as collegeId]
                      <span>${collegeId}</span>
                  [/#list]
                </div>
                <div id="recordCollegeName" class="hidden">
                  [#list recordCollegeName as collegeName]
                      <span>${collegeName}</span>
                  [/#list]
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    var listTemplate = "${base}/admin/student/listDialogCollege.jhtml";
    // 页码跳转
    $.pageSkipDialog = function (pageNumber) {
        $("#commonForm #pageNumber").val(pageNumber);
        reload();
    };

    //翻页重加载
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
            var tableCon = $("#selectCollegeList");
            tableCon.empty();
            tableCon.append(result);


         /*   // 初始化复选框
            $('#selectCollegeList .i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});
            // 选择所有，复选框改变
            $("#selectCollegeList input[name='checkAll']").on('ifChanged', function () {
                $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
                if ($(this).is(":checked")) {
                    $.ajax({
                      type:"post",
                      url:"recordCollege.jhtml",
                      data:{
                          flag:2
                      }
                    })
                } else {
                  $.ajax({
                    type:"post",
                    url:"recordCollege.jhtml",
                    data:{
                        flag:3
                    }
                  })
                }
            });

            //选择单个,点击事件
            $("#selectCollegeList input[name='ids']").on('ifClicked', function () {
                //选中状态
                if ($(this).is(":checked") == false) {
                    $.ajax({
                      type:"post",
                      url:"recordCollege.jhtml",
                      data:{
                          collogeID:$(this).val(),
                          flag:1
                      }

                    })
                } else {
                  //取消选中
                  $.ajax({
                    type:"post",
                    url:"recordCollege.jhtml",
                    data:{
                        collogeID:$(this).val(),
                        flag:0
                    }
                  })
                }
            });

            var sp = $("#recordCollegeId").children()
            var $main_college = $("#main_college").text().split('、')
            for(var i = 0 ; i < $("#selectCollegeList input[name='ids']").length ; i++){
              //判断当前的复选框是否被选中，是则为选中状态
             for(var j = 0 ; j < sp.length;j++){
                  if($("#selectCollegeList input[name='ids']")[i].value == sp[j].innerHTML){

                      $("#"+sp[j].innerHTML).iCheck('check')

                  }
              }
              //获取原先的名称，使其为勾选状态
              for(var a = 0 ; a < $main_college.length; a++){
                //getAttribute : 获取元素的属性值
                if($("#selectCollegeList input[name='ids']")[i].getAttribute("data-name") == $main_college[a]){
                    $("#"+$("#selectCollegeList input[name='ids']")[i].getAttribute("id")).iCheck('check')
                  }
              }
            } */

           /*  $("input[name='checkAll']").on('ifChanged', function () {
                    $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
                    if ($(this).is(":checked")) {
                        setCookie("college_selectAll",1)  //设置cookie标志
                    } else {
                        if(getCookie("college_selectAll")){  //反选则删除cookie
                            clearCookie("college_selectAll")
                            clearCookie("college_selectArr")
                        }
                    }
                });
                //选择单个,点击事件
                $("input[name='ids']").on('ifChanged', function () {
                    var arr = []
                    //选中状态
                    if ($(this).is(":checked")) {
                        if(getCookie("college_selectArr")){
                            arr = getCookie("college_selectArr").split(",")
                        }
                        if($.inArray($(this).val(),arr) == -1){  //去重
                            arr.push($(this).val())
                            setCookie("college_selectArr",arr.join(","))
                        }
                        
                    } else {  //反选状态
                        if(getCookie("college_selectArr")){
                            arr = getCookie("college_selectArr").split(",")
                            if($.inArray($(this).val(),arr) != -1){
                                arr.splice($.inArray($(this).val(),arr),1)  //删除元素
                            }
                            setCookie("college_selectArr",arr.join(","))
                            clearCookie("college_selectAll")   //删除全选
                        }
                    }
                });
                //判断是否为全选 是则所有选择框为选中状态
                if(getCookie("college_selectAll")){
                    $("input[name='checkAll']").iCheck("check")
                }
                //判断当前的复选框是否被选中，是则为选中状态
                if(getCookie("college_selectArr")){
                    console.log(getCookie("college_selectArr").split(","))
                    var sp = getCookie("college_selectArr").split(",")
                    for(var i = 0 ; i < $("input[name='ids']").length ; i++){
                        for(var j = 0 ; j < sp.length;j++){
                            if($("input[name='ids']")[i].value == sp[j]){
                                $("#"+sp[j]).iCheck('check')
                            }
                        }
                    }
                }  */
                
               /* var $main_college = $("#main_college").text().split('、')
                for(var i = 0 ; i < $("#selectCollegeList input[name='ids']").length ; i++){
                //判断当前的复选框是否被选中，是则为选中状态
                    for(var i = 0 ; i < $("#selectCollegeList input[name='ids']").length ; i++){
                        for(var j = 0 ; j < sp.length;j++){
                            if($("#selectCollegeList input[name='ids']")[i].value == sp[j].innerHTML){
                                // console.log($("#"+sp[j].innerHTML))
                                $("#"+sp[j].innerHTML).iCheck('check')

                            }
                        }
                    }
                    //获取原先的名称，使其为勾选状态
                    for(var a = 0 ; a < $main_college.length; a++){
                        //getAttribute : 获取元素的属性值
                        if($("#selectCollegeList input[name='ids']")[i].getAttribute("data-name") == $main_college[a]){
                            $("#"+$("#selectCollegeList input[name='ids']")[i].getAttribute("id")).iCheck('check')
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
                $("#selectCollegeList input[name='ids']").on('ifChanged', function () {
                      // console.log(getCookie("college_selectArr"))
                      var arr = []
                      //选中状态
                    if ($(this).is(":checked")) {
                          //当Cookie中已经存有存放ID的数组，则在此基础上增加ID
                          if(getCookie("college_selectArr")){
                            arr = getCookie("college_selectArr").split(",")
                          }

                          var str = $(this).val()+":"+$(this).attr("data-name")  //将用户所选择的ID及学院名称存到cookie中

                          if($.inArray(str,arr) == -1){  //去重
                            // console.log($(this).attr("data-name"))
                            arr.push(str)
                            setCookie("college_selectArr",arr.join(","))
                          }
                          //用户勾选复选框时,判断当前页面所有复选框是否都已勾选中，是则将全选设为勾选状态
                          isSelectAll()
                    } else {  //反选状态
                        //查看Cookie中是否已存有用户选中的ID
                        if(getCookie("college_selectArr")){
                          //取消勾选时，查看全选选项是否被选中，是则将其取消掉
                          if($("#selectCollegeList input[name='checkAll']").is(":checked")){
                            $("#selectCollegeList input[name='checkAll']").iCheck("uncheck")
                          }
                          //删除cookie中存在的此选项ID
                          arr = getCookie("college_selectArr").split(",")
                          var str = $(this).val()+":"+$(this).attr("data-name")  //将用户所选择的ID及学院名称从cookie中移除

                          if($.inArray(str,arr) != -1){   //查看数组中是否有存在此选项ID，有则删除此选项
                            arr.splice($.inArray(str,arr),1)  //删除元素
                          }
                          setCookie("college_selectArr",arr.join(","))   //删除之后再重新设置Cookie
                        }   
                    }
                });

                //判断当前的复选框是否被选中，是则为选中状态
                if(getCookie("college_selectArr")){
                    var sp = getCookie("college_selectArr").split(",")
                    for(var i = 0 ; i < $("#selectCollegeList input[name='ids']").length ; i++){
                      for(var j = 0 ; j < sp.length;j++){
                        var id = sp[j].split(":")[0]
                        if($("#selectCollegeList input[name='ids']")[i].value == id){
                            $("#selectCollegeList #"+id).iCheck('check')
                        }
                      }
                    }
                    //获取cookie中的selectArr数组，遍历循环查看当前页面是否都被选中，是则将全选选项勾选中
                    isSelectAll()
                }
            var $main_college = $("#main_college").text().split('、')  //只有一个的时候??
            for(var i = 0 ; i < $("#selectCollegeList input[name='ids']").length ; i++){
                //获取原先的学院名称，使其为勾选状态
                if($("#main_college").children().length > 0){
                    $main_college = $("#main_college").children()
                    for(var a = 0 ; a < $main_college.length; a++){
                      var main_college_name = ""
                      //getAttribute : 获取元素的属性值
                      if($main_college[a].innerHTML.indexOf("、") != -1){
                        main_college_name = $main_college[a].innerHTML.slice(0,$main_college[a].innerHTML.indexOf("、"))
                      }else{
                        main_college_name = $main_college[a].innerHTML
                      }
                      if($("#selectCollegeList input[name='ids']")[i].getAttribute("data-name") == main_college_name){
                          $("#"+$("#selectCollegeList input[name='ids']")[i].getAttribute("id")).iCheck('check')
                      }
                  }
                }else {
                    for(var a = 0 ; a < $main_college.length; a++){
                      //getAttribute : 获取元素的属性值
                      if($("#selectCollegeList input[name='ids']")[i].getAttribute("data-name") == $main_college[a]){
                          $("#"+$("#selectCollegeList input[name='ids']")[i].getAttribute("id")).iCheck('check')
                        }
                    }
                }
            }
        }, "html");

    }
</script>