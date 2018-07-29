@prod @stage @qa @env1 @env2 @env4
Feature: Group settings

  Scenario: Verify user can create a new group via message option
    Given I am logged into TigerText home page as "WebUser1"
    When I send "random" message to "WebGrpQa1" and "WebGrpQa2"
    Then new group of "WebUser1", "WebGrpQa1", "WebGrpQa2" is created

  Scenario: Verify user can create a new group via Hamburger menu
    Given I am logged into TigerText home page as "WebUser1"
    When I create a group of "WebUser2" and "WebQa3" with name "Auto Test New" via Hamburger menu
    Then new group "Auto Test New" is created

  Scenario: Verify user can add members to an existing group
    Given I am logged into TigerText home page as "WebUser1"
    When I add "WebUser4" to existing group "Group Settings Existing"
    Then "WebUser4" is added to existing Group "Group Settings Existing"

  Scenario: Verify user can remove members from an existing group
    Given I am logged into TigerText home page as "WebUser1"
    And I add "WebUser4" to existing group "Group Settings Existing"
    When I remove "WebUser4" from existing group "Group Settings Existing"
    Then "WebUser4" is not present in existing group "Group Settings Existing"

  Scenario: Verify user can edit the name of a new Group
    Given I am logged into TigerText home page as "WebUser1"
    And I create a group of "WebUser2" and "WebQa3" with name "Auto Test Edit" via Hamburger menu
    When  I change group name to "Auto Group Updated"
    Then group name is updated to "Auto Group Updated"

  Scenario: Verify user can cancel leaving a new group
    Given I am logged into TigerText home page as "WebUser1"
    And I create a group of "WebUser2" and "WebQa3" with name "Auto Test Cancel" via Hamburger menu
    When I cancel leaving group
    Then I see group "Auto Test Cancel"

  Scenario: Verify user can leave a group
    Given I am logged into TigerText home page as "WebUser1"
    And I create a group of "WebUser2" and "WebQa3" with name "Auto Test Leave" via Hamburger menu
    When I leave new group "Auto Test Leave"
    Then I do not see group "Auto Test Leave"

  @cleanup
  Scenario: Verify user can clear the conversation of group Webuser2
    Given I am logged into TigerText home page as "WebUser1"
    And I have an existing Group "WebUser2"
    When I clear conversation with "WebUser2"
    Then I should see no conversation with "WebUser2"

  @cleanup
  Scenario: Verify user can clear conversation of group WebUser1
    Given I am logged into TigerText home page as "WebUser2"
    And I have an existing Group "WebUser1"
    When I clear conversation with "WebUser1"
    Then I should see no conversation with "WebUser1"