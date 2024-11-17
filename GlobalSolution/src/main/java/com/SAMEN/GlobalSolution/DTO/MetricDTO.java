package com.SAMEN.GlobalSolution.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
public class MetricDTO extends RepresentationModel<MetricDTO> {

    private Long id;
    private Double electricityProvided;
    private Double nuclearParticipation;
    private Double operationalEfficiency;
    private Long nuclearPlantId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime metricDate;
}
