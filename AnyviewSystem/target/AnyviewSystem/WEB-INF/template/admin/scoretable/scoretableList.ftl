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
        .col-sm-3 {
            color: #a5a5a5;
        }

        .opColor {
            color: #333
        }

        .operation {
            float: right;
        }

        .banner {
            position: relative;
        }

        .box {
            display: none;
        }

        .box2 {
            display: block;
        }

        .button {
            position: absolute;
            width: 68px;
            height: 64px;
            top: 89px;
            right: 0px;
            background: url('${base}/resources/themes/classic/images/arrow2.png') center center no-repeat;
            z-index: 2;
        }

        .button2 {
            transform: rotate(180deg);
            left: 0px;
            right: auto;
        }

        .button:hover {
            width: 68px;
            height: 64px;
            background-color: #dcf7ff;
            opacity: 0.7%;
            cursor: pointer;
        }
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins" id="collegeList">
                <form id="listForm" action="${base}/admin/ScoreTable/scoreContent.jhtml">
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-3 m-b-xs">
                                <select name="filter_cID" id="cId" value="${(params.cID)!''}"
                                        class="bs-select form-control changeColor">
                                    <option value="" [#if classId==""]selected[/#if]>请选择班级</option>
                                    [#list classList as class]
                                        <option value="${class.id}" [#if classId == class.id]selected[/#if]
                                                class="opColor">${class.className}</option>
                                    [/#list]

                                </select>
                            </div>
                            <div class="col-sm-3 m-b-xs">
                                <select name="filter_courseId" id="courseId" value="${(params.courseId)!''}"
                                        class="bs-select form-control changeColor">
                                    <option value="" [#if courseName==""]selected[/#if]>请选择课程</option>
                                    [#list courseNames as names]
                                        <option value="${names}" [#if courseName == names]selected[/#if]
                                                class="opColor">${names}</option>
                                    [/#list]
                                </select>
                            </div>
                            <div class="col-sm-3 m-b-xs">
                                <select name="filter_vID" id="vId" value="${(params.vID)!''}"
                                        class="bs-select form-control changeColor">
                                    <option value="" [#if workingTableId==""]selected[/#if]>请选择作业表</option>
                                    [#list workingTableList as workingTable]
                                        <option value="${workingTable.id}"
                                                [#if workingTableId == workingTable.id]selected[/#if]
                                                class="opColor">${workingTable.tableName}</option>
                                    [/#list]
                                </select>
                            </div>
                            <div class="col-sm-1">
                                <div class="input-group">
                                    <button type="submit" class="btn btn-sm btn-danger" onclick="changePageNum()"> 查询
                                    </button>
                                </div>
                            </div>
                        </div>

                        <div class="operation">
                            <div style="font-weight: bold">操作:</div>
                            <a class="btn btn-outline btn-info btn-xs" id="btn-correct-export">导出</a>

                            <a class="btn btn-outline btn-info btn-xs" id="btn-correct-parameter">设置评分标准</a>

                            <a class="btn btn-outline btn-info btn-xs" id="btn-correct-itemDetails">题目完成情况</a>

                            <a class="btn btn-outline btn-info btn-xs" id="btn-correct-update">一键更新学生数据</a>
                        </div>

                        <div>
                            <div style="font-weight: bold">计算:</div>
                            <a class="btn btn-outline btn-info btn-xs" id="btn-correct-paperScore">计算卷面分</a>

                            <a class="btn btn-outline btn-info btn-xs" id="btn-correct-attitude">计算态度分</a>

                            <a class="btn btn-outline btn-info btn-xs" id="btn-correct-totalScore">计算综合分</a>

                            <a class="btn btn-outline btn-info btn-xs" id="btn-correct-oneKey">一键计算</a>
                        </div>
                        <div>
                            <a class="btn btn-outline btn-info btn-xs" id="btn-correct-allPaperScore">计算所有学生卷面分</a>


                            <a class="btn btn-outline btn-info btn-xs" id="btn-correct-allAttitude">计算所有学生态度分</a>

                            <a class="btn btn-outline btn-info btn-xs" id="btn-correct-allTotalScore">计算所有学生综合分</a>

                            <a class="btn btn-outline btn-info btn-xs" id="btn-correct-allOneKey">一键计算所有学生</a>
                        </div>

                        <div class="dataTables_wrapper form-inline">
                            <table id="mytab"
                                   class="table table-striped table-bordered table-hover dataTables-example dataTable">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" class="i-checks" name="checkAll"></th>
                                    <th>序号</th>
                                    <th>学号</th>
                                    <th>姓名</th>
                                    <th>完成题数</th>
                                    <th>累计时间(分钟)</th>
                                    <th>卷面分</th>
                                    <th>态度分</th>
                                    <th>综合分</th>
                                    <th>排名</th>
                                    <th>班级</th>
                                    <th>课程</th>
                                    <th>作业表</th>
                                    <th>做题情况</th>
                                </tr>
                                </thead>
                                <tbody>
                                [#list page.content as scoreTable]
                                    <tr>
                                        <td>
                                            <input type="checkbox" class="i-checks" name="ids" value="${scoreTable.id}"
                                                   id="${scoreTable.id}">
                                        </td>
                                        <td>${scoreTable_index + 1}</td>
                                        [#if scoreTable.student??]
                                            <td>${scoreTable.student.username!""}</td>
                                            <td>${scoreTable.student.name!""}</td>
                                        [#else]
                                            <td></td>
                                            <td></td>
                                        [/#if]
                                        <td>${scoreTable.passNum}</td>
                                        <td>${scoreTable.totalTime}</td>
                                        <td>${scoreTable.paperScore?string("#.#")}</td>
                                        <td>${scoreTable.attitudeScore?string("#.#")}</td>
                                        <td>${scoreTable.score?string("#.#")}</td>
                                        <td>${scoreTable.rank}</td>
                                        <td>${scoreTable.classSystem.className}</td>
                                        <td>${scoreTable.workingTable.course.courseName}</td>
                                        <td>${scoreTable.workingTable.tableName}</td>
                                        <td style="text-align:center"><a class="btn btn-white"
                                                                         href="${base}/admin/ScoreTable/checkOneContent.jhtml?id=${scoreTable.id}">查看</a>
                                        </td>
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
                <div id="recordScoreTable" class="hidden">
                    [#list recordScoreTable as scoreTableId]
                        <span>${scoreTableId}</span>
                    [/#list]
                </div>
            </div>
        </div>
    </div>
</div>
<!-- BEGIN paperScoreDialog -->
<div class="modal inmodal" id="paperScore" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h1 class="modal-title">计算卷面分</h1>
                <form action="paperScore.jhtml" onsubmit="addPageIds()">
                    [#--                    <div class="form-group">--]
                    [#--                        <label>完成一道题目的得分是:<input type="number" name="highScore" value="${paperScore.highScore!}"/>分(100分制)</label></div>--]
                    [#--                    <div class="form-group">--]
                    [#--                        <label>未完成一道题目的得分是:<input type="number" name="lowScore" value="${paperScore.lowScore!}"/>分(100分制)</label>--]
                    [#--                    </div>--]
                    <div class="modal-body">
                        <div class="form-group">
                            <div class="row">
                                <div class="dataTables_wrapper form-inline dialog-table" id="paperScoreContent">

                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <!--	<button type="button" class="btn btn-danger ok-set-college" id="btn-calculate_paperScore">计算</button> -->
                            <button type="submit" class="btn btn-danger ok-set-college">计算</button>
                            <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
                        </div>
                        <input name="ids" id="paperScoreIds" class="hidden">
                        <input name="paperScoreFilter_cID" id="paperScoreFilter_cID" class="hidden">
                        <input name="paperScoreFilter_courseId" id="paperScoreFilter_courseId" class="hidden">
                        <input name="paperScoreFilter_vId" id="paperScoreFilter_vId" class="hidden">
                </form>
            </div>
        </div>
    </div>
</div>
</div>
<!-- END paperScoreDialog -->

<!-- BEGIN setScoreCondition -->
<div class="modal inmodal" id="attitude" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>


                <form action="setCondition.jhtml" onsubmit="addIds()">
                    <div class="banner">
                        <div class="button button2" id="button_pre">
                        </div>
                        <div class="button button1" id="button_next">
                        </div>
                        <div class="allBox" id="allBox">
                            <div class="box box1">
                                <h1 class="modal-title" style="margin-bottom: 20px;">计算卷面分</h1>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="dataTables_wrapper form-inline dialog-table"
                                                 id="paperScoreContent">
                                                <div class="animated fadeInRight">

                                                    <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="ibox float-e-margins">
                                                                <span>

                                                                说明：<br>

                                                        1、卷面分=100/习题总数*通过题目数<br>

                                                        2、如果题目没有通过，按照0分处理<br>

                                                        3、如果题目已通过，但没有被老师批改，该学生得到该题的满分

                                                                </span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="box box2">
                                <h1 class="modal-title" style="margin-bottom: 20px;">计算态度分</h1>

                                <div class="form-group">
                                    <label>最先完成者的得分是:<input type="number" name="highScore"
                                                            value="${attitudeCondition.highScore!}"/>分(100分制)</label>
                                </div>
                                <div class="form-group">
                                    <label>最后完成者的得分是:<input type="number" name="lowScore"
                                                            value="${attitudeCondition.lowScore!}"/>分(100分制)</label>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="dataTables_wrapper form-inline dialog-table"
                                                 id="attitudeContent">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="box box3">
                                <h1 class="modal-title" style="margin-bottom: 20px;">计算综合分</h1>
                                <div class="form-group">
                                    <div class="raw">
                                        <label style="margin-right: 6px;"><input name="selectOne" type="radio"
                                                                                 value="[#if totalScore.selectOne==1]${totalScore.selectOne}[#else]1[/#if]"
                                                    [#if totalScore.selectOne==1] checked[/#if] />只使用卷面分</label>
                                        <label style="margin: auto 8px;"><input name="selectOne" type="radio"
                                                                                value="[#if totalScore.selectOne==2]${totalScore.selectOne}[#else]2[/#if]"
                                                    [#if totalScore.selectOne==2] checked[/#if] />只使用态度分</label>
                                        <label style="margin: auto 5px;"><input name="selectOne" type="radio"
                                                                                value="[#if totalScore.selectOne==3]${totalScore.selectOne}[#else]3[/#if]"
                                                    [#if totalScore.selectOne==3] checked[/#if] /> 使用卷面分×态度分</label>
                                    </div>
                                    <div class="raw">
                                        <label><input type="radio" name="" disabled
                                                      id="selectBoth"/>按比例使用卷面分和态度分(默认卷面分和态度分各占50%)</label>
                                    </div>
                                    <div class="raw">
                                        <label style="margin-right: 20px;">卷面分比例：<input type="number"
                                                                                        name="paperPercent"
                                                                                        id="paperPercent"
                                                                                        value="${totalScore.paperPercent!50}"
                                                                                        style="width: 50px;" max="100"
                                                                                        min="0"
                                                                                        disabled/>%</label>
                                        <label>态度分比例：<input type="number" name="attitudePercent" id="attitudePercent"
                                                            value="${totalScore.attitudePercent!50}"
                                                            style="width: 50px;"
                                                            max="100"
                                                            min="0" disabled/>%</label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="raw">
                                        <label><input type="checkbox" name="examination" id="examination" value="0">
                                            通过题目数量达到某个要求即可及格(非100分制自动换算）</label>
                                    </div>
                                    <div class="raw">
                                        <label>通过题数：<input type="number" name="passItemCount" id="passItemCount"
                                                           value="${totalScore.passItemCount!}" style="width: 50px;"
                                                           max="100"
                                                           min="0"/></label>
                                        <label style="margin-left: 20px;">及格分数：<input type="number" name="passScore"
                                                                                      id="passScore"
                                                                                      value="${totalScore.passScore!}"
                                                                                      style="width: 50px;" max="100"
                                                                                      min="0"/>（100分制）</label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="raw">
                                        <label><input type="checkbox" name="fullMark" id="fullMark" value="0">
                                            通过题目数量达到某个要求即可满分(非100分制自动换算）</label>
                                    </div>
                                    <div class="raw">
                                        <label>通过题数：<input type="number" name="fullScoreCount" id="fullScoreCount"
                                                           value="${totalScore.fullScoreCount!}"/></label>
                                    </div>
                                    <div style="font-weight: bold">默认情况:①使用卷面分(50%)x态度分(50%)②不设立及格线③不设立满分线</div>
                                </div>
                                <input name="ids" id="totalScoreIds" class="hidden">
                                <input name="totalScoreFilter_cID" id="totalScoreFilter_cID" class="hidden">
                                <input name="totalScoreFilter_courseId" id="totalScoreFilter_courseId" class="hidden">
                                <input name="totalScoreFilter_vId" id="totalScoreFilter_vId" class="hidden">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-danger ok-set-college" id="attitudeCount">设置</button>
                        <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
                    </div>
                    <input name="ids" id="paperScoreIds" class="hidden">
                    <input name="filter_cID" id="filter_cID" class="hidden">
                    <input name="filter_courseId" id="filter_courseId" class="hidden">
                    <input name="filter_vId" id="filter_vId" class="hidden">
                    [#--                    <input name="ids" id="attitudeIds" class="hidden">--]
                    [#--                    <input name="attitudeFilter_cID" id="attitudeFilter_cID" class="hidden">--]
                    [#--                    <input name="attitudeFilter_courseId" id="attitudeFilter_courseId" class="hidden">--]
                    [#--                    <input name="attitudeFilter_vId" id="attitudeFilter_vId" class="hidden">--]
                </form>
            </div>
        </div>
    </div>
</div>
<!-- END attitudeDialog -->
<!-- BEGIN totalScoreDialog -->
<div class="modal inmodal" id="totalScore" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h1 class="modal-title">计算综合分</h1>

                <form action="${base}/admin/ScoreTable/totalScore.jhtml" onsubmit="addTotalScoreIds()">
                    <div class="form-group">
                        <div class="raw">
                            <label style="margin-right: 6px;"><input name="selectOne" type="radio"
                                                                     value="[#if totalScore.selectOne==1]${totalScore.selectOne}[#else]1[/#if]"
                                        [#if totalScore.selectOne==1] checked[/#if] />只使用卷面分</label>
                            <label style="margin: auto 8px;"><input name="selectOne" type="radio"
                                                                    value="[#if totalScore.selectOne==2]${totalScore.selectOne}[#else]2[/#if]"
                                        [#if totalScore.selectOne==2] checked[/#if] />只使用态度分</label>
                            <label style="margin: auto 5px;"><input name="selectOne" type="radio"
                                                                    value="[#if totalScore.selectOne==3]${totalScore.selectOne}[#else]3[/#if]"
                                        [#if totalScore.selectOne==3] checked[/#if] /> 使用卷面分×态度分</label>
                        </div>
                        <div class="raw">
                            <label><input type="radio" name="" disabled
                                          id="selectBoth"/>按比例使用卷面分和态度分(默认卷面分和态度分各占50%)</label>
                        </div>
                        <div class="raw">
                            <label style="margin-right: 20px;">卷面分比例：<input type="number" name="paperPercent"
                                                                            id="paperPercent"
                                                                            value="${totalScore.paperPercent!50}"
                                                                            style="width: 50px;" max="100" min="0"
                                                                            disabled/>%</label>
                            <label>态度分比例：<input type="number" name="attitudePercent" id="attitudePercent"
                                                value="${totalScore.attitudePercent!50}" style="width: 50px;" max="100"
                                                min="0" disabled/>%</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="raw">
                            <label><input type="checkbox" name="examination" id="examination" value="0">
                                通过题目数量达到某个要求即可及格(非100分制自动换算）</label>
                        </div>
                        <div class="raw">
                            <label>通过题数：<input type="number" name="passItemCount" id="passItemCount"
                                               value="${totalScore.passItemCount!}" style="width: 50px;" max="100"
                                               min="0"/></label>
                            <label style="margin-left: 20px;">及格分数：<input type="number" name="passScore" id="passScore"
                                                                          value="${totalScore.passScore!}"
                                                                          style="width: 50px;" max="100" min="0"/>（100分制）</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="raw">
                            <label><input type="checkbox" name="fullMark" id="fullMark" value="0">
                                通过题目数量达到某个要求即可满分(非100分制自动换算）</label>
                        </div>
                        <div class="raw">
                            <label>通过题数：<input type="number" name="fullScoreCount" id="fullScoreCount"
                                               value="${totalScore.fullScoreCount!}"/></label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-danger ok-set-college" id="compute">计算</button>
                        <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>

                    </div>
                    <input name="ids" id="totalScoreIds" class="hidden">
                    <input name="totalScoreFilter_cID" id="totalScoreFilter_cID" class="hidden">
                    <input name="totalScoreFilter_courseId" id="totalScoreFilter_courseId" class="hidden">
                    <input name="totalScoreFilter_vId" id="totalScoreFilter_vId" class="hidden">
                </form>
            </div>
            <input class="hidden" value="${attitudeCondition}" id="attitudeCondition">
            <input class="hidden" value="${totalScore}" id="totalScore">
        </div>
    </div>
</div>
<!-- END totalScoreDialog -->
<!-- BEGIN allPaperScoreDialog -->
<div class="modal inmodal" id="allPaperScore" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h1 class="modal-title">计算所有学生卷面分</h1>
                <form action="${base}/admin/ScoreTable/allPaperScore.jhtml" onsubmit="addPaperQueryCondition()">
                    [#--                    <div class="form-group">--]
                    [#--                        <label>完成一道题目的得分是:<input type="number" name="highScore" value="${paperScore.highScore!}"/>分(100分制)</label></div>--]
                    [#--                    <div class="form-group">--]
                    [#--                        <label>未完成一道题目的得分是:<input type="number" name="lowScore" value="${paperScore.lowScore!}"/>分(100分制)</label>--]
                    [#--                    </div>--]
                    <div class="modal-body">
                        <div class="form-group">
                            <div class="row">
                                <div class="dataTables_wrapper form-inline dialog-table" id="allPaperScoreContent">

                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <!--	<button type="button" class="btn btn-danger ok-set-college" id="btn-calculate_paperScore">计算</button> -->
                            <button type="submit" class="btn btn-danger ok-set-college">计算</button>
                            <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
                        </div>
                        <input name="ids" id="paperScoreIds" class="hidden">
                        <input name="filter_cID" id="filter_cID" class="hidden">
                        <input name="filter_courseId" id="filter_cID" class="hidden">
                        <input name="filter_vId" id="filter_cID" class="hidden">
                </form>
            </div>
        </div>
    </div>
</div>
</div>
<!-- END allPaperScoreDialog -->
<!-- BEGIN allAttitudeDialog -->
<div class="modal inmodal" id="allAttitude" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h1 class="modal-title" style="margin-bottom: 20px;">计算所有学生态度分</h1>
                <form action="${base}/admin/ScoreTable/allAttitude.jhtml" onsubmit="addAttitudeQueryCondition()">
                    <div class="form-group">
                        <label>最先完成者的得分是:<input type="number" name="highScore" value="${attitudeCondition.highScore!}"/>分(100分制)</label>
                    </div>
                    <div class="form-group">
                        <label>最后完成者的得分是:<input type="number" name="lowScore" value="${attitudeCondition.lowScore!}"/>分(100分制)</label>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <div class="row">
                                <div class="dataTables_wrapper form-inline dialog-table" id="allAttitudeContent">

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-danger ok-set-college" id="attitudeCount">计算</button>
                        <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
                    </div>
                    <input name="ids" id="attitudeScoreIds" class="hidden">
                    <input name="filter_cID" id="allAttitudeFilter_cID" class="hidden">
                    <input name="filter_courseId" id="allAttitudeFilter_courseId" class="hidden">
                    <input name="filter_vId" id="allAttitudeFilter_vId" class="hidden">
                </form>
            </div>
        </div>
    </div>
</div>
<!-- END attitudeDialog -->
<!-- BEGIN totalScoreDialog -->
<div class="modal inmodal" id="allTotalScore" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h1 class="modal-title">计算所有学生综合分</h1>

                <form action="${base}/admin/ScoreTable/allTotalScore.jhtml" onsubmit="addTotalQueryCondition()">
                    <div class="form-group">
                        <div class="raw">
                            <label style="margin-right: 6px;"><input name="allSelectOne" type="radio"
                                                                     value="[#if allTotalScore.selectOne==1]${allTotalScore.selectOne}[#else]1[/#if]"
                                        [#if allTotalScore.selectOne==1] checked[/#if] />只使用卷面分</label>
                            <label style="margin: auto 8px;"><input name="allSelectOne" type="radio"
                                                                    value="[#if allTotalScore.selectOne==2]${allTotalScore.selectOne}[#else]2[/#if]"
                                        [#if allTotalScore.selectOne==2] checked[/#if] />只使用态度分</label>
                            <label style="margin: auto 5px;"><input name="allSelectOne" type="radio"
                                                                    value="[#if allTotalScore.selectOne==3]${allTotalScore.selectOne}[#else]3[/#if]"
                                        [#if allTotalScore.selectOne==3] checked[/#if] /> 使用卷面分×态度分</label>
                        </div>
                        <div class="raw">
                            <label><input type="radio" name="" disabled
                                          id="allSelectBoth"/>按比例使用卷面分和态度分(默认卷面分和态度分各占50%)</label>
                        </div>
                        <div class="raw">
                            <label style="margin-right: 20px;">卷面分比例：<input type="number" name="paperPercent"
                                                                            id="allPaperPercent"
                                                                            value="${allTotalScore.paperPercent!50}"
                                                                            style="width: 50px;" max="100" min="0"
                                                                            disabled/>%</label>
                            <label>态度分比例：<input type="number" name="attitudePercent" id="allAttitudePercent"
                                                value="${allTotalScore.attitudePercent!50}" style="width: 50px;"
                                                max="100"
                                                min="0" disabled/>%</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="raw">
                            <label><input type="checkbox" name="examination" id="allExamination" value="0">
                                通过题目数量达到某个要求即可及格(非100分制自动换算）</label>
                        </div>
                        <div class="raw">
                            <label>通过题数：<input type="number" name="passItemCount" id="allPassItemCount"
                                               value="${allTotalScore.passItemCount!}" style="width: 50px;" max="100"
                                               min="0"/></label>
                            <label style="margin-left: 20px;">及格分数：<input type="number" name="passScore"
                                                                          id="allPassScore"
                                                                          value="${allTotalScore.passScore!}"
                                                                          style="width: 50px;" max="100" min="0"/>（100分制）</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="raw">
                            <label><input type="checkbox" name="fullMark" id="allFullMark" value="0">
                                通过题目数量达到某个要求即可满分(非100分制自动换算）</label>
                        </div>
                        <div class="raw">
                            <label>通过题数：<input type="number" name="fullScoreCount" id="allFullScoreCount"
                                               value="${allTotalScore.fullScoreCount!}"/></label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-danger ok-set-college" id="allCompute">计算</button>
                        <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>

                    </div>
                    <input name="ids" id="paperScoreIds" class="hidden">
                    <input name="filter_cID" id="allTotalScoreFilter_cID" class="hidden">
                    <input name="filter_courseId" id="allTotalScoreFilter_courseId" class="hidden">
                    <input name="filter_vId" id="allTotalScoreFilter_vId" class="hidden">
                </form>
            </div>
            <input class="hidden" value="${attitudeCondition}" id="allAttitudeCondition">
            <input class="hidden" value="${totalScore}" id="allTotalScore">
        </div>
    </div>
    <div class="test">
        <span></span>
        <span></span>
        <span></span>
    </div>
</div>
<!-- END totalScoreDialog -->
<!-- BEGIN Script -->
[#include "/admin/include/script.ftl"]
<!-- END Script -->
<script>
    $(document).ready(function () {
        [@flash_message /]
        $(".chosen-select").chosen({width: "100%"});

        // // 复选框
        // $('.i-checks').iCheck({checkboxClass: 'icheckbox_square-red', radioClass: 'iradio_square-red',});
        // // 选择所有，复选框改变
        // $("input[name='checkAll']").on('ifChanged', function () {
        //     $("input[name='ids']").iCheck($(this).is(":checked") == true ? 'check' : 'uncheck');
        //     if ($(this).is(":checked")) {
        //         $.ajax({
        //           type:"post",
        //           url:"recordScore.jhtml",
        //           data:{
        //               flag:2
        //           }
        //         })
        //     } else {
        //       $.ajax({
        //         type:"post",
        //         url:"recordScore.jhtml",
        //         data:{
        //             flag:3
        //         }
        //       })
        //     }
        // });

        // //选择单个,点击事件
        // $("input[name='ids']").on('ifClicked', function () {
        //     //选中状态
        //     if ($(this).is(":checked") == false) {
        //         $.ajax({
        //           type:"post",
        //           url:"recordScore.jhtml",
        //           data:{
        //               scoreTableId:$(this).val(),
        //               flag:1
        //           },
        //           success:function (data) {
        //               console.log(data)
        //           }

        //         })
        //     } else {
        //       //取消选中
        //       $.ajax({
        //         type:"post",
        //         url:"recordScore.jhtml",
        //         data:{
        //             scoreTableId:$(this).val(),
        //             flag:0
        //         },
        //         success:function (data) {
        //             console.log(data);
        //         }
        //       })
        //     }
        // });

//计算卷面分按钮 ------------------------------------------------------
        $("#btn-correct-paperScore").on("click", function () {
            if (getCookie("selectArr")) {
                var ids = getCookie("selectArr").split(",")
                clearCookie("selectArr")
                console.log(ids)
                location.href = "paperScore.jhtml?&filter_cID=" + $("#cId").val() + "&filter_courseId=" + $("#courseId").val() + "&filter_vId=" + $("#vId").val() + "&ids=" + ids
            } else {
                art.warn("请至少选择一条记录进行计算")
            }

        });
//end 计算卷面分--------------------------------------

//计算所有学生卷面分按钮 ------------------------------------------------------
        $("#btn-correct-allPaperScore").on("click", function () {
            var classId = $("#cId").val();
            var courseName = $("#courseId").val();
            var workingTableId = $("#vId").val();
            if ((classId != null && classId != "") && (courseName != null && courseName != "") && (workingTableId != null && workingTableId != "")) {
                [#--var index = layer.load(1, {--]
                [#--    shade: [0.1, '#fff'] //0.1透明度的白色背景--]
                [#--});--]
                [#--//获取id--]
                [#--$.get("${base}/admin/ScoreTable/paperScoreDialog.jhtml", {}, function (result, status) {--]
                [#--    var tableCon = $("#allPaperScoreContent");--]
                [#--    tableCon.empty();--]
                [#--    tableCon.append(result);--]
                [#--    layer.close(index);--]
                [#--    $('#allPaperScore').modal('show');--]
                [#--}, "html");--]
                location.href = "allPaperScore.jhtml?&filter_cID=" + classId + "&filter_courseId=" + courseName + "&filter_vId=" + workingTableId
            } else {
                art.warn("请选择评分班级");
                return;
            }


        });
//end 计算所有学生卷面分按钮 ------------------------------------------------------

//计算态度分按钮 ------------------------------------------------------
        $("#btn-correct-attitude").on("click", function () {
            if (getCookie("selectArr")) {
                var ids = getCookie("selectArr").split(",")
                clearCookie("selectArr")
                console.log(ids)
                location.href = "attitude.jhtml?&filter_cID=" + $("#cId").val() + "&filter_courseId=" + $("#courseId").val() + "&filter_vId=" + $("#vId").val() + "&ids=" + ids
            } else {
                art.warn("请至少选择一条记录进行计算")
            }
        });
//end 计算态度分 ------------------------------------------------------


//计算所有学生态度分按钮 ------------------------------------------------------
        $("#btn-correct-allAttitude").on("click", function () {
            var classId = $("#cId").val();
            var courseName = $("#courseId").val();
            var workingTableId = $("#vId").val();
            if ((classId != null && classId != "") && (courseName != null && courseName != "") && (workingTableId != null && workingTableId != "")) {
                [#--var index = layer.load(1, {--]
                [#--    shade: [0.1, '#fff'] //0.1透明度的白色背景--]
                [#--});--]
                [#--//获取id--]
                [#--$.get("${base}/admin/ScoreTable/attitudeDialog.jhtml", {}, function (result, status) {--]
                [#--    var tableCon = $("#allAttitudeContent");--]
                [#--    tableCon.empty();--]
                [#--    tableCon.append(result);--]
                [#--    layer.close(index);--]
                [#--    $('#allAttitude').modal('show');--]
                [#--}, "html");--]
                location.href = "allAttitude.jhtml?&filter_cID=" + classId + "&filter_courseId=" + courseName + "&filter_vId=" + workingTableId

            } else {
                art.warn("请选择评分班级");
                return;
            }


        });
//end 计算所有学生态度分 ------------------------------------------------------

//计算综合分按钮------------------------------------------------------
        $("#btn-correct-totalScore").on("click", function () {
            if (getCookie("selectArr")) {
                var ids = getCookie("selectArr").split(",")
                clearCookie("selectArr")
                console.log(ids)
                location.href = "totalScore.jhtml?&filter_cID=" + $("#cId").val() + "&filter_courseId=" + $("#courseId").val() + "&filter_vId=" + $("#vId").val() + "&ids=" + ids
            } else {
                art.warn("请至少选择一条记录进行计算")
            }
        });
//end 计算综合分--------------------------------------

//计算所有学生综合分按钮 ------------------------------------------------------
        $("#btn-correct-allTotalScore").on("click", function () {
            var classId = $("#cId").val();
            var courseName = $("#courseId").val();
            var workingTableId = $("#vId").val();
            if ((classId != null && classId != "") && (courseName != null && courseName != "") && (workingTableId != null && workingTableId != "")) {
                // var index = layer.load(1, {
                //     shade: [0.1, '#fff'] //0.1透明度的白色背景
                // });
                // layer.close(index);
                // $('#allTotalScore').modal('show');
                location.href = "allTotalScore.jhtml?&filter_cID=" + classId + "&filter_courseId=" + courseName + "&filter_vId=" + workingTableId
            } else {
                art.warn("请选择评分班级");
                return;
            }

        });
//end 计算所有学生综合分 ------------------------------------------------------

//一键计算按钮 ------------------------------------------------------
        $("#btn-correct-oneKey").on("click", function () {
            if (getCookie("selectArr")) {
                var $checkeds = getCookie("selectArr").split(",")
                if (!$("#totalScore").val() && !$("#attitudeCondition").val()) {
                    art.warn("请先计算态度分以及综合分")
                    return;
                }
                clearCookie("selectArr")
                location.href = "oneKey.jhtml?ids=" + $checkeds + "&filter_cID=" + $("#cId").val() + "&filter_courseId=" + $("#courseId").val() + "&filter_vId=" + $("#vId").val()
            } else {
                art.warn("请至少选择一条记录进行计算")
                return;
            }
        });
//end 一键计算按钮 ------------------------------------------------------

//一键计算所有学生按钮 ------------------------------------------------------
        $("#btn-correct-allOneKey").on("click", function () {
            var classId = $("#cId").val();
            var courseName = $("#courseId").val();
            var workingTableId = $("#vId").val();
            if ((classId != null && classId != "") || (courseName != null && courseName != "") || (workingTableId != null && workingTableId != "")) {
                if (!$("#allTotalScore").val() && !$("#allAttitudeCondition").val()) {
                    art.warn("请先计算态度分以及综合分")
                    return;
                }
                location.href = "oneKeyAll.jhtml?&filter_cID=" + $("#cId").val() + "&filter_courseId=" + $("#courseId").val() + "&filter_vId=" + $("#vId").val()
            } else {
                art.warn("请选择评分班级");
                return;
            }

        });
//end 一键计算所有学生按钮 ------------------------------------------------------


//一键更新 -------------------------------------------------------
        $("#btn-correct-update").on("click", function () {
            location.href = "oneUpdate.jhtml?filter_cID=" + $("#cId").val() + "&filter_courseId=" + $("#courseId").val() + "&filter_vId=" + $("#vId").val()
        })
//end 一键更新 -------------------------------------------------------

//题目完成情况按钮(只能选一个？) ---------------------------------------------
        $("#btn-correct-itemDetails ").on("click", function () {
            var classId = $
            ("#cId").val();
            var courseName = $("#courseId").val();
            var workingTableId = $("#vId").val();
            if ((classId != null && classId != "") || (courseName != null && courseName != "") || (workingTableId != null && workingTableId != "")) {
                location.href = "itemDetails.jhtml?filter_cID=" + $("#cId").val() + "&filter_courseId=" + $("#courseId").val() + "&filter_vId=" + $("#vId").val()
            } else {
                art.warn("请选择评分班级");
                return;
            }

        });
//end 题目完成情况按钮(只能选一个？) ---------------------------------------------

//导出 ---------------------------------------------
        $("#btn-correct-export ").on("click", function () {
            location.href = "exportExcel.jhtml"
        });
//导出 end ---------------------------------------------

    });

    $("#cId").change(function () {
        var classId = $("#cId").val();
        $("#courseId").empty();
        $("#vId").empty();
        if (classId != null && classId != "") {
            $.ajax("${base}/admin/ScoreTable/getChangeajax.jhtml",
                {
                    type: "POST",
                    datatype: 'json',
                    data: {
                        "parentId": classId, "flag": "C"
                    },
                    cache: false,
                    success: function (data) {
                        if ("success" == data.result) {
                            if (data.courselist != null && data.courselist.length > 0) {
                                $("#courseId").append("<option value = \"" + "" + "\">" + "请选择课程" + "</option>");
                                for (var i = 0; i < data.courselist.length; i++) {
                                    var course = data.courselist[i];
                                    var key = (course.id == null ? "" : course.id);
                                    var value = (course.courseName == null ? "" : course.courseName);
                                    $("#courseId").append("<option value = \"" + value + "\">" + value + "</option>");
                                }
                            }
                        }

                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {

                        art.warn("出错");

                    }
                })
        }
    });
    $("#courseId").change(function () {
        var courseId = $("#courseId").val();
        $("#vId").empty();
        if (courseId != null && courseId != "") {
            $.ajax("${base}/admin/ScoreTable/getChangeajax.jhtml", {
                type: "POST",
                datatype: 'json',
                data: {
                    "parentId": courseId,
                    "flag": "W"
                },
                cache: false,
                success: function (data) {
                    if ("success" == data.result) {
                        if (data.workingtablelist != null && data.workingtablelist.length > 0) {
                            for (var i = 0; i < data.workingtablelist.length; i++) {
                                var workingtable = data.workingtablelist[i];
                                var key = (workingtable.id == null ? "" : workingtable.id);
                                var value = (workingtable.tableName == null ? "" : workingtable.tableName);
                                $("#vId").append("<option value = \"" + key + "\">" + value + "</option>");
                            }
                        }
                    }

                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                    art.warn("出错");

                }
            })
        }
    });

    //单选框选项改变时
    $("input:radio[name='selectOne']").click(function () {
        console.log($('input:radio[name="selectOne"]:checked').val())

        //当选择同时使用卷面分及态度分选项时
        if ($('input:radio[name="selectOne"]:checked').val() == "3") {
            $('#selectBoth').prop("checked", "true")
            $('#paperPercent').removeAttr("disabled")
            $('#attitudePercent').removeAttr("disabled")
        } else {
            $('#selectBoth').removeAttr("checked")
            $('#paperPercent').prop("disabled", "true")
            $('#attitudePercent').prop("disabled", "true")
        }
    })
    //单选框选项改变时
    $("input:radio[name='allSelectOne']").click(function () {
        console.log($('input:radio[name="allSelectOne"]:checked').val())

        //当选择同时使用卷面分及态度分选项时
        if ($('input:radio[name="allSelectOne"]:checked').val() == "3") {
            $('#allSelectBoth').prop("checked", "true")
            $('#allPaperPercent').removeAttr("disabled")
            $('#allAttitudePercent').removeAttr("disabled")
        } else {
            $('#allSelectBoth').removeAttr("checked")
            $('#allPaperPercent').prop("disabled", "true")
            $('#allAttitudePercent').prop("disabled", "true")
        }
    })
    //当用户提交时，检测卷面分及态度分的比例是否超过100%
    $("#compute").click(function () {

        //及格
        if ($("#examination").is(":checked")) {
            $("#examination").val("1")
        } else {
            $("#examination").val("0")
        }
        //满分
        if ($("#fullMark").is(":checked")) {
            $("#fullMark").val("1")
        } else {
            $("#fullMark").val("0")
        }

        //当用户提交时，检测卷面分及态度分的比例是否超过100%
        if ($('input:radio[name="selectOne"]:checked').val() == "3") {
            let paperPercent = parseInt($('#paperPercent').val()),
                attitudePercent = parseInt($('#attitudePercent').val())
            if (paperPercent + attitudePercent != 100) {
                alert("卷面分比例及态度分比例的总和必须是100%!")
                return false
            } else {
                return true;
            }
        }




        return true
    })

    //计算综合分范围判断 ----------------------------
    $("#allCompute").click(function () {
        //及格
        if ($("#allExamination").is(":checked")) {
            $("#allExamination").val("1")
        } else {
            $("#allExamination").val("0")
        }
        //满分
        if ($("#allFullMark").is(":checked")) {
            $("#allFullMark").val("1")
        } else {
            $("#allFullMark").val("0")
        }

        //当用户提交时，检测卷面分及态度分的比例是否超过100%
        if ($('input:radio[name="allSelectOne"]:checked').val() == "3") {
            let paperPercent = parseInt($('#allPaperPercent').val()),
                attitudePercent = parseInt($('#allAttitudePercent').val())
            if (paperPercent + attitudePercent != 100) {
                alert("卷面分比例及态度分比例的总和必须是100%!")
                return false
            } else {
                return true;
            }
        }

        return true
    })
    //end 计算综合分范围判断 ----------------------------

    //点击更改选择框字体颜色(默认为浅灰色)
    $(".changeColor").change(function (argument) {
        if ($(this).val()) {
            $(this).css("color", "#333")
            $($(this).children()[0]).css("color", "#a5a5a5")
        } else {
            $(this).css("color", "#a5a5a5")
        }
    })

    // //确定计算卷面分
    // function addPageIds() {
    //     var ids = getCookie("selectArr").split(",")
    //     clearCookie("selectArr")
    //     console.log(ids)
    //     $("#paperScoreIds").val(ids)
    //     $("#paperScoreFilter_cID").val($("#cId").val())
    //     $("#paperScoreFilter_courseId").val($("#courseId").val())
    //     $("#paperScoreFilter_vId").val($("#vId").val())
    // }

    // //确定计算态度分
    // function addIds() {
    //     // var ids = getCookie("selectArr").split(",")
    //     // clearCookie("selectArr")
    //     // console.log(ids)
    //     $("#filter_cID").val($("#cId").val())
    //     $("#filter_courseId").val($("#courseId").val())
    //     $("#filter_vId").val($("#vId").val())
    //     //location.href = "attitude.jhtml?ids="+ids
    // }

    // //确定综合分
    // function addTotalScoreIds() {
    //     var ids = getCookie("selectArr").split(",")
    //     clearCookie("selectArr")
    //     $("#totalScoreIds").val(ids)
    //     $("#totalScoreFilter_cID").val($("#cId").val())
    //     $("#totalScoreFilter_courseId").val($("#courseId").val())
    //     $("#totalScoreFilter_vId").val($("#vId").val())
    //     //location.href = "totalScore.jhtml?ids="+ids
    // }

    //确定查询条件
    function addQueryCondition() {
        $("#filter_cID").val($("#cId").val())
        $("#filter_courseId").val($("#courseId").val())
        $("#filter_vId").val($("#vId").val())
        //location.href = "totalScore.jhtml?ids="+ids
    }
    //
    // //确定所有学生卷面分查询条件
    // function addPaperQueryCondition() {
    //     $("#allPaperFilter_cID").val($("#cId").val())
    //     $("#allPaperFilter_courseId").val($("#courseId").val())
    //     $("#allPaperFilter_vId").val($("#vId").val())
    //     //location.href = "totalScore.jhtml?ids="+ids
    // }

    // //确定所有学生态度分查询条件
    // function addAttitudeQueryCondition() {
    //     $("#allAttitudeFilter_cID").val($("#cId").val())
    //     $("#allAttitudeFilter_courseId").val($("#courseId").val())
    //     $("#allAttitudeFilter_vId").val($("#vId").val())
    //     //location.href = "totalScore.jhtml?ids="+ids
    // }
    //
    // //确定所有学生综合分分查询条件
    // function addTotalQueryCondition() {
    //     $("#allTotalScoreFilter_cID").val($("#cId").val())
    //     $("#allTotalScoreFilter_courseId").val($("#courseId").val())
    //     $("#allTotalScoreFilter_vId").val($("#vId").val())
    //     //location.href = "totalScore.jhtml?ids="+ids
    // }

    function myKeyUp(event) {
        var flag = true;
        $("#mytab input").each(function () {
            if ($(this).val() == "") {
                flag = false;
            }
        });
        if (flag) {
            $("#mybtn").attr("disabled", false);
        } else {
            $("#mybtn").attr("disabled", true);
        }
    }

    //设置评分标准 -------------------------------------------------------------


    $("#btn-correct-parameter").on("click", function () {
        var index = layer.load(1, {
            shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        //获取id
        $.get("${base}/admin/ScoreTable/attitudeDialog.jhtml", {}, function (result, status) {
            var tableCon = $("#attitudeContent");
            tableCon.empty();
            tableCon.append(result);
            layer.close(index);
            $('#attitude').modal('show');
        }, "html");
    });

    //计算卷面分条件
    $("#attitude_pre").on("click", function () {
        var index = layer.load(1, {
            shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        //获取id
        $.get("${base}/admin/ScoreTable/paperScoreDialog.jhtml", {}, function (result, status) {
            var tableCon = $("#paperScoreContent");
            tableCon.empty();
            tableCon.append(result);
            layer.close(index);
            $('#paperScore').modal('show');
        }, "html");
    });
    //计算综合分条件
    $("#attitude_next").on("click", function () {
        var index = layer.load(1, {
            shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        layer.close(index);
        $('#totalScore').modal('show');
    });

    //设置全局变量
    var index = 1,
        button_pre = byId("button_pre"),
        button_next = byId("button_next"),
        boxes = byId("allBox").getElementsByClassName("box"),
        length = boxes.length;
    //获取元素
    function byId(id) {
        return typeof (id) === "string" ? document.getElementById(id) : id;
    }

    //改变评分选框的状态
    function changeBox() {
        for (var i = 0; i < length; i++) {
            boxes[i].style.display = "none";
        }
        boxes[index].style.display = "block";
    }

    button_pre.onclick = function () {
        index--;
        if (index < 0) {
            alert("第一页");
            // art.warn("第一页");
            index++;
        }
        changeBox();
    }

    button_next.onclick = function () {
        index++;
        if (index >= length) {
            alert("最后一页");
            // art.warn("最后一页");
            index--;
        }
        changeBox();
    }
    //end 设置评分标准 -------------------------------------------------------------
</script>
</body>

</html>
