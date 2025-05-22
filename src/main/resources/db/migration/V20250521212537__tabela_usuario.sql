CREATE TABLE IF NOT EXISTS data_cheque.usuarios (
    id UUID PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(20) NOT NULL CHECK (tipo_usuario IN ('ROLE_FUNCIONARIO', 'ROLE_CONTADOR')),
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL,
    usuario_criacao VARCHAR(100) NOT NULL,
    atualizado_em TIMESTAMP WITH TIME ZONE,
    usuario_atualizacao VARCHAR(100)
);