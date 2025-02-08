Feature: Data Import Endpoint

  As an administrator,
  I want to import data from a file into the H2 database
  So that the data is saved successfully

  Background:
    Given the file "src/test/resources/event_records.xlsx" exists with valid data

  Scenario: Successful file import
    When I send a POST request to "/api/files/import"
    Then the response status should be 200
    And the response body should contain "File uploaded and data saved successfully."
