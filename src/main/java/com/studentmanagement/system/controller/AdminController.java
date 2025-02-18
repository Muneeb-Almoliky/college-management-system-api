package com.studentmanagement.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagement.system.service.UserService;
import com.studentmanagement.system.dto.AssignRoleDTO;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/assign-roles")
    public ResponseEntity<String> assignRoles(@RequestBody AssignRoleDTO request) {
        userService.assignRoles(request.getUserId(), request.getRoles());
        return ResponseEntity.ok("Roles assigned successfully");
    }
}

