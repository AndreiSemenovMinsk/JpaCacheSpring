package com.example.jpacachespring.aop.model.entity;

import com.example.jpacachespring.aop.model.entity.telegram.Level;
import com.example.jpacachespring.aop.model.entity.telegram.Users;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Bot extends AbstractEntity  implements Serializable {

    private String name = "Default Taxi Bot";

    //TODO make one to one?
    @NotNull/*@ManyToOne*/
    @OneToOne
    private Shop shop;

    @OneToMany(mappedBy="bot")
    private List<BuyerBot> buyerBotList = new ArrayList<>();

    @OneToMany(mappedBy="currentChangingBot", fetch = FetchType.LAZY)
    private List<Users> currentChangingBotUsersList = new ArrayList<>();
/*
    @OneToMany(mappedBy="bot")
    private List<BuyerBotMessage> buyerBotMessageList;*/

    /*@OneToOne
    @MapsId
    @JoinColumn(name = "level_initial_id")*/
    private Integer initialLevel;

    @OneToMany(mappedBy="bot", fetch = FetchType.LAZY)
    private List<Level> levelList;

    public Bot(Consumer<Bot> builder){
        builder.accept(this);
    }

    public Bot() {

    }

    @Override
    public String toString() {
        return "Bot{" +
                "id=" + id +
                //", buyerBotList=" + buyerBotList +
                ", initialLevel=" + initialLevel +
                //", levelList=" + levelList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bot)) return false;
        if (!super.equals(o)) return false;
        Bot action = (Bot) o;
        return super.id.equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}

