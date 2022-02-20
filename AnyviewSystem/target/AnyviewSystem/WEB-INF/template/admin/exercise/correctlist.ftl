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
    <style>
    .table tbody tr td{
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
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
                <form id="listForm" action="correct.jhtml" method="get">
                
                	<div class="ibox-title">
                    	<div class="ibox-tools">
                    		
                    	<button class="btn btn-white" type="button" id="exportOne" style="margin-top: -8px;">按题目导出答案</button>
                    		
                    	<button class="btn btn-white" type="button"onclick="window.location='${base}/admin/exercise/list.jhtml'" 
                    		style="margin-top: -8px;">返回</button>

                    	</div>

                    	<h5>按题目批改</h5>
                	</div>
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
                
                    <div class="ibox-content">
                    

                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable" style='table-layout:fixed;'>
                                <thead>
                                <tr>

                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
                                    <th>序号</th>
                                    <th>题目名称</th>
                                    <th>章节</th>
                                    <th style='width:38%;'>题目描述</th>
                                    <th>通过人数</th>
                                    <th>难度</th>
                                    <th>操作</th>
                                </tr>
                                </thead>

                               <tbody>
                                [#list page.content as question]
                                <tr>
                                    <td>
                                        <input type="checkbox" class="i-checks" name="ids" value="${question.id}" id="${question.id}">
                                    </td>
                                    <td>${question_index + 1+(page.pageNumber-1)*20}</td>
                                    <td>${question.question_name}</td>
                                    <td>${question.chapter}</td>
                                    <td><a title="${questionContentList[question_index+(page.pageNumber-1)*20].question_description}">${questionContentList[question_index+(page.pageNumber-1)*20].question_description}</a></td>
                                    <td>${pass[question_index+(page.pageNumber-1)*20]}/${total}</td>
                                    <td>${question.difficulty}</td>
                                    <td style="text-align:center"><a class="btn btn-white" href="${base}/admin/exercise/stulist.jhtml?id=${question.id}" >批改</a></td>
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
    $(document).ready(function () {
        [@flash_message /]
        $(".chosen-select").chosen({width: "100%"});

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
                  $("#"+sp[j].innerHTML).iCheck('check')

              }
          }
        }

    });        
      $("#exportOne").click(function(){	
    	window.location="${base}/admin/exercise/exportByProblemOne.jhtml?cid=${classId}&vid=${vId}";
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
