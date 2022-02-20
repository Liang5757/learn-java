<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>${setting.siteName} - 主页</title>
    <meta name="keywords" content="${setting.siteName}">
    <meta name="description" content="${setting.siteName}">
<style>
    #left {
        float: left;
    }
    #right {
        float: right;
    }

</style>
    <!-- BEGIN HEADER -->
[#include "/admin/include/style.ftl"]
    <!-- END HEADER -->
</head>

<body class="gray-bg">
    <div>
        <div>
            <form id="importlertForm" action="importleftProblems.jhtml" method="post" enctype="multipart/form-data">
                <div id="left">
                    <h3 id="leftFileName">左边容器</h3>
                    <textarea id="leftTextArea" name="leftTextArea" rows="20" cols="70" placeholder="请导入文件" readonly >${text}</textarea>
                    <div class="row">
                        <div class="col-sm-6">
                            <label>导入.c文件<span class="required"> * </span>:</label>
                            <div class="col-sm-9">
                                <div id="file-pretty">
                                    <div class="form-group" style="padding-left: 5px;padding-right: 5px;">
                                        <input type="file" name="c1File" id="c1File" class="form-control"value="filename2">
                                        <button type="submit" >确定</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
<form id="importrightForm" action="importrightProblems.jhtml" method="post" enctype="multipart/form-data">
                <div id="right">
                    <h3 id="rightFileName">右边容器</h3>
                    <textarea id="rightTextArea" name="rightTextArea" rows="20" cols="70" placeholder="请导入文件" readonly>${text2}</textarea>
                    <div class="row">
                        <div class="col-sm-6">
                            <label>导入.c文件<span class="required"> * </span>:</label>
                            <div class="col-sm-9">
                                <div id="file-pretty">
                                    <div class="form-group" style="padding-left: 5px;padding-right: 5px;">
                                        <input type="file" name="c2File" id="c2File" class="form-control"  value="filename2">
                                        <button type="submit" >确定</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
</form>
    </div>
        <form id="importForm" action="compareCodeAjax.jhtml" method="post" enctype="multipart/form-data" onsubmit="return validate();">
     <div style="margin:0px auto;text-align:center;">
           <div class="button" style="margin-bottom: 50px;">
                    <div class="buttonContent">
                        <input type="submit" value="开始检测" class="btn btn-sm btn-danger"></input>
                    </div>
           </div>
            <div>
                <a class="btn btn-outline btn-info btn-xs"  href="batch.jhtml">批量检测</a>
            </div>
         <div class="dataTables_wrapper form-inline">
         <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
             <thead>
             <tr>
                 <th>文件一</th>
                 <th>文件二</th>
                 <th>相似度</th>
                 <th>相同字符数</th>
                 <th>平均值</th>
                 <th>特征值</th>
                 <th>查看</th>
             </tr>
             </thead>

             <tbody>
                <tr>
                    <td>${file1name}</td>
                    <td>${file2name}</td>
                    <td>${similarity}</td>
                    <td>${count}</td>
                    <td>${avg}</td>
                    <td>${cluster}</td>
                    <td style="text-align:center"><input type="button" class="btn btn-white" value="查看报告" id="report" onclick="openReport()"></td>
                </tr>
             </tbody>
         </table>
         </div>
        </form>
</div>

<!-- BEGIN Script -->
[#include "/admin/include/script.ftl"]
<!-- END Script -->
<script>
$(document).ready(function () {
 [@flash_message /]
    $(".chosen-select").chosen({width: "100%"});
});

<!-- 查看检测报告 -->
function openReport(){
window.open('${base}/DetectionResult/detection1/index.html','newwindow');
};

function validate() {
    if($('#leftTextArea').val()==""||$('#rightTextArea').val()==""){
        art.warn("请导入文件");
        return false;
    }
    else return true;
}
</script>
</body>

</html>
