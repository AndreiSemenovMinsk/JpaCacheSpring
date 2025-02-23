package com.example.jpacachespring.aop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Consumer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

//@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "partner",
        uniqueConstraints = @UniqueConstraint(
                name = "partnerConstraint",
                columnNames = {"creditorId", "debtorId"}))
@AllArgsConstructor
public class Partner extends AbstractEntity  implements Serializable {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "creditorId", nullable = false)
    private Shop creditor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "debtorId", nullable = false)
    private Shop debtor;

    @NotNull
    private BigDecimal lim;

    @NotNull
    private Integer rate;

    @NotNull
    private BigDecimal sum;

    public Partner (Consumer<Partner> builder){
        builder.accept(this);
    }

    public Partner() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Partner)) return false;
        if (!super.equals(o)) return false;
        Partner action = (Partner) o;
        return super.id.equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }

    @Override
    public String toString() {
        return "Partner{" +
                "id=" + id +
                ", creditor=" + creditor.getId() +
                ", debtor=" + debtor.getId() +
                ", lim=" + lim +
                ", rate=" + rate +
                ", sum=" + sum +
                '}';
    }
}

