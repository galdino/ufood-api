package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.ProductImage;

public interface ProductRepositoryQueries {

    ProductImage save(ProductImage productImage);
    void delete(ProductImage productImage);

}
