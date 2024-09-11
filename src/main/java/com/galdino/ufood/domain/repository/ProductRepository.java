package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.Product;
import com.galdino.ufood.domain.model.ProductImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CustomJpaRepository<Product, Long>, ProductRepositoryQueries {

    @Query("select pi from ProductImage pi join pi.product p where p.restaurant.id =:restaurantId and pi.product.id =:productId")
    Optional<ProductImage> findProductImageById(Long restaurantId, Long productId);
}
