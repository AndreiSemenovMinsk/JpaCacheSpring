package com.example.jpacachespring.controllers;

import com.example.jpacachespring.model.PersonEntity;
import com.example.jpacachespring.repo.ParentRepository;
import com.example.jpacachespring.repo.PersonRepository;
import com.example.jpacachespring.model.ParentEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequiredArgsConstructor
public class Controller2 {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ParentRepository parentRepository;

    @GetMapping("/save2")
    public void save() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            var p1 = new ParentEntity();
            p1.setName("P" + i);
            p1.setAge(i);
            parentRepository.save(p1);

            for (int j = 0; j < 10; j++) {
                var c1 = new PersonEntity();
                c1.setName("C" + j);
                c1.setAge(j);
                personRepository.save(c1);
            }
        }
        System.out.println("*save time " + (System.currentTimeMillis() - start));
    }

    @GetMapping("/small2")
    public void smallOperation() throws InterruptedException {
        final List<PersonEntity> p1 = personRepository.findByNameAndAge("C1", 1);
        final PersonEntity personEntity = p1.get(0);
        System.out.println("@@@" + personEntity.getParentEntity());
        System.out.println(personEntity.getParentEntityId());
    }

/*
    docker-compose exec jpacachespring-microservice1 bash -c "liquibase --changeLogFile=db/changelog/db.changelog-master.yaml --url='jdbc:postgresql://postgres:5432/mail_service_db' --username=mail_service --password=mail_service generateChangeLog"
    */

}
