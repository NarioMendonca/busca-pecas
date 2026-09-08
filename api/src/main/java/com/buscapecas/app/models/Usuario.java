package com.buscapecas.app.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private UUID apiKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPlano plano;

    @Column(nullable = false)
    private int requisicoesFeitasHoje;

    @Column(nullable = false)
    private LocalDate dataUltimoReset;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void antesDeSalvar() {
        if (apiKey == null) {
            apiKey = UUID.randomUUID();
        }

        if (plano == null) {
            plano = TipoPlano.BASICO;
        }

        if (dataUltimoReset == null) {
            dataUltimoReset = LocalDate.now();
        }

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void antesDeAtualizar() {
        updatedAt = LocalDateTime.now();
    }

    public boolean podeFazerRequisicao() {

        verificarResetDiario();

        return requisicoesFeitasHoje < plano.getLimiteDiario();
    }

    public void registrarRequisicao() {

        verificarResetDiario();

        requisicoesFeitasHoje++;
    }

    private void verificarResetDiario() {

        if (!LocalDate.now().equals(dataUltimoReset)) {
            requisicoesFeitasHoje = 0;
            dataUltimoReset = LocalDate.now();
        }
    }

    public int getRequisicoesRestantes() {

        verificarResetDiario();

        return plano.getLimiteDiario() - requisicoesFeitasHoje;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getApiKey() {
        return apiKey;
    }

    public void setApiKey(UUID apiKey) {
        this.apiKey = apiKey;
    }

    public TipoPlano getPlano() {
        return plano;
    }

    public void setPlano(TipoPlano plano) {
        this.plano = plano;
    }

    public int getRequisicoesFeitasHoje() {
        return requisicoesFeitasHoje;
    }

    public void setRequisicoesFeitasHoje(int requisicoesFeitasHoje) {
        this.requisicoesFeitasHoje = requisicoesFeitasHoje;
    }

    public LocalDate getDataUltimoReset() {
        return dataUltimoReset;
    }

    public void setDataUltimoReset(LocalDate dataUltimoReset) {
        this.dataUltimoReset = dataUltimoReset;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}