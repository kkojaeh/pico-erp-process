package pico.erp.process;

import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.shared.ApplicationInitializer;

@Transactional
@Configuration
@Profile({"!development", "!production"})
public class TestDataInitializer implements ApplicationInitializer {

  @Lazy
  @Autowired
  private ProcessTypeService processTypeService;

  @Lazy
  @Autowired
  private PreprocessTypeService preprocessTypeService;

  @Lazy
  @Autowired
  private ProcessService processService;


  @Autowired
  private DataProperties dataProperties;

  @Override
  public void initialize() {

    dataProperties.processTypes.forEach(processTypeService::create);
    dataProperties.preprocessTypes.forEach(preprocessTypeService::create);
    dataProperties.processTypePreprocessTypes.forEach(processTypeService::add);
    dataProperties.processes.forEach(processService::create);
    dataProperties.planCompletedProcesses.forEach(processService::completePlan);

  }

  @Data
  @Configuration
  @ConfigurationProperties("data")
  public static class DataProperties {

    List<ProcessTypeRequests.CreateRequest> processTypes = new LinkedList<>();

    List<PreprocessTypeRequests.CreateRequest> preprocessTypes = new LinkedList<>();

    List<ProcessTypeRequests.AddPreprocessTypeRequest> processTypePreprocessTypes = new LinkedList<>();

    List<ProcessRequests.CreateRequest> processes = new LinkedList<>();

    List<ProcessRequests.CompletePlanRequest> planCompletedProcesses = new LinkedList<>();

  }

}
