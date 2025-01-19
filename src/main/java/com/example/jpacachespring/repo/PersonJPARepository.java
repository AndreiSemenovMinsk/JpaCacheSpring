package com.example.jpacachespring.repo;

import com.example.jpacachespring.model.PersonEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonJPARepository extends JpaRepository<PersonEntity, Integer> {

    //@CacheRequest
    List<PersonEntity> findByNameAndAge(String name, Integer age);

    List<PersonEntity> findByParentEntityId(Integer parentId);

    List<PersonEntity> findByParentEntityNameAndAge(String parentName, Integer age);

    PersonEntity save(PersonEntity entity);
}
