CREATE SCHEMA IF NOT EXISTS data_cheque;

CREATE TABLE IF NOT EXISTS data_cheque.pessoa(
    id UUID PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpfcnpj VARCHAR(14) NOT NULL,
    telefone VARCHAR(11),
    ativo BOOLEAN NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES data_cheque.usuarios(id) ON DELETE CASCADE
)