package pico.erp.process;

import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import pico.erp.audit.AuditConfiguration;
import pico.erp.process.cost.ProcessCostRatesData;
import pico.erp.shared.ApplicationStarter;
import pico.erp.shared.Public;
import pico.erp.shared.SpringBootConfigs;
import pico.erp.shared.data.Role;
import pico.erp.shared.impl.ApplicationImpl;

@Slf4j
@SpringBootConfigs
public class ProcessApplication implements ApplicationStarter {

  public static final String CONFIG_NAME = "process/application";

  public static final String CONFIG_NAME_PROPERTY = "spring.config.name=process/application";

  public static final Properties DEFAULT_PROPERTIES = new Properties();

  static {
    DEFAULT_PROPERTIES.put("spring.config.name", CONFIG_NAME);
  }

  public static SpringApplication application() {
    return new SpringApplicationBuilder(ProcessApplication.class)
      .properties(DEFAULT_PROPERTIES)
      .web(false)
      .build();
  }

  public static void main(String[] args) {
    application().run(args);
  }

  @Bean
  @Public
  public AuditConfiguration auditConfiguration() {
    return AuditConfiguration.builder()
      .packageToScan("pico.erp.process")
      .entity(ROLE.class)
      .valueObject(ProcessCostRatesData.class)
      .build();
  }

  @Override
  public int getOrder() {
    return 5;
  }

  @Override
  public boolean isWeb() {
    return false;
  }

  @Bean
  @Public
  public Role processAccessorRole() {
    return ROLE.PROCESS_ACCESSOR;
  }

  @Bean
  @Public
  public Role processManagerRole() {
    return ROLE.PROCESS_MANAGER;
  }

  @Bean
  @Public
  public Role processTypeManagerRole() {
    return ROLE.PROCESS_TYPE_MANAGER;
  }

  @Override
  public pico.erp.shared.Application start(String... args) {
    return new ApplicationImpl(application().run(args));
  }

}
