package com.example.jpacachespring.aop.model.entity;

import com.example.jpacachespring.aop.model.entity.telegram.Users;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

//@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "cashback",
        indexes = {
                @Index(name = "IDX_CASHBACK_COL_ID", columnList = "id"),
                @Index(name = "IDX_CASHBACK_COL_USER", columnList = "users_id"),
                @Index(name = "IDX_CASHBACK_COL_SHOP", columnList = "shop_id"),
                @Index(name = "IDX_CASHBACK_COL_USER_SHOP", columnList = "users_id,shop_id")})
public class Cashback  extends AbstractEntity  implements Serializable {

    private Integer radius;

    //не OneToOne - потому что может быть несколько акций разных на одну покупку
    @NotNull
    @ManyToOne
    private Purchase purchase;

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "buyerId", nullable = false, insertable = false, updatable = false)
    private Users users;

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "shopId", nullable = false, insertable = false, updatable = false)
    private Shop shop;

    @NotNull
    @ManyToOne
    private Action action;

    public Cashback (Consumer<Cashback> builder){
        builder.accept(this);
    }

    public Cashback() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cashback)) return false;
        if (!super.equals(o)) return false;
        Cashback action = (Cashback) o;
        return super.id.equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}

