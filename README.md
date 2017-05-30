# PropertyPublisher
Summary

Problem Statement
High Level Solution
Design
Features
inbuilt
Features Road Map
Inspiration
Problem Statement

Managing Properties and configuration of an application has become a tedious task with the increasing number of implementations on load balancers enabled environment.

Making a change in configuration becomes a nightmare, because each server in the environment has to be brought down.

In some occasion even rebuilding of the application is required if the configuration is inside the jar.

Location of the Property file and the content of the property file is visible to all and its predictable too.

# Features Road Map

Integrate with Source Repositories such as SVN and GIT to read Properties file.
Automatic pushing of configuration to client as soon as the change is made in Server.
Creating a information page to show the list of users attached to the server.
Encrypting the properties file as soon as the properties are published.
Authorization based properties publishing.
Access Items

# JSON Object to Driver File
Automatic pushing of configuration to client as soon as the change is made in Server.
Creating a information page to show the list of users attached to the server.
Encrypting the properties file as soon as the properties are published.
Authorization based properties publishing.

# How to use?
# Server 
1. Download the PropertiesSender.
2. Execute the Main Class
3. This will start publishing the property file in http.

# Client
If the Client application is developed using Spring. Follow the below Steps to assing value to the variables thats created.
1. Create Objects to the classes (Either by componet scan or by defining beans in the application context).
2. Create a Bean for propertyScanProcessor and Pass all the objects / Beans as parameters to the constructor of the bean as below.

<bean id="propertyScanProcessor" class="com.properties.receiver.processor.PropertyScanProcessor">
		<constructor-arg>
			<list>
				<ref bean="xslController" />
				<ref bean="fileSystemStorageService" />
				<!-- <value>com.demo.srcpkg</value> -->
				<!-- <value>com.demo.destpkg</value> -->
			</list>
		</constructor-arg>
	</bean>
  
3. Annotate the Class with @PropertyScan so that the Processor can scan the class for variables.
4. Define Variables as mentioned below
 
  @SetProperty(property = "productName")
	private String productName;
  
 # Client Configuration
1. Perform the below configurations so that the client application can communicate to the server which is publishing the properties.
2. Create a FAT Jar with PropertyReceiver Project.
3. Add it in the client applicaiton. 
4. By default the Client applicaiton will be configured to search for Server using the below configuration. This can be modified as needed by end user. This can be done by modifying the same in propertyContext.properties file in PropertyReceiver.
publishPort=7777
publishPacketSize=65536
propertiesFileTopublish = Test.properties
hostName = localhost
activePropertyFile = tenantConfig.properties
UserDtls = {"admin:vivek@123","vivek:dec@2016","user:pass"}

5. Since its a Authenticated URL the connection would be established using user name and Password. 

