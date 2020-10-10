A multi-module application based on the customer, product and preferences class.

Application characteristics:
- multi-module application divided into: persistence, service, ui, written in java.
Persistence is a module that contains models, service contains business logic, and ui is used to communication.
- the application uses the gson library to convert data from and to json format.
- the application join data from several files, where the test data is divided according to characteristics.
- the ui module uses the spark framework to generate http requests, 
and also allows communication with the user through consoles and convenient menu.
- the application has been tested using the JUnit 5 library. 
The project also uses the extension tool to generate test data from a json file.
- the application is available on both github and dockerhub:.