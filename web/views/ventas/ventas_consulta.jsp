<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>Ventas</title>
    <link href="resources/css/tablas.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="contenedor">
    <main>
        <%@include file="../../WEB-INF/jspf/_message.jspf"%>
        <div class="fondo">
            <div class="conf">
                <h1>Lista de Ventas</h1>
                <a href="${pageContext.servletContext.contextPath}/Ventas?accion=insertar"><i class="icon icon-plus"></i> Agregar</a>
                <div class="header">
                    <form action="">
                        <input type="text" id="buscar" autocomplete="off" placeholder="Buscar...">
                        <select name="" id="category" title="Estado"> 
                            <option value="0">-- Filtrar por estado --</option>
                            <option value="Pendiente">Pendiente</option>
                            <option value="Cancelada">Cancelada</option>
                        </select>
                        <input type="reset" id="reset" value="Quitar filtro">
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