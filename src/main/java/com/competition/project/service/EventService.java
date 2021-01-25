package com.competition.project.service;

import com.competition.project.dto.EventDTO;
import javassist.NotFoundException;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

public interface EventService {
    EventDTO create(EventDTO dto ) throws InstanceAlreadyExistsException;

    EventDTO update( EventDTO dto ) throws NotFoundException;

    EventDTO getById( Long id ) throws NotFoundException;

    EventDTO deleteById( Long id ) throws NotFoundException;

    void moveToTrash( Long id ) throws NotFoundException, InstanceAlreadyExistsException;

    List<EventDTO> getAll();

    List<EventDTO> getAllInTrash();

    void restore( Long id ) throws NotFoundException, InstanceAlreadyExistsException;

    EventDTO getByAgeGroupId( Long id ) throws NotFoundException, InstanceAlreadyExistsException;
}
