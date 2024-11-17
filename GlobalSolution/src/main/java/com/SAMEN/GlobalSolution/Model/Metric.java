package com.SAMEN.GlobalSolution.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime metricDate;

    @NotNull
    @Column(nullable = false)
    private Double electricityProvided;

    @NotNull
    @Column(nullable = false)
    private Double nuclearParticipation;

    @NotNull
    @Column(nullable = false)
    private Double operationalEfficiency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nuclearplant", nullable = false)
    private NuclearPlant nuclearPlant;

    


}
