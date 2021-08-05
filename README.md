# blog-spring-java
projeto feito em spring boot: Blog completo, feito para vários usuários postarem seus artigos, com painel de administrador para gerenciar usuarios, categorias e etc...

## path: /src/main/resources/application.yml

application properties example: yml

´´´yml

server:
  error:
    include-stacktrace: never
  port: 8080


spring:
  application:
    name: your_app_name
  datasource:
    url: jdbc:mysql://localhost:3306/your_db_name?useSSL=false&jdbcCompliantTruncation=false&sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'
    username: db_username
    password: db_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      dialect: org.hibernate.dialect.MySQL8Dialect

´´´

## License
[MIT](https://choosealicense.com/licenses/mit/)
