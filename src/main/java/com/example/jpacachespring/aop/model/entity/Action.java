package com.example.jpacachespring.aop.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

//@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Action extends AbstractEntity implements Serializable {

    @NotNull
    private String name;

    @NotNull
    @ManyToOne
    private Shop shop;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ActionTypeEnum type;

    @NotNull
    private boolean towardFriend;

    @NotNull
    private boolean accommodateSum = true;

    private String description;

    @OneToMany(mappedBy="action")
    private List<Cashback> cashbackList = new ArrayList<>();

//    @OneToMany(mappedBy="action")
//    private List<CashbackWriteOff> cashbackWriteOffList = new ArrayList<>();

    @OneToMany(mappedBy="currentCreatingAction")
    private List<Shop> currentCreatingActionShopList = new ArrayList<>();
/*
    @OneToMany(mappedBy="action")
    private List<CashbackWriteOff> cashbackWriteOffList = new ArrayList<>();*/

//    @OneToMany(mappedBy="sourceAtAction")
//    private List<AbstractGroupEntity> productSourceList = new ArrayList<>();
//
//    @OneToMany(mappedBy="targetAtAction")
//    private List<AbstractGroupEntity> productTargetList = new ArrayList<>();

    @ManyToOne
    private AbstractGroupEntity productSource;

    @ManyToOne
    private AbstractGroupEntity productTarget;

    //Введите размер уровня суммы
//    @Getter(AccessLevel.NONE)
    private String levelSumString;
    //Введите в % размер начисляемого кэшбека
//    @Getter(AccessLevel.NONE)
    private String levelRatePreviousPurchaseList;

    //"Введите в % максимальную долю списания кэшбека в стоимости последуюшей покупке"
    private Integer rateFuturePurchase;
    //Введите в % размер кешбека для партнера
    private Integer rateFriendFuturePurchase;

    private Integer numberCoupon;

    private boolean active = false;

    public Action(Consumer<Action> builder){
        builder.accept(this);
    }

    public Action(Integer id){
        super.id = id;
    }

    public Action() {
        super();
    }

//    public void setLevelSumList(List<BigDecimal> levelSumList) {
//        List<String> strings = new ArrayList<>(levelSumList.size());
//        for (BigDecimal levelSum : levelSumList) {
//            strings.add(levelSum.toString());
//        }
//        levelSumString = String.join(":", strings);
//    }

//    public List<BigDecimal> getLevelSumList() {
//
//        List<BigDecimal> result = new ArrayList<>();
//        if (levelSumString != null) {
//            String[] arr = levelSumString.split(":");
//            for (String str : arr) {
//                result.add(BigDecimal.valueOf(Structures.parseLong(str)));
//            }
//        }
//        return result;
//    }

    public List<Integer> accessLevelRatePreviousPurchaseList() {

        List<Integer> result = new ArrayList<>();
        String[] arr = levelRatePreviousPurchaseList.split(":");
        for (String str : arr) {
            result.add(Integer.valueOf(str));
        }
        return result;
    }

//    public void addProductSource(AbstractGroupEntity abstractGroupEntity){
//        productSourceList.add(abstractGroupEntity);
//    }
//
//    public void addProductTarget(AbstractGroupEntity abstractGroupEntity){
//        productTargetList.add(abstractGroupEntity);
//    }

    @Override
    public String toString() {
        return "Action{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", towardFriend=" + towardFriend +
                ", accommodateSum=" + accommodateSum +
                ", description='" + description + '\'' +
                ", levelSumString='" + levelSumString + '\'' +
                ", levelRatePreviousPurchaseList='" + levelRatePreviousPurchaseList + '\'' +
                ", rateFuturePurchase=" + rateFuturePurchase +
                ", rateFriendFuturePurchase=" + rateFriendFuturePurchase +
                ", numberCoupon=" + numberCoupon +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Action)) return false;
        if (!super.equals(o)) return false;
        Action action = (Action) o;
        return super.id.equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}

