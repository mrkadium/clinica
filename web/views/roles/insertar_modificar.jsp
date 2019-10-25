<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} rol</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} rol</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Roles" method="POST">
        <div class="campo">
            <label for="idrol">ID Rol</label>
            <i></i>
            <input type="text" class="short" name="idrol" id="idrol" value="${v.idrol}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="rol">Rol</label>
            <i></i>
            <input type="text" name="rol" id="rol" value="${v.rol}">
        </div>
        <div class="campo">
            <label for="descripcion">Descripción</label>
            <i></i>
            <textarea name="descripcion" id="descripcion">${v.descripcion}</textarea>
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Roles" class="ghost-red">Cancelar</a>
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
        new Campo('rol', 'texto')
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