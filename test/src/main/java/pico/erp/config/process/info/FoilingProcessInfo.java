package pico.erp.config.process.info;

import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.SchemaIgnore;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.val;
import pico.erp.process.info.ProcessInfo;

@Data
@Attributes(title = "박", description = "박 공정에 필요한 정보")
public class FoilingProcessInfo implements ProcessInfo {

  @Attributes(title = "비고", format = "textarea")
  private String remark;

  @Override
  @SchemaIgnore
  public Map<String, String> getDisplayProperties() {
    val map = new HashMap<String, String>();
    map.put("비고", remark);
    return map;
  }

}
