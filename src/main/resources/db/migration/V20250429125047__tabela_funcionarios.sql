CREATE SCHEMA IF NOT EXISTS data_cheque;

CREATE TABLE IF NOT EXISTS data_cheque.funcionarios (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL,
    pessoa_id UUID NOT NULL,
    empregador_id UUID NOT NULL,
    contador_id UUID NOT NULL,
    cargo VARCHAR(100),
    setor VARCHAR(100),
    data_admissao TIMESTAMP WITH TIME ZONE,
    salario NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES data_cheque.usuarios(id),
    FOREIGN KEY (pessoa_id) REFERENCES data_cheque.pessoa(id),
    FOREIGN KEY (empregador_id) REFERENCES data_cheque.empregador(id),
);
