package com.SAMEN.GlobalSolution.Service;

import com.SAMEN.GlobalSolution.Controller.NuclearPlantController;
import com.SAMEN.GlobalSolution.DTO.NuclearPlantDTO;
import com.SAMEN.GlobalSolution.Exception.ResourceNotFoundException;
import com.SAMEN.GlobalSolution.Model.NuclearPlant;
import com.SAMEN.GlobalSolution.Repository.NuclearPlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class NuclearPlantService {

    @Autowired
    private NuclearPlantRepository repository;

    public Page<NuclearPlantDTO> getAllNuclearPlants(Pageable pageable) {
        Page<NuclearPlant> plantsPage = repository.findAll(pageable);

        Page<NuclearPlantDTO> plantDTOs = plantsPage.map(plant -> new NuclearPlantDTO(
                plant.getId(),
                plant.getPlantName(),
                plant.getFullCapacity(),
                plant.getNumberOfReactors()
        ));

        return plantDTOs;
    }
    public CollectionModel<EntityModel<NuclearPlantDTO>> getAllNuclearPlants(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<NuclearPlant> plantsPage = repository.findAll(pageable);

        var plantDTOs = plantsPage.stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<NuclearPlantDTO>> collectionModel = CollectionModel.of(plantDTOs);

        collectionModel.add(linkTo(methodOn(NuclearPlantController.class).getAll(page, size)).withSelfRel());

        if (plantsPage.hasNext()) {
            collectionModel.add(linkTo(methodOn(NuclearPlantController.class).getAll(page + 1, size)).withRel("next"));
        }
        if (plantsPage.hasPrevious()) {
            collectionModel.add(linkTo(methodOn(NuclearPlantController.class).getAll(page - 1, size)).withRel("prev"));
        }

        return collectionModel;
    }

    public EntityModel<NuclearPlantDTO> getNuclearPlantById(Long id) {
        NuclearPlant plant = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usina não encontrada com ID: " + id));

        return toModel(plant);
    }

    public EntityModel<NuclearPlantDTO> createNuclearPlant(NuclearPlantDTO nuclearPlantDTO) {
        NuclearPlant plant = new NuclearPlant();
        plant.setPlantName(nuclearPlantDTO.getPlantName());
        plant.setFullCapacity(nuclearPlantDTO.getFullCapacity());
        plant.setNumberOfReactors(nuclearPlantDTO.getNumberOfReactors());

        NuclearPlant savedPlant = repository.save(plant);
        return toModel(savedPlant);
    }

    public EntityModel<NuclearPlantDTO> updateNuclearPlant(Long id, NuclearPlantDTO nuclearPlantDTO) {
        NuclearPlant plant = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usina não encontrada com ID: " + id));

        plant.setPlantName(nuclearPlantDTO.getPlantName());
        plant.setFullCapacity(nuclearPlantDTO.getFullCapacity());
        plant.setNumberOfReactors(nuclearPlantDTO.getNumberOfReactors());

        NuclearPlant updatedPlant = repository.save(plant);
        return toModel(updatedPlant);
    }

    public void deleteNuclearPlant(Long id) {
        // Verifica se a usina existe antes de excluir
        NuclearPlant plant = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usina não encontrada com ID: " + id));

        // Exclui a usina do banco de dados
        repository.delete(plant);
    }

    private EntityModel<NuclearPlantDTO> toModel(NuclearPlant plant) {
        // Cria o DTO da usina
        NuclearPlantDTO dto = new NuclearPlantDTO(
                plant.getId(),
                plant.getPlantName(),
                plant.getFullCapacity(),
                plant.getNumberOfReactors()
        );

        // Cria o modelo HATEOAS com o link para o próprio recurso e a lista de todas as usinas
        return EntityModel.of(dto,
                linkTo(methodOn(NuclearPlantController.class).getById(plant.getId())).withSelfRel(),
                linkTo(methodOn(NuclearPlantController.class).getAll(0, 10)).withRel("allPlants"));
    }



}
