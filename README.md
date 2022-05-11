# Forum-site


## About

This is my first PET project. So what is this project? This is a crowdsourced Q&A site, similar to Stack Overflow. 
My goal with it is to broaden my horizons in designing and building web applications. I mainly use Spring Boot and 
React for the project. If you have any questions about the project, feel free to contact me at my email address: 
cseplo.norbert.95@gmail.com.

### Sprint 1

- Build a backend in Spring Boot
- There are Question, Answer, Comment and Tag models.
- Create JpaRepositories for models.
- Set up database relation between models.
- Build model services to manage Repositories.
- Build Controllers for simple functions (like add a new question)
- Implement a REST API behavior

Used technologies: Spring Boot, lombok, jpa, HATEOAS, mapstruct, H2, Postman.

### Sprint 2

- Create a Simple User Interface with React.
- Switch from H2 to PostgreSQL.

Used technologies: React, React-Bootstrap, React-routing, PostgreSQL, Hibernate.

### Sprint 3

- A user can register into the application by setting at least their username, e-mail address, and password.
- A user can log in to the application.
- The user can log out from the application.
- There are two different roles defined in the application. (User, Admin)
- There is an admin page, which lists all users of the application, available only with the admin role.
- Welcome email for new users.
- Create "Forgot your password" feature.
- Possibility to log in with Google Id.

Used technologies: OAuth 2.0, Spring Security