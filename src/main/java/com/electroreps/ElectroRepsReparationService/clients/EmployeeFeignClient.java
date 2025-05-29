package com.electroreps.ElectroRepsReparationService.clients;

import com.electroreps.ElectroRepsReparationService.dtos.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "electroreps-employee-service")
public interface EmployeeFeignClient {
    // Define methods to interact with the Employee service
    // For example:
    @GetMapping("/employees/{id}")
    EmployeeDTO getEmployeeById(@PathVariable("id") Long id);
}
