package pruebaapx;

public class PruebaAPX {

    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
/*
***El proyecto de Unidad Funcional está configurado como un proyecto Maven con unos proyectos hijos, que agrupan los distintos artefactos, teniendo todos los proyectos estructurados como una serie de pom.xml organizados en cascada. Construyendo el proyecto a nivel del pom.xml de la unidad funcional, se creará en la carpeta “target” una estructura de carpetas con los jares correspondientes. 
De dicha carpeta “target”, colgarán una serie de carpetas que se corresponden con los mismos que los subproyectos de la Unidad Funcional.
VERDADERO
***Menciona si lo siguiente es Verdadero o Falso
Dentro de los casos de uso de la Arquitectura de APX Y con el fin de estandarizar las aplicaciones del Banco se plantean dos paradigmas son compatibles con BBVA:
    • Backend Banca Tradicional
    • Servicios Distribuidos Backend
VERDADERO
***De la lista siguiente marca 2 funcionalidades de servicios Core incluidos en APX:
    • Soporte Transaccional JTA/XA
    • Thread Affinity
***De la lista siguiente marca 2 funcionalidades de bancarios incluidas en APX:
    • Autorización y perfilado
    • Registro de operaciones
***¿Cuáles son los protocolos lógicos disponibles en APX? Selecciona 2
    • AQPG (Formato Arquitectura Plataforma QP05: síncrono o asíncrono)
    • PS9
***¿Cuáles son las condiciones que debe de cumplir las capacidades de escalado dinámico de la arquitectura?
    • Incremento no lineal del sistema para validar tiempos de aprovisionamiento de nuevas instancias aplicativas.
    • Todas las respuestas
    • Incremento lineal de carga en el sistema
    • Decremento de la carga en el sistema
    • Prueba de estabilidad del sistema
***Los patrones de diseño sirven de apoyo para la búsqueda de soluciones a problemas comunes a la hora del desarrollo de software. De la siguiente lista ¿Cuáles de los siguientes son un patrón de diseño?
    • CRUD en librerías
    • Paginación en librerías
    • DTOs en componentes APX
    • Paginación en transacciones
*** La descripción de la Capa de Control es: Componente recibe la información necesaria para hacer el distpach de la ejecución de transacciones. Se crea a la vez que la sesión de contexto transaccional, para permitir que la arquitectura pueda ejecutar la lógica de negocio. proporciona el ámbito transaccional por lo que es capaz de confirmar (commit) o deshacer (rooll-back) ejecuciones. Este componente es el que gestiona la definición de transacción de acuerdo con la petición y ejecuta las pre y post acciones relacionadas con la lógica de negocio.
    VERDADERO
***Como se llama la unidad funcional sobre la que se va depositando los componentes con los que se construye los Servicios Backend que se publiquen en el entorno de R3(PaaS) de APX. 
    • Maven
***Menciona si lo siguiente es verdadero o falso:
Estos módulos encapsulan toda la funcionalidad relacionada con las operaciones bancarias como  diario o registro de operaciones. Hay dos puntos claves en relación con estos servicios:
1. No deben afectar al rendimiento ejecución de la lógica de negocio por lo que el diario y el registro de las operaciones han de usar colas internas para desacoplar su propia ejecución de la ejecución de la lógica de negocio
2. Estos módulos son responsables de datos que pueden ser almacenados en el diario del MainFrame o en el propio de la arquitectura. Por lo tanto, servicio de diario tiene que ser capaz de almacenar los datos del diario en ambos sitios.
    • VERDADERO
***¿Cuáles son los tres canales del Broad Network Acces de las clouds públicas?
    • REST-HTTP, SOAP-HTTP, SOAP-JMS (MQ o Rabbit)
***¿Cuál de las siguientes NO es una característica Cloud APX?
    • Componentes adicionales para PaaS: descriptores, memoria, gestión de perfiles.
***¿Cuáles de las siguientes oraciones son verdaderas? Selecciona tres:
    • No  se debe manejar el objeto Datasource directamente.
    • No se debe instanciar, ni abrir, ni cerrar explícitamente conexiones a base de datos.
    • Una UUAA solo puede acceder a los datos que ella misma custodia.
    • Una UUAA puede acceder a datos de terceros a través de librerías APX que el dueño del dato proporcione.
***Menciona si lo siguiente es verdadero o falso:
       En las buenas prácticas de logging de aplicaciones los niveles más comunes son DEBUG, INFO, WARNING y ERROR.
    • VERDADERO 
***Menciona si la descripción mencionada es verdadera o falsa:
       DEBUG, para información de muy bajo nivel solo útil para el debug de la aplicación, tanto en el desarrollo como en el análisis de incidencias.
    • Paradas y arranques de servicios y sistemas
    • Parámetros críticos o relevantes de configuración
    • Comienzo y fin de transacciones y operaciones completas
    • Cambios de estado de operaciones
    • FALSO 
***Estos tipos de librerías se pueden combinar entre sí para tener una librería con diferentes capacidades.
    • SHELL Y JDBC
***Menciona si lo siguiente es falso o verdadero:
      La arquitectura batch publica un conector WebService que admite solicitudes para la       ejecución de procesos batch ejecutadas a través del portal de desarrollo. Las aplicaciones que requieran la ejecución de procesos batch bajo demanda, también solicitan la ejecución a través de esta interfaz WebService. Este último escenario únicamente se está produciendo en entornos BBVA CIB.
    • VERDADERO
***Menciona si la definición de ERROR es verdadera o falsa:
       ERROR: información de situaciones, que aún sin ser de error, si son anómalas o no previstas,    aunque el aplicativo tiene alternativas para solventarlas.
    • FALSO
***¿Cuáles son las características que proporciona la Arquitectura Bancaria de Ejecución Batch, basada en Spring Batch?
    • Soporte Multi-BBDD mediante Datasources configurables.
    • Gestión rearranques de procesos batch en caso de error.
    • Posibilidad de Commits parciales.
    • Integración con Planificadores: BMC Control-M, Tivoli OPC, Quartz
***De la lista siguiente indique cuales son las invocaciones restringidas en la Arquitectura Batch:
    • Invocación WebServices, Servicios REST, Acceso a recursos externos vía HTTP y uso de Pre y Post Acciones.  
***Nombre de las carpetas donde se colocarán los archivos temporales de entrada y salida para los procesos batch    
    • /fichtemcomp/datent
    • /fichtemcomp/datsal
***Nomenclatura utilizada para nombrar los archivos de entrada o salida para procesos Batch
    • PUUAA_DXX_fecha_nombrefichero
***¿Cuáles son los paquetes spring que utiliza APX?
    • Spring AOP, ASM, Aspects, Core, Expression Language, JMS, JDBC, Retry, Transaction
***Son funcionalidades de la Base de datos / persistencia APX:
    • Soporte para CLOB/BLOB en Oracle
    • Two-Phase Commit entre BBDD Relacionales 
    • Conector NO SQL MongoDB
***APX permite invocación asíncrona de transacciones
    • Verdadero
***¿Cuáles son las excepciones permitidas por APX?
    • com.bbva.apx.exception.business.BusinessException
    • com.bbva.apx.exception.io.network.TimeoutException
    • com.bbva.apx.exception.db.DuplicateKeyException
    • com.bbva.apx.exception.db.NoResultException
    • com.bbva.apx.exception.db.TimeoutException
***¿Cuál es la función de IMS Connect?
    • Conector para Host
***¿Qué utilería se utiliza en una librería para consumir un servicio ASO?
    • Multichannel and Technical Interbackend
***¿Cuál es la ruta donde se encuentran los archivos de Deployed dentro del servidor?
    • D:\ENTORNO_LOCAL_APX\JBOSS\standalone\deployments
***¿Cuál es la utilería que permite envío de mensajes a través de Twitter, SMS, eMail?
    • Communication Manager
***¿Cuál es la utilidad para realizar que permite definir reglas de negocio en APX?
    • Drools
***¿Para que sirve la anotación @Mock:?
    • Indica a Junit que la propiedad donde se haya usado esta anotación es un mock, y por lo tanto, se inicializa como tal y es susceptible a ser inyectado por @InjectMocks.
***¿Cuál es la librería que proporciona APX para el cálculo de una fecha hábil?
    • QWYPRX40
***¿Un batch se puede ejecutar vía JMS?
    • Verdadero
***¿Cuál es la dependencia para la escritura de archivos Excel?
    • <dependency>
    	<groupId>com.bbva.elara</groupId>
    	<artifactId>elara-batch-poi</artifactId>
    	<version>${elara.version}</version>
    </dependency>
***¿Como se crean los paquetes Bundles?
    • Con el uso del Maven Plug in Bundle y el empaquetamiento del Bundle
***¿Cuál es la excepción que se envía en caso que un servicio APX falle?
    • BusinessException XXXXXXXXXx
    • RuntimeException

***¿Qué librería permite calcular y validar el IBAN?
    • QWYPRX62
***Si la librería es ONLINE, en el pom.xml de la interfaz y la implementación de la librería, será necesario añadir la dependencia del configuration manager application de batch con la etiqueta optional a true:
    <!—Dependenciasde batch-->
    <dependency>
        <groupId>com.bbva.elara</groupId>
        <artifactId>elara-batch</artifactId>
        <version>${elara.version}</version>
        <type>pom</type>
        <optional>true</optional>
    </dependency>
    • FALSO

***¿Cuáles son los ejemplos de patrones de diseño para APX?
    • Paginación en librerías, CRUD en librerías, DTOs en componentes APX
***¿Cuáles son los protocolos físicos disponibles en APX? Puede ser más de una
    • HTTP/REST/RESTFul (A través de servicios Backend)
    • HTTP/SOAP
***¿Warn son las situaciones que impiden la ejecución correcta de una operación o transacción, pero sin afectar a otras operaciones o transacciones (error aislado o contenido)?
    • Falso
***¿Cómo debe realizarse la implementación del DTOs?
    • Dentro de un empaquetamiento independiente (Bundle DTO) de los componentes de APX de más alto nivel (Transacciones, Librerías y Batch).
***¿Cuál es el número máximo de librerías a invocar desde una transacción?
    • 9 Librerías
***Para crear proyectos de servicios Back-End se podrá invocar el asistente de diferentes maneras: (se puede seleccionar más de una opción)
    • Desde el menú contextual específico de APX
    • Desde el menú APX
    • Desde la acción general del eclipse de creación de proyectos(New-> Project….) desde la que selecciona el asistente o wizard con el que queremos trabajar. Encontraremos el asistente dentro de la carpeta APX.
    • Desde el icono de servicio BackEnd de la barra de herramientas propia de la perspectiva APX.
***¿Cuáles son los campos que definen a una Transacción en APX?
    • Aplicación, transacción, versión, país, descripción
***De la lista siguiente marca 2 funcionalidades de servicios conectores incluidos en APX:
    • estor de eventos.
    • HTTP/HTTPs
***Un anti patrón es un diseño que conduce a una mala solución de un problema como lo son el Blob y el contenedor mágico en librerías.
    • Verdadero
***En el proyecto de Maven, en la estructura de la unidad funcional se puede ver una carpeta llamada “artifact”. De ella cuelgan los distintos subproyectos en los que se alojaran los distintos artefactos que compongan la unidad funcional. El pom.xml tiene como módulos los siguientes proyectos:
    • dtos, libraries, services, transactions
***El resource pooling es una característica que está estrechamente ligada a la IaaS, en lo referente a los pools de recursos que se manejan para el aprovisionamiento de nuevos nodos. En este sentido únicamente cabe destacar el buen rendimiento que ofrece APX, lo que permitiría una gestión óptima de los recursos virtuales y, por lo tanto, de los recursos físicos. 
    • Verdadero
***¿Cuál es la restricción al generar una jerarquía de clases DTO?
    • Que las clases no tengan dependencias circulares entre ellas
***Las utilidades APX implementadas tanto para la arquitectura online como para la escritura batch son:
    • Compresion /Descompresion se crea una librería con la capacidad de comprimir y descomprimir archivos en zip. 
    • Generador de documentos: se crea una librería con la capacidad de generar documentos
***ERROR, Información de situaciones que aun sin ser de error si son anómalas o no previstas aunque el aplicativo tiene alternativas para solventarlas
    • FALSO
***¿Cuáles de las siguientes oraciones son verdaderas? Selecciona 3
    • Una UUAA puede acceder a datos de terceros a través de las librerias APX que el dueño del dato proporcione.
    • No se deben instanciar ni abrir ni cerrar explícitamente conexiones a Base de datos
    • No se debe manejar el objeto Datasource directamente
***En la ejecución de cualquier aplicativo en la Arquitectura APX se generan trazas que son almacenadas en una bbdd Elastic Search para su posterior explotación vía consola KIBANA. Para lo cual se han creado diferentes agrupaciones en KIBANNA que permite visualizar los logs aplicativos y de arquitectura de manera eficiente y que además da la posibilidad de aplicar filtros para llegar a la ejecución de una operación particular. De la siguiente lista selecciona tres índices Kibana creados en el apartado de Discover
    • Logs Actividad Srv. Backend
    • Logs Actividad Transacciones
    • Logs Actividad Batch
***¿Cuál de las siguientes opciones son las consideraciones para tener en cuenta en la instalación en Entorno de Ejecución Local?
    • Las rutas y las URLs proporcionadas serán exclusivamente para un entorno local
    • La instalación debe realizarse sobre un ordenador de sobremesa de BBVA con sistema operativo Windows XP/7 o Linux
    • Se requiere que el equipo posea una unidad D:/ en caso de Windows
***Una vez el asistente para crear un nuevo proyecto haya finalizado, se habrá creado un proyecto Maven con un nombre que sigue la nomenclatura:
    • [UUAA][código][versión]
***Cuando se crea un paquete con la nomenclatura com.bbva.[UUAA].[API].V[versión], dentro de este paquete se crearán dos clases:
    AbstractResource.java: Esta clase generará que se expondrá desde el servicio a la que se puede invocar para obtener la descripción en RAML del servicio. ESTA CLASE NO DEBE MODIFICARSE
    Clase con la nomenclatura [UUAA][código][versión]Resource.java.. En esta clase es donde se irán definiendo las acciones que implementarán. Inicialmente se crea sin ninguna acción definida
    • Verdadero
***Describe las clases que implementan la lógica de cada uno de los steps en un proceso Batch
    • Readers, Writers, Processors
***¿Qué son los patrones de diseño en APX?
    • Es un patrón efectivo para resolver problemas similares en diferentes ocasiones por lo que es reutilizable
***Al realizar una construcción del proyecto Maven tanto de la unidad funcional que contenga el servicio backend como si se realiza a nivel del propio proyecto de servicio backend generar un jar con al nomenclatura [UUAA][código][artifact-po,-version][versión].jar. Donde la correspondencia es:
    UUAA: uuaa a la que corresponde el servicio
    Código: código definido en la entana de creación, esta formado por dos digitos
    Artifact-pom-version: versión a nivel de descripción del artefacto Maven, eliminando, sin los puntos que separan los digitos de mayor.minor.patch, con lo que es un subconjunto de tres digitos
    Version: versión del API
    • Verdadero
***Las utilidades APX implementadas únicamente para la arquitectura Online son:
    • CommunicationManager: Se crea una librería con la capacidad de invocar a G.U.C. para  el envio de notificaciones.
    • Interbackend proxy: Se crea una librería con la capacidad de invocar a los conectores IMS (para acceso a Host)
***¿Qué patrones de la siguiente lista son obligatorios del Data Transfer Object (DTO) en APX?
    • Se quiera intercambiar información entre diferentes componentes en APX de forma coherente, organizada y agrupada
***¿Qué nivel de profundidad se permite (invocación de librería a librería)
    • Evitar profundidades superiores a 3 niveles Transacción >Librería> librería>Librería
***RestFull verdadero
***No se permite la invocacion síncrona en transacciones en caso de que una transación deba invocar a otra solo se puede
hacer de manera asíncrona (se puede crear una transaccion pero solo asíncrona)
La asincronia solo debe aplicarse aplicarse en aquellos casos de transacciones no críticas
Una transaccion asíncrona no puede invoocar a otra asíncrona
No se permite invoacion sincrona entre transacciones
***Longitud maxima del nombre de los métodos 48 caracteres
***Info verdadero
***2 literales/15 caracteres
*/



































