package com.example.jpacachespring.aop.model.entity;

import com.example.jpacachespring.aop.model.entity.telegram.Users;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

//@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class BuyerBot extends AbstractEntity  implements Serializable {

    @NotNull
    @ManyToOne
    private Bot bot;

    @NotNull
    @ManyToOne
    private Users users;

    @OneToMany(mappedBy="buyerBot")
    private List<BuyerBotMessage> buyerBotMessageList = new ArrayList<>();

    public BuyerBot(Consumer<BuyerBot> builder){
        builder.accept(this);
    }

    public BuyerBot() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BuyerBot)) return false;
        if (!super.equals(o)) return false;
        BuyerBot action = (BuyerBot) o;
        return super.id.equals(action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}

