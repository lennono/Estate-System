# Estate-System
A JEE application, where clients can sell and bid on houses. 


## Overview:
The web services makes use of the javax.ws.rs package to implement a RESTful API. XML is used to display the web service property data and communicate with the client application. The ‘web.xml’ defines and uses the Jersey ServletContainer, the package is set to ‘estate’ and the url-pattern is ‘/rest/*’. 
In the client the URIs for the REST endpoints are hard coded in for each function, e.g. Doing a GET at the address: ‘http://localhost:8080/EstateManager/rest/PropertyService/properties’ will return XML containing all the properties being managed by the web service.


## Installation Instructions

1. Place the EstateManager.war file in your ‘tomcat/webapps’ (War file created using a javaee Development enviroment ) directory and restart tomcat. Example location: ‘C:\tomcat\apache-tomcat-8.5.30\webapps’
2. Run the jar file ‘client.jar’ with the command: java -jar client.jar
3. Enter a number to select an option from the menu and follow the on screen prompts. (NOTE: There is no user input validation and it has not been tested for input values other than what is asked for in the prompt messages.)

IMPORTANT: This web service stores information about the estates in a file called ‘Properties.dat’. This file will be saved in the bin, of the tomcat folder. If the current logged in user does not have user rights to write to a file in that folder then this web service will not function correctly.

## MyClient.java:
GET and POST requests were carried out by importing ‘javax.ws.rs.client.Client’ and ‘javax.ws.rs.client.ClientBuilder’ and assigning a new client to the MyClient object. The target URI was set for each endpoint along with the request type (XML) and GET or POST. If POST: a Form was created, the params were set and these are sent with the Entity. When a user bids on a property a HashMap will save that property name and the successful bid of the user. If another client bids on that same property and bids higher, then the original client will get a prompt saying that they have been out bid. They can decide to bid on that property again by typing ‘y’ or decide not to bid by typing ‘n’, in which case that property will be removed from the HashMap.

## PropertyService.java
This file contains the endpoints and takes in GET/POST requests from the client. The FormParams are converted back to java types to be used in functions.

## Property.java
This file defines a Property object and has the getters and setters. The ‘@XmlRootElement(name = “property”)’ and ‘@XmlElement’ above each setter run through the object functions to create the XML representation of a property.



## PropertyDao.java
This file manages the Property objects. It has the functions used by PropertyService to add or change Property objects and then save those to a file. A ReentrantLock is used to provide mutual exclusion for each function that will change the attributes of the properties. 


## Usage
Upon loading the client there is a menu to select different options.

1. If 1 pressed a list of the properties that are not sold will be displayed.
2. If 2 is pressed a prompt will ask for either the string ‘H’ or ‘A’ to add a house or apartment. It will then ask for an Integer for the district number. It will then ask for an Integer for the number of rooms. It will then ask for an Integer for the price of the property. Finally it will ask for an Integer for the duration in months for how long the property will be on sale. A message will display if the property was added along with the name.
3. If 3 is pressed the user can bid on a property. A prompt will ask for the property name (e.g. H1, A3). Then it will ask for the price to bid at. A successful bid will only apply if the price is larger than the current highest bid and higher than the original price listing. Successfully bidding will place the bid and property name in a HashMap biddingHistory which will check if another client has out bid the user and then offer a counter-offer prompt.
4. If 4 is pressed the menu will be displayed again.
5. If 0 is pressed the program will terminate.

## Counter-offer functionality
1. Client 1 bids on H1 for 5000000.
2. Another client, client 2 bids on H1 for 6000000.
3. Client 1 types a command and is shown a counter-offer prompt. It says H3’s highest bid is now 6000000 and gives the option to counter-offer by typing ‘y’ or ‘n’.
3. Client 1 enters ‘y’ and then enters 7000000 to beat Client 2’s bid.

## Previous Run Environment
64-bit Windows 10
Apache Tomcat version 8.5.30


