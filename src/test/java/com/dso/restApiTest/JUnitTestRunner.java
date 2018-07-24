package com.dso.restApiTest;



import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.dso.restApiTest.controller.ComputerController;
import com.dso.restApiTest.models.computer.addComputerTest;
import com.dso.restApiTest.models.computer.editComputerTest;

@RunWith(SpringRunner.class)
@SpringBootTest(
		classes = {Application.class, ComputerController.class},
		
		webEnvironment = WebEnvironment.DEFINED_PORT
		)

public class JUnitTestRunner {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(addComputerTest.class);
	
	public static void main(String[] args) {
		//This result object has many methods and it is very useful
		//Type result and press dot, all the methods will display
		//This statement is to load all type of results in the result object
		Result result = JUnitCore.runClasses(addComputerTest.class, editComputerTest.class);
		//Here it is getting the run count from the result object
		log.debug("       Total number of tests: " + result.getRunCount());
		//This is to get the failure count from the result object
		log.debug("Total number of tests failed: " + result.getFailureCount());

		for(Failure failure : result.getFailures())
		{	
			//This will print message only in case of failure
			log.debug(failure.getMessage());
		}
		//This will print the overall test result in boolean type
		log.debug("               wasSuccessful: " + result.wasSuccessful());

	}

}
