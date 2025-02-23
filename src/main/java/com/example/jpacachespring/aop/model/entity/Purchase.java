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
@Table(name = "purchase",
        indexes = {
                @Index(name = "IDX_PURCHASE_COL_ID", columnList = "id"),
                @Index(name = "IDX_PURCHASE_COL_USER", columnList = "buyer_id"),
                @Index(name = "IDX_PURCHASE_COL_SHOP", columnList = "shop_id"),
                @Index(name = "IDX_PURCHASE_COL_USER_SHOP", columnList = "buyer_id,shop_id")})
public class Purchase extends AbstractEntity  implements Serializable {

    @NotNull
    private BigDecimal sum;

    private Integer numberCoupon;

    @NotNull
    @ManyToOne
    private Users buyer;

    @NotNull
    @ManyToOne
    private Shop shop;

    //не OneToOne - потому что может быть несколько акций разных на одну покупку
    @OneToMany(mappedBy="purchase")
    private List<Cashback> cashbackList = new ArrayList<>();

    // OneToOne - потому что может быть только одна дефолтовая акция
    @OneToOne(mappedBy="purchase")
    private CashbackShopGroup cashbackShopGroup;

    @OneToMany(mappedBy="previousPurchase")
    private List<CashbackWriteOffResultPurchase> cashbackWriteOffResultPurchase = new ArrayList<>();

    public Purchase (Consumer<Purchase> builder){
        builder.accept(this);
    }

    public Purchase() {
        super();
    }

    public void addCashback(Cashback cashback){
        cashbackList.add(cashback);
    }

    public void increaseNumberCoupon(Integer addNumberCoupon){
        numberCoupon += addNumberCoupon;
    }

    public void decreaseSum(BigDecimal decreaseSum){
        this.sum = this.sum.subtract(decreaseSum);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", sum=" + sum +
                ", numberCoupon=" + numberCoupon +
                ", buyer=" + buyer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchase)) return false;
        if (!super.equals(o)) return false;
        Purchase action = (Purchase) o;
        return super.getId().equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}