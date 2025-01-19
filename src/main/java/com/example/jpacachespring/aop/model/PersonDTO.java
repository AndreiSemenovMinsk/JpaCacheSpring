package com.example.jpacachespring.aop.model;

//@Data
//@Accessors(chain = true)
public class PersonDTO extends DTO {
    private String name;
    private Integer age;
    private String surname;
    private Integer parentDTOId;

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
    public void setParentDTOId(Integer parentEntityId) {
        this.parentDTOId = parentEntityId;
    }
    public Integer getParentDTOId() {
        return parentDTOId;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return "{age: " + this.age + ", name: " + this.name + ", parentId: " + parentDTOId + ";}";
    }
}
