package pico.erp.process;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pico.erp.shared.data.Menu;
import pico.erp.shared.data.MenuCategory;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MENU implements Menu {

  PREPROCESS_TYPE_MANAGEMENT("/preprocess-type", "library_books", MenuCategory.SETTINGS),

  PROCESS_TYPE_MANAGEMENT("/process-type", "library_books", MenuCategory.SETTINGS),

  PROCESS_MANAGEMENT("/process", "show_chart", MenuCategory.PROCESS);

  String url;

  String icon;

  MenuCategory category;

  public String getId() {
    return name();
  }

}
