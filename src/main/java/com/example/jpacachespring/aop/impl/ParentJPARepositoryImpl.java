package com.example.jpacachespring.aop.impl;

import com.example.jpacachespring.aop.model.ParentDTO;
import com.example.jpacachespring.aop.repo.ParentJPARepository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ParentJPARepositoryImpl implements ParentJPARepository {

    @Override
    public List<ParentDTO> findByNameAndAge(String name, Integer age) {
        return null;
    }

    @Override
    public ParentDTO save(ParentDTO entity) {
        return null;
    }
}
