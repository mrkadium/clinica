DROP DATABASE IF EXISTS cmsa;
CREATE DATABASE cmsa;
USE cmsa;

CREATE TABLE departamentos(
	iddepartamento INT(2) PRIMARY KEY AUTO_INCREMENT,
    departamento VARCHAR(20) NOT NULL,
    zona ENUM('OCCIDENTAL', 'CENTRAL', 'ORIENTAL')
);

CREATE TABLE municipios(
	idmunicipio INT(3) PRIMARY KEY AUTO_INCREMENT,
    municipio VARCHAR(200) NOT NULL,
    iddepartamento INT(2) NOT NULL,
    
    FOREIGN KEY (iddepartamento) REFERENCES departamentos(iddepartamento),
    
    INDEX (municipio)
);

CREATE TABLE pacientes(
	idpaciente INT(11) PRIMARY KEY AUTO_INCREMENT,
	expediente INT(11) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    genero ENUM('FEMENINO', 'MASCULINO'),
    telefono CHAR(9),
    email VARCHAR(60),
    idmunicipio INT(3) NOT NULL,
    iddepartamento INT(2) NOT NULL,
    
    FOREIGN KEY (idmunicipio) REFERENCES municipios(idmunicipio),
    FOREIGN KEY (iddepartamento) REFERENCES departamentos(iddepartamento),
    
    INDEX (expediente),
    INDEX (nombres),
    INDEX (apellidos),
    INDEX (genero)
);

CREATE TABLE roles(
	idrol INT(4) PRIMARY KEY AUTO_INCREMENT,
    rol VARCHAR(40) NOT NULL
);

CREATE TABLE opciones(
	idopcion INT PRIMARY KEY AUTO_INCREMENT,
    opcion VARCHAR(100) NOT NULL,
    clasificacion VARCHAR(50) NOT NULL
);

CREATE TABLE permisos(
	idpermiso INT PRIMARY KEY AUTO_INCREMENT,
    idrol INT NOT NULL,
    idopcion INT NOT NULL,
    
    FOREIGN KEY (idrol) REFERENCES roles(idrol),
    FOREIGN KEY (idopcion) REFERENCES opciones(idopcion)
);

CREATE TABLE especialidades(
	idespecialidad INT(4) PRIMARY KEY AUTO_INCREMENT,
    especialidad VARCHAR(200) NOT NULL,
    
    INDEX (especialidad)
);

CREATE TABLE cargos(
	idcargo INT(4) PRIMARY KEY AUTO_INCREMENT,
    cargo VARCHAR(100) NOT NULL,
    
    INDEX (cargo)
);

CREATE TABLE empleados(
	idempleado INT(11) PRIMARY KEY AUTO_INCREMENT,
    idcargo INT(4) NOT NULL,
    jvpm INT(11),
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    genero ENUM('FEMENINO', 'MASCULINO'),
    fecha_nacimiento DATE NOT NULL,
    dui CHAR(10) NOT NULL,
    nit CHAR(17) NOT NULL,
    idmunicipio INT(3) NOT NULL,
    iddepartamento INT(2) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    fechacontratacion DATE,
    fechasalida DATE,
    estado ENUM('ACTIVO', 'INACTIVO'),
    
    FOREIGN KEY (idcargo) REFERENCES cargos(idcargo),
    FOREIGN KEY (idmunicipio) REFERENCES municipios(idmunicipio),
    FOREIGN KEY (iddepartamento) REFERENCES departamentos(iddepartamento),
    
    INDEX (jvpm),
    INDEX (nombres),
    INDEX (apellidos),
    INDEX (genero)
);

CREATE TABLE especialidades_medico(
	idespecialidad INT NOT NULL,
    idmedico INT NOT NULL,
    
    FOREIGN KEY (idespecialidad) REFERENCES especialidades(idespecialidad),
    FOREIGN KEY (idmedico) REFERENCES empleados(idempleado),
    
    PRIMARY KEY (idespecialidad, idmedico)
);

CREATE TABLE contactos(
	idcontacto INT PRIMARY KEY AUTO_INCREMENT,
    tipo ENUM('EMAIL', 'TELEFONO') NOT NULL,
    contacto VARCHAR(200) NOT NULL,
    idempleado INT(11) NOT NULL,
    
    FOREIGN KEY (idempleado) REFERENCES empleados(idempleado)
);

CREATE TABLE usuarios(
	idusuario INT(11) PRIMARY KEY AUTO_INCREMENT,
    usuario VARCHAR(20) BINARY NOT NULL UNIQUE,
    credencial VARCHAR(64) NOT NULL,
    idrol INT(4) NOT NULL,
    idempleado INT(11) NOT NULL,
    estado ENUM('ACTIVO', 'BLOQUEADO'),
    
    FOREIGN KEY (idrol) REFERENCES roles(idrol),
    FOREIGN KEY (idempleado) REFERENCES empleados(idempleado),
    
    INDEX (usuario)
);

CREATE TABLE laboratorios(
	idlaboratorio INT(6) PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(200) NOT NULL,
    iddepartamento INT(2) NOT NULL,
    idmunicipio INT(3) NOT NULL,
    direccion VARCHAR(200),
    descripcion LONGTEXT,
    
    FOREIGN KEY (idmunicipio) REFERENCES municipios(idmunicipio),
    FOREIGN KEY(iddepartamento) REFERENCES departamentos(iddepartamento),
    
    INDEX (nombre)
);

CREATE TABLE marcas(
	idmarca INT(11) PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(200) NOT NULL,
    
    INDEX (marca)
);

CREATE TABLE consumibles(
	idconsumible INT(11) PRIMARY KEY AUTO_INCREMENT,
    tipo ENUM('PRODUCTO', 'SERVICIO') NOT NULL,
    nombre VARCHAR(200) NOT NULL,
    presentacion ENUM('N/A', 'LÍQUIDO', 'JARABE', 'POLVO', 'PASTILLA'),
    alias VARCHAR(100) NOT NULL,
    idmarca INT(11) NOT NULL,
    preciocompra DECIMAL(10,2) NOT NULL,
    precioventa DECIMAL(10,2) NOT NULL,
    
    FOREIGN KEY (idmarca) REFERENCES marcas(idmarca),
    
    INDEX (tipo),
    INDEX (nombre),
    INDEX (alias)
);

CREATE TABLE compras(
	idcompra INT(11) PRIMARY KEY AUTO_INCREMENT,
    fecha DATETIME NOT NULL,
    idlaboratorio INT(11) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    
    FOREIGN KEY (idlaboratorio) REFERENCES laboratorios(idlaboratorio)
);

CREATE TABLE detalles_compra(
	iddetalle_compra INT(11) PRIMARY KEY AUTO_INCREMENT,
    idcompra INT(11) NOT NULL,
    idconsumibe INT(11) NOT NULL,
    fechacaducidad DATE,
    cantidad INT(11) NOT NULL,
    preciocompra DECIMAL(10,2) NOT NULL,
    precioventa DECIMAL(10,2) NOT NULL,
    gravado DECIMAL(10,2),
    
    FOREIGN KEY (idcompra) REFERENCES compras(idcompra),
    FOREIGN KEY (idconsumible) REFERENCES consumibles(idconsumible)
);

CREATE TABLE inventarios(
	idinventario INT(11) PRIMARY KEY AUTO_INCREMENT,
    fecha DATETIME NOT NULL,
    idlaboratorio INT(11) NOT NULL,
    
    FOREIGN KEY (idlaboratorio) REFERENCES laboratorios(idlaboratorio)
);

CREATE TABLE detalles_inventario(
	iddetalle_inventario INT(11) PRIMARY KEY AUTO_INCREMENT,
    idconsumibe INT(11) NOT NULL,
    fechacaducidad DATE,
    
    FOREIGN KEY (idconsumible) REFERENCES consumibles(idconsumible)
);

CREATE TABLE ventas(
	idventa INT(11) PRIMARY KEY AUTO_INCREMENT,
    fecha DATETIME NOT NULL
);

CREATE TABLE detalles_venta(
	iddetalle_venta INT(11) PRIMARY KEY AUTO_INCREMENT,
    idconsumibe INT(11) NOT NULL,
    
    FOREIGN KEY (idconsumible) REFERENCES consumibles(idconsumible)
);

-- SI ES CONSULTA
CREATE TABLE detalles_consulta(
	iddetalle_consulta INT(11) PRIMARY KEY AUTO_INCREMENT,
    idventa INT(11) NOT NULL,
    
    FOREIGN KEY (idventa) REFERENCES ventas(idventa)
);

CREATE TABLE examenes(
	idexamen INT(11) PRIMARY KEY AUTO_INCREMENT
);

/*
CREATE TABLE operaciones(
	idoperacion INT(11) PRIMARY KEY AUTO_INCREMENT,
    idtitular INT(11) NOT NULL,
    titular VARCHAR(200) NOT NULL,
    categoria ENUM('INVENTARIO', 'VENTA', 'COMPRA'),
    fecha DATETIME NOT NULL,
    idlaboratorio INT NOT NULL DEFAULT 1,
    
    FOREIGN KEY (idlaboratorio) REFERENCES laboratorios(idlaboratorio),
    
    INDEX (categoria),
    INDEX (fecha)
);

CREATE TABLE detalles_operacion(
	iddetalle INT(11) PRIMARY KEY AUTO_INCREMENT,
    idoperacion INT(11) NOT NULL,
    idconsumible INT(11) NOT NULL,
    cantidad INT(11) NOT NULL,
    preciocompra DECIMAL(10,2),
    precioventa DECIMAL(10,2) NOT NULL,
    gravado DECIMAL(10,2) NOT NULL,
    fecha_caducidad DATE,
    
    FOREIGN KEY (idoperacion) REFERENCES operaciones(idoperacion),
    FOREIGN KEY (idconsumible) REFERENCES consumibles(idconsumible)
);

CREATE TABLE detalle_consulta(
	idconsulta INT(11) PRIMARY KEY,
    idmedico INT(11) NOT NULL,
    razonconsulta LONGTEXT,
    temperatura VARCHAR(10) DEFAULT '36.5',
    frecuenciacardiaca VARCHAR(20),
    frecuenciarespiratoria VARCHAR(20),
    presionarterial VARCHAR(20),
    saturacionoxigeno VARCHAR(20),
    diagnostico LONGTEXT,
    tratamiento LONGTEXT,
    observaciones LONGTEXT,
    
    FOREIGN KEY (idconsulta) REFERENCES operaciones(idoperacion),
    FOREIGN KEY (idmedico) REFERENCES empleados(idempleado),
    
    FULLTEXT (diagnostico)
);

CREATE TABLE resultados_examenes(
	idresultado INT(11) PRIMARY KEY AUTO_INCREMENT,
    idconsulta INT(11),
    examen VARCHAR(200),
    estado ENUM('PENDIENTE','ANALIZADO'),
    resultados LONGTEXT,
    fecha DATE,
    
    FOREIGN KEY (idconsulta) REFERENCES operaciones(idoperacion)
);



-- TRIGGERS
DELIMITER //
CREATE TRIGGER bi_operaciones
BEFORE INSERT ON operaciones
FOR EACH ROW
BEGIN
	IF(NEW.categoria IN ('COMPRA')) THEN
		SET NEW.titular = (SELECT nombre FROM laboratorios WHERE idlaboratorio = NEW.idtitular);
    END IF;
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER bi_detalles_operacion
BEFORE INSERT ON detalles_operacion
FOR EACH ROW
BEGIN
	DECLARE pre_un DECIMAL(10,2);
    SET pre_un = (SELECT precioventa FROM consumibles WHERE idconsumible = NEW.idconsumible);
    
    IF(NEW.precioventa IS NULL) THEN -- CUANDO ES UNA VENTA, YA QUE NO SE PUEDE CAMBIAR EL PRECIO
		SET NEW.precioventa = pre_un;
		SET NEW.gravado = NEW.cantidad*NEW.precioventa;
	ELSE -- ES UNA COMPRA Y PUEDE MODIFICARSE EL PRECIO
		SET NEW.gravado = NEW.cantidad*NEW.precioventa;
		UPDATE consumibles SET precioventa = NEW.precioventa WHERE idconsumible = NEW.idconsumible;
    END IF;
END;
//
DELIMITER ;
*/