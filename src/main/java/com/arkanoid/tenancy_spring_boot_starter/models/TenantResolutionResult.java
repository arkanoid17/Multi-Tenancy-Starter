package com.arkanoid.tenancy_spring_boot_starter.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantResolutionResult {
    private String tenantId;
    private TenantResolutionSource resolutionSource;
}
