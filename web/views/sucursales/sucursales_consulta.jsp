<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>Sucursales</title>
    <link href="resources/css/tablas.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="contenedor">
    <main> 
        <%@include file="../../WEB-INF/jspf/_message.jspf"%>
        <div class="fondo">
            <div class="conf">
                <h1>Lista de Sucursales</h1>
                <a href="${pageContext.servletContext.contextPath}/Sucursales?accion=insertar"><i class="icon icon-plus"></i> Agregar</a>
                <div class="header">
                    <form action="">
                        <input type="text" id="buscar" autocomplete="off" placeholder="Buscar...">
                        <select name="" id="category" title="Departamento"> 
                            <option value="0">-- Filtrar por departamento --</option>
                            <c:forEach var="d" items="${Departamentos}">
                                <option value="${d.departamento}">${d.departamento}</option>
                            </c:forEach>
                        </select>
                    </form>
                </div>
                <div class="tablas">
                    ${tabla}
                </div>
            </div>
        </div>
    </main>
</div>
<script>
    const openWindow = function(url){
        location.href = url;
    }
</script>

<%@include file="../../WEB-INF/jspf/_footer.jspf"%>