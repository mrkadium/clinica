<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>Insertar/Modificar departamento</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">Modificar información de la empresa</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Empresas" method="POST">
        <div class="campo">
            <label for="idempresa">ID</label>
            <i></i>
            <input type="text" name="idempresa" class="short" id="idempresa" value="${v.getIdEmpresa()}">
        </div>
        <div class="campo">
            <label for="nombre">Nombre de la empresa</label>
            <i></i>
            <input type="text" name="nombre" id="nombre" value="${v.nombre}">
        </div>
        <div class="campo">
            <label for="facebook">Facebook</label>
            <i></i>
            <input type="text" name="facebook" id="facebook" value="${v.facebook}">
        </div>
        <div class="campo">
            <label for="whatsapp">Whatsapp</label>
            <i></i>
            <input type="text" name="whatsapp" id="whatsapp" value="${v.whatsapp}">
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Empresas" class="ghost-red">Cancelar</a>
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
        new Campo('nombre', 'texto'),
        new Campo('facebook', 'texto'),
        new Campo('whatsapp', 'texto', '', 14, 8)
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