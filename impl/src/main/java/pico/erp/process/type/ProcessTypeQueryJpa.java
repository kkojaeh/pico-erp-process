package pico.erp.process.type;

import static org.springframework.util.StringUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kkojaeh.spring.boot.component.ComponentBean;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.shared.ExtendedLabeledValue;
import pico.erp.shared.LabeledValue;
import pico.erp.shared.data.LabeledValuable;
import pico.erp.shared.jpa.QueryDslJpaSupport;

@SuppressWarnings("Duplicates")
@Service
@ComponentBean
@Transactional(readOnly = true)
@Validated
public class ProcessTypeQueryJpa implements ProcessTypeQuery {

  private final QProcessTypeEntity processType = QProcessTypeEntity.processTypeEntity;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private QueryDslJpaSupport queryDslJpaSupport;

  @Override
  public List<? extends LabeledValuable> asLabels(String keyword, long limit) {
    val query = new JPAQuery<LabeledValue>(entityManager);
    val select = Projections.bean(ExtendedLabeledValue.class,
      processType.id.value.as("value"),
      processType.name.as("label")
    );
    query.select(select);
    query.from(processType);
    query.where(processType.name
      .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", keyword, "%")));
    query.orderBy(processType.name.asc());
    query.limit(limit);
    return query.fetch();
  }

  @Override
  public Page<ProcessTypeView> retrieve(ProcessTypeView.Filter filter, Pageable pageable) {
    val query = new JPAQuery<ProcessTypeView>(entityManager);
    val select = Projections.bean(ProcessTypeView.class,
      processType.id,
      processType.name,
      processType.baseUnitCost,
      processType.lossRate,
      processType.createdBy,
      processType.createdDate,
      processType.lastModifiedBy,
      processType.lastModifiedDate,
      processType.infoTypeId
    );
    query.select(select);
    query.from(processType);

    val builder = new BooleanBuilder();

    if (!isEmpty(filter.getName())) {
      builder.and(processType.name
        .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getName(), "%")));
    }

    if (filter.getInfoTypeId() != null) {
      builder.and(processType.infoTypeId.eq(filter.getInfoTypeId()));
    }

    query.where(builder);
    return queryDslJpaSupport.paging(query, pageable, select);
  }
}
