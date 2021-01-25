package com.competition.project.repository;

import com.competition.project.entity.AgeGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AgeGroupRepository extends JpaRepository<AgeGroupEntity, Long> {
    List<AgeGroupEntity> findAllByDeletedIsFalseAndInTrashIsFalse();
    List<AgeGroupEntity> findAllByDeletedIsFalseAndInTrashIsTrue();

    @Query( value = "SELECT event_id FROM age_group WHERE age_group.id = ?1", nativeQuery = true )
    Long isAssignedById( Long id );
}
