<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} paciente</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} paciente</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Pacientes" method="POST">
        <div class="campo">
            <label for="idpaciente">ID</label>
            <i></i>
            <input readonly type="text" name="idpaciente" class="short" id="idpaciente" value="${v.idpaciente}">
        </div>
        <div class="campo">
            <label for="expediente">Expediente</label>
            <i></i>
            <input type="text" name="expediente" id="expediente" value="${v.expediente}">
        </div>
        <div class="campo">
            <label for="nombres">Nombres</label>
            <i></i>
            <input type="text" name="nombres" id="nombres" value="${v.nombres}">
        </div>
        <div class="campo">
            <label for="apellidos">Apellidos</label>
            <i></i>
            <input type="text" name="apellidos" id="apellidos" value="${v.apellidos}">
        </div>
        <div class="campo">
            <label for="fecha_nacimiento">Fecha de nacimiento</label>
            <i></i>
            <input type="date" name="fecha_nacimiento" id="fecha_nacimiento" value="${v.fecha_nacimiento}">
        </div>
        <div class="campo">
            <label for="genero">Género</label>
            <i></i>
            <div>
                <input type="radio" name="genero" value="Femenino" id="femenino" ${v.genero== 'Femenino' ? 'checked' : ''}><label for="femenino">Femenino</label>
            </div>
            <div>
                <input type="radio" name="genero" value="Masculino" id="masculino" ${v.genero == 'Masculino' ? 'checked' : ''}><label for="masculino">Masculino</label>
            </div>
        </div>
        <div class="campo">
            <label for="telefono">Teléfono</label>
            <i></i>
            <input type="text" name="telefono" id="telefono" value="${v.telefono}">
        </div>
        <div class="campo">
            <label for="email">Email</label>
            <i></i>
            <input type="text" name="email" id="email" value="${v.email}">
        </div>
        <div class="campo">
            <label for="idmunicipio">Municipio</label>
            <i></i>
            <input type="text" class="short" name="idmunicipio" id="idmunicipio" value="${v.idmunicipio}" readonly tabindex="-1">
            <input type="text" class="long" name="municipio" id="municipio" value="${m.municipio}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Pacientes?accion=municipios');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="idsucursal">Sucursal</label>
            <i></i>
            <input type="text" class="short" name="idsucursal" id="idsucursal" value="${v.idsucursal}" readonly tabindex="-1">
            <input type="text" class="long" name="direccion_suc" id="direccion_suc" value="${s.direccion}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Pacientes?accion=sucursales');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="idempleado">Empleado</label>
            <i></i>
            <input type="text" class="short" name="idempleado" id="idempleado" value="${v.idempleado}" readonly tabindex="-1">
            <input type="text" class="long" name="empleado" id="empleado" value="${e.nombres} ${e.apellidos}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Pacientes?accion=empleados');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        
        <div class="campo" id="tabla">
            <label for="campo2">Consultas</label>
            <i></i>
            <a  onclick="abrirVentana('${pageContext.servletContext.contextPath}/Consultas?accion=agregarExamen');" class="agregar"><i class="icon icon-plus"></i> Agregar</a>
            <div class="tablas">
                <table id="table01" class="examenes">
                    ${tabla}
                </table>
            </div>
        </div>
        
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Sucursales" class="ghost-red">Cancelar</a>
    </form>
</div>

<script>
    function abrirVentana(URL){
        window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
    }
    function setDataMunicipio(idmunicipio, municipio) {
        document.getElementById("idmunicipio").value = idmunicipio;
        document.getElementById("municipio").value = municipio;
    }
    function setDataSucursal(idsucursal, direccion) {
        document.getElementById("idsucursal").value = idsucursal;
        document.getElementById("direccion_suc").value = direccion;
    }
    function setDataEmpleado(idempleado, empleado) {
        document.getElementById("idempleado").value = idempleado;
        document.getElementById("empleado").value = empleado;
    }

    // VALIDACIONES DE FORMULARIO
    const formulario = document.querySelector('#formulario');

    // (input, tipo, msg, limiteSuperior, limiteInferior, soloNumeros, soloLetras)
    let campos = [
        new Campo('titulo', 'texto'),
        new Campo('resumen', 'texto'),
        new Campo('contenido', 'texto')
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