package pico.erp.process.info.type;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.reinert.jjschema.v1.JsonSchemaFactory;
import com.github.reinert.jjschema.v1.JsonSchemaV4Factory;
import java.io.Serializable;
import java.util.Optional;
import lombok.Getter;
import lombok.SneakyThrows;
import pico.erp.process.info.ProcessInfo;

public class ClassBasedProcessInfoType implements ProcessInfoType {

  private final static JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

  static {
    schemaFactory.setAutoPutDollarSchema(true);
  }

  private final Class<? extends ProcessInfo> type;

  @Getter
  private final ProcessInfoTypeId id;

  @Getter
  private final String name;

  @Getter
  private final String description;

  @Getter
  private final Serializable metadata;

  public ClassBasedProcessInfoType(Class<? extends ProcessInfo> type) {
    this.id = ProcessInfoTypeId.from(type.getName());
    this.type = type;
    JsonNode schema = schemaFactory.createSchema(type);
    TextNode titleNode = (TextNode) schema.get("title");
    TextNode descriptionNode = (TextNode) schema.get("description");
    this.name = Optional.ofNullable(titleNode)
      .map(node -> node.asText())
      .orElse("N/A");
    this.description = Optional.ofNullable(descriptionNode)
      .map(node -> node.asText())
      .orElse("N/A");
    this.metadata = schema.toString();
  }

  @Override
  @SneakyThrows
  public ProcessInfo create() {
    return type.newInstance();
  }


  @Override
  public Class<? extends ProcessInfo> getType() {
    return type;
  }
}
