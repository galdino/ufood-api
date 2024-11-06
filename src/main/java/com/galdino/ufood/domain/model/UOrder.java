package com.galdino.ufood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.galdino.ufood.domain.model.status.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class UOrder extends AbstractAggregateRoot<UOrder> {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

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
    private Address address;

    @Enumerated(EnumType.STRING)
    private UOrderStatus status = UOrderStatus.CREATED;

    @OneToMany(mappedBy = "uorder", cascade = CascadeType.ALL)
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

    @Transient
    private StatusSituation statusSituation;

    public void setPartialAmount() {
        this.partialAmount = this.uorderItems
                .stream()
                .map(UOrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setTotalAmount() {
        this.totalAmount = this.partialAmount.add(this.deliveryFee);
    }

    public StatusSituation getStatusSituation() {
        switch (this.status) {
            case CREATED:
                return new CreatedSituation();
            case CONFIRMED:
                return new ConfirmedSituation();
            case DELIVERED:
                return new DeliveredSituation();
            case CANCELED:
                return new CanceledSituation();
            default: throw new IllegalStateException("Unexpected value: " + this.status);
        }
    }

    @PrePersist
    private void generateCode() {
        this.code = UUID.randomUUID().toString();
    }

    public void registerEventUOrder(Object event) {
        super.registerEvent(event);
    }


}
