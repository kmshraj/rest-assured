# Demo Rest Api Automation using Typicode ( https://github.com/typicode/jsonplaceholder)


## Frame work

I decided to build the framework in java using

* Rest-assured java library
* JUnit 5
* Maven as build tool

Reason for choice:
* I'm comfortable with Java, Most widely used
* Rest-assured library uses BDD structure of Given/When/Then Which makes human readable
* CI/CD integration ease
* Ease of writing HTTP Requests
* Easy Data Validation

If I was to do autmation with another tool, I would pick up Postman.

However I used postman to grab the raw data for the requests.




## Design:

* The Folder structure is designed as that of dev repo  src/main/java and src/test/java, So this forces the user to have same package rest.com under src/main/java and src/test/java
* I have got the resources folder, whihc I haven't used it for this exercise
* So the Test falls under src/test/java/rest/com
    * I have DemoTest which has all the Junit test for this exercise
    * DemoHelper Which has helper class for serilisation/Deserialisation
    * POJO as in SimplePost
    * SampleData.json




## Getting Started

* Clone the repository with the git link
* Check the pom.xml has all the dependencies included

## To Compile the test
 Run  mvn test-compile

## To Run the test on command prompt

Run mvn test ( This will run all the tests)



## Trouble Shooting

* If mvn test-compile doesn't work, Try mvn clean
* Check .m2/settings.xml has correct profile settings / correct mirror url http://central.maven.org/maven2/

