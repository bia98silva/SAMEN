package com.SAMEN.GlobalSolution.Repository;

import com.SAMEN.GlobalSolution.Model.NuclearPlant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface NuclearPlantRepository extends JpaRepository<NuclearPlant, Long> {

    Optional<NuclearPlant> findByPlantName(String plantName);
    Page<NuclearPlant> findByPlantNameContaining(String name, Pageable pageable);
}
