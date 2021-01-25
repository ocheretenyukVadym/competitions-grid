package com.competition.project.service.impl;

import com.competition.project.dto.AgeGroupDTO;
import com.competition.project.entity.AgeGroupEntity;
import com.competition.project.entity.EventEntity;
import com.competition.project.excention.event.AlreadyExistResponse;
import com.competition.project.excention.event.AlreadyInTrashResponse;
import com.competition.project.excention.event.NotFoundResponse;
import com.competition.project.excention.event.NotInTrashResponce;
import com.competition.project.repository.AgeGroupRepository;
import com.competition.project.repository.EventRepository;
import com.competition.project.service.AgeGroupService;
import com.competition.project.utils.ModelMapperUtil;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

@Service
public class AgeGroupServiceImpl implements AgeGroupService {

    @Autowired
    private AgeGroupRepository repository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapperUtil modelMapper;

    @Override
    public AgeGroupDTO create(AgeGroupDTO dto, Long event_id) throws InstanceAlreadyExistsException, NotFoundException {
        if ( dto.getId() != null && repository.existsById( dto.getId() ) )
            throw new InstanceAlreadyExistsException( new AlreadyExistResponse( dto.getId(), "AgeGroup" ).getMessage() );
        AgeGroupEntity entity = modelMapper.map( dto, AgeGroupEntity.class );
        repository.save( entity );
        dto.setId( entity.getId() );
        assignToEventById(dto.getId(), event_id);
        return dto;
    }

    @Override
    public AgeGroupDTO update(AgeGroupDTO dto) throws NotFoundException {
        if ( dto.getId() == null || !repository.existsById( dto.getId() ) )
            throw new NotFoundException( new NotFoundResponse( dto.getId(), "AgeGroup" ).getMessage() );
        AgeGroupEntity entity = modelMapper.map( dto, AgeGroupEntity.class );
        if ( entity.getDeleted() || entity.getInTrash() ) {
            throw new NotFoundException( new NotFoundResponse( entity.getId(), "AgeGroup" ).getMessage() );
        }
        repository.save( entity );
        return modelMapper.map( entity, AgeGroupDTO.class );
    }

    @Override
    public AgeGroupDTO getById(Long id) throws NotFoundException {
        if ( !repository.existsById( id ) )
            throw new NotFoundException( new NotFoundResponse( id, "AgeGroup" ).getMessage() );
        AgeGroupEntity entity = repository.findById( id ).get();
        if ( entity.getDeleted() || entity.getInTrash() )
            throw new NotFoundException( new NotFoundResponse( id, "AgeGroup" ).getMessage() );
        return modelMapper.map( entity, AgeGroupDTO.class );
    }

    @Override
    public AgeGroupDTO deleteById(Long id) throws NotFoundException {
        if ( !repository.existsById( id ) )
            throw new NotFoundException( new NotFoundResponse( id, "AgeGroup" ).getMessage() );
        AgeGroupEntity entity = repository.findById( id ).get();
        if ( entity.getDeleted() )
            throw new NotFoundException( new NotFoundResponse( id, "AgeGroup" ).getMessage() );
        entity.setDeleted( true );
        repository.save( entity );
        return modelMapper.map( entity, AgeGroupDTO.class );
    }

    @Override
    public void moveToTrash( Long id ) throws NotFoundException, InstanceAlreadyExistsException {
        if ( !repository.existsById( id ) )
            throw new NotFoundException( new NotFoundResponse( id, "AgeGroup" ).getMessage() );
        AgeGroupEntity entity = repository.findById( id ).get();
        if ( entity.getInTrash() )
            throw new InstanceAlreadyExistsException( new AlreadyInTrashResponse( id, "AgeGroup" ).getMessage() );
        if ( entity.getDeleted() )
            throw new NotFoundException( new NotFoundResponse( entity.getId(), "AgeGroup" ).getMessage() );
        entity.setInTrash( true );
        repository.save( entity );
    }

    @Override
    public List<AgeGroupDTO> getAll() {
        List<AgeGroupEntity> users = repository.findAllByDeletedIsFalseAndInTrashIsFalse();
        return modelMapper.mapAll( users, AgeGroupDTO.class );
    }

    @Override
    public List<AgeGroupDTO> getAllInTrash() {
        List<AgeGroupEntity> users = repository.findAllByDeletedIsFalseAndInTrashIsTrue();
        return modelMapper.mapAll( users, AgeGroupDTO.class );
    }

    @Override
    public void restore( Long id ) throws NotFoundException, InstanceAlreadyExistsException {
        if ( !repository.existsById( id ) ) throw new NotFoundException( new NotFoundResponse( id, "AgeGroup" ).getMessage() );
        AgeGroupEntity entity = repository.findById( id ).get();
        if ( entity.getDeleted() ) throw new NotFoundException( new NotFoundResponse( id, "AgeGroup" ).getMessage() );
        if ( !entity.getInTrash() )
            throw new InstanceAlreadyExistsException( new NotInTrashResponce( id, "AgeGroup" ).getMessage() );
        entity.setInTrash( false );
        repository.save( entity );
    }

    private void assignToEventById( Long ageGroupId, Long eventId ) throws NotFoundException {
        checkAgeGroupExist( ageGroupId );
        AgeGroupEntity ageGroupEntity = repository.findById( ageGroupId ).get();
        EventEntity eventEntity = eventRepository.findById( eventId ).get();

        if ( eventEntity.getDeleted() || eventEntity.getInTrash() )
            throw new NotFoundException( new NotFoundResponse( eventId, "Event" ).getMessage() );
        if ( ageGroupEntity.getDeleted() || ageGroupEntity.getInTrash() )
            throw new NotFoundException( new NotFoundResponse( ageGroupId, "Age group" ).getMessage() );
        eventEntity.getAgeGroups().add( ageGroupEntity );
        eventRepository.save( eventEntity );
    }

    private void checkAgeGroupExist( Long id ) throws NotFoundException {
        if ( !repository.existsById( id ) )
            throw new NotFoundException( new NotFoundResponse( id, "Age group" ).getMessage() );
    }
}
