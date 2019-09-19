<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>Insertar/Modificar departamento</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">Insertar/Modificar departamento</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Departamentos" method="POST">
        <div class="campo">
            <label for="iddepartamento">ID Departamento</label>
            <i></i>
            <input type="text" class="short" name="iddepartamento" id="iddepartamento" value="${v.iddepartamento}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="departamento">Departamento</label>
            <i></i>
            <input type="text" name="departamento" id="departamento" value="${v.departamento}">
        </div>
        <div class="campo">
            <label for="zona">Zona</label>
            <i></i>
            <select name="zona" id="zona">
                <option value="0">-- Seleccionar --</option>
                <c:if test="${accion == 'insertar'}">
                    <option value="OCCIDENTAL">OCCIDENTAL</option>
                    <option value="ORIENTAL">ORIENTAL</option>
                    <option value="CENTRAL">CENTRAL</option>
                </c:if>
                <c:if test="${accion != 'insertar'}">
                    <c:forEach var="i" items="${Zonas}">
                        <c:if test="${v.zona == i.zona}">
                            <option value="${i.zona}" selected>${i.zona}</option>  
                        </c:if>
                        <c:if test="${v.zona != i.zona}">
                            <option value="${i.zona}">${i.zona}</option>  
                        </c:if>
                    </c:forEach>
                </c:if>
            </select>
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Departamentos" class="ghost-red">Cancelar</a>
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
        new Campo('departamento', 'texto', '', 30, 6),
        new Campo('zona', 'select'),
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