package com.example.jpacachespring.aop.model.entity.telegram;

import com.example.jpacachespring.aop.model.entity.AbstractEntity;
import com.example.jpacachespring.aop.model.entity.Bot;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.stereotype.Component;

/**
 * @author andrey.semenov
 */
@Component
@Entity
@Getter
@Setter
@Table(name = "level",
        uniqueConstraints = {
                @UniqueConstraint(name = "UC_LEVEL_COL_USER_CALL_NAME", columnNames = {"callName", "users_id"})},
        indexes = {
                //@Index(name = "IDX_LEVEL_COL_ID", columnList = "id"),
                @Index(name = "IDX_LEVEL_COL_CALLNAME", columnList = "callName"),
                @Index(name = "IDX_LEVEL_COL_USER", columnList = "users_id"),
                @Index(name = "IDX_LEVEL_COL_PARENTLEVEL", columnList = "parent_level_id")})
public class Level extends AbstractEntity implements Cloneable {

    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;

    @Size(max=80)
    private String callName;

    private boolean sourceIsMessage;

    private boolean terminateBotLevel = false;

    private boolean botLevel = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private Bot bot;

    //bot.getShop().getUsers().getChatId()
    private Long chatId;

    /*@OneToOne(mappedBy = "initialLevel",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)*/
    private Integer initialLevelBot;

    /*@OneToMany(fetch = FetchType.LAZY, mappedBy="currentLevel")
    private List<Users> userWithCurrentLevelList = new ArrayList<>();*/

    @OneToMany(mappedBy="currentLevelBeforeInterruption")
    private List<Users> usersWithCurrentLevelBeforeInterruptionList = new ArrayList<>();

    /*@OneToMany(mappedBy = "parentLevelId", fetch = FetchType.LAZY)//, fetch = FetchType.EAGER)
    //@LazyCollection(LazyCollectionOption.FALSE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Singular
    private List<Level> childLevels = new ArrayList<>();*/

    //@ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "parent_level_id")
    private Integer parentLevelId;

    @OneToMany(mappedBy = "level", fetch = FetchType.LAZY)//, fetch = FetchType.EAGER)
    //@LazyCollection(LazyCollectionOption.FALSE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "level", fetch = FetchType.LAZY)//, fetch = FetchType.EAGER)
    //@LazyCollection(LazyCollectionOption.FALSE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<ButtonRow> buttonRows = new ArrayList<>();
/*
    @OneToMany(mappedBy = "level")//, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Button> buttonList = new ArrayList<>();*/

    public Level(Users users, String callName, Level parentLevel, Boolean sourceIsMessage) {
        this.users = users;
        this.callName = callName;
        this.parentLevelId = parentLevel.getId();
        this.sourceIsMessage = sourceIsMessage;
        this.terminateBotLevel = false;
        this.botLevel = false;
        /*if (parentLevelId != null) {
            parentLevelId.addChildLevel(this);
        }*/
    }

    public Level() {
        super();
    }

    public void updateLevel(Users users, String callName, Level parentLevel, Boolean sourceIsMessage) {
        this.users = users;
        this.callName = callName;
        if (parentLevel != null) {
            this.parentLevelId = parentLevel.getId();
        }
        this.sourceIsMessage = sourceIsMessage;
        /*if (parentLevel != null) {
            parentLevel.addChildLevel(this);
        }*/
    }

    public void updateLevel(Users users, String callName, Level parentLevel, Boolean sourceIsMessage, Boolean isTerminate, Boolean isBotLevel) {
        this.users = users;
        this.callName = callName;
        if (parentLevel != null) {
            this.parentLevelId = parentLevel.getId();
        }
        this.sourceIsMessage = sourceIsMessage;
        this.terminateBotLevel = isTerminate;
        this.botLevel = isBotLevel;
        /*if (parentLevel != null) {
            parentLevel.addChildLevel(this);
        }*/
    }

    public void addMessage(Message message){
        messages.add(message);
    }

    public void setMessage(Integer id, Message message){
        messages.set(id, message);
    }

    public void prependMessage(Message message){
        messages.add(0, message);
    }

    /*public void addChildLevel(Level level){
        childLevels.add(level);
    }*/
    //@DeclareWarning("do not use without saveNew - use DTO")
    public void addRow(ButtonRow buttonRow){
        buttonRows.add(buttonRow);
    }


    public Level cloneShallow() throws CloneNotSupportedException {

        Level level = (Level) super.clone();

        level.users = this.users;
        level.callName = this.callName;
        level.sourceIsMessage = this.sourceIsMessage;
        level.parentLevelId = this.parentLevelId;

        return level;
    }

    @Override
    public Level clone() throws CloneNotSupportedException {

        Level level = (Level) super.clone();

        level.users = users;
        level.callName = this.callName;
        level.sourceIsMessage = this.sourceIsMessage;
        level.parentLevelId = this.parentLevelId;
        return level;
    }

    public String getIdString(){
        return getIdString(super.id);
    }

    public static String getIdString(Integer id){
        return StringUtils.leftPad("" + id, 19, "0");
    }

    @Override
    public String toString() {
        return "Level{" +
                "id=" + id +
                ", callName='" + callName + '\'' +
                ", users.id=" + (users != null ? users.getId() : "null") +
                ", sourceIsMessage=" + sourceIsMessage +
                ", initialLevelBot=" + initialLevelBot +
                ", parentLevelId=" + parentLevelId +
                ", isBotLevel=" + botLevel +
                ", isTerminate=" + terminateBotLevel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Level)) return false;
        if (!super.equals(o)) return false;
        Level level = (Level) o;
        return super.id.equals(level.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}
