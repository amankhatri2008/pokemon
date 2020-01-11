package com.matlem.pokemon.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Gender.
 */
@Entity
@Table(name = "gender")
public class Gender implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "male_percent", precision = 21, scale = 2, nullable = false, unique = true)
    private BigDecimal malePercent;

    @NotNull
    @Column(name = "female_percent", precision = 21, scale = 2, nullable = false, unique = true)
    private BigDecimal femalePercent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMalePercent() {
        return malePercent;
    }

    public Gender malePercent(BigDecimal malePercent) {
        this.malePercent = malePercent;
        return this;
    }

    public void setMalePercent(BigDecimal malePercent) {
        this.malePercent = malePercent;
    }

    public BigDecimal getFemalePercent() {
        return femalePercent;
    }

    public Gender femalePercent(BigDecimal femalePercent) {
        this.femalePercent = femalePercent;
        return this;
    }

    public void setFemalePercent(BigDecimal femalePercent) {
        this.femalePercent = femalePercent;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gender)) {
            return false;
        }
        return id != null && id.equals(((Gender) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Gender{" +
            "id=" + getId() +
            ", malePercent=" + getMalePercent() +
            ", femalePercent=" + getFemalePercent() +
            "}";
    }
}
