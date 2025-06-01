package com.electroreps.ElectroRepsReparationService.repositories;

import com.electroreps.ElectroRepsReparationService.models.Reparation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReparationRepository extends JpaRepository<Reparation, Long> {

    List<Reparation> findByClientId(Long clientId);
    List<Reparation> findByEmployeeId(Long clientId);
    List<Reparation> findByFinished(boolean finished);
    List<Reparation> findByClientIdAndEmployeeId(Long clientId, Long employeeId);
    List<Reparation> findByClientIdAndFinished(Long clientId, boolean finished);
    List<Reparation> findByEmployeeIdAndFinished(Long employeeId, boolean finished);
    List<Reparation> findByClientIdAndEmployeeIdAndFinished(Long clientId, Long employeeId, boolean finished);
}
