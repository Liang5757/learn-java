
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
                <form id="listForm" action="list.jhtml" method="get">
                    <div class="ibox-content">
                    <div class="row">
                            <div class="col-sm-3 m-b-xs hidden" >
                                <input type="text" name="filter_teacherId" value="${(params.teacherId)!''}" class="input-sm form-control">
                            </div>
                            <div class="col-sm-3 m-b-xs">
                                <input type="text" placeholder="请输入考试名称" name="filter_epName" value="${(params.epName)!''}" class="input-sm form-control">
                            </div>
                     		<div class="col-sm-1">
                                <div class="input-group">
                                    <button type="submit" class="btn btn-sm btn-danger" onclick="changePageNum()"> 查询</button> </span>
                                </div>
                            </div>
                    </div>
                        <div>
                            <a class="btn btn-outline btn-success btn-xs" id="btn-add-loippi" href="add.jhtml"><i
                                    class="fa fa-plus"></i> 添加</a>

                            <a class="btn btn-outline btn-info btn-xs" id="btn-edit-loippi"><i class="fa fa-paste"></i> 修改</a>

                           	<a class="btn btn-outline btn-danger btn-xs btn-delete-loippi-group"><i
                                    class="fa fa-trash"></i> 删除</a>
                        </div>

                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>

                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
                                    <th>序号</th>
                                    <th>考试名称</th>
                                    <th>班级</th>
                                    <th>课程</th>
                                    <th>作业表</th>
                                    <th>开始方式</th>
                                    <th>开始时间</th>
                                    <th>持续时间</th>
                                    <th>考试状态</th>
                                    <th>操作</th>
                                    <th>更新者</th>
                                    <th>更新时间</th>
                                </tr>
                                </thead>

                               <tbody>
                                [#list page.content as examplan]
                                <tr>
                                    <td>
                                        <input type="checkbox" class="i-checks" name="ids" value="${examplan.epId}" id="${examplan.epId}">
                                    </td>
                                    <td>${examplan_index + 1}</td>
                                    <td><a href="edit.jhtml?id=${examplan.epId}" style="color:#23c6c8">${examplan.epName}</a></td>
                                    <td>${examplan.classSystem.className}</td>
                                    <td>${examplan.course.courseName}</td>
                                    [#if examplan.workingTable != null]
                                    <td>${examplan.workingTable.tableName}</td>
                                    [#else]
                                    <td></td>
                                    [/#if]
                                    <td>
                                    	[#if examplan.kind==0]手动[/#if]
                                    	[#if examplan.kind==1]自动[/#if]
                                    </td>
                                    <td>[#if examplan.startTime??]${examplan.startTime?string('yyyy-MM-dd HH:mm:SS')}[/#if]</td>
                                    <td>${examplan.duration}分钟</td>
                                    <td style="color:[#if examplan.examStatus == 1]green[#elseif examplan.examStatus == 2]red[/#if]" class="checkStatus" id="checkStatus">[#if examplan.examStatus == 0]未开始[#elseif examplan.examStatus == 1]进行中[#elseif examplan.examStatus == 2]暂停中[#elseif examplan.examStatus == 3]已结束[/#if]</td>
                                    [#if examplan.kind==1]
                                      <td>/</td>
                                    [#else]
                                      <td><a href="javascript:;" style="cursor:pointer;color:[#if examplan.operation == 1]red[#else]green[/#if]" onclick="changeStatus(this)">[#if examplan.operation == 0]开始[#elseif examplan.operation == 1]暂停[#elseif examplan.operation == 2]继续[#elseif examplan.operation == 3]重新开始[/#if]</a></td>
                                    [/#if]
                                    <td>${examplan.update_person}</td>
                                    <td>[#if examplan.update_date??]${examplan.update_date?string('yyyy-MM-dd HH:mm:SS')}[/#if]</td>
                                    <td class="hidden">${examplan.epId}</td>
                                </tr>
                                [/#list]
                                </tbody>
                            </table>
                            [@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
                            [#include "/admin/include/pagination.ftl"]
                            [/@pagination]
                        </div>
                    </div>
                    <input class="hidden" name="recordflag" value="0">
                </form>
                <div id="recordTeacherId" class="hidden">
                  [#list recordTeacherId as teacherId]
                      <span>${teacherId}</span>
                  [/#list]
                </div>
            </div>
        </div>
    </div>
</div>

<!-- BEGIN Script -->
[#include "/admin/include/script.ftl"]
<!-- END Script -->
<script>
    "use strict";
    $(document).ready(function () {
        [@flash_message /]
        $(".chosen-select").chosen({width: "100%"});

        //编辑用户
        $("#btn-edit-loippi").click(function(){
            var $checkeds = jQuery($("input[name='ids']:checked"));
            if($checkeds.length > 1){
                art.warn("每次只能选择一条记录进行修改");
                return;
            }else if($checkeds.length == 0){
                art.warn("请选择一条记录进行修改");
                return;
            }
            location.href = "edit.jhtml?id=" + $($checkeds[0]).val();
        });

        // //初始化密码
        // $("#btn-initialize-loippi").click(function(){
        //     // var $checkeds = jQuery($("input[name='ids']:checked"));
        //     // var ids = []
        //     // if($checkeds.length > 0){
        //     //     for(var i = 0;i<$checkeds.length;i++){
        //     //         ids.push($($checkeds[i]).val())
        //     //     }
        //     //     location.href = "initialize.jhtml?ids=" + ids;
        //     // }else{
        //     //     art.warn("请至少选择一条记录进行重置密码")
        //     // }
            
        //     if(getCookie("selectArr")){
        //         var resetIds = getCookie("selectArr").split(",")
        //         clearCookie("selectArr")
        //         clearCookie("selectAll")
        //         location.href = "initialize.jhtml?ids=" + resetIds
        //     }else{
        //         art.warn("请至少选择一条记录进行重置密码")
        //     }
        // });

       // // 复选框
       //  $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red'});
       //  // 选择所有，复选框改变
       //  $("input[name='checkAll']").on('ifChanged', function () {
       //      $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
       //      if ($(this).is(":checked")) {
       //          $.ajax({
       //            type:"post",
       //            url:"recordTeacher.jhtml",
       //            data:{
       //                flag:2
       //            }
       //          })
       //      } else {
       //        $.ajax({
       //          type:"post",
       //          url:"recordTeacher.jhtml",
       //          data:{
       //              flag:3
       //          }
       //        })
       //      }
       //  });

       //  //选择单个,点击事件
       //  $("input[name='ids']").on('ifClicked', function () {
       //      //选中状态
       //      if ($(this).is(":checked") == false) {
       //          $.ajax({
       //            type:"post",
       //            url:"recordTeacher.jhtml",
       //            data:{
       //                teacherID:$(this).val(),
       //                flag:1
       //            },
       //            success:function (data) {
       //                console.log(data)
       //            }

       //          })
       //      } else {
       //        //取消选中
       //        $.ajax({
       //          type:"post",
       //          url:"recordTeacher.jhtml",
       //          data:{
       //              teacherID:$(this).val(),
       //              flag:0
       //          },
       //          success:function (data) {
       //              console.log(data);
       //          }
       //        })
       //      }
       //  });

       //  //判断当前的复选框是否被选中，是则为选中状态
       //  var sp = $("#recordTeacherId").children()
       //  for(var i = 0 ; i < $("input[name='ids']").length ; i++){
       //   for(var j = 0 ; j < sp.length;j++){
       //        if($("input[name='ids']")[i].value == sp[j].innerHTML){
       //            console.log($("#"+sp[j].innerHTML))
       //            $("#"+sp[j].innerHTML).iCheck('check')

       //        }
       //    }
       //  }

        //检测状态是否改变
        var $Status = $(".checkStatus")
        var $StatusTr
        var statusArr = ["未开始","进行中","暂停中","已结束"]
        if($Status.length > 0){
            //页面加载时，设置定时器
            let autoInterval1 = setInterval(function () {
                $.ajax({
                    type:"post",
                    url:"getStatus.jhtml",
                    data:{},
                    success:function (data) {
                        if(data.length>0){
                          data.forEach(obj=>{
                             for (var i = 0; i < $Status.length; i++) {
                                var $examplanTr = $($Status[i]).parent()
                                console.log($Status[i].innerHTML)
                                // console.log($examplanTr.children('td:last-child').html())
                                // console.log(statusArr[obj.examStatus])
                                if(statusArr[obj.examStatus] != $Status[i].innerHTML && (obj.epId).toString() == $examplanTr.children('td:last-child').html()){
                                    location.href="${base}/admin/examplan/list.jhtml"
                                }
                             }
                          })
                        }
                        
                    }
                })
            },10000)
            
            // for (var i = 0; i < $startMode.length; i++) {
                // if($startMode[i].innerHTML.indexOf("自动") >= 0){
                //     $examplanTr = $($startMode[i]).parent()
                //     let autoST = new Date($examplanTr.children('td').eq(7).html()).getTime(),
                //         autoDurationT = parseInt($examplanTr.children('td').eq(8).html().replace(/[^0-9]+/g, ''))*60*1000
                    
                //     if(new Date().getTime() > autoST && new Date().getTime() < (autoST+autoDurationT)){
                //         // 当前时间大于开始时间并且小于开始时间加上持续时间时，考试状态为“进行中”
                //         $examplanTr.children('td').eq(9).html("进行中")
                //         $($examplanTr.children('td').eq(9)).css("color","green")
                //         var autoInterval = setInterval(function () {
                //             if(new Date().getTime() >= (autoST+autoDurationT)){
                //                 $examplanTr.children('td').eq(9).html("已结束")
                //                 $($examplanTr.children('td').eq(9)).css("color","#666")
                //                 clearInterval(autoInterval)
                //             }
                //         })
                //     }else if(new Date().getTime() >= (autoST+autoDurationT)){
                //         //当前时间大于开始时间加上持续时间时，考试状态为"已结束"
                //         $examplanTr.children('td').eq(9).html("已结束")
                //         $($examplanTr.children('td').eq(9)).css("color","#666")
                //     }else{
                //         //页面加载时，上面两种可能不满足则设置定时器
                //         let autoInterval1 = setInterval(function () {
                //             if(new Date().getTime() > autoST && new Date().getTime() < (autoST+autoDurationT)){
                //                 $examplanTr.children('td').eq(9).html("进行中")
                //                 $($examplanTr.children('td').eq(9)).css("color","green")
                //             }else if(new Date().getTime() >= (autoST+autoDurationT)){
                //                 $examplanTr.children('td').eq(9).html("已结束")
                //                 $($examplanTr.children('td').eq(9)).css("color","#666")
                //                 clearInterval(autoInterval1)
                //             }
                //         },5000)
                //     }
                // }
            // }
        }

        //手动或者自动时，时间开始，不可修改
        if($("#checkStatus").html()){
            if($("#checkStatus").html().indexOf("未开始") == -1){
                $("#btn-edit-loippi").prop("disabled",true)
            }
        }
    });

    //定义全局变量定时器
    var interval;
    //定义暂停时，已走了的时间
    var pauseTime;

    //点击操作 更改考试状态
    function changeStatus(e) {
      var $Tr = $($(e).parent().parent())
      if(e.innerHTML == "开始" || e.innerHTML == "重新开始"){
        $.ajax({
            type:"post",
            url:"update.jhtml",
            data:{
                examPlanId:$Tr.children('td:last-child').html(),
                examStatus:1,
                operation:1,
                startTime:new Date().getTime()
            },
            success:function (data) {
                location.href="${base}/admin/examplan/list.jhtml"
            }
        })
      }else if(e.innerHTML == "暂停"){
        $.ajax({
            type:"post",
            url:"update.jhtml",
            data:{
                examPlanId:$Tr.children('td:last-child').html(),
                examStatus:2,
                operation:2,
                startTime:""
            },
            success:function () {
                location.href="${base}/admin/examplan/list.jhtml"
            }
        })
        

      }else if(e.innerHTML == "继续"){
        $.ajax({
            type:"post",
            url:"update.jhtml",
            data:{
                examPlanId:$Tr.children('td:last-child').html(),
                examStatus:1,
                operation:1,
                startTime:""
            },
            success:function (data) {
                location.href="${base}/admin/examplan/list.jhtml"
            }
        })
      }
    }

    //设置手动计时器
    // function countTime(startT,duration,$Tr,e) {
    //     // console.log(startT+duration)
    //     // console.log(new Date().getTime())
    //     // console.log(new Date().getTime() >= (startT+duration))
    //     if(new Date().getTime() >= (startT+duration)){
    //         clearInterval(interval);  //清除定时器
    //         //考试状态
    //         $Tr.children('td').eq(9).html("已结束")
    //         $($Tr.children('td').eq(9)).css("color","#666")
    //         // 操作
    //         $(e).html("重新开始")
    //         $(e).css("color","green")
    //         return;
    //     }
        
        
    // }
</script>
</body>

</html>
