@DogApiFeatureTestScenarios
Feature: Dog Api Feature

  Scenario: Dog Api Feature Test Scenario
    Given Dog API Endpoints
    Then Verify that a successful message is returned when a user searches for random breeds
    Then Verify that bulldog is on the list of all breeds
    Then Retrieve all sub-breeds for bulldog and their respective images