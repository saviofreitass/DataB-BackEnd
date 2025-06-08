package com.example.data_cheque.adapters.jdbc.contador

object ContadorSQLExpressions {
    fun sqlSelectAll() = """
        SELECT
            c.id,
            c.usuario_id,
            c. pessoa_id,
            c.crc,
            p.nome,
            p.cpfcnpj,
            p.telefone,
            p.ativo,
            u.email,
            u.senha_hash,
            u.criado_em,
            u.usuario_criacao,
            u.tipo_usuario,
            u.atualizado_em,
            u.usuario_atualizacao
        FROM contador c
        JOIN pessoa p ON c.pessoa_id = p.id
        JOIN usuarios u ON c.usuario_id = u.id
    """.trimIndent()

    fun sqlSelectById() = """
        SELECT
            c.id,
            c.usuario_id,
            c. pessoa_id,
            c.crc,
            p.nome,
            p.cpfcnpj,
            p.telefone,
            p.ativo,
            u.email,
            u.senha_hash,
            u.criado_em,
            u.usuario_criacao,
            u.tipo_usuario,
            u.atualizado_em,
            u.usuario_atualizacao
        FROM contador c
        JOIN pessoa p ON c.pessoa_id = p.id
        JOIN usuarios u ON c.usuario_id = u.id
        WHERE c.id = :id
    """.trimIndent()

    fun sqlSelectByUserId() = """
        SELECT
            c.id,
            c.usuario_id,
            c. pessoa_id,
            c.crc,
            p.nome,
            p.cpfcnpj,
            p.telefone,
            p.ativo,
            u.email,
            u.senha_hash,
            u.criado_em,
            u.usuario_criacao,
            u.tipo_usuario,
            u.atualizado_em,
            u.usuario_atualizacao
        FROM contador c
        JOIN pessoa p ON c.pessoa_id = p.id
        JOIN usuarios u ON c.usuario_id = u.id
        WHERE c.usuario_id = :usuario_id
    """.trimIndent()

    fun sqlInsertContador() = """
        INSERT INTO contador(
            id,
            usuario_id,
            pessoa_id,
            crc
        ) VALUES(
            :id,
            :usuario_id,
            :pessoa_id,
            :crc
        )
    """.trimIndent()

    fun sqlUpdateContador() = """
        UPDATE contador
            set
                usuario_id = :usuario_id,
                pessoa_id = :pessoa_id,
                crc = :crc
            WHERE id = :id
    """.trimIndent()

    fun sqlDeleteContador() = """
        DELETE
        FROM contador
        WHERE id = :id
    """.trimIndent()
 }