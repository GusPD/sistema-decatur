-- Usuario
INSERT INTO Usuario (nombre, username, password, email, habilitado, bloqueado, intentos) 
VALUES ('Gustavo Pineda','gustavo', '$2a$10$AEMBf.4hcftpIAKcUuWpbeYRI5HYNZZlP7cDSTetbRyCSEyq0d1Nq', 'gustavopineda400@gmail.com', TRUE, FALSE, 0);

-- Rol
INSERT INTO Rol (nombre) VALUES ('ADMINISTRADOR');

-- Usuario_roles
INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (1, 1);

-- Permiso
INSERT INTO Permiso (nombre) VALUES
('GESTIONAR_USUARIO_PRIVILAGE'),
('AGREGAR_USUARIO_PRIVILAGE'),
('EDITAR_USUARIO_PRIVILAGE'),
('ELIMINAR_USUARIO_PRIVILAGE'),
('EXPORTAR_USUARIO_PRIVILAGE'),
('GESTIONAR_ROL_PRIVILAGE'),
('AGREGAR_ROL_PRIVILAGE'),
('EDITAR_ROL_PRIVILAGE'),
('ELIMINAR_ROL_PRIVILAGE'),
('EXPORTAR_ROL_PRIVILAGE'),
('GESTIONAR_BITACORA_PRIVILAGE'),
('EXPORTAR_BITACORA_PRIVILAGE'),
('GESTIONAR_TERRENO_PRIVILAGE'),
('AGREGAR_TERRENO_PRIVILAGE'),
('EDITAR_TERRENO_PRIVILAGE'),
('ELIMINAR_TERRENO_PRIVILAGE'),
('VER_TERRENO_PRIVILAGE'),
('EXPORTAR_TERRENO_PRIVILAGE'),
('GESTIONAR_PROYECTO_PRIVILAGE'),
('AGREGAR_PROYECTO_PRIVILAGE'),
('EDITAR_PROYECTO_PRIVILAGE'),
('ELIMINAR_PROYECTO_PRIVILAGE'),
('VER_PROYECTO_PRIVILAGE'),
('EXPORTAR_PROYECTO_PRIVILAGE'),
('GESTIONAR_EMPRESA_PRIVILAGE'),
('AGREGAR_EMPRESA_PRIVILAGE'),
('EDITAR_EMPRESA_PRIVILAGE'),
('ELIMINAR_EMPRESA_PRIVILAGE'),
('VER_EMPRESA_PRIVILAGE'),
('EXPORTAR_EMPRESA_PRIVILAGE'),
('GESTIONAR_PROPIETARIO_PRIVILAGE'),
('VER_PROPIETARIO_PRIVILAGE'),
('AGREGAR_PROPIETARIO_PRIVILAGE'),
('SELECCIONAR_PROPIETARIO_PRIVILAGE'),
('EDITAR_PROPIETARIO_PRIVILAGE'),
('ELIMINAR_PROPIETARIO_PRIVILAGE'),
('EXPORTAR_PROPIETARIO_PRIVILAGE'),
('AGREGAR_CORREO_PROPIETARIO_PRIVILAGE'),
('ELIMINAR_CORREO_PROPIETARIO_PRIVILAGE'),
('AGREGAR_TELEFONO_PROPIETARIO_PRIVILAGE'),
('ELIMINAR_TELEFONO_PROPIETARIO_PRIVILAGE'),
('AGREGAR_REFERENCIA_PROPIETARIO_PRIVILAGE'),
('ELIMINAR_REFERENCIA_PROPIETARIO_PRIVILAGE'),
('VER_DOCUMENTO_PROPIETARIO_PRIVILAGE'),
('AGREGAR_DOCUMENTO_PROPIETARIO_PRIVILAGE'),
('ELIMINAR_DOCUMENTO_PROPIETARIO_PRIVILAGE'),
('GESTIONAR_TRABAJADOR_PRIVILAGE'),
('VER_TRABAJADOR_PRIVILAGE'),
('AGREGAR_TRABAJADOR_PRIVILAGE'),
('SELECCIONAR_TRABAJADOR_PRIVILAGE'),
('EDITAR_TRABAJADOR_PRIVILAGE'),
('ELIMINAR_TRABAJADOR_PRIVILAGE'),
('EXPORTAR_TRABAJADOR_PRIVILAGE'),
('VER_DOCUMENTO_TRABAJADOR_PRIVILAGE'),
('AGREGAR_DOCUMENTO_TRABAJADOR_PRIVILAGE'),
('ELIMINAR_DOCUMENTO_TRABAJADOR_PRIVILAGE'),
('GESTIONAR_VENTA_PRIVILAGE'),
('VER_VENTA_PRIVILAGE'),
('AGREGAR_VENTA_PRIVILAGE'),
('EDITAR_VENTA_PRIVILAGE'),
('ELIMINAR_VENTA_PRIVILAGE'),
('EXPORTAR_VENTA_PRIVILAGE'),
('AGREGAR_INFORMACION_FINANCIAMIENTO_PRIVILAGE'),
('ELIMINAR_INFORMACION_FINANCIAMIENTO_PRIVILAGE'),
('AGREGAR_INFORMACION_MANTENIMIENTO_PRIVILAGE'),
('ELIMINAR_INFORMACION_MANTENIMIENTO_PRIVILAGE'),
('VER_DOCUMENTO_VENTA_PRIVILAGE'),
('AGREGAR_DOCUMENTO_VENTA_PRIVILAGE'),
('ELIMINAR_DOCUMENTO_VENTA_PRIVILAGE'),
('ENVIO_CORREO_PRIVILAGE'),
('EDITAR_FACTURACION_PRIVILAGE'),
('GESTIONAR_DATOS_PROYECTO_PRIVILAGE'),
('GESTIONAR_PAGO_PRIVILAGE'),
('EXPORTAR_PAGO_PRIVILAGE'),
('VER_PAGO_PRIVILAGE'),
('AGREGAR_PAGO_PRIVILAGE'),
('EDITAR_PAGO_PRIVILAGE'),
('ELIMINAR_PAGO_PRIVILAGE'),
('GESTIONAR_CUENTA_BANCARIA_PRIVILAGE'),
('EXPORTAR_CUENTA_BANCARIA_PRIVILAGE'),
('AGREGAR_CUENTA_BANCARIA_PRIVILAGE'),
('EDITAR_CUENTA_BANCARIA_PRIVILAGE'),
('ELIMINAR_CUENTA_BANCARIA_PRIVILAGE'),
('GESTIONAR_VISITAS_PROYECTO_PRIVILAGE'),
('GESTIONAR_CONFIGURACION_CORREO_PRIVILAGE'),
('AGREGAR_CONFIGURACION_CORREO_PRIVILAGE'),
('EDITAR_CONFIGURACION_CORREO_PRIVILAGE'),
('ELIMINAR_CONFIGURACION_CORREO_PRIVILAGE'),
('EXPORTAR_CONFIGURACION_CORREO_PRIVILAGE'),
('AGREGAR_ESTADO_CUENTA_MANTENIMIENTO_PRIVILAGE'),
('ELIMINAR_ESTADO_CUENTA_MANTENIMIENTO_PRIVILAGE'),
('EXPORTAR_ESTADO_CUENTA_MANTENIMIENTO_PRIVILAGE'),
('AGREGAR_ESTADO_CUENTA_FINANCIAMIENTO_PRIVILAGE'),
('ELIMINAR_ESTADO_CUENTA_FINANCIAMIENTO_PRIVILAGE'),
('EXPORTAR_ESTADO_CUENTA_FINANCIAMIENTO_PRIVILAGE'),
('EXPORTAR_PAGO_VENTA_PRIVILAGE'),
('EXPORTAR_PRIMA_VENTA_PRIVILAGE'),
('GESTIONAR_ENVIO_CORREO_ELECTRONICO_PRIVILAGE'),
('ENVIAR_CORREO_ELECTRONICO_PRIVILAGE'),
('GESTIONAR_CONTACTO_PRIVILAGE'),
('AGREGAR_CONTACTO_PRIVILAGE'),
('ELIMINAR_CONTACTO_PRIVILAGE'),
('EXPORTAR_CONTACTO_PRIVILAGE'),
('GESTIONAR_MONITOREO_PAGO_PRIVILAGE'),
('EXPORTAR_MONITOREO_MANTENIMIENTO_PRIVILAGE');

-- Roles_permisos
INSERT INTO rol_permiso (id_rol, id_permiso) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11);

-- Configuración de correo
INSERT INTO configuracion_correo (name, host, port, protocol, username, password, smtp_auth, start_tls, servidor, verificado)
VALUES ('Sistema Decatur', 'smtp.gmail.com', '587', 'smtp', 'mail.projects.notification@gmail.com', 'klqfenqcgqiaxhpg', TRUE, TRUE, TRUE, TRUE);

-- Tipo de documento
INSERT INTO tipo_documento (nombre, mascara)
VALUES ('DUI', '^\d{9}$');

CREATE ROLE decatur WITH LOGIN PASSWORD 'admin755';
ALTER USER decatur WITH SUPERUSER;