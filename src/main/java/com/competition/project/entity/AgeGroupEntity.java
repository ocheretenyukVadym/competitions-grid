package com.competition.project.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
@Table( name = "age_group" )
public class AgeGroupEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotNull( message = "Title can't be null" )
    @NotBlank( message = "Title can't be empty" )
    @Length( min = 5, max = 255, message = "Length must be between 5 and 25 symbols" )
    @Column( name = "name" )
    private String name;

    @NotNull( message = "Field 'deleted' can't be null" )
    @Column( name = "deleted" )
    private Boolean deleted = false;

    @NotNull( message = "Field 'inTrash' can't be null" )
    @Column( name = "inTrash" )
    private Boolean inTrash = false;
}
