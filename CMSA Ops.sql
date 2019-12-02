USE cmsa;

INSERT INTO empresa(nombre, facebook, whatsapp) VALUES
('Clínicas Médicas San Antonio', 'Clínicas Médicas San Antonio', '+503 7654 3210');

INSERT INTO noticias(titulo, resumen, contenido) VALUES
('¡Consultas Gratis!', 'El día 13 de octubre habrá consultas gratis', 'Detalles');

INSERT INTO departamentos(departamento, zona) VALUES 
('AHUACHAPÁN','OCCIDENTAL'),
('SANTA ANA','OCCIDENTAL'),
('SONSONATE','OCCIDENTAL'),
('CABAÑAS','CENTRAL'),
('CHALATENANGO','CENTRAL'),
('CUSCATLÁN','CENTRAL'),
('LA LIBERTAD','CENTRAL'),
('LA PAZ','CENTRAL'),
('SAN SALVADOR','CENTRAL'),
('SAN VICENTE','CENTRAL'),
('USULUTÁN','ORIENTAL'),
('SAN MIGUEL','ORIENTAL'),
('MORAZÁN','ORIENTAL'),
('LA UNIÓN','ORIENTAL');

/*
INSERT INTO departamentos (departamento, zona) VALUES							
	(	"	Ahuachapán	"	,	"	OCCIDENTAL	"	)	,
	(	"	Cabañas	"	,	"	CENTRAL	"	)	,
	(	"	Chalatenango	"	,	"	CENTRAL	"	)	,
	(	"	Cuscatlán	"	,	"	CENTRAL	"	)	,
	(	"	Morazán	"	,	"	ORIENTAL	"	)	,
	(	"	La Libertad	"	,	"	CENTRAL	"	)	,
	(	"	La Paz	"	,	"	CENTRAL	"	)	,
	(	"	La Unión	"	,	"	ORIENTAL	"	)	,
	(	"	San Miguel	"	,	"	ORIENTAL	"	)	,
	(	"	San Salvador	"	,	"	CENTRAL	"	)	,
	(	"	San Vicente	"	,	"	CENTRAL	"	)	,
	(	"	Santa Ana	"	,	"	OCCIDENTAL	"	)	,
	(	"	Sonsonate	"	,	"	OCCIDENTAL	"	)	,
	(	"	Usulután	"	,	"	ORIENTAL	"	)
;
*/

INSERT INTO municipios(municipio, iddepartamento) VALUES					
("	Ahuachapán	",	1	)	,
("	Apaneca	",	1	)	,
("	Atiquizaya	",	1	)	,
("	Concepción de Ataco	",	1	)	,
("	El Refugio	",	1	)	,
("	Guaymango	",	1	)	,
("	Jujutla	",	1	)	,
("	San Francisco Menéndez	",	1	)	,
("	San Lorenzo	",	1	)	,
("	San Pedro Puxtla	",	1	)	,
("	Tacuba	",	1	)	,
("	Turín	",	1	)	,
("	Cinquera	",	2	)	,
("	Villa Dolores	",	2	)	,
("	Guacotecti	",	2	)	,
("	Ilobasco	",	2	)	,
("	Jutiapa	",	2	)	,
("	San Isidro	",	2	)	,
("	Sensuntepeque	",	2	)	,
("	Tejutepeque	",	2	)	,
("	Victoria	",	2	)	,
("	Citalá	",	3	)	,
("	Comalapa	",	3	)	,
("	Concepción Quezaltepeque	",	3	)	,
("	Dulce Nombre de María	",	3	)	,
("	El Carrizal	",	3	)	,
("	El Paraíso	",	3	)	,
("	La Laguna	",	3	)	,
("	La Palma	",	3	)	,
("	La Reina	",	3	)	,
("	Las Vueltas	",	3	)	,
("	Nombre de Jesús	",	3	)	,
("	Nueva Concepción	",	3	)	,
("	Nueva Trinidad	",	3	)	,
("	Ojos de Agua	",	3	)	,
("	Potonico	",	3	)	,
("	San Antonio de la Cruz	",	3	)	,
("	San Antonio Los Ranchos	",	3	)	,
("	San Fernando	",	3	)	,
("	San Francisco Lempa	",	3	)	,
("	San Francisco Morazán	",	3	)	,
("	San Ignacio	",	3	)	,
("	San Isidro Labrador	",	3	)	,
("	San José Cancasque	",	3	)	,
("	San José Las Flores	",	3	)	,
("	San Luis del Carmen	",	3	)	,
("	San Miguel de Mercedes	",	3	)	,
("	San Rafael	",	3	)	,
("	Santa Rita	",	3	)	,
("	Tejutla	",	3	)	,
("	Candelaria	",	4	)	,
("	Cojutepeque	",	4	)	,
("	El Carmen	",	4	)	,
("	El Rosario	",	4	)	,
("	Monte San Juan	",	4	)	,
("	Oratorio de Concepción	",	4	)	,
("	San Bartolomé Perulapía	",	4	)	,
("	San Cristóbal	",	4	)	,
("	San José Guayabal	",	4	)	,
("	San Pedro Perulapán	",	4	)	,
("	San Rafael Cedros	",	4	)	,
("	San Ramón	",	4	)	,
("	Santa Cruz Analquito	",	4	)	,
("	Santa Cruz Michapa	",	4	)	,
("	Suchitoto	",	4	)	,
("	Tenancingo	",	4	)	,
("	Arambala	",	5	)	,
("	Cacaopera	",	5	)	,
("	Chilanga	",	5	)	,
("	Corinto	",	5	)	,
("	Delicias de Concepción	",	5	)	,
("	El Divisadero	",	5	)	,
("	El Rosario	",	5	)	,
("	Gualococti	",	5	)	,
("	Guatajiagua	",	5	)	,
("	Joateca	",	5	)	,
("	Jocoaitique	",	5	)	,
("	Jocoro	",	5	)	,
("	Lolotiquillo	",	5	)	,
("	Meanguera	",	5	)	,
("	Osicala	",	5	)	,
("	Perquín	",	5	)	,
("	San Carlos	",	5	)	,
("	San Fernando	",	5	)	,
("	San Francisco Gotera	",	5	)	,
("	San Isidro	",	5	)	,
("	San Simón	",	5	)	,
("	Sensembra	",	5	)	,
("	Sociedad	",	5	)	,
("	Torola	",	5	)	,
("	Yamabal	",	5	)	,
("	Yoloaiquín	",	5	)	,
("	Antiguo Cuscatlán	",	6	)	,
("	Chiltiupán	",	6	)	,
("	Ciudad Arce	",	6	)	,
("	Colón	",	6	)	,
("	Comasagua	",	6	)	,
("	Huizúcar	",	6	)	,
("	Jayaque	",	6	)	,
("	Jicalapa	",	6	)	,
("	La Libertad	",	6	)	,
("	Santa Tecla	",	6	)	,
("	Nuevo Cuscatlán	",	6	)	,
("	San Juan Opico	",	6	)	,
("	Quezaltepeque	",	6	)	,
("	Sacacoyo	",	6	)	,
("	San José Villanueva	",	6	)	,
("	San Matías	",	6	)	,
("	San Pablo Tacachico	",	6	)	,
("	Talnique	",	6	)	,
("	Tamanique	",	6	)	,
("	Teotepeque	",	6	)	,
("	Tepecoyo	",	6	)	,
("	Zaragoza	",	6	)	,
("	Cuyultitán	",	7	)	,
("	El Rosario / Rosario de La Paz	",	7	)	,
("	Jerusalén	",	7	)	,
("	Mercedes La Ceiba	",	7	)	,
("	Olocuilta	",	7	)	,
("	Paraíso de Osorio	",	7	)	,
("	San Antonio Masahuat	",	7	)	,
("	San Emigdio	",	7	)	,
("	San Francisco Chinameca	",	7	)	,
("	San Juan Nonualco	",	7	)	,
("	San Juan Talpa	",	7	)	,
("	San Juan Tepezontes	",	7	)	,
("	San Luis La Herradura	",	7	)	,
("	San Luis Talpa	",	7	)	,
("	San Miguel Tepezontes	",	7	)	,
("	San Pedro Masahuat	",	7	)	,
("	San Pedro Nonualco	",	7	)	,
("	San Rafael Obrajuelo	",	7	)	,
("	Santa María Ostuma	",	7	)	,
("	Santiago Nonualco	",	7	)	,
("	Tapalhuaca	",	7	)	,
("	Zacatecoluca	",	7	)	,
("	Anamorós	",	8	)	,
("	Bolívar	",	8	)	,
("	Concepción de Oriente	",	8	)	,
("	Conchagua	",	8	)	,
("	El Carmen	",	8	)	,
("	El Sauce	",	8	)	,
("	Intipucá	",	8	)	,
("	La Unión	",	8	)	,
("	Lilisque	",	8	)	,
("	Meanguera del Golfo	",	8	)	,
("	Nueva Esparta	",	8	)	,
("	Pasaquina	",	8	)	,
("	Polorós	",	8	)	,
("	San Alejo	",	8	)	,
("	San José	",	8	)	,
("	Santa Rosa de Lima	",	8	)	,
("	Yayantique	",	8	)	,
("	Yucuaiquín	",	8	)	
;


INSERT INTO sucursales(idempresa, idmunicipio, direccion, telefono1, telefono2, email) VALUES
(1, 7, 'Cuesta desde el Hospital Nacional Mazzini hacia El Carmen', '2456987', '2123658', 'clinica@gmail.com');

INSERT INTO cargos(cargo, descripcion) VALUES
('DOCTOR', 'Cargo que tiene cada doctor dentro de la clínica'),
('ENFERMERO', 'Cargo que tiene cada enfermero dentro de la clínica'),
('ORDENANZA', 'Cargo que tienen las personas que se encargan del aseo y orden de la clínica');

INSERT INTO empleados(idjefe, idsucursal, idcargo, codigo, jvpm, nombres, apellidos, genero, fecha_nacimiento, dui, nit, idmunicipio, direccion, fecha_contratacion, estado) VALUES
(NULL, 1, 1, 'AG87-C19-01-001', '15143', 'Jeannette Alexandra', 'Áviles García', 'FEMENINO', '1987-01-01', '00000000-0', '0101-010101-001-0', 2, 'cualquiera', '2019-01-15', 'ACTIVO'),
(1, 1, 2, 'AA87-C19-01-001', NULL, 'Luis Carlos', 'Áviles', 'MASCULINO', '1987-01-01', '00000000-0', '0101-010101-001-0', 7, 'cualquiera', '2019-01-15', 'ACTIVO');

INSERT INTO contactos(tipo, contacto, idempleado) VALUES
('TELEFONO', '7111-1111', 1);

INSERT INTO especialidades(especialidad) VALUES
('Medicina General');

INSERT INTO especialidades_medico(idespecialidad, idmedico) VALUES
(1, 1);

INSERT INTO pacientes(expediente, nombres, apellidos, fecha_nacimiento, genero, telefono, email, idmunicipio, idsucursal, idempleado) VALUES
(1, 'Mario Adalberto', 'Rivera Olivo', '1997-08-13', 'MASCULINO', '7777-8888', 'mario@gmail.com', 3, '1', '1');

INSERT INTO horarios(idespecialidad, idsucursal, hora_inicio, hora_fin, dias) VALUES
(1, 1, '6:30 AM', '6:30 PM', 'LUNES, MIÉRCOLES Y SÁBADO'),
(1, 1, '6:30 AM', '12:00 PM', 'VIERNES');

INSERT INTO roles(rol, descripcion) VALUES
('ADMIN', 'Puede acceder a todas las opciones del sistema'),
('LIMITADO', 'Sólo puede acceder a ver las consultas pendientes');

INSERT INTO menus(menu, idpadre, descripcion, url) VALUES
('Gestión', NULL, 'Muestra las distintas operaciones que puede realizarse con respecto a las consultas', '/Gestion'),
('Operaciones', NULL, '', '/Operaciones'),
('Configuraciones', NULL, 'Muestra todas las características que se pueden configurar en el sistema', '/Configuraciones'),
('Reportes', NULL, 'Muestra los distintos reportes que puede realizar', '/Reportes'),
('Cargos', 1, '', '/Cargos'),
('Compras', 2, '', '/Compras'),
('Consultas', 2, '', '/Consultas'),
('Consumibles', 1, '', '/Consumibles'),
('Contactos', 1, '', '/Contactos'),
('Departamentos', 1, '', '/Departamentos'),
('Empleados', 1, '', '/Empleados'),
('Empresas', 1, '', '/Empresas'),
('Especialidades', 1, '', '/Especialidades'),
('Esp. por médico', 1, '', '/Especialidades_medico'),
('Horarios', 3, '', '/Horarios'),
('Inventarios', 2, '', '/Inventarios'),
('Laboratorios', 1, '', '/Laboratorios'),
('Marcas', 1, '', '/Marcas'),
('Menus', 1, '', '/Menus'),
('Municipios', 3, '', '/Municipios'),
('Noticias', 3, '', '/Noticias'),
('Pacientes', 1, '', '/Pacientes'),
('Permisos', 3, '', '/Permisos'),
('Roles', 1, '', '/Roles'),
('Sucursales', 1, '', '/Sucursales'),
('Usuarios', 1, '', '/Usuarios'),
('Reporte de ventas', 4, '', '/Ventas'),
('Reporte de consultas', 4, '', '/Ventas'),
('Constancia de buena salud', 4, '', '/Ventas'),
('Constancia de incapacidad', 4, '', '/Ventas');

INSERT INTO permisos(idrol, idmenu) SELECT 1, idmenu FROM menus;
INSERT INTO permisos(idrol, idmenu) SELECT 2, idmenu FROM menus WHERE idmenu IN (1);

INSERT INTO usuarios(idempleado, usuario, clave, idrol, estado) VALUES
(1, 'alexandra.aviles', sha2('admin', 256), 1, 'ACTIVO'), -- Será para los pacientes
(2, 'luis.aviles', sha2('admin', 256), 2, 'ACTIVO');

INSERT INTO laboratorios(nombre, idmunicipio, direccion, descripcion) VALUES
('Clínicas Médicas San Antonio', 7, 'Cuesta yendo para El Carmen', NULL),
('Laboratorios López', 2, 'Por ahí', 'Laboratorio que vende medicamentos y cosas así');

INSERT INTO marcas(marca) VALUES
('N/A'),
('Enron');

INSERT INTO consumibles(tipo, nombre, alias, presentacion, idmarca, precio_compra, precio_sugerido, precio_venta) VALUES
 -- QUIZÁ AL INSERTAR UN SERVICIO, QUE SE INSERTE EL INVENTARIO. A LOS SERVICIOS DEFINIRLES EL PRECIO DE VENTA, YA QUE NO SE COMPRAN
('SERVICIO', 'Consulta general', 'General', 'N/A', 1, 0, 0, 10),
('PRODUCTO', 'Suero oral 400ml', 'Suero oral', 'Líquido', 2, 0, 0, 0),
('PRODUCTO','Acetaminofén','Paracetamol','Pastilla',2,0,0,0);

INSERT INTO compras(fecha, idlaboratorio) VALUES
(NOW(), 2);

INSERT INTO detalles_compra(idcompra, idconsumible, fecha_caducidad, cantidad, precio_compra, precio_sugerido) VALUES
(1, 2, NOW(), 50, 2, 2.50),
(1, 3, NOW(), 200, 0.10, 0.15);

/*INSERT INTO inventarios(fecha, idlaboratorio) VALUES
(NOW(), 2),
(NOW(), 1);

INSERT INTO detalles_inventario(idinventario, idconsumible, cantidad, fecha_caducidad) VALUES -- PRODUCTOS
(1, 2, 50, NULL); -- ESTE SERÁ MODIFICADO AL MOMENTO DE CADA VENTA
INSERT INTO detalles_inventario(idinventario, idconsumible, cantidad) VALUES -- SERVICIOS
(2, 1, 1);*/


INSERT INTO ventas(numero, fecha, idpaciente, paciente, direccion, telefono, idempleado, subtotal, descuentos, total, deuda, estado) VALUES
(1, NOW(), 1, 'Mario Adalberto Rivera Olivo', NULL, NULL, 2, 0, 0, 0, 0, 'Pendiente');

INSERT INTO detalles_venta(idventa, idconsumible, cantidad, precio, excento, no_sujeto, monto_iva) VALUES
(1, 1, 1, 10, 0, 0, 0),
(1, 2, 45, 2.80, 0, 0, 0),
(1, 3, 5, 25, 0,0,0);

-- UPDATE ventas SET subtotal = 24, descuentos = 0, total = 24, deuda = 0, estado = 'CANCELADA' WHERE idventa = 1;

INSERT INTO abonos(idventa, fecha, monto) VALUES
(1, NOW(), 40); -- SI LA SUMA DE LOS ABONOS POR VENTA ES IGUAL AL TOTAL EN LA VENTA, SE CAMBIA LA VENTA DE 'PENDIENTE' A 'CANCELADA' Y LA DEUDA A 0
/*
-- UPDATE detalles_inventario SET cantidad = (cantidad - 5) WHERE iddetalle_inventario = 1; -- SÓLO SE REDUCEN LOS PRODUCTOS
*/

INSERT INTO consultas(idpaciente, idservicio, iddoctor, fecha_hora, programada, estado) VALUES
(1,1,1, NOW(),FALSE,'Atendida');

INSERT INTO empleados_consulta(idconsulta, idempleado) VALUES
(1, 1), -- IDCONSULTA ES EL ID DE LA VENTA EN LA QUE SE REGISTRÓ UN SERVICIO
(1, 2);

INSERT INTO detalles_consulta(idconsulta, razon_consulta, temperatura, frecuencia_cardiaca, frecuencia_respiratoria, presion_arterial, saturacion_oxigeno, diagnostico, tratamiento, observaciones) VALUES 
(1, 'Dolor de cabeza, mareos, náuseas', '37.5', '', '', '', '', 'Gripe común', '2 pastillas cada 8 horas', 'Debe dejar de tomar agua helada o algo así');

INSERT INTO examenes(examen, descripcion) VALUES
('Hemograma', 'Se saca sangre al paciente y se envía a laboratorio, para ver el recuento de glóbulos rojos');

INSERT INTO examenes_consulta(idconsulta, idexamen, estado, fecha_revision, resultados) VALUES
(1, 1, 'PENDIENTE', '2019-10-25', NULL);