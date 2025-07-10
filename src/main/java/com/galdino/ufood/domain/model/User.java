package com.galdino.ufood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class User {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime registerDate;

    @ManyToMany
    @JoinTable(name = "user_ugroup", joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "ugroup_id"))
    private Set<UGroup> uGroups = new HashSet<>();

    public boolean removeUGroup(UGroup uGroup) {
        return this.uGroups.remove(uGroup);
    }

    public boolean addUGroup(UGroup uGroup) {
        return this.uGroups.add(uGroup);
    }

    public boolean isNew() {
        return getId() == null;
    }
}
