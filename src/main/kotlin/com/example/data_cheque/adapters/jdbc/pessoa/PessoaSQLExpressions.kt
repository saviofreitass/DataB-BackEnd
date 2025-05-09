package com.example.data_cheque.adapters.jdbc.pessoa

object PessoaSQLExpressions {
    fun sqlSelectAll() = """
        SELECT 
            id,
            usuario_id,
            nome,
            cpfcnpj,
            telefone,
            ativo
        FROM pessoa
    """.trimIndent()

    fun sqlSelectById() = """
        SELECT 
            id,
            usuario_id,
            nome,
            cpfcnpj,
            telefone,
            ativo
        FROM pessoa
        WHERE id = :id
    """.trimIndent()

    fun sqlInsertPessoa() = """
        INSERT INTO pessoa(
            id,
            usuario_id,
            nome,
            cpfcnpj,
            telefone,
            ativo      
        ) VALUES(
            :id,
            :usuario_id,
            :nome,
            :cpfcnpj,
            :telefone,
            :ativo
        )
    """.trimIndent()

    fun sqlUpdatePessoa() = """
        UPDATE pessoa
            set 
                usuario_id = :usuario_id,
                nome = :nome,
                cpfcnpj = :cpfcnpj,
                telefone  = :telefone,
                ativo = :ativo       
        WHERE id = :id
    """.trimIndent()

    fun sqlDeletePessoaById() = """
        DELETE
        FROM pessoa
        WHERE id = :id
    """.trimIndent()
}