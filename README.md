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
2. Start MySQL
   - find SQL script in /SQLschema
   - execute the script in your mysql
3. Start Redis

4. Start Nacos
   - start in "standalone" mode
   - https://nacos.io/en-us/docs/v2/quickstart/quick-start.html
   
   For **macOS with Apple Chip**, after cloning the `nacos-docker` repo,
   edit the `.env` file in `example` folder.
   ```shell
   cd example
   vim .env
   ```
   Change the original `NACOS_VERSION=v2.1.1` to `NACOS_VERSION=v2.1.1-slim`, 
   which `v2.1.1-slim` is the version for **Apple Chip**.

   Then continue follow the [Quick Start for Nacos](https://nacos.io/en-us/docs/v2/quickstart/quick-start.html)
   ```shell
   docker-compose -f example/standalone-derby.yaml up
   ```

5. Use IDEA to open this project
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
   - POST https://espresso-auth.hexpanda.click/auth/public/user/login
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
- POST https://espresso-auth.hexpanda.click/auth/public/user/register
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
- Response body:
```
{
    "code": 20000,
    "message": "Success",
    "data": null
}
```
Logout API
- POST https://espresso-auth.hexpanda.click/auth/user/logout (carry bearer token in http header)
- Request body:
```
{
    "code": 20000,
    "message": "Success",
    "data": null
}
```
###OAuth2 Login - only support Web client

To Front-End developer:
About oauth2 login part, please refer to this GitHub:
https://github.com/The-Tech-Tutor/spring-react-login/tree/master/client

Steps:
1. customer click the url in the page:
https://espresso-auth.hexpanda.click/auth/public/oauth2/authorize/google?redirect_uri=https://espresso.malcolmpro.com/

2. The url reaches backend and redirect to Google login (this step will set two cookies)
3. Page handles the redirect response, please refer to this .js
   https://github.com/The-Tech-Tutor/spring-react-login/blob/master/client/src/user/oauth2/OAuth2RedirectHandler.js
4. Configure need to change: 
```
index.js:
export const GOOGLE_AUTH_URL = API_BASE_URL + "/auth/public/oauth2/authorize/google?redirect_uri=" + OAUTH2_REDIRECT_URI;
```
