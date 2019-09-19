<%@include file="../WEB-INF/jspf/_header.jspf"%>
    <title>Error</title>
    <link href="resources/css/error.css" rel="stylesheet" type="text/css"/>
<%@include file="../WEB-INF/jspf/_navbar.jspf"%>
</head>
<body>
    <div class="error">
        <div class="errorLogo">
            <img src="resources/img/Clínica.png" onclick="openWindow('${pageContext.servletContext.contextPath}/Home')" alt="">
        </div>
        <div class="errorContent">
            <h1><i class="icon icon-warning"></i> Error interno del servidor</h1>
            <p>Por el momento el servidor se encuentra en proceso de mantenimiento, por lo que algunas de las características del sitio no podrán ser utilizadas</p>
            <small><i>error500: internal server error.</i></small>
            <a href="${pageContext.servletContext.contextPath}/Home"><i class="icon icon-home3"></i> Volver al sitio</a>
        </div>
    </div>
</body>
</html>