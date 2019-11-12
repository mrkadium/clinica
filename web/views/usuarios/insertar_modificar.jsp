<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} usuarios</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} usuarios</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Usuarios" method="POST">
        <div class="campo">
            <label for="idusuario">ID Usuario</label>
            <i></i>
            <input type="text" class="short" name="idusuario" id="idusuario" value="${v.idusuario}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="usuario">Usuario</label>
            <i></i>
            <input type="text" name="usuario" id="usuario" value="${v.usuario}">
        </div>
        <div class="campo">
            <label for="clave">Clave</label>
            <i></i>
            <input type="text" name="clave" id="clave" value="">
        </div>
        <div class="campo">
            <label for="idempleado">Empleado</label>
            <i></i>
            <input type="text" class="short" name="idempleado" id="idempleado" value="${v.idempleado}" readonly tabindex="-1">
            <input type="text" class="long" name="empleado" id="empleado" value="${e.nombres} ${e.apellidos}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Usuarios?accion=empleados');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="idrol">Rol</label>
            <i></i>
            <select name="idrol" id="idrol">
                <option value="0">-- SELECCIONAR --</option>
                <c:forEach var="r" items="${Roles}">
                    <c:if test="${r.idrol == v.idrol}">
                        <option value="${r.idrol}" selected>${r.rol}</option>
                    </c:if>
                    <c:if test="${r.idrol != v.idrol}">
                        <option value="${r.idrol}">${r.rol}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <div class="campo">
            <label for="estado">Estado</label>
            <i></i>
            <select name="estado" id="estado">
                <option value="Activo" ${v.estado == 'Activo' ? 'selected' : ''}>Activo</option>
                <option value="Bloqueado" ${v.estado == 'Bloqueado' ? 'selected' : ''}>Inactivo</option>
            </select>
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Usuarios" class="ghost-red">Cancelar</a>
    </form>
</div>

<script>
    function abrirVentana(URL){
        window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
    }
    function setDataEmpleado(idempleado, empleado) {
        document.getElementById("idempleado").value = idempleado;
        document.getElementById("empleado").value = empleado;
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