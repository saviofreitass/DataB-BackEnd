CREATE TABLE data_cheque.funcionarios (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL,
    empregador_id UUID NOT NULL,
    contador_id UUID NOT NULL,
    cargo VARCHAR(100),
    setor VARCHAR(100),
    data_admissao DATE,
    salario NUMERIC(10, 2) NOT NULL,
    status BOOLEAN,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (empregador_id) REFERENCES empregador(id),
    FOREIGN KEY (contador_id) REFERENCES contador(id)
);
