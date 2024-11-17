package com.SAMEN.GlobalSolution.Controller;

import com.SAMEN.GlobalSolution.DTO.MetricDTO;
import com.SAMEN.GlobalSolution.Model.Metric;
import com.SAMEN.GlobalSolution.Service.MetricService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/metrics")
public class MetricController {

    @Autowired
    private MetricService service;

    @Operation(summary = "Retrieve all metrics with pagination",
            description = "This endpoint allows you to retrieve all metrics with pagination support.")
    @GetMapping
    public CollectionModel<EntityModel<MetricDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Metric> metrics = service.findAll(pageable);

        List<EntityModel<MetricDTO>> metricModels = metrics.stream()
                .map(this::convertToEntityModel)
                .collect(Collectors.toList());
        return CollectionModel.of(metricModels,
                linkTo(methodOn(MetricController.class).getAll(page, size)).withSelfRel(),
                linkTo(methodOn(MetricController.class).getMetricsByPlantId(null, page, size)).withRel("metrics-by-plant"));
    }

    @Operation(summary = "Get a specific metric by its ID",
            description = "Retrieve a metric based on its unique ID.")
    @ApiResponse(responseCode = "404", description = "Metric not found")
    @GetMapping("/{id}")
    public EntityModel<MetricDTO> getMetricById(@PathVariable Long id) {
        Metric metric = service.findById(id);
        return convertToEntityModel(metric);
    }

    @Operation(summary = "Retrieve metrics by plant ID with pagination",
            description = "Retrieve all metrics associated with a specific nuclear plant ID, with pagination support.")
    @GetMapping("/plant/{plantId}")
    public CollectionModel<EntityModel<MetricDTO>> getMetricsByPlantId(
            @PathVariable Long plantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Metric> metrics = service.findByPlantId(plantId, pageable);

        List<EntityModel<MetricDTO>> metricModels = metrics.stream()
                .map(this::convertToEntityModel)
                .collect(Collectors.toList());

        return CollectionModel.of(metricModels,
                linkTo(methodOn(MetricController.class).getMetricsByPlantId(plantId, page, size)).withSelfRel());
    }

    @Operation(summary = "Create a new metric",
            description = "Create a new metric based on the provided MetricDTO.")
    @PostMapping
    public EntityModel<MetricDTO> create(@RequestBody MetricDTO dto) {
        Metric savedMetric = service.save(dto);
        return convertToEntityModel(savedMetric);
    }

    @Operation(summary = "Update an existing metric",
            description = "Update an existing metric identified by ID.")
    @PutMapping("/{id}")
    public EntityModel<MetricDTO> update(@PathVariable Long id, @RequestBody MetricDTO dto) {
        Metric updatedMetric = service.update(id, dto);
        return convertToEntityModel(updatedMetric);
    }

    @Operation(summary = "Delete a metric by ID",
            description = "Delete the metric identified by its unique ID.")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private EntityModel<MetricDTO> convertToEntityModel(Metric metric) {
        MetricDTO dto = new MetricDTO();
        dto.setId(metric.getId());
        dto.setElectricityProvided(metric.getElectricityProvided());
        dto.setNuclearParticipation(metric.getNuclearParticipation());
        dto.setOperationalEfficiency(metric.getOperationalEfficiency());
        dto.setMetricDate(metric.getMetricDate());
        if (metric.getNuclearPlant() != null) {
            dto.setNuclearPlantId(metric.getNuclearPlant().getId());
        }

        EntityModel<MetricDTO> entityModel = EntityModel.of(dto);
        entityModel.add(linkTo(methodOn(MetricController.class).getMetricById(dto.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(MetricController.class).getAll(0, 10)).withRel("metrics"));
        if (dto.getNuclearPlantId() != null) {
            entityModel.add(linkTo(methodOn(MetricController.class).getMetricsByPlantId(dto.getNuclearPlantId(), 0, 10)).withRel("metrics-by-plant"));
        }

        return entityModel;
    }
}
