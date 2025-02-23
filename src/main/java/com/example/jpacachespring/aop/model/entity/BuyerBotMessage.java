package com.example.jpacachespring.aop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

//@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
public class BuyerBotMessage extends AbstractEntity  implements Serializable {

    @NotNull
    @ManyToOne
    private BuyerBot buyerBot;

    @NotNull
    /*@OneToOne(fetch = FetchType.LAZY)
    @MapsId*/
    private Integer/*Level*/ valuableLevel;

    /*@OneToOne(fetch = FetchType.LAZY)
    @MapsId*/
    private Integer/*Message*/ receivedMessage;

    public BuyerBotMessage(Consumer<BuyerBotMessage> builder){
        builder.accept(this);
    }

    public BuyerBotMessage() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BuyerBotMessage)) return false;
        if (!super.equals(o)) return false;
        BuyerBotMessage action = (BuyerBotMessage) o;
        return super.id.equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}

