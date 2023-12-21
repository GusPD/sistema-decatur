-- Usuario
INSERT INTO Usuario (nombre, username, password, email, habilitado, bloqueado, intentos) 
VALUES ('Gustavo Pineda','gustavo', '$2a$10$AEMBf.4hcftpIAKcUuWpbeYRI5HYNZZlP7cDSTetbRyCSEyq0d1Nq', 'gustavopineda400@gmail.com', TRUE, FALSE, 0);

-- Rol
INSERT INTO Rol (nombre) VALUES ('ADMINISTRADOR');

-- Usuario_roles
INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (1, 1);

-- Permiso
INSERT INTO Permiso (nombre) VALUES
('VER_USUARIO_PRIVILAGE'),
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
('AGREGAR_PROPIETARIO_PRIVILAGE'),
('SELECCIONAR_PROPIETARIO_PRIVILAGE'),
('EDITAR_PROPIETARIO_PRIVILAGE'),
('ELIMINAR_PROPIETARIO_PRIVILAGE'),
('EXPORTAR_PROPIETARIO_PRIVILAGE'),
('GESTIONAR_INFORMACION_PROPIETARIO_PRIVILAGE'),
('GESTIONAR_CORREO_PROPIETARIO_PRIVILAGE'),
('AGREGAR_CORREO_PROPIETARIO_PRIVILAGE'),
('ELIMINAR_CORREO_PROPIETARIO_PRIVILAGE'),
('GESTIONAR_TELEFONO_PROPIETARIO_PRIVILAGE'),
('AGREGAR_TELEFONO_PROPIETARIO_PRIVILAGE'),
('ELIMINAR_TELEFONO_PROPIETARIO_PRIVILAGE'),
('GESTIONAR_REFERENCIA_PROPIETARIO_PRIVILAGE'),
('AGREGAR_REFERENCIA_PROPIETARIO_PRIVILAGE'),
('ELIMINAR_REFERENCIA_PROPIETARIO_PRIVILAGE'),
('GESTIONAR_DOCUMENTO_PROPIETARIO_PRIVILAGE'),
('VER_DOCUMENTO_PROPIETARIO_PRIVILAGE'),
('AGREGAR_DOCUMENTO_PROPIETARIO_PRIVILAGE'),
('ELIMINAR_DOCUMENTO_PROPIETARIO_PRIVILAGE'),
('GESTIONAR_TERRENOS_PROPIETARIO_PRIVILAGE'),
('GESTIONAR_TRABAJADOR_PRIVILAGE'),
('AGREGAR_TRABAJADOR_PRIVILAGE'),
('SELECCIONAR_TRABAJADOR_PRIVILAGE'),
('EDITAR_TRABAJADOR_PRIVILAGE'),
('ELIMINAR_TRABAJADOR_PRIVILAGE'),
('EXPORTAR_TRABAJADOR_PRIVILAGE'),
('GESTIONAR_INFORMACION_TRABAJADOR_PRIVILAGE'),
('GESTIONAR_DOCUMENTO_TRABAJADOR_PRIVILAGE'),
('VER_DOCUMENTO_TRABAJADOR_PRIVILAGE'),
('AGREGAR_DOCUMENTO_TRABAJADOR_PRIVILAGE'),
('ELIMINAR_DOCUMENTO_TRABAJADOR_PRIVILAGE'),
('GESTIONAR_TERRENOS_TRABAJADOR_PRIVILAGE'),
('VER_VENTA_PRIVILAGE'),
('GESTIONAR_VENTA_PRIVILAGE'),
('AGREGAR_VENTA_PRIVILAGE'),
('EDITAR_VENTA_PRIVILAGE'),
('ELIMINAR_VENTA_PRIVILAGE'),
('EXPORTAR_VENTA_PRIVILAGE'),
('GESTIONAR_INFORMACION_VENTA_PRIVILAGE'),
('AGREGAR_INFORMACION_FINANCIAMIENTO_PRIVILAGE'),
('ELIMINAR_INFORMACION_FINANCIAMIENTO_PRIVILAGE'),
('AGREGAR_INFORMACION_MANTENIMIENTO_PRIVILAGE'),
('ELIMINAR_INFORMACION_MANTENIMIENTO_PRIVILAGE'),
('GESTIONAR_PROPIETARIOS_VENTA_PRIVILAGE'),
('GESTIONAR_TRABAJADORES_VENTA_PRIVILAGE'),
('GESTIONAR_DOCUMENTO_VENTA_PRIVILAGE'),
('VER_DOCUMENTO_VENTA_PRIVILAGE'),
('AGREGAR_DOCUMENTO_VENTA_PRIVILAGE'),
('ELIMINAR_DOCUMENTO_VENTA_PRIVILAGE'),
('GESTIONAR_FACTURACION_VENTA_PRIVILAGE'),
('EDITAR_FACTURACION_PRIVILAGE'),
('ELIMINAR_FACTURACION_PRIVILAGE'),
('GESTIONAR_PAGOS_VENTA_PRIVILAGE'),
('VER_PAGO_VENTA_PRIVILAGE'),
('AGREGAR_PAGO_VENTA_PRIVILAGE'),
('EDITAR_PAGO_VENTA_PRIVILAGE'),
('ELIMINAR_PAGO_VENTA_PRIVILAGE'),
('EXPORTAR_PAGO_VENTA_PRIVILAGE'),
('GESTIONAR_MANTENIMIENTO_VENTA_PRIVILAGE'),
('AGREGAR_ESTADO_CUENTA_MANTENIMIENTO_PRIVILAGE'),
('ELIMINAR_ESTADO_CUENTA_MANTENIMIENTO_PRIVILAGE'),
('EXPORTAR_ESTADO_CUENTA_MANTENIMIENTO_PRIVILAGE'),
('GESTIONAR_FINANCIAMIENTO_VENTA_PRIVILAGE'),
('AGREGAR_ESTADO_CUENTA_FINANCIAMIENTO_PRIVILAGE'),
('ELIMINAR_ESTADO_CUENTA_FINANCIAMIENTO_PRIVILAGE'),
('EXPORTAR_ESTADO_CUENTA_FINANCIAMIENTO_PRIVILAGE'),
('GESTIONAR_PRIMA_VENTA_PRIVILAGE'),
('EXPORTAR_PRIMA_VENTA_PRIVILAGE'),
('ENVIO_CORREO_PRIVILAGE'),
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
('GESTIONAR_ENVIO_CORREO_ELECTRONICO_PRIVILAGE'),
('ENVIAR_CORREO_ELECTRONICO_PRIVILAGE'),
('GESTIONAR_CONTACTO_PRIVILAGE'),
('AGREGAR_CONTACTO_PRIVILAGE'),
('ELIMINAR_CONTACTO_PRIVILAGE'),
('EXPORTAR_CONTACTO_PRIVILAGE'),
('GESTIONAR_MONITOREO_MANTENIMIENTO_PRIVILAGE'),
('EXPORTAR_MONITOREO_MANTENIMIENTO_PRIVILAGE'),
('AGREGAR_BACKUP_PRIVILAGE'),
('EDITAR_BACKUP_PRIVILAGE'),
('ELIMINAR_BACKUP_PRIVILAGE'),
('EXPORTAR_BACKUP_PRIVILAGE'),
('GESTIONAR_BACKUP_PRIVILAGE');

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

INSERT INTO Departamento (nombre) VALUES
('Ahuachapán'),
('Cabañas'),
('Chalatenango'),
('Cuscatlán'),
('La Libertad'),
('La Paz'),
('La Unión'),
('Morazán'),
('San Miguel'),
('San Salvador'),
('San Vicente'),
('Santa Ana'),
('Sonsonate'),
('Usulután');

INSERT INTO Municipio (id_departamento, nombre) VALUES
('1', 'Atiquizaya'),
('1', 'El Refugio'),
('1', 'San Lorenzo'),
('1', 'Turín'),
('1', 'Ahuachapán'),
('1', 'Apaneca'),
('1', 'Concepción de Ataco'),
('1', 'Tacuba'),
('1', 'Guaymango'),
('1', 'Jujutla'),
('1', 'San Francisco Menéndez'),
('1', 'San Pedro Puxtla'),
('2', 'Dolores'),
('2', 'Guacotecti'),
('2', 'Sensuntepeque'),
('2', 'San Isidro'),
('2', 'Victoria'),
('2', 'Cinquera'),
('2', 'Ilobasco'),
('2', 'Jutiapa'),
('2', 'Tejutepeque'),
('3', 'La Palma'),
('3', 'Citalá'),
('3', 'San Ignacio'),
('3', 'Agua Caliente'),
('3', 'Dulce Nombre de María'),
('3', 'El Paraíso'),
('3', 'La Reina'),
('3', 'Nueva Concepción'),
('3', 'San Fernando'),
('3', 'San Francisco Morazán'),
('3', 'San Rafael'),
('3', 'Santa Rita'),
('3', 'Tejutla'),
('3', 'Arcatao'),
('3', 'Azacualpa'),
('3', 'Chalatenango'),
('3', 'Comalapa'),
('3', 'Concepción Quezaltepeque'),
('3', 'El Carrizal'),
('3', 'La Laguna'),
('3', 'Las Vueltas'),
('3', 'Nombre de Jesús'),
('3', 'Nueva Trinidad'),
('3', 'Ojos de Agua'),
('3', 'Potonico'),
('3', 'San Antonio de la Cruz (San Antonio La Cruz)'),
('3', 'San Antonio Los Ranchos (San Antonio Ranchos)'),
('3', 'San Francisco Lempa'),
('3', 'San Isidro Labrador'),
('3', 'San José Cancasque'),
('3', 'San José Las Flores'),
('3', 'San Luis del Carmen'),
('3', 'San Miguel de Mercedes'),
('4', 'Oratorio de Concepción'),
('4', 'San Bartolomé Perulapía'),
('4', 'San José Guayabal'),
('4', 'San Pedro Perulapán'),
('4', 'Suchitoto'),
('4', 'Candelaria'),
('4', 'Cojutepeque'),
('4', 'El Carmen'),
('4', 'El Rosario'),
('4', 'Monte San Juan'),
('4', 'San Cristóbal'),
('4', 'San Rafael Cedros'),
('4', 'San Ramón'),
('4', 'Santa Cruz Analquito'),
('4', 'Santa Cruz Michapa'),
('4', 'Tenancingo'),
('5', 'Quezaltepeque'),
('5', 'San Matías'),
('5', 'San Pablo Tacachico'),
('5', 'Ciudad Arce'),
('5', 'San Juan Opico'),
('5', 'Colón'),
('5', 'Jayaque'),
('5', 'Sacacoyo'),
('5', 'Talnique'),
('5', 'Tepecoyo'),
('5', 'Antiguo Cuscatlán'),
('5', 'Huizúcar'),
('5', 'Nuevo Cuscatlán'),
('5', 'San José Villanueva'),
('5', 'Zaragoza'),
('5', 'Coastal La Libertad'),
('5', 'Chiltiupán'),
('5', 'Jicalapa'),
('5', 'La Libertad'),
('5', 'Tamanique'),
('5', 'Teotepeque'),
('5', 'Comasagua'),
('5', 'Santa Tecla'),
('6', 'Cuyultitán'),
('6', 'Olocuilta'),
('6', 'San Francisco Chinameca'),
('6', 'San Juan Talpa'),
('6', 'San Luis Talpa'),
('6', 'San Pedro Masahuat'),
('6', 'Tapalhuaca'),
('6', 'El Rosario'),
('6', 'Jerusalén'),
('6', 'Mercedes La Ceiba'),
('6', 'Paraíso de Osorio'),
('6', 'San Antonio Masahuat'),
('6', 'San Emigdio'),
('6', 'San Juan Tepezontes'),
('6', 'San Luis La Herradura'),
('6', 'San Miguel Tepezontes'),
('6', 'San Pedro Nonualco'),
('6', 'Santa María Ostuma'),
('6', 'Santiago Nonualco'),
('6', 'San Juan Nonualco'),
('6', 'San Rafael Obrajuelo'),
('6', 'Zacatecoluca'),
('7', 'Anamorós'),
('7', 'Bolívar'),
('7', 'Concepción de Oriente'),
('7', 'El Sauce'),
('7', 'Lislique'),
('7', 'Nueva Esparta'),
('7', 'Pasaquina'),
('7', 'Polorós'),
('7', 'San José'),
('7', 'Santa Rosa de Lima'),
('7', 'Conchagua'),
('7', 'El Carmen'),
('7', 'Intipucá'),
('7', 'La Unión'),
('7', 'Meanguera del Golfo'),
('7', 'San Alejo'),
('7', 'Yayantique'),
('7', 'Yucuaiquín'),
('8', 'Arambala'),
('8', 'Cacaopera'),
('8', 'Corinto'),
('8', 'El Rosario'),
('8', 'Joateca'),
('8', 'Jocoaitique'),
('8', 'Meanguera'),
('8', 'Perquín'),
('8', 'San Fernando'),
('8', 'San Isidro'),
('8', 'Torola'),
('8', 'Chilanga'),
('8', 'Delicias de Concepción'),
('8', 'El Divisadero'),
('8', 'Gualococti'),
('8', 'Guatajiagua'),
('8', 'Jocoro'),
('8', 'Lolotiquillo'),
('8', 'Osicala'),
('8', 'San Carlos'),
('8', 'San Francisco Gotera'),
('8', 'San Simón'),
('8', 'Sensembra'),
('8', 'Sociedad'),
('8', 'Yamabal'),
('8', 'Yoloaiquín'),
('9', 'Carolina'),
('9', 'Chapeltique'),
('9', 'Ciudad Barrios'),
('9', 'Nuevo Edén de San Juan'),
('9', 'San Antonio'),
('9', 'San Gerardo'),
('9', 'San Luis de la Reina'),
('9', 'Sesori'),
('9', 'Chirilagua'),
('9', 'Comacarán'),
('9', 'Moncagua'),
('9', 'Quelepa'),
('9', 'Uluazapa'),
('9', 'San Miguel'),
('9', 'Chinameca'),
('9', 'El Tránsito'),
('9', 'Lolotique'),
('9', 'Nueva Guadalupe'),
('9', 'San Jorge'),
('9', 'San Rafael (San Rafael Oriente)'),
('10', 'Aguilares'),
('10', 'El Paisnal'),
('10', 'Guazapa'),
('10', 'Apopa'),
('10', 'Nejapa'),
('10', 'Cuscatancingo'),
('10', 'Delgado'),
('10', 'Ilopango'),
('10', 'San Martín'),
('10', 'Soyapango'),
('10', 'Tonacatepeque'),
('10', 'Ayutuxtepeque'),
('10', 'Mejicanos'),
('10', 'San Marcos'),
('10', 'San Salvador'),
('10', 'Santiago Texacuangos'),
('10', 'Santo Tomás'),
('10', 'Panchimalco'),
('10', 'Rosario de Mora'),
('11', 'Apastepeque'),
('11', 'San Esteban Catarina'),
('11', 'San Ildefonso'),
('11', 'San Lorenzo'),
('11', 'San Sebastián'),
('11', 'Santa Clara'),
('11', 'Santo Domingo'),
('11', 'Guadalupe'),
('11', 'San Cayetano Istepeque'),
('11', 'San Vicente'),
('11', 'Tecoluca'),
('11', 'Tepetitán'),
('11', 'Verapaz'),
('12', 'Masahuat'),
('12', 'Metapán'),
('12', 'Santa Rosa Guachipilín'),
('12', 'Texistepeque'),
('12', 'Coatepeque'),
('12', 'El Congo'),
('12', 'Candelaria de la Frontera'),
('12', 'Chalchuapa'),
('12', 'El Porvenir'),
('12', 'San Antonio Pajonal'),
('12', 'San Sebastián Salitrillo'),
('12', 'Santiago de la Frontera'),
('13', 'Juayúa'),
('13', 'Nahuizalco'),
('13', 'Salcoatitán'),
('13', 'Santa Catarina Masahuat'),
('13', 'Nahulingo'),
('13', 'San Antonio del Monte'),
('13', 'Sonsonate'),
('13', 'Santo Domingo'),
('13', 'Sonzacate'),
('13', 'Armenia'),
('13', 'Caluco'),
('13', 'Cuisnahuat'),
('13', 'Izalco'),
('13', 'San Julián'),
('13', 'Santa Isabel Ishuatán'),
('13', 'Acajutla'),
('14', 'Alegría'),
('14', 'Berlín'),
('14', 'El Triunfo'),
('14', 'Estanzuelas'),
('14', 'Jucuapa'),
('14', 'Mercedes Umaña'),
('14', 'Nueva Granada'),
('14', 'San Buenaventura'),
('14', 'Santiago de María'),
('14', 'California'),
('14', 'Concepción Batres'),
('14', 'Ereguayquín'),
('14', 'Jucuarán'),
('14', 'Ozatlán'),
('14', 'San Dionisio'),
('14', 'Santa Elena'),
('14', 'Santa María'),
('14', 'Tecapán'),
('14', 'Usulután'),
('14', 'Jiquilisco'),
('14', 'Puerto El Triunfo'),
('14', 'San Francisco Javier'),
('14', 'San Agustín');

CREATE ROLE decatur WITH LOGIN PASSWORD 'admin755';
ALTER USER decatur WITH SUPERUSER;