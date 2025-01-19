package com.example.jpacachespring.aop.mappers;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.jpacachespring.aop.model.ParentDTO;
import com.example.jpacachespring.model.ParentEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = SPRING, uses = {PersonEntityMapper.class})
public abstract class ParentEntityMapper extends EntityMapper<ParentDTO, ParentEntity> {

    @Override
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "age", target = "age")
    public abstract ParentEntity toEntity(ParentDTO dto);


    @Override
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "age", target = "age")
    public abstract ParentDTO toDto(ParentEntity entity);

}
