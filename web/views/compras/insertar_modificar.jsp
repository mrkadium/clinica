<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} compras</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/tablas.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/formulario_con_tabla.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} compras</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Compras" method="POST">
        <div class="campo">
            <label for="idcompra">ID Compra</label>
            <i></i>
            <input type="text" class="short" name="idcompra" id="idcompra" value="${v.idcompra}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="fecha">Fecha</label>
            <i></i>
            <b>Dejar vacío para fecha y hora actual</b>
            <input type="date" name="fecha" id="fecha" value="${v.fecha.toString().substring(0,10)}">
        </div>
        <div class="campo">
            <label for="idlaboratorio">Laboratorio</label>
            <i></i>
            <input type="text" class="short" name="idlaboratorio" id="idlaboratorio" value="${v.idlaboratorio}" readonly tabindex="-1">
            <input type="text" class="long" name="nombre_laboratorio" id="nombre_laboratorio" value="${l.nombre}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Compras?accion=laboratorios');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        
        
        <div class="campo" id="tabla">
            <label for="campo2">Consumibles</label>
            <i></i>
            <a  onclick="abrirVentana('${pageContext.servletContext.contextPath}/Compras?accion=agregar');" class="agregar"><i class="icon icon-plus"></i> Agregar</a>
            <div class="tablas">
                <table id="table01">
                    <thead>
                        <tr>
                            <th>id</th>
                            <th>Consumible</th>
                            <th>P. compra</th>
                            <th>P. venta</th>
                            <th>Cantidad</th>
                            <th>Fecha cad.</th>
                            <th><i class="icon icon-bin"></i></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${Detalles_compra != null}">
                            <c:forEach var="i" items="${Detalles_compra}" varStatus="idx">
                            <tr>
                                <td><input type='text' readonly value='${i.idconsumible}' name='idconsumible'></td>
                                <td><input type='text' readonly value='${Consumibles.get(idx.index).getNombre()}' name='idconsumible'></td>
                                <td><input type='date' readonly value='${i.fecha_caducidad.toString().substring(0,10)}' name='fecha_caducidad'></td>
                                <td><input type='text' readonly value='${i.cantidad}' name='cantidad'></td>
                                <td><input type='text' readonly value='${i.precio_compra}' name='precio_compra'></td>
                                <td><input type='text' readonly value='${i.precio_sugerido}' name='precio_sugerido'></td>
                                <td><a id='${i.precio_compra*i.cantidad}' onclick='eliminarFila(this);' class='btn'><i class='icon icon-bin'></i></a></td>
                            </tr>
                            </c:forEach>
                        </c:if>
                    </tbody>
                </table>
            </div>
            <label for="">Total: $</label>
            <input type="text" name="total" value="${v.total != null ? v.total : 0}" class="short" id="total">
            <label for="">Descuento: $</label>
            <input type="text" name="descuentos" value="${v.descuentos != null ? v.descuentos : 0}" class="short" id="descuentos">
        </div>
        
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Compras" class="ghost-red">Cancelar</a>
    </form>
</div>

<script>
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
    function abrirVentana(URL){
        window.open(URL,"ventana1","width=500,height=600,scrollbars=YES,statusbar=YES,top=150,left=300");
    }

    const table_body = document.querySelector("#table01 tbody");
    const total = document.querySelector("#total");
    const descuento = document.querySelector("#descuento");

    function setDataLaboratorio(idlaboratorio, nombre_laboratorio) {
        document.getElementById("idlaboratorio").value = idlaboratorio;
        document.getElementById("nombre_laboratorio").value = nombre_laboratorio;
    }
    function setDataConsumible(idconsumible, nombre, precio_compra, precio_sugerido, cantidad, fecha_caducidad){
        //agregarlo a la tabla   
        table_body.innerHTML += 
        "<tr>"+
            "<td><input type='text' readonly value='" + idconsumible + "' name='idconsumible'></td>"+
            "<td><input type='text' class='min' readonly value='" + nombre + "' name='nombre'></td>"+
            "<td><input type='text' readonly value='" + precio_compra + "' name='precio_compra'></td>"+
            "<td><input type='text' readonly value='" + precio_sugerido + "' name='precio_sugerido'></td>"+
            "<td><input type='text' readonly value='" + cantidad + "' name='cantidad'></td>"+
            "<td><input type='date' readonly value='" + fecha_caducidad + "' name='fecha_caducidad'></td>"+
            "<td><a id='" + (parseFloat(precio_compra) * parseInt(cantidad)) + "' onclick='eliminarFila(this);' class='btn'><i class='icon icon-bin'></i></a></td>"+
        "</tr>";
        total.value = parseFloat(total.value) + (parseFloat(precio_compra) * parseInt(cantidad));
    }

    function eliminarFila(boton){
        var subtotal = boton.getAttribute("id");
        total.value = parseFloat(total.value) - parseFloat(subtotal);
        boton.parentElement.parentElement.remove();
    }
</script>
<%@include file="../../WEB-INF/jspf/_footer.jspf"%>