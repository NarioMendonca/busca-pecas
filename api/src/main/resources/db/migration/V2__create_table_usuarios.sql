CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    api_key UUID NOT NULL UNIQUE,
    plano VARCHAR(50) NOT NULL,
    requisicoes_feitas_hoje INTEGER NOT NULL DEFAULT 0,
    data_ultimo_reset DATE NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);