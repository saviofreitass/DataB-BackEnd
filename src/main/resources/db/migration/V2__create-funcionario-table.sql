CREATE TABLE funcionario (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    usuario_id UUID NOT NULL,
    empregador_id UUID NOT NULL,
    contador_id UUID NOT NULL,
    cargo VARCHAR(100),
    setor VARCHAR(100),
    data_admissao CURRENT_DATE,
    salario DOUBLE NOT NULL,
    status BOOLEAN,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (empregador_id) REFERENCES empregador(id),
    FOREIGN KEY (contador_id) REFERENCES contador(id)
);
