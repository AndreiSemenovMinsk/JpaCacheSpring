package com.example.jpacachespring.aop.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Data
@Entity
public class ShopGroup extends AbstractEntity  implements Serializable {

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "shopGroupSet")
    Set<Shop> shopSet = new HashSet<>();

    @OneToMany(mappedBy="currentConversationShopGroup")
    private List<Shop> currentConversationShopGroupShopList = new ArrayList<>();


    @OneToMany(mappedBy="shopGroup")
    private List<CashbackShopGroup> cashbackShopGroupList = new ArrayList<>();

    /*public ShopGroup(Consumer<ShopGroup> builder){
        builder.accept(this);
    }*/

    public ShopGroup(Integer id) {
        super.id = id;
    }

    public ShopGroup() {
        super();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShopGroup)) return false;
        if (!super.equals(o)) return false;
        ShopGroup action = (ShopGroup) o;
        return super.getId().equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }

}

