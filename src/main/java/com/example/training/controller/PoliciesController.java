package com.example.training.controller;

import com.example.training.model.dto.AccidentDto;
import com.example.training.model.dto.PolicyConditionsDto;
import com.example.training.model.dto.PolicyWrapperDto;
import com.example.training.service.AccidentsService;
import com.example.training.service.PoliciesService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policies")
public class PoliciesController {

    private final PoliciesService policyService;
    private final AccidentsService accidentsService;


    public PoliciesController(PoliciesService policiesService, AccidentsService accidentsService) {
        this.policyService = policiesService;
        this.accidentsService = accidentsService;
    }

    @GetMapping
    public PolicyWrapperDto getPolicies() {
        String userId = getUserIdFromContext();
        return policyService.getPolicies(userId);
    }

    @GetMapping("/{policyNumber}")
    public PolicyWrapperDto getPolicyById(@PathVariable String policyNumber) {
        return policyService.getPolicyById(policyNumber);
    }

    @GetMapping("/{policyNumber}/conditions")
    public List<PolicyConditionsDto> getPolicyConditions(@PathVariable String policyNumber) {
        return policyService.getPolicyConditions(policyNumber);
    }

    @GetMapping("/{policyNumber}/accidents")
    public List<AccidentDto> getAccidentsByPolicy(@PathVariable String policyNumber) {
        return accidentsService.getAccidentsByPolicy(policyNumber);
    }

    @GetMapping("/{policyNumber}/accidents/{accidentId}")
    public AccidentDto getAccidentById(@PathVariable String policyNumber, @PathVariable String accidentId) {
        return accidentsService.getAccidentById(accidentId);
    }

    private String getUserIdFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
