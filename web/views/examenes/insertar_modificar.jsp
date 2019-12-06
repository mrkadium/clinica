<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} exámenes</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} examen</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Examenes" method="POST">
        <div class="campo">
            <label for="idexamen">ID Examen</label>
            <i></i>
            <input type="text" class="short" name="idexamen" id="idexamen" value="${v.idexamen}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="examen">Examen</label>
            <i></i>
            <input type="text" name="examen" id="examen" value="${v.examen}">
        </div>
        <div class="campo">
            <label for="descripcion">Descripción</label>
            <i></i>
            <textarea type="text" name="descripcion" id="descripcion">${v.descripcion}</textarea>
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Examenes" class="ghost-red">Cancelar</a>
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