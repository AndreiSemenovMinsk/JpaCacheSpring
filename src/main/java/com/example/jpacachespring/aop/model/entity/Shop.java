package com.example.jpacachespring.aop.model.entity;

import com.example.jpacachespring.aop.model.entity.telegram.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//@EqualsAndHashCode(callSuper = false)//(callSuper = true)
@Data
@Entity
@Table(name = "shop",
        uniqueConstraints = {
                @UniqueConstraint(name = "UC_SHOP_COL_NAME", columnNames = {"name"}),
                @UniqueConstraint(name = "UC_SHOP_COL_LOGIN", columnNames = {"login"})},
        indexes = {
                //@Index(name = "IDX_SHOP_COL_ID", columnList = "id"),
                @Index(name = "IDX_SHOP_NAME_USER", columnList = "admin_users_id,name"),
                @Index(name = "IDX_SHOP_NAME", columnList = "name"),
                @Index(name = "IDX_SHOP_LAT", columnList = "lat"),
                @Index(name = "IDX_SHOP_LNG", columnList = "lng")})
public class Shop  extends AbstractEntity  implements Serializable {

    @NotNull
    @Size(max=30)
    private String name;

    @NotNull
    private Long chatId;

    //@OneToOne
    private Integer currentConstructShopUser;

    //@OneToOne
    private Long currentConversationShopUserChatId;

    private Integer currentChargeAction;

    @ManyToOne
    private Shop currentConversationShop;
    @ManyToOne
    private ShopGroup currentConversationShopGroup;
    @ManyToOne
    private Prd currentCreatingProduct;
    @ManyToOne
    private Action currentCreatingAction;

    @NotNull
    @ManyToOne
    private Users adminUsers;

    /*@ManyToMany(cascade = {
            CascadeType.MERGE
    })
    @JoinTable(name = "users_shop_set",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "admin_users_id")
    )*/
    @OneToMany(mappedBy="sellerShop")
    private List<Users> sellerSet = new ArrayList<>();

    @JsonIgnore
    private String secretId;

    @Lob
    @JsonIgnore
    @Column(name = "excel", columnDefinition="MEDIUMBLOB")
    private byte[] excel;

    @Lob
    @JsonIgnore
    private byte[] contractPDF;

    //@NotNull
    @Size(max=20)
    private String login;
    @JsonIgnore
    //@NotNull
    private char[] password;

    //@NotNull
    private String email;

    //@NotNull
    private String contacts;

    //@NotNull
    private String telSms;

    private Double lat;

    private Double lng;

    private String geo;

    //@NotNull
    private BigDecimal sarafanShare;

    //@NotNull
    private BigDecimal minBillShare;

    //@NotNull
    private BigDecimal paymentBalance;

    //@NotNull
    private BigDecimal cashbackBalance;

    @OneToOne
    private Bot bot;

    @OneToMany(mappedBy="shop")
    private List<Prd> productList = new ArrayList<>();

    @OneToMany(mappedBy="shop")
    private List<Action> actionList = new ArrayList<>();

    /*@OneToMany(mappedBy="shop")
    private List<Bot> botList = new ArrayList<>();*/
    @OneToMany(mappedBy="shop")
    private List<Basket> basketList = new ArrayList<>();

    @OneToMany(mappedBy="shop")
    private List<Cashback> cashbackList = new ArrayList<>();

    @OneToMany(mappedBy="shop")
    private List<Purchase> purchaseList = new ArrayList<>();

    @OneToMany(mappedBy="shop")
    private List<CashbackWriteOff> cashbackWriteOffList = new ArrayList<>();

    @OneToMany(mappedBy="debtor")//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Partner> debtorList = new ArrayList<>();

    @OneToMany(mappedBy="creditor")//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Partner> creditorList = new ArrayList<>();

    @OneToMany(mappedBy="currentConversationShop")
    private List<Shop> currentConversationShopShopList = new ArrayList<>();



    @OneToMany(mappedBy="shop")
    private List<CashbackShopGroup> cashbackShopGroupList = new ArrayList<>();



    private boolean active;
/*
    @OneToMany(mappedBy="currentConversationShop")
    private List<Users> currentConversationShopUserList = new ArrayList<>();
*/
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "shop_group_shop_set",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "shop_group_id")
    )
    private Set<ShopGroup> shopGroupSet = new HashSet<>();


    @OneToMany(mappedBy="shop")
    private List<Recommendation> recommendationList = new ArrayList<>();

    public Shop() {
        super();
    }


    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    //private Shablon shablon;

    //private DiscountCart;

    public Shop (Consumer<Shop> builder){
        builder.accept(this);
    }

    public Shop (Integer id){
        super.id = id;
    }

    public void addProduct(Prd product) {
        this.productList.add(product);
    }

    public void addCashback(Cashback cashback) {
        this.cashbackList.add(cashback);
    }

    public void addCashbackWriteOff(CashbackWriteOff cashbackWriteOff) {
        this.cashbackWriteOffList.add(cashbackWriteOff);
    }

    public void addDebtor(Partner debtor) {
        this.debtorList.add(debtor);
    }

    public void addCreditor(Partner creditor) {
        this.creditorList.add(creditor);
    }

    public void addRecomendation(Recommendation recommendation) {
        this.recommendationList.add(recommendation);
    }

    public void addPurchase(Purchase purchase) {
        this.purchaseList.add(purchase);
    }
/*
    public void addBot(Bot bot) {
        this.botList.add(bot);
    }*/


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Shop shop = (Shop) o;
        return super.id.equals(shop.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currentConstructShopUser=" + currentConstructShopUser +
                ", currentConversationShopUser=" + currentConversationShopUserChatId +
                ", email='" + email + '\'' +
                ", contacts='" + contacts + '\'' +
                ", telSms='" + telSms + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", sarafanShare=" + sarafanShare +
                ", minBillShare=" + minBillShare +
                ", paymentBalance=" + paymentBalance +
                ", cashbackBalance=" + cashbackBalance +
                '}';
    }
}
