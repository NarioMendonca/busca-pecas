package com.buscapecas.app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "vehicle",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_vehicle_natural",
        columnNames = {"name_norm", "trim_norm", "engine_desc"}
    ),
    indexes = @Index(name = "ix_vehicle_lookup", columnList = "name_norm, engine_cc")
)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "name_norm", nullable = false, length = 120)
    private String nameNorm;

    @Column(name = "trim", nullable = false, length = 180)
    private String trim = "";

    @Column(name = "trim_norm", nullable = false, length = 180)
    private String trimNorm = "";
    
    @Column(name = "engine_desc", nullable = false, length = 180)
    private String engineDesc = "";

    @Column(name = "engine_cc")
    private Integer engineCc;

    protected Vehicle() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameNorm() {
        return nameNorm;
    }

    public String getTrim() {
        return trim;
    }

    public String getTrimNorm() {
        return trimNorm;
    }

    public String getEngineDesc() {
        return engineDesc;
    }

    public Integer getEngineCc() {
        return engineCc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameNorm(String nameNorm) {
        this.nameNorm = nameNorm;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public void setTrimNorm(String trimNorm) {
        this.trimNorm = trimNorm;
    }

    public void setEngineDesc(String engineDesc) {
        this.engineDesc = engineDesc;
    }

    public void setEngineCc(Integer engineCc) {
        this.engineCc = engineCc;
    }
}
