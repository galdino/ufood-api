package com.galdino.ufood.infrastructure.service;

import com.galdino.ufood.domain.filter.DailySaleFilter;
import com.galdino.ufood.domain.model.UOrder;
import com.galdino.ufood.domain.model.UOrderStatus;
import com.galdino.ufood.domain.model.dto.DailySale;
import com.galdino.ufood.domain.service.SaleQueryService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SaleQueryServiceImpl implements SaleQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<DailySale> findDailySales(DailySaleFilter filter, String timeOffset) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<DailySale> query = builder.createQuery(DailySale.class);
        Root<UOrder> root = query.from(UOrder.class);

        Expression<Date> convertTzFunction = builder.function("convert_tz", Date.class,
                                                                    root.get("registerDate"),
                                                                    builder.literal("+00:00"),
                                                                    builder.literal(timeOffset));

        Expression<Date> registerDateFunction = builder.function("date", Date.class, convertTzFunction);

        CompoundSelection<DailySale> selection = builder.construct(DailySale.class,
                                                                   registerDateFunction,
                                                                   builder.count(root.get("id")),
                                                                   builder.sum(root.get("totalAmount")));

        var predicates = new ArrayList<Predicate>();

        predicates.add(builder.in(root.get("status")).value(UOrderStatus.CONFIRMED).value(UOrderStatus.DELIVERED));

        if (filter.getRestaurantId() != null) {
            predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
        }

        if (filter.getInitialRegisterDate() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("registerDate"), filter.getInitialRegisterDate()));
        }

        if (filter.getFinalRegisterDate() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("registerDate"), filter.getFinalRegisterDate()));
        }

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(registerDateFunction);

        return manager.createQuery(query).getResultList();
    }
}
