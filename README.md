# api-equipos-futbol
API para gestionar información de equipos de fútbol

* Java version 17 
* Springboot version 3.4.3


# Swagger
* Para ingresar y probar la aplicación podemos utilizar la url de swagger:
http://localhost:8080/swagger-ui/index.html#/
* 1. Loguearse utilizando: users-controller /auth/login
{
  "username": "test",
  "password": "12345"
} 
* 2. Copiar el token e ingresarlo en el boton "Authorize" situado en la parte superior derecha
ej: value: eyJhbGciOiJIUzI1NiJ9

* 3. Probar los endpoints

# Scripts de db memoria h2

* Se encuentran en el archivo "schema.sql" en la raiz del proyecto
* 1. Al ejecutar la aplicación los datos se crean automaticamente.




