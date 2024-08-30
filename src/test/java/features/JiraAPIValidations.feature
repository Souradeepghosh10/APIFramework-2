Feature: Jira API Validations

  Scenario Outline: Create Jira Bug
    Given Add Payload with "<DefectSummary>" and "<Name>"
    When user calls Jira Api "CreateJiraDefect" with "POST" http request
    Then the Jira API call got success with status code 201
    And add image to the same ticket with the end point "UpdateJiraDefect" with "POST" http request






    Examples:
    |DefectSummary|Name|
    | Automation testing Defect for doc1|Bug|