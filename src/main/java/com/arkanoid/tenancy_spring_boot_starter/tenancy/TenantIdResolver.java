package com.arkanoid.tenancy_spring_boot_starter.tenancy;

import com.arkanoid.tenancy_spring_boot_starter.models.TenantContext;
import com.arkanoid.tenancy_spring_boot_starter.models.TenantResolutionResult;
import com.arkanoid.tenancy_spring_boot_starter.models.TenantResolutionSource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class TenantIdResolver implements Supplier<String> {

    /*
    Returns the tenantId associated to the current request
    Hibernate uses this value for tenant_id = :tenantId
     */

    @Override
    public String get() {
        String tenantId = TenantContext.getCurrentTenant();
        if(tenantId==null || tenantId.isBlank()){
            throw new IllegalArgumentException("No tenant is available for the current request!");
        }
        return tenantId;
    }
}
