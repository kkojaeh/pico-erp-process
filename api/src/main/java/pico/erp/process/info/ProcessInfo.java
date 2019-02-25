package pico.erp.process.info;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.util.Map;

/**
 * 공정의 진행시 필요한 데이터를 갖게되는 클래스
 */
@JsonTypeInfo(use = Id.CLASS, property = "@type")
public interface ProcessInfo {

  @JsonIgnore
  Map<String, String> getDisplayProperties();

}
