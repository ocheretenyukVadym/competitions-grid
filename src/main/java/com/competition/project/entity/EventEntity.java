package com.competition.project.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table( name = "events" )
public class EventEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotNull( message = "Title can't be null" )
    @NotBlank( message = "Title can't be empty" )
    @Length( min = 5, max = 255, message = "Length must be between 5 and 25 symbols" )
    @Column( name = "name" )
    private String name;

    @Size( max = 10, message = "Max size of age groups is 10 items" )
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true )
    @JoinColumn( name = "event_id", referencedColumnName = "id" )
    private List<AgeGroupEntity> ageGroups = new ArrayList<>();

    @NotNull( message = "Field 'deleted' can't be null" )
    @Column( name = "deleted" )
    private Boolean deleted = false;

    @NotNull( message = "Field 'inTrash' can't be null" )
    @Column( name = "inTrash" )
    private Boolean inTrash = false;
}
