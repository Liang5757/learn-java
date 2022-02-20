
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
                            <h5>选择学校</h5>

                            <div class="ibox-tools">
                                <button type="button" class="btn btn-white" onclick="window.history.back();"
                                        style="margin-top: -8px;">返回
                                </button>
                               <!--  <a class="btn btn-white" href="${base}/admin/school/edit.jhtml?id=1" style="margin-top: -8px;">返回</a> -->
                            </div>
                </div>
                    <div class="ibox-content">
                      <form id="listForm" action="${base}/admin/school/editSchoolContent.jhtml">
                        <div class="row">
                            <div class="col-sm-3 m-b-xs"><input  id="filter_schoolName" type="text" placeholder="请输入学校名称" name="filter_schoolName" value="${(params.schoolName)!''}" class="input-sm form-control">
                            </div>
                        	 <div class="col-sm-3 m-b-xs"><select id="filter_schoolAddress" name="filter_schoolAddress" class="input-sm form-control">
			                          [#if params.schoolAddress??]
			                          		 <option value="${(params.schoolAddress)!''}">${(params.schoolAddress)!''}</option>
								      [#else] 
			                          		<option value="">全部省份</option>
			                          [/#if]
			                           [#list schoolAddress as address]
								        	         <option value="${address}">${address}</option>
								       	         [/#list]
								               </select>
                            </div>
                            <div class="col-sm-1">
                                <div class="input-group">
                                   <button type="submit" class="btn btn-sm btn-danger" onclick="changePageNum()"> 查询</button> </span>

                                </div>
                            </div>
                        </div>
                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>

                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
	                                   <td>序号</td>
	                                   <td>学校</td>
	                                   <td>所在省份</td>
                                </tr>
                                </thead>
                                <tbody>
                                [#list page.content as school]
                                <tr>
                                    <td>

                                    	[#if Selected??]
	                                   		 <input type="checkbox" class="i-checks" name="ids" value="${school.id}"
	                                   		[#list Selected as schoolSelect]
	                                   		[#if schoolSelect.id=school.id]
	                                        	 checked="checked"
	                                   		[/#if]
	                                    	[/#list] id="${school.id}">
                                        <!-- <input type="checkbox" class="i-checks" name="ids" value="${school.id}" id="${school.id}"> -->
                               		[#else]
                               			<input type="checkbox" class="i-checks" name="ids" value="${school.id}" >
                                   	[/#if]

                                    </td>
										<td>${school_index + 1}</td>
										<td>${school.schoolName}</td>
										<td>${school.schoolAddress}</td>
                                </tr>
                                [/#list]
                                </tbody>
                            </table>
                            [@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
                            [#include "/admin/include/pagination.ftl"]
                            [/@pagination]
                             <div class="ibox-tools">
		                         <input type="button" class="btn btn-danger" id="submitBtn"
		                                style="margin-top: -20px;" value="确定">
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
  var submitBtn = document.getElementById('submitBtn')
	submitBtn.onclick = function () {
	  var $checkeds = jQuery($("input[name='ids']:checked"));
      if($checkeds.length == 1){
        setCookie("edit_SchoolName",$(($checkeds)[0]).attr("id"))
        location.href="${base}/admin/school/sureEditSchool.jhtml?schoolId="+$($checkeds[0]).val()
      }else if($checkeds.length > 1){
        art.warn("最多只可以勾选一条数据");
        return false;
      }else{
      	art.warn("请至少勾选一条数据");
        return false;
      }

	}

  $(document).ready(function () {
    //初始化复选框
    $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});
    if(getCookie("edit_SchoolName")){
      for (var i = 0; i < $("input[name='ids']").length; i++) {
         if(getCookie("edit_SchoolName") == $($("input[name='ids']")[i]).attr("id")){
            $($("input[name='ids']")[i]).iCheck("check")
         }
      }
    }
  })

</script>
</body>

</html>
