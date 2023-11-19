/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     10/07/2023 02:01:36 p. m.                    */
/*==============================================================*/


-- Tabla ASIGNACION_PROPIETARIO
ALTER TABLE ASIGNACION_PROPIETARIO
   DROP CONSTRAINT IF EXISTS FK_ASIG_PROPIETARIO;

ALTER TABLE ASIGNACION_PROPIETARIO
   DROP CONSTRAINT IF EXISTS FK_ASIG_PROPIETARIO_VENTA;

-- Tabla ASIGNACION_VISITANTE
ALTER TABLE ASIGNACION_VISITANTE
   DROP CONSTRAINT IF EXISTS FK_ASIG_VISITANTE;

ALTER TABLE ASIGNACION_VISITANTE
   DROP CONSTRAINT IF EXISTS FK_ASIG_VISITANTE_VENTA;

-- Tabla ASIGNACION_VISITA
ALTER TABLE ASIGNACION_VISITA
   DROP CONSTRAINT IF EXISTS FK_ASIG_VISITA_VENTA;

ALTER TABLE ASIGNACION_VISITA
   DROP CONSTRAINT IF EXISTS FK_ASIG_VISITA_VISITA;

ALTER TABLE ASIGNACION_VISITA
   DROP CONSTRAINT IF EXISTS FK_ASIG_VISITA_PROPIETARIO;

ALTER TABLE ASIGNACION_VISITA
   DROP CONSTRAINT IF EXISTS FK_ASIG_VISITA_VISITANTE;

-- Tabla ASIGNACION_PROYECTO
ALTER TABLE ASIGNACION_PROYECTO
   DROP CONSTRAINT IF EXISTS FK_ASIG_PROYECTO;

ALTER TABLE ASIGNACION_PROYECTO
   DROP CONSTRAINT IF EXISTS FK_ASIG_PROYECTO_USUARIO;

-- Tabla ASIGNACION_EMPRESA
ALTER TABLE ASIGNACION_EMPRESA
   DROP CONSTRAINT IF EXISTS FK_ASIG_EMPRESA;

ALTER TABLE ASIGNACION_EMPRESA
   DROP CONSTRAINT IF EXISTS FK_ASIG_EMPRESA_USUARIO;

-- Tabla CORREO
ALTER TABLE CORREO
   DROP CONSTRAINT IF EXISTS FK_CORREO_PROPIETARIO;

-- Tabla CUENTA_BANCARIA
ALTER TABLE CUENTA_BANCARIA
   DROP CONSTRAINT IF EXISTS FK_EMPRESA_CUENTA_BANCARIA;

-- Tabla CUOTA_AMORTIZACION
ALTER TABLE CUOTA_AMORTIZACION
   DROP CONSTRAINT IF EXISTS FK_AMORTIZACION_INFO;

-- Tabla CUOTA_FINANCIAMIENTO
ALTER TABLE CUOTA_FINANCIAMIENTO
   DROP CONSTRAINT IF EXISTS FK_FINANCIAMIENTO_PAGO;

ALTER TABLE CUOTA_FINANCIAMIENTO
   DROP CONSTRAINT IF EXISTS FK_FINANCIAMIENTO_INFO;

-- Tabla CUOTA_MANTENIMIENTO
ALTER TABLE CUOTA_MANTENIMIENTO
   DROP CONSTRAINT IF EXISTS FK_MANTENIMIENTO_PAGO;

-- Tabla DOCUMENTO
ALTER TABLE DOCUMENTO
   DROP CONSTRAINT IF EXISTS FK_LIST_DOCUMENT_DOCUMENTO;

-- Tabla FACTURACION
ALTER TABLE FACTURACION
   DROP CONSTRAINT IF EXISTS FK_VENTA_FACTURACION;

-- Tabla INFO_FINANCIAMIENTO
ALTER TABLE INFO_FINANCIAMIENTO
   DROP CONSTRAINT IF EXISTS FK_VENTA_INFO_FINANCIAMIENTO;

-- Tabla INFO_MANTENIMIENTO
ALTER TABLE INFO_MANTENIMIENTO
   DROP CONSTRAINT IF EXISTS FK_VENTA_INFO_MANTENIMIENTO;

-- Tabla PAGO
ALTER TABLE PAGO
   DROP CONSTRAINT IF EXISTS FK_PAGO_CUENTA_BANCARIA;

ALTER TABLE PAGO
   DROP CONSTRAINT IF EXISTS FK_PAGO_VENTA;

-- Tabla PERSONA
ALTER TABLE PERSONA
   DROP CONSTRAINT IF EXISTS FK_PERSONA_TIPO_DOCUMENTO;

-- Tabla PROPIETARIO
ALTER TABLE PROPIETARIO
   DROP CONSTRAINT IF EXISTS FK_PROPIETARIO_PERSONA;

-- Tabla PROYECTO
ALTER TABLE PROYECTO
   DROP CONSTRAINT IF EXISTS FK_EMPRESA_PROYECTO;

-- Tabla REFERENCIA
ALTER TABLE REFERENCIA
   DROP CONSTRAINT IF EXISTS FK_REFERENCIA_PROPIETARIO;

-- Tabla ROL_PERMISO
ALTER TABLE ROL_PERMISO
   DROP CONSTRAINT IF EXISTS FK_ROL_PERMISO;

ALTER TABLE ROL_PERMISO
   DROP CONSTRAINT IF EXISTS FK_ROL_PERMISO_2;

-- Tabla TELEFONO
ALTER TABLE TELEFONO
   DROP CONSTRAINT IF EXISTS FK_TELEFONO_PROPIETARIO;

-- Tabla TERRENO
ALTER TABLE TERRENO
   DROP CONSTRAINT IF EXISTS FK_TERRENO_PROYECTO;

-- Tabla USUARIO_ROL
ALTER TABLE USUARIO_ROL
   DROP CONSTRAINT IF EXISTS FK_USUARIO_ROL;

ALTER TABLE USUARIO_ROL
   DROP CONSTRAINT IF EXISTS FK_USUARIO_ROL_2;

-- Tabla VEHICULO
ALTER TABLE VEHICULO
   DROP CONSTRAINT IF EXISTS FK_VEHICULO_VISITA;

-- Tabla VENTA
ALTER TABLE VENTA
   DROP CONSTRAINT IF EXISTS FK_VENTA_TERRENO;

ALTER TABLE VENTA
   DROP CONSTRAINT IF EXISTS FK_VENTA_LIST_DOCUMENT;

-- Tabla VISITANTE
ALTER TABLE VISITANTE
   DROP CONSTRAINT IF EXISTS FK_VISITANTE_LIST_DOCUMENTO;

ALTER TABLE VISITANTE
   DROP CONSTRAINT IF EXISTS FK_VISITANTE_PERSONA;

-- Eliminar índices
DROP INDEX IF EXISTS ASIG_PROPIETARIO_FK;
DROP INDEX IF EXISTS ASIG_PROPIETARIO_VENTA_FK;
DROP INDEX IF EXISTS ASIG_VISITANTE_FK;
DROP INDEX IF EXISTS ASIG_VISITANTE_VENTA_FK;
DROP INDEX IF EXISTS ASIG_VISITA_VENTA_FK;
DROP INDEX IF EXISTS ASIG_VISITA_VISITA_FK;
DROP INDEX IF EXISTS ASIG_VISITA_PROPIETARIO_FK;
DROP INDEX IF EXISTS ASIG_VISITA_VISITANTE_FK;
DROP INDEX IF EXISTS ASIG_PROYECTO_FK;
DROP INDEX IF EXISTS ASIG_PROYECTO_USUARIO_FK;
DROP INDEX IF EXISTS ASIG_EMPRESA_FK;
DROP INDEX IF EXISTS ASIG_EMPRESA_USUARIO_FK;
DROP INDEX IF EXISTS PROPIETARIO_CORREO_FK;
DROP INDEX IF EXISTS EMPRESA_CUENTA_BANCARIA_FK;
DROP INDEX IF EXISTS VENTA_FACTURACION_FK;
DROP INDEX IF EXISTS VENTA_INFO_FINANCIAMIENTO_FK;
DROP INDEX IF EXISTS VENTA_INFO_MANTENIMIENTO_FK;
DROP INDEX IF EXISTS INFO_AMORTIZACION_FK;
DROP INDEX IF EXISTS PAGO_FINANCIAMIENTO_FK;
DROP INDEX IF EXISTS INFO_FINANCIAMIENTO_FK;
DROP INDEX IF EXISTS PAGO_MANTENIMIENTO_FK;
DROP INDEX IF EXISTS LIST_DOCUMENT_DOCUMENTO_FK;
DROP INDEX IF EXISTS VENTA_PAGO_FK;
DROP INDEX IF EXISTS PAGO_CUENTA_BANCARIA_FK;
DROP INDEX IF EXISTS PERSONA_PROPIETARIO_FK;
DROP INDEX IF EXISTS TIPO_DOCUMENTO_PERSONA_FK;
DROP INDEX IF EXISTS PROPIETARIO_LIST_DOCUMENTO_FK;
DROP INDEX IF EXISTS PROYECTO_EMPRESA_FK;
DROP INDEX IF EXISTS PROPIETARIO_REFERENCIA_FK;
DROP INDEX IF EXISTS PROPIETARIO_TELEFONO_FK;
DROP INDEX IF EXISTS TERRENO_PROYECTO_FK;
DROP INDEX IF EXISTS USUARIO_ROL2_FK;
DROP INDEX IF EXISTS USUARIO_ROL_FK;
DROP INDEX IF EXISTS VEHICULO_VISITA_FK;
DROP INDEX IF EXISTS VENTA_LIST_DOCUMENTO_FK;
DROP INDEX IF EXISTS TERRENO_VENTA_FK;
DROP INDEX IF EXISTS VISITANTE_LIST_DOCUMENT_FK;
DROP INDEX IF EXISTS VISITANTE_PERSONA_FK;

-- Eliminar tablas con restricciones
DROP TABLE IF EXISTS ASIGNACION_PROPIETARIO CASCADE;
DROP TABLE IF EXISTS ASIGNACION_VISITANTE CASCADE;
DROP TABLE IF EXISTS ASIGNACION_VISITA CASCADE;
DROP TABLE IF EXISTS ASIGNACION_PROYECTO CASCADE;
DROP TABLE IF EXISTS ASIGNACION_EMPRESA CASCADE;
DROP TABLE IF EXISTS BITACORA CASCADE;
DROP TABLE IF EXISTS CORREO CASCADE;
DROP TABLE IF EXISTS CUENTA_BANCARIA CASCADE;
DROP TABLE IF EXISTS EMPRESA CASCADE;
DROP TABLE IF EXISTS FACTURACION CASCADE;
DROP TABLE IF EXISTS INFO_FINANCIAMIENTO CASCADE;
DROP TABLE IF EXISTS INFO_MANTENIMIENTO CASCADE;
DROP TABLE IF EXISTS CUOTA_AMORTIZACION CASCADE;
DROP TABLE IF EXISTS CUOTA_FINANCIAMIENTO CASCADE;
DROP TABLE IF EXISTS CUOTA_MANTENIMIENTO CASCADE;
DROP TABLE IF EXISTS DOCUMENTO CASCADE;
DROP TABLE IF EXISTS LISTADO_DOCUMENTO CASCADE;
DROP TABLE IF EXISTS PAGO CASCADE;
DROP TABLE IF EXISTS PERMISO CASCADE;
DROP TABLE IF EXISTS PERSONA CASCADE;
DROP TABLE IF EXISTS TIPO_DOCUMENTO CASCADE;
DROP TABLE IF EXISTS PROPIETARIO CASCADE;
DROP TABLE IF EXISTS PROYECTO CASCADE;
DROP TABLE IF EXISTS REFERENCIA CASCADE;
DROP TABLE IF EXISTS ROL CASCADE;
DROP TABLE IF EXISTS ROL_PERMISO CASCADE;
DROP TABLE IF EXISTS TELEFONO CASCADE;
DROP TABLE IF EXISTS TERRENO CASCADE;
DROP TABLE IF EXISTS USUARIO CASCADE;
DROP TABLE IF EXISTS USUARIO_ROL CASCADE;
DROP TABLE IF EXISTS VEHICULO CASCADE;
DROP TABLE IF EXISTS VENTA CASCADE;
DROP TABLE IF EXISTS VISITA CASCADE;
DROP TABLE IF EXISTS VISITANTE CASCADE;
DROP TABLE IF EXISTS CONFIGURACION_CORREO CASCADE;
DROP TABLE IF EXISTS RESET_PASSWORD CASCADE;

-- Definición de tabla ASIGNACION_PROPIETARIO
CREATE TABLE ASIGNACION_PROPIETARIO
(
   ID_ASIG_PROPIETARIO  SERIAL PRIMARY KEY,
   ID_PROPIETARIO       INTEGER,
   ID_VENTA             INTEGER,
   ESTADO               VARCHAR(15)
);
-- Índice ASIG_PROPIETARIO_VENTA_FK
CREATE INDEX ASIG_PROPIETARIO_VENTA_FK ON ASIGNACION_PROPIETARIO (ID_VENTA);
-- Índice ASIG_PROPIETARIO_FK
CREATE INDEX ASIG_PROPIETARIO_FK ON ASIGNACION_PROPIETARIO (ID_PROPIETARIO);


-- Definición de tabla ASIGNACION_VISITANTE
CREATE TABLE ASIGNACION_VISITANTE
(
   ID_ASIG_VISITANTE  SERIAL PRIMARY KEY,
   ID_VISITANTE       INTEGER,
   ID_VENTA           INTEGER
);

-- Índice ASIG_VISITANTE_VENTA_FK
CREATE INDEX ASIG_VISITANTE_VENTA_FK ON ASIGNACION_VISITANTE (ID_VENTA);

-- Índice ASIG_VISITANTE_FK
CREATE INDEX ASIG_VISITANTE_FK ON ASIGNACION_VISITANTE (ID_VISITANTE);

-- Definición de tabla ASIGNACION_VISITA
CREATE TABLE ASIGNACION_VISITA
(
   ID_ASIG_VISITA    SERIAL PRIMARY KEY,
   REPRESENTANTE     INTEGER,
   ID_VENTA          INTEGER,
   ID_VISITA         INTEGER,
   ID_PROPIETARIO    INTEGER,
   ID_VISITANTE      INTEGER
);
-- Índice ASIG_VISITA_VENTA_FK
CREATE INDEX ASIG_VISITA_VENTA_FK ON ASIGNACION_VISITA (ID_VENTA);
-- Índice ASIG_VISITA_VISITA_FK
CREATE INDEX ASIG_VISITA_VISITA_FK ON ASIGNACION_VISITA (ID_VISITA);
-- Índice ASIG_VISITA_PROPIETARIO_FK
CREATE INDEX ASIG_VISITA_PROPIETARIO_FK ON ASIGNACION_VISITA (ID_PROPIETARIO);
-- Índice ASIG_VISITA_VISITANTE_FK
CREATE INDEX ASIG_VISITA_VISITANTE_FK ON ASIGNACION_VISITA (ID_VISITANTE);

-- Definición de tabla ASIGNACION_PROYECTO
CREATE TABLE ASIGNACION_PROYECTO
(
   ID_PROYECTO  INTEGER,
   ID_USUARIO   INTEGER,
   PRIMARY KEY (ID_USUARIO, ID_PROYECTO)
);
-- Índice ASIG_PROYECTO_USUARIO_FK
CREATE INDEX ASIG_PROYECTO_USUARIO_FK ON ASIGNACION_PROYECTO (ID_USUARIO);
-- Índice ASIG_PROYECTO_FK
CREATE INDEX ASIG_PROYECTO_FK ON ASIGNACION_PROYECTO (ID_PROYECTO);

-- Definición de tabla ASIGNACION_EMPRESA
CREATE TABLE ASIGNACION_EMPRESA
(
   ID_EMPRESA  INTEGER,
   ID_USUARIO  INTEGER,
   PRIMARY KEY (ID_USUARIO, ID_EMPRESA)
);
-- Índice ASIG_EMPRESA_USUARIO_FK
CREATE INDEX ASIG_EMPRESA_USUARIO_FK ON ASIGNACION_EMPRESA (ID_USUARIO);
-- Índice ASIG_EMPRESA_FK
CREATE INDEX ASIG_EMPRESA_FK ON ASIGNACION_EMPRESA (ID_EMPRESA);

-- Definición de tabla BITACORA
CREATE TABLE BITACORA
(
   ID_BITACORA SERIAL PRIMARY KEY,
   USERNAME VARCHAR(50),
   EVENTO VARCHAR(100),
   HORA TIMESTAMP,
   IP_EQUIPO VARCHAR(15)
);

-- Definición de tabla CORREO
CREATE TABLE CORREO
(
   ID_CORREO SERIAL PRIMARY KEY,
   ID_PROPIETARIO INTEGER,
   TIPO VARCHAR(20),
   CORREO VARCHAR(150)
);
-- Índice PROPIETARIO_CORREO_FK
CREATE INDEX PROPIETARIO_CORREO_FK ON CORREO (ID_PROPIETARIO);

-- Definición de tabla CUENTA_BANCARIA
CREATE TABLE CUENTA_BANCARIA
(
   ID_CUENTA_BANCARIA SERIAL PRIMARY KEY,
   ID_EMPRESA INTEGER,
   NOMBRE VARCHAR(200),
   TITULAR VARCHAR(200),
   BANCO VARCHAR(200),
   TIPO VARCHAR(20),
   CUENTA VARCHAR(20)
);
-- Índice PROYECTO_CUENTA_BANCARIA_FK
CREATE INDEX EMPRESA_CUENTA_BANCARIA_FK ON CUENTA_BANCARIA (ID_EMPRESA);

-- Definición de tabla CUOTA_AMORTIZACION
CREATE TABLE CUOTA_AMORTIZACION
(
   ID_CUOTA_AMORTIZACION SERIAL PRIMARY KEY,
   ID_INFO_FINANCIAMIENTO INTEGER,
   FECHA_CUOTA DATE,
   MONTO NUMERIC(6,2),
   DIAS_INTERES INTEGER,
   INTERES NUMERIC(6,2),
   COMISION NUMERIC(6,2),
   CAPITAL NUMERIC(6,2),
   SALDO NUMERIC(6,2)
);
-- Índice INFO_AMORTIZACION_FK
CREATE INDEX INFO_AMORTIZACION_FK ON CUOTA_AMORTIZACION (ID_INFO_FINANCIAMIENTO);

-- Definición de tabla CUOTA_FINANCIAMIENTO
CREATE TABLE CUOTA_FINANCIAMIENTO
(
   ID_CUOTA_FINANCIAMIENTO SERIAL PRIMARY KEY,
   ID_PAGO INTEGER,
   ID_INFO_FINANCIAMIENTO INTEGER,
   FECHA_REGISTRO TIMESTAMP,
   FECHA_CUOTA DATE,
   DIAS_INTERES_CORRIENTE INTEGER,
   INTERES_CORRIENTE NUMERIC(6,2),
   DIAS_INTERES_MORA INTEGER,
   INTERES_MORA NUMERIC(6,2),
   PAGADO_INTERES NUMERIC(6,2),
   PENDIENTE_INTERES NUMERIC(6,2),
   COMISION NUMERIC(6,2),
   RECARGO NUMERIC(6,2),
   OTROS NUMERIC(6,2),
   PAGADO_OTROS NUMERIC(6,2),
   PENDIENTE_OTROS NUMERIC(6,2),
   CAPITAL NUMERIC(6,2),
   SALDO NUMERIC(6,2)
);
-- Índice PAGO_FINANCIAMIENTO_FK
CREATE INDEX PAGO_FINANCIAMIENTO_FK ON CUOTA_FINANCIAMIENTO (ID_PAGO);

CREATE INDEX INFO_FINANCIAMIENTO_FK ON CUOTA_FINANCIAMIENTO (ID_INFO_FINANCIAMIENTO);

-- Definición de tabla CUOTA_MANTENIMIENTO
CREATE TABLE CUOTA_MANTENIMIENTO
(
   ID_CUOTA_MANTENIMIENTO SERIAL PRIMARY KEY,
   ID_PAGO INTEGER,
   FECHA_REGISTRO TIMESTAMP,
   FECHA_CUOTA DATE,
   CUOTA NUMERIC(9,2),
   SALDO_CUOTA NUMERIC(9,2),
   RECARGO NUMERIC(9,2),
   SALDO_RECARGO NUMERIC(9,2),
   DESCUENTO NUMERIC(9,2),
   OTROS NUMERIC(9,2)
);
-- Índice PAGO_MANTENIMIENTO_FK
CREATE INDEX PAGO_MANTENIMIENTO_FK ON CUOTA_MANTENIMIENTO (ID_PAGO);

-- Definición de tabla DOCUMENTO
CREATE TABLE DOCUMENTO
(
   ID_DOCUMENTO SERIAL PRIMARY KEY,
   ID_LIST_DOCUMENTO INTEGER,
   NOMBRE VARCHAR(200),
   DOCUMENTO BYTEA
);
-- Índice LIST_DOCUMENT_DOCUMENTO_FK
CREATE INDEX LIST_DOCUMENT_DOCUMENTO_FK ON DOCUMENTO (ID_LIST_DOCUMENTO);

-- Definición de tabla EMPRESA
CREATE TABLE EMPRESA
(
   ID_EMPRESA SERIAL PRIMARY KEY,
   NOMBRE VARCHAR(200)
);

-- Definición de tabla FACTURACION
CREATE TABLE FACTURACION
(
   ID_FACTURACION SERIAL PRIMARY KEY,
   ID_VENTA INTEGER,
   NOMBRE VARCHAR(200),
   N_REGISTRO VARCHAR(12),
   NIT VARCHAR(14),
   DUI VARCHAR(9),
   DIRECCION VARCHAR(300),
   GIRO VARCHAR(200)
);
-- Índice VENTA_FACTURACION_FK
CREATE INDEX VENTA_FACTURACION_FK ON FACTURACION (ID_VENTA);

-- Definición de tabla INFO_FINANCIAMIENTO
CREATE TABLE INFO_FINANCIAMIENTO
(
   ID_INFO_FINANCIAMIENTO SERIAL PRIMARY KEY,
   ID_VENTA INTEGER,
   FECHA_APLICACION DATE,
   MONTO NUMERIC(9,2),
   PLAZO INTEGER,
   TASA NUMERIC(6,2),
   CUOTA_KI NUMERIC(9,2),
   MULTA_FINANCIAMIENTO NUMERIC(9,2)
);
-- Índice VENTA_INFO_FINANCIAMIENTO_FK
CREATE INDEX VENTA_INFO_FINANCIAMIENTO_FK ON INFO_FINANCIAMIENTO (ID_VENTA);

-- Definición de tabla INFO_MANTENIMIENTO
CREATE TABLE INFO_MANTENIMIENTO
(
   ID_INFO_MANTENIMIENTO SERIAL PRIMARY KEY,
   ID_VENTA INTEGER,
   FECHA_APLICACION DATE,
   CUOTA_MANTENIMIENTO NUMERIC(9,2),
   MULTA_MANTENIMIENTO NUMERIC(9,2)
);
-- Índice VENTA_INFO_MANTENIMIENTO_FK
CREATE INDEX VENTA_INFO_MANTENIMIENTO_FK ON INFO_MANTENIMIENTO (ID_VENTA);

-- Definición de tabla LISTADO_DOCUMENTO
CREATE TABLE LISTADO_DOCUMENTO
(
   ID_LIST_DOCUMENTO SERIAL PRIMARY KEY
);

-- Definición de tabla PAGO
CREATE TABLE PAGO
(
   ID_PAGO SERIAL PRIMARY KEY,
   ID_CUENTA_BANCARIA INTEGER,
   ID_VENTA INTEGER,
   FECHA_REGISTRO TIMESTAMP,
   FECHA DATE,
   RECIBO INTEGER,
   ESTADO BOOLEAN,
   COMPROBANTE VARCHAR(50),
   TIPO VARCHAR(20),
   MONTO NUMERIC(9,2),
   OTROS NUMERIC(9,2),
   DESCUENTO NUMERIC(9,2),
   OBSERVACIONES VARCHAR(500)
);
-- Índice PAGO_CUENTA_BANCARIA_FK
CREATE INDEX PAGO_CUENTA_BANCARIA_FK ON PAGO (ID_CUENTA_BANCARIA);
-- Índice VENTA_PAGO_FK
CREATE INDEX VENTA_PAGO_FK ON PAGO (ID_VENTA);

-- Definición de tabla PERMISO
CREATE TABLE PERMISO
(
   ID_PERMISO SERIAL PRIMARY KEY,
   NOMBRE VARCHAR(200)
);

-- Definición de tabla PERSONA
CREATE TABLE PERSONA
(
   ID_PERSONA SERIAL PRIMARY KEY,
   ID_TIPO_DOCUMENTO INTEGER,
   NUMERO VARCHAR(50),
   NOMBRE VARCHAR(200),
   APELLIDO VARCHAR(200)
);
-- Índice TIPO_DOCUMENTO_PERSONA_FK
CREATE INDEX TIPO_DOCUMENTO_PERSONA_FK ON PERSONA (ID_TIPO_DOCUMENTO);

-- Definición de tabla TIPO_DOCUMENTO
CREATE TABLE TIPO_DOCUMENTO
(
   ID_TIPO_DOCUMENTO SERIAL PRIMARY KEY,
   NOMBRE VARCHAR(200),
   MASCARA VARCHAR(500)
);

-- Definición de tabla PROPIETARIO
CREATE TABLE PROPIETARIO
(
   ID_PROPIETARIO SERIAL PRIMARY KEY,
   ID_PERSONA INTEGER,
   ID_LIST_DOCUMENTO INTEGER,
   PROFESION VARCHAR(200),
   DIRECCION_CASA VARCHAR(300),
   LUGAR_TRABAJO VARCHAR(200),
   DIRECCION_TRABAJO VARCHAR(300)
);
-- Índice PERSONA_PROPIETARIO_FK
CREATE INDEX PERSONA_PROPIETARIO_FK ON PROPIETARIO (ID_PERSONA);
-- Índice PROPIETARIO_LIST_DOCUMENTO_FK
CREATE INDEX PROPIETARIO_LIST_DOCUMENTO_FK ON PROPIETARIO (ID_LIST_DOCUMENTO);

-- Definición de tabla PROYECTO
CREATE TABLE PROYECTO
(
   ID_PROYECTO SERIAL PRIMARY KEY,
   ID_EMPRESA INTEGER,
   NOMBRE VARCHAR(200)
);
-- Índice PROYECTO_EMPRESA_FK
CREATE INDEX PROYECTO_EMPRESA_FK ON PROYECTO (ID_EMPRESA);

-- Definición de tabla REFERENCIA
CREATE TABLE REFERENCIA
(
   ID_REFERENCIA SERIAL PRIMARY KEY,
   ID_PROPIETARIO INTEGER,
   NOMBRE VARCHAR(200),
   APELLIDO VARCHAR(200),
   TELEFONO VARCHAR(12),
   CORREO VARCHAR(150)
);
-- Índice PROPIETARIO_REFERENCIA_FK
CREATE INDEX PROPIETARIO_REFERENCIA_FK ON REFERENCIA (ID_PROPIETARIO);

-- Definición de tabla ROL
CREATE TABLE ROL
(
   ID_ROL SERIAL PRIMARY KEY,
   NOMBRE VARCHAR(200)
);

-- Definición de tabla ROL_PERMISO
CREATE TABLE ROL_PERMISO
(
   ID_ROL INTEGER NOT NULL,
   ID_PERMISO INTEGER NOT NULL,
   PRIMARY KEY (ID_ROL, ID_PERMISO)
);
-- Índice ROL_PERMISO_FK
CREATE INDEX ROL_PERMISO_FK ON ROL_PERMISO (ID_ROL);
-- Índice ROL_PERMISO2_FK
CREATE INDEX ROL_PERMISO2_FK ON ROL_PERMISO (ID_PERMISO);

-- Definición de tabla TELEFONO
CREATE TABLE TELEFONO
(
   ID_TELEFONO SERIAL PRIMARY KEY,
   ID_PROPIETARIO INTEGER,
   TIPO VARCHAR(20),
   TELEFONO VARCHAR(12)
);
-- Índice PROPIETARIO_TELEFONO_FK
CREATE INDEX PROPIETARIO_TELEFONO_FK ON TELEFONO (ID_PROPIETARIO);

-- Definición de tabla TERRENO
CREATE TABLE TERRENO
(
   ID_TERRENO SERIAL PRIMARY KEY,
   ID_PROYECTO INTEGER,
   MATRICULA VARCHAR(18),
   NUMERO INTEGER,
   POLIGONO CHAR(1),
   SECCION CHAR(1),
   AREA_METROS NUMERIC(6,2),
   AREA_VARAS NUMERIC(6,2)
);
-- Índice TERRENO_PROYECTO_FK
CREATE INDEX TERRENO_PROYECTO_FK ON TERRENO (ID_PROYECTO);

-- Definición de tabla USUARIO
CREATE TABLE USUARIO
(
   ID_USUARIO SERIAL PRIMARY KEY,
   NOMBRE VARCHAR(100), 
   USERNAME VARCHAR(50),
   PASSWORD VARCHAR(100),
   EMAIL VARCHAR(50),
   INTENTOS INTEGER,
   BLOQUEADO BOOLEAN,
   HABILITADO BOOLEAN
);

-- Definición de tabla USUARIO_ROL
CREATE TABLE USUARIO_ROL
(
   ID_USUARIO INTEGER NOT NULL,
   ID_ROL INTEGER NOT NULL,
   PRIMARY KEY (ID_USUARIO, ID_ROL)
);
-- Índice USUARIO_ROL_FK
CREATE INDEX USUARIO_ROL_FK ON USUARIO_ROL (ID_USUARIO);
-- Índice USUARIO_ROL2_FK
CREATE INDEX USUARIO_ROL2_FK ON USUARIO_ROL (ID_ROL);

-- Definición de tabla VEHICULO
CREATE TABLE VEHICULO
(
   ID_VISITA INTEGER NOT NULL,
   ID_VEHICULO SERIAL PRIMARY KEY,
   NUMERO_PLACA VARCHAR(7),
   COLOR VARCHAR(30),
   TIPO VARCHAR(20)
);
-- Índice VEHICULO_VISITA_FK
CREATE INDEX VEHICULO_VISITA_FK ON VEHICULO (ID_VISITA);

-- Definición de tabla VENTA
CREATE TABLE VENTA
(
   ID_VENTA SERIAL PRIMARY KEY,
   ID_LIST_DOCUMENTO INTEGER,
   ID_TERRENO INTEGER,
   FECHA DATE,
   NOMBRE VARCHAR(200),
   PRECIO NUMERIC(9,2),
   DESCUENTO NUMERIC(9,2),
   MONTO NUMERIC(9,2),
   TERCEROS BOOLEAN,
   ESTADO VARCHAR(8)
);
-- Índice TERRENO_VENTA_FK
CREATE INDEX TERRENO_VENTA_FK ON VENTA (ID_TERRENO);
-- Índice VENTA_LIST_DOCUMENTO_FK
CREATE INDEX VENTA_LIST_DOCUMENTO_FK ON VENTA (ID_LIST_DOCUMENTO);

-- Definición de tabla VISITA
CREATE TABLE VISITA
(
   ID_VISITA SERIAL PRIMARY KEY,
   CANTIDAD_ADULTOS INTEGER,
   CANTIDAD_NINOS INTEGER,
   FECHA_ENTRADA DATE,
   HORA_ENTRADA TIME,
   FECHA_SALIDA DATE,
   HORA_SALIDA TIME,
   OBSERVACIONES VARCHAR(500)
);

-- Definición de tabla VISITANTE
CREATE TABLE VISITANTE
(
   ID_VISITANTE SERIAL PRIMARY KEY,
   ID_LIST_DOCUMENTO INTEGER,
   ID_PERSONA INTEGER,
   ROL VARCHAR(20),
   EMPLEADOR VARCHAR(200)
);
-- Índice VISITANTE_PERSONA_FK
CREATE INDEX VISITANTE_PERSONA_FK ON VISITANTE (ID_PERSONA);
-- Índice VISITANTE_LIST_DOCUMENT_FK
CREATE INDEX VISITANTE_LIST_DOCUMENT_FK ON VISITANTE (ID_LIST_DOCUMENTO);

-- Definición de tabla CONFIGURACION_CORREO
CREATE TABLE CONFIGURACION_CORREO
(
   ID_CONFIGURACION SERIAL PRIMARY KEY,
   NAME VARCHAR(100),
   HOST VARCHAR(50),
   PORT VARCHAR(20),
   PROTOCOL VARCHAR(50),
   USERNAME VARCHAR(100),
   PASSWORD VARCHAR(100),
   SMTP_AUTH INTEGER,
   START_TLS INTEGER,
   SERVIDOR BOOLEAN
);

-- Definición de tabla RESET_PASSWORD
CREATE TABLE RESET_PASSWORD
(
   ID_ASIGNACION SERIAL PRIMARY KEY,
   USERNAME VARCHAR(100),
   TOKEN VARCHAR(300),
   FECHA DATE
);

-- Sentencias ALTER TABLE para agregar restricciones de clave externa (FK)

-- Tabla ASIGNACION_PROPIETARIO
ALTER TABLE ASIGNACION_PROPIETARIO
ADD CONSTRAINT FK_ASIG_PROPIETARIO FOREIGN KEY (ID_PROPIETARIO)
REFERENCES PROPIETARIO (ID_PROPIETARIO);

ALTER TABLE ASIGNACION_PROPIETARIO
ADD CONSTRAINT FK_ASIG_PROPIETARIO_VENTA FOREIGN KEY (ID_VENTA)
REFERENCES VENTA (ID_VENTA);

-- Tabla ASIGNACION_VISITANTE
ALTER TABLE ASIGNACION_VISITANTE
ADD CONSTRAINT FK_ASIG_VISITANTE FOREIGN KEY (ID_VISITANTE)
REFERENCES VISITANTE (ID_VISITANTE);

ALTER TABLE ASIGNACION_VISITANTE
ADD CONSTRAINT FK_ASIG_VISITANTE_VENTA FOREIGN KEY (ID_VENTA)
REFERENCES VENTA (ID_VENTA);

ALTER TABLE ASIGNACION_VISITA
ADD CONSTRAINT FK_ASIG_VISITA_VISITA FOREIGN KEY (ID_VISITA)
REFERENCES VISITA (ID_VISITA);

ALTER TABLE ASIGNACION_VISITA
ADD CONSTRAINT FK_ASIG_VISITA_VENTA FOREIGN KEY (ID_VENTA)
REFERENCES VENTA (ID_VENTA);

ALTER TABLE ASIGNACION_VISITA
ADD CONSTRAINT FK_ASIG_VISITA_PROPIETARIO FOREIGN KEY (ID_PROPIETARIO)
REFERENCES PROPIETARIO (ID_PROPIETARIO);

ALTER TABLE ASIGNACION_VISITA
ADD CONSTRAINT FK_ASIG_VISITA_VISITANTE FOREIGN KEY (ID_VISITANTE)
REFERENCES VISITANTE (ID_VISITANTE);

-- Tabla ASIGNACION_PROYECTO
ALTER TABLE ASIGNACION_PROYECTO
ADD CONSTRAINT FK_ASIG_PROYECTO FOREIGN KEY (ID_PROYECTO)
REFERENCES PROYECTO (ID_PROYECTO);

ALTER TABLE ASIGNACION_PROYECTO
ADD CONSTRAINT FK_ASIG_PROYECTO_USUARIO FOREIGN KEY (ID_USUARIO)
REFERENCES USUARIO (ID_USUARIO);

-- Tabla ASIGNACION_EMPRESA
ALTER TABLE ASIGNACION_EMPRESA
ADD CONSTRAINT FK_ASIG_EMPRESA FOREIGN KEY (ID_EMPRESA)
REFERENCES EMPRESA (ID_EMPRESA);

ALTER TABLE ASIGNACION_EMPRESA
ADD CONSTRAINT FK_ASIG_EMPRESA_USUARIO FOREIGN KEY (ID_USUARIO)
REFERENCES USUARIO (ID_USUARIO);

-- Tabla CORREO
ALTER TABLE CORREO
ADD CONSTRAINT FK_CORREO_PROPIETARIO FOREIGN KEY (ID_PROPIETARIO)
REFERENCES PROPIETARIO (ID_PROPIETARIO);

-- Tabla CUENTA_BANCARIA
ALTER TABLE CUENTA_BANCARIA
ADD CONSTRAINT FK_EMPRESA_CUENTA_BANCARIA FOREIGN KEY (ID_EMPRESA)
REFERENCES EMPRESA (ID_EMPRESA);

-- Tabla CUOTA_AMORTIZACION
ALTER TABLE CUOTA_AMORTIZACION
ADD CONSTRAINT FK_AMORTIZACION_INFO FOREIGN KEY (ID_INFO_FINANCIAMIENTO)
REFERENCES INFO_FINANCIAMIENTO (ID_INFO_FINANCIAMIENTO);

-- Tabla CUOTA_FINANCIAMIENTO
ALTER TABLE CUOTA_FINANCIAMIENTO
ADD CONSTRAINT FK_FINANCIAMIENTO_PAGO FOREIGN KEY (ID_PAGO)
REFERENCES PAGO (ID_PAGO);

ALTER TABLE CUOTA_FINANCIAMIENTO
ADD CONSTRAINT FK_FINANCIAMIENTO_INFO FOREIGN KEY (ID_INFO_FINANCIAMIENTO)
REFERENCES INFO_FINANCIAMIENTO (ID_INFO_FINANCIAMIENTO);

-- Tabla CUOTA_MANTENIMIENTO
ALTER TABLE CUOTA_MANTENIMIENTO
ADD CONSTRAINT FK_MANTENIMIENTO_PAGO FOREIGN KEY (ID_PAGO)
REFERENCES PAGO (ID_PAGO);

-- Tabla DOCUMENTO
ALTER TABLE DOCUMENTO
ADD CONSTRAINT FK_LIST_DOCUMENT_DOCUMENTO FOREIGN KEY (ID_LIST_DOCUMENTO)
REFERENCES LISTADO_DOCUMENTO (ID_LIST_DOCUMENTO);

-- Tabla FACTURACION
ALTER TABLE FACTURACION
ADD CONSTRAINT FK_VENTA_FACTURACION FOREIGN KEY (ID_VENTA)
REFERENCES VENTA (ID_VENTA);

-- Tabla INFO_FINANCIAMIENTO
ALTER TABLE INFO_FINANCIAMIENTO
ADD CONSTRAINT FK_VENTA_INFO_FINANCIAMIENTO FOREIGN KEY (ID_VENTA)
REFERENCES VENTA (ID_VENTA);

-- Tabla INFO_MANTENIMIENTO
ALTER TABLE INFO_MANTENIMIENTO
ADD CONSTRAINT FK_VENTA_INFO_MANTENIMIENTO FOREIGN KEY (ID_VENTA)
REFERENCES VENTA (ID_VENTA);

-- Tabla PAGO
ALTER TABLE PAGO
ADD CONSTRAINT FK_PAGO_CUENTA_BANCARIA FOREIGN KEY (ID_CUENTA_BANCARIA)
REFERENCES CUENTA_BANCARIA (ID_CUENTA_BANCARIA);

ALTER TABLE PAGO
ADD CONSTRAINT FK_PAGO_VENTA FOREIGN KEY (ID_VENTA)
REFERENCES VENTA (ID_VENTA);

-- Tabla PERSONA
ALTER TABLE PERSONA
ADD CONSTRAINT FK_PERSONA_TIPO_DOCUMENTO FOREIGN KEY (ID_TIPO_DOCUMENTO)
REFERENCES TIPO_DOCUMENTO (ID_TIPO_DOCUMENTO);

-- Tabla PROPIETARIO
ALTER TABLE PROPIETARIO
ADD CONSTRAINT FK_PROPIETARIO_PERSONA FOREIGN KEY (ID_PERSONA)
REFERENCES PERSONA (ID_PERSONA);

-- Tabla PROYECTO
ALTER TABLE PROYECTO
ADD CONSTRAINT FK_EMPRESA_PROYECTO FOREIGN KEY (ID_EMPRESA)
REFERENCES EMPRESA (ID_EMPRESA);

-- Tabla REFERENCIA
ALTER TABLE REFERENCIA
ADD CONSTRAINT FK_REFERENCIA_PROPIETARIO FOREIGN KEY (ID_PROPIETARIO)
REFERENCES PROPIETARIO (ID_PROPIETARIO);

-- Tabla ROL_PERMISO
ALTER TABLE ROL_PERMISO
ADD CONSTRAINT FK_ROL_PERMISO FOREIGN KEY (ID_ROL)
REFERENCES ROL (ID_ROL);

ALTER TABLE ROL_PERMISO
ADD CONSTRAINT FK_ROL_PERMISO_2 FOREIGN KEY (ID_PERMISO)
REFERENCES PERMISO (ID_PERMISO);

-- Tabla TELEFONO
ALTER TABLE TELEFONO
ADD CONSTRAINT FK_TELEFONO_PROPIETARIO FOREIGN KEY (ID_PROPIETARIO)
REFERENCES PROPIETARIO (ID_PROPIETARIO);

-- Tabla TERRENO
ALTER TABLE TERRENO
ADD CONSTRAINT FK_TERRENO_PROYECTO FOREIGN KEY (ID_PROYECTO)
REFERENCES PROYECTO (ID_PROYECTO);

-- Tabla USUARIO_ROL
ALTER TABLE USUARIO_ROL
ADD CONSTRAINT FK_USUARIO_ROL FOREIGN KEY (ID_USUARIO)
REFERENCES USUARIO (ID_USUARIO);

ALTER TABLE USUARIO_ROL
ADD CONSTRAINT FK_USUARIO_ROL_2 FOREIGN KEY (ID_ROL)
REFERENCES ROL (ID_ROL);

-- Tabla VEHICULO
ALTER TABLE VEHICULO
ADD CONSTRAINT FK_VEHICULO_VISITA FOREIGN KEY (ID_VISITA)
REFERENCES VISITA (ID_VISITA);

-- Tabla VENTA
ALTER TABLE VENTA
ADD CONSTRAINT FK_VENTA_TERRENO FOREIGN KEY (ID_TERRENO)
REFERENCES TERRENO (ID_TERRENO);

ALTER TABLE VENTA
ADD CONSTRAINT FK_VENTA_LIST_DOCUMENT FOREIGN KEY (ID_LIST_DOCUMENTO)
REFERENCES LISTADO_DOCUMENTO (ID_LIST_DOCUMENTO);

-- Tabla VISITANTE
ALTER TABLE VISITANTE
ADD CONSTRAINT FK_VISITANTE_LIST_DOCUMENTO FOREIGN KEY (ID_LIST_DOCUMENTO)
REFERENCES LISTADO_DOCUMENTO (ID_LIST_DOCUMENTO);

ALTER TABLE VISITANTE
ADD CONSTRAINT FK_VISITANTE_PERSONA FOREIGN KEY (ID_PERSONA)
REFERENCES PERSONA (ID_PERSONA);

-- Vista VISTA_TERRENO
CREATE OR REPLACE VIEW VISTA_TERRENO AS
SELECT 
    ID_TERRENO,
    ID_PROYECTO,
    MATRICULA,
    NUMERO || SECCION AS LOTE,
    POLIGONO,
    AREA_METROS,
    AREA_VARAS
FROM TERRENO;

-- Vista VISTA_PROPIETARIOS_PROYECTO
CREATE OR REPLACE VIEW VISTA_PROPIETARIOS_PROYECTO AS
SELECT
    T.ID_PROYECTO AS ID_PROYECTO,
    P.ID_PERSONA AS ID_PERSONA,
    P.NOMBRE || ' ' || P.APELLIDO AS NOMBRE,
    string_agg(T.POLIGONO || '-' || T.NUMERO || T.SECCION, ', ' ORDER BY T.ID_TERRENO) AS LOTES,
    C.CORREOS,
    TL.TELEFONOS
FROM
    PROPIETARIO PR
    JOIN PERSONA P ON PR.ID_PERSONA = P.ID_PERSONA
    JOIN ASIGNACION_PROPIETARIO AP ON PR.ID_PROPIETARIO = AP.ID_PROPIETARIO
    JOIN VENTA V ON AP.ID_VENTA = V.ID_VENTA
    JOIN TERRENO T ON V.ID_TERRENO = T.ID_TERRENO
    LEFT JOIN (
        SELECT ID_PROPIETARIO, string_agg(C.CORREO, ', ') AS CORREOS
        FROM CORREO C
        GROUP BY ID_PROPIETARIO
    ) C ON PR.ID_PROPIETARIO = C.ID_PROPIETARIO
    LEFT JOIN (
        SELECT ID_PROPIETARIO, string_agg(TL.TELEFONO, ', ') AS TELEFONOS
        FROM TELEFONO TL
        GROUP BY ID_PROPIETARIO
    ) TL ON PR.ID_PROPIETARIO = TL.ID_PROPIETARIO
GROUP BY
    T.ID_PROYECTO, P.ID_PERSONA, P.NOMBRE, P.APELLIDO, C.CORREOS, TL.TELEFONOS;

-- Vista VISTA_TRABAJADORES_PROYECTO
CREATE OR REPLACE VIEW VISTA_TRABAJADORES_PROYECTO AS
SELECT
    P.ID_PERSONA,
    T.ID_PROYECTO,
    P.NOMBRE || ' ' || P.APELLIDO AS NOMBRE,
    V.EMPLEADOR,
    string_agg(T.POLIGONO || '-' || T.NUMERO || T.SECCION, ', ' ORDER BY T.ID_TERRENO) AS LOTES
FROM
    PERSONA P
    JOIN VISITANTE V ON P.ID_PERSONA = V.ID_PERSONA
    JOIN ASIGNACION_VISITANTE AV ON V.ID_VISITANTE = AV.ID_VISITANTE
    JOIN VENTA VE ON AV.ID_VENTA = VE.ID_VENTA
    JOIN TERRENO T ON VE.ID_TERRENO = T.ID_TERRENO
GROUP BY
    P.ID_PERSONA, T.ID_PROYECTO, P.NOMBRE, P.APELLIDO, V.EMPLEADOR;

-- Vista VISTA_VENTAS_ACTIVA
CREATE OR REPLACE VIEW VISTA_VENTAS_ACTIVA AS
SELECT
    V.ID_VENTA,
    T.ID_PROYECTO,
    T.POLIGONO,
    T.NUMERO || T.SECCION AS LOTE,
    T.MATRICULA,
    T.AREA_METROS,
    T.AREA_VARAS
FROM
    TERRENO T
JOIN
    VENTA V ON T.ID_TERRENO = V.ID_TERRENO
WHERE
    V.ESTADO = 'Activo';

-- TRIGGER TRG_AGREGAR_PROPIETARIO
CREATE OR REPLACE FUNCTION agregar_propietario() RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO listado_documento DEFAULT VALUES RETURNING id_list_documento INTO NEW.id_list_documento;
  NEW.id_list_documento := NEW.id_list_documento;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER TRG_AGREGAR_PROPIETARIO
BEFORE INSERT ON propietario
FOR EACH ROW
EXECUTE FUNCTION agregar_propietario();

-- TRIGGER TRG_ELIMINAR_PROPIETARIO
CREATE OR REPLACE FUNCTION eliminar_propietario() RETURNS TRIGGER AS $$
DECLARE
  id_lista_documento INTEGER;
BEGIN
  id_lista_documento := OLD.id_list_documento;
  DELETE FROM documento WHERE id_list_documento = id_lista_documento;
  DELETE FROM listado_documento WHERE id_list_documento = id_lista_documento;
  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER TRG_ELIMINAR_PROPIETARIO
AFTER DELETE ON propietario
FOR EACH ROW
EXECUTE FUNCTION eliminar_propietario();

-- TRIGGER TRG_AGREGAR_VISITANTE
CREATE OR REPLACE FUNCTION agregar_visitante() RETURNS TRIGGER AS $$
DECLARE
  id_lista_documento INTEGER;
BEGIN
  INSERT INTO listado_documento DEFAULT VALUES RETURNING id_list_documento INTO NEW.id_list_documento;
  NEW.id_list_documento := NEW.id_list_documento;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER TRG_AGREGAR_VISITANTE
BEFORE INSERT ON visitante
FOR EACH ROW
EXECUTE FUNCTION agregar_visitante();

-- TRIGGER TRG_ELIMINAR_VISITANTE
CREATE OR REPLACE FUNCTION eliminar_visitante() RETURNS TRIGGER AS $$
DECLARE
  id_lista_documento INTEGER;
BEGIN
  id_lista_documento := OLD.id_list_documento;
  DELETE FROM documento WHERE id_list_documento = id_lista_documento;
  DELETE FROM listado_documento WHERE id_list_documento = id_lista_documento;
  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER TRG_ELIMINAR_VISITANTE
AFTER DELETE ON visitante
FOR EACH ROW
EXECUTE FUNCTION eliminar_visitante();

-- TRIGGER TRG_AGREGAR_VENTA
CREATE OR REPLACE FUNCTION trg_agregar_venta() RETURNS TRIGGER AS $$
DECLARE
  id_lista_documento INTEGER;
BEGIN
  INSERT INTO listado_documento DEFAULT VALUES RETURNING id_list_documento INTO NEW.id_list_documento;
  NEW.id_list_documento := NEW.id_list_documento;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER TRG_AGREGAR_VENTA
BEFORE INSERT ON venta
FOR EACH ROW
EXECUTE FUNCTION trg_agregar_venta();

-- TRIGGER TRG_ELIMINAR_VENTA
CREATE OR REPLACE FUNCTION trg_eliminar_venta() RETURNS TRIGGER AS $$
DECLARE
  id_lista_documento INTEGER;
BEGIN
  id_lista_documento := OLD.id_list_documento;
  DELETE FROM documento WHERE id_list_documento = id_lista_documento;
  DELETE FROM listado_documento WHERE id_list_documento = id_lista_documento;
  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER TRG_ELIMINAR_VENTA
AFTER DELETE ON venta
FOR EACH ROW
EXECUTE FUNCTION trg_eliminar_venta();




