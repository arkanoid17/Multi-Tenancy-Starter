package com.arkanoid.tenancy_spring_boot_starter.tenancy;

import com.arkanoid.tenancy_spring_boot_starter.models.TenantContext;
import com.arkanoid.tenancy_spring_boot_starter.models.TenantResolutionResult;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {

    private final TenantResolver tenantResolver;

    public TenantFilter(TenantResolver tenantResolver) {
        this.tenantResolver = tenantResolver;
    }

    /*
    * For every request it intercepts the request and resolves the tenant from
    * the request using TenantResolver implementation
    * */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            TenantResolutionResult result = tenantResolver.resolve(request);
            TenantContext.setCurrentTenant(result.getTenantId());
            filterChain.doFilter(request,response);
        }
        finally {
            TenantContext.clear();
        }
    }
}
