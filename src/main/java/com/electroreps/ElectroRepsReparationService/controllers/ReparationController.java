package com.electroreps.ElectroRepsReparationService.controllers;

import com.electroreps.ElectroRepsReparationService.dtos.ReparationDataDTO;
import com.electroreps.ElectroRepsReparationService.models.Reparation;
import com.electroreps.ElectroRepsReparationService.services.ReparationService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/reparations")
public class ReparationController {
    @Autowired
    private ReparationService reparationService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getReparationById(@PathVariable Long id) {
        return reparationService.getReparationById(id);
    }

    @GetMapping
    public ResponseEntity<?> getReparationWithParams(
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) Boolean finished) {
        return reparationService.getReparationWithParams(clientId, employeeId, finished);
    }

    @PostMapping
    public ResponseEntity<?> postReparation(@Valid Reparation reparation) {
        return reparationService.postReparation(reparation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putReparation( @PathVariable Long id, @Valid Reparation reparation) {
        return reparationService.updateReparation(id, reparation);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchReparation(@PathVariable Long id, ReparationDataDTO reparation) {
        return reparationService.updateReparationData(id, reparation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReparation(@PathVariable Long id) {
        return reparationService.deleteReparation(id);
    }



}
