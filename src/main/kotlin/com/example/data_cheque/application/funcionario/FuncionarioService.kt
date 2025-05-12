package com.example.data_cheque.application.funcionario

import com.example.data_cheque.application.funcionario.exception.FuncionarioNaoCadastrado
import com.example.data_cheque.application.funcionario.exception.FuncionarioNaoEncontradoException
import com.example.data_cheque.application.pessoa.PessoaCreateCommand
import com.example.data_cheque.application.pessoa.PessoaService
import com.example.data_cheque.application.pessoa.PessoaUpdateCommand
import com.example.data_cheque.application.usuario.UsuarioCreateCommand
import com.example.data_cheque.application.usuario.UsuarioService
import com.example.data_cheque.application.usuario.UsuarioUpdateCommand
import com.example.data_cheque.domain.funcionario.Funcionario
import com.example.data_cheque.domain.funcionario.FuncionarioRepository
import com.example.data_cheque.domain.usuario.Role
import org.springframework.stereotype.Service
import java.util.*

@Service
class FuncionarioService (
    private val funcionarioRepository: FuncionarioRepository,
    private val usuarioService: UsuarioService,
    private val pessoaService: PessoaService
) {
    fun findAll(): List<Funcionario> {
        return funcionarioRepository.findAll()
    }
    fun findById(funcionarioId: UUID): Funcionario {
        return funcionarioRepository.findById(funcionarioId) ?: throw FuncionarioNaoEncontradoException(funcionarioId)
    }

    fun insert(funcionarioCreateCommand: FuncionarioCreateCommand): Funcionario? {
        try{
            val usuarioCreateCommand = UsuarioCreateCommand(
                email = funcionarioCreateCommand.email,
                senha = funcionarioCreateCommand.senha,
                tipoUsuario = Role.ROLE_FUNCIONARIO,
                atualizadoEm = null
            )
            val novoUsuario = usuarioService.insert(usuarioCreateCommand)

            val pessoaCreateCommand = PessoaCreateCommand(
                usuarioId = novoUsuario.id,
                nome = funcionarioCreateCommand.nome,
                cpfcnpj = funcionarioCreateCommand.cpfcnpj,
                telefone = funcionarioCreateCommand.telefone,
                ativo = true
            )
            val novaPessoa = pessoaService.insert(pessoaCreateCommand)

            val novoFuncionario = funcionarioCreateCommand.toFuncionario( novaPessoa.id, novoUsuario.id)
            funcionarioRepository.insert(novoFuncionario)
            return funcionarioRepository.findById(novoFuncionario.id)
        }catch (e: FuncionarioNaoCadastrado){
            throw e
        }
    }

    fun update(funcionarioUpdateCommand: FuncionarioUpdateCommand, funcionarioId: UUID): Funcionario {
        val usuarioUpdateCommand = UsuarioUpdateCommand(
            id = funcionarioUpdateCommand.usuarioId,
            email = funcionarioUpdateCommand.email,
            senha = funcionarioUpdateCommand.senha
        )
        val usuarioAtualizado = usuarioService.update(usuarioUpdateCommand, funcionarioUpdateCommand.usuarioId)

        val pessoaUpdateCommand = PessoaUpdateCommand(
            nome = funcionarioUpdateCommand.nome,
            cpfcnpj = funcionarioUpdateCommand.cpfcnpj,
            telefone = funcionarioUpdateCommand.telefone,
            ativo = true
        )
        val pessoaAtualizada = pessoaService.update(pessoaUpdateCommand, funcionarioUpdateCommand.pessoaId)

        val funcionarioEncontrado = funcionarioRepository.findById(funcionarioId)
        funcionarioEncontrado?.let { funcionarioUpdateCommand.toFuncionarioAtualizado(it) }
            ?.let { funcionarioRepository.update(it) }
        return findById(funcionarioId)
    }

    fun delete(funcionarioId: UUID){
        funcionarioRepository.findById(funcionarioId = funcionarioId) ?: throw FuncionarioNaoEncontradoException(funcionarioId)
        funcionarioRepository.delete(funcionarioId)
    }
}