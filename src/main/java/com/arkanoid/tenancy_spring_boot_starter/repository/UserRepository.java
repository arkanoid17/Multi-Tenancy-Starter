package com.arkanoid.tenancy_spring_boot_starter.repository;

import com.arkanoid.tenancy_spring_boot_starter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
