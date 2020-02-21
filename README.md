
# Microservices-twitter

## Descripción microservicio
Microservicio que consume Tweets que basado en unas restricciones los persiste en una base de datos para su gestión a través de una API REST.


## Restricciones
Se deben persistir aquellos tweets cuyos usuarios superen un número de seguidores. Por defecto 1500.<br/>
Se deben persistir aquellos tweets cuyo idioma esté en una lista de idiomas permitidos. Por defecto español, francés e italiano. <br/>


## Datos persistidos
Se almacena la siguiente información (en caso de tenerla) para cada tweet. El nombre del usuario, texto del mismo, cantidad de seguidores, lenguaje, latitud/longitud, nombre del lugar y un check de validación.
Se han incluido el lenguaje y cantidad de seguidores para corroborar las restricciones del problema propuesto.
Se realiza la persistencia en memoria


## API
Se ha adjuntado el siguiente fichero "Tweets.postman_collection.json" para importarlo en Postman y poder probar las invocaciones posibles.
<br/>
| Acción                                                | URL                       | Método |
 ------------------------------------------------------ | ------------------------- | -------|
| **Consultar** los tweets 						        | /tweets                   | GET    |
| **Consultar** los tweets **validados/no validados**   | /tweets?areValidated=true | GET    |
| **Validar** tweets                                    | /tweets/{id}              | PATCH  |
| **Consultar** los **trending Hashtags**               | /trendingHashtags         | GET    |


**Valores permitidos**
> `?areValidated=true` ignorando mayusculas/minúsculas, cualquier otro valor buscará los tweets no validados

> Siendo `{id}` el valor del id asignado al tweet grabado en memoria


# Construcción del proyecto | Test JUnit
Descargar el repositorio con el comando:
`git clone https://github.com/MauricioRicciCalvo/Microservices-twitter.git` <br/> 
y posicionarse en el proyecto 
<br/>`cd Microservices-twitter`

Hacer `mvn clean install` en este directorio. 
> Este proceso puede tardar aproximadamente 1-2 minuto al incluir los test para cada operativa.


## Lanzar el microservicio
Hacer `mvn spring-boot:run` en el directorio. Este comando pondrá en funcionamiento el microservicio, pudiendo de esta forma comenzar a emplear su API. 
> URL : http://localhost:8080/tweets - Consultar API para el resto de invocaciones
