package com.SAMEN.GlobalSolution.Service;

import com.SAMEN.GlobalSolution.DTO.MetricDTO;
import com.SAMEN.GlobalSolution.Exception.ResourceNotFoundException;
import com.SAMEN.GlobalSolution.Model.Metric;
import com.SAMEN.GlobalSolution.Model.NuclearPlant;
import com.SAMEN.GlobalSolution.Repository.MetricRepository;
import com.SAMEN.GlobalSolution.Repository.NuclearPlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MetricService {

    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    private NuclearPlantRepository nuclearPlantRepository;

    public Page<Metric> findAll(Pageable pageable) {
        return metricRepository.findAll(pageable);
    }

    public Page<Metric> findByPlantId(Long plantId, Pageable pageable) {
        return metricRepository.findByNuclearPlantId(plantId, pageable);
    }

    public List<Metric> findAll() {
        return metricRepository.findAll();
    }

    public Metric findById(Long id) {
        return metricRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Métrica não encontrada com ID: " + id));
    }

    public List<Metric> findByPlantId(Long plantId) {
        return metricRepository.findByNuclearPlantId(plantId);
    }


    public Metric save(MetricDTO dto) {
        NuclearPlant plant = nuclearPlantRepository.findById(dto.getNuclearPlantId())
                .orElseThrow(() -> new ResourceNotFoundException("Usina não encontrada com ID: " + dto.getNuclearPlantId()));

        Metric metric = new Metric();
        metric.setMetricDate(dto.getMetricDate());
        metric.setElectricityProvided(dto.getElectricityProvided());
        metric.setNuclearParticipation(dto.getNuclearParticipation());
        metric.setOperationalEfficiency(dto.getOperationalEfficiency());
        metric.setNuclearPlant(plant);

        return metricRepository.save(metric);
    }

    public Metric update(Long id, MetricDTO dto) {
        Metric metric = metricRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Métrica não encontrada com ID: " + id));

        metric.setElectricityProvided(dto.getElectricityProvided());
        metric.setNuclearParticipation(dto.getNuclearParticipation());
        metric.setOperationalEfficiency(dto.getOperationalEfficiency());

        if (dto.getNuclearPlantId() != null) {
            NuclearPlant plant = nuclearPlantRepository.findById(dto.getNuclearPlantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usina não encontrada com ID: " + dto.getNuclearPlantId()));
            metric.setNuclearPlant(plant);
        }

        return metricRepository.save(metric);
    }

    public void delete(Long id) {
        Metric metric = metricRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Métrica não encontrada com ID: " + id));

        metricRepository.delete(metric);
    }
}
