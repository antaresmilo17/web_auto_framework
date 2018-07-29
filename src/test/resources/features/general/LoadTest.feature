Feature: Load test

  @loadTest
  Scenario: Load Test Single User QA
    Given I am on TigerText Login Page
    And I enter correct credentials for WebQa2 while load testing
    Then I clear WebQa2 web resources

  @ltLoadTest
  Scenario Outline: Track Log In Time for LT Users
    Given I am on TigerText Login Page
#    And I clear <lt users> web resources
    Then I enter correct credentials for <lt users> while load testing 5 times per user
    Examples:
    |lt users|
    | 1k1v2  |
    | 10k1v  |
    | 3k1v2  |
    | 5k1v2  |
    | 10k2v2 |
    | 15k1v  |
