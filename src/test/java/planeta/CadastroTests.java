package planeta;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import paginas.CadastroPages;

public class CadastroTests {
	
	static WebDriver driver;
	static CadastroPages cadastroPages;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.netflix.com/br/login");
		cadastroPages = new CadastroPages(driver);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		//driver.close();
	}

	@Test
	public void test() throws FileNotFoundException, InterruptedException {
		cadastroPages.preencherCampos();
	}

}
