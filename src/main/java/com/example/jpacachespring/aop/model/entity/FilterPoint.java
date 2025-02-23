package com.example.jpacachespring.aop.model.entity;

import com.example.jpacachespring.aop.model.entity.category.Cat;
import com.example.jpacachespring.aop.model.entity.category.LanguageEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "filter_point"/*, uniqueConstraints=@UniqueConstraint(name="UC_filter_point_name", columnNames={"name", "categoryId"})*/)
public class FilterPoint  extends AbstractEntity  implements Serializable {

    @NotNull
    private Integer inputType;

/*    @ElementCollection
    @CollectionTable(name = "i18n_filter_point_unit_name", foreignKey = @ForeignKey(name = "fk_i18n_FilterPoint_unitName"), joinColumns = @JoinColumn(name = "id"))
    @MapKeyColumn(name = "locale")
    @Column(name = "text")
    private Map<LanguageEnum, String> unitNameLanguages = new HashMap<>();*/

    @Size(max=50)
    private String unitNameEN;

    @Size(max=50)
    private String unitNameRU;

    @Size(max=50)
    private String unitNameDE;

    @Size(max=50)
    private String nameEN;

    @Size(max=50)
    private String nameRU;

    @Size(max=50)
    private String nameDE;

    /*   @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "i18n_filter_point_name", foreignKey = @ForeignKey(name = "fk_i18n_FilterPoint_name"), joinColumns = @JoinColumn(name = "id"))
    @MapKeyColumn(name = "locale")
    @Column(name = "text")
    private Map<LanguageEnum, String> nameLanguages = new HashMap<>();*/

//    @NotNull
//    private String unitName;
//    @NotNull
//    @Size(max=30)
//    private String name;

    private BigDecimal minValue;

    private BigDecimal maxValue;
/*
    @ManyToMany(mappedBy = "filterPointSet")
    private Set<Cat> categorySet = new HashSet<>();
*/  @NotNull
    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Cat category;

    @OneToMany(mappedBy="filterPoint")
    private List<CategoryFilterProduct> categoryFilterProductList = new ArrayList<>();

    @OneToMany(mappedBy="filterPoint")
    private List<FilterOption> filterOptionList = new ArrayList<>();

    public FilterPoint (Consumer<FilterPoint> builder){
        builder.accept(this);
    }

    public FilterPoint (Integer id){
        super.id = id;
    }

    public FilterPoint() {
        super();
    }

    public void addCategoryFilterProduct(CategoryFilterProduct categoryFilterProduct) {
        this.categoryFilterProductList.add(categoryFilterProduct);
    }

    public void addName(String name, LanguageEnum language) {
        if (LanguageEnum.EN.equals(language)) {
            this.nameEN = name;
        } else if (LanguageEnum.RU.equals(language)) {
            this.nameRU = name;
        } else if (LanguageEnum.DE.equals(language)) {
            this.nameDE = name;
        }
    }

    public String getName(LanguageEnum language) {
        if (LanguageEnum.EN.equals(language)) {
            return this.nameEN;
        } else if (LanguageEnum.RU.equals(language)) {
            return this.nameRU;
        } else if (LanguageEnum.DE.equals(language)) {
            return this.nameDE;
        }
        return null;
    }

    public void addUnitName(String name, LanguageEnum language) {
        if (LanguageEnum.EN.equals(language)) {
            this.unitNameEN = name;
        } else if (LanguageEnum.RU.equals(language)) {
            this.unitNameRU = name;
        } else if (LanguageEnum.DE.equals(language)) {
            this.unitNameDE = name;
        }
    }

    public String getUnitName(LanguageEnum language) {
        if (LanguageEnum.EN.equals(language)) {
            return this.unitNameEN;
        } else if (LanguageEnum.RU.equals(language)) {
            return this.unitNameRU;
        } else if (LanguageEnum.DE.equals(language)) {
            return this.unitNameDE;
        }
        return null;
    }

    @Override
    public String toString() {
        return "FilterPoint{" +
                "id=" + id +
                ", inputType=" + inputType +
                ", Cat=" + category.getId() +
                ", unitName='" + category.getAlias() + "\'" +
                ", Cat='" + (category.getId() > 0 ?  "+" : "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++") + "\'" +
//                ", name='" + name + '\'' +
                '}';
    }
}

