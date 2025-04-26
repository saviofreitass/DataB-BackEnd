package com.example.data_cheque.adapters.jdbc.contracheque

object ContrachequeSqlExpressions {
    private fun campos() = 
        """
            id,
            func_id,
            data_pagamento,
            data_ref_inicio,       
            data_ref_fim,         
            salario_base,
            hora_extra,
            adicional_noturno,
            comissoes,
            beneficios,
            inss,
            irrf,
            fgts,
            outros_descontos,
            criado_em,
            usuario_criacao,
            atualizado_em,
            usuario_atualizacao"""

    fun sqlSelectAll() =
        """
        SELECT ${campos()}
        from contracheque
        """.trimIndent()

    fun sqlSelectById() =
        """
        SELECT ${campos()}
        from contracheque
        WHERE id = :id
        """.trimIndent()

    fun sqlInsertContracheque() =
        """
        INSERT INTO contracheque(
            id,
            func_id,
            data_pagamento,
            data_ref_inicio,       
            data_ref_fim,         
            salario_base,
            hora_extra,
            adicional_noturno,
            comissoes,
            beneficios,
            inss,
            irrf,
            fgts,
            outros_descontos,
            criado_em,
            usuario_criacao,
            atualizado_em,
            usuario_atualizacao
        ) VALUES (
            :id,
            :func_id,
            :data_pagamento,
            :data_ref_inicio,
            :data_ref_fim,         
            :salario_base,
            :hora_extra,
            :adicional_noturno,
            :comissoes,
            :beneficios,
            :inss,
            :irrf,
            :fgts,
            :outros_descontos,
            :criado_em,
            :usuario_criacao,
            :atualizado_em,
            :usuario_atualizacao
        )
        """.trimIndent()

    fun sqlUpdateContracheque() =
        """
        UPDATE contracheque
        SET data_pagamento = :data_pagamento,
            data_ref_inicio = :data_ref_inicio,       
            data_ref_fim = :data_ref_fim,         
            salario_base = :salario_base,
            hora_extra = :hora_extra,
            adicional_noturno = :adicional_noturno,
            comissoes = :comissoes,
            beneficios = :beneficios,
            inss = :inss,
            irrf = :irrf,
            fgts = :fgts,
            outros_descontos = :outros_descontos,
            atualizado_em = :atualizado_em,
            usuario_atualizacao = :usuario_atualizacao
        WHERE id = :id
        """.trimIndent()

    fun sqlDeleteById() =
        """
        DELETE from contracheque
        WHERE id = :id
        """.trimIndent()
    
}