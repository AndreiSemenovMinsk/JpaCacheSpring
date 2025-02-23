package com.example.jpacachespring.aop.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Consumer;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

//@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class CashbackWriteOffResultPurchase extends AbstractEntity  implements Serializable {

    private BigDecimal sum;

    private Integer number;

    @ManyToOne
    private Purchase previousPurchase;

    @ManyToOne
    private CashbackWriteOff cashbackWriteOff;

    public CashbackWriteOffResultPurchase(Consumer<CashbackWriteOffResultPurchase> builder){
        builder.accept(this);
    }

    public CashbackWriteOffResultPurchase() {

    }

    @Override
    public String toString() {
        return "CashbackWriteOff{" +
                "id=" + id +
                ", sum=" + sum +
                ", number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CashbackWriteOffResultPurchase)) return false;
        if (!super.equals(o)) return false;
        CashbackWriteOffResultPurchase action = (CashbackWriteOffResultPurchase) o;
        return super.id.equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}

