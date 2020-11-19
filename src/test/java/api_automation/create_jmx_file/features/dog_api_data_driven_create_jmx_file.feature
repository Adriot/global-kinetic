@DogAPIDataDrivenCreateJMXFile
Feature: Dog API Data Driven Create JMX File
    
Scenario: Dog API Data Driven Create JMX File
    Given
        Then Get User Defined Variables Sheet to use at "DOG_API_TEST_JMETER_ENGINE_DATASHEET,USER_VARIABLES_SHEET"
        Then Get Boundary Extractors Sheet to use at "DOG_API_TEST_JMETER_ENGINE_DATASHEET,BOUNDARY_EXTRACTORS_SHEET"
        Then Get Loggers List to add at "DOG_API_TEST_JMETER_ENGINE_DATASHEET,LOGGERS_SHEET"
        Then Get Test Plan Data to use at "DOG_API_TEST_JMETER_ENGINE_DATASHEET,TEST_PLANS_SHEET"
        Then Run Test Plans