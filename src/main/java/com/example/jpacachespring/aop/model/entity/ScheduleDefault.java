package com.example.jpacachespring.aop.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

//@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class ScheduleDefault  extends AbstractEntity  implements Serializable {

    @NotNull
    private Integer timeStart;

    @NotNull
    private Integer timeEnd;

    @NotNull
    private Integer day;

    @NotNull
    private Integer month;

    @NotNull
    private Integer year;

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "productId", nullable = false, insertable = true, updatable = true)
    private Prd product;

    public ScheduleDefault (Consumer<ScheduleDefault> builder){
        builder.accept(this);
    }

    public ScheduleDefault() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScheduleDefault)) return false;
        if (!super.equals(o)) return false;
        ScheduleDefault action = (ScheduleDefault) o;
        return super.getId().equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}

