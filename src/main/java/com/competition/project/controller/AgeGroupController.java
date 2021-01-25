package com.competition.project.controller;

import com.competition.project.dto.AgeGroupDTO;
import com.competition.project.dto.EventDTO;
import com.competition.project.service.AgeGroupService;
import com.competition.project.service.EventService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;

@Controller
@RequestMapping( "/events" )
public class AgeGroupController {
    @Autowired
    private EventService eventService;

    @Autowired
    private AgeGroupService service;

    @GetMapping( "/{event_id}/groups/{id}" )
    public ResponseEntity<EventDTO> getById(@PathVariable Long event_id, @PathVariable Long id ) throws NotFoundException, InstanceAlreadyExistsException {
        EventDTO dto = eventService.getByAgeGroupId( id );
        return new ResponseEntity<>( dto, HttpStatus.OK );
    }
    @PostMapping( "/{event_id}/groups" )
    public ResponseEntity<?> create( @PathVariable Long event_id, @RequestBody AgeGroupDTO dto ) throws NotFoundException, InstanceAlreadyExistsException {
        return new ResponseEntity<>( service.create( dto, event_id ), HttpStatus.CREATED );
    }
}
