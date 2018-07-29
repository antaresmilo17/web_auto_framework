@qa @env1 @env2 @env4
Feature:Forum settings

  Background:
    Given I am logged into TigerText home page as "Auto Admin"

  Scenario: Verify a user can create a new forum via admin tool
    When I create a forum of "WebUser1 " and "WebUser2" with name "QA New Forum" via admin tool
    Then new forum "QA New Forum" is created

  Scenario: Verify a user can remove members for a forum via admin tool
    When I remove "WebUser4" from forum "Auto QA Forum"
    Then "WebUser4" is not present in forum "Auto QA Forum"

  Scenario: Verify a user can add members to a forum via admin tool
    When I add "WebUser4" to existing Forum "Auto QA Forum" via admin tool
    Then I should see "WebUser4" added to forum "Auto QA Forum"

  Scenario: Verify a user can edit the name of a forum via admin tool
    When I change forum name from "Auto Change Forum" to "Auto Change QA Edit Members Forums"
    Then forum name is changed to "Auto Change QA Edit Members Forums"

  @cleanup
  Scenario: Verify user can change Test QA Edit Members Forums back to its original name
    When I change forum name from "Auto Change QA Edit Members Forums" to "Auto Change Forum"
    Then forum name is changed to "Auto Change Forum"

  Scenario: Verify a user can explore forums
    When I explore forums
    Then I see "Public Forum" in explore forum menu

  Scenario: Verify a user can leave an existing forum via admin tool
    When I leave forum "Auto QA Leave Forum"
    Then I do not see forum "Auto QA Leave Forum"

  @cleanup
  Scenario: Verify user can join the forum Auto QA Forum
    When I add "Auto Admin" to existing Forum "Auto QA Leave Forum" via admin tool
    Then I should see "Auto Admin" added to forum "Auto QA Leave Forum"

  @cleanup
  Scenario: Verify user can delete the forum "QA New Forum" via admin tools
    When I delete forum "QA New Forum" via admin tools
    Then I do not see forum "QA New Forum" in admin tools