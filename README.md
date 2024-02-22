# Prueba Técnica 4 - Gestión de Reservas de Vuelos y Hoteles

Este proyecto es una aplicación de gestión de reservas de vuelos y hoteles. Permite a los usuarios buscar vuelos y hoteles disponibles, así como realizar reservas para ellos.

## Características

-   Crud de vuelos.
-   Crud de hoteles.
-   Crud de personas.
-   Crud de habitaciones.
-   Reserva de asientos de un vuelo.
-   Reserva de habitación por fechas.
-   Seguridad: autenticación de usuarios y control de acceso a los recursos.
-   Documentación con Swagger

## Tecnologías Utilizadas

-   Java
-   Spring Boot
-   Hibernate
-   MySQL (u otra base de datos relacional)
-   Maven (o Gradle)
-   Mockito y JUnit para pruebas unitarias
-   Spring Security para la autenticación y autorización de usuarios

## Configuración

1.  **Clonar el repositorio:**

    `git clone https://github.com/Rolob3rto/RuizRoberto_pruebaTecnica4.git`
    
2.  **Configurar la base de datos:**
se crea una base de datos llamada agency
`spring.datasource.url=jdbc:mysql://localhost:3306/agency?useSSL=false&serverTimezone=UTC`
3. **Arrancar la aplicación**
una vez este configurada la base de datos y operativa, entonces podemos arrancar la aplicación
4. **Acceder a la aplicación**
Se puede acceder por:
	- navegador web
	- Postman
	- Swagger ([http://localhost:8080/agency/doc/swagger-ui.html](http://localhost:8080/doc/swagger-ui.html) )
	
## Casos Supuestos
- A nivel global se ha añadido el atributo active para la realización de borrado lógico en la app
- La entidad Person:
		Se usa para la realización de reservas y que quede constancia de quien a reservado
	- consta de:
		- nombre 
		- apellido 
		- dni
		- email (es por el que se busca luego, simulando una posible cuenta en una web)
- La entidad Room:
		Se usa para guardar datos importantes de la habitacion como:
	- consta de:
		- nombre (es atributo único para diferenciarse del resto)
		- tipo de habitación
		- precio
- La entidad Booking bifurca en 2 vertientes hijas:
	- para las reservas de habitación
	- para las reservas de vuelo
		pero a nivel estructural en la BBDD queda como una sola tabla de reservas ya que varían en muy poco una de otra.
	- constan de:
		-  una fecha (la que se ha reservado)
		- total de coste de la reserva (se calcula respectivamente con la habitacion y vuelo)
		-  y concretamente la de habitaciones guarda 2 fechas que son las que el usuario ha reservado la habitación
- La entidad Vuelo:
	- consta de:
		- origen (ciudad o lugar)
		- destino (ciudad o lugar)
		- numero de asientos (es el numero de asientos que hay en el avión disponibles)
		- tipo de asiento
		- fecha del vuelo
		- precio 
## Archivos
Colección de Postman en la carpeta resources del proyecto, al igual que la BBDD
