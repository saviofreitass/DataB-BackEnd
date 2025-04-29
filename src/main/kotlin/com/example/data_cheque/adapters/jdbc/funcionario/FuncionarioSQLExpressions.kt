package com.example.data_cheque.adapters.jdbc.funcionario

object FuncionarioSQLExpressions {
    fun sqlSelectAll() = """
        SELECT 
            id,
            usuario_id,
            pessoa_id,
            cargo,
            setor,
            data_admissao,
            salario
        FROM funcionarios
    """.trimIndent()

    fun sqlSelectById() = """
        SELECT 
            id,
            usuario_id,
            pessoa_id,
            cargo,
            setor,
            data_admissao,
            salario
        FROM funcionarios
        WHERE id = :id
    """.trimIndent()

    fun sqlInsertFuncionario() = """
        INSERT INTO funcionarios(
            id,
            usuario_id,
            pessoa_id,
            cargo,
            setor,
            data_admissao,
            salario    
        ) VALUES(
            :id,
            :usuario_id,
            :pessoa_id,
            :cargo,
            :setor,
            :data_admissao,
            :salario
        )
    """.trimIndent()

    fun sqlUpdateFuncionario() = """
        UPDATE funcionarios
            set 
                usuario_id = :usuario_id,
                pessoa_id = :pessoa_id,
                cargo = :cargo,
                setor = :setor,
                data_admissao = :data_admissao,
                salario = :salario     
        WHERE id = :id
    """.trimIndent()

    fun sqlDeleteFuncionarioById() = """
        DELETE
        FROM funcionarios
        WHERE id = :id
    """.trimIndent()
}