package id.co.edts.ticketservice.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"id.co.edts.*"})
@EnableJpaRepositories(basePackages = "id.co.edts.basedomain.repository")
@EntityScan(basePackages = "id.co.edts.basedomain.model")
public class InitConfig {

}
