package com.galdino.ufood.infrastructure.repository;

import com.galdino.ufood.domain.model.ProductImage;
import com.galdino.ufood.domain.repository.ProductRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;
    @Transactional
    @Override
    public ProductImage save(ProductImage productImage) {
        return manager.merge(productImage);
    }
}
