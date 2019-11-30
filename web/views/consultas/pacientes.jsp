<!DOCTYPE html>
<html lang="Es-es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="resources/img/Clínica.ico">
    <link rel="stylesheet" href="resources/css/principal.css">
    <link rel="stylesheet" href="resources/css/tablas.css">
    <link rel="stylesheet" href="resources/css/fonts.css">
    <script src="resources/js/main.js"></script>
    <style>
        #table01 tbody tr:hover{
            cursor: pointer;
        }
    </style>
    <title>Seleccionar Paciente</title>
</head>
<body>
    <div class="contenedor">
        <main> 
            <div class="fondo">
                <div class="conf">
                    <h1>Selección de paciente</h1>
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
    <script>
        function _Seleccionar_(row){
            var idempleado = row.cells[0].innerHTML;
            var empleado = row.cells[1].innerHTML;
            window.opener.setDataPaciente(idempleado, empleado);
            window.close();
        }
    </script>
</body>
</html>