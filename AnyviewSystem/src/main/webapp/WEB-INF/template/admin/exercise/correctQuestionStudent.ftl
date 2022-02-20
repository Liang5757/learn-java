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
						<button class="btn btn-white" type="button"onclick="window.history.back();" style="margin-top: -8px;">返回</button>
                    </div>

                    <h5>按学生批改</h5>

                </div>
                <div class="ibox-content">
                    <form id="saveCorrectForm" class="form-horizontal" action="correctSave.jhtml" method="post">


                        <div class="form-group showWorkTable">
                       		<div class="row">
                                <div class="col-sm-6" style="display:none">
                                    <label class="col-sm-3 control-label">标志位<span
                                            class="required"> * </span></label>

                                    <div class="col-sm-9">
                                        <input type="text" name="flag" id="eId" class="flag" maxlength="200"
                                               value="${flag}" readonly="readonly"/>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                        	<div class="row">
                                <div class="col-sm-6" style="display:none">
                                    <label class="col-sm-3 control-label">批改ID<span
                                            class="required"> * </span></label>

                                    <div class="col-sm-9">
                                        <input type="text" name="eId${map.index}" id="eId" class="form-control"
                                               maxlength="200"
                                               value="${map.index}" readonly="readonly"/>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2 control-label">序号:</label>

                                    <div class="col-sm-9">
                                        <label class="col-sm-6 control-label" name="xh" id="xh">${map.index+1}</label>                                    
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                          
                            <div class="row">
                            <!-- if-->
                                <div class="col-sm-6">
                                    <label class="col-sm-2 control-label">学号:</label>

                                    <div class="col-sm-9">
                                        <label class="col-sm-6 control-label">${map.username}</label>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                           <div class="row">
                               <div class="col-sm-6">
                                   <label class="col-sm-2 control-label">姓名:</label>

                                   <div class="col-sm-9">
                                       <label class="col-sm-6 control-label">${map.name}</label>
                                   </div>
                               </div>
                           </div>
                            &nbsp;
                          <!-- if-->
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2 control-label">题目描述:</label>

                                    <div class="col-sm-9">
                                    [#--<label class="col-sm-9 control-label">${questionContentList[exercise_index].question_description}</label>--]
                                        <textarea id="description" class="col-sm-9" rows="${(map.description?length / 18)?int}" style="border: none;resize:none;width:600px;"
                                                  readonly>${map.description}</textarea>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2 control-label">标准答案:</label>

                                    <div class="col-sm-9">
                                        <textarea id="standard" class="col-sm-9" rows="${(map.standard?length / 17)?int}" style="border: none;resize:none;width:600px;"
                                                  readonly>${map.standard}</textarea>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2 control-label">学生答案:</label>
                                    <div class="col-sm-9">
                                    [#--<label class="col-sm-9 control-label">${questionContentList[exercise_index].student_answer}</label>--]
                                    [#--    <textarea class="col-sm-9" rows="${(questionContentList[exercise_index].student_answer?length / 16)?int}" style="border: none;resize:none"
                                                  readonly>${questionContentList[exercise_index].student_answer}</textarea> --]
                                        <textarea id="answer" class="col-sm-9" rows="${(map.studentAnswer?length / 17)?int}" style="border: none;resize:none;width:600px;"
                                                  readonly>${map.studentAnswer}</textarea>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2 control-label">通过<span class="required"></span>:</label>
                                    <div class="col-sm-4" style="margin-top:7px">
                                        <p id=pass>[#if map.pass == 0]否[#elseif map.pass == 1]是[#else]/[/#if]</p>
                                    </div>
                                </div>
                            </div>
                          &nbsp;
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-2 control-label">评语<span class="required"></span>:</label>
                                    <div class="col-sm-9 form-inline">
                                        <textarea rows="6" cols="60" placeholder="请填写评语" id="eComment"
                                                  name="eComment${map.index}"
                                        >${map.comment!""}</textarea>
                                    </div>                                
                                </div>
                            </div>
                        </div>
                        <!--/row-->

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-2">
                            	[#if flag==0 ]
                                <input class="btn btn-outline btn-success" type="button" id="prev" value="上一题">
                                [/#if]
                            </div>
                            <div class="col-sm-2">
                                <input type="button" class="btn btn-danger" id="save0" value="确定"></input>                
                            </div>
                            <div class="col-sm-2">
                                [#if flag==0 ]
                                <input class="btn btn-outline btn-success" type="button" id="next" value="下一题"></input>
                                [/#if]
                            </div>
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

    var $workTable = $(".showWorkTable")  //获取所有的作业列表

    $(document).ready(function () {
        [@flash_message /]
        $(".chosen-select").chosen({width: "100%"});
        $(".input-daterange").datepicker({keyboardNavigation: false, forceParse: false, autoclose: true});
        $(".browserButton").browser({
            callback: function (url) {
                $("#" + $(this).attr('for')).val(url);
            }
        });
		 
        $(".input-group.date").datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
        
        $($workTable[0]).css("display", "none");
        $($workTable[${index}]).css("display", "block");

    });

  
</script>
</body>
<script>
	eindex=-1;
	nindex=-1;
	pindex=-1;
	sid=-1;
	cid=-1;
	
 	$("#next").click(function () {
 		if(eindex==-1){
 			eindex=${eindex};
 			nindex=${nindex};
 			pindex=${pindex};
 			sid=${sid};
 			cid=${cid};
 		}
 	
		if(nindex==eindex){
			art.warn("后面没有了哦");
			return;
		}
		else{
			$.ajax({
				type:"GET",
				url :"jump.jhtml",
				data:{eindex:nindex,sid:sid,cid:cid},
				success:function(result){
					$("#xh").html(result.eindex+1);
					$("#pass").text(result.pass);
					$("#eComment").val(result.comment);
					$("#description").text(result.description);
					$("#answer").text(result.answer);
					$("#standard").text(result.standard);
					
 					eindex=result.eindex;
 					nindex=result.nindex;
 					pindex=result.pindex;	
 					
 					window.scrollTo(0,0);				
				}
			}
			)
		}
    }) 

	$("#prev").click(function () {
 		if(eindex==-1){
 			eindex=${eindex};
 			nindex=${nindex};
 			pindex=${pindex};
 			sid=${sid};
 			cid=${cid};
 		}
 	
		if(pindex==eindex){
			art.warn("前面没有了哦");
			return;
		}
		else{
			$.ajax({
				type:"GET",
				url :"jump.jhtml",
				data:{eindex:pindex,sid:sid,cid:cid},
				success:function(result){
					$("#xh").html(result.eindex+1);
					$("#pass").text(result.pass);
					$("#eComment").val(result.comment);
					$("#description").text(result.description);
					$("#answer").text(result.answer);
					$("#standard").text(result.standard);
					
 					eindex=result.eindex;
 					nindex=result.nindex;
 					pindex=result.pindex;	
 					
 					window.scrollTo(0,0);				
				}
			}
			)
		}
    })
    
     $("#save0").click(function () {
     	 if(eindex==-1){
 			eindex=${eindex};
 			nindex=${nindex};
 			pindex=${pindex};
 			sid=${sid};
 			cid=${cid};
 		}
     
 		if($("#pass").text()=="/"){
 			art.warn("还没有提交，先不着急写评语吧");
 			return;
 		}
 		else{	
 		 	$.ajax({
     			type:"POST",
     			url:"save0.jhtml",
     			data:{eindex:eindex,sid:sid, cid:cid, comment:$("#eComment").val()},
     			success:function(result){
     				art.warn("评价成功");
     				window.scrollTo(0,0);
     			}
     		
     		})	
 		}
     })
 
</script>
</html>
