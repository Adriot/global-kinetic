package jmeter.run_jmx_file;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = {"@JMXFiles"},
        features = {"jmeter/run_jmx_file/features"},
        glue = {"jmeter/run_jmx_file/steps"}
)

public class RunJMXFiles {
}
