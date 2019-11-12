<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} laboratorio</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} laboratorio</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Laboratorios" method="POST">
        <div class="campo">
            <label for="idlaboratorio">ID Laboratorio</label>
            <i></i>
            <input type="text" class="short" name="idlaboratorio" id="idlaboratorio" value="${v.idlaboratorio}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="nombre">Nombre</label>
            <i></i>
            <input type="text" name="nombre" id="nombre" value="${v.nombre}">
        </div>
        <div class="campo">
            <label for="idmunicipio">Municipio</label>
            <i></i>
            <input type="text" class="short" name="idmunicipio" id="idmunicipio" value="${v.idmunicipio}" readonly tabindex="-1">
            <input type="text" class="long" name="municipio" id="municipio" value="${m.municipio}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Laboratorios?accion=municipios');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="direccion">Direccion</label>
            <i></i>
            <textarea type="text" name="direccion" id="direccion">${v.direccion}</textarea>
        </div>
        <div class="campo">
            <label for="descripcion">Descripción</label>
            <i></i>
            <textarea type="text" name="descripcion" id="descripcion">${v.descripcion}</textarea>
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Laboratorios" class="ghost-red">Cancelar</a>
    </form>
</div>

<script>
    function abrirVentana(URL){
        window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
    }
    function setDataMunicipio(idmunicipio, municipio) {
        document.getElementById("idmunicipio").value = idmunicipio;
        document.getElementById("municipio").value = municipio;
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