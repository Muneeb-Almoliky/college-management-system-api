package com.studentmanagement.system.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TeacherDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;

}
