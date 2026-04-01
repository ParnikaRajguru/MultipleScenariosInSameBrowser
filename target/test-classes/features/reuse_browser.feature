Feature: Reusing Browser with Excel

Scenario: Login using reused browser with Excel data
  Given user opens the login page
  When user logs in using excel data

Scenario: Logout using the same reused browser
  Given user is already using the same browser session
  And user opens the login page
  When user logs in with username "tomsmith" and password "SuperSecretPassword!"
  And user clicks logout button
  Then user should see logout message
