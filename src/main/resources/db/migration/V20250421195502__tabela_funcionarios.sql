CREATE TABLE IF NOT EXISTS data_cheque.funcionarios (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL,
    empregador_id UUID NOT NULL,
    contador_id UUID NOT NULL,
    cargo VARCHAR(100),
    setor VARCHAR(100),
    data_admissao TIMESTAMP WITH TIME ZONE,
    salario NUMERIC(10, 2) NOT NULL,
    status BOOLEAN,
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em TIMESTAMP WITH TIME ZONE,
);
