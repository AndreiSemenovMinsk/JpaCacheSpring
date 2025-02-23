package com.example.jpacachespring.aop.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "name_word",
        uniqueConstraints = { @UniqueConstraint(name = "UC_NAME_WORD_COL_NAME", columnNames = {"text"})},
        indexes = {
                @Index(name = "IDX_TEXT", columnList = "text")})
public class NameWord extends AbstractEntity  implements Serializable {

    @NotNull
    @Size(max=20)
    private String text;

    /*@ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "name_word_product",
            joinColumns = @JoinColumn(name = "name_word_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Prd> productSet = new HashSet<>();*/

    @OneToMany(mappedBy="nameWord")
    private List<NameWordProduct> nameWordProductList = new ArrayList<>();

    public NameWord(Consumer<NameWord> builder){
        builder.accept(this);
    }

    public NameWord() {

    }
}

