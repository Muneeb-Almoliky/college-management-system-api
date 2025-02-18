package com.studentmanagement.system.repository;

import com.studentmanagement.system.entity.Role;
import com.studentmanagement.system.security.RoleType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


// public interface RoleRepository extends JpaRepository<Role, Long> {
//     Optional<Role> findByName(String name);
// }



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    List<Role> findByNameIn(List<RoleType> names);

}
