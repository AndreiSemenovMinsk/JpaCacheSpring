package com.example.jpacachespring.aop.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

//@Data
//@Accessors(chain = true)
public class ParentDTO extends DTO {
    private String name;
    private Integer age;
    private String surname;
    private List<PersonDTO> personList = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public List<PersonDTO> getPersonList() {
        return personList;
    }

    public void addPersonList(PersonDTO personList) {
        this.personList.add(personList);
    }

    @Override
    public String toString() {
        return " age: " + this.age + ", name: " + this.name;
    }
}
