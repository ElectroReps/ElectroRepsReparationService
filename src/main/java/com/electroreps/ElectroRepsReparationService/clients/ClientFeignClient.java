package com.electroreps.ElectroRepsReparationService.clients;

import com.electroreps.ElectroRepsReparationService.dtos.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "electroreps-client-service")
public interface ClientFeignClient {
    // Define methods to interact with the Client service
    // For example:
    @GetMapping("/clients/{id}")
    ClientDTO getClientById(@PathVariable("id") Long id);
}
