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
    name = "cross_reference",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_cross_reference",
        columnNames = {"part_id", "reference_brand", "reference_code_norm"}
    ),
    indexes = @Index(name = "ix_cross_reference_code", columnList = "reference_code_norm")
)
public class CrossReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "part_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_cross_reference_part"))
    private Part part;

    @Column(name = "reference_brand", nullable = false, length = 120)
    private String referenceBrand;

    @Column(name = "reference_code", nullable = false, length = 80)
    private String referenceCode;

    @Column(name = "reference_code_norm", nullable = false, length = 80)
    private String referenceCodeNorm;

    protected CrossReference() {
    }

    public Long getId() {
        return id;
    }

    public Part getPart() {
        return part;
    }

    public String getReferenceBrand() {
        return referenceBrand;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public String getReferenceCodeNorm() {
        return referenceCodeNorm;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public void setReferenceBrand(String referenceBrand) {
        this.referenceBrand = referenceBrand;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public void setReferenceCodeNorm(String referenceCodeNorm) {
        this.referenceCodeNorm = referenceCodeNorm;
    }
}
