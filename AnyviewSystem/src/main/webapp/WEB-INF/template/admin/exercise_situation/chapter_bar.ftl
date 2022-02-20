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
[#--                        <select name="chapters" id="chapters"--]
[#--                                onchange="selectChapter(this.value)"--]
[#--                                >--]
[#--                            <option value="">请选择章节</option>--]
[#--                            [#list chapters as chapter]--]
[#--                                <option value="${chapter.name}">${chapter.name}</option>--]
[#--                            [/#list]--]
[#--                        </select>--]
                        <button class="btn btn-white" type="button" onclick="back()" style="margin-top: -8px;">返回</button>
                    </div>
                    <h5>章节完成情况</h5>
                </div>
                <div class="container" >
                    <input type="radio" name="table_bar" value="table"/>表格
                    <input type="radio" name="table_bar" id="bar" value="bar" checked/>图形
                    <div id="situationBar" style="width: 100%;height: 500px;"></div>
                </div>
            </div>

            <form id="chapterForm" action="${base}/admin/exercise_situation/chapter_table.jhtml" method="post">
                <input type="hidden" id="schemeId" name="schemeId"/>
                <input type="hidden" id="chapterName" name="chapterName" />
                <input type="hidden" id="studentIds" name="studentIds" />
            </form>
            [#include "/admin/exercise_situation/loading.ftl"]
        </div>
    </div>
</div>
<!-- BEGIN Script -->

[#include "/admin/include/script.ftl"]

<script src="${base}/resources/js/exercise_situation/echarts.min.js"></script>
<!-- END Script -->
<script>
    $("input[name=table_bar]").click(function(){
        var value = $(this).val();
        if (value === 'table'){
            $('#loading').modal('show');
            $("#bar").attr("checked","true");
            document.getElementById("schemeId").setAttribute("value",${schemeId});
            document.getElementById("chapterName").setAttribute("value",'${chapterName}');
            document.getElementById("studentIds").setAttribute("value",JSON.stringify(${studentIds}));
            document.getElementById("chapterForm").submit();
        }
    });
    $(document).ready(function () {
        [@flash_message /]
        showChart();
    });
    function back() {
        document.location.href="${base}/admin/exercise_situation/list.jhtml"
    }

    function showChart() {
        var chart = echarts.init(document.getElementById("situationBar"));
        var option = {
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                // data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎','百度','谷歌','必应','其他']
                data: ['已完成','未完成'],
                selected: {
                    '未完成':false
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : ${questionNames},//['第一章','第二章','第三章','第四章']
                    axisLabel: {
                        interval: 0 //auto自动隐藏显示不下的(默认) 0全部显示 {number}定义间隔 {function(index,date[index])} true显示
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'已完成',
                    type:'bar',
                    stack: '完成情况',
                    data:${finished},
                    itemStyle:{
                        color: '#87CEFA'
                    },
                    barWidth: 40
                },
                {
                    name:'未完成',
                    type:'bar',
                    stack: '完成情况',
                    data:${unFinished},
                    itemStyle:{
                        color: '#61a0a8'
                    },
                    barWidth: 40
                }
            ]
        };
        if (option && typeof option === "object") {
            chart.setOption(option, true);
        }
    }

    [#--function selectChapter(chapterName) {--]
    [#--    var schemeId = ${chapters[0].schemeId};--]
    [#--    $.ajax({--]
    [#--        type: 'post',--]
    [#--        url: '${base}/admin/exercise_situation/chapter.jhtml',--]
    [#--        data: {--]
    [#--            schemeId: schemeId,--]
    [#--            chapterName: chapterName,--]
    [#--            studentIds: JSON.stringify(${studentIds})--]
    [#--        },--]
    [#--        success:function(data){--]
    [#--            alert(data);--]
    [#--        }--]
    [#--    })--]
    [#--}--]
</script>
</body>

</html>