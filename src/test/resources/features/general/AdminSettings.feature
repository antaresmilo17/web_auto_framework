@qa @env1 @env2 @env4
Feature: Test Admin Settings

  Scenario: Verify admin users can create new users
    Given I am logged into TigerText home page as "Auto Admin"
    When I create users "AdminWebAutoUser10, AdminWebAutoUser20"
    Then users "AdminWebAutoUser10, AdminWebAutoUser20" are created

  Scenario: Verify admin users can create a new group conversation via message option
    Given I am logged into TigerText home page as "WebQa2"
    And I create a group of "AdminWebAutoUser10" and "AdminWebAutoUser20" with name "New group via Admin" via Hamburger menu
    And I send "random" message to "New group via Admin"
    When I try to retrieve message info
    Then I see sender as "WebQa2" and Receiver as "New group via Admin" of the new group

  Scenario: Verify admin users can delete users via admin tool
    Given I am logged into TigerText home page as "Auto Admin"
    When I delete users "AdminWebAutoUser10, AdminWebAutoUser20"
    Then users "AdminWebAutoUser10, AdminWebAutoUser20" are deleted

  @clearResources
  Scenario: Verify admin is able to clear possible cn resources
    Given I am logged into TigerText home page as "Auto Admin"
    And I navigate to the customer support page
    And I search for user "SAML"
    When I select customer support "Devices" admin option
    Then I should be able to clear user web resources

  Scenario: Verify admin user can create custom organizations via admin tool
    Given I am logged into TigerText home page as "Auto Admin"
    When I create custom organizations
    Then the custom organizations are created