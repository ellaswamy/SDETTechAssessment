# SDETTechAssessment

Getting Started
------------------------
SDETTechAssessment project has both API and UI tests. Clone the project and run it on your local

Pre-Requisites
------------------
1. Java7/Java8 are required
2. Any IDE that supports Java
3. Download maven from https://maven.apache.org/download.cgi
4. Configure environment variables for maven for your machine
  Mac machine:
  a. export M2_HOME=/users/<user>/<maven-path>/<maven-version> (e.g. export M2_HOME=/users/abc/downloads/apache-maven-3.6.1)
  b. export M2=$M2_HOME/bin
  c. export PATH=$M2:$PATH
  
  Windows machine:
  a. Go to system properties (Right click on "This PC" -->properties)
  b. Go to Advanced System Settings
  c. Click on Environment Variables
  d. Add environment variable "M2_HOME =<path to apache maven> (e.g. c://users/<username>/downloads/apache-maven-3.6.1-bin\apache-maven-3.6.1)
  e. Go to Path in the environment variables and click on Edit
  f. Click on "New"
  g. Enter bin path of maven. (e.g.c://users/<username>/downloads/apache-maven-3.6.1-bin\apache-maven-3.6.1\bin)
  
5. Verify if maven is present on your machine. Run below command to verify.
  mvn --version

EnvironmentConfig.properties
-------------------------------
This file is present under SDETTechAssessment/resources folder.
This file is used to set up the API Key and other environment variables such as Browser and Operating System for UI Tests

WebDrivers for UI tests
------------------------
1. Webdriver executables for Chrome and firefox (MAC and Windows) are present under SDETTechAssessment/resources.
2. If the tests need to be run on any other machine, respective drivers have to be downloaded https://www.seleniumhq.org/download/

TMDBTestData.xlsx
------------------------
This file is present under SDETTechAssessment/resources folder
This is used to input the test data from.
If new test data need to be added for the existing tests, add the data to existing sheets respectively

Running Tests
------------------------
Before running tests using maven, Right click on the project --> Go to Maven --> Click on Update Project. This updates all maven dependencies from maven repository
1. Go to the cloned location of the project in command prompt
2. run "mvn compile" command to compile the project
3. run "mvn test" command to run the tests
