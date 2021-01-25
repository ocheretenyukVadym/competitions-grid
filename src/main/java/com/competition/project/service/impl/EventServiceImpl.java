package com.competition.project.service.impl;

import com.competition.project.dto.EventDTO;
import com.competition.project.entity.AgeGroupEntity;
import com.competition.project.entity.EventEntity;
import com.competition.project.excention.event.AlreadyExistResponse;
import com.competition.project.excention.event.AlreadyInTrashResponse;
import com.competition.project.excention.event.NotFoundResponse;
import com.competition.project.excention.event.NotInTrashResponce;
import com.competition.project.repository.AgeGroupRepository;
import com.competition.project.repository.EventRepository;
import com.competition.project.service.EventService;
import com.competition.project.utils.ModelMapperUtil;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private AgeGroupRepository ageGroupRepository;

    @Autowired
    private ModelMapperUtil modelMapper;

    @Override
    public EventDTO create(EventDTO dto) throws InstanceAlreadyExistsException {
        if ( dto.getId() != null && repository.existsById( dto.getId() ) )
            throw new InstanceAlreadyExistsException( new AlreadyExistResponse( dto.getId(), "Event" ).getMessage() );
        EventEntity entity = modelMapper.map( dto, EventEntity.class );
        repository.save( entity );
        dto.setId( entity.getId() );
        return dto;
    }

    @Override
    public EventDTO update(EventDTO dto) throws NotFoundException {
        if ( dto.getId() == null || !repository.existsById( dto.getId() ) )
            throw new NotFoundException( new NotFoundResponse( dto.getId(), "Event" ).getMessage() );
        EventEntity entity = modelMapper.map( dto, EventEntity.class );
        if ( entity.getDeleted() || entity.getInTrash() ) {
            throw new NotFoundException( new NotFoundResponse( entity.getId(), "Event" ).getMessage() );
        }
        repository.save( entity );
        return modelMapper.map( entity, EventDTO.class );
    }

    @Override
    public EventDTO getById(Long id) throws NotFoundException {
        if ( !repository.existsById( id ) )
            throw new NotFoundException( new NotFoundResponse( id, "Event" ).getMessage() );
            EventEntity entity = repository.findById( id ).get();
        if ( entity.getDeleted() || entity.getInTrash() )
            throw new NotFoundException( new NotFoundResponse( id, "Event" ).getMessage() );
        return modelMapper.map( entity, EventDTO.class );
    }

    @Override
    public EventDTO deleteById(Long id) throws NotFoundException {
        if ( !repository.existsById( id ) )
            throw new NotFoundException( new NotFoundResponse( id, "Event" ).getMessage() );
        EventEntity entity = repository.findById( id ).get();
        if ( entity.getDeleted() )
            throw new NotFoundException( new NotFoundResponse( id, "Event" ).getMessage() );
        entity.setDeleted( true );
        repository.save( entity );
        return modelMapper.map( entity, EventDTO.class );
    }

    @Override
    public void moveToTrash( Long id ) throws NotFoundException, InstanceAlreadyExistsException {
        if ( !repository.existsById( id ) )
            throw new NotFoundException( new NotFoundResponse( id, "Event" ).getMessage() );
        EventEntity entity = repository.findById( id ).get();
        if ( entity.getInTrash() )
            throw new InstanceAlreadyExistsException( new AlreadyInTrashResponse( id, "Event" ).getMessage() );
        if ( entity.getDeleted() )
            throw new NotFoundException( new NotFoundResponse( entity.getId(), "Event" ).getMessage() );
        entity.setInTrash( true );
        repository.save( entity );
    }

    @Override
    public List<EventDTO> getAll() {
        List<EventEntity> users = repository.findAllByDeletedIsFalseAndInTrashIsFalse();
        return modelMapper.mapAll( users, EventDTO.class );
    }

    @Override
    public List<EventDTO> getAllInTrash() {
        List<EventEntity> users = repository.findAllByDeletedIsFalseAndInTrashIsTrue();
        return modelMapper.mapAll( users, EventDTO.class );
    }

    @Override
    public void restore( Long id ) throws NotFoundException, InstanceAlreadyExistsException {
        if ( !repository.existsById( id ) ) throw new NotFoundException( new NotFoundResponse( id, "Event" ).getMessage() );
        EventEntity entity = repository.findById( id ).get();
        if ( entity.getDeleted() ) throw new NotFoundException( new NotFoundResponse( id, "Event" ).getMessage() );
        if ( !entity.getInTrash() )
            throw new InstanceAlreadyExistsException( new NotInTrashResponce( id, "Event" ).getMessage() );
        entity.setInTrash( false );
        repository.save( entity );
    }

    @Override
    public EventDTO getByAgeGroupId(Long id) throws NotFoundException, InstanceAlreadyExistsException {
        if ( !ageGroupRepository.existsById( id ) )
            throw new NotFoundException( new NotFoundResponse( id, "AgeGroup" ).getMessage() );
        if ( ageGroupRepository.isAssignedById( id ) == null )
            throw new InstanceAlreadyExistsException( new NotFoundResponse( id, "AgeGroup" ).getMessage() );
        AgeGroupEntity entity = ageGroupRepository.findById( id ).get();
        if ( entity.getDeleted() ) throw new NotFoundException( new NotFoundResponse( id, "AgeGroup" ).getMessage() );
        EventEntity eventEntity = repository.findByAgeGroupId( id );
        if ( eventEntity.getDeleted() )
            throw new NotFoundException( new NotFoundResponse( id, "Event" ).getMessage() );
        return modelMapper.map( eventEntity, EventDTO.class );
    }
}
