package com.studentmanagement.system.dto;

import com.studentmanagement.system.security.RoleType;
import java.util.List;
import lombok.Data;

@Data

public class AssignRoleDTO {
    private Long userId;
    private List<RoleType> roles; 

}
