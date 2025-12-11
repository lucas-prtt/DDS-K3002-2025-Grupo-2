DROP DATABASE IF EXISTS estadisticas;
CREATE DATABASE estadisticas;
USE estadisticas;

CREATE TABLE ConfiguracionGlobal (
    clave VARCHAR(255) NOT NULL, 
    valor VARCHAR(255), 
    PRIMARY KEY (clave)
);

CREATE TABLE DimensionCategoria (
    categoria_id BIGINT NOT NULL AUTO_INCREMENT, 
    nombre VARCHAR(50), 
    PRIMARY KEY (categoria_id)
);

CREATE TABLE DimensionColeccion (
    coleccionId BIGINT NOT NULL AUTO_INCREMENT, 
    titulo VARCHAR(50), 
    descripcion VARCHAR(150), 
    idColeccionAgregador VARCHAR(255), 
    PRIMARY KEY (coleccionId)
);

CREATE TABLE DimensionTiempo (
    anio INTEGER, 
    hora INTEGER, 
    tiempo_id BIGINT NOT NULL AUTO_INCREMENT, 
    PRIMARY KEY (tiempo_id)
);

CREATE TABLE DimensionUbicacion (
    ubicacion_id BIGINT NOT NULL AUTO_INCREMENT, 
    pais VARCHAR(255), 
    provincia VARCHAR(255), 
    PRIMARY KEY (ubicacion_id)
);

CREATE TABLE FactColeccion (
    cantidadHechos BIGINT, 
    categoriaId BIGINT NOT NULL, 
    coleccionId BIGINT NOT NULL, 
    ubicacionId BIGINT NOT NULL, 
    PRIMARY KEY (categoriaId, coleccionId, ubicacionId)
);

CREATE TABLE FactHecho (
    cantidadDeHechos BIGINT, 
    categoriaId BIGINT NOT NULL, 
    tiempoId BIGINT NOT NULL, 
    ubicacionId BIGINT NOT NULL, 
    PRIMARY KEY (categoriaId, tiempoId, ubicacionId)
);

CREATE TABLE FactSolicitud (
    cantidadDeSolicitudes BIGINT, 
    nombreEstado VARCHAR(255) NOT NULL, 
    PRIMARY KEY (nombreEstado)
);

-- Foreign Keys
ALTER TABLE FactColeccion 
ADD CONSTRAINT FK6r9ivpm9tnt5nj9tmmkbvqwnf 
FOREIGN KEY (categoriaId) 
REFERENCES DimensionCategoria (categoria_id);

ALTER TABLE FactColeccion 
ADD CONSTRAINT FKolxjcjq2mtiuap8htx8dun75d 
FOREIGN KEY (coleccionId) 
REFERENCES DimensionColeccion (coleccionId);

ALTER TABLE FactColeccion 
ADD CONSTRAINT FKccvw1du4f698yp689c871xj03 
FOREIGN KEY (ubicacionId) 
REFERENCES DimensionUbicacion (ubicacion_id);

ALTER TABLE FactHecho 
ADD CONSTRAINT FK312p8oqbu3mypir8l152icqoj 
FOREIGN KEY (categoriaId) 
REFERENCES DimensionCategoria (categoria_id);

ALTER TABLE FactHecho 
ADD CONSTRAINT FK3mam3dmklc8gjqorrii11dpfp 
FOREIGN KEY (tiempoId) 
REFERENCES DimensionTiempo (tiempo_id);

ALTER TABLE FactHecho 
ADD CONSTRAINT FKrk9cro9luld1artpd6y24guyk 
FOREIGN KEY (ubicacionId) 
REFERENCES DimensionUbicacion (ubicacion_id);

-- Datos de prueba
INSERT INTO ConfiguracionGlobal (clave, valor) VALUES
('version', '1.0'),
('ultima_actualizacion', '2025-12-10');

INSERT INTO DimensionCategoria (categoria_id, nombre)
VALUES
(1, 'Historia'),
(2, 'Ciencia');

INSERT INTO DimensionColeccion (coleccionId, titulo, descripcion, idColeccionAgregador)
VALUES
(1, 'Eventos', 'Hechos históricos relevantes', 'col001'),
(2, 'Avances', 'Eventos científicos', 'col002');

INSERT INTO DimensionTiempo (tiempo_id, anio, hora)
VALUES
(1, 2024, 14),
(2, 2025, 16);

INSERT INTO DimensionUbicacion (ubicacion_id, pais, provincia)
VALUES
(1, 'Argentina', 'Buenos Aires'),
(2, 'Argentina', 'Córdoba');

INSERT INTO FactColeccion (categoriaId, coleccionId, ubicacionId, cantidadHechos)
VALUES
(1, 1, 1, 12),
(2, 2, 2, 8);

INSERT INTO FactHecho (categoriaId, tiempoId, ubicacionId, cantidadDeHechos)
VALUES
(1, 1, 1, 5),
(2, 2, 2, 4);

INSERT INTO FactSolicitud (nombreEstado, cantidadDeSolicitudes)
VALUES
('PENDIENTE', 3),
('ACEPTADO', 6),
('RECHAZADO', 1);