package com.noovosoft.business.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "taxation-service", url = "http://localhost:8085")
public interface TaxationClient {

    @PostMapping("/taxations")
    Taxation fileTax(@RequestBody Taxation taxation);

    @GetMapping("/taxations/total/{clientId}")
    double getTotalTaxPaid(@PathVariable UUID clientId);
}
