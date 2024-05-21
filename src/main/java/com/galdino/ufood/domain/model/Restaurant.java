package com.galdino.ufood.domain.model;

import com.galdino.ufood.core.validation.ValueZeroAddDescription;
import com.galdino.ufood.domain.exception.ProductNotFoundException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ValueZeroAddDescription(fieldValue = "deliveryFee", fieldDescription = "name", mandatoryDescription = "Free Delivery")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurant {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "delivery_fee", nullable = false)
    private BigDecimal deliveryFee;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "kitchen_id", nullable = false)
    private Kitchen kitchen;

    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime registerDate;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime updateDate;

    @Column(nullable = false)
    private Boolean active = Boolean.TRUE;

    @Column(nullable = false)
    private Boolean open = Boolean.TRUE;

    @ManyToMany//(fetch = FetchType.EAGER)
    @JoinTable(name = "restaurant_payment_method", joinColumns = @JoinColumn(name = "restaurant_id"),
               inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean removePaymentMethod(PaymentMethod paymentMethod) {
        return this.paymentMethods.remove(paymentMethod);
    }

    public boolean addPaymentMethod(PaymentMethod paymentMethod) {
        return this.paymentMethods.add(paymentMethod);
    }

    public Product existsProduct(Product product) {
        return this.products.stream()
                            .filter(p -> p.equals(product))
                            .findFirst()
                            .orElseThrow(() -> new ProductNotFoundException(
                                                    String.format("Unable to find product with id %d in restaurant with id %d", product.getId(), this.id)));
    }

    public void close() {
        this.open = false;
    }

    public void open() {
        this.open = true;
    }
}
