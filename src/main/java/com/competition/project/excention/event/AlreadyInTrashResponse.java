package com.competition.project.excention.event;

import lombok.Data;

@Data
public class AlreadyInTrashResponse {
    private String message;
    public AlreadyInTrashResponse(Long id, String name  ){
        this.message = name + " with id: " + id + " already in trash.";
    }
}
