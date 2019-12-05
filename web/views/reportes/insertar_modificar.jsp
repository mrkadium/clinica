<%@include file="../../WEB-INF/jspf/_header.jspf"%>
    <title>Reportes</title>
    <link href="resources/css/formulario.css" rel="stylesheet" type="text/css"/>
    <style>
        .hide{
            display:none;
        }
        .boton{
            color: rgb(0,164,167);
            margin: 8px;
            cursor: pointer;
        }
        .boton:hover{
            font-weight: bold;
        }
    </style>
<%@include file="../../WEB-INF/jspf/_navbar.jspf"%>

<div class="formulario">
    <p id="buena_salud" class="boton"><i class="icon icon-file-text2"></i> Constancia de buena salud</p>
    <p id="incapacidad" class="boton"><i class="icon icon-file-text2"></i> Constancia de incapacidad</p>
    <p id="medica" class="boton"><i class="icon icon-file-text2"></i> Constancia médica</p>
    <form id="formulario" class="buena_salud ${op == 'buena_salud' ? '' : 'hide'}" action="${pageContext.servletContext.contextPath}/ConstanciaBuenaSalud" method="GET">
    <h1 id="titulo">Constancia de buena salud</h1>
        <div class="campo">
            <label for="idpaciente">Paciente</label>
            <i></i>
            <input type="text" required class="short" name="idpaciente" id="idpaciente" value="${p.idpaciente}" readonly tabindex="-1">
            <input type="text" class="long" name="paciente" id="paciente" value="${p.nombres} ${p.apellidos}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Consultas?accion=pacientes');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="iddoctor">Doctor</label>
            <i></i>
            <input type="text" required class="short" name="iddoctor" id="iddoctor" value="${d.iddoctor}" readonly tabindex="-1">
            <input type="text" class="long" name="doctor" id="doctor" value="${d.nombres} ${d.apellidos}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Consultas?accion=doctores');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Generar"> 
        <a href="${pageContext.servletContext.contextPath}/Reportes" class="ghost-red">Cancelar</a>
    </form>
    
    
    <form id="formulario" class="incapacidad ${op == 'incapacidad' ? '' : 'hide'}" action="${pageContext.servletContext.contextPath}/ConstanciaIncapacidad" method="GET">
    <h1 id="titulo">Constancia de incapacidad</h1>
        <div class="campo">
            <label for="idpaciente">Paciente</label>
            <i></i>
            <input type="text" required class="short" name="idpaciente" id="idpaciente" value="${p.idpaciente}" readonly tabindex="-1">
            <input type="text" class="long" name="paciente" id="paciente" value="${p.nombres} ${p.apellidos}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Consultas?accion=pacientes');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="iddoctor">Doctor</label>
            <i></i>
            <input type="text" required class="short" name="iddoctor" id="iddoctor" value="${d.iddoctor}" readonly tabindex="-1">
            <input type="text" class="long" name="doctor" id="doctor" value="${d.nombres} ${d.apellidos}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Consultas?accion=doctores');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="diagnostico">Diagnóstio</label>
            <i></i>
            <input type="text" name="diagnostico" id="diagnostico" value="${v.diagnostico}">
        </div>
        <div class="campo">
            <label for="dias">Días</label>
            <i></i>
            <input type="number" class="short" name="dias" min="1" id="dias">
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Generar"> 
        <a href="${pageContext.servletContext.contextPath}/Reportes" class="ghost-red">Cancelar</a>
    </form>
    
    
    <form id="formulario" class="medica ${op == 'medica' ? '' : 'hide'}" action="${pageContext.servletContext.contextPath}/ConstanciaMedica" method="GET">
    <h1 id="titulo">Constancia médica</h1>
        <div class="campo">
            <label for="idpaciente">Paciente</label>
            <i></i>
            <input type="text" required class="short" name="idpaciente" id="idpaciente" value="${p.idpaciente}" readonly tabindex="-1">
            <input type="text" class="long" name="paciente" id="paciente" value="${p.nombres} ${p.apellidos}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Consultas?accion=pacientes');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="iddoctor">Doctor</label>
            <i></i>
            <input type="text" required class="short" name="iddoctor" id="iddoctor" value="${d.iddoctor}" readonly tabindex="-1">
            <input type="text" class="long" name="doctor" id="doctor" value="${d.nombres} ${d.apellidos}" readonly tabindex="-1">
            <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Consultas?accion=doctores');" class="lupa"><i class="icon icon-search"></i></a>
        </div>
        <div class="campo">
            <label for="diagnostico">Diagnóstio</label>
            <i></i>
            <input type="text" name="diagnostico" id="diagnostico" value="${v.diagnostico}">
        </div>
        <input type="submit" name="" id="" class="ghost-blue" value="Generar"> 
        <a href="${pageContext.servletContext.contextPath}/Home" class="ghost-red">Cancelar</a>
    </form>
</div>

<script>
    function abrirVentana(URL){
        window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
    }
    function setDataDoctor(iddoctor, doctor) {
        var iddoctores = document.querySelectorAll("#iddoctor");
        var doctores = document.querySelectorAll("#doctor");
        for(i=0; i<iddoctores.length; i++){
            iddoctores[i].value = iddoctor;
            doctores[i].value = doctor;
        }
    }
    function setDataPaciente(idempleado, empleado) {
        var iddoctores = document.querySelectorAll("#idpaciente");
        var doctores = document.querySelectorAll("#paciente");
        for(i=0; i<iddoctores.length; i++){
            iddoctores[i].value = idempleado;
            doctores[i].value = empleado;
        }
    }
    
    
    
    
    const formularios = document.querySelectorAll("#formulario");
    const buena_salud = document.querySelector("#buena_salud");
    const incapacidad = document.querySelector("#incapacidad");
    const medica = document.querySelector("#medica");
    buena_salud.addEventListener('click', function(){
        for(i=0; i<formularios.length; i++){
            if(formularios[i].classList[0] == "buena_salud"){
                formularios[i].classList.toggle('hide');
            }else{
                formularios[i].classList.add('hide');
            }
        }
    });
    incapacidad.addEventListener('click', function(){
        for(i=0; i<formularios.length; i++){
            if(formularios[i].classList[0] == "incapacidad"){
                formularios[i].classList.toggle('hide');
            }else{
                formularios[i].classList.add('hide');
            }
        }
    });
    medica.addEventListener('click', function(){
        for(i=0; i<formularios.length; i++){
            if(formularios[i].classList[0] == "medica"){
                formularios[i].classList.toggle('hide');
            }else{
                formularios[i].classList.add('hide');
            }
        }
    });
    
    
    

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
</script>
<%@include file="../../WEB-INF/jspf/_footer.jspf"%>