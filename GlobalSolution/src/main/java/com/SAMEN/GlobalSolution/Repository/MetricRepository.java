package com.SAMEN.GlobalSolution.Repository;

import com.SAMEN.GlobalSolution.Model.Metric;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {

    List<Metric> findByNuclearPlantId(Long nuclearPlantId);

    Page<Metric> findAll(Pageable pageable);

    Page<Metric> findByNuclearPlantId(Long plantId, Pageable pageable);

}
