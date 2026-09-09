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

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, unique = true)
    private UUID apiKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPlano plano;

    @Column(name = "requisicoes_feitas_no_mes", nullable = false)
    private int requisicoesFeitasNoMes;

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
            dataUltimoReset = LocalDate.now().withDayOfMonth(1);
        }

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void antesDeAtualizar() {
        updatedAt = LocalDateTime.now();
    }

    public boolean podeFazerRequisicao() {

        verificarResetMensal();

        return requisicoesFeitasNoMes < plano.getLimiteMensal();
    }

    public void registrarRequisicao() {

        verificarResetMensal();

        requisicoesFeitasNoMes++;
    }

    private void verificarResetMensal() {

        LocalDate inicioDoMesAtual =
                LocalDate.now().withDayOfMonth(1);

        if (dataUltimoReset == null ||
                dataUltimoReset.isBefore(inicioDoMesAtual)) {

            requisicoesFeitasNoMes = 0;
            dataUltimoReset = inicioDoMesAtual;
        }
    }

    public int getRequisicoesRestantes() {

        verificarResetMensal();

        return plano.getLimiteMensal() - requisicoesFeitasNoMes;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public int getRequisicoesFeitasNoMes() {
        return requisicoesFeitasNoMes;
    }

    public void setRequisicoesFeitasNoMes(int requisicoesFeitasNoMes) {
        this.requisicoesFeitasNoMes = requisicoesFeitasNoMes;
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