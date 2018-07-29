Feature: Test Message Everyone

  Background:
    Given I am logged into TigerText home page as "WebUser1"

  @qa @env1 @env2 @env4
  Scenario: Verify user can send a message to everyone broadcast
    When I send "random" message to "Everyone"
    Then the message is delivered to "Everyone"

  @qa @env1 @env2 @env4
  Scenario: Verify user can cancel sending a message to everyone broadcast
    When I cancel sending message to "Everyone"
    Then the message is not delivered to "Everyone"

  @qa @env1 @env2 @env4
  Scenario: Verify user can recall a message from everyone broadcast
    And I send "random" message to "Everyone"
    When I recall the last message sent
    Then the message is not delivered to "Everyone"

  @qa @env1 @env2 @env4
  Scenario: Verify user can retrieve the message information from everyone broadcast
    And I send "random" message to "Everyone"
    When I try to retrieve message info
    Then I should see sender as "WebUser1" and receiver as "Everyone"

  @qa @env1 @env2 @env4
  Scenario: Verify user can print a conversation from everyone broadcast
    And I send "random" message to "Everyone"
    When I click print preview
    Then I see message sent to "Everyone" in preview

  @qa @env1 @env2 @env4
  Scenario: Verify user can send a priority message to everyone broadcast
    When I send "priority" message to "Everyone"
    Then the priority message is delivered to "Everyone"

  @qa @env1 @env2 @env4
  Scenario Outline: Verify user can send different types of messages to everyone broadcast (link, emoji, image)
    When I send "<MessageType>" message to "Everyone"
    Then the message has "<MessageType>"
    Examples:
      |MessageType|
      |link       |
      |emoji      |
      |image      |

  @qa @env1 @env2 @env4
  Scenario: Verify user can clear a conversation from everyone broadcast
    When I clear conversation with "Everyone"
    Then I should see no conversation with "Everyone"
