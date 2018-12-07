package pico.erp.process.type.xcelite;

import com.ebay.xcelite.annotations.Column;
import com.ebay.xcelite.annotations.Row;
import com.ebay.xcelite.converters.ColumnValueConverter;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.process.info.type.ProcessInfoTypeId;
import pico.erp.process.type.ProcessTypeId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Row(colsOrder = {"id", "name", "baseUnitCost", "infoTypeId", "costRates", "difficultyGrades"})
public class ProcessTypeXportData {

  @Column(converter = ProcessTypeIdColumnValueConverter.class)
  ProcessTypeId id;

  @Column
  String name;

  @Column(converter = BigDecimalColumnValueConverter.class)
  BigDecimal baseUnitCost;

  @Column(converter = ProcessInfoTypeIdColumnValueConverter.class)
  ProcessInfoTypeId infoTypeId;

  @Column
  String costRates;

  @Column
  String difficultyGrades;


  public static class ProcessTypeIdColumnValueConverter implements
    ColumnValueConverter<String, ProcessTypeId> {

    @Override
    public ProcessTypeId deserialize(String value) {
      return ProcessTypeId.from(value);
    }

    @Override
    public String serialize(ProcessTypeId value) {
      return value.getValue();
    }

  }

  public static class ProcessInfoTypeIdColumnValueConverter implements
    ColumnValueConverter<String, ProcessInfoTypeId> {

    @Override
    public ProcessInfoTypeId deserialize(String value) {
      return ProcessInfoTypeId.from(value);
    }

    @Override
    public String serialize(ProcessInfoTypeId value) {
      return value.getValue();
    }

  }

  public static class BigDecimalColumnValueConverter implements
    ColumnValueConverter<String, BigDecimal> {

    @Override
    public BigDecimal deserialize(String value) {
      return new BigDecimal(value);
    }

    @Override
    public String serialize(BigDecimal value) {
      return value.toString();
    }

  }


}
