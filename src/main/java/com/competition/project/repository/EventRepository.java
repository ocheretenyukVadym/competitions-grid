package com.competition.project.repository;

import com.competition.project.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findAllByDeletedIsFalseAndInTrashIsFalse();

    List<EventEntity> findAllByDeletedIsFalseAndInTrashIsTrue();

    @Query( value = "SELECT event.id, event.deleted, event.in_trash, event.name "
            + "FROM events JOIN age_group ON age_group.event_id = event.id "
            + "WHERE age_group.id = ?1", nativeQuery = true )
    EventEntity findByAgeGroupId( Long id );
}
