package com.competition.project.excention.event;

import lombok.Getter;

@Getter
public class NotFoundResponse {
    private String message;
    public NotFoundResponse( Long id, String name){
        this.message = name + " with id " + id + " not found.";
    }
}
