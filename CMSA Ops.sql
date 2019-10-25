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

INSERT INTO municipios(municipio, iddepartamento) VALUES
('SONSONATE',3),
('SONZACATE',3),
('NAHULINGO',3),
('IZALCO',3),
('NAHUIZALCO',3),
('ACAJUTLA',3),
('SAN ANTONIO DEL MONTE',3);

INSERT INTO sucursales(codigo, idempresa, idmunicipio, direccion, telefono1, telefono2, email) VALUES
('C19-01', 1, 7, 'Cuesta desde el Hospital Nacional Mazzini hacia El Carmen', '2456987', '2123658', 'clinica@gmail.com');

INSERT INTO cargos(cargo, descripcion) VALUES
('DOCTOR', 'Cargo que tiene cada doctor dentro de la clínica'),
('ENFERMERO', 'Cargo que tiene cada enfermero dentro de la clínica'),
('ORDENANZA', 'Cargo que tienen las personas que se encargan del aseo y orden de la clínica');

INSERT INTO empleados(idsucursal, idcargo, codigo, jvpm, nombres, apellidos, genero, fecha_nacimiento, dui, nit, idmunicipio, direccion, fecha_contratacion, estado) VALUES
(1, 1, 'AG87-C19-01-001', '15143', 'Jeannette Alexandra', 'Áviles García', 'FEMENINO', '1987-01-01', '00000000-0', '0101-010101-001-0', 2, 'cualquiera', '2019-01-15', 'ACTIVO'),
(1, 2, 'AA87-C19-01-001', NULL, 'Luis Carlos', 'Áviles', 'MASCULINO', '1987-01-01', '00000000-0', '0101-010101-001-0', 7, 'cualquiera', '2019-01-15', 'ACTIVO');

INSERT INTO contactos(tipo, contacto, idempleado) VALUES
('TELEFONO', '7111-1111', 1);

INSERT INTO especialidades(especialidad) VALUES
('Medicina General');

INSERT INTO especialidades_medico(idespecialidad, idmedico) VALUES
(1, 1);

INSERT INTO pacientes(expediente, nombres, apellidos, fecha_nacimiento, genero, telefono, email, idmunicipio, codigo_sucursal, codigo_empleado) VALUES
(1, 'Mario Adalberto', 'Rivera Olivo', '1997-08-13', 'MASCULINO', '7777-8888', 'mario@gmail.com', 3, 'C19-01', 'AA87-C19-01-001');

INSERT INTO horarios(idespecialidad, idsucursal, hora_inicio, hora_fin, dias) VALUES
(1, 1, '6:30 AM', '6:30 PM', 'LUNES, MIÉRCOLES Y SÁBADO'),
(1, 1, '6:30 AM', '12:00 PM', 'VIERNES');

INSERT INTO roles(rol, descripcion) VALUES
('ADMIN', 'Puede acceder a todas las opciones del sistema'),
('LIMITADO', 'Sólo puede acceder a ver las consultas pendientes');

INSERT INTO menus(menu, idpadre, descripcion, url) VALUES
('Home', NULL, 'Pantalla principal', '/Home'),
('Configuraciones', 1, 'Muestra todas las características que se pueden configurar en el sistema', '/Home?accion=configuraciones');

INSERT INTO permisos(idrol, idmenu) SELECT 1, idmenu FROM menus;
INSERT INTO permisos(idrol, idmenu) SELECT 1, idmenu FROM menus WHERE idmenu IN (1);

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
('SERVICIO', 'Consulta general', 'General', 'N/A', 1, 0, 0, 10), -- QUIZÁ AL INSERTAR UN SERVICIO, QUE SE INSERTE EL INVENTARIO
('PRODUCTO', 'Suero oral 400ml', 'Suero oral', 'LÍQUIDO', 2, 2, 2.50, 2.80);

INSERT INTO compras(fecha, idlaboratorio) VALUES
(NOW(), 2);

INSERT INTO detalles_compra(idcompra, idconsumible, fecha_caducidad, cantidad, precio_compra, precio_sugerido, gravado) VALUES
(1, 2, NULL, 50, 2, 2.50, 100);

INSERT INTO inventarios(fecha, idlaboratorio) VALUES
(NOW(), 2),
(NOW(), 1);

INSERT INTO detalles_inventario(idinventario, idconsumible, cantidad, fecha_caducidad) VALUES -- PRODUCTOS
(1, 2, 50, NULL); -- ESTE SERÁ MODIFICADO AL MOMENTO DE CADA VENTA
INSERT INTO detalles_inventario(idinventario, idconsumible, cantidad) VALUES -- SERVICIOS
(2, 1, 1);

INSERT INTO ventas(numero, fecha, idpaciente, paciente, direccion, telefono, idempleado, subtotal, descuentos, total, deuda, estado) VALUES
(1, NOW(), 1, 'Mario Adalberto Rivera Olivo', NULL, NULL, 2, 0, 0, 0, 0, 'PENDIENTE');

INSERT INTO detalles_venta(idventa, idconsumible, cantidad, precio, excento, gravado, no_sujeto, monto_iva, monto_fovial, monto_guerra) VALUES
(1, 2, 5, 2.80, 0, 14, 0, 0, 0, 0),
(1, 1, 1, 10, 0, 10, 0, 0, 0, 0);

UPDATE ventas SET subtotal = 24, descuentos = 0, total = 24, deuda = 0, estado = 'CANCELADA' WHERE idventa = 1;

INSERT INTO abonos(idventa, fecha, monto) VALUES
(1, NOW(), 24); -- SI LA SUMA DE LOS ABONOS POR VENTA ES IGUAL AL TOTAL EN LA VENTA, SE CAMBIA LA VENTA DE 'PENDIENTE' A 'CANCELADA' Y LA DEUDA A 0

UPDATE detalles_inventario SET cantidad = (cantidad - 5) WHERE iddetalle_inventario = 1; -- SÓLO SE REDUCEN LOS PRODUCTOS

INSERT INTO empleados_consulta(idconsulta, idempleado) VALUES
(1, 1), -- IDCONSULTA ES EL ID DE LA VENTA EN LA QUE SE REGISTRÓ UN SERVICIO
(1, 2);

INSERT INTO detalles_consulta(idconsulta, razon_consulta, temperatura, frecuencia_cardiaca, frecuencia_respiratoria, presion_arterial, saturacion_oxigeno, diagnostico, tratamiento, observaciones) VALUES 
(1, 'Dolor de cabeza, mareos, náuseas', '37.5', '', '', '', '', 'Gripe común', '2 pastillas cada 8 horas', 'Debe dejar de tomar agua helada o algo así');

INSERT INTO examenes(examen, descripcion) VALUES
('Hemograma', 'Se saca sangre al paciente y se envía a laboratorio, para ver el recuento de glóbulos rojos');

INSERT INTO examenes_consulta(idconsulta, idexamen, estado, fecha_revision, resultados) VALUES
(1, 1, 'PENDIENTE', '2019-10-25', NULL);