CREATE TABLE IF NOT EXISTS data_cheque.funcionarios (
    id UUID PRIMARY KEY NOT NULL,
    usuario_id UUID NOT NULL,
    pessoa_id UUID NOT NULL,
    empregador_id UUID NOT NULL,
    contador_id UUID NOT NULL,
    cargo VARCHAR(100),
    setor VARCHAR(100),
    data_admissao TIMESTAMP WITH TIME ZONE,
    salario NUMERIC(10, 2) NOT NULL,
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL,
    usuario_criacao VARCHAR(100) NOT NULL,
    atualizado_em TIMESTAMP WITH TIME ZONE,
    usuario_atualizacao VARCHAR(100),
    FOREIGN KEY (usuario_id) REFERENCES data_cheque.usuarios(id),
    FOREIGN KEY (empregador_id) REFERENCES data_cheque.empregador(id),
    FOREIGN KEY (contador_id) REFERENCES data_cheque.contador(id)
    );