# demo
ES spring boot project

Using ElasticSearch/Kibana 6.6.2; 
x-pack-transport 6.4.3;


When connecting to ES, we take username/passord over https on port 9210, which is the master node in es cluster.

This application also provides simple JWT authentication method and HTTPS feature:

POST https://localhost:443/api/auth/signup

{
	"name": "wuhuan",
	"username" : "wuhuan",
	"email": "wuhuan@gmail.com",
	"password": "123456"
}

POST https://localhost:443/api/auth/signin

{
	"usernameOrEmail":"wuhuan@gmail.com",
	"password":"12346"
}
This response will return JWT token

{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNTU4OTI5MjI4LCJleHAiOjE1NTk1MzQwMjh9.rS_DhoWbWykWVEK6QVRC3UpAtSYa1X_bSF-CKyjvmQ5YuAWKx1X96ExEhCBRTp-zBecHASkmcuWAbH-XqN-Kmg",
    "tokenType": "Bearer"
}

For other Uri, you may send request with this Bearer token.