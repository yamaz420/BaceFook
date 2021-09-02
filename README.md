# BaceFook / DevConnector

Team Green School project - social network web-browser-application for aspiring- as well as professional -developers.

## Getting Started
The App was created according to the MVC (Model-View-Controller) paradigm. Basis of the View component was made using HTML/CSS/JS.

Deploy the App locally:

* Clone Repository to desired local destination directory
* Create a Database with MySQL (DATABASE, USERNAME and PASSWORD)
* In application.properties enter the DATABASE, USERNAME and PASSWORD:
```Java
spring.datasource.url=jdbc:mysql://localhost:3306/DATABASE?useSSL=false&serverTimezone=UTC
spring.datasource.username=bfdata
spring.datasource.password=DB-PASSWORD
```
* Run the project using IntelliJ (IDE) and, in your browser, navigate to URL: localhost:8080

## Technology Stack
* MySQL - Database used

* Java 11 - Java/JDK version used

* Maven - Dependency Management

* Spring Boot - Back-End Framework used with the following dependencies:
  * Spring Boot Starter Data JPA
  * Spring Boot Starter Thymeleaf
  * Spring Boot Starter Web (uses Spring MVC)
  * Spring Boot DevTools
  * MySql Connector Java
  * Spring Boot Starter Test

jQuery - JavaScript Library

IntelliJ - IDE used

## Versioning
* Git for versioning
  * Git Bash - Git CLI
  * GitKraken - Git GUI
  * GitHub Desktop

## Collaborators:
Marcus Mobark - tech lead - lead author
Haris Nezirevic
Erik Torres Puente
Erik Johnsson
Hajk Karufanian
