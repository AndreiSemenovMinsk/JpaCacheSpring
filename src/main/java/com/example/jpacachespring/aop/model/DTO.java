package com.example.jpacachespring.aop.model;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
@Accessors(chain = true)
public class DTO {

    @Id
    private int id = ThreadLocalRandom.current().nextInt();

    private int mark;// = ThreadLocalRandom.current().nextInt();
    private List<Object> fieldValues;
    private List<String> methodKeys;

    public DTO () {
        mark = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public List<Object> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<Object> fieldValues) {
        this.fieldValues = fieldValues;
    }
}
