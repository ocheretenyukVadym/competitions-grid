package com.competition.project.excention.event;

import lombok.Getter;

@Getter
public class AlreadyExistResponse {
    private String message;
    public AlreadyExistResponse(Long id, String name ){
        this.message = name + " with id: " + id + " already exist.";
    }
}
