package com.galdino.ufood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
    private OffsetDateTime registerDate;

    @Column(columnDefinition = "datetime")
    private OffsetDateTime confirmedDate;

    @Column(columnDefinition = "datetime")
    private OffsetDateTime canceledDate;

    @Column(columnDefinition = "datetime")
    private OffsetDateTime deliveredDate;

    @Embedded
    private Address deliveryAddress;

    @Enumerated(EnumType.STRING)
    private UOrderStatus status = UOrderStatus.CREATED;

    @OneToMany(mappedBy = "uorder")
    private List<UOrderItem> uorderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
