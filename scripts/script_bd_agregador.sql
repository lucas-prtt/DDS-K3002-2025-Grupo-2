DROP DATABASE IF EXISTS agregador;
CREATE DATABASE agregador;
USE agregador;

CREATE TABLE categoria (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50),
  PRIMARY KEY (id)
);

CREATE TABLE coleccion (
  titulo VARCHAR(50),
  descripcion VARCHAR(150),
  id VARCHAR(255) NOT NULL,
  tipo_algoritmo_consenso ENUM ('IRRESTRICTO','MAYORIA_SIMPLE','MULTIPLES_MENCIONES','ABSOLUTO'),
  PRIMARY KEY (id)
);

CREATE TABLE contribuyente (
  es_administrador BIT,
  fecha_nacimiento DATE,
  apellido VARCHAR(20),
  nombre VARCHAR(20),
  id VARCHAR(255) NOT NULL,
  mail VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE criterio_de_distancia (
  distancia_maxima FLOAT(53),
  latitud FLOAT(53),
  longitud FLOAT(53),
  id BIGINT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE criterio_de_fecha (
  fecha_final DATETIME(6),
  fecha_inicial DATETIME(6),
  id BIGINT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE criterio_de_pertenencia (
  id BIGINT NOT NULL AUTO_INCREMENT,
  coleccion_id VARCHAR(255),
  tipo VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE estado_solicitud (
  id BIGINT NOT NULL AUTO_INCREMENT,
  dtype VARCHAR(31) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE etiqueta (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50),
  PRIMARY KEY (id)
);

CREATE TABLE fuente (
  ultima_peticion DATETIME(6),
  alias VARCHAR(255),
  id VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE fuente_coleccion (
  coleccion_id VARCHAR(255) NOT NULL,
  fuente_id VARCHAR(255) NOT NULL
);

CREATE TABLE fuente_dinamica (
  id VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE fuente_estatica (
  fue_consultada BIT,
  id VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE fuente_proxy (
  id VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE hecho (
  anonimato BIT,
  latitud FLOAT(53),
  longitud FLOAT(53),
  visible BIT,
  categoria_id BIGINT,
  fecha_acontecimiento DATETIME(6),
  fecha_carga DATETIME(6),
  fecha_ultima_modificacion DATETIME(6),
  titulo VARCHAR(200),
  contenido_texto VARCHAR(500),
  descripcion VARCHAR(1000),
  autor_id VARCHAR(255),
  id VARCHAR(255) NOT NULL,
  origen ENUM ('CARGA_MANUAL','DATASET','CONTRIBUYENTE','EXTERNO'),
  PRIMARY KEY (id)
);

CREATE TABLE hecho_coleccion (
  consensuado BIT NOT NULL,
  coleccion_id VARCHAR(255) NOT NULL,
  hecho_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (coleccion_id, hecho_id)
);

CREATE TABLE hecho_etiqueta (
  etiqueta_id BIGINT NOT NULL,
  hecho_id VARCHAR(255) NOT NULL
);

CREATE TABLE hecho_fuente (
  fuente_id VARCHAR(255) NOT NULL,
  hecho_id VARCHAR(255) NOT NULL
);

CREATE TABLE multimedia (
  id BIGINT NOT NULL AUTO_INCREMENT,
  url VARCHAR(500),
  hecho_id VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE solicitud_eliminacion (
  estado_id BIGINT,
  fecha_resolucion DATETIME(6),
  fecha_subida DATETIME(6),
  id BIGINT NOT NULL AUTO_INCREMENT,
  motivo VARCHAR(2000),
  administrador_id VARCHAR(255),
  hecho_id VARCHAR(255),
  solicitante_id VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE INDEX idx_hecho_titulo 
ON hecho (titulo);

ALTER TABLE solicitud_eliminacion 
ADD CONSTRAINT UK_2mkkxxppf51jlfjsktv37mph6 
UNIQUE (estado_id);

ALTER TABLE criterio_de_distancia 
ADD CONSTRAINT FKd50mh9ienqhxx774bferudbja 
FOREIGN KEY (id) 
REFERENCES criterio_de_pertenencia (id);

ALTER TABLE criterio_de_fecha 
ADD CONSTRAINT FK5ud8788t45gka7k44997qgrhn 
FOREIGN KEY (id) 
REFERENCES criterio_de_pertenencia (id);

ALTER TABLE criterio_de_pertenencia 
ADD CONSTRAINT FKf8xx2dmeexxbtnr32ts2iok46 
FOREIGN KEY (coleccion_id) 
REFERENCES coleccion (id);

ALTER TABLE fuente_coleccion 
ADD CONSTRAINT FK2kom8oiye1djhtemx521gumok 
FOREIGN KEY (fuente_id) 
REFERENCES fuente (id);

ALTER TABLE fuente_coleccion 
ADD CONSTRAINT FK1njmqwmhnb08bmcy54x4o7vru 
FOREIGN KEY (coleccion_id) 
REFERENCES coleccion (id);

ALTER TABLE fuente_dinamica 
ADD CONSTRAINT FKghan2q7v7l235i7im3ecrbwpc 
FOREIGN KEY (id) 
REFERENCES fuente (id);

ALTER TABLE fuente_estatica 
ADD CONSTRAINT FKoyv10349beolkjfn5mokdupii 
FOREIGN KEY (id) 
REFERENCES fuente (id);

ALTER TABLE fuente_proxy 
ADD CONSTRAINT FKlb2hy55pjhu3l55d737umb399 
FOREIGN KEY (id) 
REFERENCES fuente (id);

ALTER TABLE hecho 
ADD CONSTRAINT FKmkc51vo6ljr261nkr9wx7ttba 
FOREIGN KEY (autor_id) 
REFERENCES contribuyente (id);

ALTER TABLE hecho 
ADD CONSTRAINT FKfxqo9x3difya3cmevhhu3wqcf 
FOREIGN KEY (categoria_id) 
REFERENCES categoria (id);

ALTER TABLE hecho_coleccion 
ADD CONSTRAINT FKf7y6vt2wuxtngwuquqjv7jeiq 
FOREIGN KEY (coleccion_id) 
REFERENCES coleccion (id);

ALTER TABLE hecho_coleccion 
ADD CONSTRAINT FK9ioaby1akswxxxqub3srs5g5u 
FOREIGN KEY (hecho_id) 
REFERENCES hecho (id);

ALTER TABLE hecho_etiqueta 
ADD CONSTRAINT FK6b0b6suy1yewhc50velm6ofer 
FOREIGN KEY (etiqueta_id) 
REFERENCES etiqueta (id);

ALTER TABLE hecho_etiqueta 
ADD CONSTRAINT FKg7uc345phhykol9f8ubasvjnb 
FOREIGN KEY (hecho_id) 
REFERENCES hecho (id);

ALTER TABLE hecho_fuente 
ADD CONSTRAINT FKt3qd78wmvw1va6vl8wkfxg68j 
FOREIGN KEY (hecho_id) 
REFERENCES hecho (id);

ALTER TABLE hecho_fuente 
ADD CONSTRAINT FK4a5cqvdt9vqwhi03r2lyqqahf 
FOREIGN KEY (fuente_id) 
REFERENCES fuente (id);

ALTER TABLE multimedia 
ADD CONSTRAINT FKhuvcbgnq5qhvcpgextavng2xr 
FOREIGN KEY (hecho_id) 
REFERENCES hecho (id);

ALTER TABLE solicitud_eliminacion 
ADD CONSTRAINT FKi3c3hlf300n4k10ps9y3wfy29 
FOREIGN KEY (administrador_id) 
REFERENCES contribuyente (id);

ALTER TABLE solicitud_eliminacion 
ADD CONSTRAINT FK6y22qkjycsr6131w43kk5k51g 
FOREIGN KEY (estado_id) 
REFERENCES estado_solicitud (id);

ALTER TABLE solicitud_eliminacion 
ADD CONSTRAINT FKdsurmbwawpst9tef87ihtv3e5 
FOREIGN KEY (hecho_id) 
REFERENCES hecho (id);

ALTER TABLE solicitud_eliminacion 
ADD CONSTRAINT FK6gc21yheuacpdfql2td6mp43l 
FOREIGN KEY (solicitante_id) 
REFERENCES contribuyente (id);

-- Datos de prueba
INSERT INTO categoria (id, nombre) VALUES
(1, 'Historia'),
(2, 'Ciencia'),
(3, 'Política');

INSERT INTO contribuyente (id, nombre, apellido, mail, fecha_nacimiento, es_administrador)
VALUES
('u001', 'Juan', 'Pérez', 'juan@example.com', '1990-05-10', 0),
('u002', 'Ana', 'Gómez', 'ana@example.com', '1988-09-20', 1);

INSERT INTO etiqueta (id, nombre) VALUES
(1, 'Importante'),
(2, 'Histórico'),
(3, 'Urgente');

INSERT INTO coleccion (id, titulo, descripcion, tipo_algoritmo_consenso)
VALUES
('col001', 'Eventos Argentinos', 'Hechos históricos relevantes', 'MAYORIA_SIMPLE'),
('col002', 'Ciencia Moderna', 'Eventos científicos destacados', 'IRRESTRICTO');

INSERT INTO criterio_de_pertenencia (id, coleccion_id, tipo)
VALUES
(1, 'col001', 'DISTANCIA'),
(2, 'col002', 'FECHA');

INSERT INTO criterio_de_distancia (id, distancia_maxima, latitud, longitud)
VALUES
(1, 50.0, -34.60, -58.38);

INSERT INTO criterio_de_fecha (id, fecha_inicial, fecha_final)
VALUES
(2, '2020-01-01', '2025-12-31');

INSERT INTO fuente (id, alias, ultima_peticion)
VALUES
('f001', 'API Pública Argentina', '2025-12-10 12:00:00'),
('f002', 'Dataset Conae', '2025-12-11 15:30:00');

INSERT INTO fuente_dinamica (id) VALUES ('f001');
INSERT INTO fuente_estatica (id, fue_consultada) VALUES ('f002', 1);

INSERT INTO hecho (
    id, titulo, descripcion, contenido_texto, categoria_id, autor_id,
    latitud, longitud, anonimato, visible,
    fecha_acontecimiento, fecha_carga, fecha_ultima_modificacion, origen
)
VALUES
('h001', 'Batalla de Obligado', 'Conflicto histórico del siglo XIX',
 'Descripción extendida...', 1, 'u001', -34.58, -58.42, 0, 1,
 '1845-11-20 10:00:00', '2025-12-10 12:00:00', '2025-12-10 12:10:00', 'CONTRIBUYENTE'),

('h002', 'Descubrimiento en Física', 'Avance científico argentino',
 'Texto detallado...', 2, 'u002', -31.42, -64.18, 1, 1,
 '2023-05-11 09:00:00', '2025-12-09 16:20:00', '2025-12-09 16:20:00', 'CONTRIBUYENTE');

INSERT INTO hecho_coleccion (coleccion_id, hecho_id, consensuado)
VALUES
('col001', 'h001', 1),
('col002', 'h002', 0);

INSERT INTO hecho_etiqueta (etiqueta_id, hecho_id)
VALUES
(2, 'h001'),
(1, 'h002');

INSERT INTO hecho_fuente (fuente_id, hecho_id)
VALUES
('f001', 'h001'),
('f002', 'h002');

INSERT INTO multimedia (url, hecho_id)
VALUES
('https://example.com/imagen1.jpg', 'h001'),
('https://example.com/imagen2.jpg', 'h002');

INSERT INTO estado_solicitud (id, dtype) VALUES
(1, 'Pendiente'),
(2, 'Aprobada');

INSERT INTO solicitud_eliminacion (
    id, motivo, fecha_subida, estado_id, solicitante_id, hecho_id
)
VALUES
(1, 'Información incorrecta', '2025-12-10 11:30:00', 1, 'u001', 'h001');