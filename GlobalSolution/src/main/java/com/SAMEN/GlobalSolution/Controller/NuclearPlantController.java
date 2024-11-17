package com.SAMEN.GlobalSolution.Controller;

import com.SAMEN.GlobalSolution.DTO.NuclearPlantDTO;
import com.SAMEN.GlobalSolution.Service.NuclearPlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/nuclear-plants")
public class NuclearPlantController {

    @Autowired
    private NuclearPlantService service;

    @Operation(summary = "Lista todas as usinas nucleares", description = "Retorna uma lista paginada de usinas nucleares.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usinas nucleares recuperada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos parâmetros"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public CollectionModel<EntityModel<NuclearPlantDTO>> getAll(
            @Parameter(description = "Número da página para paginação")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Número de itens por página")
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<NuclearPlantDTO> nuclearPlantsPage = service.getAllNuclearPlants(pageable);

        CollectionModel<EntityModel<NuclearPlantDTO>> collectionModel = CollectionModel.of(
                nuclearPlantsPage.getContent().stream()
                        .map(this::toEntityModel)
                        .toList()
        );

        collectionModel.add(linkTo(methodOn(NuclearPlantController.class).getAll(page, size)).withSelfRel());
        if (nuclearPlantsPage.hasNext()) {
            collectionModel.add(linkTo(methodOn(NuclearPlantController.class)
                    .getAll(page + 1, size)).withRel("next"));
        }
        if (nuclearPlantsPage.hasPrevious()) {
            collectionModel.add(linkTo(methodOn(NuclearPlantController.class)
                    .getAll(page - 1, size)).withRel("previous"));
        }

        return collectionModel;
    }

    @Operation(summary = "Obtém uma usina nuclear por ID", description = "Retorna os detalhes de uma usina nuclear específica com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usina nuclear encontrada"),
            @ApiResponse(responseCode = "404", description = "Usina nuclear não encontrada")
    })
    @GetMapping("/{id}")
    public EntityModel<NuclearPlantDTO> getById(
            @Parameter(description = "ID da usina nuclear a ser recuperada")
            @PathVariable Long id) {
        return service.getNuclearPlantById(id);
    }

    @Operation(summary = "Cria uma nova usina nuclear", description = "Cria uma nova usina nuclear e a persiste no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usina nuclear criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados de entrada")
    })
    @PostMapping
    public ResponseEntity<EntityModel<NuclearPlantDTO>> create(@RequestBody NuclearPlantDTO dto) {
        EntityModel<NuclearPlantDTO> entityModel = service.createNuclearPlant(dto);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @Operation(summary = "Atualiza uma usina nuclear existente", description = "Atualiza os dados de uma usina nuclear existente com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usina nuclear atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usina nuclear não encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados de entrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<NuclearPlantDTO>> update(@PathVariable Long id, @RequestBody NuclearPlantDTO dto) {
        EntityModel<NuclearPlantDTO> updatedEntity = service.updateNuclearPlant(id, dto);
        return ResponseEntity.ok(updatedEntity);
    }

    @Operation(summary = "Exclui uma usina nuclear", description = "Exclui uma usina nuclear do sistema com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usina nuclear excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usina nuclear não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteNuclearPlant(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<NuclearPlantDTO> toEntityModel(NuclearPlantDTO dto) {
        EntityModel<NuclearPlantDTO> entityModel = EntityModel.of(dto);
        entityModel.add(linkTo(methodOn(NuclearPlantController.class).getById(dto.getId())).withSelfRel());
        return entityModel;
    }
}
