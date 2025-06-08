CREATE TABLE IF NOT EXISTS data_cheque.contador (
    id UUID PRIMARY KEY NOT NULL,
    usuario_id UUID NOT NULL,
    pessoa_id UUID NOT NULL,
    crc VARCHAR(14),
    FOREIGN KEY (usuario_id) REFERENCES data_cheque.usuarios(id),
    FOREIGN KEY (pessoa_id) REFERENCES data_cheque.pessoa(id)
)