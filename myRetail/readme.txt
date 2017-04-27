Project Title: myRetail RESTful Service
Creates restful service that can retrieve product and price details. 
for e.g.: http://localhost:8080/myRetail/webapi/products/16483589


This project is based on:
Framework used: JAX-RS API - Java API for Restful services 
                Jersey - JAX-RS implementation 

Ref: https://jersey.java.net/


NoSQL database used: Amazon DynamoDB

Amazon DynamoDB is a fast and flexible NoSQL database service. We need to run the DynamoDB instance before executing the project. With DynamoDB, you can create database tables that can store and retrieve any amount of data, and serve any level of request traffic.

Ref: http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html

DynamoDB on your computer requires the Java Runtime Environment (JRE) version 6.x or newer; it will not run on older JRE versions.

Install local version of DynamoDB to run the application.
Download here: http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html
After installation, in CLI run the following command to get the noSQL up and running: 
java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar 

It initializes DynamoDB local with the configurations set in the code.

pom.xml includes dependencies for Amazon DynamoDB & JUNIT for unit testing.

Steps to run the service:
1. Install DynamoDB local version and run the instance of DynamoDB by executing the command:
java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar 
2. Open project in IDE Eg: Eclipse (Download here: https://eclipse.org/downloads/packages/release/luna/sr2). Clean and build the project.
2. Start your server Eg.Tomcat (Download here : http://tomcat.apache.org/download-80.cgi)
3. Test HTTP Get request in your browser for eg: http://localhost:8080/myRetail/webapi/products/16752456 
Or use any testing tool to test both HTTP GET and HTTP PUT action, Eg Postman (Download here: https://www.getpostman.com/)

Test Cases:

Test Case 1: To make a get call at http://localhost:8080/myRetail/webapi/products/16752456 

cURl Command:

curl -X GET \
  http://localhost:8080/myRetail/webapi/products/16752456 \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 3694dbf7-85e1-16fd-a006-ad7a5d2873e4'

return : 
Status:404 Not Found

{
  "error": "The requesting product entity does not exist"
}


Test Case 2 : To accept a PUT call to create a product entity at http://localhost:8080/myRetail/webapi/products/16752456

cURl Command:

curl -X PUT \
  http://localhost:8080/myRetail/webapi/products/16752456 \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: e0402319-8987-0beb-b51f-136b29534f06' \
  -d '{
  "productID": 16752456,
  "productName": "Sony TV",
  "productPrice": {
  	"value": 1000,
  	"currencyCode": "USD"
  }
}'

return: 
Status : 202 Accepted


Test Case 3: To make a get call to retrieve productID 16752456 at http://localhost:8080/myRetail/webapi/products/16752456

cURl Command:

curl -X GET \
  http://localhost:8080/myRetail/webapi/products/16752456 \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 3a4f205a-be49-30e0-29bf-b7dd2bb26527' \
  -d '{
  "productID": 16752456,
  "productName": "Sony TV",
  "productPrice": {
  	"value": 1000,
  	"currencyCode": "USD"
  }
}'

return:
Status: 200 OK

{
  "productID": 16752456,
  "productName": "Sony TV",
  "productPrice": {
    "value": 1000,
    "currencyCode": "USD"
  }
}


Unit Testing:
All tests are located in src/test/java
To run the tests in Eclipse:
Right click on the test package and run it as JUNIT.


