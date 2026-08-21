package com.arkanoid.tenancy_spring_boot_starter.controller;

import com.arkanoid.tenancy_spring_boot_starter.entity.User;
import com.arkanoid.tenancy_spring_boot_starter.models.TenantContext;
import com.arkanoid.tenancy_spring_boot_starter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestTenantController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> tenantResult(){
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User tenantResult(@RequestBody String name){
        User user = User.builder().name(name).build();
        return userRepository.save(user);
    }

    @PatchMapping("/users/{id}")
    public User tenantResult(@PathVariable Long id,@RequestBody String name){
        User user = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid user id!"));
        user.setName(name);
        return userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id){
        User user = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid user id!"));
        userRepository.delete(user);
        return "Success";
    }

}
