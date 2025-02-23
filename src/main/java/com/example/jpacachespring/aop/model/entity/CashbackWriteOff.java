package com.example.jpacachespring.aop.model.entity;

import com.example.jpacachespring.aop.model.entity.telegram.Users;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

//@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class CashbackWriteOff  extends AbstractEntity  implements Serializable {

    private BigDecimal sum;

    private Integer numberCoupon;

    @NotNull
    private boolean approved;

    @OneToMany(mappedBy="cashbackWriteOff")
    private List<CashbackWriteOffResultPurchase> resultPurchaseList = new ArrayList<>();

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "shopId", nullable = false, insertable = false, updatable = false)
    private Shop shop;

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "buyerId", nullable = false, insertable = false, updatable = false)
    private Users users;


//    @ManyToOne(optional = true)
//    private Action action;

    public CashbackWriteOff (Consumer<CashbackWriteOff> builder){
        builder.accept(this);
    }

    public CashbackWriteOff() {

    }

    @Override
    public String toString() {
        return "CashbackWriteOff{" +
                "id=" + id +
                ", sum=" + sum +
                ", numberCoupon=" + numberCoupon +
                ", approved=" + approved +
                ", shop=" + shop.getId() +
                ", users=" + users.getId() +
//                ", action=" + action.getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CashbackWriteOff)) return false;
        if (!super.equals(o)) return false;
        CashbackWriteOff action = (CashbackWriteOff) o;
        return super.id.equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}

