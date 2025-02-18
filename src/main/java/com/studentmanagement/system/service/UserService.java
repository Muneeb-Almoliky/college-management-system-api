package com.studentmanagement.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagement.system.entity.Role;
import com.studentmanagement.system.entity.UserEntity;
import com.studentmanagement.system.exception.ResourceNotFoundException;
import com.studentmanagement.system.repository.RoleRepository;
import com.studentmanagement.system.repository.UserRepository;
import com.studentmanagement.system.security.RoleType;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void assignRoles(Long userId, List<RoleType> roles) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Role> roleEntities = roleRepository.findByNameIn(roles);
        if (roleEntities.isEmpty()) {
            throw new ResourceNotFoundException("Roles not found");
        }

        user.setRoles(roleEntities);
        userRepository.save(user);
    }
}

