<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} cargos</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} contacto</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Contactos" method="POST">
        <div class="campo">
            <label for="idcontacto">ID Contacto</label>
            <i></i>
            <input type="text" class="short" name="idcontacto" id="idcontacto" value="${v.idcontacto}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="tipo">Tipo</label>
            <i></i>
            <select id="tipo" name="tipo">
                <option value="0">-- SELECCIONAR --</option>
                <c:forEach var="t" items="${Tipos}">
                    <c:if test="${t==v.tipo}">
                        <option value="${t}" selected>${t}</option>
                    </c:if>
                    <c:if test="${t!=v.tipo}">
                        <option value="${t}">${t}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <div class="campo">
            <label for="contacto">Contacto</label>
            <i></i>
            <input type="text" name="contacto" id="contacto" value="${v.contacto}">
        </div>
        <div class="campo">
            <label for="idempleado">Empleado</label>
            <i></i>
            <input type="text" class="short" name="idempleado" id="idempleado" value="${v.idempleado}" readonly tabindex="-1">
            <input type="text" class="long" name="empleado" id="empleado" value="${Empleado.apellidos}, ${Empleado.nombres}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Contactos?accion=empleados');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Contactos" class="ghost-red">Cancelar</a>
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
    const openWindow = function(url){
        location.href = url;
    }

    // VALIDACIONES DE FORMULARIO
    const formulario = document.querySelector('#formulario');

    // (input, tipo, msg, limiteSuperior, limiteInferior, soloNumeros, soloLetras)
    let campos = [
        new Campo('tipo', 'select'),
        new Campo('contacto', 'texto'),
        new Campo('idempleado', 'texto')
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
