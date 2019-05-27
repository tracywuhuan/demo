# demo
ES spring boot project

Using ElasticSearch/Kibana 6.6.2; 
x-pack-transport 6.4.3;


When connecting to ES, we take username/passord over https on port 9210, which is the master node in es cluster.

This application also provides simple JWT authentication method and HTTPS feature:

POST https://localhost:443/api/auth/signup
```JSON
{
	"name": "wuhuan",
	"username" : "wuhuan",
	"email": "wuhuan@gmail.com",
	"password": "123456"
}
```
POST https://localhost:443/api/auth/signin
```JSON
{
	"usernameOrEmail":"wuhuan@gmail.com",
	"password":"12346"
}
```
This response will return JWT token
```JSON
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNTU4OTI5MjI4LCJleHAiOjE1NTk1MzQwMjh9.rS_DhoWbWykWVEK6QVRC3UpAtSYa1X_bSF-CKyjvmQ5YuAWKx1X96ExEhCBRTp-zBecHASkmcuWAbH-XqN-Kmg",
    "tokenType": "Bearer"
}
```
For other Uri, you may send request with this Bearer token.


Backend MySQL samples
```SQL
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL  AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `username` varchar(15) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(100) NOT NULL,

  PRIMARY KEY (`id`)
)

 CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  
  PRIMARY KEY (`id`)
)

 CREATE TABLE `user_roles` (

  `id` int(11) NOT NULL AUTO_INCREMENT,

  `user_id` bigint NOT NULL,

  `role_id` int(11) NOT NULL,

  PRIMARY KEY (`id`),

  KEY `fk_user_role_t_role_1` (`role_id`),

  KEY `fk_user_role_t_user_1` (`user_id`),

  CONSTRAINT `fk_user_role_t_role_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,

  CONSTRAINT `fk_user_role_t_user_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE

)

INSERT INTO `users` (`id`, `name`, `username`, `email`, `password`) VALUES
	('1', 'mukesh','Mukesh Sharma', 'mukesh@163.com','$2a$10$N0eqNiuikWCy9ETQ1rdau.XEELcyEO7kukkfoiNISk/9F7gw6eB0W');

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');


INSERT INTO `user_roles` VALUES ('1','1','2');
```
