<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ES-es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Inicio de sesión</title>
    <link rel="stylesheet" href="resources/css/login.css">
</head>
<body>
    <div class="contenedor">
        <div class="logo">
            <img src="resources/img/Clínica.jpg" alt="">
        </div>
        <div class="formulario">
            <h2>Clínicas Médicas <span>San Antonio</span></h2>
            <h1>Inicio de sesión</h1>
            <c:if test="${status == 3}">
                <h1 style="color: coral">${msg}</h1>
            </c:if>
            <form action="Login" method="POST">
                <input type="text" name="user" id="" ${status == 3 ? 'disabled' : ''} placeholder="Usuario" autofocus>
                <input type="password" name="pass" id="" ${status == 3 ? 'disabled' : ''} placeholder="Contraseña">
                <input type="submit" value="Ingresar" name="" id="" ${status == 3 ? 'disabled' : ''}>
            </form>
                <a href="${pageContext.servletContext.contextPath}/Home">Volver a página principal</a>
        </div>
    </div>
</body>
</html>