<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>Insertar/Modificar sucursal</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">Insertar/modificar sucursal</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Sucursales" method="POST">
        <div class="campo">
            <label for="idsucursal">ID</label>
            <i></i>
            <input type="text" name="idsucursal" class="short" id="idsucursal" value="${v.idsucursal}">
        </div>
        <div class="campo">
            <label for="departamento">Departamento</label>
            <i></i>
            <select name="departamento" id="departamento">
                <option value="0">-- Seleccionar --</option>
                <c:forEach var="d" items="${Departamentos}">
                    <c:if test="${d.iddepartamento == v.iddepartamento}">
                        <option value="${d.iddepartamento}" selected>${d.departamento}</option>
                    </c:if>
                    <c:if test="${d.iddepartamento != v.iddepartamento}">
                        <option value="${d.iddepartamento}">${d.departamento}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <div class="campo">
            <label for="idmunicipio">Municipio</label>
            <i></i>
            <select name="idmunicipio" id="idmunicipio">
                <option value="0">-- Seleccionar --</option>
                <c:forEach var="m" items="${Municipios}">
                    <c:if test="${m.idmunicipio == v.idmunicipio}">
                        <option value="${m.idmunicipio}" selected>${m.municipio}</option>
                    </c:if>
                    <c:if test="${m.idmunicipio != v.idmunicipio}">
                        <option value="${m.idmunicipio}">${m.municipio}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <div class="campo">
            <label for="direccion">Dirección</label>
            <i></i>
            <textarea name="direccion" id="direccion">${v.direccion}</textarea>
        </div>
        <div class="campo">
            <label for="telefono1">Teléfono 1</label>
            <i></i>
            <input type="text" name="telefono1" id="telefono1" value="${v.telefono1}">
        </div>
        <div class="campo">
            <label for="telefono2">Teléfono 2</label>
            <i></i>
            <input type="text" name="telefono2" id="telefono2" value="${v.telefono2}">
        </div>
        <div class="campo">
            <label for="email">Email</label>
            <i></i>
            <input type="text" name="email" id="email" value="${v.email}">
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Sucursales" class="ghost-red">Cancelar</a>
    </form>
</div>

<script>
    const openWindow = function(url){
        location.href = url;
    }
    
    //SELECCIONAR DEPARTAMENTO
    
    

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