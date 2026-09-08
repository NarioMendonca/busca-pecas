package com.buscapecas.app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "fitment",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_fitment",
        columnNames = {"part_id", "vehicle_id", "year_start", "year_end"}
    ),
    indexes = {
        @Index(name = "ix_fitment_vehicle_years", columnList = "vehicle_id, year_start, year_end"),
        @Index(name = "ix_fitment_part", columnList = "part_id")
    }
)
public class Fitment {

    public static final short OPEN_YEAR = 9999;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "part_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_fitment_part"))
    private Part part;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_fitment_vehicle"))
    private Vehicle vehicle;

    @Column(name = "year_start", nullable = false)
    private Short yearStart;

    @Column(name = "year_end", nullable = false)
    private Short yearEnd;

    @Column(name = "note", length = 500)
    private String note;

    protected Fitment() {
    }

    public boolean isInProduction() {
        return yearEnd != null && yearEnd == OPEN_YEAR;
    }

    public Short getDisplayYearEnd() {
        return isInProduction() ? null : yearEnd;
    }

    public boolean coversYear(int year) {
        return yearStart != null && yearEnd != null && year >= yearStart && year <= yearEnd;
    }

    public Long getId() {
        return id;
    }

    public Part getPart() {
        return part;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Short getYearStart() {
        return yearStart;
    }

    public Short getYearEnd() {
        return yearEnd;
    }

    public String getNote() {
        return note;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setYearStart(Short yearStart) {
        this.yearStart = yearStart;
    }

    public void setYearEnd(Short yearEnd) {
        this.yearEnd = yearEnd;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
