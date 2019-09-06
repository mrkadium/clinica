SELECT
	a.*,
    b.municipio,
    c.departamento
FROM pacientes a, municipios b, departamentos c
WHERE
	a.idmunicipio = b.idmunicipio
    AND b.idmunicipio = c.idmunicipio
;