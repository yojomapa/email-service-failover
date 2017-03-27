package com.yojomapa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by yojomapa on 26/03/17.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features="src/it/resources", glue="com.yojomapa", plugin = {"pretty", "html:target/cucumber-html-report"})
public class CucumberRunnerIntegrationTest {
}
