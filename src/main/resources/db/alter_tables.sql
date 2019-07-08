--29/11
alter table facturacab add column cotizacion double precision;
alter table facturacab add column clasefactura character varying(1) NOT NULL DEFAULT 'F'::character varying;

--19/11 Eliminar la tabla facturadet y correr este script
CREATE TABLE facturadet
(
  idfacturadet serial NOT NULL,
  idfacturacab integer,
  concepto character varying(100),
  cantidad numeric(21,6) DEFAULT 1,
  exenta numeric(21,6) DEFAULT 0,
  gravada5 numeric(21,6) DEFAULT 0,
  gravada10 numeric(21,6) DEFAULT 0,
  iva5 numeric(21,6) DEFAULT 0,
  iva10 numeric(21,6) DEFAULT 0,
  idplancuenta integer,
  precioventa numeric(21,6),
  total numeric(21,6),
  descuento numeric(21,6),
  preciocosto numeric(21,6),
  idarticulo integer,
  idplancuentaiva integer,
  idarticulodeposito integer,
  CONSTRAINT pk_facturadet PRIMARY KEY (idfacturadet),
  CONSTRAINT fk_facturadet_artdep FOREIGN KEY (idarticulodeposito)
      REFERENCES articulodeposito (idarticulodeposito) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_facturadet_articulo FOREIGN KEY (idarticulo)
      REFERENCES articulo (idarticulo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_facturadet_facturacab FOREIGN KEY (idfacturacab)
      REFERENCES facturacab (idfacturacab) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_facturadet_plancuenta FOREIGN KEY (idplancuenta)
      REFERENCES plancuenta (idplancuenta) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT
)

-- cambiar el owner a perseo
ALTER TABLE facturadet
  OWNER TO perseo;


--14/11
alter table banco add column  idempresa integer;
alter table caja add column  idempresa integer;
--13/11
alter table cliente add column  idarticuloprecioventacab integer;

--12/11
alter table conceptomov add column  tipo character varying(1);


alter table ordenpagocab add column retencion boolean NOT NULL;
alter table ordenpagocab add column importeretenido numeric(21,6) NOT NULL;

alter table compracab add column observaciones text;
alter table compradet add column tipoprovision text;
