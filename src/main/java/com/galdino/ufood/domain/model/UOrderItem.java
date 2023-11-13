package com.galdino.ufood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class UOrderItem {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "uorder_id", nullable = false)
    private UOrder uorder;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
