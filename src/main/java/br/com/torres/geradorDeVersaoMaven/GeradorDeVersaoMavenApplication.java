package br.com.torres.geradorDeVersaoMaven;	

import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import br.com.torres.geradorDeVersaoMaven.view.interfaces.IMainScreenRender;

@SpringBootApplication
@EntityScan("br.com.torres.geradorDeVersaoMaven.JSON.components")
public class GeradorDeVersaoMavenApplication implements ApplicationRunner {

	@Autowired	
	private IMainScreenRender mainFrame; 
	
	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(GeradorDeVersaoMavenApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);
		context.close();
	}

	public void run(ApplicationArguments args) throws Exception {
	}

}
