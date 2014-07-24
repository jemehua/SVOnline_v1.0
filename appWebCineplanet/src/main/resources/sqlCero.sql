drop table if exists "tipodocumento" cascade;
drop table if exists "entrada" cascade;
drop table if exists "task" cascade;
drop table if exists "tipoentrada" cascade;
drop table if exists "agencia" cascade;
drop table if exists "detalleventa" cascade;
drop table if exists "cliente" cascade;
drop table if exists "rol" cascade;
drop table if exists "menu" cascade;
drop table if exists "movimiento" cascade;
drop table if exists "venta" cascade;
drop table if exists "permiso" cascade;
drop table if exists "itemmenu" cascade;
drop table if exists "usuario" cascade;
drop table if exists "detalleentrada" cascade;


--eliminar pkey  
ALTER TABLE cliente
  DROP CONSTRAINT cliente_pkey CASCADE

--
ALTER TABLE venta DROP COLUMN idcliente;

ALTER TABLE venta ADD idcliente bigint;

ALTER TABLE cliente ADD idcliente bigint;

UPDATE cliente SET idcliente=1 WHERE nrodocumento='42591173';
UPDATE cliente SET idcliente=2 WHERE nrodocumento='42591172';

UPDATE venta SET idcliente=1 WHERE idventa=1;
UPDATE venta SET idcliente=2 WHERE idventa=2;

ALTER TABLE cliente
  ADD CONSTRAINT cliente_pkey PRIMARY KEY(idcliente);

ALTER TABLE venta
  ADD CONSTRAINT fk_venta_cliente FOREIGN KEY (idcliente)
      REFERENCES cliente (idcliente) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE cliente ADD COLUMN apellidos character varying(200);

alter table tipoentrada add column precio type numeric(19,2); 

--SELECT 'DROP TABLE '||table_name||' CASCADE CONSTRAINTS;' FROM user_tables

INSERT INTO MENU (IDMENU, ESTADO, NOMBRE) VALUES ('1', 'A', 'Administración');
INSERT INTO MENU (IDMENU, ESTADO, NOMBRE) VALUES ('2', 'A', 'Operación');

--administracion
--INSERT INTO ITEMMENU (IDITEMMENU, ESTADO, NOMBRE, URLXHTML, IDMENU) VALUES ('1', 'A', 'Usuario', 'contents/usuario.xhtml', '1');
INSERT INTO ITEMMENU (IDITEMMENU, ESTADO, NOMBRE, URLXHTML, IDMENU) VALUES ('2', 'A', 'Agencia', 'contents/agencia.xhtml', '1');
INSERT INTO ITEMMENU (IDITEMMENU, ESTADO, NOMBRE, URLXHTML, IDMENU) VALUES ('4', 'A', 'Cliente', 'contents/cliente.xhtml', '1');
INSERT INTO ITEMMENU (IDITEMMENU, ESTADO, NOMBRE, URLXHTML, IDMENU) VALUES ('5', 'A', 'Entradas', 'contents/entradas.xhtml', '1');

--opreaciones
INSERT INTO ITEMMENU (IDITEMMENU, ESTADO, NOMBRE, URLXHTML, IDMENU) VALUES ('3', 'A', 'Ventas', 'contents/ventas.xhtml', '2');

--roles
INSERT INTO ROL (IDROL, ESTADO, NOMBRE) VALUES ('1', 'A', 'Administrador');

--administrador
--INSERT INTO PERMISO (IDPERMISO, ESTADO, IDITEMMENU, IDROL) VALUES ('1', '1', '1', '1');
INSERT INTO PERMISO (IDPERMISO, ESTADO, IDITEMMENU, IDROL) VALUES ('2', '1', '2', '1');
INSERT INTO PERMISO (IDPERMISO, ESTADO, IDITEMMENU, IDROL) VALUES ('4', '1', '4', '1');
INSERT INTO PERMISO (IDPERMISO, ESTADO, IDITEMMENU, IDROL) VALUES ('5', '1', '5', '1');

INSERT INTO PERMISO (IDPERMISO, ESTADO, IDITEMMENU, IDROL) VALUES ('3', '1', '3', '1');

--usuario
INSERT INTO USUARIO (IDUSUARIO, APMATERNO, APPATERNO, ESTADO, NOMBRE, USUARIO, IDROL, CLAVE) VALUES ('1', 'Salazar', 'Salinas', 'A', 'Laura', 'admin', '1', 'admin');

--tipo de entrada
INSERT INTO tipoentrada(idtipoentrada, descripcion, estado, nombre, tipovale) VALUES (1, 'Toda la cadena - 3D', 'A', 'ENTRADAS S/.17.00', 1);
INSERT INTO tipoentrada(idtipoentrada, descripcion, estado, nombre, tipovale) VALUES (2, 'Toda la cadena', 'A', 'ENTRADAS S/.10.00', 1);
INSERT INTO tipoentrada(idtipoentrada, descripcion, estado, nombre, tipovale) VALUES (3, 'Toda la cadena con excepción de Alcázar,LaMolina,Salaverry,SanBorja', 'A', 'ENTRADAS S/.9.00', 1);
INSERT INTO tipoentrada(idtipoentrada, descripcion, estado, nombre, tipovale) VALUES (4, 'Toda la cadena con excepción de Alcázar,LaMolina,Salaverry,SanBorja,Primavera,SanMiguel', 'A', 'ENTRADAS S/.7.00', 1);

INSERT INTO tipoentrada(idtipoentrada, descripcion, estado, nombre, tipovale) VALUES (5, 'Toda la cadena (01 pop corn gigante +02 bebidas medianas)', 'A', 'COMBO GIGANTE', 2);
INSERT INTO tipoentrada(idtipoentrada, descripcion, estado, nombre, tipovale) VALUES (6, 'Toda la cadena (01 pop corn grande +02 bebidas medianas)', 'A', 'COMBO GRANDE', 2);
INSERT INTO tipoentrada(idtipoentrada, descripcion, estado, nombre, tipovale) VALUES (7, 'Toda la cadena (01 Hotdog Jumbo+01 bebida mediana)', 'A', 'COMBO MEDIANO', 2);
INSERT INTO tipoentrada(idtipoentrada, descripcion, estado, nombre, tipovale) VALUES (8, 'Toda la cadena (01 pop corn mediano+01 bebida mediana)', 'A', 'COMBO MEDIANO 1', 2);
--tipo de documento
INSERT INTO tipodocumento(idtipodocumento, estado, nombre) VALUES (1, 'A', 'Boleta');
INSERT INTO tipodocumento(idtipodocumento, estado, nombre) VALUES (2, 'A', 'Factura');

