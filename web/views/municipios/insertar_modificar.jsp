<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>Insertar/Modificar municipio</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">Insertar/Modificar municipio</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Municipios" method="POST">
        <div class="campo">
            <label for="idmunicipio">ID Municipio</label>
            <i></i>
            <input type="text" class="short" name="idmunicipio" id="idmunicipio" value="${v.idmunicipio}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="municipio">Municipio</label>
            <i></i>
            <input type="text" name="municipio" id="municipio" value="${v.municipio}">
        </div>
        <div class="campo">
            <label for="iddepartamento">Departamento</label>
            <i></i>
            <select name="iddepartamento" id="iddepartamento">
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
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Municipios" class="ghost-red">Cancelar</a>
    </form>
</div>

<script>
    const openWindow = function(url){
        location.href = url;
    }

    // VALIDACIONES DE FORMULARIO
    const formulario = document.querySelector('#formulario');

    // (input, tipo, msg, limiteSuperior, limiteInferior, soloNumeros, soloLetras)
    let campos = [
        new Campo('municipio', 'texto'),
        new Campo('iddepartamento', 'select'),
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