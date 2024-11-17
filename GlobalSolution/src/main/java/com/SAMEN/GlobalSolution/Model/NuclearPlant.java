package com.SAMEN.GlobalSolution.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class NuclearPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 50)
    private String plantName;

    @NotNull
    @Column(nullable = false)
    private Integer fullCapacity;

    @NotNull
    @Column(nullable = false)
    private Integer numberOfReactors;

    @OneToMany(mappedBy = "nuclearPlant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Metric> metrics = new ArrayList<>();
}
