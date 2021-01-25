package com.competition.project.service;

import com.competition.project.dto.AgeGroupDTO;
import javassist.NotFoundException;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

public interface AgeGroupService {
    AgeGroupDTO create(AgeGroupDTO dto, Long event_id ) throws InstanceAlreadyExistsException, NotFoundException;

    AgeGroupDTO update( AgeGroupDTO dto ) throws NotFoundException;

    AgeGroupDTO getById( Long id ) throws NotFoundException;

    AgeGroupDTO deleteById( Long id ) throws NotFoundException;

    void moveToTrash( Long id ) throws NotFoundException, InstanceAlreadyExistsException;

    List<AgeGroupDTO> getAll();

    List<AgeGroupDTO> getAllInTrash();

    void restore( Long id ) throws NotFoundException, InstanceAlreadyExistsException;
}
