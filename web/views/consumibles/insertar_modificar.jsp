<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} consumible</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} consumible</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Consumibles" method="POST">
        <div class="campo">
            <label for="idconsumible">ID Consumible</label>
            <i></i>
            <input type="text" class="short" name="idconsumible" id="idconsumible" value="${v.idconsumible}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="tipo">Tipo</label>
            <i></i>
            <select name="tipo" id="tipo">
                <option value="0">-- SELECCIONAR --</option>
                <option value="Producto" ${v.tipo == 'Producto' ? 'selected' : ''}>Producto</option>
                <option value="Servicio" ${v.tipo == 'Servicio' ? 'selected' : ''}>Servicio</option>
            </select>
        </div>
        <div class="campo">
            <label for="nombre">Nombre</label>
            <i></i>
            <input type="text" name="nombre" id="nombre" value="${v.nombre}">
        </div>
        <div class="campo">
            <label for="alias">Alias</label>
            <i></i>
            <input type="text" name="alias" id="alias" value="${v.alias}">
        </div>
        <div class="campo">
            <label for="presentacion">Tipo</label>
            <i></i>
            <select name="presentacion" id="presentacion">
                <option value="0">-- SELECCIONAR --</option>
                <option value="N/A" ${v.presentacion == 'N/A' ? 'selected' : ''}>N/A</option>
                <option value="Líquido" ${v.presentacion == 'Líquido' ? 'selected' : ''}>Líquido</option>
                <option value="Jarabe" ${v.presentacion == 'Jarabe' ? 'selected' : ''}>Jarabe</option>
                <option value="Polvo" ${v.presentacion == 'Polvo' ? 'selected' : ''}>Polvo</option>
                <option value="Pastilla" ${v.presentacion == 'Pastilla' ? 'selected' : ''}>Pastilla</option>
                <option value="Cápsula" ${v.presentacion == 'Cápsula' ? 'selected' : ''}>Cápsula</option>
            </select>
        </div>
        <div class="campo">
            <label for="idmarca">Marca</label>
            <i></i>
            <input type="text" class="short" name="idmarca" id="idmarca" value="${v.idmarca}" readonly tabindex="-1">
            <input type="text" class="long" name="marca" id="marca" value="${m.marca}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Consumibles?accion=marcas');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="precio_compra">Precio de compra</label>
            <i></i>
            <input type="text" name="precio_compra" id="precio_compra" value="${v.precio_compra != null ? v.precio_compra : 0}">
        </div>
        <div class="campo">
            <label for="precio_sugerido">Precio sugerido</label>
            <i></i>
            <input type="text" name="precio_sugerido" id="precio_sugerido" value="${v.precio_sugerido != null ? v.precio_sugerido : 0}">
        </div>
        <div class="campo">
            <label for="precio_venta">Precio de venta</label>
            <i></i>
            <input type="text" name="precio_venta" id="precio_venta" value="${v.precio_venta != null ? v.precio_venta : 0}">
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Consumibles" class="ghost-red">Cancelar</a>
    </form>
</div>

<script>
    function abrirVentana(URL){
        window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
    }
    function setDataMarca(idmarca, marca) {
        document.getElementById("idmarca").value = idmarca;
        document.getElementById("marca").value = marca;
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