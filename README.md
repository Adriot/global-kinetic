# global-kinetic
GLOBAL KINETIC TECHNICAL ASSESSMENT : QA AUTOMATION ENGINEER

I created a 3 branched for the respective sections:

- web_automation
- mobile_automation
- api_automation

I then merged them into a branch called practical which has all the 3 sections and then made it the default branch.

IMPORTING/OPENING THE PROJEC:

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

WEB AUTOMATION

For the web automation section:
- Go to src\test\java\mobile_automation
- Run the script GlobalKineticUniversalMusicPlayer

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

