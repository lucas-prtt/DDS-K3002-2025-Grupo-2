## Servicio Agregador
 Se encarga de consultar las distintas fuentes de datos y combinar sus datos. Deberá planificarse
(utilizar un schedule) para la llamada del agregador a los servicios de las diferentes fuentes.
 El objetivo del agregador es combinar y depurar datos y no implicará acceso por parte de usuarios
finales al mismo a través de interfaz de usuario.
 Este servicio utiliza el mecanismo de rechazos de solicitudes de eliminación spam en forma
automática definido en la Entrega 2
 Este servicio implementará una funcionalidad de depuración de datos que analizará los diferentes
datos y buscará registros duplicados (las decisiones de arquitectura y diseño en general sobre esta
funcionalidad corresponderá a cada grupo)
 Este servicio tratará la normalización de hechos e información según se define debajo.


## Normalización de Hechos e Información
Una de las principales problemáticas que surgen al integrar servicios externos (como fuentes proxy o
datasets provistos por terceros) es la inconsistencia en la representación de los datos. Las distintas fuentes
de hechos pueden utilizar diferentes convenciones para nombres de categorías, formatos de fecha u otros
atributos clave.
Por ejemplo, un mismo hecho podría estar categorizado como “Incendio Forestal” en una fuente, “fuego
forestal” en otra. Estas diferencias pueden hacer que los hechos recibidos de distintos orígenes parezcan
diferentes, aunque en la realidad representan la misma pieza de información.
Por este motivo, antes de persistir los datos en un medio relacional, es fundamental aplicar un proceso de
normalización que estandarice los valores clave. Este proceso de curaduría deberá contemplar mapeo de
categorías, validar y convertir fechas y resolver ambigüedades entre los hechos.
Esta normalización será parte del servicio agregador (las decisiones de arquitectura y diseño en general
sobre esta funcionalidad corresponderá a cada grupo)

## API Administrativa de MetaMapa
Corresponde a la API REST indicada en la Entrega 3 y se deberán exponer las siguientes operaciones
consultando y operando sobre la base de datos:
● Operaciones CRUD sobre las colecciones.
● Modificación del algoritmo de consenso.
● Agregar o quitar fuentes de hechos de una colección.
● Aprobar o denegar una solicitud de eliminación de un hecho.
## API Pública para otras instancias de MetaMapa
Corresponde a la API REST indicada en la Entrega 3 y se deberán exponer las siguientes operaciones
consultando y operando sobre la base de datos:
● Consulta de hechos dentro de una colección.
● Generar una solicitud de eliminación a un hecho.
● Navegación filtrada sobre una colección.
● Navegación curada o irrestricta sobre una colección.
● Reportar un hecho.

## Módulos centrales
Además el sistema central contará con:
● un módulo de visualizaciones y administración de datos,
● un módulo de estadísticas, que podrán ser consultadas tanto online a través de una interfaz Web
como consumidas por otras ONGs,
● un módulo para la recepción de denuncias sobre el contenido y protección de datos personales.
Por el momento no se cuenta con información respecto a los últimos 2 módulos, mientras que el módulo de
visualizaciones y administración de datos incorporará todas las funcionalidades (mas allá de los servicios
mencionados anteriormente) que posee MetaMapa integrándose con la API administrativa correspondiente