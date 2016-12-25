package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	public static void main(String[] args) {
		System.out.println("Register Tests");
		Result registerTestResults = JUnitCore.runClasses(TestRegister.class); 
		for (Failure failure : registerTestResults.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(registerTestResults.wasSuccessful());
	}
}
