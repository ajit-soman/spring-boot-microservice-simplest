# spring-boot-microservice-simplest

![alt text](https://raw.githubusercontent.com/ajit-soman/spring-boot-microservice-simplest/master/eureka-home.png)

This github repository consist of 4 microservices:

1. **eureka-server**:  A microservice registry server. All other microservices will register with this server and can used to get information about other microservices.

      *Important Annotation(s)*: `@EnableEurekaServer`

      *Application.properties*

      ```
      spring.application.name=Eureka-server
      # Eureka server is client as well so prevent registering with itself
      eureka.client.register-with-eureka=false
      # Default Eureka server port
      server.port=8761
      ```

2. **employee-eureka-client**: A small microservice with single endpoint . It will register itself with eureka-server.

    *Important Annotation(s)*: `@EnableEurekaClient`

    *Application.properties*

    ```
    spring.application.name=Employee-service
    # Just a number. If you are deploying multiple instance of this service
    # you can change the number. This will reflect in endpoint reponse
    number=1
    # Tomcat port
    server.port=3333
    ```
3. **person-eureka-client**: It is same as employee-eureka-client but the endpoint will return a different text. Also it has a additional endpoint that make a call to employee-eureka-client using RestTemplate instead of hardcoding the url like this:

    > restTemplate.getForObject("http://localhost:3333/", String.class);

    we change to this:

    > restTemplate.getForObject("http://employee-service/", String.class);

    In this case `employee-service` is equivalent to `localhost:3333`, This information is fetch from Eureka-server and will be stored in cache for further use. if there are muliple employee-eureka-client microservice running then the http call will be load balanced automactically using netflix ribbon.

    *Important Annotation(s)* : `@EnableEurekaClient`, `@LoadBalanced`
    
    *Application.properties*

    ```
    spring.application.name=Person-service
    # Just a number. If you are deploying multiple instance of this service
    # you can change the number. This will reflect in endpoint reponse
    number=1
    # Tomcat port
    server.port=4444
    ```
4. **zuul-gateway**: It is used as a gateway server which forwards the request to respective microservices along with load balancing. It also register with eureka and uses it to get information about other microservices

    *Important Annotation(s)*: `@EnableZuulProxy`, `@EnableDiscoveryClient`

    *Application.properties*

    ```
    spring.application.name=zuul-gateway
    #routes to employee service app
    zuul.routes.employee.service-id=employee-service
    #routes to person service app
    zuul.routes.person.service-id=person-service
    ```
    Take a look at last two property in the properties file.
    
    >zuul.routes.XXXX.service-id=YYYY
    
    The XXXX will be the route url and YYYY will be the spring.application.name text. Consider example:
    
    >zuul.routes.person.service-id=person-service
    
    If any hit came with URL http://localhost:8080/person then it will be forward to person-service's root url . If there are multiple person-service running then it will be load balanced using ribbon .
    Below is the screenshot.  I just changed the `number=2` in application.properties of other person-service for identification
    
    ![alt text](https://raw.githubusercontent.com/ajit-soman/spring-boot-microservice-simplest/master/curl-output.png)
     
