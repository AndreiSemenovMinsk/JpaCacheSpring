package com.example.jpacachespring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class PersonEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column
    private String name;

    @Column
    private Integer age;

    @Column
    private String surname;

    @ManyToOne(fetch = FetchType.LAZY)
    private ParentEntity parentEntity;


}
