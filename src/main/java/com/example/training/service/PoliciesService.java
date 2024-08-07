package com.example.training.service;

import com.example.training.model.dto.PolicyConditionsDto;
import com.example.training.model.dto.PolicyDto;
import com.example.training.model.dto.PolicyWrapperDto;
import com.example.training.model.entity.Policy;
import com.example.training.proxy.PoliciesProxyService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PoliciesService {

    private final PoliciesProxyService policiesProxyService;

    public PoliciesService(PoliciesProxyService policiesProxyService) {
        this.policiesProxyService = policiesProxyService;
    }

    public PolicyWrapperDto getPolicies(String dni) {

        var policies = policiesProxyService.getPolicies(dni);
        List<PolicyDto> policyDtos = mapToPolicyDto(policies);

        return PolicyWrapperDto.builder()
                .policies(policyDtos)
                .build();
    }

    public PolicyWrapperDto getPolicyById(String policyId) {

        var policy = policiesProxyService.getPolicyById(policyId);
        if (policy != null) {
            PolicyDto policyDto = new PolicyDto(policy.getPolicyId(), policy.getDescription(), policy.getCoverages());
            return PolicyWrapperDto.builder()
                    .policies(List.of(policyDto))
                    .build();
        }
        return PolicyWrapperDto.builder()
                .policies(List.of())
                .build();
    }

    public List<PolicyConditionsDto> getPolicyConditions(String policyId) {
        var conditions = policiesProxyService.getPolicyConditions(policyId);

        if (conditions != null) {
            PolicyConditionsDto policyConditionsDto = new PolicyConditionsDto(List.of(conditions));
            return List.of(policyConditionsDto);
        }
        return List.of();
    }

    private List<PolicyDto> mapToPolicyDto(Policy[] policies) {
        return policies != null ?
                List.of(policies).stream()
                        .map(policy -> new PolicyDto(policy.getPolicyId(), policy.getDescription()))
                        .collect(Collectors.toList()) :
                List.of();
    }


}
