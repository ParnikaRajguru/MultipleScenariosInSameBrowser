package com.deno.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests; 
import io.cucumber.testng.CucumberOptions; 
 
@CucumberOptions( 
        features = "src/test/resources/features", 
        glue = {"com.deno.stepDefinitions", "com.deno.hooks"}, 
        plugin = {"pretty"}, 
        monochrome = true 
) 
public class RunCucumberTest extends AbstractTestNGCucumberTests { 
} 
