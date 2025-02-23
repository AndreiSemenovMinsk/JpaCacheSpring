package com.example.jpacachespring.aop.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Data
@Entity
public class BasketProduct extends AbstractEntity implements Serializable {

    @NotNull
    @ManyToOne
    private Basket basket;

    @NotNull
    @ManyToOne
    private Prd product;

    @NotNull
    private Integer productAmount;

    public BasketProduct(Consumer<BasketProduct> builder){
        builder.accept(this);
    }

    public BasketProduct() {

    }

    @Override
    public String toString() {
        return "BasketProduct{" +
                "id=" + id +
                ", productAmount=" + productAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasketProduct)) return false;
        if (!super.equals(o)) return false;
        BasketProduct action = (BasketProduct) o;
        return super.id.equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}

