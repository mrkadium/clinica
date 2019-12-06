<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} consulta</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} consulta</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/DetalleConsulta" method="POST">
        <div class="campo">
            <label for="iddetalle_consulta">ID Detalle de Consulta</label>
            <i></i>
            <input type="text" class="short" name="iddetalle_consulta" id="iddetalle_consulta" value="${v.iddetalle_consulta}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="idconsulta">ID Consulta</label>
            <i></i>
            <input type="text" class="short" name="idconsulta" id="idconsulta" value="${v.idconsulta != null ? v.idconsulta : idconsulta}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="razon_consulta">Razón de la consulta</label>
            <i></i>
            <textarea name="razon_consulta" id="razon_consulta">${v.razon_consulta}</textarea>
        </div>
        <div class="campo">
            <label for="temperatura">Temperatura</label>
            <i></i>
            <input type="text" name="temperatura" id="temperatura" value="${v.temperatura}">
        </div>
        <div class="campo">
            <label for="frecuencia_cardiaca">Frecuencia cardíaca</label>
            <i></i>
            <input type="text" name="frecuencia_cardiaca" id="frecuencia_cardiaca" value="${v.frecuencia_cardiaca}">
        </div>
        <div class="campo">
            <label for="frecuencia_respiratoria">Frecuencia respiratoria</label>
            <i></i>
            <input type="text" name="frecuencia_respiratoria" id="frecuencia_respiratoria" value="${v.frecuencia_respiratoria}">
        </div>
        <div class="campo">
            <label for="presion_arterial">Presion arterial</label>
            <i></i>
            <input type="text" name="presion_arterial" id="presion_arterial" value="${v.getPresion_arterial()}">
        </div>
        <div class="campo">
            <label for="saturacion_oxigeno">Saturacion de oxígeno</label>
            <i></i>
            <input type="text" name="saturacion_oxigeno" id="saturacion_oxigeno" value="${v.saturacion_oxigeno}">
        </div>
        <div class="campo">
            <label for="diagnostico">Diagnóstico</label>
            <i></i>
            <textarea name="diagnostico" id="diagnostico">${v.diagnostico}</textarea>
        </div>
        <div class="campo">
            <label for="tratamiento">Tratamiento</label>
            <i></i>
            <textarea name="tratamiento" id="tratamiento">${v.tratamiento}</textarea>
        </div>
        <div class="campo">
            <label for="observaciones">Observaciones</label>
            <i></i>
            <textarea name="observaciones" id="observaciones">${v.observaciones}</textarea>
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Consultas" class="ghost-red">Cancelar</a>
    </form>
</div>

<script>
    function abrirVentana(URL){
        window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
    }
    function setDataJefe(idjefe, jefe) {
        document.getElementById("idjefe").value = idjefe;
        document.getElementById("jefe").value = jefe;
    }
    function setDataSucursal(idsucursal, direccion_sucursal) {
        document.getElementById("idsucursal").value = idsucursal;
        document.getElementById("direccion_sucursal").value = direccion_sucursal;
    }
    function setDataMunicipio(idmunicipio, municipio) {
        document.getElementById("idmunicipio").value = idmunicipio;
        document.getElementById("municipio").value = municipio;
    }
    const openWindow = function(url){
        location.href = url;
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