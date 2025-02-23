package com.example.jpacachespring.aop.model.entity;

import com.example.jpacachespring.aop.model.entity.telegram.Users;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import jakarta.persistence.*;

//@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@Entity
public class Basket extends AbstractEntity implements Serializable {

    @NotNull
    private String note;

    @NotNull
    @ManyToOne
    private Shop shop;

    @NotNull
    private Boolean temp = false;

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "buyerId", nullable = false, insertable = false, updatable = false)
    private Users users;

    @OneToMany(mappedBy = "basket", orphanRemoval = true)
    private List<BasketProduct> basketProductList = new ArrayList<>();

    /*@ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "basket_product",
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Prd> productSet = new HashSet<>();*/

    public Basket(Consumer<Basket> builder) {
        builder.accept(this);
    }

    public Basket(Integer id) {
        super.id = id;
    }

    public Basket() {
        super();
    }

    public void addBasketProduct(BasketProduct basketProduct) {
        basketProductList.add(basketProduct);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", temp='" + temp + '\'' +
                ", users='" + users.getId() + '\'' +
                ", shop='" + shop.getId() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Basket)) return false;
        if (!super.equals(o)) return false;
        Basket action = (Basket) o;
        return super.id.equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}

