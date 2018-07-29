@importUsers
Feature: Test Admin Settings
#  Command to run this: gradle test -Dorganization.name=ImportOrgTest2 -Dcucumber.options="--tags @importUsers"
#  If you want to run through here, add this to VM options (below the glue): -Dorganization.name=<ORG NAME>

  Scenario: Create Custom Organizations
    Given I am logged into TigerText home page as "Auto Admin"
    When I create a single custom organization
    Then the custom organization is created

  Scenario: Import Users
    Given I am logged into TigerText home page as "Auto Admin"
    And the org is switched to the new org created
    When I import users
    Then users are created
