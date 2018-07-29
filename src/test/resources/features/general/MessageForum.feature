Feature: Test Forum messaging features

  Background:
    Given I am logged into TigerText home page as "Auto Admin"
    And I have an existing forum "Auto QA Forum"

  @qa @env1 @env2 @env4
  Scenario: Verify user can send a message to an existing forum
    And I send "random" message to "Auto QA Forum"
    Then the message is delivered to "Auto QA Forum"

  @qa @env1 @env2 @env4
  Scenario: Verify a user can cancel sending messages to a forum
    And I cancel sending message to "Auto QA Forum"
    Then the message is not delivered to "Auto QA Forum"

  @qa @env1 @env2 @env4
  Scenario: Verify user can recall a message sent to a forum
    And I send "random" message to "Auto QA Forum"
    When I recall the last message sent
    Then the message is not delivered to "Auto QA Forum"

  @qa @env1 @env2 @env4
  Scenario: Verify user can retrieve message information from a forum
    And I send "random" message to "Auto QA Forum"
    When I try to retrieve message info
    Then I should see sender as "Auto Admin" and receiver as "Auto QA Forum"

  @qa @env1 @env2 @env4
  Scenario: Verify user can print the conversation of a forum
    And I send "random" message to "Auto QA Forum"
    When I click print preview
    Then I see message sent to "Auto QA Forum" in preview

  @qa @env1 @env2 @env4
  Scenario Outline: Verify user can mute a forum conversation for a given amount of time (8 hours, 1 day, 1 week, 1 year)
    And I send "random" message to "Auto QA Forum"
    When I mute conversation for "<duration>"
    Then I should see the conversation is muted for forum "Auto QA Forum"
    Examples:
      |duration|
      |8 hours |
      |1 Day   |
      |1 Week  |
      |1 Year  |

  @qa @env1 @env2 @env4
  Scenario: Verify user can send priority messages to an existing forum
    And I send "priority" message to "Auto QA Forum"
    Then the priority message is delivered to "Auto QA Forum"

  @qa @env1 @env2 @env4
  Scenario Outline: Verify user can send different types of messages to a forum (link, emoji, image)
    And I send "<MessageType>" message to "Auto QA Forum"
    Then the message has "<MessageType>" to "Auto QA Forum"
    Examples:
      |MessageType|
      |link       |
      |emoji      |
      |image      |

  @qa @env1 @env2 @env4
  Scenario: Verify forum conversation can be cleared
    And I clear conversation with "Auto QA Forum"
    Then I should see no conversation with "Auto QA Forum"