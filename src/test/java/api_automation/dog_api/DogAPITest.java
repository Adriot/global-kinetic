package api_automation.dog_api;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = {"@DogApiFeatureTestScenarios"},
        features = {"src/test/java/api_automation/dog_api/features"},
        glue = {"api_automation/dog_api/steps"},
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/api/i_lab/dog_api/report.html"},
        monochrome = true
)

public class DogAPITest {
}
