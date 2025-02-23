package com.example.jpacachespring.aop.model.entity.category;


import com.example.jpacachespring.aop.model.entity.AbstractGroupEntity;
import com.example.jpacachespring.aop.model.entity.FilterPoint;
import com.example.jpacachespring.aop.model.entity.Prd;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;


@Getter
@Setter
@Entity
public class Cat extends AbstractGroupEntity implements Serializable {

    @NotNull
    private boolean actual;

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "categoryGroupId", nullable = false)
    private CatG categoryGroup;

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "categoryGroupId", nullable = false)
    private CatSG categorySuperGroup;


    @ManyToOne
    //@JoinColumn(name = "categoryGroupId", nullable = false)
    private Cat parentCategory;

    @OneToMany(mappedBy="parentCategory")
    private Set<Cat> childCategorySet = new HashSet<>();

    /*@ManyToMany
    @JoinTable(name = "category_product",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Prd> productSet = new HashSet<>();*/

    @OneToMany(mappedBy="category")
    private Set<Prd> productSet = new HashSet<>();

/*
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "category_filter_point",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "filter_point_id")
    )
    private Set<FilterPoint> filterPointSet = new HashSet<>();
*/

    @OneToMany(mappedBy="category")
    private List<FilterPoint> filterPointList = new ArrayList<>();

    public Cat(Consumer<Cat> builder){
        super();
        builder.accept(this);
    }

    public Cat() {
        super();
    }

    public void addProduct(Prd product) {
        this.productSet.add(product);
    }

    public void addFilterPoint(FilterPoint filterPoint) {
        this.filterPointList.add(filterPoint);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + super.getId() +
//                ", name=" + super.getName() +
                '}';
    }
}

