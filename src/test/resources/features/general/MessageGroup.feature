Feature: Test Group messaging features

  Background:
    Given I am logged into TigerText home page as "WebUser1"
    And I have an existing Group "Auto Test Existing"

  @prod @qa @env1 @env2 @env4
  Scenario: Verify user can send a message to an existing group
    When I send "random" message to "Auto Test Existing"
    Then the message is delivered to "Auto Test Existing"

  @prod @qa @env1 @env2 @env4
  Scenario: Verify user can cancel sending message to an existing group
    When I cancel sending message to "Auto Test Existing"
    Then the message is not delivered to "Auto Test Existing"

  @prod @qa @env1 @env2 @env4
  Scenario: Verify user can recall a message from an existing group
    And I send "random" message to "Auto Test Existing"
    When I recall the last message sent
    Then the message is not delivered to "Auto Test Existing"

  @prod @qa @env1 @env2 @env4
  Scenario: Verify user can see the message information for an existing group
    And I send "random" message to "Auto Test Existing"
    When I try to retrieve message info
    Then I should see sender as "WebUser1" and receiver as "Auto Test Existing"

  @prod @qa @env1 @env2 @env4
  Scenario: Verify user can print a conversation of an existing group
    And I send "random" message to "Auto Test Existing"
    When I click print preview
    Then I see message sent to "Auto Test Existing" in preview

  @prod @qa @env1 @env2 @env4
  Scenario Outline: Verify user can mute a group conversation for a given amount of time (8 hours, 1 day, 1 week, 1 year)
    And I send "random" message to "Auto Test Existing"
    When I mute conversation for "<duration>"
    Then the conversation is muted
    Examples:
      |duration|
      |8 hours |
      |1 Day   |
      |1 Week  |
      |1 Year  |

  @qa @env1 @env2 @env2 @env4
  Scenario: Verify user can send a priority message to an existing group
    When I send "priority" message to "Auto Test Existing"
    Then the priority message is delivered to "Auto Test Existing"

  @prod @qa @env1 @env2 @env4
  Scenario Outline: : Verify user can send different types of messages to an existing group (emoji, link, image)
    When I send "<MessageType>" message to "Auto Test Existing"
    Then the message has "<MessageType>"
    Examples:
      |MessageType|
      |emoji      |
      |link       |
      |image      |

  @cleanup @prod @qa @env1  @env2 @env4
  Scenario: Verify user can clear the conversation of an existing group
    When I clear conversation with "Auto Test Existing"
    Then I should see no conversation with "Auto Test Existing"
