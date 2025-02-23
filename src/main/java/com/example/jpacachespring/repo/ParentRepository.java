package com.example.jpacachespring.repo;

import com.example.jpacachespring.model.ParentEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface ParentRepository extends JpaRepository<ParentEntity, Integer> {

    List<ParentEntity> findByNameAndAge(String name, Integer age);

    ParentEntity save(ParentEntity entity);
}
