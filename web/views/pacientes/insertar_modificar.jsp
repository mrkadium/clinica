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
            <input type="text" name="expediente" class="short" id="expediente" value="${v.expediente}">
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
            <label for="resumen">Resumen</label>
            <i></i>
            <!--<input type="text" name="resumen" id="resumen" value="${v.resumen}">-->            
            <textarea name="resumen" id="resumen">${v.resumen}</textarea>
        </div>
        <div class="campo">
            <label for="contenido">Contenido</label>
            <i></i>
            <!--<input type="text" name="contenido" id="contenido" value="${v.contenido}">-->
            <textarea name="contenido" id="contenido">${v.contenido}</textarea>
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Sucursales" class="ghost-red">Cancelar</a>
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