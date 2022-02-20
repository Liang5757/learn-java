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
    <style type="text/css">
        .col-sm-3{
          color:#a5a5a5;
        }
        .opColor{
          color: #333
        }
        
        .table .fs-up{
        display: inline-block;
        width: 14px;
        vertical-align: middle;
      }
      .table .arrow-up,.table .arrow-down,.table .selected-up,.table .selected-down{
        width: 14px;
        height: 8px;
        float: left;
      }
      .table .arrow-up{
        background: url('${base}/resources/themes/classic/images/arrow_up.png');
      }
      .table .arrow-down{
        background: url('${base}/resources/themes/classic/images/arrow_down.png');
      }
      .table .selected-up{
        background: url('${base}/resources/themes/classic/images/selected_up.png');
      }
      .table .selected-down{
        background: url('${base}/resources/themes/classic/images/selected_down.png');
      }
      
      
      .progress{
			  height: 25px;
			  background: #262626;
			  padding: 5px;
			  overflow: visible;
			  border-radius: 20px;
			  border-top: 1px solid #000;
			  border-bottom: 1px solid #7992a8;
			  margin-top: 50px;
		  }
		  .progress .progress-bar{
			  border-radius: 20px;
			  position: relative;
			  animation: animate-positive 2s;
		  }
		  .progress .progress-value{
			  display: block;
			  padding: 3px 7px;
			  font-size: 13px;
			  color: #fff;
			  border-radius: 4px;
			  background: #191919;
			  border: 1px solid #000;
			  position: absolute;
			  top: -40px;
			  right: -10px;
		  }
		  .progress .progress-value:after{
			  content: "";
			  border-top: 10px solid #191919;
			  border-left: 10px solid transparent;
			  border-right: 10px solid transparent;
			  position: absolute;
			  bottom: -6px;
			  left: 26%;
		  }
		  .progress-bar.active{
			  animation: reverse progress-bar-stripes 0.40s linear infinite, animate-positive 2s;
		  }
		  @-webkit-keyframes animate-positive{
			  0% { width: 0; }
		  }
		  @keyframes animate-positive{
			  0% { width: 0; }
		  }

    </style>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <form id="listForm" action="list.jhtml" method="get" name="form">
                    <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-3 m-b-xs">
                                <select name="filter_cId" id="cId" value="${(params.cId)!''}"
                                              class="bs-select form-control changeColor" >
                                              <option value="" [#if classId==""]selected[/#if]>请选择班级</option>
                                  [#list classList as class]
                                         <option value="${class.id}" [#if classId == class.id]selected[/#if] class="opColor">${class.className}</option>
                                  [/#list]
                                      
                                </select>
                          </div>
                          <div class="col-sm-3 m-b-xs">
                                <select name="filter_courseId" id="courseId" value="${(params.courseId)!''}" class="bs-select form-control changeColor">
                               		<option value="" [#if courseId==""]selected[/#if]>请选择课程</option>
                                  [#list courseList as course]
                                         <option value="${course.id}" [#if courseId == course.id]selected[/#if] class="opColor">${course.courseName}</option>
                                  [/#list]
                                </select>
                          </div>
                          <div class="col-sm-3 m-b-xs">
                                 <select name="filter_vId" id="vId" value="${(params.vId)!''}" class="bs-select form-control changeColor">
                                 	<option value="" [#if workingTableId==""]selected[/#if]>请选择作业表</option>
                                  [#list workingTableList as workingTable]
                                         <option value="${workingTable.id}"  [#if workingTableId == workingTable.id]selected[/#if] class="opColor">${workingTable.tableName}</option>
                                  [/#list] 
                                </select>
                          </div>
                           <div class="col-sm-1">
                                <div class="input-group">
                                    <button type="submit" class="btn btn-sm btn-danger" onclick="changePageNum()"> 查询</button>
                                </div>
                           </div>
                    </div>
                        <div>
                         	<a class="btn btn-outline btn-info btn-xs" id="btn-correct-loippi">按题目批改</a>
                         	<a class="btn btn-outline btn-info btn-xs" id="export">按学生导出答案</a>

								<div class="container" id="bar" style="display:none;">
									<div class="row">
										<div class="col-md-offset-3 col-md-6">
											<div class="progress">
												<div class="progress-bar progress-bar-info progress-bar-striped active" id="bluebar" style="width: 0%;">
													<div class="progress-value" id="barvalue">0%</div>
												</div>
											</div>
										</div>
									</div>
								</div>
													
                        </div>

                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>

                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
                                    <th>序号</th>
                                    <!-- <th>学号</th> -->
                                    <th><a href="list.jhtml?Name_flag=[#if Name_flag == 'asc' || Name_flag == null]desc[#elseif Name_flag == 'desc']asc[/#if]&filter_cId=${classId}&filter_courseId=${courseId}&filter_vId=${workingTableId}" style="color:#666">学号
                                      	<em class="fs-up"><i class="[#if Name_flag == 'desc' || Name_flag == null]arrow-up
                                      	[#elseif Name_flag == 'asc']selected-up[/#if]"></i><i class="[#if Name_flag == 'asc' || Name_flag == null]arrow-down
                                      	[#elseif Name_flag == 'desc']selected-down[/#if]"></i></em></a>
                                    </th>
                                    <th>姓名</th>
                                    <th>班级</th>
                                    <th>课程</th>
                                    <th>作业表</th>
                                    <th>通过题目数</th>
                                    <th>按学生批改</th>
                                </tr>
                                </thead>

                               <tbody id="record">
                               	  [#if page!=null]
                                  [#list maps as map]
                                    <tr>
                                        <td>
                                            <input type="checkbox" class="i-checks" name="ids" value="0" id="0">
                                        </td>
                                        <td>${map_index+1+(page.pageNumber-1)*20}</td>
                                        <td>${map.sno}</td>
                                        <td>${map.sname}</td>
                                        <td>${className}</td>
                                        <td>${courseName}</td>
                                        <td>${workingTableName}</td>
                                        <td>${map.pass}/${total}</td>
                                        <td style="text-align:center"><input type="button" class="btn btn-white" value="批改" 
                                        	id="exercise" onclick="checkexercise(${map.sid},
                                        		${classId},${courseId},${workingTableId})"></td>

                                    </tr>
                                  [/#list]
                                  [#else]
                                  [/#if]
                                </tbody>
                            </table>
                            [#if flag!=0]
                            [@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
                            [#include "/admin/include/pagination.ftl"]
                            [/@pagination]
                            [#else]
                            [/#if]
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
    $(document).ready(function () {
        [@flash_message /]
        $(".chosen-select").chosen({width: "100%"});

        //编辑用户
        $("#btn-correct-loippi").click(function(){
        	
			if($('#vId').val()== ""){
                art.warn("请先筛选出一个作业表，再批改此作业表的题目");
                return;
            }
            else if($('#cId').val()== ""){
                art.warn("请筛选出一个班级")
            }
            else{
            	location.href = "correct.jhtml?workingTableId="+ $(vId).val()+"&classId="+$(cId).val();
            }
            
        });
        
    
        // 复选框
        $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red'});
        // 选择所有，复选框改变
        $("input[name='checkAll']").on('ifChanged', function () {
            $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
            if ($(this).is(":checked")) {
                $.ajax({
                  type:"post",
                  url:"recordTeacher.jhtml",
                  data:{
                      flag:2
                  }
                })
            } else {
              $.ajax({
                type:"post",
                url:"recordTeacher.jhtml",
                data:{
                    flag:3
                }
              })
            }
        });

        //选择单个,点击事件
        $("input[name='ids']").on('ifClicked', function () {
            //选中状态
            if ($(this).is(":checked") == false) {
                $.ajax({
                  type:"post",
                  url:"recordTeacher.jhtml",
                  data:{
                      teacherID:$(this).val(),
                      flag:1
                  }

                })
            } else {
              //取消选中
              $.ajax({
                type:"post",
                url:"recordTeacher.jhtml",
                data:{
                    teacherID:$(this).val(),
                    flag:0
                }
              })
            }
        });

        //判断当前的复选框是否被选中，是则为选中状态
        var sp = $("#recordTeacherId").children()
        for(var i = 0 ; i < $("input[name='ids']").length ; i++){
         for(var j = 0 ; j < sp.length;j++){
              if($("input[name='ids']")[i].value == sp[j].innerHTML){
                  console.log($("#"+sp[j].innerHTML))
                  $("#"+sp[j].innerHTML).iCheck('check')

              }
          }
        }

        //页面加载时，没有任何记录则禁用题目批改功能
        if($("#record").children().length <= 0){
            $("#btn-correct-loippi").attr("disabled","true")
            //将按钮颜色设为不可用的灰色
            $("#btn-correct-loippi").css("background-color","#cdd6d6")
            $("#btn-correct-loippi").css("border-color","#cdd6d6")
            $("#btn-correct-loippi").css("color","#999")
            
           	$("#export").attr("disabled","true")
            $("#export").css("background-color","#cdd6d6")
            $("#export").css("border-color","#cdd6d6")
            $("#export").css("color","#999")
        }

    });

    //点击更改选择框字体颜色(默认为浅灰色)
    $(".changeColor").change(function (argument) {
        if($(this).val()){
          $(this).css("color","#333")
          $($(this).children()[0]).css("color","#a5a5a5")
        }else{
          $(this).css("color","#a5a5a5")
        }
    })

    function checkexercise(sid,cid,courseid,vid){
    	location.href = "${base}/admin/exercise/selectlist.jhtml?cId="+cid+"&courseId="+courseid+"&vId="+vid+"&sid="+sid+"&flag=0"
    }

    $("#cId").change( function () {
        var classId=$("#cId").val();
        $("#courseId").empty();
        $("#vId").empty();
        if (classId!=null&&classId!=""){
            $.ajax("${base}/admin/exercise/getChangeajax.jhtml",
                    {
                type:"POST",
                datatype:'json',
                data:{
                "parentId":classId, "flag":"C"
            },
            cache:false,
            success:function (data) {
                if ("success"==data.result){
                    if (data.courselist !=null&&data.courselist.length>0){
						$("#courseId").append("<option value = \"" +""+ "\">"+ "请选择课程"+"</option>");
                        for(var i=0;i<data.courselist.length;i++){
                            var course=data.courselist[i];
                            var key=(course.id==null?"":course.id);
                            var value=(course.courseName==null? "":course.courseName);
                            
                            $("#courseId").append("<option value = \"" + key + "\">"+ value +"</option>");
                        }
                    }
                }

            },
            error:function(XMLHttpRequest, textStatus, errorThrown){

                art.warn("出错");

            }
        })
        }
    });
    $("#courseId").change( function () {

        var courseId=$("#courseId").val();
        $("#vId").empty();
        if (courseId!=null&&courseId!=""){
            $.ajax("${base}/admin/exercise/getChangeajax.jhtml",{
                type:"POST",
                datatype:'json',
            data:{
                "parentId":courseId,
                        "flag":"W"
            },
            cache:false,
            success:function (data) {
                if ("success"==data.result){
                    if (data.workingtablelist !=null&&data.workingtablelist.length>0){
                        for(var i=0;i<data.workingtablelist.length;i++){
                            var workingtable=data.workingtablelist[i];
                            var key=(workingtable.id==null?"":workingtable.id);
                            var value=(workingtable.tableName==null? "":workingtable.tableName);
                            $("#vId").append("<option value = \"" + key + "\">"+ value +"</option>");
                        }
                    }
                }

            },
            error:function(XMLHttpRequest, textStatus, errorThrown){

                art.warn("出错");

            }
        })
        }
    });
    
        
    $("#export").click(function(){	
    	window.location="${base}/admin/exercise/exportByStudentOne.jhtml?cid=${classId}&coid=${courseId}&vid=${workingTableId}";
		$("#bar").css("display","inline");
        
        var onProcess = function(){
        	setTimeout(function(){
        		$.ajax({
		            url:"${base}/admin/exercise/getProcess.jhtml",
		            success:function(data){
       					$("#bluebar").css("width",data.process +'%');
    					$("#barvalue").text(data.process+'%');
    						
		            }
	        	})        	
	        	if($("#barvalue").text()=="100%"){
    				$("#bar").css("display","none");
    				art.warn("下载完成!");
    				return;
    			}
    			onProcess();
        	},500);
        }
        
        onProcess();
    })
        
</script>
</body>

</html>
