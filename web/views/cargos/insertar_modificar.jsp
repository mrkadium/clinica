<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} cargos</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} cargos</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Cargos" method="POST">
        <div class="campo">
            <label for="idcargo">ID Cargo</label>
            <i></i>
            <input type="text" class="short" name="idcargo" id="idcargo" value="${v.idcargo}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="cargo">Cargo</label>
            <i></i>
            <input type="text" name="cargo" id="cargo" value="${v.cargo}">
        </div>
        <div class="campo">
            <label for="descripcion">Descripción</label>
            <i></i>
            <input type="text" name="descripcion" id="descripcion" value="${v.descripcion}">
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