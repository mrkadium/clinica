USE cmsa;

-- PACIENTES
SELECT
	a.*,
    b.municipio,
    c.departamento
FROM pacientes a, municipios b, departamentos c
WHERE
	a.idmunicipio = b.idmunicipio
    AND b.iddepartamento = c.iddepartamento
;
SELECT
	a.idpaciente,
	CONCAT(a.nombres,' ', a.apellidos) AS paciente,
    CONCAT(b.municipio, ', ', c.departamento) AS direccion
FROM pacientes a, municipios b, departamentos c
WHERE
	a.idmunicipio = b.idmunicipio
    AND b.iddepartamento = c.iddepartamento;


-- COMPRAS
SELECT * FROM compras;
SELECT
	a.idcompra, a.fecha, b.nombre,
    a.subtotal, a.descuentos, a.total,
    (SELECT SUM(cantidad) FROM detalles_compra WHERE idcompra = a.idcompra) AS cant_productos
FROM compras a, laboratorios b
WHERE
	a.idlaboratorio = b.idlaboratorio
;
-- DETALLES_COMPRA
SELECT * FROM detalles_compra;
SELECT * FROM consumibles;



-- INVENTARIOS
SELECT * FROM inventarios;
-- DETALLES_INVENTARIO
SELECT * FROM detalles_inventario;
SELECT * FROM consumibles;
SELECT 
	a.idconsumible, b.nombre, b.tipo,
    IF(b.tipo = 'Producto', SUM(a.cantidad), 1) as existencias,
    b.precio_venta
FROM detalles_inventario a, consumibles b
WHERE 
	a.idconsumible = b.idconsumible
--    AND b.tipo = 'Producto'
GROUP BY a.idconsumible
ORDER BY b.tipo DESC
;



-- VENTAS
SELECT * FROM ventas;
-- DETALLES_VENTA
SELECT * FROM detalles_venta;
SELECT a.idconsumible, b.nombre, a.cantidad, a.precio
FROM detalles_venta a, consumibles b
WHERE a.idconsumible = b.idconsumible;


-- ABONOS
SELECT * FROM abonos;


-- EMPLEADOS_CONSULTA
SELECT * FROM empleados_consulta;
SELECT a.*, b.apellidos FROM empleados_consulta a, empleados b WHERE a.idempleado = b.idempleado;


-- DETALLES_CONSULTA
SELECT * FROM detalles_consulta;


-- EXÁMENES
SELECT * FROM examenes;


-- EXÁMENES_CONSULTA
SELECT * FROM examenes_consulta;


-- MUNICIPIOS
SELECT * FROM municipios;

-- CONSULTAS
SELECT * FROM consultas;
SELECT
    a.idconsulta, a.fecha_hora, 
    CONCAT(b.nombres,' ',b.apellidos) as paciente,
    c.nombre, CONCAT(d.nombres,' ',d.apellidos) as doctor, a.idventa, a.programada, a.estado
FROM consultas a, pacientes b, consumibles c, empleados d
WHERE
	a.idpaciente = b.idpaciente
    AND a.idservicio = c.idconsumible
    AND a.iddoctor = d.idempleado
ORDER BY a.fecha_hora
;
SELECT
    a.idconsulta, a.fecha_hora, 
    CONCAT(b.nombres,' ',b.apellidos) as paciente,
    c.nombre, IF(a.iddoctor IS NULL,'-',CONCAT(d.nombres,' ',d.apellidos)) as doctor, a.idventa, a.programada, a.estado
FROM consultas a
INNER JOIN pacientes b ON a.idpaciente = b.idpaciente
INNER JOIN consumibles c ON a.idservicio = c.idconsumible
LEFT JOIN empleados d ON a.iddoctor = d.idempleado
ORDER BY a.idconsulta
;

SELECT * FROM detalles_consulta WHERE idconsulta = 1;

SELECT idempleado, CONCAT(nombres,' ',apellidos) AS empleado FROM empleados WHERE jvpm IS NOT NULL;


-- EMPLEADOS
SELECT * FROM empleados;


-- EMPLEADOS_CONSULTA (MINIVIEW)
SELECT * FROM empleados_consulta;
SELECT 
	a.idempleado_consulta, a.idempleado, 
    CONCAT(b.nombres,' ',b.apellidos) AS empleado,
    c.cargo
FROM empleados_consulta a, empleados b, cargos c
WHERE
	a.idempleado = b.idempleado
    AND b.idcargo = c.idcargo
    AND a.idconsulta = 1
;



-- EMPLEADOS (EMPLEADOS_CONSULTA)
SELECT 
	b.idempleado, 
    CONCAT(b.nombres,' ',b.apellidos) AS empleado,
    c.cargo
FROM empleados b, cargos c
WHERE
	b.idcargo = c.idcargo
;


-- MENÚS POR ROL
SELECT * FROM roles;
SELECT * FROM menus;
SELECT * FROM permisos WHERE idrol = 1;


-- HORARIOS
SELECT * FROM horarios;
SELECT
	a.idhorario, IF(a.idespecialidad IS NOT NULL, c.especialidad,'-') AS especialidad, b.codigo, a.hora_inicio, a.hora_fin, a.dias
FROM horarios a
INNER JOIN sucursales b ON a.idsucursal = b.idsucursal
LEFT JOIN especialidades c ON a.idespecialidad = c.idespecialidad;


-- CONSTANCIAS
-- BUENA SALUD
SELECT CONCAT('Por este medio hago constar que ', a.nombres,' ',a.apellidos, ' consta de buena salud') as texto
FROM pacientes a;
SELECT 
	IF((SELECT x.genero FROM empleados x WHERE x.idempleado = 1) = 'MASCULINO', 'Dr.', 'Dra.') as sexo,
    (SELECT x.jvpm FROM empleados x WHERE x.idempleado = 1) as jvpm,
    (SELECT CONCAT(x.nombres,' ',x.apellidos) FROM empleados x WHERE x.idempleado = 1) as doctor,
	CONCAT(nombres,' ',apellidos) AS paciente,
	IF(MONTH(NOW()) - MONTH(fecha_nacimiento) < 0, YEAR(NOW()) - YEAR(fecha_nacimiento) - 1, 
		IF(MONTH(NOW()) - MONTH(fecha_nacimiento) = 0 AND DAY(NOW()) - DAY(fecha_nacimiento) < 0, YEAR(NOW()) -  YEAR(fecha_nacimiento) - 1, 
			YEAR(NOW()) -  YEAR(fecha_nacimiento))) AS edad
FROM pacientes
WHERE idpaciente = 1;



-- CONSULTA PARA REPORTE
SELECT * FROM consultas;
SELECT * FROM detalles_consulta;



-- EXÁMENES
SELECT * FROM examenes;
SELECT * FROM examenes_consulta;

SELECT
	a.idexamen_consulta, a.idconsulta, a.idexamen, b.examen, a.estado, a.fecha_revision, a.resultados
FROM examenes_consulta a, examenes b
WHERE
	a.idexamen = b.idexamen
    AND a.idconsulta = 1;

