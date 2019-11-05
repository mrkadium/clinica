<%@include file="../../WEB-INF/jspf/_header.jspf" %>
    <title>Contactos</title>
    <link href="resources/css/tablas.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="contenedor">
    <main>
        <%@include file="../../WEB-INF/jspf/_message.jspf"%>
        <div class="fondo">
            <div class="conf">
                <h1>Lista de Contactos</h1>
                <a href="${pageContext.servletContext.contextPath}/Contactos?accion=insertar"><i class="icon icon-plus"></i> Agregar</a>
                <div class="header">
                    <form action="">
                        <input type="text" id="buscar" autocomplete="off" placeholder="Buscar...">
                    </form>
                </div>
                <div class="tablas">
                    ${tabla}
                </div>
            </div>
        </div>
    </main>
</div>

<%@include file="../../WEB-INF/jspf/_footer.jspf"%>