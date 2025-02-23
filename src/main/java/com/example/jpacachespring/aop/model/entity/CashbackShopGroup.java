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
@Table(name = "cashback_shop_group",
        indexes = {
                @Index(name = "IDX_CASHBACK_SHOP_GROUP_COL_ID", columnList = "id"),
                @Index(name = "IDX_CASHBACK_SHOP_GROUP_COL_USER", columnList = "users_id"),
                @Index(name = "IDX_CASHBACK_SHOP_GROUP_COL_SHOP", columnList = "shop_group_id"),
                @Index(name = "IDX_CASHBACK_SHOP_GROUP_COL_USER_SHOP", columnList = "users_id,shop_group_id")})
public class CashbackShopGroup extends AbstractEntity  implements Serializable {

    // OneToOne - потому что может быть только одна дефолтовая акция
    @NotNull
    @OneToOne
    private Purchase purchase;

    @NotNull
    private boolean manual;

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "buyerId", nullable = false, insertable = false, updatable = false)
    private Users users;

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "shopId", nullable = false, insertable = false, updatable = false)
    private ShopGroup shopGroup;

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "shopId", nullable = false, insertable = false, updatable = false)
    private Shop shop;

    public CashbackShopGroup(Consumer<CashbackShopGroup> builder){
        builder.accept(this);
    }

    public CashbackShopGroup() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CashbackShopGroup)) return false;
        if (!super.equals(o)) return false;
        CashbackShopGroup action = (CashbackShopGroup) o;
        return super.id.equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}

