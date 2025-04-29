package com.example.data_cheque.adapters.jdbc.usuario

object UsuarioSQLExpressions {
    fun sqlSelectAll() = """
        SELECT 
            id,
            email,
            senha,
            tipoUsuario,
            criado_em,
            atualizadoEm,
            usuarioUpdate,
            nome,
            cpfcnpj,
            telefone,
            ativo
        FROM usuarios u
    """.trimIndent()

    fun sqlSelectById() = """
        SELECT 
            id,
            email,
            senha,
            tipoUsuario,
            criado_em,
            atualizadoEm,
            usuarioUpdate,
            nome,
            cpfcnpj,
            telefone,
            ativo
        FROM usuarios u
        WHERE u.id = :id
    """.trimIndent()

    fun sqlInsertFuncionario() = """
        INSERT INTO usuarios(
            id,
            email,
            senha,
            tipoUsuario,
            criado_em,
            atualizadoEm,
            usuarioUpdate,
            nome,
            cpfcnpj,
            telefone,
            ativo        
        ) VALUES(
           :id,
           :email,
           :senha,
           :tipoUsuario,
           :criado_em,
           :atualizadoEm,
           :usuarioUpdate,
           :nome,
           :cpfcnpj,
           :telefone,
           :ativo 
        )
    """.trimIndent()
    
    fun sqlUpdateFuncionario() = """
        UPDATE funcionarios
            set 
               id = :id,
               email = :email,
               senha = :senha,
               tipoUsuario = :tipoUsuario,
               criado_em = :creatAt,
               atualizadoEm = :atualizadoEm,
               usuarioUpdate = :usuarioUpdate,
               nome = :nome,
               cpfcnpj = :cpfcnpj,
               telefone = :telefone,
               ativo = :ativo 
            WHERE id = :id
    """.trimIndent()

    fun sqlDeleteFuncionarioById() = """
        DELETE
        FROM funcionarios
        WHERE id = :id
    """.trimIndent()
}