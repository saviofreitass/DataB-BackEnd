package com.example.data_cheque.adapters.jdbc.empregador

object EmpregadorSqlExpressions {
    private fun campos() =
        """
            id,
            contador_id,
            cnpj,
            razao_social,
            nome_fantasia,
            endereco,
            criado_em,
            usuario_criacao,
            atualizado_em,
            usuario_atualizacao
        """

    fun sqlSelectAllContador() =
        """
            SELECT ${campos()}
            FROM data_cheque.empregador
            WHERE contador_id = :contador_id
        """.trimIndent()

    fun sqlSelectByIdContador() =
        """
            SELECT ${campos()}
            FROM data_cheque.empregador
            WHERE contador_id = :contador_id and id = :id
        """.trimIndent()

    fun sqlInsertEmpregador() =
        """
            INSERT INTO data_cheque.empregador(
                id,
                contador_id,
                cnpj,
                razao_social,
                nome_fantasia,
                endereco,
                criado_em,
                usuario_criacao,
                atualizado_em,
                usuario_atualizacao)
                VALUES (
                :id,
                :contador_id,
                :cnpj,
                :razao_social,
                :nome_fantasia,
                :endereco,
                :criado_em,
                :usuario_criacao,
                :atualizado_em,
                :usuario_atualizacao
                 )
            """.trimIndent()

    fun sqlUpdateEmpregador() =
        """
            UPDATE data_cheque.empregador
            SET cnpj = :cnpj,
                razao_social = :razao_socia,
                nome_fantasia = :nome_fantasia,
                endereco = :endereco,
                atualizado_em = :atualizado_em,
                usuario_atualizacao = :usuario_atualizacao
            WHERE id = :id    
        """.trimIndent()

    fun sqlDeleteById()=
        """
            DELETE FROM data_cheque.empregador
            WHERE id = :id
        """.trimIndent()
}