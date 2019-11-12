<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} sucursal</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} sucursal</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Sucursales" method="POST">
        <div class="campo">
            <label for="idsucursal">ID</label>
            <i></i>
            <input readonly type="text" name="idsucursal" class="short" id="idsucursal" value="${v.idsucursal}">
        </div>
        <div class="campo">
            <label for="idmunicipio">Municipio</label>
            <i></i>
            <input type="text" class="short" name="idmunicipio" id="idmunicipio" value="${v.idmunicipio}" readonly tabindex="-1">
            <input type="text" class="long" name="municipio" id="municipio" value="${m.municipio}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Sucursales?accion=municipios');" class="lupa"><i class="icon icon-search"></i></a>
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
    function abrirVentana(URL){
        window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
    }
    function setDataMunicipio(idmunicipio, municipio) {
        document.getElementById("idmunicipio").value = idmunicipio;
        document.getElementById("municipio").value = municipio;
    }
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
    
    const departamentos = document.querySelectorAll("#iddepartamento option");
    const municipios = document.querySelectorAll("#idmunicipio option")
    
    departamentos.addEventListener('change', function(){
        
    });
</script>
<%@include file="../../WEB-INF/jspf/_footer.jspf"%>