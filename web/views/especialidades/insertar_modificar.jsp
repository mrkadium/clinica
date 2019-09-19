<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} especialidad</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} especialidad</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Especialidades" method="POST">
        <div class="campo">
            <label for="idespecialidad">ID Especialidad</label>
            <i></i>
            <input type="text" class="short" name="idespecialidad" id="idespecialidad" value="${v.idespecialidad}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="especialidad">Especialidad</label>
            <i></i>
            <input type="text" name="especialidad" id="especialidad" value="${v.especialidad}">
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Cargos" class="ghost-red">Cancelar</a>
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
        new Campo('especialidad', 'texto')
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