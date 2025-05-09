CREATE SCHEMA IF NOT EXISTS data_cheque;

CREATE TABLE IF NOT EXISTS data_cheque.funcionarios (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL,
    empregador_id UUID,
    contador_id UUID,
    cargo VARCHAR(100),
    setor VARCHAR(100),
    data_admissao TIMESTAMP WITH TIME ZONE,
    salario NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES data_cheque.usuarios(id) ON DELETE CASCADE
);
