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
            <input type="date" name="fecha_nacimiento" id="fecha_nacimiento">
        </div>
        <div class="campo">
            <label for="genero">Género</label>
            <i></i>
            <div>
                <input type="radio" name="genero" value="FEMENINO" id="femenino"><label for="femenino">Femenino</label>
            </div>
            <div>
                <input type="radio" name="genero" value="MASCULINO" id="masculino"><label for="masculino">Masculino</label>
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
            <select name="idmunicipio">
                <option value="0">-- SELECCIONAR --</option>
                <c:forEach var="m" items="${Municipios}">
                    <c:if test="${v.idmunicipio == m.idmunicipio}">
                        <option value="${m.idmunicipio}" selected>${m.municipio}</option>
                    </c:if>
                    <c:if test="${v.idmunicipio != m.idmunicipio}">
                        <option value="${m.idmunicipio}">${m.municipio}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <div class="campo">
            <label for="codigo_sucursal">Sucursal</label>
            <i></i>
            <input type="text" class="short" name="codigo_sucursal" id="codigo_sucursal" readonly tabindex="-1">
            <input type="text" class="long" name="direccion" id="direccion" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Pacientes?accion=sucursales');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="dui_empleado">Empleado</label>
            <i></i>
            <input type="text" class="short" name="dui_empleado" id="dui_empleado" readonly tabindex="-1">
            <input type="text" class="long" name="empleado" id="empleado" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Pacientes?accion=empleados');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Sucursales" class="ghost-red">Cancelar</a>
    </form>
</div>

<script>
    function abrirVentana(URL){
        window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
    }
    function setDataSucursal(codigo_sucursal, direccion) {
        document.getElementById("codigo_sucursal").value = codigo_sucursal;
        document.getElementById("direccion").value = direccion;
    }
    function setDataEmpleado(dui_empleado, empleado) {
        document.getElementById("dui_empleado").value = dui_empleado;
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