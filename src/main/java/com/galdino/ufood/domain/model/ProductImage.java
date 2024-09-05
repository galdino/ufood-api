package com.galdino.ufood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ProductImage {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "product_id")
    private Long id;

    private String fileName;
    private String description;
    private String contentType;
    private Long size;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Product product;
}
