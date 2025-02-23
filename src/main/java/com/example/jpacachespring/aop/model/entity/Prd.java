package com.example.jpacachespring.aop.model.entity;

import com.example.jpacachespring.aop.model.entity.category.Cat;
import com.example.jpacachespring.aop.model.entity.category.CatG;
import com.example.jpacachespring.aop.model.entity.category.CatSG;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
//@Indexed
@Entity
/*@Table(name = "product",
        uniqueConstraints = { @UniqueConstraint(name = "UC_PRODUCT_COL_NAME", columnNames = {"shop_id"})},
//        , "name"
        indexes = {
                @Index(name = "IDX_PRODUCT_ID", columnList = "id"),
//                @Index(name = "IDX_PRODUCT_NAME_SHOP", columnList = "active,shop_id,name"),
                @Index(name = "IDX_PRODUCT_SHOP", columnList = "active,shop_id"),
//                @Index(name = "IDX_PRODUCT_NAME", columnList = "active,name"),
                @Index(name = "IDX_PRODUCT_POPULARITY", columnList = "popularity")})*/
public class Prd extends AbstractGroupEntity  implements Serializable {

    @NotNull
    private Long chatId;

    private Integer popularity;

    private String article;
    //@NotNull
//    @Field
    private String shortText;

    //@NotNull
    private String bigText;

    //@NotNull
    private BigDecimal price;

    private Integer priceHash;

    @Lob
    @JsonIgnore
    @Column(name = "image", columnDefinition="MEDIUMBLOB")
    private byte[] image;

    //@NotNull
    private BigDecimal discount;

    //@NotNull
    private boolean productService;

    private Long duration;

    //@NotNull
    @ManyToOne
    //@JoinColumn(name = "shopId", nullable = false, insertable = true, updatable = true)
    private Shop shop;

    private boolean active = false;

    /*
    @ManyToMany(mappedBy = "productSet", fetch = FetchType.LAZY)
    private Set<Cat> categorySet = new HashSet<>();

    @ManyToMany(mappedBy = "productSet", fetch = FetchType.LAZY)
    private Set<CatG> categoryGroupSet = new HashSet<>();

    @ManyToMany(mappedBy = "productSet", fetch = FetchType.LAZY)
    private Set<CatSG> categorySuperGroupSet = new HashSet<>();
    */
    @NotNull
    @ManyToOne
    private Cat category;

    @NotNull
    @ManyToOne
    private CatG categoryGroup;

    @NotNull
    @ManyToOne
    private CatSG categorySuperGroup;

/*
    @ManyToMany(mappedBy = "productSet")
    private Set<NameWord> nameWordSet = new HashSet<>();*/

    @OneToMany(mappedBy="product")
    private List<NameWordProduct> nameWordProductList = new ArrayList<>();

/*
    @OneToMany(mappedBy="product")
    private List<Purchase> purchaseList = new ArrayList<>();*/

    @OneToMany(mappedBy="product", fetch = FetchType.LAZY)
    private List<ScheduleDefault> scheduleDefaultList = new ArrayList<>();

    @OneToMany(mappedBy="product", fetch = FetchType.LAZY)
    private List<ScheduleBuyer> scheduleBuyerList = new ArrayList<>();

    /*@ManyToMany(mappedBy = "productSet")
    private Set<Basket> basketList = new HashSet<>();*/

    @OneToMany(mappedBy="product", fetch = FetchType.LAZY)
    private List<BasketProduct> basketProductList = new ArrayList<>();

    @OneToMany(mappedBy="product", fetch = FetchType.LAZY)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy="product", fetch = FetchType.LAZY)
    private List<CategoryFilterProduct> categoryFilterProductList = new ArrayList<>();

    @OneToMany(mappedBy="currentCreatingProduct", fetch = FetchType.LAZY)
    private List<Shop> currentCreatingProductShopList = new ArrayList<>();

    public Prd(Consumer<Prd> builder){
        builder.accept(this);
    }

    public Prd(Integer id){
        super.setId(id);
    }

    public Prd() {
        super();
    }

    public void setPrice(BigDecimal price){
        if (price != null) {
            this.priceHash = price.intValue();
            this.price = price;
        }
    }

/*
    public void addCategory(Cat category) {
        this.categorySet.add(category);
    }

    public void addCategoryGroup(CatG categoryGroup) {
        this.categoryGroupSet.add(categoryGroup);
    }

    public void addCategorySuperGroup(CatSG categorySuperGroup) {
        this.categorySuperGroupSet.add(categorySuperGroup);
    }
 */
/*
    public void addPurchase(Purchase purchase) {
        this.purchaseList.add(purchase);
    }*/

    public void addScheduleDefault(ScheduleDefault scheduleDefault) {
        this.scheduleDefaultList.add(scheduleDefault);
    }

    public void addScheduleBuyer(ScheduleBuyer scheduleBuyer) {
        this.scheduleBuyerList.add(scheduleBuyer);
    }

    public void addBasketProduct(BasketProduct basketProduct) {
        this.basketProductList.add(basketProduct);
    }

    public void addBookmark(Bookmark bookmark) {
        this.bookmarkList.add(bookmark);
    }

    public void addCategoryFilterProduct(CategoryFilterProduct categoryFilterProduct) {
        this.categoryFilterProductList.add(categoryFilterProduct);
    }


    @Override
    public String toString() {
        return "Prd{" +
                "id=" + super.getId() +
//                ", name='" + super.getName() + '\'' +
                ", shortText='" + shortText + '\'' +
                ", bigText='" + bigText + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", productService=" + productService +
                ", duration=" + duration +
                '}';
    }
/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prd)) return false;
        if (!super.equals(o)) return false;
        Prd product = (Prd) o;
        return productService == product.productService
                && active == product.active
                && Objects.equals(shortText, product.shortText)
                && Objects.equals(bigText, product.bigText)
                && Objects.equals(price, product.price)
                && Objects.equals(discount, product.discount)
                && Objects.equals(duration, product.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), shortText, bigText, price, discount, productService, duration, active);
    }*/


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prd)) return false;
        if (!super.equals(o)) return false;
        Prd action = (Prd) o;
        return super.getId().equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}

