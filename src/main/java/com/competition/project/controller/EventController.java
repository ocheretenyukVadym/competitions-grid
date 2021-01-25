package com.competition.project.controller;

import com.competition.project.dto.EventDTO;
import com.competition.project.service.EventService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.websocket.server.PathParam;

@Controller
@RequestMapping( "/events" )
public class EventController {
    @Autowired
    private EventService service;

    @GetMapping( "/{id}" )
    public ResponseEntity<EventDTO> getById(@PathVariable Long id ) throws NotFoundException {
        EventDTO dto = service.getById( id );
        return new ResponseEntity<>( dto, HttpStatus.OK );
    }

    @PostMapping
    public ResponseEntity<?> create( @RequestBody EventDTO dto ) throws InstanceAlreadyExistsException {
        return new ResponseEntity<>( service.create( dto ), HttpStatus.CREATED );
    }

    @PutMapping
    public ResponseEntity<EventDTO> update( @RequestBody EventDTO dto ) throws NotFoundException {
        return new ResponseEntity<>( service.update( dto ), HttpStatus.OK );
    }

    @DeleteMapping
    public ResponseEntity<EventDTO> deleteById( @PathParam( "id" ) Long id ) throws NotFoundException {
        return new ResponseEntity<>( service.deleteById( id ), HttpStatus.OK );
    }

    @GetMapping
    public ResponseEntity getAllEvents() {
        return new ResponseEntity( service.getAll(), HttpStatus.OK );
    }

    @GetMapping( "/allInTrash" )
    public ResponseEntity<?> getAllInTrash() {
        return new ResponseEntity<>( service.getAllInTrash(), HttpStatus.OK );
    }

    @PostMapping( "/moveToTrash/{id}" )
    public ResponseEntity<?> moveToTrashById( @PathVariable Long id ) throws NotFoundException, InstanceAlreadyExistsException {
        service.moveToTrash( id );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @PostMapping( "/restore/{id}" )
    public ResponseEntity<?> restore( @PathVariable Long id ) throws NotFoundException, InstanceAlreadyExistsException {
        service.restore( id );
        return new ResponseEntity<>( HttpStatus.OK );
    }
}
