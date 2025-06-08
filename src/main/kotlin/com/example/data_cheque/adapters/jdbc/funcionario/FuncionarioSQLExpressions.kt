package com.example.data_cheque.adapters.jdbc.funcionario

object FuncionarioSQLExpressions {
    fun sqlSelectAll() = """
    SELECT 
            f.id,
            f.contador_id,
            f.usuario_id,
            f.pessoa_id,
            f.cargo,
            f.setor,
            f.data_admissao,
            f.salario,
            p.nome,
            p.cpfcnpj,
            p.telefone,
            p.ativo,
            u.email,
            u.criado_em,
            u.usuario_criacao,
            u.tipo_usuario,
            u.atualizado_em,
            u.usuario_atualizacao
        FROM funcionarios f
        JOIN pessoa p ON f.pessoa_id = p.id
        JOIN usuarios u ON f.usuario_id = u.id
    """.trimIndent()


    fun sqlSelectById() = """
        SELECT 
            f.id,
            f.contador_id,
            f.usuario_id,
            f.pessoa_id,
            f.cargo,
            f.setor,
            f.data_admissao,
            f.salario,
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
        FROM funcionarios f
        JOIN pessoa p ON f.pessoa_id = p.id
        JOIN usuarios u ON f.usuario_id = u.id
        WHERE f.id = :id
    """.trimIndent()

    fun sqlSelectByUserId() = """
        SELECT 
            f.id,
            f.contador_id,
            f.usuario_id,
            f.pessoa_id,
            f.cargo,
            f.setor,
            f.data_admissao,
            f.salario,
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
        FROM funcionarios f
        JOIN pessoa p ON f.pessoa_id = p.id
        JOIN usuarios u ON f.usuario_id = u.id
        WHERE f.usuario_id = :usuario_id
    """.trimIndent()

    fun sqlInsertFuncionario() = """
        INSERT INTO funcionarios(
            id,
            contador_id,
            usuario_id,
            pessoa_id,
            cargo,
            setor,
            data_admissao,
            salario    
        ) VALUES(
            :id,
            :contador_id,
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
                contador_id = :contador_id,
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