# Estate-System
A JEE application, where clients can sell and bid on houses


## Overview:
The web services makes use of the javax.ws.rs package to implement a RESTful API. XML is used to display the web service property data and communicate with the client application. The ‘web.xml’ defines and uses the Jersey ServletContainer, the package is set to ‘estate’ and the url-pattern is ‘/rest/*’. 
In the client the URIs for the REST endpoints are hard coded in for each function, e.g. Doing a GET at the address: ‘http://localhost:8080/EstateManager/rest/PropertyService/properties’ will return XML containing all the properties being managed by the web service.


## Installation Instructions

1. Place the EstateManager.war file in your ‘tomcat/webapps’ (War file created using a javaee Development enviroment ) directory and restart tomcat. Example location: ‘C:\tomcat\apache-tomcat-8.5.30\webapps’
2. Run the jar file ‘client.jar’ with the command: java -jar client.jar
3. Enter a number to select an option from the menu and follow the on screen prompts. (NOTE: There is no user input validation and it has not been tested for input values other than what is asked for in the prompt messages.)

IMPORTANT: This web service stores information about the estates in a file called ‘Properties.dat’. This file will be saved in the bin, of the tomcat folder. If the current logged in user does not have user rights to write to a file in that folder then this web service will not function correctly.





