package com.matlem.pokemon.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Pokemon.
 */
@Entity
@Table(name = "pokemon")
public class Pokemon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "animation_time_0")
    private Float animationTime0;

    @Column(name = "animation_time_1")
    private Float animationTime1;

    @Column(name = "animation_time_2")
    private Float animationTime2;

    @Column(name = "animation_time_3")
    private Float animationTime3;

    @Column(name = "animation_time_4")
    private Float animationTime4;

    @Column(name = "animation_time_5")
    private Float animationTime5;

    @Column(name = "animation_time_6")
    private Float animationTime6;

    @Column(name = "animation_time_7")
    private Float animationTime7;

    @Column(name = "animation_time_8")
    private Float animationTime8;

    @Column(name = "height")
    private Float height;

    @Column(name = "model_height")
    private Float modelHeight;

    @Column(name = "km_buddy_distance")
    private Float kmBuddyDistance;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "model_scale")
    private Float modelScale;

    @Column(name = "max_cp")
    private Float maxCP;

    @Column(name = "cylinder_ground")
    private Float cylinderGround;

    @Column(name = "cylinder_radius")
    private Float cylinderRadius;

    @Column(name = "disk_radius")
    private Float diskRadius;

    @Column(name = "jhi_key")
    private String key;

    @Column(name = "cinematic_moves_id")
    private Integer cinematicMovesId;

    @Column(name = "base_attack")
    private Integer baseAttack;

    @Column(name = "base_defense")
    private Integer baseDefense;

    @Column(name = "base_stamina")
    private Integer baseStamina;

    @ManyToOne
    @JsonIgnoreProperties("buddySizes")
    private BuddySize buddySize;

    @ManyToMany
    @JoinTable(name = "pokemon_cinematic_moves_many",
               joinColumns = @JoinColumn(name = "pokemon_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "cinematic_moves_many_id", referencedColumnName = "id"))
    private Set<CinematicMoves> cinematicMovesManies = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "pokemon_quick_moves_many",
               joinColumns = @JoinColumn(name = "pokemon_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "quick_moves_many_id", referencedColumnName = "id"))
    private Set<QuickMoves> quickMovesManies = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("families")
    private Family family;

    @ManyToMany
    @JoinTable(name = "pokemon_form_many",
               joinColumns = @JoinColumn(name = "pokemon_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "form_many_id", referencedColumnName = "id"))
    private Set<Form> formManies = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "pokemon_user_many",
               joinColumns = @JoinColumn(name = "pokemon_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "user_many_id", referencedColumnName = "id"))
    private Set<User> userManies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Pokemon name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getAnimationTime0() {
        return animationTime0;
    }

    public Pokemon animationTime0(Float animationTime0) {
        this.animationTime0 = animationTime0;
        return this;
    }

    public void setAnimationTime0(Float animationTime0) {
        this.animationTime0 = animationTime0;
    }

    public Float getAnimationTime1() {
        return animationTime1;
    }

    public Pokemon animationTime1(Float animationTime1) {
        this.animationTime1 = animationTime1;
        return this;
    }

    public void setAnimationTime1(Float animationTime1) {
        this.animationTime1 = animationTime1;
    }

    public Float getAnimationTime2() {
        return animationTime2;
    }

    public Pokemon animationTime2(Float animationTime2) {
        this.animationTime2 = animationTime2;
        return this;
    }

    public void setAnimationTime2(Float animationTime2) {
        this.animationTime2 = animationTime2;
    }

    public Float getAnimationTime3() {
        return animationTime3;
    }

    public Pokemon animationTime3(Float animationTime3) {
        this.animationTime3 = animationTime3;
        return this;
    }

    public void setAnimationTime3(Float animationTime3) {
        this.animationTime3 = animationTime3;
    }

    public Float getAnimationTime4() {
        return animationTime4;
    }

    public Pokemon animationTime4(Float animationTime4) {
        this.animationTime4 = animationTime4;
        return this;
    }

    public void setAnimationTime4(Float animationTime4) {
        this.animationTime4 = animationTime4;
    }

    public Float getAnimationTime5() {
        return animationTime5;
    }

    public Pokemon animationTime5(Float animationTime5) {
        this.animationTime5 = animationTime5;
        return this;
    }

    public void setAnimationTime5(Float animationTime5) {
        this.animationTime5 = animationTime5;
    }

    public Float getAnimationTime6() {
        return animationTime6;
    }

    public Pokemon animationTime6(Float animationTime6) {
        this.animationTime6 = animationTime6;
        return this;
    }

    public void setAnimationTime6(Float animationTime6) {
        this.animationTime6 = animationTime6;
    }

    public Float getAnimationTime7() {
        return animationTime7;
    }

    public Pokemon animationTime7(Float animationTime7) {
        this.animationTime7 = animationTime7;
        return this;
    }

    public void setAnimationTime7(Float animationTime7) {
        this.animationTime7 = animationTime7;
    }

    public Float getAnimationTime8() {
        return animationTime8;
    }

    public Pokemon animationTime8(Float animationTime8) {
        this.animationTime8 = animationTime8;
        return this;
    }

    public void setAnimationTime8(Float animationTime8) {
        this.animationTime8 = animationTime8;
    }

    public Float getHeight() {
        return height;
    }

    public Pokemon height(Float height) {
        this.height = height;
        return this;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getModelHeight() {
        return modelHeight;
    }

    public Pokemon modelHeight(Float modelHeight) {
        this.modelHeight = modelHeight;
        return this;
    }

    public void setModelHeight(Float modelHeight) {
        this.modelHeight = modelHeight;
    }

    public Float getKmBuddyDistance() {
        return kmBuddyDistance;
    }

    public Pokemon kmBuddyDistance(Float kmBuddyDistance) {
        this.kmBuddyDistance = kmBuddyDistance;
        return this;
    }

    public void setKmBuddyDistance(Float kmBuddyDistance) {
        this.kmBuddyDistance = kmBuddyDistance;
    }

    public Float getWeight() {
        return weight;
    }

    public Pokemon weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getModelScale() {
        return modelScale;
    }

    public Pokemon modelScale(Float modelScale) {
        this.modelScale = modelScale;
        return this;
    }

    public void setModelScale(Float modelScale) {
        this.modelScale = modelScale;
    }

    public Float getMaxCP() {
        return maxCP;
    }

    public Pokemon maxCP(Float maxCP) {
        this.maxCP = maxCP;
        return this;
    }

    public void setMaxCP(Float maxCP) {
        this.maxCP = maxCP;
    }

    public Float getCylinderGround() {
        return cylinderGround;
    }

    public Pokemon cylinderGround(Float cylinderGround) {
        this.cylinderGround = cylinderGround;
        return this;
    }

    public void setCylinderGround(Float cylinderGround) {
        this.cylinderGround = cylinderGround;
    }

    public Float getCylinderRadius() {
        return cylinderRadius;
    }

    public Pokemon cylinderRadius(Float cylinderRadius) {
        this.cylinderRadius = cylinderRadius;
        return this;
    }

    public void setCylinderRadius(Float cylinderRadius) {
        this.cylinderRadius = cylinderRadius;
    }

    public Float getDiskRadius() {
        return diskRadius;
    }

    public Pokemon diskRadius(Float diskRadius) {
        this.diskRadius = diskRadius;
        return this;
    }

    public void setDiskRadius(Float diskRadius) {
        this.diskRadius = diskRadius;
    }

    public String getKey() {
        return key;
    }

    public Pokemon key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getCinematicMovesId() {
        return cinematicMovesId;
    }

    public Pokemon cinematicMovesId(Integer cinematicMovesId) {
        this.cinematicMovesId = cinematicMovesId;
        return this;
    }

    public void setCinematicMovesId(Integer cinematicMovesId) {
        this.cinematicMovesId = cinematicMovesId;
    }

    public Integer getBaseAttack() {
        return baseAttack;
    }

    public Pokemon baseAttack(Integer baseAttack) {
        this.baseAttack = baseAttack;
        return this;
    }

    public void setBaseAttack(Integer baseAttack) {
        this.baseAttack = baseAttack;
    }

    public Integer getBaseDefense() {
        return baseDefense;
    }

    public Pokemon baseDefense(Integer baseDefense) {
        this.baseDefense = baseDefense;
        return this;
    }

    public void setBaseDefense(Integer baseDefense) {
        this.baseDefense = baseDefense;
    }

    public Integer getBaseStamina() {
        return baseStamina;
    }

    public Pokemon baseStamina(Integer baseStamina) {
        this.baseStamina = baseStamina;
        return this;
    }

    public void setBaseStamina(Integer baseStamina) {
        this.baseStamina = baseStamina;
    }

    public BuddySize getBuddySize() {
        return buddySize;
    }

    public Pokemon buddySize(BuddySize buddySize) {
        this.buddySize = buddySize;
        return this;
    }

    public void setBuddySize(BuddySize buddySize) {
        this.buddySize = buddySize;
    }

    public Set<CinematicMoves> getCinematicMovesManies() {
        return cinematicMovesManies;
    }

    public Pokemon cinematicMovesManies(Set<CinematicMoves> cinematicMoves) {
        this.cinematicMovesManies = cinematicMoves;
        return this;
    }

    public Pokemon addCinematicMovesMany(CinematicMoves cinematicMoves) {
        this.cinematicMovesManies.add(cinematicMoves);

        return this;
    }

    public Pokemon removeCinematicMovesMany(CinematicMoves cinematicMoves) {
        this.cinematicMovesManies.remove(cinematicMoves);

        return this;
    }

    public void setCinematicMovesManies(Set<CinematicMoves> cinematicMoves) {
        this.cinematicMovesManies = cinematicMoves;
    }

    public Set<QuickMoves> getQuickMovesManies() {
        return quickMovesManies;
    }

    public Pokemon quickMovesManies(Set<QuickMoves> quickMoves) {
        this.quickMovesManies = quickMoves;
        return this;
    }

    public Pokemon addQuickMovesMany(QuickMoves quickMoves) {
        this.quickMovesManies.add(quickMoves);

        return this;
    }

    public Pokemon removeQuickMovesMany(QuickMoves quickMoves) {
        this.quickMovesManies.remove(quickMoves);

        return this;
    }

    public void setQuickMovesManies(Set<QuickMoves> quickMoves) {
        this.quickMovesManies = quickMoves;
    }

    public Family getFamily() {
        return family;
    }

    public Pokemon family(Family family) {
        this.family = family;
        return this;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Set<Form> getFormManies() {
        return formManies;
    }

    public Pokemon formManies(Set<Form> forms) {
        this.formManies = forms;
        return this;
    }

    public Pokemon addFormMany(Form form) {
        this.formManies.add(form);

        return this;
    }

    public Pokemon removeFormMany(Form form) {
        this.formManies.remove(form);

        return this;
    }

    public void setFormManies(Set<Form> forms) {
        this.formManies = forms;
    }

    public Set<User> getUserManies() {
        return userManies;
    }

    public Pokemon userManies(Set<User> users) {
        this.userManies = users;
        return this;
    }

    public Pokemon addUserMany(User user) {
        this.userManies.add(user);
        return this;
    }

    public Pokemon removeUserMany(User user) {
        this.userManies.remove(user);
        return this;
    }

    public void setUserManies(Set<User> users) {
        this.userManies = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pokemon)) {
            return false;
        }
        return id != null && id.equals(((Pokemon) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", animationTime0=" + getAnimationTime0() +
            ", animationTime1=" + getAnimationTime1() +
            ", animationTime2=" + getAnimationTime2() +
            ", animationTime3=" + getAnimationTime3() +
            ", animationTime4=" + getAnimationTime4() +
            ", animationTime5=" + getAnimationTime5() +
            ", animationTime6=" + getAnimationTime6() +
            ", animationTime7=" + getAnimationTime7() +
            ", animationTime8=" + getAnimationTime8() +
            ", height=" + getHeight() +
            ", modelHeight=" + getModelHeight() +
            ", kmBuddyDistance=" + getKmBuddyDistance() +
            ", weight=" + getWeight() +
            ", modelScale=" + getModelScale() +
            ", maxCP=" + getMaxCP() +
            ", cylinderGround=" + getCylinderGround() +
            ", cylinderRadius=" + getCylinderRadius() +
            ", diskRadius=" + getDiskRadius() +
            ", key='" + getKey() + "'" +
            ", cinematicMovesId=" + getCinematicMovesId() +
            ", baseAttack=" + getBaseAttack() +
            ", baseDefense=" + getBaseDefense() +
            ", baseStamina=" + getBaseStamina() +
            "}";
    }
}
