<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} menú</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} menú</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Menus" method="POST">
        <div class="campo">
            <label for="idmenu">ID Menú</label>
            <i></i>
            <input type="text" class="short" name="idmenu" id="idmenu" value="${v.idmenu}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="menu">Menú</label>
            <i></i>
            <input type="text" name="menu" id="menu" value="${v.menu}">
        </div>
        <div class="campo">
            <label for="idpadre">Menú padre</label>
            <i></i>
            <input type="text" class="short" name="idpadre" id="idpadre" readonly tabindex="-1" value="${padre.idpadre}">
            <input type="text" class="long" name="padre" id="padre" readonly tabindex="-1" value="${padre.padre}">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Menus?accion=padres');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="descripcion">Descripción</label>
            <i></i>
            <textarea name="descripcion" id="descripcion">${v.descripcion}</textarea>
        </div>
        <div class="campo">
            <label for="url">URL</label>
            <i></i>
            <input type="text" name="url" id="url" value="${v.url}">
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Menus" class="ghost-red">Cancelar</a>
    </form>
</div>

<script>
    function abrirVentana(URL){
        window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
    }
    function setDataPadre(idmenu, menu) {
        document.getElementById("idpadre").value = idmenu;
        document.getElementById("padre").value = menu;
    }
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