package com.example.jpacachespring.aop.model.entity.telegram;

import com.example.jpacachespring.aop.model.entity.AbstractEntity;
import com.example.jpacachespring.aop.model.entity.AbstractEntity;
import com.example.jpacachespring.aop.model.entity.category.LanguageEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

/**
 * @author andrey.semenov
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "message",
        indexes = {
                @Index(name = "IDX_MESSAGE_COL_ID", columnList = "id")})
public class Message extends AbstractEntity {

    @ManyToOne
    private Level level;

    private Integer levelID;

    @Size(max=160)
    private String nameEN;

    @Size(max=160)
    private String nameRU;

    @Size(max=160)
    private String nameDE;

//    private String text;
//
//    @Size(max=50)
//    private String alias;

/*    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "i18n_Message", foreignKey = @ForeignKey(name = "fk_i18n_Message"), joinColumns = @JoinColumn(name = "id"))
    @MapKeyColumn(name = "locale")
    @Column(name = "text")
    private Map<LanguageEnum, String> nameLanguages = new HashMap<>();*/

    @Lob
    private byte[] image;

    private String imageDescription;
    private Double longitude;
    private Double latitude;

    @OneToMany(mappedBy="currentChangingMessage", fetch = FetchType.LAZY)
    private List<Users> usersWithChangingMessageList = new ArrayList<>();

    public Message(Level newLevel,
                   Integer levelID,
                   String nameEN,
                   String nameRU,
                   String nameDE,
                   byte[] image,
                   String imageDescription,
                   Double longitude,
                   Double latitude) {
        this.level = newLevel;
        this.levelID = levelID;
        this.nameEN = nameEN;
        this.nameRU = nameRU;
        this.nameDE = nameDE;
        this.image = image;
        this.imageDescription = imageDescription;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Message(Level newLevel,
                   Integer levelID,
                   Map<LanguageEnum, String> nameLanguages,
                   byte[] image,
                   String imageDescription,
                   Double longitude,
                   Double latitude) {
        this.level = newLevel;
        this.levelID = levelID;
        if (nameLanguages != null) {
            nameLanguages.forEach((k, v) -> {
                addName(v, k);
            });
        }
        this.image = image;
        this.imageDescription = imageDescription;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Message(Level newLevel,
                   Integer levelID,
                   Map<LanguageEnum, String> nameLanguages,
                   byte[] image,
                   String imageDescription) {
        this.level = newLevel;
        this.levelID = levelID;
        if (nameLanguages != null) {
            nameLanguages.forEach((k, v) -> {
                addName(v, k);
            });
        }
        this.image = image;
        this.imageDescription = imageDescription;
    }

    public Message(Level level,
                   Map<LanguageEnum, String> nameLanguages) {
        this.level = level;
        nameLanguages.forEach((k, v) -> {
            addName(v, k);
        });
    }

    public Message(Level level,
                   Integer levelID,
                   Map<LanguageEnum, String> nameLanguages) {
        this.level = level;
        this.levelID = levelID;
        nameLanguages.forEach((k, v) -> {
            addName(v, k);
        });
    }

    public Message() {
        super();
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

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", nameRU='" + nameRU + "'" +
                ", nameDE='" + nameDE +
                ", nameEN='" + nameEN +
                ", imageDescription='" + imageDescription + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        if (!super.equals(o)) return false;
        Message message = (Message) o;
        return super.id.equals(message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId());
    }

    public String getText(LanguageEnum language) {
        if (LanguageEnum.EN.equals(language)) {
            return this.nameEN;
        } else if (LanguageEnum.RU.equals(language)) {
            return this.nameRU;
        } else if (LanguageEnum.DE.equals(language)) {
            return this.nameDE;
        }
        return null;
    }

    public void setText(LanguageEnum language, String name) {
        if (LanguageEnum.EN.equals(language)) {
            this.nameEN = name;
        } else if (LanguageEnum.RU.equals(language)) {
            this.nameRU = name;
        } else if (LanguageEnum.DE.equals(language)) {
            this.nameDE = name;
        }
    }
}
