package com.example.jpacachespring.aop.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.function.Consumer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "filter_option")
public class FilterOption extends AbstractEntity  implements Serializable {

    @NotNull
    private String name;

//    @NotNull
    @ManyToOne
    @JoinColumn(name = "filterPointId"/*, nullable = false*/)
    private FilterPoint filterPoint;

    public FilterOption(Consumer<FilterOption> builder){
        builder.accept(this);
    }

    public FilterOption() {
        super();
    }

    @Override
    public String toString() {
        return "FilterOption{" +
                "name='" + name + '\'' +
                ", filterPoint=" + filterPoint +
                ", filterPoint='" +
                (filterPoint.getId() > 0 ?  "+" : filterPoint.getNameRU() + "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + filterPoint.getNameEN()) + "\'" +
                '}';
    }
}

