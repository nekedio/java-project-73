package hexlet.code.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import static hexlet.code.config.SpringConfigForTest.TEST_PROFILE;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile(TEST_PROFILE)
@ComponentScan(basePackages = "hexlet.code")
@PropertySource(value = "classpath:/config/application.yml")
public class SpringConfigForTest {

    public static final String TEST_PROFILE = "test";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
