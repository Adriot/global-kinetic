package api_automation.create_jmx_file;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = {"@DogAPIDataDrivenCreateJMXFile"},
        features = {"src/test/java/api_automation/create_jmx_file/features"},
        glue = {"api_automation/create_jmx_file/steps"}
)

public class DogAPIDataDrivenCreateJMXFile {
}
