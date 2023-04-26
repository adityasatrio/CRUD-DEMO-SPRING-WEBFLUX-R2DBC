Spring webflux CRUD Demo ( an overkill CRUD demo )

Core Tech-Stack 
1. Java 19.x
2. Spring boot 3.x
3. Spring webflux 
4. Spring security
5. Spring data R2DBC (postgresql)

Documentation : 
- Swagger using spring-doc, since springfox is un-maintained 

Implementation 
- [x] API login and register using spring security + spring webflux for API authentication and JWT 
  - [x] Register API with encoded password hashed and salted
  - [ ] Asymmetric JWT token
  - [x] Validate API using JWT 
  - [ ] Refresh JWT token flow 
  - [ ] Multi roles user validation 
- [ ] Request validation in DTO layer 
  - [ ] Spring global error handler for standard response success and error
- [x] Using Spring data JPA, postgresql with reactive R2DBC
  - [x] Get data user 
  - [x] Update data user name  
  - [ ] Update data username using transaction   
  - [ ] Complete unit test for controller - service - repository
- [ ] Swagger UI for API documentation using spring doc 
- [ ] Migration and seeder
- [ ] Cache layer ( redis / caffeine )
- [ ] Message broker integration
- [ ] Search engine integration ( typesense / meilisearch)
- [ ] API GRPC
- [ ] Dockerized
- [ ] Logging configuration

How to 
1. Compile and run spring boot application
```shell
mvn clean compile 
./mvnw spring-boot:run
```
2. Accessing swagger-UI
```shell
http://localhost:8080/webjars/swagger-ui/index.html
```
3. Register your user `/authentication/register`, example payload : 
```json
{
  "phoneNumber": "0812312312300",
  "password": "+D^dlW",
  "name": "john doe"
}
```
4. if success and user created, login with registered user and password  `/authentication/login`
5. if login success, get the JWT token for authenticate for accessing `/user` API