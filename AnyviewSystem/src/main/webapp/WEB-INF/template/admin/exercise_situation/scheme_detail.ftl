<!DOCTYPE html>
<html
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
                        <button class="btn btn-white" type="button" onclick="back()" style="margin-top: -8px;">返回</button>
                    </div>
                    <h5>做题情况</h5>
                </div>
                <div class="container" >
                    <input type="radio" name="student_scheme" value="scheme" checked/>按作业表
                    <input type="radio" name="student_scheme" id="student" value="student"/>按学生
                    <select class="pull-right" name="chapters" id="chapters" onchange="selectChapter(this.value)">
                        <option value="">请选择章节</option>
                        [#list chapters as chapter]
                            <option value="${chapter.name}">${chapter.name}</option>
                        [/#list]
                    </select>
                    <div id="situationBar" style="width: 100%;height: 500px;"></div>
                </div>
                <form id="studentSituationForm" action="${base}/admin/exercise_situation/students_detail.jhtml" method="post">
                    <input type="hidden" id="ccsIds" name="ccsIds"/>
                </form>
                <form id="chapterForm" action="${base}/admin/exercise_situation/chapter_bar.jhtml" method="post">
                    <input type="hidden" id="schemeId" name="schemeId"/>
                    <input type="hidden" id="chapterName" name="chapterName" />
                    <input type="hidden" id="studentIds" name="studentIds" />
                </form>
            </div>
            [#include "/admin/exercise_situation/loading.ftl"]
        </div>
    </div>
</div>
<!-- BEGIN Script -->

[#include "/admin/include/script.ftl"]

<script src="${base}/resources/js/exercise_situation/echarts.min.js"></script>
<!-- END Script -->
<script>

    $("input[name=student_scheme]").click(function(){
        var value = $(this).val();
        if (value === 'student'){
            $('#loading').modal('show');
            document.getElementById("ccsIds").setAttribute("value",JSON.stringify(${ccsIds}));
            
            document.getElementById("studentSituationForm").submit();
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
                data: ['已完成','未完成']
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
                    data : ${chapterNames}//['第一章','第二章','第三章','第四章']
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                // {
                //     name:'直接访问',
                //     type:'bar',
                //     data:[320, 332, 301, 334, 390, 330, 320]
                // },
                {
                    name:'已完成',
                    type:'bar',
                    stack: '广告',
                    data:${finished},
                    itemStyle:{
                        color: '#87CEFA'
                    },
                    barWidth: 40

                },
                {
                    name:'未完成',
                    type:'bar',
                    stack: '广告',
                    data:${unFinished},
                    itemStyle:{
                        color: '#61a0a8'
                    },
                    barWidth: 40
                }
                // {
                //     name:'搜索引擎',
                //     type:'bar',
                //     data:[862, 1018, 964, 1026, 1679, 1600, 1570],
                //     markLine : {
                //         lineStyle: {
                //             normal: {
                //                 type: 'dashed'
                //             }
                //         },
                //         data : [
                //             [{type : 'min'}, {type : 'max'}]
                //         ]
                //     }
                // },
                // {
                //     name:'百度',
                //     type:'bar',
                //     barWidth : 5,
                //     stack: '搜索引擎',
                //     data:[620, 732, 701, 734, 1090, 1130, 1120]
                // },
                // {
                //     name:'谷歌',
                //     type:'bar',
                //     stack: '搜索引擎',
                //     data:[120, 132, 101, 134, 290, 230, 220]
                // },
                // {
                //     name:'必应',
                //     type:'bar',
                //     stack: '搜索引擎',
                //     data:[60, 72, 71, 74, 190, 130, 110]
                // },
                // {
                //     name:'其他',
                //     type:'bar',
                //     stack: '搜索引擎',
                //     data:[62, 82, 91, 84, 109, 110, 120]
                // }
            ]
        };
        if (option && typeof option === "object") {
            chart.setOption(option, true);
        }
    }

    function selectChapter(chapterName) {
        $('#loading').modal('show');
        var schemeId = ${chapters[0].schemeId};
        document.getElementById("schemeId").setAttribute("value",schemeId);
        document.getElementById("chapterName").setAttribute("value",chapterName);
        document.getElementById("studentIds").setAttribute("value",JSON.stringify(${studentIds}));
        document.getElementById("chapterForm").submit();
        [#--$.ajax({--]
        [#--    type: 'post',--]
        [#--    url: '${base}/admin/exercise_situation/chapter.jhtml',--]
        [#--    data: {--]
        [#--        schemeId: schemeId,--]
        [#--        chapterName: chapterName,--]
        [#--        studentIds: JSON.stringify(${studentIds})--]
        [#--    },--]
        [#--    success:function(data){--]
        [#--        console.log(data);--]
        [#--    }--]
        [#--})--]
    }
</script>
</body>

</html>