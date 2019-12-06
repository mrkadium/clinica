DROP DATABASE IF EXISTS cmsa;
CREATE DATABASE cmsa;
USE cmsa;

CREATE TABLE empresa(
	idempresa INT PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(200),
    facebook VARCHAR(100),
    whatsapp VARCHAR(20)
);

CREATE TABLE noticias(
	idnoticia INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(200) NOT NULL,
    resumen VARCHAR(250) NOT NULL,
    contenido LONGTEXT NOT NULL
);

CREATE TABLE departamentos(
	iddepartamento INT(2) PRIMARY KEY AUTO_INCREMENT,
    departamento VARCHAR(20) NOT NULL,
    zona ENUM('Occidental', 'Central', 'Oriental')
);

CREATE TABLE municipios(
	idmunicipio INT(3) PRIMARY KEY AUTO_INCREMENT,
    municipio VARCHAR(200) NOT NULL,
    iddepartamento INT(2) NOT NULL,
    
    FOREIGN KEY (iddepartamento) REFERENCES departamentos(iddepartamento),
    
    INDEX (municipio)
);

CREATE TABLE sucursales(
	idsucursal INT PRIMARY KEY AUTO_INCREMENT,
    codigo CHAR(6), -- C19-01
    idempresa INT DEFAULT 1,
    idmunicipio INT NOT NULL,
    direccion LONGTEXT,
    telefono1 VARCHAR(20),
    telefono2 VARCHAR(20),
    email VARCHAR(100),
    
    FOREIGN KEY (idempresa) REFERENCES empresa(idempresa),
    FOREIGN KEY (idmunicipio) REFERENCES municipios(idmunicipio)
);

CREATE TABLE cargos(
	idcargo INT(4) PRIMARY KEY AUTO_INCREMENT,
    cargo VARCHAR(100) NOT NULL,
    descripcion LONGTEXT,
    
    INDEX (cargo)
);

CREATE TABLE empleados(
	idempleado INT(11) PRIMARY KEY AUTO_INCREMENT,
    idjefe INT,
    idsucursal INT,
    idcargo INT(4) NOT NULL,
    codigo CHAR(15), -- RO97-C19-01-001 (SI SÓLO TIENE UN APELLIDO, SE REPETIRÁ LA INICIAL)
    jvpm INT(11),
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    genero ENUM('Femenino', 'Masculino'),
    fecha_nacimiento DATE NOT NULL,
    dui CHAR(10) NOT NULL,
    nit CHAR(17) NOT NULL,
    idmunicipio INT(3) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    fecha_contratacion DATE,
    fecha_salida DATE,
    estado ENUM('Activo', 'Inactivo'),
    
    FOREIGN KEY (idjefe) REFERENCES empleados(idempleado),
    FOREIGN KEY (idsucursal) REFERENCES sucursales(idsucursal),
    FOREIGN KEY (idcargo) REFERENCES cargos(idcargo),
    FOREIGN KEY (idmunicipio) REFERENCES municipios(idmunicipio),
    
    INDEX (codigo),
    INDEX (jvpm),
    INDEX (nombres),
    INDEX (apellidos)
);

CREATE TABLE contactos(
	idcontacto INT PRIMARY KEY AUTO_INCREMENT,
    tipo ENUM('Email', 'Telefono') NOT NULL,
    contacto VARCHAR(200) NOT NULL,
    idempleado INT(11) NOT NULL,
    
    FOREIGN KEY (idempleado) REFERENCES empleados(idempleado)
);

CREATE TABLE especialidades(
	idespecialidad INT(4) PRIMARY KEY AUTO_INCREMENT,
    especialidad VARCHAR(200) NOT NULL,
    
    INDEX (especialidad)
);

CREATE TABLE especialidades_medico(
	idespecialidad_medico INT PRIMARY KEY AUTO_INCREMENT,
	idespecialidad INT NOT NULL,
    idmedico INT NOT NULL,
    
    FOREIGN KEY (idespecialidad) REFERENCES especialidades(idespecialidad),
    FOREIGN KEY (idmedico) REFERENCES empleados(idempleado)
);

CREATE TABLE pacientes(
	idpaciente INT PRIMARY KEY AUTO_INCREMENT,
	expediente INT NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    genero ENUM('Femenino', 'Masculino'),
    telefono CHAR(9),
    email VARCHAR(60),
    idmunicipio INT NOT NULL,
    idsucursal INT NOT NULL,
    idempleado INT NOT NULL,
    
    FOREIGN KEY (idmunicipio) REFERENCES municipios(idmunicipio),
    FOREIGN KEY (idsucursal) REFERENCES sucursales(idsucursal),
    FOREIGN KEY (idempleado) REFERENCES empleados(idempleado),
    
    INDEX (expediente),
    INDEX (nombres),
    INDEX (apellidos)
);

CREATE TABLE horarios(
	idhorario INT PRIMARY KEY AUTO_INCREMENT,
    idespecialidad INT,
    idsucursal INT,
    hora_inicio VARCHAR(100),
    hora_fin VARCHAR(100),
    dias VARCHAR(100),
    
    FOREIGN KEY (idespecialidad) REFERENCES especialidades(idespecialidad),
    FOREIGN KEY (idsucursal) REFERENCES sucursales(idsucursal)
);

CREATE TABLE roles(
	idrol INT PRIMARY KEY AUTO_INCREMENT,
    rol VARCHAR(40) NOT NULL,
    descripcion VARCHAR(254)
);

CREATE TABLE menus(
	idmenu INT PRIMARY KEY AUTO_INCREMENT,
    menu VARCHAR(100) NOT NULL,
	idpadre INT,
    descripcion VARCHAR(254),
    url VARCHAR(254),
    
    FOREIGN KEY (idpadre) REFERENCES menus(idmenu)
);

CREATE TABLE permisos(
	idpermiso INT PRIMARY KEY AUTO_INCREMENT,
    idrol INT NOT NULL,
    idmenu INT NOT NULL,
    
    FOREIGN KEY (idrol) REFERENCES roles(idrol),
    FOREIGN KEY (idmenu) REFERENCES menus(idmenu)
);

CREATE TABLE usuarios(
	idusuario INT PRIMARY KEY AUTO_INCREMENT,
    idempleado INT NOT NULL,
    usuario VARCHAR(20) BINARY NOT NULL UNIQUE,
    clave VARCHAR(64) NOT NULL,
    idrol INT NOT NULL,
    estado ENUM('Activo', 'Bloqueado'),
    
    FOREIGN KEY (idempleado) REFERENCES empleados(idempleado),
    FOREIGN KEY (idrol) REFERENCES roles(idrol),
    
    INDEX (usuario)
);

CREATE TABLE laboratorios(
	idlaboratorio INT(6) PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(200) NOT NULL,
    idmunicipio INT(3) NOT NULL,
    direccion VARCHAR(200),
    descripcion LONGTEXT,
    
    FOREIGN KEY (idmunicipio) REFERENCES municipios(idmunicipio),
    
    INDEX (nombre)
);

CREATE TABLE marcas(
	idmarca INT(11) PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(200) NOT NULL,
    
    INDEX (marca)
);

CREATE TABLE consumibles(
	idconsumible INT(11) PRIMARY KEY AUTO_INCREMENT,
    tipo ENUM('Producto', 'Servicio') NOT NULL,
    nombre VARCHAR(200) NOT NULL,
    alias VARCHAR(100),
    presentacion ENUM('N/A', 'Líquido', 'Jarabe', 'Polvo', 'Pastilla', 'Cápsula'),
    idmarca INT(11),
    precio_compra DECIMAL(10,2) NOT NULL,
    precio_sugerido DECIMAL(10,2) NOT NULL,
    precio_venta DECIMAL(10,2) NOT NULL,
    
    FOREIGN KEY (idmarca) REFERENCES marcas(idmarca),
    
    INDEX (nombre),
    INDEX (alias)
);

CREATE TABLE compras(
	idcompra INT(11) PRIMARY KEY AUTO_INCREMENT,
    fecha DATETIME NOT NULL,
    idlaboratorio INT(11) NOT NULL, -- DISTRIBUIDOR O EMPRESA QUE VENDE EL PRODUCTO
    subtotal DECIMAL(10,2),
    descuentos DECIMAL(10,2),
    total DECIMAL(10,2),
    
    FOREIGN KEY (idlaboratorio) REFERENCES laboratorios(idlaboratorio)
);

DROP TRIGGER IF EXISTS bi_compras;
DELIMITER //
CREATE TRIGGER bi_compras
BEFORE INSERT ON compras
FOR EACH ROW
BEGIN
	IF(NEW.subtotal IS NULL) THEN
		SET NEW.subtotal = 0;
    END IF;
	IF(NEW.descuentos IS NULL) THEN
		SET NEW.descuentos = 0;
    END IF;
	IF(NEW.total IS NULL) THEN
		SET NEW.total = 0;
    END IF;
    
    INSERT INTO inventarios VALUES (NEW.idcompra, NEW.fecha, NEW.idlaboratorio);
END;
//
DELIMITER ;

CREATE TABLE detalles_compra(
	iddetalle_compra INT(11) PRIMARY KEY AUTO_INCREMENT,
    idcompra INT(11) NOT NULL,
    idconsumible INT(11) NOT NULL,
    fecha_caducidad DATE,
    cantidad INT(11) NOT NULL,
    precio_compra DECIMAL(10,2) NOT NULL,
    precio_sugerido DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2),
    
    FOREIGN KEY (idcompra) REFERENCES compras(idcompra),
    FOREIGN KEY (idconsumible) REFERENCES consumibles(idconsumible)
);


DROP TRIGGER IF EXISTS bi_detalles_compras;
DELIMITER //
CREATE TRIGGER bi_detalles_compra
BEFORE INSERT ON detalles_compra
FOR EACH ROW
BEGIN
	DECLARE t_venta DECIMAL(10,2);    
    SET t_venta = (NEW.cantidad*NEW.precio_compra);
    SET NEW.total = t_venta;
    
    UPDATE compras SET subtotal = subtotal + t_venta, total = total + t_venta WHERE idcompra = NEW.idcompra;  
    
    -- ACTUALIZAR EL CONSUMIBLE CON EL NUEVO PRECIO DE VENTA QUE VA EN LA COMPRA
    UPDATE consumibles SET precio_venta = NEW.precio_sugerido WHERE idconsumible = NEW.idconsumible;
    
    
    INSERT INTO detalles_inventario(idinventario, idconsumible, cantidad, fecha_caducidad) VALUES 
    (NEW.idcompra, NEW.idconsumible, NEW.cantidad, NEW.fecha_caducidad);
END;
//
DELIMITER ;

CREATE TABLE inventarios(
	idinventario INT(11) PRIMARY KEY AUTO_INCREMENT,
    fecha DATETIME NOT NULL,
    idlaboratorio INT(11) NOT NULL,
    
    FOREIGN KEY (idlaboratorio) REFERENCES laboratorios(idlaboratorio)
);

CREATE TABLE detalles_inventario(
	iddetalle_inventario INT(11) PRIMARY KEY AUTO_INCREMENT,
    idinventario INT NOT NULL,
    idconsumible INT(11) NOT NULL,
    cantidad INT NOT NULL,
    fecha_caducidad DATE,
    
    FOREIGN KEY (idinventario) REFERENCES inventarios(idinventario),
    FOREIGN KEY (idconsumible) REFERENCES consumibles(idconsumible)
);

CREATE TABLE ventas( -- FACTURA
	idventa INT(11) PRIMARY KEY AUTO_INCREMENT,
    numero INT NOT NULL,
    fecha DATETIME NOT NULL,
    idpaciente INT NOT NULL,
	paciente VARCHAR(200) NOT NULL,
	direccion LONGTEXT,
	telefono VARCHAR(20),
	idempleado INT NOT NULL,
	subtotal DECIMAL(10,2),
	descuentos DECIMAL(10,2),
	total DECIMAL(10,2),
	deuda DECIMAL(10,2),
    estado ENUM('Pendiente', 'Cancelada') DEFAULT 'Pendiente',
	
    FOREIGN KEY (idpaciente) REFERENCES pacientes(idpaciente),
    FOREIGN KEY (idempleado) REFERENCES empleados(idempleado)
);

DROP TRIGGER IF EXISTS bi_ventas;
DELIMITER //
CREATE TRIGGER bi_ventas
BEFORE INSERT ON ventas
FOR EACH ROW
BEGIN
	DECLARE n INT;
    DECLARE cant INT;
    
	SET cant = (SELECT COUNT(*) FROM ventas ORDER BY numero LIMIT 1);
    IF(cant > 0) THEN
		SET n = (SELECT numero FROM ventas ORDER BY numero LIMIT 1);
    ELSE
		SET n = 0;
    END IF;
	SET n = n+1;
    SET NEW.numero = n;
    SET NEW.estado = 'Pendiente';
END;
//
DELIMITER ;

CREATE TABLE detalles_venta(
	iddetalle_venta INT(11) PRIMARY KEY AUTO_INCREMENT,
    idventa INT NOT NULL,
    idconsumible INT(11) NOT NULL,
    cantidad INT(11),
	precio DECIMAL(10,2),
	excento DECIMAL(10,2),
	total	DECIMAL(10,2),
	no_sujeto DECIMAL(10,2),
	monto_iva DECIMAL(10,2),
    
    FOREIGN KEY (idventa) REFERENCES ventas(idventa),
    FOREIGN KEY (idconsumible) REFERENCES consumibles(idconsumible)
);

DROP TRIGGER IF EXISTS bi_detalles_venta;
DELIMITER //
CREATE TRIGGER bi_detalles_venta
BEFORE INSERT ON detalles_venta
FOR EACH ROW
BEGIN
	-- HAY QUE REDUCIR EL INVENTARIO, CAMBIAR EL SUBTOTAL Y EL TOTAL
	DECLARE t_venta DECIMAL(10,2);        
    DECLARE id_detalle_inventario INT;
    DECLARE cantidad_venta DECIMAL(10,2);
    DECLARE cantidad_inventario DECIMAL(10,2);
    
    SET NEW.precio = (SELECT precio_venta FROM consumibles WHERE idconsumible = NEW.idconsumible);
    SET t_venta = (NEW.cantidad*NEW.precio);
    SET NEW.total = t_venta;
    SET cantidad_venta = NEW.cantidad;
    
    UPDATE ventas SET subtotal = subtotal + t_venta, total = total + t_venta, deuda = deuda + t_venta WHERE idventa = NEW.idventa;    
    
    loop1: LOOP
		SET id_detalle_inventario = (SELECT iddetalle_inventario FROM detalles_inventario WHERE idconsumible = NEW.idconsumible AND cantidad > 0 AND idconsumible IN (SELECT idconsumible FROM consumibles WHERE tipo = 'PRODUCTO') LIMIT 1);
		SET cantidad_inventario = (SELECT cantidad FROM detalles_inventario WHERE idconsumible = NEW.idconsumible AND cantidad > 0 AND idconsumible IN (SELECT idconsumible FROM consumibles WHERE tipo = 'PRODUCTO') LIMIT 1);
        IF(cantidad_inventario IS NOT NULL) THEN
			IF(cantidad_venta > 0) THEN
				IF(cantidad_venta >= cantidad_inventario) THEN
					SET cantidad_venta = cantidad_venta - cantidad_inventario;
                    UPDATE detalles_inventario SET cantidad = 0 WHERE iddetalle_inventario = id_detalle_inventario;
                    ITERATE loop1;
				ELSE
					SET cantidad_inventario = cantidad_inventario - cantidad_venta;
                    UPDATE detalles_inventario SET cantidad = cantidad_inventario WHERE iddetalle_inventario = id_detalle_inventario;
                    LEAVE loop1;
                END IF;
			ELSE
				LEAVE loop1;
            END IF;
		ELSE
			LEAVE loop1;
        END IF;
    END LOOP loop1;
    
END;
//
DELIMITER ;

CREATE TABLE abonos(
	idabono INT PRIMARY KEY AUTO_INCREMENT,
    idventa	INT(11),
	fecha DATETIME,
	monto DECIMAL(10,2),
    
    FOREIGN KEY (idventa) REFERENCES ventas(idventa)
);

DROP TRIGGER IF EXISTS bi_abonos;
DELIMITER //
CREATE TRIGGER bi_abonos
BEFORE INSERT ON abonos
FOR EACH ROW
BEGIN
	-- REDUCIR LA DEUDA DE LA VENTA CON CADA ABONO
    -- HASTA QUE LA SUMA DE ABONOS = TOTAL SE CAMBIARÁ DE 'PENDIENTE' A CANCELADA
    DECLARE deuda_venta DECIMAL(10,2);
    SET deuda_venta = (SELECT deuda FROM ventas WHERE idventa = NEW.idventa);
    SET deuda_venta = deuda_venta - NEW.monto;
    
    UPDATE ventas SET deuda = deuda_venta WHERE idventa = NEW.idventa;
    
    IF(deuda_venta = 0) THEN
		UPDATE ventas SET estado = 'Cancelada' WHERE idventa = NEW.idventa;
    END IF;
END;
//
DELIMITER ;


-- SI ES CONSULTA
CREATE TABLE consultas(
	idconsulta INT PRIMARY KEY AUTO_INCREMENT,
    idpaciente INT NOT NULL,
    idservicio INT NOT NULL,
    iddoctor INT,
    idventa INT,
    fecha_hora DATETIME NOT NULL,
    programada BOOL DEFAULT FALSE,
    estado ENUM('Pendiente', 'Atendida'),
    
    FOREIGN KEY (idventa) REFERENCES ventas(idventa),
	FOREIGN KEY (idpaciente) REFERENCES pacientes(idpaciente),
    FOREIGN KEY (idservicio) REFERENCES consumibles(idconsumible),
    FOREIGN KEY (iddoctor) REFERENCES empleados(idempleado)
);

CREATE TABLE empleados_consulta(
	idempleado_consulta INT PRIMARY KEY AUTO_INCREMENT,
	idconsulta INT NOT NULL,
    idempleado INT NOT NULL,
    
    FOREIGN KEY (idconsulta) REFERENCES consultas(idconsulta),
    FOREIGN KEY (idempleado) REFERENCES empleados(idempleado)
);

CREATE TABLE detalles_consulta(
	iddetalle_consulta INT(11) PRIMARY KEY AUTO_INCREMENT,
    idconsulta INT,
    razon_consulta LONGTEXT,
	temperatura	VARCHAR(10),
	frecuencia_cardiaca VARCHAR(20),
	frecuencia_respiratoria VARCHAR(20),
	presion_arterial	VARCHAR(20),
	saturacion_oxigeno VARCHAR(20),
	diagnostico LONGTEXT,
	tratamiento LONGTEXT,
	observaciones LONGTEXT,
    
    FOREIGN KEY (idconsulta) REFERENCES consultas(idconsulta)
);

CREATE TABLE examenes(
	idexamen INT(11) PRIMARY KEY AUTO_INCREMENT,
    examen VARCHAR(200) NOT NULL,
    descripcion LONGTEXT
);

CREATE TABLE examenes_consulta(
	idexamen_consulta INT PRIMARY KEY AUTO_INCREMENT,
	idconsulta INT NOT NULL,
    idexamen INT NOT NULL,
    estado ENUM('Pendiente', 'Revisado'),
    fecha_revision DATE,
    resultados LONGTEXT,
    
    FOREIGN KEY (idconsulta) REFERENCES consultas(idconsulta),
    FOREIGN KEY (idexamen) REFERENCES examenes(idexamen)
);



-- TRIGGER PARA CÓDIGO DE SUCURSAL
DROP TRIGGER IF EXISTS bi_sucursales_codigo;
DELIMITER //
CREATE TRIGGER bi_sucursales_codigo
BEFORE INSERT ON sucursales
FOR EACH ROW
BEGIN
	DECLARE id CHAR(3);
	DECLARE correlativo INT;
    DECLARE char_correlativo VARCHAR(3);
    SET id = CONCAT('C', SUBSTRING(YEAR(NOW()), 3));
    SET correlativo = (SELECT COUNT(*) FROM sucursales WHERE SUBSTRING(codigo, 1, 3) = id);
    SET correlativo = correlativo + 1;
    
    IF (correlativo < 10) THEN
		SET char_correlativo = CONCAT('0',correlativo);
	ELSEIF (correlativo < 100) THEN
		SET char_correlativo = CONCAT('',correlativo);
	END IF;
    
    SET NEW.codigo = CONCAT(id, '-', char_correlativo);
END;
//

-- TRIGGER PARA CÓDIGO DE EMPLEADO
DROP TRIGGER IF EXISTS bi_empleados_codigo;
DELIMITER //
CREATE TRIGGER bi_empleados_codigo
BEFORE INSERT ON empleados
FOR EACH ROW
BEGIN
	DECLARE idemp VARCHAR(12);
    DECLARE codigo_sucursal VARCHAR(10);
    DECLARE correlativo INT;
    DECLARE char_correlativo VARCHAR(3);
    
    SET codigo_sucursal = (SELECT codigo FROM sucursales WHERE idsucursal = NEW.idsucursal);
    SET idemp = CONCAT(SUBSTRING(NEW.nombres,1,1),SUBSTRING(NEW.apellidos,1,1), SUBSTRING(YEAR(NEW.fecha_nacimiento), 3), '-', codigo_sucursal);
    SET correlativo = (SELECT COUNT(*) FROM empleados WHERE SUBSTRING(codigo, 1, 11) = idemp) + 1;
    
    IF (correlativo < 10) THEN
		SET char_correlativo = CONCAT('00',correlativo);
	ELSEIF (correlativo < 100) THEN
		SET char_correlativo = CONCAT('0',correlativo);
	ELSE
		SET char_correlativo = CONCAT('',correlativo);
	END IF;
    
    SET NEW.codigo = CONCAT(idemp,'-',char_correlativo);
END;
//

/* --OTROS TRIGGERS
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