package Chris_FinalExam_Project2.Chris_FinalExam_Project2;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FirstSelenium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       WebDriver driver = new ChromeDriver();
       driver.get("https://demo.guru99.com/test/newtours/login.php");
       
       System.out.println("Title of the page is: " + driver.getTitle());
       
	};

}
