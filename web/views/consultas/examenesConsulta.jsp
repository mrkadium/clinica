<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="resources/css/fonts.css">
        <link rel="stylesheet" href="resources/css/principal.css">
        <link rel="stylesheet" href="resources/css/formulario.css">
        <link rel="stylesheet" href="resources/css/tablas.css">
        <link rel="stylesheet" href="resources/css/formulario_con_tabla.css">
        <script type="text/javascript" src="resources/js/main.js"></script>
        <style>
            #table01 tbody tr:hover{
                cursor: pointer;
            }
        </style>
    </head>
    <body>
    <div class="formulario">
        <h1 id="titulo">Formulario</h1>
        <form id="formulario" action="">
            <div class="campo">
                <label for="idconsumible">Examen</label>
                <i></i>
                <div class="varios">
                    <input type="text" required class="short" name="idconsumible" id="idconsumible" value="" readonly tabindex="-1">
                    <input type="text" required class="long" name="nombre" id="nombre" readonly tabindex="-1">
                    <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Ventas?accion=consumibles');" class="lupa"><i class="icon icon-search"></i></a>
                </div>
            </div>
            <div class="fondo">
                <div class="conf">
                    <h1>Selección de examen</h1>
                    <div class="header">
                        <input type="text" id="buscar" autocomplete="off" placeholder="Buscar...">
                    </div>
                    <div class="tablas">
                        ${tabla}
                    </div>
                </div>
            </div> 
            <div class="campo">
                <label for="estado">Estado</label>
                <i></i>
                <select type="text" required class="short" name="estado" id="estado">
                    <option value="Pendiente">Pendiente</option>
                    <option value="Revisado">Revisado</option>
                </select>
            </div>
            <div class="campo">
                <label for="fecha_revision">Fecha de revisión</label>
                <i></i>
                <input type="date" name="fecha_revision" id="fecha_revision">
            </div>
            <div class="campo">
                <label for="resultados">Resultados</label>
                <i></i>
                <input type="text" class="short" name="resultados" id="resultados">
            </div>
            <div class="botones">
                <input type="submit" name="" id="" class="ghost-blue" value="Agregar"> 
                <a href="" class="ghost-red">Cancelar</a>
                <p style="display:none"><i>Procesando...</i></p>
            </div>
        </form>
    </div>
    <script>
        const formulario = document.querySelector("#formulario");
        function abrirVentana(URL){
            window.open(URL,"ventana1","width=500,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
        }
            
        const idconsumible = document.querySelector("#idconsumible");
        const nombre = document.querySelector("#nombre");
        const precio_compra = document.querySelector("#estado");
        const cantidad = document.querySelector("#fecha_revision");
        const existencias = document.querySelector("#resultados");

        formulario.addEventListener('submit', function(e){
            e.preventDefault();           
            window.opener.setDataExamen(idconsumible.value, nombre.value, precio_compra.value, cantidad.value, existencias.value);
            window.close();
        });
        function _Seleccionar_(row){
            var idjefe = row.cells[0].innerHTML;
            var jefe = row.cells[1].innerHTML;
            
            idconsumible.value = idjefe;
            nombre.value = jefe;
        }
    </script>
    </body>
</html>