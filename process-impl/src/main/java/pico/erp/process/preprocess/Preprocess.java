package pico.erp.process.preprocess;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pico.erp.attachment.AttachmentId;
import pico.erp.audit.annotation.Audit;
import pico.erp.comment.subject.CommentSubjectId;
import pico.erp.process.Process;
import pico.erp.process.info.ProcessInfo;
import pico.erp.process.preprocess.type.PreprocessType;
import pico.erp.shared.event.Event;
import pico.erp.user.UserData;

/**
 * 공정은 하나의 품목에 국한 될수 없다 지게차 이동 및 재고 조사 등의 작업은 도출되는 결과가 품목이 아닐 수 없다 작업과 공정의 차이가 혼동 될 수 있지만 투입과 결과라는
 * 내용은 이미 BOM 을 통해 명시하고 있기 때문에 해당 공정에서는 공정에 대한 결과를 명시하지 않는다
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Audit(alias = "preprocess")
public class Preprocess implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  PreprocessId id;

  String name;

  Process process;

  PreprocessType type;

  PreprocessStatusKind status;

  BigDecimal chargeCost;

  String description;

  UserData manager;

  CommentSubjectId commentSubjectId;

  AttachmentId attachmentId;

  ProcessInfo info;

  boolean deleted;

  OffsetDateTime deletedDate;

  public Preprocess() {
    deleted = false;
  }

  public PreprocessMessages.CreateResponse apply(PreprocessMessages.CreateRequest request) {
    this.id = request.getId();
    this.process = request.getProcess();
    this.type = request.getType();
    this.status = PreprocessStatusKind.DRAFT;
    this.description = request.getDescription();
    this.manager = request.getManager();
    this.commentSubjectId = CommentSubjectId.from(this.id.getValue().toString());
    this.attachmentId = request.getAttachmentId();
    this.chargeCost = request.getChargeCost();
    this.info = request.getProcessInfoLifecycler().initialize(this.getType().getInfoTypeId());
    this.name = String.format("%s (%s)", type.getName(), this.process.getItem().getCode());
    return new PreprocessMessages.CreateResponse(
      Arrays.asList(new PreprocessEvents.CreatedEvent(this.id))
    );
  }

  public PreprocessMessages.UpdateResponse apply(PreprocessMessages.UpdateRequest request) {
    if (!isUpdatable()) {
      throw new PreprocessExceptions.CannotUpdateException();
    }
    Preprocess old = toBuilder().build();
    this.description = request.getDescription();
    this.manager = request.getManager();
    this.attachmentId = request.getAttachmentId();

    Collection<Event> events = new LinkedList<>();
    if (!this.type.equals(old.type)) {
      ProcessInfo info = request.getProcessInfoLifecycler()
        .initialize(this.getType().getInfoTypeId());
      if (!info.getClass().equals(old.info.getClass())) {
        this.info = info;
      }
    }
    events.add(new PreprocessEvents.UpdatedEvent(this.id));
    return new PreprocessMessages.UpdateResponse(events);
  }

  public PreprocessMessages.DeleteResponse apply(PreprocessMessages.DeleteRequest request) {
    deleted = true;
    deletedDate = OffsetDateTime.now();
    return new PreprocessMessages.DeleteResponse(
      Arrays.asList(new PreprocessEvents.DeletedEvent(this.id))
    );
  }


  public boolean isUpdatable() {
    return status == PreprocessStatusKind.DRAFT;
  }


}
