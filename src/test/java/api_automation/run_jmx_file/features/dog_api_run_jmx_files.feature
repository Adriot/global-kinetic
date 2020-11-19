@DogAPIRunJMXFiles
Feature: Dog API Run JMX Files
    
Scenario: Dog API Run JMX Files
    Given
        Then Get JMX Files Data to use at "DOG_API_JMX_FILES_DATASHEET,JMX_FILES_SHEET"
        Then Get JMX Loggers Data to use at "DOG_API_JMX_FILES_DATASHEET,JMX_LOGGERS_SHEET"
        Then Run JMX Files