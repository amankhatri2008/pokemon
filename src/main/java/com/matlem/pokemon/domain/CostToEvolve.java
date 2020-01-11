package com.matlem.pokemon.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CostToEvolve.
 */
@Entity
@Table(name = "cost_to_evolve")
public class CostToEvolve implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candy_cost")
    private Integer candyCost;

    @Column(name = "km_buddy_distance")
    private Integer kmBuddyDistance;

    @ManyToOne
    @JsonIgnoreProperties("costToEvolves")
    private EvolutionType evolutionType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCandyCost() {
        return candyCost;
    }

    public CostToEvolve candyCost(Integer candyCost) {
        this.candyCost = candyCost;
        return this;
    }

    public void setCandyCost(Integer candyCost) {
        this.candyCost = candyCost;
    }

    public Integer getKmBuddyDistance() {
        return kmBuddyDistance;
    }

    public CostToEvolve kmBuddyDistance(Integer kmBuddyDistance) {
        this.kmBuddyDistance = kmBuddyDistance;
        return this;
    }

    public void setKmBuddyDistance(Integer kmBuddyDistance) {
        this.kmBuddyDistance = kmBuddyDistance;
    }

    public EvolutionType getEvolutionType() {
        return evolutionType;
    }

    public CostToEvolve evolutionType(EvolutionType evolutionType) {
        this.evolutionType = evolutionType;
        return this;
    }

    public void setEvolutionType(EvolutionType evolutionType) {
        this.evolutionType = evolutionType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CostToEvolve)) {
            return false;
        }
        return id != null && id.equals(((CostToEvolve) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CostToEvolve{" +
            "id=" + getId() +
            ", candyCost=" + getCandyCost() +
            ", kmBuddyDistance=" + getKmBuddyDistance() +
            "}";
    }
}
