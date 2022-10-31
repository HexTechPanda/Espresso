# Espresso (backend)
***
## How to set up
1. Download Software
  - IDEA
  - JDK 11
  - MySQL-5.7.28 (You can use docker to install)
  - Redis (You can use docker to install)
  - Nacos (You can use docker to install) https://nacos.io/en-us/docs/v2/quickstart/quick-start-docker.html
  - (Optional) Database management software like 'Navicat'
3. Start MySQL
   - find SQL script in /SQLschema
   - execute the script in your mysql
4. Start Redis
5. Start Nacos
   - start in "standalone" mode
   - https://nacos.io/en-us/docs/v2/quickstart/quick-start.html
6. Use IDEA to open this project
   - Look into every Maven(*.pom) file, ensure all dependency is included.
   - Look at application.yml under every module, ensure the datasource config (username, password) is correct.
   - start service under every module

## References
Just google "start Spring Cloud with IDEA", "nacos", "maven"

## API Design
- API url start with "/public" can be called without authentication.
- Other API should be called with authentication token. Just set token in HTTP Header "Authorization", value like "Bearer eyJhbGcixxxxx"
- You can call Login API to get the auth token.

About endpoint
localhost:6001 is the gateway, it will redirect api to target service,
but you also can call the target service directly with endpoint like
localhost:8011
## Auth service
Login API
   - POST localhost:6001/auth/public/user/login
   - Request body:
```json
{
  "username": "tester01",
  "password": "tester123"
}
```
- Response body:
```json
{
    "code": 20000,
    "message": "Login success.",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4NDY1YjdiNGVlY2M0NzA0YjllZmE4NzljMzhhYTAxMiIsInN1YiI6IjE1ODY3MDAxMjY0MDQ1NzExMzciLCJpc3MiOiJ5ZGxjbGFzcyIsImlhdCI6MTY2NzE4MjkwOSwiZXhwIjoxNjY3MTg2NTA5fQ.GJe6qZnyNqQ6sFCKc1i-iiwIo4qgEcY6rssV8TPz-yQ"
    }
}
```
Register API
- POST localhost:6001/auth/public/user/register
- Request body:
```json
{
    "username": "tester02",
    "password": "tester123",
    "repPassword": "tester123",
    "nickName": "tester01nick",
    "mobile": "88883366",
    "email": "tester02@test.com"
}
```
Logout API
- POST localhost:6001/auth/user/logout
