# Estate-System
A JEE application, where clients can sell and bid on houses


## Overview:
The web services makes use of the javax.ws.rs package to implement a RESTful API. XML is used to display the web service property data and communicate with the client application. The ‘web.xml’ defines and uses the Jersey ServletContainer, the package is set to ‘estate’ and the url-pattern is ‘/rest/*’. 
In the client the URIs for the REST endpoints are hard coded in for each function, e.g. Doing a GET at the address: ‘http://localhost:8080/EstateManager/rest/PropertyService/properties’ will return XML containing all the properties being managed by the web service.




