package pico.erp.process.jpa;

import static org.springframework.util.StringUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.process.ProcessQuery;
import pico.erp.process.data.ProcessView;
import pico.erp.shared.Public;
import pico.erp.shared.jpa.QueryDslJpaSupport;

@Service
@Public
@Transactional(readOnly = true)
@Validated
public class ProcessQueryJpa implements ProcessQuery {

  private final QProcessEntity process = QProcessEntity.processEntity;

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
      process.itemId,
      process.type.id.as("typeId"),
      process.type.name.as("typeName"),
      process.status,
      process.difficulty,
      process.managerId,
      process.managerName,
      process.createdBy,
      process.createdDate,
      process.lastModifiedBy,
      process.lastModifiedDate
    );
    query.select(select);
    query.from(process);

    val builder = new BooleanBuilder();

    if (!isEmpty(filter.getName())) {
      builder.and(process.name
        .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getName(), "%")));
    }
    if (filter.getItemId() != null) {
      builder.and(process.itemId.eq(filter.getItemId()));
    }
    if (filter.getManagerId() != null) {
      builder.and(process.managerId.eq(filter.getManagerId()));
    }

    if (filter.getProcessTypeId() != null) {
      builder.and(process.type.id.eq(filter.getProcessTypeId()));
    }

    query.where(builder);
    return queryDslJpaSupport.paging(query, pageable, select);
  }
}
