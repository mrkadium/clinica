<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>Permisos</title>
    <link href="resources/css/tablas.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/maestro_detalle.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>
<div class="contenedor">
    <main>
        <%@include file="../../WEB-INF/jspf/_message.jspf"%>
        <div class="fondo">
            <div class="conf">
                <h1>Lista de Permisos</h1>
                <form id="formulario" action="${pageContext.servletContext.contextPath}/Permisos" method="POST">
                    <div class="campo">
                        <label for="idrol">Rol</label>
                        <i></i>
                        <div class="varios">
                            <input type="text" class="short" name="idrol" id="idrol" value="${idrol}" readonly tabindex="-1">
                            <input type="text" class="long" name="rol" id="rol" value="${rol}" readonly tabindex="-1">
                            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Permisos?accion=roles');" class="lupa"><i class="icon icon-search"></i></a>
                        </div>
                    </div>
<!--                    <div class="campo">
                        <label for="campo1">Menú</label>
                        <i></i>
                        <div class="varios">
                            <input type="text" class="short" name="campo1" id="campo1" value="1" readonly tabindex="-1">
                            <input type="text" class="long" name="campo1" id="campo1" readonly tabindex="-1">
                            <a href="#" class="lupa"><i class="icon icon-search"></i></a>
                        </div>
                    </div>-->

                    <input type="submit" name="" id="" class="ghost-blue" value="Guardar">
                    <div class="header">
                        <!--<form action="">-->
                            <input type="text" id="buscar" autocomplete="off" placeholder="Buscar...">
                            <input type="reset" id="reset" value="Quitar filtro">
                        <!--</form>-->
                    </div>
                    <div class="tablas">
                        ${tabla}
                    </div>
                </form>
            </div>
        </div>
    </main>
</div>
<script>    
        function abrirVentana (URL){
            window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
        }
</script>
<%@include file="../../WEB-INF/jspf/_footer.jspf"%>

    