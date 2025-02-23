package com.example.jpacachespring.aop.model.entity.telegram;

import com.example.jpacachespring.aop.model.entity.*;
import com.example.jpacachespring.aop.model.entity.category.LanguageEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author andrey.semenov
 */
@Entity
@Getter
@Setter
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "UC_USER_COL_LOGIN", columnNames = {"login"}),
                @UniqueConstraint(name = "UC_USER_COL_CHAT", columnNames = {"chatId"}),
                @UniqueConstraint(name = "UC_USER_COL_VIBER", columnNames = {"viber"})},
        indexes = {
                //@Index(name = "IDX_USER_COL_ID", columnList = "id"),
                @Index(name = "IDX_USER_COL_LOGIN", columnList = "login"),
                @Index(name = "IDX_USER_COL_CHAT", columnList = "chatId"),
                @Index(name = "IDX_USER_COL_VIBER", columnList = "viber")})
public class Users extends AbstractEntity {

    private Long expirationTimeMilliseconds;

    private String refreshToken;

    private String accessToken;

    @NotNull
    @Size(max=30)
    private String name;

    @Size(max=50)
    private String email;

    private LanguageEnum language;

    @Size(max=20)
    private String login;

    private char[] password;

    private String sessionId;

    @JsonIgnore
    private byte[] runner;

    @Size(max=30)
    private String viber;

    private char[] viberLast;

    private String viberAvatar;

    private Integer currentAdminShop;

    /*@OneToOne(mappedBy = "currentConstructShopUser",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)*/
    private Integer currentConstructShop;

    /*@OneToOne(mappedBy = "currentConversationShopUser",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)*/
    private Integer currentConversationShop;

    private Integer currentSearchAbstractGroupEntity;

    private boolean shopOwner;

    private String role;

    @OneToMany(mappedBy="buyer")
    private List<Recommendation> givenRecommendationList = new ArrayList<>();

    @OneToMany(mappedBy="friend")
    private List<Recommendation> takenRecommendationList = new ArrayList<>();

    @OneToMany(mappedBy="users")
    //@LazyCollection(LazyCollectionOption.FALSE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<BuyerBot> buyerBotList = new ArrayList<>();

    @OneToMany(mappedBy= "adminUsers")
    //@LazyCollection(LazyCollectionOption.FALSE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Shop> shopList = new ArrayList<>();

    /*@ManyToMany(mappedBy = "sellerSet")
    Set<Shop> shopSet = new HashSet<>();*/

    @ManyToOne(fetch = FetchType.LAZY)
    private Shop sellerShop;

    @OneToMany(mappedBy="buyer")
    private List<Purchase> purchaseList = new ArrayList<>();

    @OneToMany(mappedBy="users")
    private List<Basket> basketList = new ArrayList<>();

    @OneToMany(mappedBy="users")
    private List<Bookmark> bookmarksList = new ArrayList<>();

    @OneToMany(mappedBy="users")
    private List<Cashback> cashbackList = new ArrayList<>();

    @OneToMany(mappedBy="users")
    private List<CashbackWriteOff> cashbackWriteOffList = new ArrayList<>();

    @OneToMany(mappedBy="users")
    private List<ScheduleBuyer> scheduleBuyerList = new ArrayList<>();
/*
    @OneToMany(mappedBy="currentConversationUser")
    private List<Shop> currentConversationBuyerShopList = new ArrayList<>();
*/

    private Long chatId;
    //@ManyToOne(fetch = FetchType.LAZY)
    private Integer currentLevelId;
    private Integer currentLevelBeforeConfigId;
    @ManyToOne
    private Level currentLevelBeforeInterruption;
    @ManyToOne(fetch = FetchType.LAZY)
    private Bot currentChangingBot;
    @ManyToOne(fetch = FetchType.LAZY)
    private Button currentChangingButton;
    @ManyToOne(fetch = FetchType.LAZY)
    private Message currentChangingMessage;


    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Level> levels = new ArrayList<>();
/*
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Parameter> parameters = new ArrayList<>();*/

    public Users(Long chatId, String name) {
        this.chatId = chatId;
        this.name = name;
    }

    public Users(Integer id) {
        super.id = id;
    }

    public Users(Consumer<Users> builder){
        builder.accept(this);
    }

    public Users() {

    }

    public void addLevel(Level level){
        levels.add(level);
    }
/*
    public void addParameter(Parameter parameter){
        parameters.add(parameter);
    }*/


    public void addScheduleBuyer(ScheduleBuyer scheduleBuyer) {
        this.scheduleBuyerList.add(scheduleBuyer);
    }

    public void addGivenRecomendation(Recommendation recommendation) {
        this.givenRecommendationList.add(recommendation);
    }

    public void addTakenRecomendation(Recommendation recommendation) {
        this.takenRecommendationList.add(recommendation);
    }

    public void addPurchase(Purchase purchase) {
        this.purchaseList.add(purchase);
    }

    public void addBasket(Basket basket) {
        this.basketList.add(basket);
    }

    public void addBookmarks(Bookmark bookmark) {
        this.bookmarksList.add(bookmark);
    }

    public void addCashback(Cashback cashback) {
        this.cashbackList.add(cashback);
    }

    public void addCashbackWriteOff(CashbackWriteOff cashbackWriteOff) {
        this.cashbackWriteOffList.add(cashbackWriteOff);
    }

    public void addShop(Shop shop) {
        this.shopList.add(shop);
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login=" + login +
                ", password=" + Arrays.toString(password) +
                ", sessionId='" + sessionId + '\'' +
                ", currentLevelId=" + currentLevelId +
                ", currentAdminShop=" + currentAdminShop +
                ", currentConstructShop=" + currentConstructShop +
                ", currentConversationShop=" + currentConversationShop +
                ", chatId=" + chatId +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        System.out.println("(o instanceof Users)+++++++"+(o instanceof Users));
        System.out.println("super.equals(o)+++++++++++"+super.equals(o));

        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        if (!super.equals(o)) return false;
        Users users = (Users) o;

        System.out.println("super.id+++" + super.id + "+++++users.getId()+" + users.getId());
        System.out.println(super.id.equals(users.getId()));

        return super.id.equals(users.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }
}
