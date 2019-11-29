<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="resources/css/fonts.css">
        <link rel="stylesheet" href="resources/css/principal.css">
        <link rel="stylesheet" href="resources/css/formulario.css">
        <link rel="stylesheet" href="resources/css/tablas.css">
        <link rel="stylesheet" href="resources/css/formulario_con_tabla.css">
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
                <label for="idconsumible">Consumible</label>
                <i></i>
                <div class="varios">
                    <input type="text" required class="short" name="idconsumible" id="idconsumible" value="" readonly tabindex="-1">
                    <input type="text" required class="long" name="nombre" id="nombre" readonly tabindex="-1">
                    <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Ventas?accion=consumibles');" class="lupa"><i class="icon icon-search"></i></a>
                </div>
            </div>
            <div class="fondo">
                <div class="conf">
                    <h1>Selección de consumible</h1>
                    <div class="header">
                        <input type="text" id="buscar" autocomplete="off" placeholder="Buscar...">
                    </div>
                    <div class="tablas">
                        ${tabla}
                    </div>
                </div>
            </div> 
            <div class="campo">
                <label for="precio">Precio</label>
                <i></i>
                <input type="text" readonly required class="short" name="precio" id="precio">
            </div>
            <div class="campo">
                <label for="cantidad">Cantidad</label>
                <i></i>
                <input type="number" required min="1" max="" class="short" name="cantidad" id="cantidad">
            </div>
            <div class="campo">
                <label for="existencias">Existencias disponibles</label>
                <i></i>
                <input type="text" required class="short" name="existencias" id="existencias">
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
        const precio_compra = document.querySelector("#precio");
        const cantidad = document.querySelector("#cantidad");
        const existencias = document.querySelector("#existencias");

        formulario.addEventListener('submit', function(e){
            e.preventDefault();           
            window.opener.setDataConsumible(idconsumible.value, nombre.value, precio_compra.value, cantidad.value);
            window.close();
        });
        function _Seleccionar_(row){
            var idjefe = row.cells[0].innerHTML;
            var jefe = row.cells[1].innerHTML;
            var existencia = row.cells[3].innerHTML;
            var precio = row.cells[4].innerHTML;
            
            idconsumible.value = idjefe;
            nombre.value = jefe;
            cantidad.max = existencia;
            precio_compra.value = precio;
            existencias.value = existencia;
        }
    </script>
    </body>
</html>