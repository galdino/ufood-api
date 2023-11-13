package com.galdino.ufood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class UOrder {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "partial_amount", nullable = false)
    private BigDecimal partialAmount;

    @Column(name = "delivery_fee", nullable = false)
    private BigDecimal deliveryFee;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime registerDate;

    @Column(columnDefinition = "datetime")
    private LocalDateTime confirmedDate;

    @Column(columnDefinition = "datetime")
    private LocalDateTime canceledDate;

    @Column(columnDefinition = "datetime")
    private LocalDateTime deliveredDate;

    @Embedded
    private Address deliveryAddress;

    private UOrderStatus status;

    @OneToMany(mappedBy = "uorder")
    private List<UOrderItem> uorderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
