package jmeter.data_driven_create_jmx_file;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = {"@DataDrivenCreateJMXFile"},
        features = {"src/test/java/jmeter/data_driven_create_jmx_file/features"},
        glue = {"jmeter/data_driven_create_jmx_file/steps"}
)

public class DataDrivenCreateJMXFile {
}
