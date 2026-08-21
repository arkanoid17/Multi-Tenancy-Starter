package com.arkanoid.tenancy_spring_boot_starter.tenancy;

import com.arkanoid.tenancy_spring_boot_starter.models.TenantResolutionResult;
import com.arkanoid.tenancy_spring_boot_starter.models.TenantResolutionSource;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class TenantResolver {

    /*
    * For any request extract the tenant-id from the header and returns
    * resolved tenant details in TenantResolutionResult model
    * */

    public TenantResolutionResult resolve(HttpServletRequest request){
        String tenantId = request.getHeader("X-tenant-Id");

        if(tenantId==null || tenantId.isEmpty()){
            throw new IllegalArgumentException("Tenant ID is missing!");
        }

        return TenantResolutionResult
                .builder()
                .tenantId(tenantId)
                .resolutionSource(TenantResolutionSource.HEADER)
                .build();
    }
}
