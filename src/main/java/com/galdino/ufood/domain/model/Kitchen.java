package com.galdino.ufood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@JsonRootName("cuisine")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Kitchen {
    @NotNull
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore
//    @JsonProperty("title")
    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "kitchen")
    private List<Restaurant> restaurants = new ArrayList<>();

}
