package allG.weato;

import allG.weato.config.apiConfig.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class WeatoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatoApplication.class, args);
	}

}
