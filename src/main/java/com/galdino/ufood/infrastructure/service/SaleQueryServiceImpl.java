package com.galdino.ufood.infrastructure.service;

import com.galdino.ufood.domain.filter.DailySaleFilter;
import com.galdino.ufood.domain.model.UOrder;
import com.galdino.ufood.domain.model.dto.DailySale;
import com.galdino.ufood.domain.service.SaleQueryService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

@Service
public class SaleQueryServiceImpl implements SaleQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<DailySale> findDailySales(DailySaleFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<DailySale> query = builder.createQuery(DailySale.class);
        Root<UOrder> root = query.from(UOrder.class);

        Expression<Date> registerDateFunction = builder.function("date", Date.class, root.get("registerDate"));

        CompoundSelection<DailySale> selection = builder.construct(DailySale.class,
                                                        registerDateFunction,
                                                                   builder.count(root.get("id")),
                                                                   builder.sum(root.get("totalAmount")));

        query.select(selection);
        query.groupBy(registerDateFunction);

        return manager.createQuery(query).getResultList();
    }
}
