package com.buscapecas.app.catalog.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "specification",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_specification",
        columnNames = {"part_id", "name"}
    )
)
public class Specification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "part_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_specification_part"))
    private Part part;

    @Column(name = "name", nullable = false, length = 180)
    private String name;

    @Column(name = "value", length = 300)
    private String value;

    protected Specification() {
    }

    public Long getId() {
        return id;
    }

    public Part getPart() {
        return part;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
