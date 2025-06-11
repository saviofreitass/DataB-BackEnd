package com.example.data_cheque.adapters.jdbc.usuario

object UsuarioSQLExpressions {
    fun sqlSelectAll() = """
        SELECT 
            id,
            email,
            senha_hash,
            tipo_usuario,
            criado_em,
            usuario_criacao,
            atualizado_em,
            usuario_atualizacao
        FROM usuarios u
    """.trimIndent()

    fun sqlSelectById() = """
        SELECT 
            id,
            email,
            senha_hash,
            tipo_usuario,
            criado_em,
            usuario_criacao,
            atualizado_em,
            usuario_atualizacao
        FROM usuarios u
        WHERE u.id = :id
    """.trimIndent()

    fun findByEmail() = """
        SELECT 
            id,
            email,
            senha_hash,
            tipo_usuario,
            criado_em,
            usuario_criacao,
            atualizado_em,
            usuario_atualizacao
        FROM usuarios
        WHERE email = :email
    """.trimIndent()

    fun sqlInsertUsuario() = """
        INSERT INTO usuarios(
            id,
            email,
            senha_hash,
            tipo_usuario,
            criado_em,
            usuario_criacao,
            atualizado_em,
            usuario_atualizacao      
        ) VALUES(
           :id,
           :email,
           :senha_hash,
           :tipo_usuario,
           :criado_em,
           :usuario_criacao,
           :atualizado_em,
           :usuario_atualizacao
        )
    """.trimIndent()
    
    fun sqlUpdateUsuario() = """
        UPDATE usuarios
            set 
               id = :id,
               email = :email,
               senha_hash = :senha_hash,
               tipo_usuario = :tipo_usuario,
               criado_em = :criado_em,
               usuario_criacao = :usuario_criacao,
               atualizado_em = :atualizado_em,
               usuario_atualizacao = :usuario_atualizacao
            WHERE id = :id
    """.trimIndent()

    fun sqlDeleteUsuarioById() = """
        DELETE
        FROM usuarios
        WHERE id = :id
    """.trimIndent()
}