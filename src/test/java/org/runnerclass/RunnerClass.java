package org.runnerclass;

import org.cucumberreport.JvmReport;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)

@CucumberOptions(features="src\\test\\resources",glue="org.stepdefinition",dryRun=false,
plugin= {"html:target\\reports",
		 "junit:target\\reports\\*****.xml",
		"json:target\\reports\\******.json",
		 "rerun:src\\test\\resources\\rerun\\Failed.txt"
}
)
public class RunnerClass {
	


@AfterClass
 public static void report() {
	JvmReport.generateJvmReport("G:\\selenium\\CucumberSample\\target\\reports\\Fb_login2.json");

}
}
