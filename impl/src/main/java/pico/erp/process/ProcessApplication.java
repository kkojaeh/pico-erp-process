package pico.erp.process;

import kkojaeh.spring.boot.component.Give;
import kkojaeh.spring.boot.component.SpringBootComponent;
import kkojaeh.spring.boot.component.SpringBootComponentBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pico.erp.process.ProcessApi.Roles;
import pico.erp.shared.SharedConfiguration;
import pico.erp.shared.data.Role;

@Slf4j
@SpringBootComponent("process")
@EntityScan
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
@SpringBootApplication
@Import(value = {
  SharedConfiguration.class
})
public class ProcessApplication {

  public static void main(String[] args) {
    new SpringBootComponentBuilder()
      .component(ProcessApplication.class)
      .run(args);
  }
  @Bean
  @Give
  public Role processAccessorRole() {
    return Roles.PROCESS_ACCESSOR;
  }

  @Bean
  @Give
  public Role processManagerRole() {
    return Roles.PROCESS_MANAGER;
  }

  @Bean
  @Give
  public Role processTypeManagerRole() {
    return Roles.PROCESS_TYPE_MANAGER;
  }

}
