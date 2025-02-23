package com.example.jpacachespring.aop.model.entity.telegram;

import com.example.jpacachespring.aop.model.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * @author andrey.semenov
 */
@Entity
@Getter
@Setter
@Table(name = "button_row",
        indexes = {
                //@Index(name = "IDX_BUTTONROW_COL_ID", columnList = "id"),
                @Index(name = "IDX_BUTTONROW_COL_LEVEL", columnList = "level_id")})
public class ButtonRow extends AbstractEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Level level;

    @OneToMany(mappedBy = "buttonRow"/*, cascade = CascadeType.MERGE*/, fetch = FetchType.EAGER)
    //@LazyCollection(LazyCollectionOption.FALSE)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Button> buttonList = new ArrayList<>();

    public ButtonRow(Level level) {
        this.level = level;
    }

    public ButtonRow() {
        super();
    }

    /*public ButtonRow(Level level, List<Button> buttonList) {
        this.level = level;
        this.buttonList = buttonList;
    }*/

    public void add(Button button) {
        buttonList.add(button);
    }

    @Override
    public String toString() {
        return "ButtonRow{" +
                "id=" + id +
                ", buttonList=" + buttonList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ButtonRow)) return false;
        if (!super.equals(o)) return false;
        ButtonRow buttonRow = (ButtonRow) o;
        return super.id.equals(buttonRow.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}
