
<!DOCTYPE html>
<html>

<head>

     <title>${setting.siteName} - 主页</title>
    <meta name="keywords" content="${setting.siteName}">
  <meta name="description" content="${setting.siteName}">


    <link href="${base}/resources/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="${base}/resources/css/font-awesome.min.css" rel="stylesheet">

    <link href="${base}/resources/css/animate.min.css" rel="stylesheet">
    <link href="${base}/resources/css/style.min.css?v=3.0.0" rel="stylesheet">

</head>

<body class="gray-bg">


    <div class="middle-box text-center animated fadeInDown">
        <h3 class="font-bold"> ${message("admin.error.message")}</h3>
        <div class="error-desc">
				[#if constraintViolations?has_content]
					[#list constraintViolations as constraintViolation]
						[${constraintViolation.propertyPath}] ${constraintViolation.message}<br/>
					[/#list]
				[/#if]
        </div>
    </div>

       <!-- 全局js -->
    <script src="${base}/resources/js/jquery-2.1.1.min.js"></script>
    <script src="${base}/resources/js/bootstrap.min.js?v=3.4.0"></script>


</body>

</html>
