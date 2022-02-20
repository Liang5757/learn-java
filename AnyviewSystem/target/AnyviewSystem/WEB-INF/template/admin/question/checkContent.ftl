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
        textarea {
            width: 100%;
        }
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">

                <div class="ibox-title">
                    <div class="ibox-tools">
                        <a class="btn btn-white" href="list.jhtml" style="margin-top: -8px;">返回</a>
                    </div>
                    <h5>题目内容</h5>
                </div>
                <div class="ibox-content">
                    <form id="saveQuestionContentForm" class="form-horizontal" action="saveEditQuestionContent.jhtml"
                          method="post" onsubmit="add_HeaderFile()">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-6" style="display:none">
                                    <label class="col-sm-3 control-label">题目ID<span
                                            class="required"> * </span></label>
                                    <div class="col-sm-9">
                                        <input type="text" name="question_id" class="form-control" maxlength="200"
                                               value="${id}" readonly="readonly"/>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-9">
                                    <label class="col-sm-2 control-label">题目描述<span class="required"></span>:</label>

                                    <div class="col-sm-9 form-inline">
                                        <textarea rows="6" cols="60" placeholder="请输入题目描述" id="question_description"
                                                  name="question_description" class="question_edit"
                                        >[#if fileFlag == 1]${fileContent!""}[#else]${questionContent.question_description!""}[/#if]</textarea>
                                    </div>
                                    <div class="col-sm-1 form-inline">
                                        <a onclick="importHeader(1)"
                                           class="btn btn-outline btn-danger btn-sm importFile">导入</a>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-9">
                                    <label class="col-sm-2 control-label">标准答案<span class="required"></span>:</label>

                                    <div class="col-sm-9 form-inline">
                                        <textarea rows="6" cols="60" placeholder="请输入标准答案" id="standard_answer"
                                                  name="standard_answer" class="question_edit"
                                        >[#if fileFlag == 2]${fileContent!""}[#else]${questionContent.standard_answer!""}[/#if]</textarea>
                                    </div>
                                    <div class="col-sm-1 form-inline">
                                        <a onclick="importHeader(2)"
                                           class="btn btn-outline btn-danger btn-sm importFile">导入</a>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-9">
                                    <label class="col-sm-2 control-label">学生答案<span class="required"></span>:</label>

                                    <div class="col-sm-9 form-inline">
                                        <textarea rows="6" cols="60" placeholder="请输入学生答案" id="student_answer"
                                                  name="student_answer" class="question_edit"
                                        >[#if fileFlag == 3]${fileContent!""}[#else]${questionContent.student_answer!""}[/#if]</textarea>
                                    </div>
                                    <div class="col-sm-1 form-inline">
                                        <a onclick="importHeader(3)"
                                           class="btn btn-outline btn-danger btn-sm importFile">导入</a>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-9">
                                    <label class="col-sm-2 control-label">主文件内容<span class="required"></span>:</label>

                                    <div class="col-sm-9 form-inline">
                                        <textarea rows="6" cols="60" placeholder="请输入主文件内容" id="headerfile_content"
                                                  name="headerfile_content" class="question_edit"
                                        >[#if fileFlag == 4]${fileContent!""}[#else]${questionContent.headerfile_content!""}[/#if]</textarea>
                                    </div>
                                    <div class="col-sm-1 form-inline">
                                        <a onclick="importHeader(4)"
                                           class="btn btn-outline btn-danger btn-sm importFile">导入</a>
                                    </div>
                                </div>
                            </div>
                            &nbsp;
                            <div class="row">
                                <div class="col-sm-9" id="Add">
                                    <label class="col-sm-2 control-label">头文件<span class="required"></span>:</label>
                                [#assign i = 1]
                                [#if fileContent?? && fileFlag == 0]
                                    [#list fileContent?split("(youyitech)") as content]
                                    [#--计算并区分相对应的头文件 --]
                                        [#assign inputCount = "1${content_index}5"]
                                        [#if countArr?? && countArr != "null"]
                                            [#list countArr?split(",") as count]
                                                [#assign i = i+1]
                                                [#if count?string?index_of("1${content_index}5") != -1]
                                                    [#assign inputCount = "${i}${content_index}0"]
                                                    [#break]
                                                [#elseif count_index + 1 == countArr?split(",")?size]
                                                    [#assign inputCount = "1${content_index}5"]
                                                [/#if]
                                            [/#list]
                                        [/#if]
                                        [#if content_index + 1 == fileContent?split("(youyitech)")?size]
                                            [#break ]
                                        [/#if]
                                        [#if content_index > 0]
                                            <div class="col-sm-2 control-label"></div>
                                        [/#if]
                                        <div class="col-sm-9 form-inline header" style="margin-top:10px;">
                                            <div class="form-inline">
                                                <textarea rows="1" cols="60" class="${inputCount}"
                                                          name="header_file"
                                                          placeholder="请输入头文件名称">[#if fileName??]${fileName?split("(youyitech)")[content_index]}[/#if]</textarea>
                                            </div>
                                            <div class="form-inline">
                                                <textarea rows="6" cols="60" class="${inputCount}"
                                                          name="header_file_content"
                                                          placeholder="请输入头文件内容">${content!""}</textarea>
                                            </div>
                                        </div>
                                        <div class="col-sm-1 form-inline">
                                            <a onclick="importHeader(0,${inputCount})"
                                               class="btn btn-outline btn-danger btn-sm">导入</a>
                                        </div>
                                    [/#list]
                                [#--判断lists_headerfile是否存在 （编辑）   --]
                                [#elseif (lists_headerfile?? && fileContent?? == false) || (fileContent?? && fileFlag != 0 && lists_headerfile?size > 1)]
                                    [#list lists_headerfile as headerfile]
                                        [#if headerfile.header_file_content == "" && countArr?? ]
                                        [#else]
                                            <div class="col-sm-2 control-label"></div>
                                            <div class="col-sm-9 form-inline header" style="margin-top:10px;">
                                                <div class="form-inline">
                                                    <textarea rows="1" cols="60" class="${headerfile_index}"
                                                              name="header_file"
                                                              placeholder="请输入头文件名称">${headerfile.header_file!""}</textarea>
                                                </div>
                                                <div class="form-inline">
                                                    <textarea rows="6" cols="60" class="${headerfile_index}"
                                                              name="header_file_content"
                                                              placeholder="请输入头文件内容">${headerfile.header_file_content!""}</textarea>
                                                </div>
                                            </div>
                                            <div class="col-sm-1 form-inline">
                                                <a onclick="importHeader(0,${headerfile_index})"
                                                   class="btn btn-outline btn-danger btn-sm">导入</a>
                                            </div>
                                        [/#if]
                                    [/#list]
                                [#--添加状态--]
                                [#elseif (fileContent?? == false && lists_headerfile?? == false) || (fileContent?? && fileFlag != 0 && countArr?? == false)]
                                    <div class="col-sm-9 form-inline header">
                                        <div class="form-inline">
                                            <textarea rows="1" cols="60" class="0" name="header_file"
                                            ></textarea>
                                        </div>
                                        <div class="form-inline">
                                            <textarea rows="6" cols="60" placeholder="请输入头文件内容" class="0"
                                                      name="header_file_content"
                                            ></textarea>
                                        </div>
                                    </div>
                                    <div class="col-sm-1 form-inline">
                                        <a onclick="importHeader(0,0)"
                                           class="btn btn-outline btn-danger btn-sm importFile">导入</a>
                                    </div>
                                [/#if]
                                </div>
                                <div style="width: 500px;padding-left: 170px;">
                                    <input id="add_header_file" type="button" class="btn btn-outline btn-success"
                                           value="继续添加" style="margin-top: 5px;">
                                </div>
                            </div>
                        </div>
                        <!--/row-->

                        <div class="form-group">
                            <div class="col-sm-12 col-sm-offset-2">
                                <button id="sumb" type="submit" class="btn btn-danger">确定</button>
                            </div>
                        </div>

                        <input class="hidden" id="headerFiles" name="headerFiles">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- BEGIN Script -->
[#include "/admin/include/script.ftl"]
<!-- END Script -->
<script src="${base}/resources/js/dropzone.js"></script>
<script>
    "use strict";

    $(document).ready(function () {
        [@flash_message /]

        // console.log("check_question_description---->="+storage['check_question_description'])
        //获取以保存的导入的文件，并将其显示到页面上
        //题目描述
        if (storage['check_fileFlag'] != "1" && storage['check_question_description'] != null) {
            $("#question_description").val(storage['check_question_description'])
        }
        //标准答案
        if (storage['check_fileFlag'] != "2" && storage['check_standard_answer'] != null) {
            $("#standard_answer").val(storage['check_standard_answer'])
        }
        //学生答案
        if (storage['check_fileFlag'] != "3" && storage['check_student_answer'] != null) {
            $("#student_answer").val(storage['check_student_answer'])
        }
        // //主文件标题
        // if(storage['check_fileFlag'] != "4" && storage['check_headerfile_name'] != null){
        //   $("#headerfile_name").val(storage['check_headerfile_name'])
        // }
        //主文件内容
        if (storage['check_fileFlag'] != "4" && storage['check_headerfile_content'] != null) {
            $("#headerfile_content").val(storage['check_headerfile_content'])
        }
        //头文件标题及内容
        // var $header_file_content = $("textarea[name='header_file_content']")
        var fileContentArr = [],
                fileHeaderArr = []
        // console.log(storage['check_headerFileContentArr'])
        if (storage['check_headerFileContentArr'] != null) {
            if (storage['check_headerFileContentArr'].split("(====)").length > 1) {
                fileContentArr = storage['check_headerFileContentArr'].split("(youyi)")
                fileHeaderArr = storage['check_headerFileArr'].split("(youyi)")
                // for (let a = 0; a < $header_file_content.length; a++) {
                //     let headerClass = ""
                //     if ($($header_file_content[a]).attr('class').indexOf(" ") != 0) {
                //         headerClass = $($header_file_content[a]).attr('class').split(" ")[0]
                //     } else {
                //         headerClass = $($header_file_content[a]).attr('class')
                //     }
                //
                // }
                //除了点击导入以外的其他头文件内容class
                [#if countArr??]
                    [#list countArr?split(",") as count]
                        //将之前以保存的文件一一对应存放进去
                        for (let i = 0; i < fileContentArr.length; i++) {
                            // console.log("fileContentArr--->="+fileContentArr[i])
                            if (${count} == fileContentArr[i].split("(====)")[0]
                        )
                            {
                                //添加新的textarea到头文件中
                                addContent(${count}, fileContentArr[i].split("(====)")[1],fileHeaderArr[i].split("(====)")[1])
                            }
                        }
                    [/#list]
                [/#if]
            }

        }

        //重新导入时，添加新的textare到头文件中


        //拖拽文件上传  题目描述~主文件内容
        $(".question_edit").dropzone({
            url: "saveimportContent.jhtml",
            paramName: "cfile",
            // acceptedFiles: ".js,.obj,.dae,.c,.*",
            clickable: false,  //去除点击事件
            init: function () {
                // console.log($(".question_edit"))
                this.on("success", function () {
                    location.href = "checkContent.jhtml"
                })
                this.on("addedfile", function () {
                    var arr = ["question_description", "standard_answer", "student_answer", "headerfile_content"]
                    arr.forEach((id, index) => {
                        if (this.element.id == id) {
                            importHeader(index + 1, -1, 1)
                        }
                    })
                })
            }
        });
        //拖拽文件上传  头文件内容
        $("textarea[name='header_file_content']").dropzone({
            url: "saveimportContent.jhtml",
            paramName: "cfile",
            clickable: false,  //去除点击事件
            init: function () {
                this.on("success", function () {
                    location.href = "checkContent.jhtml"
                })
                this.on("addedfile", function () {
                    // console.log($(this.element).attr("class").split(" ")[0])
                    importHeader(0, parseInt($(this.element).attr("class").split(" ")[0]), 0)
                })
            }
        });

    });

    //点击继续添加按钮增加头文件
    var headerCount;
    $("#add_header_file").click(function () {
        //获取头文件内容长度，设置headerCount 区分头文件（从0开始)
        headerCount = $("textarea[name='header_file']").length
        // console.log(headerCount);
        $("#Add").append('<div class="col-sm-2 control-label"></div>' +
                '<div class="col-sm-9 form-inline header" style="margin-top:10px;">' +
                '<div class="form-inline">' +
                '<textarea rows="1" cols="60" class="' + headerCount + '" name="header_file" placeholder="请输入头文件名称"' +
                '></textarea>' +
                '</div>' +
                '<div class="form-inline">' +
                ' <textarea rows="6" cols="60" class="' + headerCount + '" name="header_file_content" placeholder="请输入头文件内容"' +
                '></textarea>' +
                '</div>' +
                ' </div>' +
                '<div class="col-sm-1 form-inline">' +
                '<a onclick="importHeader(0,' + headerCount + ')" class="btn btn-outline btn-danger btn-sm" style="float:right">导入</a>' +
                '</div>')
        //拖拽文件上传  头文件内容 (新添加的头文件初始化)
        $("." + headerCount).dropzone({
            url: "saveimportContent.jhtml",
            paramName: "cfile",
            // acceptedFiles: ".js,.obj,.dae,.c",
            clickable: false,  //去除点击事件
            init: function () {
                this.on("success", function () {
                    location.href = "checkContent.jhtml"
                })
                this.on("addedfile", function () {
                    importHeader(0, headerCount, 0)
                })
            }
        });
    })

    //提交表单时，获取多个头文件内容与ID，并将其组合
    function add_HeaderFile() {
        //获取内容
        var $headers = $(".header").children()
        var headerFileStr = "", $HeaderChilds
        for (var i = 0; i < $headers.length; i++) {
            $HeaderChilds = $($headers[i]).children()[0]
            if (i != 0) {
                headerFileStr += ",,,"
            }
            headerFileStr += $($HeaderChilds).val()
        }
        // console.log(headerFileStr)
        $("#headerFiles").val(headerFileStr)

        // //获取ID
        // var $headerfile_id = $(".headerfile_id")
        // var headerfileIdArr = []
        // if($headerfile_id.length > 0){
        //   for(var d = 0; d < $headerfile_id.length; d++){
        //     headerfileIdArr.push($($headerfile_id[d]).text())
        //  }
        //  // console.log(headerfileIdArr.join(",,,"))
        //  $("#questionHeaderFile_id").val(headerfileIdArr.join(",,,"))
        // }

    }


    //导入头文件时，获取共有多少个头文件,并将其一一对应
    //将已经导入的文件保存
    function importHeader(flag, count, isDrop) {
        // flag:分辨是哪个模块的导入
        // count:分辨是哪个头文件的导入
        // isDrop:判断是否为拖拽

        var headerFileArr = [],
                headerFileContentArr = [],
                countArr = []
        //将已经导入的模块内容存放进session中
        //题目描述
        if ($("#question_description").val() != "") {
            storage.setItem("check_question_description", $("#question_description").val())
        }
        //标准答案
        if ($("#standard_answer").val() != "") {
            storage.setItem("check_standard_answer", $("#standard_answer").val())
        }
        //学生答案
        if ($("#student_answer").val() != "") {
            storage.setItem("check_student_answer", $("#student_answer").val())
        }
        // //主文件标题
        // if($("#headerfile_name").val()!=""){
        //   storage.setItem("check_headerfile_name",$("#headerfile_name").val())
        // }
        //主文件内容
        if ($("#headerfile_content").val() != "") {
            storage.setItem("check_headerfile_content", $("#headerfile_content").val())
        }
        //头文件标题及内容
        //获取头文件的长度,循环遍历将头文件的内容存放进session中
        var $headerFileContent = $("textarea[name='header_file_content']")
        var $headerFile = $("textarea[name='header_file']")
        for (let i = 0; i < $headerFileContent.length; i++) {
            let headerFileContenClass = $($headerFileContent[i]).attr("class")
            if($($headerFileContent[i]).attr("class").indexOf(" ") != -1){
                headerFileContenClass = $($headerFileContent[i]).attr("class").split(" ")[0]
            }else{
                headerFileContenClass = $($headerFileContent[i]).attr("class")
            }
            headerFileContentArr.push(headerFileContenClass + "(====)" + $($headerFileContent[i]).val())
            headerFileArr.push(headerFileContenClass + "(====)" + $($headerFile[i]).val())
            //将除了点击导入的头文件以外的头文件内容存储起来
            if (headerFileContenClass != count) {
                countArr.push(headerFileContenClass)
            }
        }
        // console.log("countArr--->"+countArr)
        storage.setItem("check_headerFileContentArr", headerFileContentArr.join("(youyi)"))
        storage.setItem("check_headerFileArr", headerFileArr.join("(youyi)"))

        //获取flag以及count 区分是哪个textare的导入
        storage.setItem("check_fileFlag", flag)
        storage.setItem("check_count", count)

        //设置标志位 区分是哪个页面发送的请求(编辑/添加)
        storage.setItem("ContentFlag", 1)

        // //获取继续添加的各个头文件
        // if($textareaLen.length > 1){
        //   for (var i = 1; i < $textareaLen.length; i++) {
        //     countArr.push($($textareaLen[i]).attr('class').split(" ")[0])
        //   }
        // }
        var str = countArr.join(",") || "null"
        // console.log("str--->="+str)
        //跳转
        if (isDrop == 1) {   //判断是否为拖拽 (题目描述~主文件内容)
            $.ajax({
                type: "get",
                url: "importContent.jhtml?flag=" + flag + "&headerCount=null&countArr=" + str
                // success:function(){
                //   location.href="editContent.jhtml"
                // }
            })
        } else if (isDrop == 0) {  //判断是否为拖拽 (头文件内容)
            $.ajax({
                type: "get",
                url: "importContent.jhtml?flag=0&headerCount=" + count + "&countArr=" + str
            })
        } else {
            if (count != undefined) {  //头文件
                location.href = "importContent.jhtml?flag=0&headerCount=" + count + "&countArr=" + str
            } else {
                location.href = "importContent.jhtml?flag=" + flag + "&headerCount=null&countArr=" + str
            }
        }


    }


    //
    function addContent(random, content,header) {
        $("#Add").append('<div class="col-sm-2 control-label"></div>' +
                '<div class="col-sm-9 form-inline header" style="margin-top:10px;">' +
                '<div class="form-inline">' +
                '<textarea rows="1" cols="60" class="' + random + '" name="header_file" placeholder="请输入头文件名称"' +
                '>'+header+'</textarea>' +
                '</div>' +
                '<div class="form-inline">' +
                '<textarea rows="6" cols="60" class="' + random + '" name="header_file_content" placeholder="请输入头文件内容"' +
                '>' + content + '</textarea>' +
                '</div>' +
                '</div>' +
                '<div class="col-sm-1 form-inline">' +
                '<a onclick="importHeader(0,' + random + ')" class="btn btn-outline btn-danger btn-sm">导入</a>' +
                '</div>')
    }


</script>
</body>
</html>
