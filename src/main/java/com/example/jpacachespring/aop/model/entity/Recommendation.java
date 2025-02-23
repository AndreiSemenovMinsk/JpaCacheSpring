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
@Table(name = "recommendation",
        uniqueConstraints = {
                @UniqueConstraint(name = "UC_RECOMMENDATION_COL_BUYER_FRIEND_SHOP", columnNames = {"buyer_id", "friend_id", "shop_id"})},
        indexes = {
                @Index(name = "IDX_RECOMMENDATION_COL_ID", columnList = "id"),
                @Index(name = "IDX_RECOMMENDATION_COL_FRIEND", columnList = "friend_id"),
                @Index(name = "IDX_RECOMMENDATION_COL_BUYER", columnList = "buyer_id"),
                @Index(name = "IDX_RECOMMENDATION_COL_SHOP", columnList = "shop_id"),
                @Index(name = "IDX_RECOMMENDATION_COL_BUYER_SHOP", columnList = "buyer_id,shop_id"),
                @Index(name = "IDX_RECOMMENDATION_COL_FRIEND_SHOP", columnList = "friend_id,shop_id")})
public class Recommendation extends AbstractEntity  implements Serializable {

    // если дал рекомендацию магазин
    @ManyToOne
    //@JoinColumn(name = "shopId", nullable = false, insertable = true, updatable = true)
    private Shop shop;

    // тот, кто получил рекомендацию
    @NotNull
    @ManyToOne
    //@JoinColumn(name = "buyerId", nullable = false, insertable = true, updatable = true)
    private Users buyer;

    // тот, кто дал рекомендацию
    @ManyToOne
    //@JoinColumn(name = "friendId", nullable = false, insertable = true, updatable = true)
    private Users friend;

    public Recommendation(Consumer<Recommendation> builder){
        builder.accept(this);
    }

    public Recommendation() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recommendation)) return false;
        if (!super.equals(o)) return false;
        Recommendation action = (Recommendation) o;
        return super.getId().equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}

