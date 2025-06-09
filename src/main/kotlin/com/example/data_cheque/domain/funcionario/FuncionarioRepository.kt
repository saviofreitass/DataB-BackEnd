package com.example.data_cheque.domain.funcionario

import com.example.data_cheque.domain.pessoa.Pessoa
import java.util.*

interface FuncionarioRepository {
    fun findAll(): List<Funcionario>

    fun findById(funcionarioId: UUID): Funcionario?

    fun findByUserId(usuarioId: UUID): Funcionario?

    fun insert(funcionario: Funcionario): Boolean

    fun update(funcionario: Funcionario): Boolean

    fun delete(funcionarioId: UUID): Boolean
}