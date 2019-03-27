package pico.erp.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.stream.Stream;
import kkojaeh.spring.boot.component.Give;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pico.erp.config.process.info.BondingProcessInfo;
import pico.erp.config.process.info.CoatingProcessInfo;
import pico.erp.config.process.info.CuttingProcessInfo;
import pico.erp.config.process.info.DesigningProcessInfo;
import pico.erp.config.process.info.EmbossingProcessInfo;
import pico.erp.config.process.info.FoilingProcessInfo;
import pico.erp.config.process.info.LaminatingProcessInfo;
import pico.erp.config.process.info.MoldingProcessInfo;
import pico.erp.config.process.info.OutputProcessInfo;
import pico.erp.config.process.info.PackagingProcessInfo;
import pico.erp.config.process.info.PrintCoatingProcessInfo;
import pico.erp.config.process.info.PrintingProcessInfo;
import pico.erp.config.process.info.ThomsonProcessInfo;
import pico.erp.process.info.type.ClassBasedProcessInfoType;
import pico.erp.process.info.type.ProcessInfoType;

@Configuration
public class ProcessConfiguration {

  @SneakyThrows
  public static void main(String... args) {
    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule("interface-implements", Version.unknownVersion());
    module.addSerializer(new StdSerializer<Stream<?>>(Stream.class, true) {
      @Override
      public void serialize(Stream<?> stream, JsonGenerator jgen, SerializerProvider provider)
        throws IOException {
        provider.findValueSerializer(Iterator.class, null)
          .serialize(stream.iterator(), jgen, provider);
      }
    });
    // SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
    // module.setAbstractTypes(resolver);
    mapper.registerModule(module);
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    System.out.println(mapper.writeValueAsString(LocalDateTime.now()));
  }

  @Give
  @Bean
  public ProcessInfoType bondingProcessInfo() {
    return new ClassBasedProcessInfoType("bonding", BondingProcessInfo.class);
  }

  @Give
  @Bean
  public ProcessInfoType coatingProcessInfo() {
    return new ClassBasedProcessInfoType("coating", CoatingProcessInfo.class);
  }

  @Give
  @Bean
  public ProcessInfoType cuttingProcessInfo() {
    return new ClassBasedProcessInfoType("cutting", CuttingProcessInfo.class);
  }

  @Give
  @Bean
  public ProcessInfoType designingProcessInfo() {
    return new ClassBasedProcessInfoType("designing", DesigningProcessInfo.class);
  }

  @Give
  @Bean
  public ProcessInfoType embossingProcessInfo() {
    return new ClassBasedProcessInfoType("embossing", EmbossingProcessInfo.class);
  }

  @Give
  @Bean
  public ProcessInfoType foilingProcessInfo() {
    return new ClassBasedProcessInfoType("foiling", FoilingProcessInfo.class);
  }

  @Give
  @Bean
  public ProcessInfoType laminatingProcessInfo() {
    return new ClassBasedProcessInfoType("laminating", LaminatingProcessInfo.class);
  }

  @Give
  @Bean
  public ProcessInfoType moldingProcessInfo() {
    return new ClassBasedProcessInfoType("molding", MoldingProcessInfo.class);
  }

  @Give
  @Bean
  public ProcessInfoType outputProcessInfo() {
    return new ClassBasedProcessInfoType("output", OutputProcessInfo.class);
  }

  @Give
  @Bean
  public ProcessInfoType packagingProcessInfo() {
    return new ClassBasedProcessInfoType("packaging", PackagingProcessInfo.class);
  }

  @Give
  @Bean
  public ProcessInfoType printCoatingProcessInfo() {
    return new ClassBasedProcessInfoType("print-coating", PrintCoatingProcessInfo.class);
  }

  @Give
  @Bean
  public ProcessInfoType printProcessInfo() {
    return new ClassBasedProcessInfoType("printing", PrintingProcessInfo.class);
  }

  @Give
  @Bean
  public ProcessInfoType thomsonProcessInfo() {
    return new ClassBasedProcessInfoType("thomson", ThomsonProcessInfo.class);
  }




}
