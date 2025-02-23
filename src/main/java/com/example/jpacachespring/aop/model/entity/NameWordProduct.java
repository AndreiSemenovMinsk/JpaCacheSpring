package com.example.jpacachespring.aop.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.function.Consumer;
import jakarta.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "name_word_product")
public class NameWordProduct extends AbstractEntity  implements Serializable {

    @ManyToOne
    private NameWord nameWord;

    @ManyToOne
    private Prd product;

    public NameWordProduct(Consumer<NameWordProduct> builder){
        builder.accept(this);
    }

    public NameWordProduct() {

    }

    @Override
    public String toString() {
        return "NameWordProduct{" +
                "id=" + id +
                ", nameWord=" + nameWord.getText() +
//                ", product=" + product.getName() +
                '}';
    }
}