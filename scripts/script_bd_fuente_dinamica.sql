DROP DATABASE IF EXISTS fuente_dinamica;
CREATE DATABASE fuente_dinamica;
USE fuente_dinamica;

CREATE TABLE categoria (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50),
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

CREATE TABLE hecho (
    anonimato BIT,
    categoria_id BIGINT,
    fecha_acontecimiento DATETIME(6),
    fecha_carga DATETIME(6),
    fecha_ultima_modificacion DATETIME(6),
    ubicacion_id BIGINT,
    titulo VARCHAR(200),
    contenido_texto VARCHAR(500),
    descripcion VARCHAR(1000),
    autor_id VARCHAR(255),
    id VARCHAR(255) NOT NULL,
    sugerencia VARCHAR(255),
    estado_revision ENUM ('PENDIENTE','ACEPTADO','RECHAZADO'),
    origen ENUM ('CONTRIBUYENTE'),
    PRIMARY KEY (id)
);

CREATE TABLE hecho_contenido_multimedia (
    contenido_multimedia_id BIGINT NOT NULL,
    hecho_id VARCHAR(255) NOT NULL
);

CREATE TABLE multimedia (
    id BIGINT NOT NULL AUTO_INCREMENT,
    url VARCHAR(500),
    PRIMARY KEY (id)
);

CREATE TABLE revision_hecho (
    fecha DATETIME(6),
    id BIGINT NOT NULL AUTO_INCREMENT,
    administrador_id VARCHAR(255),
    hecho_id VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE ubicacion (
    latitud FLOAT(53),
    longitud FLOAT(53),
    id BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)
);

-- Foreign Keys
ALTER TABLE hecho 
ADD CONSTRAINT FKmkc51vo6ljr261nkr9wx7ttba 
FOREIGN KEY (autor_id) 
REFERENCES contribuyente (id);

ALTER TABLE hecho 
ADD CONSTRAINT FKfxqo9x3difya3cmevhhu3wqcf 
FOREIGN KEY (categoria_id) 
REFERENCES categoria (id);

ALTER TABLE hecho 
ADD CONSTRAINT FKkfvjlwwdwdue1jec72j940ssh 
FOREIGN KEY (ubicacion_id) 
REFERENCES ubicacion (id);

ALTER TABLE hecho_contenido_multimedia 
ADD CONSTRAINT FKkiere939u2vomp5pyh8au9vp4 
FOREIGN KEY (contenido_multimedia_id) 
REFERENCES multimedia (id);

ALTER TABLE hecho_contenido_multimedia 
ADD CONSTRAINT FKpft7jmadg3o98116ld9qd61ew 
FOREIGN KEY (hecho_id) 
REFERENCES hecho (id);

ALTER TABLE revision_hecho 
ADD CONSTRAINT FK9anl0yju1orluhn3nbq6xqvp1 
FOREIGN KEY (administrador_id) 
REFERENCES contribuyente (id);

ALTER TABLE revision_hecho 
ADD CONSTRAINT FK4mr2xu554pbhojp97bmyvahi4 
FOREIGN KEY (hecho_id) 
REFERENCES hecho (id);

-- Datos de prueba
INSERT INTO categoria (id, nombre) VALUES
(1, 'Historia'),
(2, 'Ciencia');

INSERT INTO contribuyente (id, nombre, apellido, mail, fecha_nacimiento, es_administrador)
VALUES
('u001', 'Carlos', 'López', 'carlos@example.com', '1992-02-15', 0),
('u002', 'María', 'Suárez', 'maria@example.com', '1990-07-20', 1);

INSERT INTO ubicacion (id, latitud, longitud)
VALUES
(1, -34.60, -58.38),
(2, -31.42, -64.18);

INSERT INTO hecho (
    id, titulo, descripcion, contenido_texto, categoria_id,
    autor_id, ubicacion_id, anonimato, fecha_acontecimiento,
    fecha_carga, fecha_ultima_modificacion, sugerencia, estado_revision, origen
)
VALUES
('hd001', 'Incidente Histórico', 'Descripción breve...', 'Contenido...', 1,
 'u001', 1, 0, '1890-10-10 10:00:00',
 '2025-12-10 09:00:00', '2025-12-10 09:00:00', NULL, 'ACEPTADO', 'CONTRIBUYENTE'),

('hd002', 'Nuevo Avance', 'Descubrimiento reciente', 'Texto...', 2,
 'u002', 2, 1, '2022-09-11 14:00:00',
 '2025-12-09 18:30:00', '2025-12-09 18:30:00', 'Mejorar la descripción', 'PENDIENTE', 'CONTRIBUYENTE');

INSERT INTO multimedia (id, url)
VALUES
(1, 'https://example.com/x1.jpg'),
(2, 'https://example.com/x2.jpg');

INSERT INTO hecho_contenido_multimedia (contenido_multimedia_id, hecho_id)
VALUES
(1, 'hd001'),
(2, 'hd002');

INSERT INTO revision_hecho (id, fecha, administrador_id, hecho_id)
VALUES
(1, '2025-12-10 12:00:00', 'u002', 'hd001');