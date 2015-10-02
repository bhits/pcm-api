# 1. Externalizing and Encrypting

## 1.1. Externalization

A PropertyTemplate folder has been created which mimics our application specific properties e.g, database.proerties.
Below is the structure of <b>PCM</b> under <b>\ds4p-prod\PropertyTemplate</b>. All developers should be using this template for any changes in these application specific properties.
	
		PropertyTemplate
			- pcm
				- pg
					- config
						*.properties

#### Setting Up System Property Variables (Ignore this step if variables are already defined for another project)
Create two new System property variables in catalina.properties under {CATALINA_HOME} or Pivotal “Servers” in STS:

		C2S_KEY=9HPcr8z634

		C2S_PROPS=C:\\eclipse-workspaces\\ds4p-workspace\\ds4p-prod\\PropertyTemplate



NOTES:

	1.	Path of C2S_PROPS must point to PropertyTemplate directory of your current workspace branch.

	2.	Restart STS to pick up newly created System Property variables.



## 1.2.	Encryption

While adding a new variable in application specific properties e.g, providers.proerties or creating a new properties file from scratch, ensure that all the sensitive information is encrypted using Jasypt.

#### Using Jasypt
An open source product called Java Simplified Encryption (JASYPT) is used to replace clear text passwords in files with encrypted strings that are decrypted at run time. Encrypting credentials Jasypt provides a command line utility that can be used to encrypt the values of your properties.

Download the Jasypt distribution and unpack it. The utilities reside in the bin directory.

	C:\Users\himalay.majumdar\Downloads\jasypt-1.9.2\bin>encrypt input=admin password=9HPcr8z634
	----ENVIRONMENT-----------------
	Runtime: Oracle Corporation Java HotSpot(TM) 64-Bit Server VM 25.25-b02
	----ARGUMENTS-------------------
	input: admin
	password: 9HPcr8z634
	----OUTPUT----------------------
	VSWiYdKWUgxQGzQw7WjEAA==

Update your properties file(under PropertyTemplate folder) putting the out inside ENC().

Example:

	database.password=ENC(VSWiYdKWUgxQGzQw7WjEAA==)
--------------------------------------------------------------------------------------------------
# 2. Logback Configuration Externalization Instructions

## 2.1. Quick Start

Please follow the steps below to quickly startup the application. For details and customization, please see the following topics.

### Steps

Running in Spring Tool Suite(STS)

1. Added two system property variables in catalina.properties under {CATALINA_HOME} or Pivotal “Servers” in STS
 
   - AUTO_SCAN=true

   - SCAN_PERIOD=30 seconds          

2. Start consent2share-bl server
3. Go to the application home page and log in the role as `System Admin`
4. Go to the site `https://localhost:8443/consent2share-pg/instrumentation/loggerCheck`
5. The page will show currently logger information

## 2.2. Logback Configuration Externalization

### 2.2.1. About Logback Configuration Externalization

Logback allows you to redefine logging behavior without needing to recompile your code. Currently the application adopts externalize part of the configuration file. The logback.xml in the application package. It could only contain configuration element and include element which includes the externalized configuration file. Part or all of the included configuration file path can be externalized as system property variables defined .

### 2.2.2. Default Configuration
There are two variables need to be defined in CATALINA properties in configuration file `logback.xml` and the values set as default value.

1. `AUTO_SCAN=true`

2. `SCAN_PERIOD=30 seconds`

There are three main value in the externalized configuration file `logback_included.xml`.

1. `<property name="APP_LOG_FOLDER" value="C:\\java\\C2S_LOGS\\pcm" />`
	
2. `<property name="LOG_NAME" value="pcm-pg" />`

3. `<property name="gov.samhsa.consent2share_lOGGER.level" value="warn" />`

The 1st set the path of generated loggers and 2rd value named the logger file. The 3rd value define logging behavior, developers can change this value to redefine its behavior.  



# 3. Node, Grunt, and LESS Compilation with Frontend Maven Plugin

The Frontend Maven Plugin will now install Node.js and npm, Grunt, and all of the dependanices required for Grunt to compile LESS files to CSS files. The LESS files are located in the __src/main/frontend/less/__ directory. When compiled, the CSS files will be generated in the __src/main/webapp/resources/css/__ directory.

By running `mvn clean install`, the Frontend Maven Plugin will automatically perform all of the above actions for you. There is a known bug in the Frontend Maven Plugin, where the build may fail the first two times you run `mvn clean install`. It should work on the third try, and after it starts working you should no longer have any problems with the plugin causing the build to fail.

__NOTE:__ Every time you run `mvn clean install` and the LESS files are compiled to CSS, you will have to refresh the CSS directory in the Eclipse IDE in order for it to pick up the newly compiled CSS files. In the Eclipise IDE, simply expand the directory tree to locate the __src/main/webapp/resources/css/__ directory inside the __consent2share-web-pg__ project. Right click on the css folder and select "Refresh".

--------------------------------------------------------------------------------------------------

# 4. Logger Configuration RESTful API

Following the RESTful APIs below, the current logger level can be checked and CXF in/out logging interceptors can be enabled/disabled for all concrete `AbstractCXFLoggingConfigurerClient` instances at runtime. This API requires a user session with `ROLE_SYSADMIN` role. Also for `PUT` API, a valid CSRF token must be provided with `X-CSRF-TOKEN` header. A valid CSRF token can be easily found in some of the system admin page DOMs.

+ Checking Log Level: 
	+ **(GET) https://{host}:{port}/consent2share-pg/instrumentation/loggerCheck**
+ Enable/Disable CXF Client Logging Interceptors:
	+ **(PUT) https://{host}:{port}/consent2share-pg/instrumentation/cxfLoggingInterceptors/enabled/{true/false}**
