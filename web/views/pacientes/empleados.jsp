<!DOCTYPE html>
<html lang="Es-es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="resources/img/Cl�nica.ico">
    <link rel="stylesheet" href="resources/css/principal.css">
    <link rel="stylesheet" href="resources/css/tablas.css">
    <link rel="stylesheet" href="resources/css/fonts.css">
    <script src="resources/js/main.js"></script>
    <style>
        #table01 tbody tr:hover{
            cursor: pointer;
        }
    </style>
    <title>Seleccionar Empleado</title>
</head>
<body>        
    <main>
        <div class="fondo">
            <div class="header">
                <h1>Selecci�n de empleado</h1>
                <form action="">
                    <input type="text" id="buscar" autocomplete="off" placeholder="Buscar...">
                </form>
            </div>
            <div class="tablas">
                ${tabla}
            </div>
        </div>
    </main>            
    <script>
        function _Seleccionar_(row){
            var dui_empleado = row.cells[0].innerHTML;
            var empleado = row.cells[2].innerHTML;
            window.opener.setDataEmpleado(dui_empleado, empleado);
            window.close();
        }
    </script>
</body>
</html>