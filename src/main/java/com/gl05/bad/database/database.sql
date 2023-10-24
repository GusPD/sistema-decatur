/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     10/07/2023 02:01:36 p. m.                    */
/*==============================================================*/


alter table ASIGNACION_PROPIETARIO
   drop constraint FK_ASIG_PROPIETARIO;

alter table ASIGNACION_PROPIETARIO
   drop constraint FK_ASIG_PROPIETARIO_VENTA;

alter table ASIGNACION_VISITANTE
   drop constraint FK_ASIG_VISITANTE;

alter table ASIGNACION_VISITANTE
   drop constraint FK_ASIG_VISITANTE_VENTA;

alter table ASIGNACION_VISITA
   drop constraint FK_ASIG_VISITA_VENTA;

alter table ASIGNACION_VISITA
   drop constraint FK_ASIG_VISITA_VISITA;

alter table ASIGNACION_VISITA
   drop constraint FK_ASIG_VISITA_PROPIETARIO;

alter table ASIGNACION_VISITA
   drop constraint FK_ASIG_VISITA_VISITANTE;

alter table ASIGNACION_PROYECTO
   drop constraint FK_ASIG_PROYECTO;

alter table ASIGNACION_PROYECTO
   drop constraint FK_ASIG_PROYECTO_USUARIO;

alter table ASIGNACION_EMPRESA
   drop constraint FK_ASIG_EMPRESA;

alter table ASIGNACION_EMPRESA
   drop constraint FK_ASIG_EMPRESA_USUARIO;

alter table CORREO
   drop constraint FK_CORREO_PROPIETARIO;

alter table CUENTA_BANCARIA
   drop constraint FK_PROYECTO_CUENTA_BANCARIA;

alter table CUOTA_AMORTIZACION
   drop constraint FK_AMORTIZACION_VENTA;

alter table CUOTA_FINANCIAMIENTO
   drop constraint FK_FINANCIAMIENTO_PAGO;

alter table CUOTA_MANTENIMIENTO
   drop constraint FK_MANTENIMIENTO_PAGO;

alter table DOCUMENTO
   drop constraint FK_LIST_DOCUMENT_DOCUMENTO;

alter table FACTURACION
   drop constraint FK_VENTA_FACTURACION;

alter table INFO_FINANCIAMIENTO
   drop constraint FK_VENTA_INFO_FINANCIAMIENTO;

alter table INFO_MANTENIMIENTO
   drop constraint FK_VENTA_INFO_MANTENIMIENTO;

alter table PAGO
   drop constraint FK_PAGO_CUENTA_BANCARIA;

alter table PAGO
   drop constraint FK_PAGO_VENTA;

alter table PROPIETARIO
   drop constraint FK_PROPIETARIO_PERSONA;

alter table PROYECTO
   drop constraint FK_EMPRESA_PROYECTO;

alter table REFERENCIA
   drop constraint FK_REFERENCIA_PROPIETARIO;

alter table ROL_PERMISO
   drop constraint FK_ROL_PERMISO;

alter table ROL_PERMISO
   drop constraint FK_ROL_PERMISO_2;

alter table TELEFONO
   drop constraint FK_TELEFONO_PROPIETARRIO;

alter table TERRENO
   drop constraint FK_TERRENO_PROYECTO;

alter table USUARIO_ROL
   drop constraint FK_USUARIO_ROL;

alter table USUARIO_ROL
   drop constraint FK_USUARIO_ROL_2;

alter table VEHICULO
   drop constraint FK_VEHICULO_VISITA;

alter table VENTA
   drop constraint FK_VENTA_TERRENO;

alter table VENTA
   drop constraint FK_VENTA_LIST_DOCUMENT;

alter table VISITANTE
   drop constraint FK_VISITANTE_LIST_DOCUMENTO;

alter table VISITANTE
   drop constraint FK_VISITANTE_PERSONA;

drop index ASIG_PROPIETARIO_FK;

drop index ASIG_PROPIETARIO_VENTA_FK;

drop table ASIGNACION_PROPIETARIO cascade constraints;

drop index ASIG_VISITANTE_FK;

drop index ASIG_VISITANTE_VENTA_FK;

drop table ASIGNACION_VISITANTE cascade constraints;

drop index ASIG_VISITA_VENTA_FK;

drop index ASIG_VISITA_VISITA_FK;

drop index ASIG_VISITA_PROPIETARIO_FK;

drop index ASIG_VISITA_VISITANTE_FK;

drop table ASIGNACION_VISITA cascade constraints;

drop index ASIG_PROYECTO_FK;

drop index ASIG_PROYECTO_USUARIO_FK;

drop table ASIGNACION_PROYECTO cascade constraints;

drop index ASIG_EMPRESA_FK;

drop index ASIG_EMPRESA_USUARIO_FK;

drop table ASIGNACION_EMPRESA cascade constraints;

drop table BITACORA cascade constraints;

drop index PROPIETARIO_CORREO_FK;

drop table CORREO cascade constraints;

drop index PROYECTO_CUENTA_BANCARIA_FK;

drop table CUENTA_BANCARIA cascade constraints;

drop table EMPRESA cascade constraints;

drop index VENTA_FACTURACION_FK;

drop table FACTURACION cascade constraints;

drop index VENTA_INFO_FINANCIAMIENTO_FK;

drop table INFO_FINANCIAMIENTO cascade constraints;

drop index VENTA_INFO_MANTENIMIENTO_FK;

drop table INFO_MANTENIMIENTO cascade constraints;

drop index VENTA_AMORTIZACION_FK;

drop table CUOTA_AMORTIZACION cascade constraints;

drop index PAGO_FINANCIAMIENTO_FK;

drop table CUOTA_FINANCIAMIENTO cascade constraints;

drop index PAGO_MANTENIMIENTO_FK;

drop table CUOTA_MANTENIMIENTO cascade constraints;

drop index LIST_DOCUMENT_DOCUMENTO_FK;

drop table DOCUMENTO cascade constraints;

drop table LISTADO_DOCUMENTO cascade constraints;

drop index VENTA_PAGO_FK;

drop index PAGO_CUENTA_BANCARIA_FK;

drop table PAGO cascade constraints;

drop table PERMISO cascade constraints;

drop table PERSONA cascade constraints;

drop index PERSONA_PROPIETARIO_FK;

drop index PROPIETARIO_LIST_DOCUMENT_FK;

drop table PROPIETARIO cascade constraints;

drop index PROYECTO_EMPRESA_FK;

drop table PROYECTO cascade constraints;

drop index PROPIETARIO_REFERENCIA_FK;

drop table REFERENCIA cascade constraints;

drop table ROL cascade constraints;

drop index ROL_PERMISO2_FK;

drop index ROL_PERMISO_FK;

drop table ROL_PERMISO cascade constraints;

drop index PROPIETARIO_TELEFONO_FK;

drop table TELEFONO cascade constraints;

drop index TERRENO_PROYECTO_FK;

drop table TERRENO cascade constraints;

drop table USUARIO cascade constraints;

drop index USUARIO_ROL2_FK;

drop index USUARIO_ROL_FK;

drop table USUARIO_ROL cascade constraints;

drop index VEHICULO_VISITA_FK;

drop table VEHICULO cascade constraints;

drop index VENTA_LIST_DOCUMENTO_FK;

drop index TERRENO_VENTA_FK;

drop table VENTA cascade constraints;

drop table VISITA cascade constraints;

drop index VISITANTE_LIST_DOCUMENT_FK;

drop index VISITANTE_PERSONA_FK;

drop table VISITANTE cascade constraints;

drop table CONFIGURACION_CORREO cascade constraints;

drop table RESET_PASSWORD cascade constraints;

drop sequence S_ASIGNACION_PROPIETARIO;

drop sequence S_ASIGNACION_VISITANTE;

drop sequence S_ASIGNACION_VISITA;

drop sequence S_BITACORA;

drop sequence S_CORREO;

drop sequence S_CUENTA_BANCARIA;

drop sequence S_CUOTA_AMORTIZACION;

drop sequence S_CUOTA_FINANCIAMIENTO;

drop sequence S_CUOTA_MANTENIMIENTO;

drop sequence S_DOCUMENTO;

drop sequence S_EMPRESA;

drop sequence S_FACTURACION;

drop sequence S_INFO_FINANCIAMIENTO;

drop sequence S_INFO_MANTENIMIENTO;

drop sequence S_LISTADO_DOCUMENTO;

drop sequence S_PAGO;

drop sequence S_PERMISO;

drop sequence S_PERSONA;

drop sequence S_PROPIETARIO;

drop sequence S_PROYECTO;

drop sequence S_REFERENCIA;

drop sequence S_ROL;

drop sequence S_TELEFONO;

drop sequence S_TERRENO;

drop sequence S_USUARIO;

drop sequence S_VEHICULO;

drop sequence S_VENTA;

drop sequence S_VISITA;

drop sequence S_VISITANTE;

drop sequence S_CONFIGURACION_CORREO;

drop sequence S_RESET_PASSWORD;

create sequence S_ASIGNACION_PROPIETARIO;

create sequence S_ASIGNACION_VISITANTE;

create sequence S_ASIGNACION_VISITA;

create sequence S_BITACORA;

create sequence S_CORREO;

create sequence S_CUENTA_BANCARIA;

create sequence S_CUOTA_AMORTIZACION;

create sequence S_CUOTA_FINANCIAMIENTO;

create sequence S_CUOTA_MANTENIMIENTO;

create sequence S_DOCUMENTO;

create sequence S_EMPRESA;

create sequence S_FACTURACION;

create sequence S_INFO_FINANCIAMIENTO;

create sequence S_INFO_MANTENIMIENTO;

create sequence S_LISTADO_DOCUMENTO;

create sequence S_PAGO;

create sequence S_PERMISO;

create sequence S_PERSONA;

create sequence S_PROPIETARIO;

create sequence S_PROYECTO;

create sequence S_REFERENCIA;

create sequence S_ROL;

create sequence S_TELEFONO;

create sequence S_TERRENO;

create sequence S_USUARIO;

create sequence S_VEHICULO;

create sequence S_VENTA;

create sequence S_VISITA;

create sequence S_VISITANTE;

create sequence S_CONFIGURACION_CORREO;

create sequence S_RESET_PASSWORD;

/*==============================================================*/
/* Table: ASIGNACION_PROPIETARIO                                */
/*==============================================================*/
create table ASIGNACION_PROPIETARIO 
(
   ID_ASIG_PROPIETARIO  NUMBER(6)            not null,
   ID_PROPIETARIO       NUMBER(6),
   ID_VENTA             NUMBER(6),
   ESTADO               VARCHAR2(15),
   constraint PK_ASIGNACION_PROPIETARIO primary key (ID_ASIG_PROPIETARIO)
);

/*==============================================================*/
/* Index: ASIG_PROPIETARIO_VENTA_FK                             */
/*==============================================================*/
create index ASIG_PROPIETARIO_VENTA_FK on ASIGNACION_PROPIETARIO (
   ID_VENTA ASC
);

/*==============================================================*/
/* Index: ASIG_PROPIETARIO_FK                                   */
/*==============================================================*/
create index ASIG_PROPIETARIO_FK on ASIGNACION_PROPIETARIO (
   ID_PROPIETARIO ASC
);

/*==============================================================*/
/* Table: ASIGNACION_VISITANTE                                  */
/*==============================================================*/
create table ASIGNACION_VISITANTE
(
   ID_ASIG_VISITANTE  NUMBER(6)            not null,
   ID_VISITANTE       NUMBER(6),
   ID_VENTA           NUMBER(6),
   constraint PK_ASIGNACION_VISITANTE primary key (ID_ASIG_VISITANTE)
);

/*==============================================================*/
/* Index: ASIG_VISITANTE_VENTA_FK                               */
/*==============================================================*/
create index ASIG_VISITANTE_VENTA_FK on ASIGNACION_VISITANTE (
   ID_VENTA ASC
);

/*==============================================================*/
/* Index: ASIG_VISITANTE_FK                                     */
/*==============================================================*/
create index ASIG_VISITANTE_FK on ASIGNACION_VISITANTE (
   ID_VISITANTE ASC
);

/*==============================================================*/
/* Table: ASIGNACION_VISITA                                     */
/*==============================================================*/
create table ASIGNACION_VISITA
(
   ID_ASIG_VISITA    NUMBER(6)            not null,
   REPRESENTANTE     NUMBER(6),
   ID_VENTA          NUMBER(6),
   ID_VISITA         NUMBER(6),
   ID_PROPIETARIO    NUMBER(6),
   ID_VISITANTE      NUMBER(6),
   constraint PK_ASIGNACION_VISITA primary key (ID_ASIG_VISITA)
);

/*==============================================================*/
/* Index: ASIG_VISITA_VENTA_FK                                  */
/*==============================================================*/
create index ASIG_VISITA_VENTA_FK on ASIGNACION_VISITA(
   ID_VENTA ASC
);

/*==============================================================*/
/* Index: ASIG_VISITA_VISITA_FK                                 */
/*==============================================================*/
create index ASIG_VISITA_VISITA_FK on ASIGNACION_VISITA (
   ID_VISITA ASC
);

/*==============================================================*/
/* Index: ASIG_VISITA_PROPIETARIO_FK                            */
/*==============================================================*/
create index ASIG_VISITA_PROPIETARIO_FK on ASIGNACION_VISITA (
   ID_PROPIETARIO ASC
);

/*==============================================================*/
/* Index: ASIG_VISITA_VISITANTE_FK                              */
/*==============================================================*/
create index ASIG_VISITA_VISITANTE_FK on ASIGNACION_VISITA (
   ID_VISITANTE ASC
);

/*==============================================================*/
/* Table: ASIGNACION_PROYECTO                                   */
/*==============================================================*/
create table ASIGNACION_PROYECTO
(
   ID_PROYECTO          NUMBER(6),
   ID_USUARIO           NUMBER(6),
   constraint PK_USUARIO_PROYECTO primary key (ID_USUARIO, ID_PROYECTO)
);

/*==============================================================*/
/* Index: ASIG_PROYECTO_USUARIO_FK                              */
/*==============================================================*/
create index ASIG_PROYECTO_USUARIO_FK on ASIGNACION_PROYECTO (
   ID_USUARIO ASC
);

/*==============================================================*/
/* Index: ASIG_PROYECTO_FK                                      */
/*==============================================================*/
create index ASIG_PROYECTO_FK on ASIGNACION_PROYECTO (
   ID_PROYECTO ASC
);

/*==============================================================*/
/* Table: ASIGNACION_EMPRESA                                    */
/*==============================================================*/
create table ASIGNACION_EMPRESA
(
   ID_EMPRESA           NUMBER(6),
   ID_USUARIO           NUMBER(6),
   constraint PK_USUARIO_EMPRESA primary key (ID_USUARIO, ID_EMPRESA)
);

/*==============================================================*/
/* Index: ASIG_EMPRESA_USUARIO_FK                              */
/*==============================================================*/
create index ASIG_EMPRESA_USUARIO_FK on ASIGNACION_EMPRESA (
   ID_USUARIO ASC
);

/*==============================================================*/
/* Index: ASIG_EMPRESA_FK                                       */
/*==============================================================*/
create index ASIG_EMPRESA_FK on ASIGNACION_EMPRESA (
   ID_EMPRESA ASC
);

/*==============================================================*/
/* Table: BITACORA                                              */
/*==============================================================*/
create table BITACORA 
(
   ID_BITACORA          NUMBER(6)            not null,
   USERNAME             VARCHAR2(50),
   EVENTO               VARCHAR2(100),
   HORA                 TIMESTAMP,
   IP_EQUIPO            VARCHAR2(15),
   constraint PK_BITACORA primary key (ID_BITACORA)
);

/*==============================================================*/
/* Table: CORREO                                                */
/*==============================================================*/
create table CORREO 
(
   ID_CORREO            NUMBER(6)            not null,
   ID_PROPIETARIO       NUMBER(6),
   TIPO                 VARCHAR2(20),
   CORREO               VARCHAR2(150),
   constraint PK_CORREO primary key (ID_CORREO)
);

/*==============================================================*/
/* Index: PROPIETARIO_CORREO_FK                                 */
/*==============================================================*/
create index PROPIETARIO_CORREO_FK on CORREO (
   ID_PROPIETARIO ASC
);

/*==============================================================*/
/* Table: CUENTA_BANCARIA                                       */
/*==============================================================*/
create table CUENTA_BANCARIA 
(
   ID_CUENTA_BANCARIA   NUMBER(6)            not null,
   ID_PROYECTO          NUMBER(6),
   NOMBRE               VARCHAR2(200),
   TITULAR              VARCHAR2(200),
   BANCO                VARCHAR2(200),
   TIPO                 VARCHAR2(20),
   CUENTA               VARCHAR2(20),
   constraint PK_CUENTA_BANCARIA primary key (ID_CUENTA_BANCARIA)
);

/*==============================================================*/
/* Index: PROYECTO_CUENTA_BANCARIA_FK                           */
/*==============================================================*/
create index PROYECTO_CUENTA_BANCARIA_FK on CUENTA_BANCARIA (
   ID_PROYECTO ASC
);

/*==============================================================*/
/* Table: CUOTA_AMORTIZACION                                    */
/*==============================================================*/
create table CUOTA_AMORTIZACION 
(
   ID_CUOTA_AMORTIZACION NUMBER(6)            not null,
   ID_VENTA             NUMBER(6),
   FECHA_CUOTA          DATE,
   MONTO                NUMBER(6,2),
   DIAS_INTERES         NUMBER,
   INTERES              NUMBER(6,2),
   COMISION             NUMBER(6,2),
   CAPITAL              NUMBER(6,2),
   SALDO                NUMBER(6,2),
   constraint PK_CUOTA_AMORTIZACION primary key (ID_CUOTA_AMORTIZACION)
);

/*==============================================================*/
/* Index: VENTA_AMORTIZACION_FK                                 */
/*==============================================================*/
create index VENTA_AMORTIZACION_FK on CUOTA_AMORTIZACION (
   ID_VENTA ASC
);

/*==============================================================*/
/* Table: CUOTA_FINANCIAMIENTO                                  */
/*==============================================================*/
create table CUOTA_FINANCIAMIENTO 
(
   ID_CUOTA_FINANCIAMIENTO NUMBER(6)            not null,
   ID_PAGO              NUMBER(6),
   FECHA_CUOTA          DATE,
   DIAS_INTERES_CORRIENTE NUMBER,
   INTERES_CORRIENTE    NUMBER(6,2),
   DIAS_INTERES_MORA    NUMBER,
   INTERES_MORA         NUMBER(6,2),
   PAGADO_INTERES       NUMBER(6,2),
   PENDIENTE_INTERES    NUMBER(6,2),
   COMISION             NUMBER(6,2),
   RECARGO              NUMBER(6,2),
   OTROS                NUMBER(6,2),
   PAGADO_OTROS         NUMBER(6,2),
   PENDIENTE_OTROS      NUMBER(6,2),
   CAPITAL              NUMBER(6,2),
   SALDO                NUMBER(6,2),
   constraint PK_CUOTA_FINANCIAMIENTO primary key (ID_CUOTA_FINANCIAMIENTO)
);

/*==============================================================*/
/* Index: PAGO_FINANCIAMIENTO_FK                                */
/*==============================================================*/
create index PAGO_FINANCIAMIENTO_FK on CUOTA_FINANCIAMIENTO (
   ID_PAGO ASC
);

/*==============================================================*/
/* Table: CUOTA_MANTENIMIENTO                                   */
/*==============================================================*/
create table CUOTA_MANTENIMIENTO 
(
   ID_CUOTA_MANTENIMIENTO NUMBER(20)            not null,
   ID_PAGO              NUMBER(9),
   FECHA_CUOTA          DATE,
   CUOTA                NUMBER(9,2),
   SALDO_CUOTA          NUMBER(9,2),
   FECHA_RECARGO        TIMESTAMP,
   RECARGO              NUMBER(9,2),
   SALDO_RECARGO        NUMBER(9,2),
   DESCUENTO            NUMBER(9,2),
   ESTADO_MANTENIMIENTO NUMBER(1),
   ESTADO_RECARGO       NUMBER(1),
   constraint PK_CUOTA_MANTENIMIENTO primary key (ID_CUOTA_MANTENIMIENTO)
);

/*==============================================================*/
/* Index: PAGO_MANTENIMIENTO_FK                                 */
/*==============================================================*/
create index PAGO_MANTENIMIENTO_FK on CUOTA_MANTENIMIENTO (
   ID_PAGO ASC
);

/*==============================================================*/
/* Table: DOCUMENTO                                             */
/*==============================================================*/
create table DOCUMENTO 
(
   ID_DOCUMENTO         NUMBER(6)            not null,
   ID_LIST_DOCUMENTO    NUMBER(6),
   NOMBRE               VARCHAR2(200),
   DOCUMENTO            BLOB,
   constraint PK_DOCUMENTO primary key (ID_DOCUMENTO)
);

/*==============================================================*/
/* Index: LIST_DOCUMENT_DOCUMENTO_FK                            */
/*==============================================================*/
create index LIST_DOCUMENT_DOCUMENTO_FK on DOCUMENTO (
   ID_LIST_DOCUMENTO ASC
);

/*==============================================================*/
/* Table: EMPRESA                                               */
/*==============================================================*/
create table EMPRESA 
(
   ID_EMPRESA           NUMBER(6)            not null,
   NOMBRE               VARCHAR2(200),
   constraint PK_EMPRESA primary key (ID_EMPRESA)
);

/*==============================================================*/
/* Table: FACTURACION                                           */
/*==============================================================*/
create table FACTURACION 
(
   ID_FACTURACION       NUMBER(9)            not null,
   ID_VENTA             NUMBER(9),
   NOMBRE               VARCHAR2(200),
   N_REGISTRO           VARCHAR2(12),
   NIT                  VARCHAR2(14),
   DUI                  VARCHAR2(9),
   DIRECCION            VARCHAR2(300),
   GIRO                 VARCHAR2(200),
   constraint PK_FACTURACION primary key (ID_FACTURACION)
);

/*==============================================================*/
/* Index: VENTA_FACTURACION_FK                                  */
/*==============================================================*/
create index VENTA_FACTURACION_FK on FACTURACION (
   ID_VENTA ASC
);

/*==============================================================*/
/* Table: INFO_FINANCIAMIENTO                                   */
/*==============================================================*/
create table INFO_FINANCIAMIENTO
(
   ID_INFO_FINANCIAMIENTO  NUMBER(9)            not null,
   ID_VENTA                NUMBER(9),
   FECHA_APLICACION        DATE,
   MONTO                   NUMBER(9,2),
   PLAZO                   NUMBER,
   TASA                    NUMBER(6,2),
   CUOTA_KI                NUMBER(9,2),
   MULTA_FINANCIAMIENTO NUMBER(9,2),
   constraint PK_INFO_FINANCIAMIENTO primary key (ID_INFO_FINANCIAMIENTO)
);

/*==============================================================*/
/* Index: VENTA_INFO_FINANCIAMIENTO_FK                          */
/*==============================================================*/
create index VENTA_INFO_FINANCIAMIENTO_FK on INFO_FINANCIAMIENTO (
   ID_VENTA ASC
);

/*==============================================================*/
/* Table: INFO_MANTENIMIENTO                                    */
/*==============================================================*/
create table INFO_MANTENIMIENTO 
(
   ID_INFO_MANTENIMIENTO      NUMBER(9)            not null,
   ID_VENTA                   NUMBER(9),
   FECHA_APLICACION           DATE,
   CUOTA_MANTENIMIENTO        NUMBER(9,2),
   MULTA_MANTENIMIENTO        NUMBER(9,2),
   constraint PK_INFO_MANTENIMIENTO primary key (ID_INFO_MANTENIMIENTO)
);

/*==============================================================*/
/* Index: VENTA_INFO_MANTENIMIENTO_FK                           */
/*==============================================================*/
create index VENTA_INFO_MANTENIMIENTO_FK on INFO_MANTENIMIENTO (
   ID_VENTA ASC
);

/*==============================================================*/
/* Table: LISTADO_DOCUMENTO                                     */
/*==============================================================*/
create table LISTADO_DOCUMENTO 
(
   ID_LIST_DOCUMENTO    NUMBER(6)            not null,
   constraint PK_LISTADO_DOCUMENTO primary key (ID_LIST_DOCUMENTO)
);

/*==============================================================*/
/* Table: PAGO                                                  */
/*==============================================================*/
create table PAGO 
(
   ID_PAGO              NUMBER(6)            not null,
   ID_CUENTA_BANCARIA   NUMBER(6),
   ID_VENTA             NUMBER(6),
   FECHA                DATE,
   RECIBO               NUMBER,
   COMPROBANTE          VARCHAR2(50),
   TIPO                 VARCHAR2(20),
   MONTO                NUMBER(6,2),
   OTROS                NUMBER(6,2),
   DESCUENTO            NUMBER(6,2),
   OBSERVACIONES        VARCHAR2(500),
   constraint PK_PAGO primary key (ID_PAGO)
);

/*==============================================================*/
/* Index: PAGO_CUENTA_BANCARIA_FK                               */
/*==============================================================*/
create index PAGO_CUENTA_BANCARIA_FK on PAGO (
   ID_CUENTA_BANCARIA ASC
);

/*==============================================================*/
/* Index: VENTA_PAGO_FK                                         */
/*==============================================================*/
create index VENTA_PAGO_FK on PAGO (
   ID_VENTA ASC
);

/*==============================================================*/
/* Table: PERMISO                                               */
/*==============================================================*/
create table PERMISO 
(
   ID_PERMISO           NUMBER(6)            not null,
   NOMBRE               VARCHAR2(200),
   constraint PK_PERMISO primary key (ID_PERMISO)
);

/*==============================================================*/
/* Table: PERSONA                                               */
/*==============================================================*/
create table PERSONA 
(
   ID_PERSONA           NUMBER(6)            not null,
   DUI                  VARCHAR2(9),
   NOMBRE               VARCHAR2(200),
   APELLIDO             VARCHAR2(200),
   constraint PK_PERSONA primary key (ID_PERSONA)
);

/*==============================================================*/
/* Table: PROPIETARIO                                           */
/*==============================================================*/
create table PROPIETARIO 
(
   ID_PROPIETARIO       NUMBER(6)            not null,
   ID_PERSONA           NUMBER(6),
   ID_LIST_DOCUMENTO    NUMBER(6),
   PROFESION            VARCHAR2(200),
   DIRECCION_CASA       VARCHAR2(300),
   LUGAR_TRABAJO        VARCHAR2(200),
   DIRECCION_TRABAJO    VARCHAR2(300),
   constraint PK_PROPIETARIO primary key (ID_PROPIETARIO)
);

/*==============================================================*/
/* Index: PERSONA_PROPIETARIO_FK                                */
/*==============================================================*/
create index PERSONA_PROPIETARIO_FK on PROPIETARIO (
   ID_PERSONA ASC
);

/*==============================================================*/
/* Index: PROPIETARIO_LIST_DOCUMENT_FK                            */
/*==============================================================*/
create index PROPIETARIO_LIST_DOCUMENT_FK on PROPIETARIO (
   ID_LIST_DOCUMENTO ASC
);

/*==============================================================*/
/* Table: PROYECTO                                              */
/*==============================================================*/
create table PROYECTO 
(
   ID_PROYECTO          NUMBER(6)            not null,
   ID_EMPRESA           NUMBER(6),
   NOMBRE               VARCHAR2(200),
   constraint PK_PROYECTO primary key (ID_PROYECTO)
);

/*==============================================================*/
/* Index: PROYECTO_EMPRESA_FK                                   */
/*==============================================================*/
create index PROYECTO_EMPRESA_FK on PROYECTO (
   ID_EMPRESA ASC
);

/*==============================================================*/
/* Table: REFERENCIA                                            */
/*==============================================================*/
create table REFERENCIA 
(
   ID_REFERENCIA        NUMBER(6)            not null,
   ID_PROPIETARIO       NUMBER(6),
   NOMBRE               VARCHAR2(200),
   APELLIDO             VARCHAR2(200),
   TELEFONO             VARCHAR2(12),
   CORREO               VARCHAR2(150),
   constraint PK_REFERENCIA primary key (ID_REFERENCIA)
);

/*==============================================================*/
/* Index: PROPIETARIO_REFERENCIA_FK                             */
/*==============================================================*/
create index PROPIETARIO_REFERENCIA_FK on REFERENCIA (
   ID_PROPIETARIO ASC
);

/*==============================================================*/
/* Table: ROL                                                   */
/*==============================================================*/
create table ROL 
(
   ID_ROL               NUMBER(6)            not null,
   NOMBRE               VARCHAR2(200),
   constraint PK_ROL primary key (ID_ROL)
);

/*==============================================================*/
/* Table: ROL_PERMISO                                           */
/*==============================================================*/
create table ROL_PERMISO 
(
   ID_ROL               NUMBER(6)            not null,
   ID_PERMISO           NUMBER(6)            not null,
   constraint PK_ROL_PERMISO primary key (ID_ROL, ID_PERMISO)
);

/*==============================================================*/
/* Index: ROL_PERMISO_FK                                        */
/*==============================================================*/
create index ROL_PERMISO_FK on ROL_PERMISO (
   ID_ROL ASC
);

/*==============================================================*/
/* Index: ROL_PERMISO2_FK                                       */
/*==============================================================*/
create index ROL_PERMISO2_FK on ROL_PERMISO (
   ID_PERMISO ASC
);

/*==============================================================*/
/* Table: TELEFONO                                              */
/*==============================================================*/
create table TELEFONO 
(
   ID_TELEFONO          NUMBER(6)            not null,
   ID_PROPIETARIO       NUMBER(6),
   TIPO                 VARCHAR2(20),
   TELEFONO             VARCHAR2(12),
   constraint PK_TELEFONO primary key (ID_TELEFONO)
);

/*==============================================================*/
/* Index: PROPIETARIO_TELEFONO_FK                               */
/*==============================================================*/
create index PROPIETARIO_TELEFONO_FK on TELEFONO (
   ID_PROPIETARIO ASC
);

/*==============================================================*/
/* Table: TERRENO                                               */
/*==============================================================*/
create table TERRENO 
(
   ID_TERRENO           NUMBER(6)            not null,
   ID_PROYECTO          NUMBER(6),
   MATRICULA            VARCHAR2(18),
   NUMERO               NUMBER,
   POLIGONO             CHAR(1),
   SECCION              CHAR(1),
   AREA_METROS          NUMBER(6,2),
   AREA_VARAS           NUMBER(6,2),
   constraint PK_TERRENO primary key (ID_TERRENO)
);

/*==============================================================*/
/* Index: TERRENO_PROYECTO_FK                                   */
/*==============================================================*/
create index TERRENO_PROYECTO_FK on TERRENO (
   ID_PROYECTO ASC
);

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO 
(
   ID_USUARIO           NUMBER(6)            not null,
   USERNAME             VARCHAR2(50),
   PASSWORD             VARCHAR2(100),
   EMAIL                VARCHAR2(50),
   INTENTOS             NUMBER,
   BLOQUEADO            NUMBER(1),
   HABILITADO           NUMBER(1),
   constraint PK_USUARIO primary key (ID_USUARIO)
);

/*==============================================================*/
/* Table: USUARIO_ROL                                           */
/*==============================================================*/
create table USUARIO_ROL 
(
   ID_USUARIO           NUMBER(6)            not null,
   ID_ROL               NUMBER(6)            not null,
   constraint PK_USUARIO_ROL primary key (ID_USUARIO, ID_ROL)
);

/*==============================================================*/
/* Index: USUARIO_ROL_FK                                        */
/*==============================================================*/
create index USUARIO_ROL_FK on USUARIO_ROL (
   ID_USUARIO ASC
);

/*==============================================================*/
/* Index: USUARIO_ROL2_FK                                       */
/*==============================================================*/
create index USUARIO_ROL2_FK on USUARIO_ROL (
   ID_ROL ASC
);

/*==============================================================*/
/* Table: VEHICULO                                              */
/*==============================================================*/
create table VEHICULO 
(
   ID_VISITA            NUMBER(6)            not null,
   ID_VEHICULO          NUMBER(6),
   NUMERO_PLACA         VARCHAR2(7),
   COLOR                VARCHAR2(30),
   TIPO                 VARCHAR2(20)
);

/*==============================================================*/
/* Index: VEHICULO_VISITA_FK                                    */
/*==============================================================*/
create index VEHICULO_VISITA_FK on VEHICULO (
   ID_VISITA ASC
);

/*==============================================================*/
/* Table: VENTA                                                 */
/*==============================================================*/
create table VENTA 
(
   ID_VENTA             NUMBER(9)            not null,
   ID_LIST_DOCUMENTO    NUMBER(9),
   ID_TERRENO           NUMBER(9),
   FECHA                DATE,
   NOMBRE               VARCHAR2(200),
   PRECIO               NUMBER(9,2),
   DESCUENTO            NUMBER(9,2),
   MONTO                NUMBER(9,2),
   ESTADO               VARCHAR2(8),
   constraint PK_VENTA primary key (ID_VENTA)
);

/*==============================================================*/
/* Index: TERRENO_VENTA_FK                                      */
/*==============================================================*/
create index TERRENO_VENTA_FK on VENTA (
   ID_TERRENO ASC
);

/*==============================================================*/
/* Index: VENTA_LIST_DOCUMENTO_FK                               */
/*==============================================================*/
create index VENTA_LIST_DOCUMENTO_FK on VENTA (
   ID_LIST_DOCUMENTO ASC
);

/*==============================================================*/
/* Table: VISITA                                                */
/*==============================================================*/
create table VISITA 
(
   ID_VISITA            NUMBER(6)            not null,
   CANTIDAD_ADULTOS     NUMBER,
   CANTIDAD_NINOS       NUMBER,
   FECHA_ENTRADA        DATE,
   HORA_ENTRADA         DATE,
   FECHA_SALIDA         DATE,
   HORA_SALIDA          DATE,
   OBSERVACIONES        VARCHAR2(500),
   constraint PK_VISITA primary key (ID_VISITA)
);

/*==============================================================*/
/* Table: VISITANTE                                             */
/*==============================================================*/
create table VISITANTE 
(
   ID_VISITANTE         NUMBER(6)            not null,
   ID_LIST_DOCUMENTO    NUMBER(6),
   ID_PERSONA           NUMBER(6),
   ROL                  VARCHAR2(20),
   EMPLEADOR            VARCHAR2(200),
   constraint PK_VISITANTE primary key (ID_VISITANTE)
);

/*==============================================================*/
/* Index: VISITANTE_PERSONA_FK                                  */
/*==============================================================*/
create index VISITANTE_PERSONA_FK on VISITANTE (
   ID_PERSONA ASC
);

/*==============================================================*/
/* Index: VISITANTE_LIST_DOCUMENT_FK                            */
/*==============================================================*/
create index VISITANTE_LIST_DOCUMENT_FK on VISITANTE (
   ID_LIST_DOCUMENTO ASC
);


/*==============================================================*/
/* Table: CONFIGURACION_CORREO                                  */
/*==============================================================*/
create table CONFIGURACION_CORREO 
(
   ID_CONFIGURACION     NUMBER(9)            not null,
   NAME                 VARCHAR2(100),
   HOST                 VARCHAR2(50),
   PORT                 VARCHAR2(20),
   PROTOCOL             VARCHAR2(50),
   USERNAME             VARCHAR2(100),
   PASSWORD             VARCHAR2(100),
   SMTP_AUTH            NUMBER(1),
   START_TLS            NUMBER(1),
   constraint PK_CONFIGURACION_CORREO primary key (ID_CONFIGURACION)
);

/*==============================================================*/
/* Table: RESET_PASSWORD                                        */
/*==============================================================*/
create table RESET_PASSWORD 
(
   ID_ASIGNACION        NUMBER(9)            not null,
   USERNAME             VARCHAR2(100),
   TOKEN                VARCHAR2(300),
   FECHA                DATE,
   constraint PK_RESET_PASSWORD  primary key (ID_ASIGNACION)
);

alter table ASIGNACION_PROPIETARIO
   add constraint FK_ASIG_PROPIETARIO foreign key (ID_PROPIETARIO)
      references PROPIETARIO (ID_PROPIETARIO);

alter table ASIGNACION_PROPIETARIO
   add constraint FK_ASIG_PROPIETARIO_VENTA foreign key (ID_VENTA)
      references VENTA (ID_VENTA);

alter table ASIGNACION_VISITANTE
   add constraint FK_ASIG_VISITANTE foreign key (ID_VISITANTE)
      references VISITANTE (ID_VISITANTE);

alter table ASIGNACION_VISITANTE
   add constraint FK_ASIG_VISITANTE_VENTA foreign key (ID_VENTA)
      references VENTA (ID_VENTA);

alter table ASIGNACION_VISITA
   add constraint FK_ASIG_VISITA_VISITA foreign key (ID_VISITA)
      references VISITA (ID_VISITA);

alter table ASIGNACION_VISITA
   add constraint FK_ASIG_VISITA_VENTA foreign key (ID_VENTA)
      references VENTA (ID_VENTA);

alter table ASIGNACION_VISITA
   add constraint FK_ASIG_VISITA_PROPIETARIO foreign key (ID_PROPIETARIO)
      references PROPIETARIO (ID_PROPIETARIO);

alter table ASIGNACION_VISITA
   add constraint FK_ASIG_VISITA_VISITANTE foreign key (ID_VISITANTE)
      references VISITANTE (ID_VISITANTE);

alter table ASIGNACION_PROYECTO
   add constraint FK_ASIG_PROYECTO foreign key (ID_PROYECTO)
      references PROYECTO (ID_PROYECTO);

alter table ASIGNACION_PROYECTO
   add constraint FK_ASIG_PROYECTO_USUARIO foreign key (ID_USUARIO)
      references USUARIO (ID_USUARIO);

alter table ASIGNACION_EMPRESA
   add constraint FK_ASIG_EMPRESA foreign key (ID_EMPRESA)
      references EMPRESA (ID_EMPRESA);

alter table ASIGNACION_EMPRESA
   add constraint FK_ASIG_EMPRESA_USUARIO foreign key (ID_USUARIO)
      references USUARIO (ID_USUARIO);

alter table CORREO
   add constraint FK_CORREO_PROPIETARIO foreign key (ID_PROPIETARIO)
      references PROPIETARIO (ID_PROPIETARIO);

alter table CUENTA_BANCARIA
   add constraint FK_PROYECTO_CUENTA_BANCARIA foreign key (ID_PROYECTO)
      references PROYECTO (ID_PROYECTO);

alter table CUOTA_AMORTIZACION
   add constraint FK_AMORTIZACION_VENTA foreign key (ID_VENTA)
      references VENTA (ID_VENTA);

alter table CUOTA_FINANCIAMIENTO
   add constraint FK_FINANCIAMIENTO_PAGO foreign key (ID_PAGO)
      references PAGO (ID_PAGO);

alter table CUOTA_MANTENIMIENTO
   add constraint FK_MANTENIMIENTO_PAGO foreign key (ID_PAGO)
      references PAGO (ID_PAGO);

alter table DOCUMENTO
   add constraint FK_LIST_DOCUMENT_DOCUMENTO foreign key (ID_LIST_DOCUMENTO)
      references LISTADO_DOCUMENTO (ID_LIST_DOCUMENTO);

alter table FACTURACION
   add constraint FK_VENTA_FACTURACION foreign key (ID_VENTA)
      references VENTA (ID_VENTA);

alter table INFO_FINANCIAMIENTO
   add constraint FK_VENTA_INFO_FINANCIAMIENTO foreign key (ID_VENTA)
      references VENTA (ID_VENTA);

alter table INFO_MANTENIMIENTO
   add constraint FK_VENTA_INFO_MANTENIMIENTO foreign key (ID_VENTA)
      references VENTA (ID_VENTA);

alter table PAGO
   add constraint FK_PAGO_CUENTA_BANCARIA foreign key (ID_CUENTA_BANCARIA)
      references CUENTA_BANCARIA (ID_CUENTA_BANCARIA);

alter table PAGO
   add constraint FK_PAGO_VENTA foreign key (ID_VENTA)
      references VENTA (ID_VENTA);

alter table PROPIETARIO
   add constraint FK_PROPIETARIO_PERSONA foreign key (ID_PERSONA)
      references PERSONA (ID_PERSONA);

alter table PROYECTO
   add constraint FK_EMPRESA_PROYECTO foreign key (ID_EMPRESA)
      references EMPRESA (ID_EMPRESA);

alter table REFERENCIA
   add constraint FK_REFERENCIA_PROPIETARIO foreign key (ID_PROPIETARIO)
      references PROPIETARIO (ID_PROPIETARIO);

alter table ROL_PERMISO
   add constraint FK_ROL_PERMISO foreign key (ID_ROL)
      references ROL (ID_ROL);

alter table ROL_PERMISO
   add constraint FK_ROL_PERMISO_2 foreign key (ID_PERMISO)
      references PERMISO (ID_PERMISO);

alter table TELEFONO
   add constraint FK_TELEFONO_PROPIETARRIO foreign key (ID_PROPIETARIO)
      references PROPIETARIO (ID_PROPIETARIO);

alter table TERRENO
   add constraint FK_TERRENO_PROYECTO foreign key (ID_PROYECTO)
      references PROYECTO (ID_PROYECTO);

alter table USUARIO_ROL
   add constraint FK_USUARIO_ROL foreign key (ID_USUARIO)
      references USUARIO (ID_USUARIO);

alter table USUARIO_ROL
   add constraint FK_USUARIO_ROL_2 foreign key (ID_ROL)
      references ROL (ID_ROL);

alter table VEHICULO
   add constraint FK_VEHICULO_VISITA foreign key (ID_VISITA)
      references VISITA (ID_VISITA);

alter table VENTA
   add constraint FK_VENTA_TERRENO foreign key (ID_TERRENO)
      references TERRENO (ID_TERRENO);

alter table VENTA
   add constraint FK_VENTA_LIST_DOCUMENT foreign key (ID_LIST_DOCUMENTO)
      references LISTADO_DOCUMENTO (ID_LIST_DOCUMENTO);

alter table VISITANTE
   add constraint FK_VISITANTE_LIST_DOCUMENTO foreign key (ID_LIST_DOCUMENTO)
      references LISTADO_DOCUMENTO (ID_LIST_DOCUMENTO);

alter table VISITANTE
   add constraint FK_VISITANTE_PERSONA foreign key (ID_PERSONA)
      references PERSONA (ID_PERSONA);

--Vistas para la presentación de los datos
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

CREATE OR REPLACE VIEW VISTA_PROPIETARIOS_PROYECTO AS
SELECT
    T.ID_PROYECTO AS ID_PROYECTO,
    P.ID_PERSONA AS ID_PERSONA,
    P.DUI AS DUI,
    P.NOMBRE || ' ' ||  P.APELLIDO AS NOMBRE,
    LISTAGG(T.POLIGONO || '-' || T.NUMERO || T.SECCION, ', ') WITHIN GROUP (ORDER BY T.ID_PROYECTO, T.POLIGONO, T.NUMERO, T.SECCION) AS LOTES,
    C.CORREOS,
    TL.TELEFONOS
FROM
    propietario PR
    JOIN persona P ON PR.ID_PERSONA = P.ID_PERSONA
    JOIN asignacion_propietario AP ON PR.ID_PROPIETARIO = AP.ID_PROPIETARIO
    JOIN venta V ON AP.ID_VENTA = V.ID_VENTA
    JOIN terreno T ON V.ID_TERRENO = T.ID_TERRENO
    LEFT JOIN (
        SELECT ID_PROPIETARIO, LISTAGG(C.CORREO, ', ') WITHIN GROUP (ORDER BY C.ID_CORREO) AS CORREOS
        FROM correo C
        GROUP BY ID_PROPIETARIO
    ) C ON PR.ID_PROPIETARIO = C.ID_PROPIETARIO
    LEFT JOIN (
        SELECT ID_PROPIETARIO, LISTAGG(TL.TELEFONO, ', ') WITHIN GROUP (ORDER BY TL.ID_TELEFONO) AS TELEFONOS
        FROM telefono TL
        GROUP BY ID_PROPIETARIO
    ) TL ON PR.ID_PROPIETARIO = TL.ID_PROPIETARIO
GROUP BY
    T.ID_PROYECTO, P.ID_PERSONA, P.DUI, P.NOMBRE, P.APELLIDO, C.CORREOS, TL.TELEFONOS;


CREATE OR REPLACE VIEW VISTA_TRABAJADORES_PROYECTO AS
SELECT
    p.ID_PERSONA,
    t.ID_PROYECTO,
    p.DUI,
    p.NOMBRE || ' ' || p.APELLIDO AS NOMBRE,
    v.EMPLEADOR,
    LISTAGG(t.POLIGONO || '-' || t.NUMERO || t.SECCION, ', ') WITHIN GROUP (ORDER BY t.ID_TERRENO) AS LOTES
FROM
    persona p
    JOIN visitante v ON p.ID_PERSONA = v.ID_PERSONA
    JOIN asignacion_visitante av ON v.ID_VISITANTE = av.ID_VISITANTE
    JOIN venta ve ON av.ID_VENTA = ve.ID_VENTA
    JOIN terreno t ON ve.ID_TERRENO = t.ID_TERRENO
GROUP BY
    p.ID_PERSONA, t.ID_PROYECTO, p.DUI, p.NOMBRE, p.APELLIDO, v.EMPLEADOR;

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
    terreno T
JOIN
    venta V ON T.ID_TERRENO = V.ID_TERRENO
WHERE
    V.ESTADO = 'Activo';

-- Eliminar el trigger TRG_AGREGAR_PROPIETARIO
--DROP TRIGGER TRG_AGREGAR_PROPIETARIO;

-- Eliminar el trigger TRG_ELIMINAR_PROPIETARIO
--DROP TRIGGER TRG_ELIMINAR_PROPIETARIO;

CREATE OR REPLACE TRIGGER TRG_AGREGAR_PROPIETARIO
BEFORE INSERT ON PROPIETARIO
FOR EACH ROW
DECLARE
  id_lista_documento NUMBER;
BEGIN
  id_lista_documento := S_LISTADO_DOCUMENTO.NEXTVAL;

  -- Insertar el listado de documentos
  INSERT INTO LISTADO_DOCUMENTO (ID_LIST_DOCUMENTO) VALUES (id_lista_documento);
  :NEW.ID_LIST_DOCUMENTO := id_lista_documento;
END;
/

CREATE OR REPLACE TRIGGER TRG_ELIMINAR_PROPIETARIO
AFTER DELETE ON PROPIETARIO
FOR EACH ROW
DECLARE
  id_lista_documento NUMBER;
BEGIN
  id_lista_documento := :OLD.ID_LIST_DOCUMENTO;
  DELETE FROM DOCUMENTO WHERE ID_LIST_DOCUMENTO = id_lista_documento;
  DELETE FROM LISTADO_DOCUMENTO WHERE ID_LIST_DOCUMENTO = id_lista_documento;
END;
/

-- Eliminar el trigger TRG_AGREGAR_VISITANTE
--DROP TRIGGER TRG_AGREGAR_VISITANTE;

-- Eliminar el trigger TRG_ELIMINAR_VISITANTE
--DROP TRIGGER TRG_ELIMINAR_VISITANTE;

CREATE OR REPLACE TRIGGER TRG_AGREGAR_VISITANTE
BEFORE INSERT ON VISITANTE
FOR EACH ROW
DECLARE
  id_lista_documento NUMBER;
BEGIN
  id_lista_documento := S_LISTADO_DOCUMENTO.NEXTVAL;

  -- Insertar el listado de documentos
  INSERT INTO LISTADO_DOCUMENTO (ID_LIST_DOCUMENTO) VALUES (id_lista_documento);
  :NEW.ID_LIST_DOCUMENTO := id_lista_documento;
END;
/

CREATE OR REPLACE TRIGGER TRG_ELIMINAR_VISITANTE
AFTER DELETE ON VISITANTE
FOR EACH ROW
DECLARE
  id_lista_documento NUMBER;
BEGIN
  id_lista_documento := :OLD.ID_LIST_DOCUMENTO;
  DELETE FROM DOCUMENTO WHERE ID_LIST_DOCUMENTO = id_lista_documento;
  DELETE FROM LISTADO_DOCUMENTO WHERE ID_LIST_DOCUMENTO = id_lista_documento;
END;
/

-- Eliminar el trigger TRG_AGREGAR_VENTA
--DROP TRIGGER TRG_AGREGAR_VENTA;

-- Eliminar el trigger TRG_ELIMINAR_VENTA
--DROP TRIGGER TRG_ELIMINAR_VENTA;

CREATE OR REPLACE TRIGGER TRG_AGREGAR_VENTA
BEFORE INSERT ON VENTA
FOR EACH ROW
DECLARE
  id_lista_documento NUMBER;
BEGIN
  id_lista_documento := S_LISTADO_DOCUMENTO.NEXTVAL;

  -- Insertar el listado de documentos
  INSERT INTO LISTADO_DOCUMENTO (ID_LIST_DOCUMENTO) VALUES (id_lista_documento);
  :NEW.ID_LIST_DOCUMENTO := id_lista_documento;
END;
/

CREATE OR REPLACE TRIGGER TRG_ELIMINAR_VENTA
AFTER DELETE ON VENTA
FOR EACH ROW
DECLARE
  id_lista_documento NUMBER;
BEGIN
  id_lista_documento := :OLD.ID_LIST_DOCUMENTO;
  DELETE FROM DOCUMENTO WHERE ID_LIST_DOCUMENTO = id_lista_documento;
  DELETE FROM LISTADO_DOCUMENTO WHERE ID_LIST_DOCUMENTO = id_lista_documento;
END;
/

--Eliminación de registros de token para el restablecimiento de contraseña no utilizados
CREATE OR REPLACE PROCEDURE ELIMINAR_REGISTROS_CADUCADOS AS
BEGIN
  DELETE FROM RESET_PASSWORD WHERE FECHA <= SYSDATE;
  COMMIT;
END;
/
BEGIN
  DBMS_SCHEDULER.drop_job('ELIMINAR_REGISTROS_CADUCADOS_JOB');
END;
/
BEGIN
  DBMS_SCHEDULER.create_job(
    job_name        => 'ELIMINAR_REGISTROS_CADUCADOS_JOB',
    job_type        => 'PLSQL_BLOCK',
    job_action      => 'BEGIN ELIMINAR_REGISTROS_CADUCADOS; END;',
    start_date      => SYSTIMESTAMP,
    repeat_interval => 'FREQ=DAILY; BYHOUR=10; BYMINUTE=0; BYSECOND=0',
    enabled         => TRUE
  );
END;
/
