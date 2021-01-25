package com.competition.project.dto;

import lombok.Data;

@Data
public class AgeGroupDTO {
    private Long id;
    private String name;
    private Boolean deleted = false;
    private Boolean inTrash = false;
}
