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
    name = "part",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_part_code", columnNames = "code"),
        @UniqueConstraint(name = "uk_part_code_norm", columnNames = "code_norm")
    },
    indexes = @Index(name = "ix_part_group", columnList = "product_group_norm")
)
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, length = 60)
    private String code;

    @Column(name = "code_norm", nullable = false, length = 60)
    private String codeNorm;

    @Column(name = "product_group", length = 180)
    private String productGroup;

    @Column(name = "product_group_norm", length = 180)
    private String productGroupNorm;

    @Column(name = "image_file", length = 300)
    private String imageFile;

    protected Part() {
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getCodeNorm() {
        return codeNorm;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public String getProductGroupNorm() {
        return productGroupNorm;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCodeNorm(String codeNorm) {
        this.codeNorm = codeNorm;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public void setProductGroupNorm(String productGroupNorm) {
        this.productGroupNorm = productGroupNorm;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }
}
