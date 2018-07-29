Feature: Test peer to peer messaging

  Background:
    Given I am logged into TigerText home page as "WebUser1"

  @smokeprod @smoke @prod @stage @qa @env1 @env2 @env4
  Scenario: Verify user can send a message to a single user
    When I send "random" message to "WebQa1"
    Then the message is delivered to "WebQa1"

  @prod @stage @qa @env1 @env2 @env4
  Scenario: Verify user cancel sending message to a single user
    When I cancel sending message to "WebQa1"
    Then the message is not delivered to "WebQa1"

  @prod @stage @qa @env1 @env2 @env4
  Scenario: Verify user can recall a message sent to a single user
    And I send "random" message to "WebQa1"
    When I recall the last message sent
    Then the message is not delivered to "WebQa1"

  @prod @stage @qa @env1 @env2 @env4
  Scenario: Verify user can see the message information sent to a single user
    And I send "random" message to "WebQa1"
    When I try to retrieve message info
    Then I should see sender as "WebUser1" and receiver as "WebQa1"

  @prod @stage @qa @env1 @env2 @env4
  Scenario: Verify user can print a conversation of a single user
    And I send "random" message to "WebQa1"
    When I click print preview
    Then I see message sent to "WebQa1" in preview

  @prod @stage @qa @env1 @env2 @env4
  Scenario Outline: Verify user can mute a single user conversation for a given amount of time (8 hours, 1 day, 1 week, 1 year)
    And I send "random" message to "WebQa1"
    When I mute conversation for "<duration>"
    Then the conversation is muted
    Examples:
    |duration|
    |8 hours |
    |1 Day   |
    |1 Week  |
    |1 Year  |

  @qa @env1 @env2 @env4
  Scenario: Verify user can send a priority message to a single user
    When I send "priority" message to "WebQa1"
    Then the priority message is delivered to "WebQa1"

  @smokeprod @smoke @prod @stage @qa @env1 @env2 @env4 @IEignore
  Scenario: Verify user can send image messages to a single user
    When I send "image" message to "WebQa1"
    Then the message has "image"

  @smokeprod @smoke @prod @stage @qa @env1 @env2 @env4
  Scenario Outline: Verify user can send different types of messages to a single user (link and emoji)
    When I send "<MessageType>" message to "WebQa1"
    Then the message has "<MessageType>"
    Examples:
    |MessageType|
    |link       |
    |emoji      |

  @smokeprod @smoke @prod @stage @qa @env1 @env2 @env4
  Scenario: Verify user can clear the conversation of a single user
    When I clear conversation with "WebQa1"
    Then I should see no conversation with "WebQa1"
