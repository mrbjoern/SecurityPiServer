# SecurityPiServer
Is a platform working with the SecurityPi project, monitoring for instance temperature and motion at home.
The server platform consts of two parts. 1) A web-interface showing current status of the system, unusual
events etc, and 2) An API for reporting of events.

The platform is built on Spring and uses Json when communicating with the API.

## Configuration
SecurityPiServer is currently set up to use SSL on all connections. Remember to add the following to your 
application.properties file.

    server.port = port
    keystore.file = keystore.p12
    keystore.password = yourpassword

Connections to most parts of the RESTful API requires and API token. Those can be generated in the admin pages.
Remember to include X-Api-Key - your unique token, to the http request header in your application.

    X-Api-Key = 32 char long unique string