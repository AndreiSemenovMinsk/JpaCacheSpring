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


//@Data
@Getter
@Setter
@Entity
//@Table(name = "category_super_group", uniqueConstraints=@UniqueConstraint(name="UC_categorySuperGroup_name", columnNames={"name"}))
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CatSG extends AbstractGroupEntity implements Serializable {

    @OneToMany(mappedBy="categorySuperGroup")
    private Set<CatG> categoryGroupSet = new HashSet<>();

/*
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "category_super_group_product",
            joinColumns = @JoinColumn(name = "category_super_group_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Prd> productSet = new HashSet<>();*/

    @OneToMany(mappedBy="categorySuperGroup")
    private Set<Prd> productSet = new HashSet<>();

    public CatSG(Consumer<CatSG> builder){
        builder.accept(this);
    }

    public CatSG() {
        super();
    }

    public void addProduct(CatG categoryGroup) {
        this.categoryGroupSet.add(categoryGroup);
    }

    public Set<Prd> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Prd> productSet) {
        this.productSet = productSet;
    }

    @Override
    public String toString() {
        return "CatSG{" +
                "id=" + super.getId() +
                '}';
    }
}

