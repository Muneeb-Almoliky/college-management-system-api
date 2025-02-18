package com.studentmanagement.system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;;

@Setter
@Getter
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;
    
}
