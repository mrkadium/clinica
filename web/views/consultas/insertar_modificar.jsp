<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} consulta</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/tablas.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/formulario_con_tabla.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} consulta</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Consultas" method="POST">
        <div class="campo">
            <label for="idconsulta">ID Consulta</label>
            <i></i>
            <input type="text" class="short" name="idconsulta" id="idconsulta" value="${v.idconsulta}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="idpaciente">Paciente</label>
            <i></i>
            <input type="text" class="short" name="idpaciente" id="idpaciente" value="${v.idpaciente}" readonly tabindex="-1">
            <input type="text" class="long" name="paciente" id="paciente" value="${p.nombres} ${p.apellidos}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Consultas?accion=pacientes');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="idservicio">Servicio</label>
            <i></i>
            <input type="text" class="short" name="idservicio" id="idservicio" value="${v.idservicio}" readonly tabindex="-1">
            <input type="text" class="long" name="servicio" id="servicio" value="${c.nombre}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Consultas?accion=servicios');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="iddoctor">Doctor por preferencia</label>
            <i></i>
            <input type="text" class="short" name="iddoctor" id="iddoctor" value="${v.iddoctor}" readonly tabindex="-1">
            <input type="text" class="long" name="doctor" id="doctor" value="${e.nombres} ${e.apellidos}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Consultas?accion=doctores');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="idventa">ID Venta</label>
            <i></i>
            <input type="text" class="short" name="idventa" id="idventa" value="${v.idventa}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="fecha">Fecha</label>
            <i></i>
            <input type="date" name="fecha" id="fecha" value="${v.fecha_hora.toString().substring(0,10)}">
        </div>
        <div class="campo">
            <label for="hora">Hora</label>
            <i></i>
            Hora: 
            <select class="short" name="hora">
            <c:forEach var="hora" items="${Horas}">
                <option ${v.fecha_hora.toString().substring(11,13) == hora ? 'selected' : ''} value="${hora}">${hora}</option>
            </c:forEach>
            </select>
            Minutos: 
            <select class="short" name="min">
            <c:forEach var="min" items="${Minutos}">
                <option ${v.fecha_hora.toString().substring(14,16) == min ? 'selected' : ''} value="${min}">${min}</option>
            </c:forEach>
            </select>
        </div>
        <div class="campo">
            <label for="estado_consulta">Estado</label>
            <i></i>
            <select name="estado_consulta" id="estado" >
                <option value="Pendiente">Pendiente</option>
                <option ${v.estado == 'Atendida' ? 'selected' : ''} value="Atendida">Atendida</option>
            </select>
        </div>
        
        
        <div class="campo" id="tabla">
            <label for="campo2">Doctores/Enfermeros participantes</label>
            <i></i>
            <a  onclick="abrirVentana('${pageContext.servletContext.contextPath}/Consultas?accion=agregarEmpleado');" class="agregar"><i class="icon icon-plus"></i> Agregar</a>
            <div class="tablas">
                <table id="table01" class="doctores">
                    <thead>
                        <tr>
                            <th>id</th>
                            <th>Empleado</th>
                            <th>Cargo</th>
                            <th><i class="icon icon-bin"></i></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${Empleados_consulta != null}">
                            <c:forEach var="i" items="${Empleados_consulta}">
                            <tr>
                                <td><input type='text' readonly value='${i.idempleado_consulta}' name='idempleado_consulta'></td>
                                <td style="display:none;"><input type='text' readonly value='${i.idempleado}' name='idempleado'></td>
                                <td><input type='text' readonly value='${i.empleado}' name='empleado'></td>
                                <td><input type='text' readonly value='${i.cargo}' name='cargo'></td>
                                <td></td>
                            </tr>
                            </c:forEach>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
        
        
        <div class="campo" id="tabla">
            <label for="campo2">Exámenes</label>
            <i></i>
            <a  onclick="abrirVentana('${pageContext.servletContext.contextPath}/Consultas?accion=agregarExamen');" class="agregar"><i class="icon icon-plus"></i> Agregar</a>
            <div class="tablas">
                <table id="table01" class="examenes">
                    <thead>
                        <tr>
                            <th>id</th>
                            <th>Examen</th>
                            <th>Estado</th>
                            <th>Fecha revisión</th>
                            <th>Resultados</th>
                            <th><i class="icon icon-bin"></i></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${Examenes_consulta != null}">
                            <c:forEach var="i" items="${Examenes_consulta}">
                            <tr>
                                <td><input type='text' readonly value='${i.idexamen_consulta}' name='idexamen_consulta'></td>
                                <td style="display:none;"><input type='text' readonly value='${i.idexamen}' name='idexamen'></td>
                                <td><input type='text' readonly value='${i.examen}' name='examen'></td>
                                <td><input type='text' readonly value='${i.estado}' name='estado'></td>
                                <td><input type='date' readonly value='${i.fecha_revision.toString().substring(0,10)}' name='fecha_revision'></td>
                                <td><input type='text' readonly value='${i.resultados}' name='resultados'></td>
                                <td></td>
                            </tr>
                            </c:forEach>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Consultas" class="ghost-red">Cancelar</a>
    </form>
</div>

<script>
    function abrirVentana(URL){
        window.open(URL,"ventana1","width=450,height=600,scrollbars=YES,statusbar=YES,top=150,right=150");
    }
    function setDataDoctor(idjefe, jefe) {
        document.getElementById("iddoctor").value = idjefe;
        document.getElementById("doctor").value = jefe;
    }
    function setDataPaciente(idsucursal, direccion_sucursal) {
        document.getElementById("idpaciente").value = idsucursal;
        document.getElementById("paciente").value = direccion_sucursal;
    }
    function setDataServicio(idmunicipio, municipio) {
        document.getElementById("idservicio").value = idmunicipio;
        document.getElementById("servicio").value = municipio;
    }
    const table_body = document.querySelector(".doctores tbody");
    function setDataEmpleadoConsulta(idempleado, empleado, cargo){
        //agregarlo a la tabla 
        var encontrado = false;
        let table_filas = document.querySelectorAll(".doctores tbody tr");
        for(var i=0; i<table_filas.length; i++){
            if(table_filas[i].cells[1].firstChild.value == idempleado){
                encontrado = true;
            }
        }
        if(!encontrado){
            table_body.innerHTML += 
            "<tr>"+
                "<td><input type='text' readonly value='0' name='idempleado_consulta'></td>"+
                "<td style='display:none;'><input type='text' readonly value='" + idempleado + "' name='idempleado'></td>"+
                "<td><input type='text' readonly value='" + empleado + "' name='empleado'></td>"+
                "<td><input type='text' readonly value='" + cargo + "' name='cargo'></td>"+
                "<td><a onclick='eliminarFila(this);' class='btn'><i class='icon icon-bin'></i></a></td>"+
            "</tr>";
        }
    }
    const table_body2 = document.querySelector(".examenes tbody");
    function setDataExamen(idexamen, examen, estado, fecha_revision, resultados){
        table_body2.innerHTML += 
        "<tr>"+
            "<td><input type='text' readonly value='0' name='idexamen_consulta'></td>"+
            "<td style='display:none;'><input type='text' readonly value='" + idexamen + "' name='idexamen'></td>"+
            "<td><input type='text' readonly value='" + examen + "' name='examen'></td>"+
            "<td><input type='text' readonly value='" + estado + "' name='estado'></td>"+
            "<td><input type='date' readonly value='" + fecha_revision + "' name='fecha_revision'></td>"+
            "<td><input type='text' readonly value='" + resultados + "' name='resultados'></td>"+
            "<td><a onclick='eliminarFila(this);' class='btn'><i class='icon icon-bin'></i></a></td>"+
        "</tr>";
    }

    function eliminarFila(boton){
        boton.parentElement.parentElement.remove();
    }

    // VALIDACIONES DE FORMULARIO
    const formulario = document.querySelector('#formulario');

    // (input, tipo, msg, limiteSuperior, limiteInferior, soloNumeros, soloLetras)
    let campos = [
        new Campo('departamento', 'texto', '', 30, 6),
        new Campo('zona', 'select'),
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