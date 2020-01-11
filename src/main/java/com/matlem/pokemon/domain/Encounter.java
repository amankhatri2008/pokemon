package com.matlem.pokemon.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Encounter.
 */
@Entity
@Table(name = "encounter")
public class Encounter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attack_probability")
    private Float attackProbability;

    @Column(name = "attack_timer")
    private Float attackTimer;

    @Column(name = "base_flee_rate")
    private Float baseFleeRate;

    @Column(name = "base_capture_rate")
    private Float baseCaptureRate;

    @Column(name = "camera_distance")
    private Float cameraDistance;

    @Column(name = "collision_radius")
    private Float collisionRadius;

    @Column(name = "dodge_distance")
    private Float dodgeDistance;

    @Column(name = "dodge_probability")
    private Float dodgeProbability;

    @Column(name = "jump_time")
    private Float jumpTime;

    @Column(name = "max_pokemon_action_frequency")
    private Float maxPokemonActionFrequency;

    @Column(name = "min_pokemon_action_frequency")
    private Float minPokemonActionFrequency;

    @ManyToOne
    @JsonIgnoreProperties("encounters")
    private EncounterType encounterType;

    @ManyToOne
    @JsonIgnoreProperties("encounters")
    private Gender gender;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAttackProbability() {
        return attackProbability;
    }

    public Encounter attackProbability(Float attackProbability) {
        this.attackProbability = attackProbability;
        return this;
    }

    public void setAttackProbability(Float attackProbability) {
        this.attackProbability = attackProbability;
    }

    public Float getAttackTimer() {
        return attackTimer;
    }

    public Encounter attackTimer(Float attackTimer) {
        this.attackTimer = attackTimer;
        return this;
    }

    public void setAttackTimer(Float attackTimer) {
        this.attackTimer = attackTimer;
    }

    public Float getBaseFleeRate() {
        return baseFleeRate;
    }

    public Encounter baseFleeRate(Float baseFleeRate) {
        this.baseFleeRate = baseFleeRate;
        return this;
    }

    public void setBaseFleeRate(Float baseFleeRate) {
        this.baseFleeRate = baseFleeRate;
    }

    public Float getBaseCaptureRate() {
        return baseCaptureRate;
    }

    public Encounter baseCaptureRate(Float baseCaptureRate) {
        this.baseCaptureRate = baseCaptureRate;
        return this;
    }

    public void setBaseCaptureRate(Float baseCaptureRate) {
        this.baseCaptureRate = baseCaptureRate;
    }

    public Float getCameraDistance() {
        return cameraDistance;
    }

    public Encounter cameraDistance(Float cameraDistance) {
        this.cameraDistance = cameraDistance;
        return this;
    }

    public void setCameraDistance(Float cameraDistance) {
        this.cameraDistance = cameraDistance;
    }

    public Float getCollisionRadius() {
        return collisionRadius;
    }

    public Encounter collisionRadius(Float collisionRadius) {
        this.collisionRadius = collisionRadius;
        return this;
    }

    public void setCollisionRadius(Float collisionRadius) {
        this.collisionRadius = collisionRadius;
    }

    public Float getDodgeDistance() {
        return dodgeDistance;
    }

    public Encounter dodgeDistance(Float dodgeDistance) {
        this.dodgeDistance = dodgeDistance;
        return this;
    }

    public void setDodgeDistance(Float dodgeDistance) {
        this.dodgeDistance = dodgeDistance;
    }

    public Float getDodgeProbability() {
        return dodgeProbability;
    }

    public Encounter dodgeProbability(Float dodgeProbability) {
        this.dodgeProbability = dodgeProbability;
        return this;
    }

    public void setDodgeProbability(Float dodgeProbability) {
        this.dodgeProbability = dodgeProbability;
    }

    public Float getJumpTime() {
        return jumpTime;
    }

    public Encounter jumpTime(Float jumpTime) {
        this.jumpTime = jumpTime;
        return this;
    }

    public void setJumpTime(Float jumpTime) {
        this.jumpTime = jumpTime;
    }

    public Float getMaxPokemonActionFrequency() {
        return maxPokemonActionFrequency;
    }

    public Encounter maxPokemonActionFrequency(Float maxPokemonActionFrequency) {
        this.maxPokemonActionFrequency = maxPokemonActionFrequency;
        return this;
    }

    public void setMaxPokemonActionFrequency(Float maxPokemonActionFrequency) {
        this.maxPokemonActionFrequency = maxPokemonActionFrequency;
    }

    public Float getMinPokemonActionFrequency() {
        return minPokemonActionFrequency;
    }

    public Encounter minPokemonActionFrequency(Float minPokemonActionFrequency) {
        this.minPokemonActionFrequency = minPokemonActionFrequency;
        return this;
    }

    public void setMinPokemonActionFrequency(Float minPokemonActionFrequency) {
        this.minPokemonActionFrequency = minPokemonActionFrequency;
    }

    public EncounterType getEncounterType() {
        return encounterType;
    }

    public Encounter encounterType(EncounterType encounterType) {
        this.encounterType = encounterType;
        return this;
    }

    public void setEncounterType(EncounterType encounterType) {
        this.encounterType = encounterType;
    }

    public Gender getGender() {
        return gender;
    }

    public Encounter gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Encounter)) {
            return false;
        }
        return id != null && id.equals(((Encounter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Encounter{" +
            "id=" + getId() +
            ", attackProbability=" + getAttackProbability() +
            ", attackTimer=" + getAttackTimer() +
            ", baseFleeRate=" + getBaseFleeRate() +
            ", baseCaptureRate=" + getBaseCaptureRate() +
            ", cameraDistance=" + getCameraDistance() +
            ", collisionRadius=" + getCollisionRadius() +
            ", dodgeDistance=" + getDodgeDistance() +
            ", dodgeProbability=" + getDodgeProbability() +
            ", jumpTime=" + getJumpTime() +
            ", maxPokemonActionFrequency=" + getMaxPokemonActionFrequency() +
            ", minPokemonActionFrequency=" + getMinPokemonActionFrequency() +
            "}";
    }
}
