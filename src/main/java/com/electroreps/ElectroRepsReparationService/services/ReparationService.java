package com.electroreps.ElectroRepsReparationService.services;

import com.electroreps.ElectroRepsReparationService.clients.ClientFeignClient;
import com.electroreps.ElectroRepsReparationService.clients.EmployeeFeignClient;
import com.electroreps.ElectroRepsReparationService.dtos.ClientDTO;
import com.electroreps.ElectroRepsReparationService.dtos.EmployeeDTO;
import com.electroreps.ElectroRepsReparationService.dtos.ReparationDataDTO;
import com.electroreps.ElectroRepsReparationService.models.Reparation;
import com.electroreps.ElectroRepsReparationService.repositories.ReparationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReparationService {

    @Autowired
    private ReparationRepository reparationRepository;

    @Autowired
    ClientFeignClient clientFeignClient;

    @Autowired
    EmployeeFeignClient employeeFeignClient;

    public ResponseEntity<?> getReparationById(Long id) {

        Optional<Reparation> reparation = reparationRepository.findById(id);
        if (reparation.isPresent()) {
            return ResponseEntity.ok(reparation.get());
        } else {
            return ResponseEntity.status(404).body("No reparation found with id " + id);
        }
    }

    public ResponseEntity<?> getReparationWithParams(Long clientId, Long employeeId,Boolean finished) {

        if (clientId == null && employeeId == null && finished == null) {
            return ResponseEntity.ok(reparationRepository.findAll());
        } else if (clientId != null && employeeId == null && finished == null) {
            return ResponseEntity.ok(reparationRepository.findByClientId(clientId));
        } else if (clientId == null && employeeId != null && finished == null) {
            return ResponseEntity.ok(reparationRepository.findByEmployeeId(employeeId));
        } else if (clientId != null && employeeId != null && finished == null) {
            return ResponseEntity.ok(reparationRepository.findByClientIdAndEmployeeId(clientId, employeeId));
        } else if (clientId != null && employeeId == null && finished != null) {
            return ResponseEntity.ok(reparationRepository.findByClientIdAndFinished(clientId, finished));
        } else if (clientId == null && employeeId != null && finished != null) {
            return ResponseEntity.ok(reparationRepository.findByEmployeeAndFinished(employeeId, finished));
        }else{
            return ResponseEntity.ok(reparationRepository.findByClientIdAndEmployeeIdAndFinished(clientId, employeeId, finished));
        }

    }

    public ResponseEntity<?> postReparation(Reparation reparation) {

        ClientDTO client = clientFeignClient.getClientById(reparation.getClientId());
        if (client.getName() == null || client.getEmail() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No client found with id " + reparation.getClientId());
        }

        EmployeeDTO employee = employeeFeignClient.getEmployeeById(reparation.getEmployeeId());
        if (employee.getName() == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee found with id " + reparation.getClientId());
        }

        Reparation savedReparation = reparationRepository.save(reparation);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(savedReparation);
    }

    public ResponseEntity<?> updateReparation(Long id, Reparation reparation) {

        Optional<Reparation> existingReparation = reparationRepository.findById(id);
        if (existingReparation.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reparation found with id " + id);
        }

        ClientDTO client = clientFeignClient.getClientById(reparation.getClientId());
        if (client.getName() == null || client.getEmail() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No client found with id " + reparation.getClientId());
        }

        EmployeeDTO employee = employeeFeignClient.getEmployeeById(reparation.getEmployeeId());
        if (employee.getName() == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee found with id " + reparation.getClientId());
        }

        reparation.setId(existingReparation.get().getId());
        Reparation savedReparation = reparationRepository.save(reparation);
        return ResponseEntity.ok(reparation);
    }

    public ResponseEntity<?> updateReparationData(Long id, ReparationDataDTO reparationData) {


        Optional<Reparation> existingReparation = reparationRepository.findById(id);
        if (existingReparation.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reparation found with id " + id);
        }

        EmployeeDTO employee = employeeFeignClient.getEmployeeById(reparationData.getEmployeeId());
        if (employee.getName() == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee found with id " + reparationData.getEmployeeId());
        }

        Reparation reparation = existingReparation.get();
        if (reparationData.getIssueDescription() != null && !reparationData.getIssueDescription().isEmpty()) {
            reparation.setIssueDescription(reparationData.getIssueDescription());
        }
        reparation.setFinished(reparationData.isFinished());
        reparation.setEmployeeId(reparationData.getEmployeeId());

        Reparation savedReparation = reparationRepository.save(reparation);
        return ResponseEntity.ok(savedReparation);


    }

    public ResponseEntity<?> deleteReparation(Long id) {

        Optional<Reparation> existingReparation = reparationRepository.findById(id);
        if (existingReparation.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reparation found with id " + id);
        }
        reparationRepository.delete(existingReparation.get());
        return ResponseEntity.noContent().build();
    }
}
