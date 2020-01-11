package com.matlem.pokemon.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;


import java.io.Serializable;

/**
 * A Types.
 */
@Entity
@Table(name = "types")
public class Types implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "name_id")
    private String nameId;

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

    public Types name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameId() {
        return nameId;
    }

    public Types nameId(String nameId) {
        this.nameId = nameId;
        return this;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Types)) {
            return false;
        }
        return id != null && id.equals(((Types) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Types{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nameId='" + getNameId() + "'" +
            "}";
    }
}
