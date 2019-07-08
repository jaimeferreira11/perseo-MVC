--No se porque solo funciona el registrar orden pago si se saca este constraint
alter table ordenpagocompra drop constraint fk_ordenpagocompra_ordenpagocab;

ALTER TABLE compradet ALTER COLUMN idarticulodeposito DROP NOT NULL;
INSERT INTO articulo(
            idarticulo, idsucursal, idusuario, descripcion, codigobarra,
            idfamilia, idmarca, codmoneda, idunidadmedida, idtipoimpuesto,
            estado, idlineaarticulo, preciocosto,
            idempresa)
    VALUES (0, 1, 0, 'Servicio Generico', '?',
            1, 1, 1, 1, 10,
            TRUE, 1, 0,
            1);

INSERT INTO marca (descripcion) values ('AOC');
INSERT INTO marca (descripcion) values ('DELL');
INSERT INTO marca (descripcion) values ('Samsung');
INSERT INTO marca (descripcion) values ('Apple');

INSERT INTO articulo(idsucursal, idusuario, descripcion, codigobarra,
            idfamilia, idmarca, codpais, codmoneda, idunidadmedida, idtipoimpuesto,
            fechalog, fechaestado, estado, idlineaarticulo)
VALUES (1, 0, 'Mouse USB Optico', '000123', 1, 1, NULL, 1, 1, 10, now(),now(),TRUE,1);
INSERT INTO articulo(idsucursal, idusuario, descripcion, codigobarra,
            idfamilia, idmarca, codpais, codmoneda, idunidadmedida, idtipoimpuesto,
            fechalog, fechaestado, estado, idlineaarticulo)
VALUES (1, 0, 'Mouse USB Bolilla', '000124', 1, 1, NULL, 1, 1, 10, now(),now(),TRUE,1);
INSERT INTO articulo(idsucursal, idusuario, descripcion, codigobarra,
            idfamilia, idmarca, codpais, codmoneda, idunidadmedida, idtipoimpuesto,
            fechalog, fechaestado, estado, idlineaarticulo)
VALUES (1, 0, 'Monitor 16', '000125', 1, 1, NULL, 1, 1, 10, now(),now(),TRUE,1);
INSERT INTO articulo(idsucursal, idusuario, descripcion, codigobarra,
            idfamilia, idmarca, codpais, codmoneda, idunidadmedida, idtipoimpuesto,
            fechalog, fechaestado, estado, idlineaarticulo)
VALUES (1, 0, 'Monitor 14', '000126', 1, 1, NULL, 1, 1, 10, now(),now(),TRUE,1);
INSERT INTO articulo(idsucursal, idusuario, descripcion, codigobarra,
            idfamilia, idmarca, codpais, codmoneda, idunidadmedida, idtipoimpuesto,
            fechalog, fechaestado, estado, idlineaarticulo)
VALUES (1, 0, 'Monitor 22', '000127', 1, 1, NULL, 1, 1, 10, now(),now(),TRUE,1);
INSERT INTO articulo(idsucursal, idusuario, descripcion, codigobarra,
            idfamilia, idmarca, codpais, codmoneda, idunidadmedida, idtipoimpuesto,
            fechalog, fechaestado, estado, idlineaarticulo)
VALUES (1, 0, 'Teclado USB Negro', '000128', 1, 1, NULL, 1, 1, 10, now(),now(),TRUE,1);
INSERT INTO articulo(idsucursal, idusuario, descripcion, codigobarra,
            idfamilia, idmarca, codpais, codmoneda, idunidadmedida, idtipoimpuesto,
            fechalog, fechaestado, estado, idlineaarticulo)
VALUES (1, 0, 'Teclado USB con luces', '000129', 1, 1, NULL, 1, 1, 10, now(),now(),TRUE,1);
INSERT INTO articulo(idsucursal, idusuario, descripcion, codigobarra,
            idfamilia, idmarca, codpais, codmoneda, idunidadmedida, idtipoimpuesto,
            fechalog, fechaestado, estado, idlineaarticulo)
VALUES (1, 0, 'Parlante USB', '000130', 1, 1, NULL, 1, 1, 10, now(),now(),TRUE,1);
