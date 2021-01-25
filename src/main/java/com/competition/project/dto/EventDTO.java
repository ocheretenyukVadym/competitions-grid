package com.competition.project.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EventDTO {
    private Long id;
    private String name;
    private List<AgeGroupDTO> ageGroups = new ArrayList<>();
    private Boolean deleted = false;
    private Boolean inTrash = false;
}
