<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- BEGIN HEADER -->
    [#include "/admin/include/style.ftl"]
    <!-- END HEADER -->
    <style>
        /*#scoreText {*/
        /*    !*方法一*!*/
        /*    !*text-align = center; !*placeholder文字居中*!*!*/
        /*    !*text-align = left; !*placeholder文字居左*!*!*/
        /*    !*text-align = right; !*placeholder文字居右*!*!*/
        /*    !*!*方法二*!*!*/
        /*    !*padding-left：10px; !*placeholder文字距左10px*!*!*/
        /*    !*padding-right：10px; !*placeholder文字距右10px*!*!*/
        /*    !*!*方法三*!*!*/
        /*    box-sizing:border-box;*/
        /*}*/
        .table tbody tr td{
            overflow: hidden;
            text-overflow:ellipsis;
            white-space: nowrap;
        }
    </style>

</head>
<body>

<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="studentInfo">
    <div id="studentInfo">
        <div style="margin-top: 20px">
            <span id="sno" style="margin-left: 28%;font-size: 20px">学号:${student.username}</span>
            <span id="name" style="margin-left: 9%;font-size: 20px">姓名:${student.name}</span>
        </div>
        <div>
            <span id="class" style="margin-left: 28%;margin-top: 10px;font-size: 20px;">班级:${classEntity.className}</span>
            <span class="attitudeScore">
                <span style="margin-left: 17%;font-size: 25px">态度评分：</span>
                <span><input id="scoreText" type="text" style="width: 72px;height: 40px;float: left;margin-top:-35px;margin-left: 72%" value=""></span>
                <span>
[#--                    <input type="button" style="float: left;margin-left: 24px;margin-top: -28px" value="确定">--]
                        <button type="button" id="confirm" style="float: left;margin-left: 24px;margin-top: -28px"  onclick="confirmScore()">自动评分</button>
                </span>
                <br>
                <span>
                    <button type="button" id="score" style="margin-left: 64%"  onclick="automaticScore()">自动评分</button>
            </span>
        </div>
    </div>
    <div style="margin-left: 27%;margin-top: 20px;font-size: 23px;">数据分析</div>
    <div id="buttons" style="margin-top: 6px;margin-left: 345px">
       <label >
           <input type="radio" name="analysis" id="cmpRight" value="" />编译正确次数</label>
        <label>
            <input type="radio" style="margin-left: 20px" name="analysis" id="runRight" value="" />运行正确次数</label>
        <label >
            <input type="radio" style="margin-left: 20px" name="analysis" id="cmpError" value=""/>编译错误次数</label>
        <label>
            <input type="radio"  style="margin-left: 20px" name="analysis" id="runError" value=""/>运行错误次数</label>
        <label>
            <input type="radio"  style="margin-left: 20px" name="analysis" id="accumTime" value=""/>做题时长</label>
        <label>
            <input type="radio" style="margin-left: 20px" name="analysis" id="firstPast" value=""/>首次通过时间</label>

[#--        <input type="hidden" name="list" id="exerciseList" value="${exerciseList}">--]
    </div>
</div>
<input type="hidden" id="data" value="" />
<div id="bar_line" style="width:850px;height:400px;margin-left: 20%;"></div>
<div id="scatter" style="width:850px;height:400px;margin-left: 20%;display: none"></div>

<div class="ibox-content">
    <div class="dataTables_wrapper form-inline">
        <table class="table table-striped table-bordered table-hover dataTables-example dataTable" style='table-layout:fixed;'>
            <thead>
            <tr>

[#--                <th><input type="checkbox" class="i-checks" name="checkAll"></th>--]
[#--                <th>序号</th>--]
                <th>章节</th>
                <th>题目名称</th>
                <th style='width:300px;'>题目描述</th>
                <th>编译正确次数</th>
                <th>运行正确次数</th>
                <th>编译错误次数</th>
                <th>运行错误次数</th>
                <th>开始做题时间</th>
                <th>首次通过时间</th>
                <th>做题时长</th>
            </tr>
            </thead>
            <tbody>
            [#list exerciseList as exercise]
                <tr>
[#--                    <td>--]
[#--                        <input type="checkbox" class="i-checks" name="ids" value="${question.id}" id="${question.id}">--]
[#--                    </td>--]
[#--                    <td type=>${exercise_index + 1}</td>--]
                    <td>${exercise.question.chapter}</td>
                    <td>${exercise.question.question_name}</td>
                    <td><a title="${questionContentList[exercise_index].question_description}">${questionContentList[exercise_index].question_description}</a></td>
                    <td>${exercise.cmpRightCount}</td>
                    <td>${exercise.runRightCount}</td>
                    <td>${exercise.cmpErrorCount}</td>
                    <td>${exercise.runErrCount}</td>
                    <td>0</td>
[#--                    <td>${exercise.firstPastTime}</td>--]
                    <td>0</td>
                    <td>${exercise.accumTime}</td>
[#--                    <td>${exercise.question.content}</td>--]
[#--                    <td><a title="${questionContentList[question_index+(page.pageNumber-1)*20].question_description}">${questionContentList[question_index+(page.pageNumber-1)*20].question_description}</a></td>--]
[#--                    <td>${pass[question_index+(page.pageNumber-1)*20]}/${total}</td>--]
[#--                    <td>${question.difficulty}</td>--]
[#--                    <td style="text-align:center"><a class="btn btn-white" href="${base}/admin/exercise/stulist.jhtml?id=${question.id}" >批改</a></td>--]
                </tr>
            [/#list]
            </tbody>
        </table>
[#--        [@pagination pageNumber = page.pageNumber totalPages = page.totalPages]--]
[#--            [#include "/admin/include/pagination.ftl"]--]
[#--        [/@pagination]--]
    </div>
</div>
<!-- ECharts单文件引入 -->
<!-- BEGIN Script -->
[#include "/admin/include/script.ftl"]
<!-- END Script -->

<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script type="text/javascript">
    var myChart;
    $(function () {
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });

        // 路径配置
//--------------------------------------------折线图&柱状图 start-------------------------------------------//
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar' ,// 使用柱状图就加载bar模块，按需加载
                'echarts/chart/line',
                // 'echarts/chart/pie'
            ],
            function (ec) {
                myChart =ec.init(document.getElementById('bar_line'));
                // 基于准备好的dom，初始化echarts图表
                option = {

                    tooltip : {
                        // trigger: 'axis'
                    },
                    toolbox: {
                        // show : true,
                        // y: 'bottom',
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    // calculable : true,
                    // legend: {
                    //     data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎','百度','谷歌','必应','其他']
                    // },
                    title:{
                        text:'数据分析',
                        x: 'center',
                        y: '20'
                    },
                    yAxis : [
                        {
                            type : 'value',
                            position: 'left',
                        }
                    ]
                };
                // 为echarts对象加载数据
                myChart.setOption(option);
            }
        );

        [#--$.ajax("${base}/admin/exercise_situation/getExerciseList.jhtml",// 发送请求的URL字符串。--]
        [#--    {--]
        [#--        dataType : "json", // 预期服务器返回的数据类型。--]
        [#--        type : "get", //  请求方式 POST或GET--]
        [#--        // contentType:"application/json", //  发送信息至服务器时的内容编码类型--]
        [#--        // // 发送到服务器的数据。--]
        [#--        // data:JSON.stringify({classId:cId}),--]
        [#--        // data:{cmpRight:true},--]
        [#--        async:  false , // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求--]
        [#--        // 请求成功后的回调函数。--]
        [#--        success :function(data){--]
        [#--            myChart.setOption(--]
        [#--                {--]
        [#--                    xAxis : {--]
        [#--                        type : 'category',--]
        [#--                        data:data.xAxis--]
        [#--                    },--]
        [#--                    series : [--]
        [#--                        {--]
        [#--                            name:'编译错误次数',--]
        [#--                            type:'bar',--]
        [#--                            // areaStyle:{},--]
        [#--                            data:data.compRightArray--]
        [#--                        }--]
        [#--                    ]--]
        [#--                }--]
        [#--            );--]
        [#--        },--]
        [#--        // 请求出错时调用的函数--]
        [#--        error:function(){--]
        [#--            alert("数据分析失败");--]
        [#--        }--]
        [#--    });--]
        //编译正确触发函数
        // if ("#cmpRight".checked){
            $("#cmpRight").click(function(){
            $.ajax("${base}/admin/exercise_situation/getExerciseList.jhtml",// 发送请求的URL字符串。
                {
                    dataType : "json", // 预期服务器返回的数据类型。
                    type : "get", //  请求方式 POST或GET
                    // contentType:"application/json", //  发送信息至服务器时的内容编码类型
                    // // 发送到服务器的数据。
                    // data:JSON.stringify({classId:cId}),
                    // data:{cmpRight:true},
                    async:  true , // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
                    // 请求成功后的回调函数。
                    success :function(data){
                        myChart.setOption(
                            {
                                xAxis : {
                                    type : 'category',
                                    data:data.xAxis
                                },
                                series : [
                                    {
                                        name:'编译正确次数',
                                        type:'bar',
                                        // areaStyle:{},
                                        data:data.compRightArray
                                    },
                                    {
                                        name:'平均编译错误次数',
                                        type:'line',
                                        // areaStyle:{},
                                        data:data.compRightAvgArray
                                    }
                                ]
                            }
                        );
                    },
                    // 请求出错时调用的函数
                    error:function(){
                        alert("数据分析失败");
                    }
                });
            });
        // }

        //运行正确触发函数
        $("#runRight").click(function(){
            $.ajax("${base}/admin/exercise_situation/getExerciseList.jhtml",// 发送请求的URL字符串。
                {
                    dataType : "json", // 预期服务器返回的数据类型。
                    type : "get", //  请求方式 POST或GET
                    // contentType:"application/json", //  发送信息至服务器时的内容编码类型
                    // // 发送到服务器的数据。
                    // data:JSON.stringify({classId:cId}),
                    // data:{runRight:true},
                    async:  true , // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
                    // 请求成功后的回调函数。
                    success :function(data){
                        myChart.setOption(
                            {
                                xAxis : {
                                    type : 'category',
                                    data:data.xAxis
                                },
                                series : [
                                    {
                                        name:'编译错误次数',
                                        type:'bar',
                                        // areaStyle:{},
                                        data:data.runRightArray
                                    },
                                    {
                                        name:'平均运行正确次数',
                                        type:'line',
                                        // areaStyle:{},
                                        data:data.runRightAvgArray
                                    }
                                ]
                            }
                        );
                    },
                    // 请求出错时调用的函数
                    error:function(){
                        alert("数据分析失败");
                    }
                });
        });
        //编译错误触发函数
        $("#cmpError").click(function(){
            $.ajax("${base}/admin/exercise_situation/getExerciseList.jhtml",// 发送请求的URL字符串。
                {
                    dataType : "json", // 预期服务器返回的数据类型。
                    type : "get", //  请求方式 POST或GET
                    // contentType:"application/json", //  发送信息至服务器时的内容编码类型
                    // // 发送到服务器的数据。
                    // data:JSON.stringify({classId:cId}),
                    // data:{cmpError:true},
                    async:  true , // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
                    // 请求成功后的回调函数。
                    success :function(data){
                        myChart.setOption(
                            {
                                xAxis : {
                                    type : 'category',
                                    data:data.xAxis
                                },
                                series : [
                                    {
                                        name:'编译错误次数',
                                        type:'bar',
                                        // areaStyle:{},
                                        data:data.compErrorArray
                                    },
                                    {
                                        name:'平均编译错误次数',
                                        type:'line',
                                        // areaStyle:{},
                                        data:data.compErrorAvgArray
                                    }
                                ]
                            }
                        );
                    },
                    // 请求出错时调用的函数
                    error:function(){
                        alert("数据分析失败");
                    }
                });
        });
        //运行错误触发函数
        $("#runError").click(function(){
            $.ajax("${base}/admin/exercise_situation/getExerciseList.jhtml",// 发送请求的URL字符串。
                {
                    dataType : "json", // 预期服务器返回的数据类型。
                    type : "get", //  请求方式 POST或GET
                    // contentType:"application/json", //  发送信息至服务器时的内容编码类型
                    // // 发送到服务器的数据。
                    // data:JSON.stringify({classId:cId}),
                    // data:{runError:true},
                    async:  true , // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
                    // 请求成功后的回调函数。
                    success :function(data){
                        myChart.setOption(
                            {
                                xAxis : {
                                    type : 'category',
                                    data:data.xAxis
                                },
                                series : [
                                    {
                                        name:'编译错误次数',
                                        type:'bar',
                                        // areaStyle:{},
                                        data:data.runErrorArray
                                    },
                                    {
                                        name:'平均运行错误次数',
                                        type:'line',
                                        // areaStyle:{},
                                        data:data.runErrorAvgArray
                                    }
                                ]
                            }
                        );
                    },
                    // 请求出错时调用的函数
                    error:function(){
                        alert("数据分析失败");
                    }
                });
        });
        //做题时长按钮触发函数
        $("#accumTime").click(function(){
            $.ajax("${base}/admin/exercise_situation/getExerciseList.jhtml",// 发送请求的URL字符串。
                {
                    dataType : "json", // 预期服务器返回的数据类型。
                    type : "get", //  请求方式 POST或GET
                    // contentType:"application/json", //  发送信息至服务器时的内容编码类型
                    // // 发送到服务器的数据。
                    // data:JSON.stringify({classId:cId}),
                    // data:{accumTime:true},
                    async:  true , // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
                    // 请求成功后的回调函数。
                    success :function(data){
                        myChart.setOption(
                            {
                                xAxis : {
                                    type : 'category',
                                    data:data.xAxis
                                },
                                series : [
                                    {
                                        name:'编译错误次数',
                                        type:'bar',
                                        // areaStyle:{},
                                        data:data.accuTimeArray
                                    },
                                    {
                                        name:'平均做题时长',
                                        type:'line',
                                        // areaStyle:{},
                                        data:data.accuTimeAvgArray
                                    }
                                ]
                            }
                        );
                    },
                    // 请求出错时调用的函数
                    error:function(){
                        alert("数据分析失败");
                    }
                });
        });

    });
//---------------------------------------------------------------

    $("#firstPast").on("click",function () {
        var myChart2;
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/scatter',
                'echarts/chart/bar' ,// 使用柱状图就加载bar模块，按需加载
                'echarts/chart/line',
                'echarts/chart/pie'
            ],
            function (ec) {
                myChart2 =ec.init(document.getElementById('scatter'));
                // 基于准备好的dom，初始化echarts图表
                option = {

                    tooltip : {
                        trigger: 'axis'
                    },
                    toolbox: {
                        // show : true,
                        // y: 'bottom',
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    // calculable : true,
                    // legend: {
                    //     data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎','百度','谷歌','必应','其他']
                    // },
                    title:{
                        text:'数据分析',
                        x: 'center',
                        y: '20'
                    },
                    yAxis : [
                        {
                            type: 'value',
                             // data: ['低落伤心','正常平和', '开心快乐'],
                            // type : 'value',
                            // boundaryGap: [0, '100%'],
                            splitLine: {
                                show: false
                            }
                            // position: 'left',
                        }
                    ]
                };
                // 为echarts对象加载数据
                myChart2.setOption(option);
            }
        );

        //编译正确触发函数
        // if ("#cmpRight".checked){
        // $("#firstPast").click(function(){
            $.ajax("${base}/admin/exercise_situation/getExerciseList.jhtml",// 发送请求的URL字符串。
                {
                    dataType : "json", // 预期服务器返回的数据类型。
                    type : "get", //  请求方式 POST或GET
                    // contentType:"application/json", //  发送信息至服务器时的内容编码类型
                    // // 发送到服务器的数据。
                    // data:JSON.stringify({classId:cId}),
                    // data:{cmpRight:true},
                    async:  true , // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
                    // 请求成功后的回调函数。
                    success :function(data){
                        // console.log(data);
                        // var date = new Date(1500341149000);
                                var data2 = [
            [      //维度X   维度Y   其他维度 ...
                //在 visualMap 中可以将一个或多个维度映射到颜色，大小等多个图形属性上。
                ['2019/01/07','低落伤心',321773631,'yy','低落伤心'],
                ['2019/01/05','低落伤心',321773631,'yy','低落伤心']
            ],
            [
                ['2019/01/02','正常平和',321773631,'yy','正常平和'],
                ['2019/01/04','正常平和',321773631,'yy','正常平和'],
            ],
            [
                ['2019/01/03','开心快乐',321773631,'yy','开心快乐'],
                ['2019/01/10','开心快乐',200000000,'yy','开心快乐'],
            ]
        ];

        var xdata = ['2019/01/01', '2019/01/02', '2019/01/03','2019/01/04'
            ,'2019/01/05', '2019/01/06', '2019/01/07','2019/01/08'
            , '2019/01/09', '2019/01/10','2019/01/11'
        ];
                        console.log(data.firstPastArray.length);
                        var test = new Date('2019/9/1');
                        console.log(test.Format("hh:mm:ss"));
                        var arr = new Array();
                        for (var j = 0, len = data.firstPastArray.length; j < len; j++) {
                            var date = new Date(data.firstPastArray[j]);
                            arr[j] = date.Format("yyyy-MM-dd hh:mm:ss");
                            console.log(arr[j]);
                        }
                        // var format = date.Format("yyyy-MM-dd hh:mm:ss");
                        // console.log(format);
                        myChart2.setOption(
                            {
                                xAxis : {
                                    type : 'time',
                                    splitLine: {
                                        show: false
                                    },
                                    maxInterval: 3600*1000,
                                    min: new Date("00:00"),
                                    max: new Date("24:00"),
                                    // min: new Date('2019/9/1'),
                                    // max: new Date('2019/12/30'),
                                    axisLabel: {
                                        interale: 0,
                                        // rotate: -40,
                                        formatter: function (value) {//在这里写你需要的时间格式
                                            var t_date = new Date(value);
                                            return [t_date.getFullYear(), t_date.getMonth() + 1, t_date.getDate()].join('-') + " ";
                                                // + [t_date.getHours(), t_date.getMinutes()].join(':');

                                        }
                                    }
                                    // data:['2019/9/1','2019/12/30']
                                    // data : xdata
                                    // data:['2019-10-21','2019-10-22','2019-10-29','2019-11-05','2019-11-26']
                                },
                                series : [
                                    {
                                        name:'低落伤心',
                                        type:'scatter',
                                        // areaStyle:{},
                                        data:[['2019/9/2', 2], ['2019/9/3', 1], ['2019/9/20', 3], ['2019/9/30', 1], ['2019/9/6', 1],['2019/9/12', 1]],
                                        // data : data2[0],

                                        showSymbol: false,
                                        hoverAnimation: false,
                                        // symbolSize:20,

                                    }
                                ]
                            }
                        );
                    },
                    // 请求出错时调用的函数
                    error:function(){
                        alert("数据分析失败");
                    }
                });

        // });

    });
    //-------------------------------------------------------------
[#--//--------------------------------------------折线图&柱状图 start-------------------------------------------//--]
[#--    // 使用--]
[#--    require(--]
[#--        [--]
[#--            'echarts',--]
[#--            'echarts/chart/bar' ,// 使用柱状图就加载bar模块，按需加载--]
[#--            'echarts/chart/line',--]
[#--            'echarts/chart/pie'--]
[#--        ],--]
[#--        function (ec) {--]
[#--            // 基于准备好的dom，初始化echarts图表--]
[#--            var myChart = ec.init(document.getElementById('bar_line'));--]

[#--//            var option = {--]
[#--//                tooltip: {--]
[#--//                    show: true--]
[#--//                },--]
[#--//                legend: {--]
[#--//                    data:['销量']--]
[#--//                },--]
[#--//                xAxis : [--]
[#--//                    {--]
[#--//                        type : 'category',--]
[#--//                        data : ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]--]
[#--//                    }--]
[#--//                ],--]
[#--//                yAxis : [--]
[#--//                    {--]
[#--//                        type : 'value'--]
[#--//                    }--]
[#--//                ],--]
[#--//                series : [--]
[#--//                    {--]
[#--//                        "name":"销量",--]
[#--//                        "type":"bar",--]
[#--//                        "data":[5, 20, 40, 10, 10, 20]--]
[#--//                    }--]
[#--//                ]--]
[#--//            };--]
[#--            option = {--]

[#--                tooltip : {--]
[#--                    // trigger: 'axis'--]
[#--                },--]
[#--                toolbox: {--]
[#--                    // show : true,--]
[#--                    // y: 'bottom',--]
[#--                    feature : {--]
[#--                        mark : {show: true},--]
[#--                        dataView : {show: true, readOnly: false},--]
[#--                        magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},--]
[#--                        restore : {show: true},--]
[#--                        saveAsImage : {show: true}--]
[#--                    }--]
[#--                },--]
[#--                // calculable : true,--]
[#--                // legend: {--]
[#--                //     data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎','百度','谷歌','必应','其他']--]
[#--                // },--]
[#--                title:{--]
[#--                    text:'数据分析',--]
[#--                    x: 'center',--]
[#--                    y: '20'--]
[#--                },--]
[#--                xAxis : [--]
[#--                    {--]
[#--                        type : 'category',--]
[#--                        splitLine : {show : false},--]
[#--                        data : ['周一','周二','周三','周四','周五','周六','周日']--]
[#--                    }--]
[#--                ],--]
[#--                yAxis : [--]
[#--                    {--]
[#--                        type : 'value',--]
[#--                        position: 'left',--]
[#--                        // boundaryGap: [0, 10]--]
[#--                    }--]
[#--                ],--]
[#--                series : [--]
[#--                    {--]
[#--                        name:'直接访问',--]
[#--                        type:'bar',--]
[#--                        // areaStyle:{},--]
[#--                        data:[320, 332, 301, 334, 390, 330, 320]--]
[#--                    },--]
[#--                    {--]
[#--                        name:'搜索引擎',--]
[#--                        type:'line',--]
[#--                        data:[862, 1018, 964, 1026, 1679, 1600, 1570]--]
[#--                    },--]
[#--                ]--]
[#--            };--]
[#--                // 为echarts对象加载数据--]
[#--                myChart.setOption(option);--]
[#--        }--]
[#--        );--]

//--------------------------------------------折线图&柱状图 end-------------------------------------------//
//     require(
//         [
//             'echarts',
//             // 'echarts/chart/bar' ,// 使用柱状图就加载bar模块，按需加载
//             // 'echarts/chart/line',
//             // 'echarts/chart/pie',
//             'echarts/chart/scatter'
//         ],
//     function(ec){
//     // 基于准备好的dom，初始化echarts图表
//         var myChart = ec.init(document.getElementById('scatter'));
//         //--------------------
//         var data = [
//             [      //维度X   维度Y   其他维度 ...
//                 //在 visualMap 中可以将一个或多个维度映射到颜色，大小等多个图形属性上。
//                 ['2019/01/07','低落伤心',321773631,'yy','低落伤心'],
//                 ['2019/01/05','低落伤心',321773631,'yy','低落伤心']
//             ],
//             [
//                 ['2019/01/02','正常平和',321773631,'yy','正常平和'],
//                 ['2019/01/04','正常平和',321773631,'yy','正常平和'],
//             ],
//             [
//                 ['2019/01/03','开心快乐',321773631,'yy','开心快乐'],
//                 ['2019/01/10','开心快乐',200000000,'yy','开心快乐'],
//             ]
//         ];
//
//         // var xdata = ['2019/01/01', '2019/01/02', '2019/01/03','2019/01/04'
//         //     ,'2019/01/05', '2019/01/06', '2019/01/07','2019/01/08'
//         //     , '2019/01/09', '2019/01/10','2019/01/11'
//         // ];
//
//         option = {
//
//             title: {
//                 text: '心情'
//             },
//             legend: {
//                 right: 10,
//                 data: ['低落伤心', '正常平和', '开心快乐'],
//             },
//             xAxis: {
//                 splitLine: {
//                     lineStyle: {
//                         type: 'dashed'
//                     }
//                 },
//                 type: 'category',
//                 boundaryGap: false, //类目轴中 boundaryGap 可以配置为 true 和 false。默认为 true，这时候刻度只是作为分隔线，标签和数据点都会在两个刻度之间的带(band)中间。
//                 data: xdata
//
//             },
//             yAxis: {
//                 type: 'category',
//                 data: ['低落伤心','正常平和', '开心快乐'],
//                 splitLine: {
//                     lineStyle: {
//                         type: 'dashed' //分隔线线的类型。'solid'  'dashed' 'dotted'
//                     }
//                 },
//                 scale: true  //是否是脱离 0 值比例。设置成 true 后坐标刻度不会强制包含零刻度。在双数值轴的散点图中比较有用。 只在数值轴中（type: 'value'）有效。
//             },
//             series: [
//                 // {
//                 //     name: '低落伤心',
//                 //     data: data[0],
//                 //     type: 'scatter',
//                 //     symbolSize: function (data) {
//                 //         // console.log(Math.sqrt(data[2]));
//                 //         return Math.sqrt(data[2]) / 9e2;  //5e2==5*100=500
//                 //     },
//                 //     // label: {
//                 //     //     emphasis: {
//                 //     //         show: true,
//                 //     //         formatter: function (param) {
//                 //     //             return param.data[3];
//                 //     //         },
//                 //     //         position: 'top'
//                 //     //     }
//                 //     // },
//                 //     // itemStyle: {
//                 //     //
//                 //     //     type: 'radial',
//                 //     //     x: 0.4,
//                 //     //     y: 0.3,
//                 //     //     r: 1,
//                 //     //     shadowBlur: 10,
//                 //     //     shadowColor: 'rgba(120, 36, 50, 0.5)',
//                 //     //     shadowOffsetY: 5,
//                 //     //     colorStops: [{
//                 //     //         offset: 0, color: '#AEA1E9' // 0% 处的颜色
//                 //     //     }, {
//                 //     //         offset: 1, color: '#AEA1E9' // 100% 处的颜色
//                 //     //     }],
//                 //     //     global: false // 缺省为 false
//                 //     // }
//                 // },
//                 // {
//                 //     name: '正常平和',
//                 //     data: data[1],
//                 //     type: 'scatter',
//                 //     symbolSize: function (data) {
//                 //         return Math.sqrt(data[2]) / 5e2;
//                 //     },
//                 //     // label: {
//                 //     //     emphasis: {
//                 //     //         show: true,
//                 //     //         formatter: function (param) {
//                 //     //             return param.data[3];
//                 //     //         },
//                 //     //         position: 'top'
//                 //     //     }
//                 //     // },
//                 //     // itemStyle: {
//                 //     //
//                 //     //     type: 'radial',
//                 //     //     x: 0.5,
//                 //     //     y: 0.5,
//                 //     //     r: 0.5,
//                 //     //     shadowBlur: 10,
//                 //     //     shadowColor: 'rgba(25, 100, 150, 0.5)',
//                 //     //     shadowOffsetY: 5,
//                 //     //     colorStops: [{
//                 //     //         offset: 0,
//                 //     //         color: '#000'
//                 //     //     }, {
//                 //     //         offset: 1,
//                 //     //         color: '#000'
//                 //     //     }]
//                 //     //
//                 //     // }
//                 // },
//                 {
//                     name: '开心快乐',
//                     data: data[2],
//                     type: 'scatter',
//                     symbolSize: function (data) {
//                         return Math.sqrt(data[2]) / 5e2;
//                     },
//                     // label: {
//                     //     emphasis: {
//                     //         show: true,
//                     //         // formatter: function (param) {
//                     //         //     return param.data[3];
//                     //         // },
//                     //         // position: 'top'
//                     //     }
//                     // },
//                     // label: {
//                     //     emphasis: {
//                     //         show: true,
//                     //         formatter: function (param) {
//                     //             return param.data[3];
//                     //         },
//                     //         position: 'top'
//                     //     }
//                     // },
//                     itemStyle: {
//                         shadowBlur: 10,
//                         shadowColor: 'rgba(25, 100, 150, 0.5)',
//                         shadowOffsetY: 5,
//                         colorStops: [{
//                             offset: 0,
//                             color: 'rgb(129, 227, 238)'
//                         }, {
//                             offset: 1,
//                             color: 'rgb(25, 183, 207)'
//                         }]
//                     }
//                 }
//             ]
//
//     };
//         // 为echarts对象加载数据
//         myChart.setOption(option);
// }
//     );
//     // 使用
//     // require(
//     //     [
//     //         'echarts',
//     //         'echarts/chart/scatter'
//     //     ],
//     //     function (ec) {
//     //         // 基于准备好的dom，初始化echarts图表
//     //         var myChart = ec.init(document.getElementById('scatter'));
//     //
//     //         option = {
//     //
//     //             tooltip : {
//     //                 // trigger: 'axis'
//     //             },
//     //             toolbox: {
//     //                 // show : true,
//     //                 // y: 'bottom',
//     //                 feature : {
//     //                     mark : {show: true},
//     //                     dataView : {show: true, readOnly: false},
//     //                     magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled','scatter']},
//     //                     restore : {show: true},
//     //                     saveAsImage : {show: true}
//     //                 }
//     //             },
//     //             title:{
//     //                 text:'数据分析',
//     //                 x: 'center',
//     //                 y: '20'
//     //             },
//     //             xAxis : [
//     //                 {
//     //                     type : 'category',
//     //                     splitLine : {show : false},
//     //                     data : ['周一','周二','周三','周四','周五','周六','周日']
//     //                 }
//     //             ],
//     //             yAxis : [
//     //                 {
//     //                     type : 'value',
//     //                     position: 'left'
//     //                 }
//     //             ],
//     //             series : [
//     //                 {
//     //                     name:'直接访问',
//     //                     symbolSize: 12,
//     //                     type:'scatter',
//     //                     // areaStyle:{},
//     //                     data: [
//     //                         // 维度X   维度Y   其他维度 ...
//     //                         [10.0, 8.04],
//     //                         [8.0, 6.95],
//     //                         [13.0, 7.58],
//     //                         [9.0, 8.81],
//     //                         [11.0, 8.33],
//     //                         [14.0, 9.96],
//     //                         [6.0, 7.24],
//     //                         [4.0, 4.26],
//     //                         [12.0, 10.84],
//     //                         [7.0, 4.82],
//     //                         [5.0, 5.68]
//     //                     ]
//     //                 },
//     //             ]
//     //         };
//     //         // 为echarts对象加载数据
//     //         myChart.setOption(option);
//     //     }
//     // );
//     //--------------------


    var bar_line = document.getElementById("bar_line");
    var scatter = document.getElementById("scatter");

    // var exerciseList = document.getElementById("exerciseList").value;
    //     console.log(exerciseList);
    //编译正确
    $("#cmpRight").on("click", function () {
        bar_line.style.display = "block";
        scatter.style.display = "none";
    });

    //运行正确
    $("#runRight").on("click", function () {
        bar_line.style.display = "block";
        scatter.style.display = "none";
    });

    //编译错误
    $("#cmpError").on("click", function () {
        bar_line.style.display = "block";
        scatter.style.display = "none";
    });

    //运行错误
    $("#runError").on("click", function () {
        bar_line.style.display = "block";
        scatter.style.display = "none";
    });

    //做题时长
    $("#accumTime").on("click", function () {
        bar_line.style.display = "block";
        scatter.style.display = "none";
    });

    //首次通过
    $("#firstPast").on("click", function () {
        bar_line.style.display = "none";
        scatter.style.display = "block"
    });
//-----------------------
//     $(function() {
//         var btn = $("#getdata");
//         var dataSize;
//         // 样式
//         var mychart = echarts.init(document.getElementById('main'));
//         mychart.setOption({
//             title : {
//                 text : '各省男女人数统计'
//             },
//             legend : {},
//             xAxis : {
//                 type : 'category',
//                 data:[]
//             },
//             yAxis : {},
//             // tooltip:提示框组件
//             tooltip : {
//                 trigger : 'axis',
//                 axisPointer : {
//                     type : 'cross',
//                     crossStyle : {
//                         color : 'red'
//                     },
//                     lineStyle : {
//                         color : 'red'
//                     }
//                 }
//             },
//             // toolbox:工具栏
//             toolbox : {
//                 emphasis : {},
//                 show : true,
//                 showTitle : true,
//                 feature : {
//                     saveAsImage : {},// 保存为图片
//                     restore : {},// 还原
//                     dataView : {},// 数据视图
//                     magicType : {
//                         type : ['line', 'bar']
//                     },
//                     // 数据缩放
//                     dataZoom : {}
//                 }
//             },
//             series : [
//                 {
//                     name:'男',
//                     type : 'bar',
//                     data:[]
//                 }, {
//                     name:'女',
//                     type : 'bar',
//                     data:[]
//                 }
//
//             ]
//
//         });
//         // 异步获取数据
//         btn.click(function(){
//             $.post('/echart/index5',function(data){
//                 mychart.setOption({
//                     xAxis : {
//                         type : 'category',
//                         data:data.category
//                     },
//                     series : [
//                         {
//                             name:'男',
//                             type : 'bar',
//                             data:data.man
//                         }, {
//                             name:'女',
//                             type : 'bar',
//                             data:data.woman
//                         }
//                     ]
//                 });
//             },'json');
//         });
//     });

//--------------------
//--封装函数
    Date.prototype.Format = function(fmt)
    {
        var o = {
            "M+" : this.getMonth()+1,                 //月份   
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时   
            "m+" : this.getMinutes(),                 //分   
            "s+" : this.getSeconds(),                 //秒   
            "q+" : Math.floor((this.getMonth()+3)/3), //季度   
            "S"  : this.getMilliseconds()             //毫秒   
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    };


//--------------------------------------------折线图&柱状图 start-------------------------------------------//

    function automaticScore() {
        $.ajax({
            url:"${base}/admin/exercise_situation/attitudeScore.jhtml",
            dataType: "json",
            async: true, //异步请求，默认为false
            success: function (data) {
                // console.log(data);
                // $('scoreText').value = data
                document.getElementById("scoreText").value=data["totalAttitudeScore"];
            }
        })
    };

    function confirmScore() {
        $.ajax({
            url:"${base}/admin/exercise_situation/confirmScore.jhtml",
            data:{
                attitudeScore: function () {
                    return $("scoreText").val();
                }
            },
            dataType: "json",
            async: true, //异步请求，默认为false
            success: function (data) {
                console.log(data);
            }
        })
    }

</script>
</body>