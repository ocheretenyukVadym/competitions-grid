package com.competition.project.excention.event;

import lombok.Getter;

@Getter
public class NotInTrashResponce {
    private String message;
    public NotInTrashResponce( Long id, String name ) {
        this.message = name + " with id " + id + " not in trash.";
    }
}
