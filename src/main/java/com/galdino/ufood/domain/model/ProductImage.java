package com.galdino.ufood.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Builder
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

    public ProductImage() {

    }

    public ProductImage(Long id, String fileName, String description, String contentType, Long size, Product product) {
        this.id = id;
        this.fileName = fileName;
        this.description = description;
        this.contentType = contentType;
        this.size = size;
        this.product = product;
    }
}
