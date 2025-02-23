package com.example.jpacachespring.aop.model.entity.category;

import com.example.jpacachespring.aop.model.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import jakarta.persistence.*;

//@Data
@Getter
@Setter
@Entity
public class NameLocale extends AbstractEntity implements Serializable {
/*
    @NotNull
    @ManyToOne
    private AbstractGroupEntity abstractGroup;

    @OneToMany(mappedBy = "nameLocal")
    private List<LanguageEnum>  language;

    @NotNull
    private String alias;

    public NameLocale(Consumer<NameLocale> builder){
        builder.accept(this);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + super.getId() +
                '}';
    }*/

    public NameLocale() {
        super();
    }
}

