package com.example.jpacachespring.aop.mappers;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.jpacachespring.aop.model.PersonDTO;
import com.example.jpacachespring.model.PersonEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = SPRING, uses = {ParentEntityMapper.class})
public abstract class PersonEntityMapper extends EntityMapper<PersonDTO, PersonEntity> {

    @Override
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "age", target = "age")
    public abstract PersonEntity toEntity(PersonDTO dto);

    @Override
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "age", target = "age")
//    @Mapping(source = "parentEntity.id", target = "parentId")
    public abstract PersonDTO toDto(PersonEntity entity);

}
