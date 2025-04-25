CREATE TABLE IF NOT EXISTS data_cheque.contador (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL,
    crc VARCHAR(12) NOT NULL UNIQUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITH TIME ZONE,
    status BOOLEAN
);