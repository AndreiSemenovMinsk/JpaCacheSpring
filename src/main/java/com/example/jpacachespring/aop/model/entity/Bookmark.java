package com.example.jpacachespring.aop.model.entity;

import com.example.jpacachespring.aop.model.entity.telegram.Users;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.function.Consumer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

//@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
public class Bookmark  extends AbstractEntity  implements Serializable {

    @NotNull
    private Integer radius;

    @NotNull
    private boolean viberNotify;

    @NotNull
    private boolean priceUpdated = true;

    private BigDecimal bidPrice;

    @NotNull
    @ManyToOne
    private Shop shop;

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "buyerId", nullable = false, insertable = false, updatable = false)
    private Users users;

    private Instant notification;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    /*@ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "bookmark_product",
            joinColumns = @JoinColumn(name = "bookmark_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )*/
    private Prd product;

    public Bookmark (Consumer<Bookmark> builder){
        builder.accept(this);
    }

    public Bookmark() {
        super();
    }


    @Override
    public String toString() {
        return "Bookmark{" +
                "id=" + id +
                ", radius=" + radius +
                ", viberNotify=" + viberNotify +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bookmark)) return false;
        if (!super.equals(o)) return false;
        Bookmark action = (Bookmark) o;
        return super.id.equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}

