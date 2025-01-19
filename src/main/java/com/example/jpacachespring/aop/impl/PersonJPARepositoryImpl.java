package com.example.jpacachespring.aop.impl;

import com.example.jpacachespring.aop.model.PersonDTO;
import com.example.jpacachespring.aop.repo.PersonJPARepository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class PersonJPARepositoryImpl implements PersonJPARepository {

    //@CacheRequest
    @Override
    public List<PersonDTO> findByNameAndAge(String name, Integer age) {
        return null;
    }

    @Override
    public List<PersonDTO> findByParentDTOId(Integer parentId) {
        return null;
    }

    @Override
    public List<PersonDTO> findByParentDTONameAndAge(String parentName, Integer age) {
        return null;
    }

    @Override
    public PersonDTO save(PersonDTO entity) {
        return null;
    }
}
