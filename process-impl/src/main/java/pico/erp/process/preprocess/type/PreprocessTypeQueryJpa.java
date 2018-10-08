package pico.erp.process.preprocess.type;

import static org.springframework.util.StringUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.shared.ExtendedLabeledValue;
import pico.erp.shared.LabeledValue;
import pico.erp.shared.Public;
import pico.erp.shared.data.LabeledValuable;
import pico.erp.shared.jpa.QueryDslJpaSupport;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional(readOnly = true)
@Validated
public class PreprocessTypeQueryJpa implements PreprocessTypeQuery {

  private final QPreprocessTypeEntity preprocessType = QPreprocessTypeEntity.preprocessTypeEntity;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private QueryDslJpaSupport queryDslJpaSupport;

  @Override
  public List<? extends LabeledValuable> asLabels(String keyword, long limit) {
    val query = new JPAQuery<LabeledValue>(entityManager);
    val select = Projections.bean(ExtendedLabeledValue.class,
      preprocessType.id.value.as("value"),
      preprocessType.name.as("label"),
      preprocessType.infoTypeName.as("subLabel")
    );
    query.select(select);
    query.from(preprocessType);
    query.where(preprocessType.name
      .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", keyword, "%")));
    query.orderBy(preprocessType.name.asc());
    query.limit(limit);
    return query.fetch();
  }

  @Override
  public Page<PreprocessTypeView> retrieve(PreprocessTypeView.Filter filter, Pageable pageable) {
    val query = new JPAQuery<PreprocessTypeView>(entityManager);
    val select = Projections.bean(PreprocessTypeView.class,
      preprocessType.id,
      preprocessType.name,
      preprocessType.baseCost,
      preprocessType.createdBy,
      preprocessType.createdDate,
      preprocessType.lastModifiedBy,
      preprocessType.lastModifiedDate,
      preprocessType.infoTypeId,
      preprocessType.infoTypeName
    );
    query.select(select);
    query.from(preprocessType);

    val builder = new BooleanBuilder();

    if (!isEmpty(filter.getName())) {
      builder.and(preprocessType.name
        .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getName(), "%")));
    }

    if (filter.getProcessInfoTypeId() != null) {
      builder.and(preprocessType.infoTypeId.eq(filter.getProcessInfoTypeId()));
    }

    query.where(builder);
    return queryDslJpaSupport.paging(query, pageable, select);
  }
}
