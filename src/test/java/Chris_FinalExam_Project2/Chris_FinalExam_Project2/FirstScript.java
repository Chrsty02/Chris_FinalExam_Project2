package Chris_FinalExam_Project2.Chris_FinalExam_Project2;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FirstScript {
	 @Test
	  public void testMethod() {
		  System.out.println("This is test method");
	  }
	  
	  @Test
	  public void testMethod2() {
		  System.out.println("This is test method 2");
	  }
	  
	  @BeforeMethod
	  public void beforeMethod() {
		  System.out.println("This is before method");
	  }

	  @AfterMethod
	  public void afterMethod() {
		  System.out.println("This is after method");
	  }

	  @BeforeClass
	  public void beforeClass() {
		  System.out.println("This is before class");
	  }

	  @AfterClass
	  public void afterClass() {
		  System.out.println("This is after class");
	  }

	  @BeforeTest
	  public void beforeTest() {
		  System.out.println("This is before test");
	  }

	  @AfterTest
	  public void afterTest() {
		  System.out.println("This is after test");
	  }

	  @BeforeSuite
	  public void beforeSuite() {
		  System.out.println("This is before Suite");
	  }

	  @AfterSuite
	  public void afterSuite() {
		  System.out.println("This is after Suite");
	  }

	}

