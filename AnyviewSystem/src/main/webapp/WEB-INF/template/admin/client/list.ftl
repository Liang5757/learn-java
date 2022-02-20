<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

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

<style>
    h2 {
        color: rgb(0, 0, 0);
        font-style:inherit;
        position:relative;
        left: 160px; 

    }
    .hint {
        font-size: 14px;
        font-style: oblique;
        color:cadetblue
    }
    .submit-bottun{
        position:relative;
        left: 380px; 
    }

    .a-upload{
        border-top:2px solid rgb(16, 156, 190); 
        border-bottom:2px solid rgb(16, 156, 190);
    }

    .file-choose{
        position:relative;
        left: 180px; 
    }

    #radio-choose{
        position:relative;
        left: 180px;
    }
    
    #version-text{
        position:relative;
        left: 380px;
    }

</style>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight index">
    <!--第一个表BEGIN-->
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <!-- <p>当前版本号为:${versionId}</p> -->

                    <form action="uploadClient.jhtml" method="post" enctype="multipart/form-data" class="a-upload">
                        <h2> 新版本的学生客户端上传</h2>
                        <div class="file-choose">
                            <input type="file" name="uploadFile" />
                        </div>
                        </br>
       <!--                <div id="version-text">
                            	版本号:<input type="text" name="version"/>
                        </div>
        -->
                        <div id="radio-choose">
                        <p>该版本是否兼容？</p>
                        <input type="radio" name="compatibility" value="兼容">兼容
                        <input type="radio" name="compatibility" value="不兼容">不兼容
                        </div>
                        <div class="submit-bottun">
                            <input type="submit" value="上传" class="btn btn-sm btn-danger"/>
                        </div>
                        <p class="hint">提示：上传文件需为压缩包形式（zip和rar格式）。</p>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>


</body>

<!-- BEGIN Script -->
[#include "/admin/include/script.ftl"]
<!-- END Script -->


<script>
    $(document).ready(function () {
        [@flash_message /]
        $(".chosen-select").chosen({width: "100%"});
    });
</script>

</html>
