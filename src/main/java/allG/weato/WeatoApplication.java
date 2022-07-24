package allG.weato;

import allG.weato.config.oauth.properties.AppProperties;
import allG.weato.config.oauth.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties({AppProperties.class,CorsProperties.class})
@EnableJpaAuditing
@SpringBootApplication
public class WeatoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatoApplication.class, args);
	}

}
