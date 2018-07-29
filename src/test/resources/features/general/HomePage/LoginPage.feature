Feature: Test TigerText login

  Background:
    Given I am on TigerText Login Page

  @prod @stage @qa @env1 @env2 @env4
  Scenario: Verify Login to TigerText fails with an empty username
    When I enter blank username
    Then I should see error message

  @prod @stage @qa @env1 @env2 @env4
  Scenario: Verify Login to TigerText fails with an incorrect username
    When I enter incorrect username
    Then I should be asked to "Try Again"

  @ihisignore @prod @stage @qa @env1 @env2 @env4
  Scenario: Verify user is directed to the "Forgot password" page set up when clicking in the "Forgot Password" link
    When I enter correct username
    And I click Forgot Password
    Then I should see Password setup Page

  @ignore
  Scenario: Verify user is directed to the "TigerText Download" page when clicking on the Download Now link
    When I click "Download Now" link
    Then I should see "TigerText Download" page

  @prod @stage @qa @env1 @env2 @env4
  Scenario: Verify user is directed to the "iTunes Download" page when clicking on the iPhone link
    When I click "iPhone" link
    Then I should see "iTunes Download" page

  @prod @stage @qa @env1 @env2 @env4
  Scenario: Verify user is directed to the "iTunes Download" page when clicking on the iPad link
    When I click "iPad" link
    Then I should see "iTunes Download" page

  @prod @stage @qa @env1 @env2 @env4
  Scenario: Verify user is directed to the "Google Play Download" page when clicking on the Android link
    When I click "Android" link
    Then I should see "Google Play Download" page

  @prod @stage @qa @env1 @env2 @env4
  Scenario: Verify user is directed to the "End User License agreement" page when clicking on the Terms link
    When I click "Terms" link
    Then I should see "End User license agreement" page

  @prod @stage
  Scenario: Verify user is directed to the "Privacy" page when clicking on the Privacy link
    When I click "Privacy" link
    Then I should see "Privacy" page

  @prod @stage
  Scenario: Verify user is directed to the "TigerText Business" page when clicking on the TigerText Business link
    When I click "TigerText Business" link
    Then I should see "TigerText Business" page

  @prod @stage @qa @env1 @env2 @env4 @ignore
  Scenario: Verify user is directed to the "Support" page when clicking on the Support link
    When I click Support link
    Then I should see "Support" page

  @prod @stage @qa @env1 @env2 @env4
  Scenario: Verify user can Login to TigerText using correct credentials
    When I enter "correct" credentials
    Then I should see TigerText home page

  @smokeprod @smoke @prod @stage @qa @env1 @env2 @env4
  Scenario: Verify user can Log out
    Given I am logged in to TigerText Home page
    When I click Sign out button
    Then I should see TigerText login page

  @saml @ignore
  Scenario: Verify user can Login to a SAML account
    And I enter "SAML" credentials
    When I click the OneLogin log in button
    Then I should see the user inbox

  @ihis
  Scenario: Verify user can Login to a IHIS account
    And I enter "IHIS" credentials
    Then I should see TigerText home page

  @autoLogout @ignore
  Scenario: Verify user gets timed out after being idled for 10 minutes
    Given I am logged into TigerText home page as "Auto Logout"
    When I wait for 10 minutes
    Then I should see the timeout screen