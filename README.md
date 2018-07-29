# web-automation
Web Automation using Java, Selenium , Cucumber , Gradle , Saucelabs

# Machine Setup:

1. Open Terminal
2. Install Homebew: https://brew.sh/
3. Install git: https://www.atlassian.com/git/tutorials/install-git
4. Install JAVA 8 SDK https://java.com/en/download/help/mac_install.xml
5. Setup the environment variable for JAVA_HOME
6. Install gradle: on terminal issue command: brew install gradle
7. Install IntelliJ IDEA "Community edition IDE": https://www.jetbrains.com/idea/download/#section=mac
8. Clone the repo: git clone git@github.com:tigertext/tt_web_automation.git.
9. Install the appropriate drivers: brew install chromedriver and brew install geckodriver

# IntelliJ Setup
1. Open IntelliJ and Under Configure, select Plugins.
2. Search and download the plugins for: Gherkins and Cucumber for Java
3. Close Dialog when done and select Open and open the project.
4. Look at the event log and you should see message saying "Unlinked Gradle Project?". Click Import Gradle Project
5. Uncheck the checkbox for "Create separate module per source set"
6. Use gradle wrapper task configuration
7. Click OK
8. Wait for some dependencies to download and you should get the dialog for "Import Gradle Project". Click OK with the checkbox checked.
9. Build the project.

# Troubleshooting IntelliJ Issues:
1. If there is an issue with no Project SDK selected, set it to 1.8 under File -> Project Structure -> Project Settings -> Project.
2. If there is a compile error with Java selection 1.9, go to File -> Project Structure -> Modules and set the Language Level to 8 - Lamdas
3. If running a test case and an error appears about glue/spring framework/transactiondetails, this is because of a bug with springframework. Edit the config of the run in IntelliJ and the glue should only be:
com.tigertext.automation.scenarioSettings stepdef

#Command to execute the automation
Command to run framework remotely
gradle test -Denvironment.type=qa -Dremote.browser=chrome -Dremote.platform="macOS 10.12" -Dremote.version=58.0 -Dcucumber.options="--tags @autoLogout"

Command to run framework locally with specific tag
gradle test -Dcucumber.options="--tags @autoLogout"

Parameters to run framework with Intelli J (VM):
-Dkey={ENTER CORRECT TIGER CONNECT API KEY}
-Dsecret={ENTER CORRECT TIGER CONNECT API SECRET KEY}

Parameters to run framework in Browserstack with Intelli J (VM):
-Dkey={ENTER CORRECT TIGER CONNECT API KEY}
-Dsecret={ENTER CORRECT TIGER CONNECT API SECRET KEY}
-Dremote.stackusername={ENTER CORRECT BROWSERSTACK USERNAME}
-Dremote.stackkey={ENTER CORRECT BROWSERSTACK KEY}

Tests can be configured for SauceLabs using parameters:
-Denvironment=qa/dev/prod
-Dremote.browser=chrome/firefox/safari
-Dremote.version=58.0 (for chrome), (53 for firefox)
-Dremote.platform="macOS 10.12"/"Windows 10"
-Dcucumber.options=$tag(any tag that you want to run)

Use this to change SauceLabs configurations
https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/

command to ignore newline characters
git config --global core.autocrlf input
