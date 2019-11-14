<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} horario</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} horario</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Horarios" method="POST">
        <div class="campo">
            <label for="idhorario">ID Horario</label>
            <i></i>
            <input type="text" class="short" name="idhorario" id="idhorario" value="${v.idhorario}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="idespecialidad">Especialidad</label>
            <i></i>
            <input type="text" class="short" name="idespecialidad" id="idespecialidad" value="${v.idespecialidad}" readonly tabindex="-1">
            <input type="text" class="long" name="especialidad" id="especialidad" value="${e.especialidad}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Horarios?accion=especialidades');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="idsucursal">Sucursal</label>
            <i></i>
            <input type="text" class="short" name="idsucursal" id="idsucursal" value="${v.idsucursal}" readonly tabindex="-1">
            <input type="text" class="long" name="direccion_sucursal" id="direccion_sucursal" value="${s.direccion}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Horarios?accion=sucursales');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="hora_inicio">Desde</label>
            <i></i>
            <input type="time" name="hora_inicio" id="hora_inicio" value="${v.hora_inicio}">
        </div>
        <div class="campo">
            <label for="hora_fin">Hasta</label>
            <i></i>
            <input type="time" name="hora_fin" id="hora_fin" value="${v.hora_fin}">
        </div>
        <div class="campo">
            <label for="dias">Días</label>
            <i></i>
            <textarea type="text" name="dias" id="dias">${v.dias}</textarea>
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Horarios" class="ghost-red">Cancelar</a>
    </form>
</div>

<script>
    function abrirVentana(URL){
        window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
    }
    function setDataEspecialidad(idespecialidad, especialidad) {
        document.getElementById("idespecialidad").value = idespecialidad;
        document.getElementById("especialidad").value = especialidad;
    }
    function setDataSucursal(idsucursal, direccion_sucursal) {
        document.getElementById("idsucursal").value = idsucursal;
        document.getElementById("direccion_sucursal").value = direccion_sucursal;
    }

    // VALIDACIONES DE FORMULARIO
    const formulario = document.querySelector('#formulario');

    // (input, tipo, msg, limiteSuperior, limiteInferior, soloNumeros, soloLetras)
    let campos = [
        new Campo('cargo', 'texto'),
        new Campo('descripcion', 'texto')
    ];

    formulario.addEventListener('submit', function(e){
        let evaluar = true;
        for(c of campos){
            if(c.alertar() == false){
                evaluar = false;
            }
        }
        if(!evaluar){
            e.preventDefault();
        }else{
            formulario.submit();
        }
    });
</script>
<%@include file="../../WEB-INF/jspf/_footer.jspf"%>