package com.SAMEN.GlobalSolution.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
public class NuclearPlantDTO extends RepresentationModel<NuclearPlantDTO> {

    private Long id;
    private String plantName;
    private Integer fullCapacity;
    private Integer numberOfReactors;

}
