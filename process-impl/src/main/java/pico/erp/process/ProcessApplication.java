package pico.erp.process;

import java.util.HashMap;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.util.UriTemplate;
import pico.erp.attachment.data.AttachmentCategory;
import pico.erp.attachment.data.AttachmentCategoryId;
import pico.erp.attachment.impl.AttachmentCategoryImpl;
import pico.erp.audit.data.AuditConfiguration;
import pico.erp.comment.subject.type.data.CommentSubjectType;
import pico.erp.comment.subject.type.data.CommentSubjectType.CommentSubjectTypeImpl;
import pico.erp.comment.subject.type.data.CommentSubjectTypeId;
import pico.erp.process.data.ProcessCostRatesData;
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

  @Public
  @Bean
  public AttachmentCategory preprocessAttachmentCategory() {
    return new AttachmentCategoryImpl(AttachmentCategoryId.from("preprocess"), "사전 공정");
  }

  @Bean
  @Public
  public CommentSubjectType preprocessCommentSubjectType(
    final @Value("${comment.uri.preprocess}") String template) {
    return new CommentSubjectTypeImpl(
      CommentSubjectTypeId.from("preprocess"),
      info -> new UriTemplate(template).expand(new HashMap<String, String>() {
        {
          put("subjectId", info.getSubjectId().getValue());
          put("commentId", info.getId().getValue());
        }
      })
    );
  }

  @Bean
  @Public
  public Role processAccessorRole() {
    return ROLE.PROCESS_ACCESSOR;
  }

  @Public
  @Bean
  public AttachmentCategory processAttachmentCategory() {
    return new AttachmentCategoryImpl(AttachmentCategoryId.from("process"), "공정");
  }

  @Bean
  @Public
  public CommentSubjectType processCommentSubjectType(
    final @Value("${comment.uri.process}") String template) {
    return new CommentSubjectTypeImpl(
      CommentSubjectTypeId.from("process"),
      info -> new UriTemplate(template).expand(new HashMap<String, String>() {
        {
          put("subjectId", info.getSubjectId().getValue());
          put("commentId", info.getId().getValue());
        }
      })
    );
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
