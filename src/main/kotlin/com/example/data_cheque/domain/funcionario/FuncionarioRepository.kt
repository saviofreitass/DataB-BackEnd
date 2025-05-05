package com.example.data_cheque.domain.funcionario

import com.example.data_cheque.application.funcionario.FuncionarioCommandResponse
import java.util.*

interface FuncionarioRepository {
    fun findAll(): List<FuncionarioCommandResponse>

    fun findById(funcionarioId: UUID): Funcionario?

    fun insert(funcionario: Funcionario): Boolean

    fun update(funcionario: Funcionario): Boolean

    fun delete(funcionarioId: UUID): Boolean}