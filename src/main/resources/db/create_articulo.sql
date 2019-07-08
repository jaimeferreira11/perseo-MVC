-- Crear tabla de deposito
CREATE TABLE deposito
(
  iddeposito serial NOT NULL,
  descripcion text,
  estado boolean NOT NULL DEFAULT true,
  idsucursal integer NOT NULL,
  idempresa integer NOT NULL,
  fechalog timestamp without time zone,
  idusuariolog integer,
  CONSTRAINT deposito_pkey PRIMARY KEY (iddeposito),
  CONSTRAINT deposito_idempresa_fkey FOREIGN KEY (idempresa)
      REFERENCES empresa (idempresa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT deposito_idsucursal_fkey FOREIGN KEY (idsucursal)
      REFERENCES sucursal (idsucursal) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Crear tabla articulo
CREATE TABLE articulo
(
  idarticulo serial NOT NULL,
  descripcion text NOT NULL,
  stock double precision NOT NULL DEFAULT 0,
  idtipoimpuesto integer NOT NULL,
  estado boolean DEFAULT true,
  idempresa integer NOT NULL,
  CONSTRAINT articulos_pkey PRIMARY KEY (idarticulo),
  CONSTRAINT articulos_idempresa_fkey FOREIGN KEY (idempresa)
      REFERENCES empresa (idempresa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT articulos_idtipoimpuesto_fkey FOREIGN KEY (idtipoimpuesto)
      REFERENCES tipoimpuesto (idtipoimpuesto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

--Crear articulo sucursal
CREATE TABLE articulosucursal
(
  idarticulosucursal serial NOT NULL,
  idsucursal integer NOT NULL,
  idarticulo integer NOT NULL,
  stock double precision NOT NULL DEFAULT 0,
  nroalta integer,
  nrobaja integer,
  nrotransferencia integer,
  preciocosto double precision NOT NULL DEFAULT 0,
  idempresa integer NOT NULL,
  CONSTRAINT articulosucursal_pkey PRIMARY KEY (idarticulosucursal),
  CONSTRAINT articulosucursal_idarticulo_fkey FOREIGN KEY (idarticulo)
      REFERENCES articulo (idarticulo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT articulosucursal_idempresa_fkey FOREIGN KEY (idempresa)
      REFERENCES empresa (idempresa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT articulosucursal_idsucursal_fkey FOREIGN KEY (idsucursal)
      REFERENCES sucursal (idsucursal) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

--Crear Tabla articulopreciocab
CREATE TABLE articuloprecioventacab
(
  idarticuloprecioventacab serial NOT NULL,
  descripcion text,
  estado boolean,
  idempresa integer,
  CONSTRAINT articuloprecioventacab_pkey PRIMARY KEY (idarticuloprecioventacab),
  CONSTRAINT articuloprecioventacab_idempresa_fkey FOREIGN KEY (idempresa)
      REFERENCES empresa (idempresa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

--Crear tabla articulopreciodet
CREATE TABLE articuloprecioventadet
(
  idarticuloprecioventadet serial NOT NULL,
  idarticuloprecioventacab integer NOT NULL,
  idarticulo integer NOT NULL,
  precio double precision,
  CONSTRAINT articuloprecioventadet_pkey PRIMARY KEY (idarticuloprecioventadet),
  CONSTRAINT articuloprecioventadet_idarticulo_fkey FOREIGN KEY (idarticulo)
      REFERENCES articulo (idarticulo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT articuloprecioventadet_idarticuloprecioventacab_fkey FOREIGN KEY (idarticuloprecioventacab)
      REFERENCES articuloprecioventacab (idarticuloprecioventacab) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE tipobajaarticulo
(
  idtipobajaarticulo serial NOT NULL,
  descripcion text,
  estado boolean,
  CONSTRAINT tipobajaarticulo_pkey PRIMARY KEY (idtipobajaarticulo)
);

CREATE TABLE tipomovimiento
(
  idtipomovimiento serial NOT NULL,
  descripcion text,
  estado boolean,
  CONSTRAINT tipomovimiento_pkey PRIMARY KEY (idtipomovimiento)
);

CREATE TABLE articulomovimiento
(
  idarticulomovimiento serial NOT NULL,
  idarticulo integer NOT NULL,
  idsucursal integer NOT NULL,
  entrada double precision,
  salida double precision,
  tipo character(1),
  fecha date,
  iddeposito integer NOT NULL,
  nroalta integer,
  nrotransferencia integer,
  nrobaja integer,
  idtipobajaarticulo integer,
  idempresa integer,
  CONSTRAINT articulomovimiento_pkey PRIMARY KEY (idarticulomovimiento),
  CONSTRAINT articulomovimiento_idarticulo_fkey FOREIGN KEY (idarticulo)
      REFERENCES articulo (idarticulo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT articulomovimiento_iddeposito_fkey FOREIGN KEY (iddeposito)
      REFERENCES deposito (iddeposito) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT articulomovimiento_idempresa_fkey FOREIGN KEY (idempresa)
      REFERENCES empresa (idempresa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT articulomovimiento_idsucursal_fkey FOREIGN KEY (idsucursal)
      REFERENCES sucursal (idsucursal) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT articulomovimiento_idtipobajaarticulo_fkey FOREIGN KEY (idtipobajaarticulo)
      REFERENCES tipobajaarticulo (idtipobajaarticulo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE pedidocab
(
  idpedidocab serial NOT NULL,
  idcliente integer,
  fecha date,
  hora timestamp without time zone,
  idsucursal integer,
  idusuario integer,
  horaentrega timestamp without time zone,
  idusuarioentrega integer,
  estado character varying,
  idempresa integer,
  CONSTRAINT pedidocab_pkey PRIMARY KEY (idpedidocab),
  CONSTRAINT pedidocab_idcliente_fkey FOREIGN KEY (idcliente)
      REFERENCES cliente (idcliente) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT pedidocab_idempresa_fkey FOREIGN KEY (idempresa)
      REFERENCES empresa (idempresa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT pedidocab_idsucursal_fkey FOREIGN KEY (idsucursal)
      REFERENCES sucursal (idsucursal) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT pedidocab_idusuario_fkey FOREIGN KEY (idusuario)
      REFERENCES usuario (idusuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT pedidocab_idusuarioentrega_fkey FOREIGN KEY (idusuarioentrega)
      REFERENCES usuario (idusuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE pedidodet
(
  idpedidodet serial NOT NULL,
  idpedidocab integer,
  idarticulo integer,
  cantidad integer,
  CONSTRAINT pedidodet_pkey PRIMARY KEY (idpedidodet),
  CONSTRAINT pedidodet_idpedidocab_fkey FOREIGN KEY (idpedidocab)
      REFERENCES pedidocab (idpedidocab) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE notacreditocab
(
  idnotacreditocab serial NOT NULL,
  fecha date,
  nronotacredito text,
  idcliente integer NOT NULL,
  idfacturacab integer,
  idsucursal integer,
  idusuario integer,
  estado boolean,
  timbrado integer,
  total double precision,
  codmoneda integer,
  idempresa integer,
  totaleq double precision,
  tipocambio double precision,
  CONSTRAINT notacreditocab_pkey PRIMARY KEY (idnotacreditocab),
  CONSTRAINT notacreditocab_idcliente_fkey FOREIGN KEY (idcliente)
      REFERENCES cliente (idcliente) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT notacreditocab_idempresa_fkey FOREIGN KEY (idempresa)
      REFERENCES empresa (idempresa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT notacreditocab_idfacturacab_fkey FOREIGN KEY (idfacturacab)
      REFERENCES facturacab (idfacturacab) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT notacreditocab_idmoneda_fkey FOREIGN KEY (codmoneda)
      REFERENCES moneda (codmoneda) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT notacreditocab_idsucursal_fkey FOREIGN KEY (idsucursal)
      REFERENCES sucursal (idsucursal) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT notacreditocab_idusuario_fkey FOREIGN KEY (idusuario)
      REFERENCES usuario (idusuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE notacreditodet
(
  idnotacreditodet serial NOT NULL,
  idnotacreditocab integer,
  monto double precision,
  iva double precision,
  idtipoimpuesto double precision,
  idarticulo integer,
  descripcion text,
  cantidad double precision,
  montoeq double precision,
  ivaeq double precision,
  CONSTRAINT notacreditodet_pkey PRIMARY KEY (idnotacreditodet),
  CONSTRAINT notacreditodet_idnotacreditocab_fkey FOREIGN KEY (idnotacreditocab)
      REFERENCES notacreditocab (idnotacreditocab) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);