Feeds is an Spring Boot application including dependecies for REST and JPA.

Feeding of news has been scheduled through a task to be executed every 5 minutes.

The task pull news from http://feeds.nos.nl/nosjournaal?format=xml and it unmarshals the response into an Rss entity.

The items are recovered from Rss entity to get title, description, publication Date and image from each one of then and be stored in the database using JPA.

From another side, a REST service has been implemented to provide the previously stored items in the database.

To deploy start the apllication we execute(previously generated with spring-boot-maven-plugin):

>java -jar feeds-0.0.1-SNAPSHOT.jar


Using Postman or another different client we can test the REST service. Pagination is also allowed:

http://localhost:8080/feed/news?page=0&size=100&sort=publication,desc
