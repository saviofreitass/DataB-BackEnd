CREATE TABLE IF NOT EXISTS contracheque
(
    id                      UUID                        NOT NULL PRIMARY KEY,
    func_id                 UUID                        NOT NULL,
    data_pagamento          DATE                        NOT NULL,
    periodo_referencia      VARCHAR(10)                 NOT NULL,
    salario_base            DECIMAL(12,2)               NOT NULL,
    hora_extra              DECIMAL(12,2),
    adicional_noturno       DECIMAL(12,2),
    comissoes               DECIMAL(12,2),
    beneficios              DECIMAL(12,2),
    inss                    DECIMAL(12,2)               NOT NULL,
    irrf                    DECIMAL(12,2),
    fgts                    DECIMAL(12,2)               NOT NULL,
    outros_descontos        DECIMAL(12,2),
    criado_em               TIMESTAMP WITH TIME ZONE    NOT NULL,
    usuario_criacao         VARCHAR(100)                NOT NULL,
    atualizado_em           TIMESTAMP WITH TIME ZONE,
    usuario_atualizacao     VARCHAR(100)
)