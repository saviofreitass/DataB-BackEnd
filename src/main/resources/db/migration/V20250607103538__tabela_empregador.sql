CREATE TABLE IF NOT EXISTS data_cheque.empregador (
    id UUID PRIMARY KEY NOT NULL,
    contador_id UUID NOT NULL,
    cnpj VARCHAR(14) NOT NULL,
    razao_social VARCHAR(255) NOT NULL,
    nome_fantasia VARCHAR(255) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL,
    usuario_criacao VARCHAR(100) NOT NULL,
    atualizado_em TIMESTAMP WITH TIME ZONE,
    usuario_atualizacao VARCHAR(100),
    FOREIGN KEY (contador_id) REFERENCES data_cheque.contador(id)
)