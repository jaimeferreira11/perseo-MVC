## Perseo; Sistema Financiero, Stock y Facturacion

Perseo esta basado en Maven y utiliza Spring Boot para ejecutar el artefacto JAR compilado con un servidor Tomcat incrustado.

## Prerequisites

* Maven 3.3.3 o superior
* Java 8

## Configuracion Inicial

Estos son pasos que sólo necesitará realizar la primera vez que esté clonando el proyecto

### Paso 0, clonar el repositorio

```shell
git clone git clone https://TU_CUENTA/orionsoftware/perseo.git
```
Importar como un proyecto Maven

### Paso 1, crear la base de datos

Ejecutar con psql el script:

```shell
psql -U YOUR_USER_HERE -f src/main/resources/db/create_database.sql
```

También puede copiar y pegar el siguiente fragmento a su cliente PSQL favorito:

```
CREATE USER "perseo" WITH ENCRYPTED PASSWORD 'perseo';
ALTER ROLE "perseo" WITH createdb;
CREATE database "perseo";
```

### Paso 2, restaurar la base de datos

Ponganse en contacto con el Administrador para obtener el backup de la base de datos.


### Paso 3, Ejecutar la Aplicacion

Ejecutar la aplicacion con apache tomcat:


Y eso es todo ;) , su servidor se lanzará de forma predeterminada en http://localhost:8080/perseo

Forma de ejecutar con Maven:
```mvn spring-boot:run```