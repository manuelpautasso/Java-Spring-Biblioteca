# Biblioteca Java Spring boot API REST

Api Rest para el manejo del catálogo y prestaciones de una biblioteca usando Spring boot, Spring Security, JPA, MySQL.

## Requerimientos
- JDK 11 o superior
- Maven
- Tomcat
- MySQL Workbench 8.0
- Cuenta en Send Grid para el manejo de mails de confirmación de usuario

## Setup

## Pasos para el Setup

**1. Clonar la aplicación**

```bash
git clone https://github.com/manolomanolero/Java-Spring-Biblioteca.git
```

**2. Importar la base de datos**

+ Importar `bd_mysql_biblioteca.sql` en el MySQL Workbench



**3. Cambiar las variables de entorno**

+ Cambiar el nombre de `example-env.properties` por `env.properties`

+ Agregar en `env.properties` los datos de la base de datos, de Send grid y 

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Para ejecutar la app**

```bash
mvn spring-boot:run
```

La app va a correr localmente en <http://localhost:8080>.

## Explorar los endpoints

La documentacion de todos los endpoints se puede ver en:
- Se puede importar desde Postman el archivo "Biblioteca API.postman_collection.json"
- De forma online en https://documenter.getpostman.com/view/14617154/UzBnrShn

