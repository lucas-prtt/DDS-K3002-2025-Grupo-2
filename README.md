# Metamapa - Sistema de gestion de mapeos colaborativos
Trabajo práctico anual de la materia Diseño de Sistemas (UTN FRBA) desarrollado de forma colaborativa como proyecto integrador backend + frontend.

## Descripcion general
MetaMapa es un Sistema de código abierto diseñado para la recopilación, visibilización y mapeo colaborativo de información. Está diseñado para que otras ONG, universidades u organismos estatales puedan instalarlo en sus servidores, gestionarlo y ofrecerlo para sus comunidades, en forma de instancias particulares. A través de un sistema distribuido de fuentes de datos, criterios de consenso y protección de identidades metaMapa permite a usuarios y organizaciones consultar, contribuir y gestionar información geolocalizada.

## Arquitectura del Sistema

MetaMapa está construido sobre una arquitectura de microservicios, orientada a garantizar escalabilidad, mantenibilidad y una clara separación de responsabilidades entre los distintos componentes del sistema.

La plataforma se organiza en los siguientes módulos principales:

- ### Agregador
  Núcleo central del sistema. Gestiona colecciones, hechos y usuarios, y coordina la interacción entre los distintos servicios.

- ### API Pública
  Interfaz REST destinada a consultas públicas, accesible sin autenticación.

- ### API Administrativa
  Servicio encargado de la gestión de usuarios, identidades, permisos y solicitudes administrativas.

- ### Fuentes de Datos
  Módulos especializados que encapsulan el acceso y procesamiento de distintos tipos de fuentes de información.

- ### Modulo de estadisticas
  Modulo opcional que se conecta directamente a la base de datos del agregador para importar datos a una base de datos OLAP para la generacion de estadisticas de manera periodica.

- ### Interfaz
  Servicio que expone la interfaz web a los usuarios permitiendo el uso del sistema.


##  Tecnologías Utilizadas
### Backend
- Java Spring Boot
- Hibernate / JPA
- Bases de datos: MySQL, H2
- Autenticación y autorización: OAuth 2.0 con Keycloak

### Frontend
- Thymeleaf (Motor de plantillas)
- Tailwind CSS
- JavaScript
- HTML5

### Infraestructura y DevOps
- Docker (containerización de servicios)
- Maven (gestión de dependencias y build)
- Eureka (service discovery)
- AWS EC2 (despliegue en la nube)

### Monitoreo y Observabilidad
- Prometheus (métricas)
- Grafana (visualización)
- Loki (logs centralizados)
- Zipkin (trazabilidad distribuida)



## Integrantes:

- Agustín Nicolás Herzkovich  
- Ezequiel Su  
- Julián Zorreguieta  
- Lucas Martín Presotto  
- Martín Alberto Rincón  
- Nicolás Mauro Piacentini  
- Simón Alejandro Rojas Pereira
