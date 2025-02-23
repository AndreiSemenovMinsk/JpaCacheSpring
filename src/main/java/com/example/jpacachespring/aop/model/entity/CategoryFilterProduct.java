package com.example.jpacachespring.aop.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Consumer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

//@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "category_filter_product",
        uniqueConstraints = {
                @UniqueConstraint(name = "UC_CATEGORY_FILTER_PRODUCT", columnNames = {"product_id", "filter_point_id"})},
        indexes = {
                @Index(name = "IDX_RECOMMENDATION_COL_ID", columnList = "id"),
                @Index(name = "IDX_CATEGORY_FILTER_PRODUCT_COL_FRIEND", columnList = "product_id"),
                @Index(name = "IDX_CATEGORY_FILTER_PRODUCT_COL_BUYER", columnList = "filter_point_id"),
                @Index(name = "IDX_CATEGORY_FILTER_PRODUCT_COL_SHOP", columnList = "product_id,filter_point_id")})
public class CategoryFilterProduct  extends AbstractEntity  implements Serializable {

    private Integer value;

    private BigDecimal rawValue;

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "productId", nullable = false, insertable = false, updatable = false)
    private Prd product;

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "filterPointId", nullable = false, insertable = false, updatable = false)
    private FilterPoint filterPoint;

    public CategoryFilterProduct (Consumer<CategoryFilterProduct> builder){
        builder.accept(this);
    }

    public CategoryFilterProduct() {

    }

    public void setRawValue(BigDecimal rawValue) {
        this.rawValue = rawValue;

        BigDecimal resultValue;
        if (rawValue.compareTo(filterPoint.getMinValue()) < 0) {
            resultValue = filterPoint.getMinValue();
        } else if (rawValue.compareTo(filterPoint.getMaxValue()) > 0) {
            resultValue = filterPoint.getMaxValue();
        } else {
            resultValue = rawValue;
        }

        this.value = resultValue.subtract(filterPoint.getMinValue())
                .multiply(BigDecimal.valueOf(1000)).divide(filterPoint.getMaxValue().subtract(filterPoint.getMinValue())).intValue();
//        this.hashCode = this.value * 2_000_000 + this.id.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryFilterProduct)) return false;
        if (!super.equals(o)) return false;
        CategoryFilterProduct action = (CategoryFilterProduct) o;
        return super.id.equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }

    @Override
    public String toString() {
        return "CategoryFilterProduct{" +
                "id = " + super.getId() +
                ", value=" + value +
                ", rawValue=" + rawValue +
                ", product=" + product.getId() +
                ", filterPoint=" + filterPoint.getId() +
                '}';
    }
}

