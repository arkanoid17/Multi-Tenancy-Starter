package com.arkanoid.tenancy_spring_boot_starter.entity;

import com.arkanoid.tenancy_spring_boot_starter.models.TenantContext;
import com.arkanoid.tenancy_spring_boot_starter.tenancy.TenantIdResolver;
import com.arkanoid.tenancy_spring_boot_starter.tenancy.TenantResolver;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

/*
* @FilterDef(...) defines a Hibernate filter called tenantFilter
* that tells Hibernate that it accepts tenantId
* Then @Filter(...) tells hibernate whenever this filter is enabled add
* tenant_id = :tenantId to queries for this entity
* */

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FilterDef(
        name = "tenantFilter",
        applyToLoadByKey = true,
        autoEnabled = true,
        parameters = @ParamDef(
                name = "tenantId",
                type = String.class,
                resolver = TenantIdResolver.class
        )
)
@Filter(
        name = "tenantFilter",
        condition = "tenant_id = :tenantId"
)
public abstract class TenantAwareEntity {

    @Column(name = "tenant_id",nullable = false)
    private String tenantId;


    /*
    * Assigns tenant to any newly created entities
    * */

    @PrePersist
    protected void assignTenant(){
        String currentTenant = getRequiredTenant();
        this.tenantId = currentTenant;
    }

    @PreDestroy
    @PreUpdate
    protected void validateTenant(){
        String currentTenant = getRequiredTenant();

        if(!currentTenant.equals(this.tenantId)){
            throw new IllegalStateException("Tenant ID mismatch!");
        }
    }




    private String getRequiredTenant(){
        String currentTenant = TenantContext.getCurrentTenant();

        if(currentTenant==null || currentTenant.isBlank()){
            throw new IllegalArgumentException("No tenant is available for the current request!");
        }
        return currentTenant;
    }
}


