# TCS2011-Group1
Note: This instruction is not for who not familiar with web development.

This project contains 2 folders for server api and client. 

- Server api use java spring boot framework with mysql as database and for realtime features, it uses apache kafka to stream data.

- Client use vue js framework and other additional components for features.

# HOW TO RUN SOURCES?
Switch to branch realease

Server api source:

- To run server api, first you must set up java development environment with minimum version is 8 and maven.

- Second, you must set up apache kafka. Apache kafka contains 2 important parts: zookeeper and kafka.

- Third, you must set up mysql server (in here i use XAMPP).

- After setting up zookeper and kafka, to start kafka, open command prompt then locate to /bin/windows (if you are using window) in kafka folder and run these commands in different command prompts in order:
    1. zookeeper-server-start.bat ../../config/zookeeper.properties
    2. kafka-server-start.bat ../../config/server.properties
    3. kafka-topics --create --topic kafka-chat --zookeeper localhost:2181 --replication-factor 1 --partitions 1 (for first configuration only)

- After starting kafka, open server api source and then open application.properties (its in resources folder) and config your database connection. To config your database connetion, make sure you create a new database in mysql server then config that connection link to the application.properties file.

- After that, open the api source in BackEnd folder in your preferred IDE and start the source code (my IDE used to develop it is visual code).

Client source:

 - Open the client source in edunetic-magazine folder in your preffered IDE.
 - For first configuration, run this command in your terminal: npm install.
 - To run the source, run this command in your terminal: npm run serve.
 - As this source is a demo source, you should run source client in localhost:8081

# To login in system
- You can use these accounts:

email: admin@gmail.com pass: 123456a (role admin)

email: mc@gmail.com pass: 123456a (role marketing manager)

email: mm@gmail.com pass: 123456a (role marketing coordinator)

email: student@gmail.com pass: 123456a (role student)

- For guest role: If you are first time login, you can use google sign in.










