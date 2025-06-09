package com.example.data_cheque.application.funcionario

import com.example.data_cheque.application.funcionario.exception.FuncionarioNaoCadastrado
import com.example.data_cheque.application.funcionario.exception.FuncionarioNaoEncontradoException
import com.example.data_cheque.application.pessoa.PessoaService
import com.example.data_cheque.application.pessoa.exception.PessoaNaoEncontradaException
import com.example.data_cheque.application.usuario.EncoderPassword
import com.example.data_cheque.application.usuario.UsuarioService
import com.example.data_cheque.application.usuario.exception.UsuarioNaoEncontradoException
import com.example.data_cheque.domain.funcionario.Funcionario
import com.example.data_cheque.domain.funcionario.FuncionarioRepository
import com.example.data_cheque.domain.pessoa.Pessoa
import org.springframework.stereotype.Service
import java.util.*

@Service
class FuncionarioService (
    private val funcionarioRepository: FuncionarioRepository,
    private val usuarioService: UsuarioService,
    private val pessoaService: PessoaService,
    private val encoderPassword: EncoderPassword
) {
    fun findAll(): List<Funcionario> {
        return funcionarioRepository.findAll()
    }
    fun findById(funcionarioId: UUID): Funcionario {
        return funcionarioRepository.findById(funcionarioId) ?: throw FuncionarioNaoEncontradoException(funcionarioId)
    }

    fun findByUserId(usuarioId: UUID): Funcionario?{
        return funcionarioRepository.findByUserId(usuarioId)
    }

    fun insert(funcionarioCreateCommand: FuncionarioCreateCommand): Funcionario? {

        usuarioService.createValidations(funcionarioCreateCommand.usuario)
        pessoaService.createValidation(funcionarioCreateCommand.pessoa)

        try{
            val novoUsuario = usuarioService.insert(funcionarioCreateCommand.usuario)
            val novaPessoa = pessoaService.insert(funcionarioCreateCommand.pessoa)

            val novoFuncionario = funcionarioCreateCommand.toFuncionario(novaPessoa, novoUsuario)
            funcionarioRepository.insert(novoFuncionario)
            return funcionarioRepository.findById(novoFuncionario.id)
        }catch (e: FuncionarioNaoCadastrado){
            throw e
        }
    }

    fun update(funcionarioUpdateCommand: FuncionarioUpdateCommand, funcionarioId: UUID): Funcionario {
        val usuarioAtualizado = funcionarioUpdateCommand.usuario?.let { usuarioDto ->
            usuarioService.updateValidation(usuarioDto)
            usuarioService.update(usuarioDto, usuarioDto.id)
        }

        val pessoaAtualizada = funcionarioUpdateCommand.pessoa?.let { pessoaDTO ->
            pessoaService.updateValidation(pessoaDTO)
            pessoaService.update(pessoaDTO, pessoaDTO.id)
        }

        val funcionarioEncontrado = funcionarioRepository.findById(funcionarioId)
        funcionarioEncontrado?.let { funcionarioUpdateCommand.toFuncionarioAtualizado(funcionarioEncontrado, encoderPassword) }
            ?.let { funcionarioRepository.update(it) }
        return findById(funcionarioId)
    }

    fun delete(funcionarioId: UUID){
        val funcionario = funcionarioRepository.findById(funcionarioId)?: throw FuncionarioNaoEncontradoException(funcionarioId)
        val usuarioId = funcionario.usuario.id
        val pessoaId = funcionario.pessoa.id
        funcionarioRepository.delete(funcionarioId)
        usuarioService.delete(usuarioId)
        pessoaService.delete(pessoaId)
    }
}