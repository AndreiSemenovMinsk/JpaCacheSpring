package com.example.jpacachespring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ParentEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column
    private String name;

    @Column
    private Integer age;

    @OneToMany(mappedBy = "parentEntity")
    private List<PersonEntity> personEntityList = new ArrayList<>();
}
