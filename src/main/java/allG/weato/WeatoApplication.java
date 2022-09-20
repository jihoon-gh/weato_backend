package allG.weato;


import allG.weato.oauth2.properties.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class WeatoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatoApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000/**","https://weato.net/**")
						.allowedMethods("GET", "POST", "PATCH", "DELETE")
						.allowedHeaders("append,delete,entries,foreach,get,has,keys,set,values,Authorization")
						.allowCredentials(true);
			}
		};
	}

}
