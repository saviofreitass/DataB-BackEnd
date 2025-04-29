package com.example.data_cheque.application.funcionario

import com.example.data_cheque.application.funcionario.exception.FuncionarioNaoEncontradoException
import com.example.data_cheque.application.usuario.UsuarioCreateCommand
import com.example.data_cheque.domain.funcionario.Funcionario
import com.example.data_cheque.domain.funcionario.FuncionarioRepository
import java.util.*

class FuncionarioService (
    private val funcionarioRepository: FuncionarioRepository
) {
    fun findAll(): List<Funcionario> {
        return funcionarioRepository.findAll()
    }
    fun findById(funcionarioId: UUID): Funcionario {
        return funcionarioRepository.findById(funcionarioId) ?: throw FuncionarioNaoEncontradoException(funcionarioId)
    }

    fun insert(funcionario: FuncionarioCreateCommand, usuarioCreateCommand: UsuarioCreateCommand): Funcionario {
        val funcionarioDomain = funcionario.toFuncionario()
        funcionarioRepository.insert(funcionario = funcionarioDomain)
        return findById(funcionarioDomain.id)
    }

    fun update(funcionario: FuncionarioUpdateCommand, funcionarioId: UUID): Funcionario {
        funcionarioRepository.findById(funcionarioId = funcionarioId) ?: throw FuncionarioNaoEncontradoException(funcionarioId)
        funcionarioRepository.update(funcionario.toFuncionario(funcionarioId))
        return findById(funcionarioId = funcionarioId)
    }

    fun delete(funcionarioId: UUID){
        funcionarioRepository.findById(funcionarioId = funcionarioId) ?: throw FuncionarioNaoEncontradoException(funcionarioId)
        funcionarioRepository.delete(funcionarioId)
    }
}