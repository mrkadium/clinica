<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} ventas</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/tablas.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/formulario_con_tabla.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} venta</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Ventas" method="POST">
        <div class="campo">
            <label for="idventa">ID Venta</label>
            <i></i>
            <input type="text" class="short" name="idventa" id="idventa" value="${v.idventa}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="fecha">Fecha</label>
            <i></i>
            <b>Dejar vacío para fecha y hora actual</b>
            <input type="date" ${v != null ? ' readonly' : ''} name="fecha" id="fecha" value="${v.fecha.toString().substring(0,10)}">
        </div>
        <div class="campo">
            <label for="idpaciente">Paciente</label>
            <i></i>
            <input type="text" class="short" name="idpaciente" id="idpaciente" value="${v.idpaciente}" readonly tabindex="-1">
            <input type="text" class="long" name="nombre_paciente" id="nombre_paciente" value="${p.nombres} ${p.apellidos}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Ventas?accion=pacientes');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="direccion">Direccion</label>
            <i></i>
            <input type="text" readonly name="direccion" id="direccion" value="${m.municipio}, ${d.departamento}">
        </div>
        
        <c:if test="${v != null}">
            <!--MOSTRAR LA PERSONA QUE ESTÁ REGISTRÓ LA VENTA, SINO SÓLO INSERTARLA OBTENIENDO EL USUARIO-->
        </c:if>
        
        
        <div class="campo" id="tabla">
            <label for="campo2">Consumibles</label>
            <i></i>
            <a  onclick="abrirVentana('${pageContext.servletContext.contextPath}/Ventas?accion=agregar');" class="agregar"><i class="icon icon-plus"></i> Agregar</a>
            <div class="tablas">
                <table id="table01">
                    <thead>
                        <tr>
                            <th>id</th>
                            <th>Consumible</th>
                            <th>Precio</th>
                            <th>Cantidad</th>
                            <th><i class="icon icon-bin"></i></th>
                        </tr>
                    </thead>
                    <tbody id="consumibles">
                        <c:forEach var="i" items="${Detalles_venta}" varStatus="idx">
                            <tr>
                                <td><input type='text' readonly value='${i.idconsumible}' name='idconsumible'></td>
                                <td><input type='text' readonly value='${Consumibles.get(idx.index).getNombre()}' name='nombre_consumible'></td>
                                <td><input type='text' readonly value='${i.precio}' name='precio'></td>
                                <td><input type='text' readonly value='${i.cantidad}' name='cantidad'></td>
                                <td><a id='${i.precio*i.cantidad}' onclick='eliminarFila(this);' class='btn'><i class='icon icon-bin'></i></a></td>
                            </tr>
                            </c:forEach>
                    </tbody>
                </table>
            </div>
            <label for="">Total: $</label>
            <input type="text" readonly name="total" value="${v.total != null ? v.total : 0}" class="short" id="total">
            <label for="">Descuento: $</label>
            <input type="text" readonly name="descuentos" value="${v.descuentos != null ? v.descuentos : 0}" class="short" id="descuentos">
        </div>
        <br><br>
        <div class="campo">
            <label for="estado">Estado de pago</label>
            <i></i>
            <input type="text" readonly name="estado" id="estado" value="${v.estado != null ? v.estado : 'Pendiente'}">
        </div>
        <div class="campo" id="tabla">
            <label for="campo2">Abonos</label>
            <i></i>
            <div class="tablas">
                <table id="table01">
                    <thead>
                        <tr>
                            <th>id</th>
                            <th>Monto</th>
                            <th>Fecha</th>
                            <th><i class="icon icon-bin"></i></th>
                        </tr>
                    </thead>
                    <tbody id="abonos">
                        <c:if test="${Abonos != null}">
                            <c:forEach var="i" items="${Abonos}">
                            <tr>
                                <td><input type='text' readonly value='${i.idabono}' name='idabono'></td>
                                <td><input type='text' readonly value='${i.monto}' name='montito'></td>
                                <td><input type='text' readonly value='${i.fecha.toString().substring(0,10)}' name='fechita'></td>
                            </tr>
                            </c:forEach>
                        </c:if>
                    </tbody>
                </table>
            </div>
            <label>Monto: </label>
            <input type="text" name="monto" class="short" id="monto">
            <a  onclick="setDataMonto();" class="agregar"><i class="icon icon-plus"></i> Agregar</a>
            <br>
            <label for="">Total abonado: $</label>
            <input type="text" readonly name="total_abono" value="${v.deuda != null ? v.total - v.deuda : 0}" class="short" id="total_abono">
            <label for="">Deuda: $</label>
            <input type="text" readonly name="deuda" value="${v.deuda != null ? v.deuda : 0}" class="short" id="deuda">
        </div>
        
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Ventas" class="ghost-red">Cancelar</a>
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
        window.open(URL,"ventana1","width=500,height=600,scrollbars=YES,statusbar=YES,top=150,right=100");
    }

    const table_body = document.querySelector("#consumibles");
    const abonos = document.querySelector("#abonos");
    const total = document.querySelector("#total");
    const descuento = document.querySelector("#descuento");
    const monto = document.querySelector("#monto");
    const deuda = document.querySelector("#deuda");
    const total_abono = document.querySelector("#total_abono");
    const estado = document.querySelector("#estado");

    function setDataMonto(){
        var d = parseFloat(deuda.value);
        var m = parseFloat(monto.value);
        var t = parseFloat(total.value);
        var ta = parseFloat(total_abono.value);
        
        if(estado.value == 'Pendiente' && ((m <= t && m+ta <= t) || m <= d)){
            abonos.innerHTML += 
                "<tr>" +
                    "<td><input readyonly type='text' value='0' name='idmonto'></td>" +
                    "<td><input readyonly type='text' value='"+monto.value+"' name='montito'></td>" +
                    "<td><input readyonly type='date' name='fechita'></td>" +
                    "<td><a id='" + monto.value + "' onclick='eliminarFilaMonto(this);' class='btn'><i class='icon icon-bin'></i></a></td>" +
                "</tr>";
            total_abono.value = parseFloat(total_abono.value) + parseFloat(monto.value);
            monto.value = "";
        }else{
            monto.value = "";
            monto.focus();
        }
        
    }
    function setDataLaboratorio(idlaboratorio, nombre_laboratorio) {
        document.getElementById("idlaboratorio").value = idlaboratorio;
        document.getElementById("nombre_laboratorio").value = nombre_laboratorio;
    }
    function setDataPaciente(idpaciente, nombre_paciente, direccion){
        document.getElementById("idpaciente").value = idpaciente;
        document.getElementById("nombre_paciente").value = nombre_paciente;
        document.getElementById("direccion").value = direccion;
    }
    function setDataConsumible(idconsumible, nombre, precio, cantidad){
        //agregarlo a la tabla   
        table_body.innerHTML += 
        "<tr>"+
            "<td><input type='text' readonly value='" + idconsumible + "' name='idconsumible'></td>"+
            "<td><input type='text' class='min' readonly value='" + nombre + "' name='nombre_consumible'></td>"+
            "<td><input type='text' readonly value='" + precio + "' name='precio'></td>"+
            "<td><input type='text' readonly value='" + cantidad + "' name='cantidad'></td>"+
            "<td><a id='" + (parseFloat(precio) * parseInt(cantidad)) + "' onclick='eliminarFila(this);' class='btn'><i class='icon icon-bin'></i></a></td>"+
        "</tr>";
        alert(parseFloat(total.value));
        alert(parseFloat(precio));
        alert(parseInt(cantidad));
        alert(parseFloat(total.value) + (parseFloat(precio) * parseInt(cantidad)));
        total.value = parseFloat(total.value) + (parseFloat(precio) * parseInt(cantidad));
    }

    function eliminarFila(boton){
        var subtotal = boton.getAttribute("id");
        total.value = parseFloat(total.value) - parseFloat(subtotal);
        boton.parentElement.parentElement.remove();
    }
    function eliminarFilaMonto(boton){
        var montito = boton.getAttribute("id");
        total_abono.value = parseFloat(total_abono.value) - parseFloat(montito);
        boton.parentElement.parentElement.remove();
        monto.value = "";
    }
</script>
<%@include file="../../WEB-INF/jspf/_footer.jspf"%>