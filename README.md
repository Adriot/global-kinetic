# GLOBAL KINETIC
# TECHNICAL ASSESSMENT : QA AUTOMATION ENGINEER

I created a 3 branched for the respective sections:

- web_automation
- mobile_automation
- api_automation

I then merged them into a branch called practical which has all the 3 sections and then made it the default branch.

# IMPORTING/OPENING THE PROJEC:

Preferebly one can use intelliJ to import the project.

- Get the repository http url,
- Clone the project,
- Import the project on intellij or any IDE of your choice.

File -> new -> Project From Existing Sources -> Browse to your project path -> Use gradle wrapper if you like but preferebly.

After opening the project it will download the dependencies build. Give it some time.

When it is done building just confirm that it is in either of the following branches:

By default:
  - practical
  
You can checkout:
- web_automation
- mobile_automation
- api_automation
    
The branch practical has all the 3 sections of the practical assessment.
The others have the respective code for each section of the practical.
You can either checkout the individual branches for the separate sections or you can use practical to run all the scripts.

# WEB AUTOMATION

For the web automation section:
- Go to: src\test\java\web_automation
- Run the script GlobalKineticCareers

For review:
- Go to src\test\java\
Look at the packages:
- selenium.web.drive
- page_factory.global_kinetic

Data Sheets:
- The datasheet that it uses is on: src\test\resources\data\web_automation\Global Kinetic Data Sheet.xlsx
- The config file for data is on: src\test\resources\web_automation_data_config.properties

Reporting:
- The script generates screenshots and an excel report and allure reporting is also configured
- Find the screenshots on: src\test\resources\reporting\screenshots
- Find the excel report on: src\test\resources\reporting\<Timestamp> Global Kinetic Careers.xlsx
- To get the Allure report run the gradle tasks for allure on: other {allureReport, allureServe}
- After running the the respective allure tasks look on build to get the generated files.


# MOBILE AUTOMATION

For the Mobile Automation sections:
- Go to: src\test\java\mobile_automation
- Run the script GlobalKineticUniversalMusicPlayer

For review:
- Go to src\test\java\
Look at the packages:
- selenium.web.drive
- page_factory.universal_music_player

Reporting:
- The script does not generates any report but allure reporting is also configured
- To get the Allure report run the gradle tasks for allure on: other {allureReport, allureServe}
- After running the the respective allure tasks look on build to get the generated files.


# API AUTOMATION

For the API Automation setcion:
- Go to: src\test\java\api_automation
# REST Assured
NOTE: THIS IS A BDD Apprach

Go to the package: 
- api_automation.dog_api
- Run the script DogAPITest
- Features are under: features
- Steps are under: steps

Reporting:
- The script does generates and html report on: target/cucumber-reports/api/i_lab/dog_api/report.html and allure reporting is also configured
- To get the Allure report run the gradle tasks for allure on: other {allureReport, allureServe}
- After running the the respective allure tasks look on build to get the generated files.

# JMETER
NOTE: THIS IS A BDD Apprach

# RUN JMX FILE (EXISTING ONE)
This takes an existing JMX file and run it using the JMeter Engine And Genarte the artifacts for reporting
Go to the package: 
- api_automation.run_jmx_file
- Run the script DogAPIRunJMXFiles
- Features are under: features
- Steps are under: steps

Config File:
- The config file is on: src\test\resources\config.properties

Data Sheets:
- The datasheet is on: src\test\resources\data\Dog API JMX Files.xlsx

Reporting:
- The script does generates and html report on the reporting path on a folder named as the test plan name on the datasheet.

# CREATE AND RUN JMX FILE (NEW FROM CODE)
This creates a JMX file from java code and run it using the JMeter Engine And Genarte the artifacts for reporting
Go to the package: 
- api_automation.create_jmx_file
- Run the script DogAPIDataDrivenCreateJMXFile
- Features are under: features
- Steps are under: steps

Config File:
- The config file is on: src\test\resources\config.properties

Data Sheets:
- The datasheet is on: src\test\resources\data\create_jmx_files\DogAPIDataDrivenCreateJMXFile.xlsx

Reporting:
- The script does generates and html report on the reporting path on a folder named as the test plan name on the datasheet.
