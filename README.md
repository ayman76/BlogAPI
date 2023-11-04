
# REST Blog Api Web Service

This API allow to access and create data from many categories such as Blogs, comments, tags , categories, and users it build recently using Rest


## Documentation

https://documenter.getpostman.com/view/26688798/2s9YXe6P3H


## Get Started

- Clone the repository to your computer
```bash
git clone https://github.com/ayman76/BlogAPI.git
```
- Open Project in your editor
- Configure application.properties

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/blogApi?createDatabaseIfNotExist=true
spring.datasource.username={username}
spring.datasource.password={password}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# Allows To Hibernate to generate SQL optimized for a particular DBMS
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
# drop n create table, good for testing, comment this in production
spring.jpa.hibernate.ddl-auto=update
# Show SQL queries
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

```

- Run the app using maven

```bash
maven spring-boot:run

```
The app will start running at http://localhost:8080
## EndPoints
- Blog
- User
- Category
- Tags
- Comments
