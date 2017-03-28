# email-service-failover
This a rest service for email written in java that failsover different main providers

This service aims to expose only one consolidated Rest API to the user while using several email providers under the hood, one as the main and the others as the backup in case of failure. This fail-over happens without affecting the original request of the user. The one choosen as the main provider is configurable.

## Suported Providers (so far)

[Sendgrid](https://sendgrid.com/docs/index.html)

[Mailgun](https://documentation.mailgun.com/quickstart.html)


## Dependencies:

Java 8

spring-boot

[Sendgrid Java](https://github.com/sendgrid/sendgrid-java)

[Mailgun Library](https://github.com/sargue/mailgun)

[Java Mandril API](https://github.com/rschreijer/lutung) (Pending to integrate due to restrictions on free account)

[Project Lombok](https://projectlombok.org/)

[Swagger-ui](http://swagger.io/swagger-ui/)


## Monitoring:

It is done with spring actuator and have these endpoints (among others) to check the status of the application.

/health

/loggers

/metrics


## API Docs:

One example of to use the API and also to play with it, can be found in 
/swagger-ui.html

## ToDos:

-Integrate with the Mandrill provider

-Improve test coverage

-Think a way to initialize better the Strategies
