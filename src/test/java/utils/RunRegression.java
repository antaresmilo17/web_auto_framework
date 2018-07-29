package utils;

import courgette.api.CourgetteOptions;
import courgette.api.CourgetteRunLevel;
import courgette.api.junit.Courgette;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Courgette.class)
@CourgetteOptions(
        threads = 2,
        runLevel = CourgetteRunLevel.FEATURE,
        rerunFailedScenarios = false,
        showTestOutput = true,
        cucumberOptions = @CucumberOptions(
                features = "./src/test/resources/features/general",
                glue = {"stepdef", "com.tigertext.automation.scenarioSettings"},
                plugin = {
                        "pretty",
                        "json:target/cucumber-report/cucumber.json",
                        "html:target/cucumber-report/cucumber.html"}
        ))
public class RunRegression {
}
