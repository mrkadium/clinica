<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>${op} empleado</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <h1 id="titulo">${op} empleado</h1>
    <form id="formulario" action="${pageContext.servletContext.contextPath}/Empleados" method="POST">
        <div class="campo">
            <label for="idempleado">ID Empleado</label>
            <i></i>
            <input type="text" class="short" name="idempleado" id="idempleado" value="${v.idempleado}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="jvpm">JVPM</label>
            <i></i>
            <input type="text" name="jvpm" id="jvpm" value="${v.jvpm}">
        </div>
        <div class="campo">
            <label for="codigo">Código</label>
            <i></i>
            <input type="text" name="codigo" id="codigo" value="${v.codigo}" readonly tabindex="-1">
        </div>
        <div class="campo">
            <label for="nombres">Nombres</label>
            <i></i>
            <input type="text" name="nombres" id="nombres" value="${v.nombres}">
        </div>
        <div class="campo">
            <label for="apellidos">Apellidos</label>
            <i></i>
            <input type="text" name="apellidos" id="apellidos" value="${v.apellidos}">
        </div>
        <div class="campo">
            <label for="genero">Género</label>
            <i></i>
            <div>
                <input type="radio" name="genero" value="Femenino" id="femenino" ${v.genero== 'Femenino' ? 'checked' : ''}><label for="femenino">Femenino</label>
            </div>
            <div>
                <input type="radio" name="genero" value="Masculino" id="masculino" ${v.genero == 'Masculino' ? 'checked' : ''}><label for="masculino">Masculino</label>
            </div>
        </div>
        <div class="campo">
            <label for="fecha_nacimiento">Fecha de nacimiento</label>
            <i></i>
            <input type="date" name="fecha_nacimiento" id="fecha_nacimiento" value="${v.fecha_nacimiento}">
        </div>
        <div class="campo">
            <label for="dui">DUI</label>
            <i></i>
            <input type="text" name="dui" id="dui" value="${v.dui}">
        </div>
        <div class="campo">
            <label for="nit">NIT</label>
            <i></i>
            <input type="text" name="nit" id="nit" value="${v.nit}">
        </div>
        <div class="campo">
            <label for="idmunicipio">Municipio</label>
            <i></i>
            <input type="text" class="short" name="idmunicipio" id="idmunicipio" value="${m.idmunicipio}" readonly tabindex="-1">
            <input type="text" class="long" name="municipio" id="municipio" value="${m.municipio}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Empleados?accion=municipios');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="direccion">Dirección</label>
            <i></i>
            <textarea type="text" name="direccion" id="direccion">${v.direccion}</textarea>
        </div>
        <div class="campo">
            <label for="idcargo">Cargo</label>
            <i></i>
            <select name="idcargo" id="idcargo">
                <option value="0">-- Seleccionar --</option>
                <c:forEach var="c" items="${Cargos}">
                    <c:if test="${c.idcargo == v.idcargo}">
                        <option value="${c.idcargo}" selected>${c.cargo}</option>
                    </c:if>
                    <c:if test="${c.idcargo != v.idcargo}">
                        <option value="${c.idcargo}">${c.cargo}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <div class="campo">
            <label for="idjefe">Jefe</label>
            <i></i>
            <input type="text" class="short" name="idjefe" id="idjefe" value="${jefe.idempleado}" readonly tabindex="-1">
            <input type="text" class="long" name="jefe" id="jefe" value="${jefe.apellidos}, ${jefe.nombres}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Empleados?accion=jefes');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="idsucursal">Sucursal</label>
            <i></i>
            <input type="text" class="short" name="idsucursal" id="idsucursal" value="${s.idsucursal}" readonly tabindex="-1">
            <input type="text" class="long" name="direccion_sucursal" id="direccion_sucursal" value="${s.direccion}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Empleados?accion=sucursales');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="fecha_contratacion">Fecha de contratación</label>
            <i></i>
            <input type="date" name="fecha_contratacion" id="fecha_contratacion" value="${v.fecha_contratacion}">
        </div>
        <div class="campo">
            <label for="fecha_salida">Fecha de salida</label>
            <i></i>
            <input type="date" name="fecha_salida" id="fecha_salida" value="${v.fecha_salida}">
        </div>
        <div class="campo">
            <label for="estado">Estado</label>
            <i></i>
            <select name="estado" id="estado">
                <c:if test="${v.estado!=null}">
                    <c:if test="${v.estado=='Activo'}">
                        <option value="Activo" selected>Activo</option>
                        <option value="Inactivo">Inactivo</option>
                    </c:if>
                    <c:if test="${v.estado!='Activo'}">
                        <option value="Activo">Activo</option>
                        <option value="Inactivo" selected>Inactivo</option>
                    </c:if>
                </c:if>
                <c:if test="${v.estado == null}">
                    <option value="Activo">Activo</option>
                    <option value="Inactivo">Inactivo</option>
                </c:if>
            </select>
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Guardar"> 
        <a href="${pageContext.servletContext.contextPath}/Empleados" class="ghost-red">Cancelar</a>
    </form>
</div>

<script>
    function abrirVentana(URL){
        window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
    }
    function setDataSucursal(idsucursal, direccion) {
        document.getElementById("idsucursal").value = idsucursal;
        document.getElementById("direccion_sucursal").value = direccion;
    }
    function setDataJefe(idjefe, jefe) {
        document.getElementById("idjefe").value = idjefe;
        document.getElementById("jefe").value = jefe;
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