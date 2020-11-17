@DataDrivenCreateJMXFile
Feature: Data Driven Create JMX File
    
Scenario: Data Driven Create JMX File
    Given
        Then Get User Defined Variables Sheet to use at "TEST_JMETER_ENGINE_DATASHEET,USER_VARIABLES_SHEET"
        Then Get Boundary Extractors Sheet to use at "TEST_JMETER_ENGINE_DATASHEET,BOUNDARY_EXTRACTORS_SHEET"
        Then Get Loggers List to add at "TEST_JMETER_ENGINE_DATASHEET,LOGGERS_SHEET"
        Then Get Test Plan Data to use at "TEST_JMETER_ENGINE_DATASHEET,TEST_PLANS_SHEET"
        Then Run Test Plans