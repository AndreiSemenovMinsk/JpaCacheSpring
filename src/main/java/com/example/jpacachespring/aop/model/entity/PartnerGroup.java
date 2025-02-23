package com.example.jpacachespring.aop.model.entity;

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
@Table(name = "partner_group",
        uniqueConstraints = @UniqueConstraint(
                name = "partnerGroupConstraint",
                columnNames = {"creditorId", "debtorId"}))
public class PartnerGroup extends AbstractEntity  implements Serializable {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "creditorId", nullable = false)
    private Shop creditor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "debtorId", nullable = false)
    private ShopGroup debtor;

    @NotNull
    private BigDecimal lim;
    // почему нет rate ставки на группу?

    @NotNull
    private String name;

    @NotNull
    private BigDecimal sum;

    public PartnerGroup(Consumer<PartnerGroup> builder){
        builder.accept(this);
    }

    public PartnerGroup() {

    }

//    public String getShopNames(){
//        return this.debtor.getShopSet().stream().map(e -> e.getName().substring(0, 5)).collect(Collectors.joining(", "));
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartnerGroup)) return false;
        if (!super.equals(o)) return false;
        PartnerGroup action = (PartnerGroup) o;
        return super.id.equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }

    @Override
    public String toString() {
        return "PartnerGroup{" +
                "id=" + id +
                ", creditor=" + creditor.getId() +
                ", debtor=" + debtor.getId() +
                ", lim=" + lim +
                ", name='" + name + '\'' +
                ", sum=" + sum +
                '}';
    }
}

