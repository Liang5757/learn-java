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
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <form id="listForm" action="batchDetection.jhtml" method="post" enctype="multipart/form-data">
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-3 m-b-xs">
                                <input type="file" id="in" name="in" webkitdirectory ><span>输入路径</span>
                            </div>
                            <div class="col-sm-1">
                                <div class="input-group">
                                    <button type="submit" id="start" class="btn btn-sm btn-danger">开始检测</button> </span>
                                </div>
                            </div>
                        </div>
                        <div>
                            <a class="btn btn-outline btn-info btn-xs" id="btn-delete" href="batch.jhtml">清空数据</a>
                        </div>

                        <div class="dataTables_wrapper form-inline">
                            <table class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>
                                    <th>文件一</th>
                                    <th>文件二</th>
                                    <th><a href="sort.jhtml?similarflag=[#if similarflag == 'asc' || similarflag == null]desc[#elseif similarflag == 'desc']asc[/#if]" style="color:#666">相似度<em class="fs-up"><i class="[#if similarflag == 'desc' || similarflag == null]arrow-up[#elseif similarflag == 'asc']selected-up[/#if]"></i><i class="[#if similarflag == 'asc' || similarflag == null]arrow-down[#elseif similarflag == 'desc']selected-down[/#if]"></i></em></a></th>
                                    <th>相同字符数</th>
                                    <th>平均值</th>
                                    <th>特征值</th>
                                    <th>位置</th>
                                    <th>查看</th>
                                </tr>
                                </thead>

                                <tbody>
                                [#list result as result]
                                <tr>
                                    <td>${result.fileAname}</td>
                                    <td>${result.fileBname}</td>
                                    <td>${result.similarity}%</td>
                                    <td>${result.count}</td>
                                    <td>${result.avg}</td>
                                    <td>${result.cluster}</td>
                                    <td>${result.outputLocal}</td>
                                    <td style="text-align:center"><input type="button" class="btn btn-white" value="查看报告" id="report" onclick="openReport(${result.id})"></td>
                                </tr>
                                [/#list]
                                </tbody>
                            </table>

                        </div>
                    </div>
                </form>
                <div id="recordschemeContent" class="hidden">
                  [#list recordschemeContent as schemeContent]
                      <span>${schemeContent}</span>
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

     $("#start").click(function () {
         var file=$("#in").val();
        if (file==''){
            alert("请选择文件夹");
            return;
        }

     })

    });


    //
    
<!-- 查看批量检测报告 -->
function openReport(id){
var href=eval(id);
//console.log('${base}/DetectionResult/detection'+href+'/index.html');
window.open('${base}/DetectionResult/detection'+href+'/index.html','newwindow');
};

</script>
</body>

</html>
