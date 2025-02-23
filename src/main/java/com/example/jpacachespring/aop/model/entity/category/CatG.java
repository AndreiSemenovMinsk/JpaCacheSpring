package com.example.jpacachespring.aop.model.entity.category;

import com.example.jpacachespring.aop.model.entity.AbstractGroupEntity;
import com.example.jpacachespring.aop.model.entity.Prd;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

//@Data
@Getter
@Setter
@Entity
public class CatG extends AbstractGroupEntity implements Serializable {

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "categorySuperGroupId", nullable = false)
    private CatSG categorySuperGroup;

    @OneToMany(mappedBy="categoryGroup")
    private Set<Cat> categorySet = new HashSet<>();

    /*@ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "category_group_product",
            joinColumns = @JoinColumn(name = "category_group_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Prd> productSet = new HashSet<>();*/

    @OneToMany(mappedBy="categoryGroup")
    private Set<Prd> productSet = new HashSet<>();

    public CatG(Consumer<CatG> builder){
        builder.accept(this);
    }

    public CatG() {
        super();
    }

    public void addProduct(Cat product) {
        this.categorySet.add(product);
    }

    @Override
    public String toString() {
        return "CatG{" +
                "id=" + super.getId() +
                ", name=" + super.getAlias() +
                '}';
    }
}

