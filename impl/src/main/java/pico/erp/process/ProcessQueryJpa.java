package pico.erp.process;

import static org.springframework.util.StringUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kkojaeh.spring.boot.component.Give;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.process.type.QProcessTypeEntity;
import pico.erp.shared.jpa.QueryDslJpaSupport;

@Service
@Give
@Transactional(readOnly = true)
@Validated
public class ProcessQueryJpa implements ProcessQuery {

  private final QProcessEntity process = QProcessEntity.processEntity;

  private final QProcessTypeEntity processType = QProcessTypeEntity.processTypeEntity;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private QueryDslJpaSupport queryDslJpaSupport;

  @Override
  public Page<ProcessView> retrieve(ProcessView.Filter filter, Pageable pageable) {
    val query = new JPAQuery<ProcessView>(entityManager);
    val select = Projections.bean(ProcessView.class,
      process.id,
      process.name,
      processType.id.as("typeId"),
      processType.name.as("typeName"),
      process.status,
      process.difficulty,
      process.lossRate,
      process.createdBy,
      process.createdDate,
      process.lastModifiedBy,
      process.lastModifiedDate
    );
    query.select(select);
    query.from(process);
    query.leftJoin(processType)
      .on(process.typeId.eq(processType.id));

    val builder = new BooleanBuilder();

    if (!isEmpty(filter.getName())) {
      builder.and(process.name
        .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getName(), "%")));
    }
    if (filter.getTypeId() != null) {
      builder.and(process.typeId.eq(filter.getTypeId()));
    }

    query.where(builder);
    return queryDslJpaSupport.paging(query, pageable, select);
  }
}
