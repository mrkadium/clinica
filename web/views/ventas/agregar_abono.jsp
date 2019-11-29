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
                <label for="precio">Precio</label>
                <i></i>
                <input type="text" readonly required class="short" name="precio" id="precio">
            </div>
            <div class="campo">
                <label for="cantidad">Cantidad</label>
                <i></i>
                <input type="number" required min="1" max="" class="short" name="cantidad" id="cantidad">
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