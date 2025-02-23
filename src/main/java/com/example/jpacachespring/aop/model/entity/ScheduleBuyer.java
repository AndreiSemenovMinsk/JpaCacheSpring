package com.example.jpacachespring.aop.model.entity;

import com.example.jpacachespring.aop.model.entity.telegram.Users;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class ScheduleBuyer  extends AbstractEntity  implements Serializable {

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
    //@JoinColumn(name = "buyerId", nullable = false, insertable = false, updatable = false)
    private Users users;

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "productId", nullable = false, insertable = false, updatable = false)
    private Prd product;

    public ScheduleBuyer (Consumer<ScheduleBuyer> builder){
        builder.accept(this);
    }

    public ScheduleBuyer() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScheduleBuyer)) return false;
        if (!super.equals(o)) return false;
        ScheduleBuyer action = (ScheduleBuyer) o;
        return super.getId().equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}

